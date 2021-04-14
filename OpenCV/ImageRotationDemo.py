import cv2 as cv

src = cv.imread("japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    cv.imshow("windowM", src)
    rows,cols = src.shape[:2] 
#(col/2,rows/2) is the center of rotation for the image 
# M is the cordinates of the center 
    M = cv.getRotationMatrix2D((cols/2,rows/2),90,1) 
    dst = cv.warpAffine(src,M,(cols,rows)) 

    cv.imshow("windowMy", dst)
                
                
                
