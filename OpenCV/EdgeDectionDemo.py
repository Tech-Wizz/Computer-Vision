import cv2 as cv
import numpy as np

src = cv.imread("japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    gray = cv.cvtColor(src, cv.COLOR_RGB2GRAY)
    final = cv.Canny(gray, 75,200)
    cv.imshow("Original", gray)
    cv.imshow("adaptive", final)            
                
                
