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


def stabilize_video(video):