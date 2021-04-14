import cv2 as cv
import numpy as np

src = cv.imread("japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    gray = cv.cvtColor(src, cv.COLOR_RGB2GRAY)
    ret,thresh_binary = cv.threshold(gray,127,255,cv.THRESH_BINARY)
    ret,thresh_binary_inv = cv.threshold(gray,127,255,cv.THRESH_BINARY_INV)
    ret,thresh_trunc = cv.threshold(gray,127,255,cv.THRESH_TRUNC)
    ret,thresh_tozero = cv.threshold(gray,127,255,cv.THRESH_TOZERO)
    ret,thresh_tozero_inv = cv.threshold(gray,127,255,cv.THRESH_TOZERO_INV)


#DISPLAYING THE DIFFERENT THRESHOLDING STYLES
names = ['Original Image','BINARY','THRESH_BINARY_INV','THRESH_TRUNC','THRESH_TOZERO','THRESH_TOZERO_INV']
images = gray,thresh_binary,thresh_binary_inv,thresh_trunc,thresh_tozero,thresh_tozero_inv


for i in range(6):
    cv.imshow(names[i],images[i])
    
    
    #cv.imshow("Oringal", gray)
                
                
                
