import numpy as np
import cv2

threshold = 3


def detect_features(frame):
	orb = cv2.ORB_create()
	kp = orb.detect(frame, None)
	return kp


def match_features(fr1, fr2, f1, f2):
	orb = cv2.ORB_create()
	f1, d1 = orb.compute(fr1, f1)
	f2, d2 = orb.compute(fr2, f2)

	bf = cv2.BFMatcher(cv2.NORM_HAMMING)
	temp = bf.knnMatch(d1, d2, k=2)
	matches = []
	for m,n in temp:
		if m.distance < 0.8*n.distance:
			matches.append(m)

	pts1 = []
	pts2 = []
	for match in matches:
		pts1_ind = match.queryIdx
		pts2_ind = match.trainIdx
		pts1_kp = f1[pts1_ind]
		pts2_kp = f2[pts2_ind]
		pts1.append(pts1_kp.pt)
		pts2.append(pts2_kp.pt)

	return np.asarray(pts1), np.asarray(pts2)
	# return cv2.drawMatches(fr1, f1, fr1, f2, matches, np.zeros((1,1)), flags=2)


def estimate_transform(pts1, pts2):
	M, _ = cv2.findHomography(pts1, pts2, cv2.RANSAC, 5.0)
	return M


def stabilize_video(video):
	cv2.ocl.setUseOpenCL(False)
	cap = cv2.VideoCapture(video)

	ret, first_frame = cap.read()
	first_frame = cv2.cvtColor(first_frame, cv2.COLOR_BGR2GRAY)
	prev_frame = first_frame
	prev_features = detect_features(first_frame)
	rows, cols = first_frame.shape

	cumulative_transform = np.identity(3)

	while(cap.isOpened()):
		ret, frame = cap.read()
		if ret == False:
			# video is over
			break

		gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

		features = detect_features(frame)
		pts1, pts2 = match_features(frame, prev_frame, features, prev_features)
		transform = estimate_transform(pts1, pts2)

		# Append to cumulative transform
		temp_cum_transform = np.identity(4)
		temp_cum_transform[:3,:3] = cumulative_transform
		temp_transform = np.identity(4)
		temp_transform[:3,:3] = transform
		cumulative_transform = temp_transform.dot(temp_cum_transform)[:3,:3]

		stabilized = cv2.warpPerspective(frame, cumulative_transform, (cols, rows))

		cv2.imshow('frame', stabilized)

		prev_frame = frame
		prev_features = features

		if cv2.waitKey(1) & 0xFF == ord('q'):
			break

	cap.release()
	cv2.destroyAllWindows()
