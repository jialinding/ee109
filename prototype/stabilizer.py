import numpy as np
import cv2

threshold = 3

def detect_features(frame):
    orb = cv2.ORB()
    kp = orb.detect(frame, None)
    return cv2.drawKeypoints(frame, kp, color=(255,0,0))

def match_features(fr1, fr2, f1, f2):
    f1, d1 = orb.compute(fr1, f1)
    f2, d2 = orb.computer(fr2, f2)

    bf = cv2.BFMatcher(cv2.NORM_HAMMING)
    temp = bf.knnmatch(d1, d2, k=2)
    matches = []
    for m,n in temp:
        if m.distance < 0.8*n.distance:
            matches.append([m])

    return matches
    # return cv2.drawMatches(fr1, f1, fr1, f2, matches[:10], flags=2)

def estimate_transform(matches):
	pts1 = matches[0]
	pts2 = matches[1]
	M = cv2.getAffineTransform(pts1, pts2)
	return M


def stabilize_video(video):
	cap = cv2.VideoCapture(video)

	ret, first_frame = cap.read()
	first_frame = cv2.cvtColor(first_frame, cv2.COLOR_BGR2GRAY)
	prev_frame = first_frame
	prev_features = detect_features(first_frame)
	rows, cols = first_frame[:,:,0].shape

	cumulative_transform = np.eye(2, 3)

	while(cap.isOpened()):
		ret, frame = cap.read()

		gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

		features = detect_features(frame)
		matches = match_features(prev_frame, frame, prev_features, features)
		transform = estimate_transform(matches)

		# Append to cumulative transform
		temp_cum_transform = np.identity(3)
		temp_cum_transform[:2,:] = cumulative_transform
		temp_transform = np.identity(3)
		temp_transform[:2,:] = transform
		cumulative_transform = temp_cum_transform.dot(temp_transform)[:2,:]

		stabilized = cv2.warpAffine(first_frame, cumulative_transform, (cols, rows))
		# stabilized = gray

		cv2.imshow('frame', stabilized)

		prev_frame = frame
		prev_features = features

		if cv2.waitKey(1) & 0xFF == ord('q'):
			break

	cap.release()
	cv2.destroyAllWindows()
