import cv2 as cv
import numpy as np

src = cv.imread("japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    cv.imshow("windowM", src)
    rows,cols = src.shape[:2] 
 #shifting the image 100 pixels in both dimensions
    #make sure you import numpy
    M = np.float32([[1,0,-100],[0,1,-100]]) 
    dst = cv.warpAffine(src,M,(cols,rows)) 

    cv.imshow("windowMy", dst)
                
                
                
