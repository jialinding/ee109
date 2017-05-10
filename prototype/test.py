from stabilizer import *
from timeit import default_timer as timer

def main():
	start = timer()
	stabilize_video('sample_video.mp4')
	end = timer()
	print "Time: {} s".format(end-start)


if __name__ == "__main__":
	main()