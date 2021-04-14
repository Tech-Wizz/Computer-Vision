import cv2 as cv, time
import numpy as np
import sys

capture = cv.VideoCapture()
capture.open(0, cv.CAP_DSHOW) #from opencv.org; allows usb cameras to open almost immediatly
capture.set(3,640)
capture.set(4,480)
cv.namedWindow("Stream")
cv.namedWindow("Edges")
cv.namedWindow("Motion - Ghost")
cv.namedWindow("Motion - Binary")
cv.namedWindow("Motion - Contours")

status, img = capture.read()
ones = np.full((480, 640, 3), 1, np.uint8)
zeros = np.full((480, 640, 1), 0, np.uint8)
deltas = image1 = np.full((480, 640, 3), 0, np.float32)

print("start...")

while True:
    try:
        status, img = capture.read() ##this is one frame
        og = img.copy()
        img = cv.accumulateWeighted(img, deltas, .5)
        img = cv.convertScaleAbs(img)
        img = cv.convertScaleAbs(img, alpha=2.2, beta=0)
        img = cv.blur(img, (9,9))
        blur = img.copy()
        image1 = cv.convertScaleAbs(image1)
        image1 = cv.absdiff(img, image1)
        image1 = cv.cvtColor(image1, cv.COLOR_RGB2GRAY)
        cv.imshow("Motion - Ghost", image1)
        ret,image1 = cv.threshold(image1, 20, 40, cv.THRESH_BINARY)
        image1 = cv.blur(image1, (25,25))
        ret,image1 = cv.threshold(image1, 10, 255, cv.THRESH_BINARY)
        cv.imshow("Motion - Binary", image1)
        edges = cv.Canny(image1.copy(), 10, 255)
        image2 = image1.copy()
        cv.imshow("Edges", edges)
        contours, h = cv.findContours(edges, cv.RETR_CCOMP, cv.CHAIN_APPROX_SIMPLE)

        for c in contours: ##concepts borrowed from Clayton Darwin - youtube
            if (cv.contourArea(c) < 2000):
                continue

            m = cv.moments(c)
            cx = int(m['m10']/m['m00'])
            cy = int(m['m01']/m['m00'])
            x,y,w,h = cv.boundingRect(c)
            rx = x+int(w/2)
            ry = y+int(h/2)
            ca = cv.contourArea(c)

            og = cv.rectangle(og, (x,y), (x+w,y+h), (0,255,0), 2)
            ones = cv.rectangle(ones, (x,y), (x+w,y+h), (0,255,0), 2)

        cv.imshow("Stream", og)
        cv.imshow("Motion - Contours", ones)
        ones = np.full((480, 640, 3), 1, np.uint8)

        image1 = cv.convertScaleAbs(blur).astype('float32')

        kill = cv.waitKey(1)
        if kill == 27: #esc key
            break
    except:
##        print("img: " + str(type(img[1][1][1])) + " " + str(img.shape))
##        print("image1: " + str(type(image1[1][1])) + " " + str(image1.shape))
##        print("ones: " + str(type(ones[1][1][0])) + " " + str(ones.shape))
##        print("zeros: " + str(type(zeros[1][1][0])) + " " + str(zeros.shape))
##        print("deltas: " + str(type(deltas[1][1][1])) + " " + str(deltas.shape))
        
        e = sys.exc_info()[0]
        capture.release()
        cv.destroyAllWindows()
        raise
capture.release()
cv.destroyAllWindows()
