import cv2 as cv

src = cv.imread("japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    cv.imshow("windowM", src)
    #get original size of Numpyd
    print(src.shape)
    #converting image to size (100,100,3) 
    smaller_image = cv.resize(src,(100,100),interpolation=1)
    cv.imshow("windowMy", smaller_image )
    ##Always best to keep the same x,y scale for the resolution
    proper_smaller_image = cv.resize(src,(212,100),interpolation=1) 
    cv.imshow("window3", proper_smaller_image )
                
                
                
