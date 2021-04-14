import cv2 as cv

capture = cv.VideoCapture(0)
cv.namedWindow("Video Capture")

print("start...")
while True:
    status, img = capture.read()

    cv.imshow("Stream", img)

    kill = cv.waitKey(1)
    if kill == 27: #esc key
        break
cv.destroyAllWindows()
