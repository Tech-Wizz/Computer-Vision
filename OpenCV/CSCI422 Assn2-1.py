import cv2 as cv, time
import numpy as np

global hh #high hue
global hv #high value
global hs #high saturation
global lh
global lv
global ls
hh = hv = hs = lh = lv = ls = -1

low = np.array([0, 0, 0])
high = np.array([255, 255, 255])

def getPixel(event, x, y, flags, param):
    if(event == cv.EVENT_LBUTTONUP):
        print("\nloc. (x, y): " + str(x) + ", " + str(y))
        print("hsv: " + str(img[y, x]))

def setHueLow(value):
##    print("Hue Low Set: " + str(value))
    global lh
    lh = value
    low[0] = lh

def setHueHigh(value):
##    print("Hue High Set: " + str(value))
    global hh
    hh = value
    high[0] = hh

def setSatLow(value):
##    print("Sat Low Set: " + str(value))
    global ls
    ls = value
    low[2] = ls

def setSatHigh(value):
##    print("Sat High Set: " + str(value))
    global hs
    hs = value
    high[2] = hs

def setValueLow(value):
##    print("Value Low Set: " + str(value))
    global lv
    lv = value
    low[1] = lv

def setValueHigh(value):
##    print("Value High Set: " + str(value))
    global hv
    hv = value
    high[1] = hv
##    print("Low: " + str(low) + "\nHigh: " + str(high))
##    print("\n" + str(lh) + "-" + str(hh))
##    print("\n" + str(ls) + "-" + str(hs))
##    print("\n" + str(lv) + "-" + str(hv))

capture = cv.VideoCapture()
capture.open(0, cv.CAP_DSHOW) #from opencv.org; allows usb cameras to open almost immediatly
cv.namedWindow("Stream")
cv.namedWindow("HSV")
cv.namedWindow("Contours")
cv.namedWindow("Tracking")
cv.setMouseCallback("HSV", getPixel)

cv.createTrackbar('low hue', "Stream", 0, 255, setHueLow)
cv.createTrackbar('high hue', "Stream", 255, 255, setHueHigh)
cv.createTrackbar('low value', "Stream", 0, 255, setValueLow)
cv.createTrackbar('high value', "Stream", 255, 255, setValueHigh)
cv.createTrackbar('low saturation', "Stream", 0, 255, setSatLow)
cv.createTrackbar('high saturation', "Stream", 255, 255, setSatHigh)

print("start...")
while True:
    status, img = capture.read()
    

    cv.imshow("Stream", img)
    img = cv.cvtColor(img, cv.COLOR_RGB2HSV)
    cv.imshow("HSV", img)

    if(hh !=  -1 and hv != -1 and hs !=  -1 and  lh !=  -1 and lv !=  -1 and ls !=  -1):
        kernel = np.ones((9, 9), np.uint8) #geeks for geeks
        tracking = cv.inRange(img, low, high)
        tracking = cv.erode(tracking, kernel, iterations = 5)
        tracking = cv.dilate(tracking, kernel, iterations = 3)
        cv.imshow("Tracking", tracking)

    kill = cv.waitKey(1)
    if kill == 27: #esc key
        break
capture.release()
cv.destroyAllWindows()

