import cv2 as cv

src = cv.imread("C:/Users/wowar/Documents/japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    cv.imshow("window", src)
    src = cv.Canny(src, 100, 300)
    cv.imshow("canny window", src)
