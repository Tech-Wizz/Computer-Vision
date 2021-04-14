import cv2 as cv, time
import numpy as np


cv.namedWindow("Walker_Ward")

#Level  1
src = cv.imread("mugshot.jpg", 1)
if src is None:
    print("missing image")
else:
    dims = src.shape
    h = src.shape[0]
    w = src.shape[1]
    c = src.shape[2]
    srcBkp = src.copy()
    #src = cv.cvtColor(src, cv.COLOR_RGB2GRAY)    
    #cv.imshow("Walker_Ward", src)
    

    #Level  2
    thirty = np.full((h, w, c), 30, np.uint8)
    
    img = src + thirty

    #Level  3
    x = 50
    y = h - 75
    cv.putText(src, ("CSCI 442 Walker Ward"), (x,y), cv.FONT_HERSHEY_SIMPLEX, 1, (255,0,0), 2, cv.LINE_AA)

    #Level 4
    face_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_frontalface_default.xml') #overflow
    eye_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_eye.xml')

    gray = cv.cvtColor(src, cv.COLOR_RGB2GRAY)    
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x,y,w,h) in faces:
        cv.rectangle(src, (x,y), (x+w, y+h), (255,0,0), 2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = src[y:y+h, x:x+w]
        eyes = eye_cascade.detectMultiScale(roi_gray, 1.3, 5)
    
        for (ex,ey,ew,eh) in eyes:
            cv.rectangle(roi_color,(ex,ey),(ex+ew+10,ey+eh),(0,255,0),2)

    #Level 5
    gray = cv.cvtColor(src, cv.COLOR_RGB2GRAY)    
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x,y,w,h) in faces:
        cv.rectangle(src, (x,y), (x+w, y+h), (255,0,0), 2)
        eyes = eye_cascade.detectMultiScale(gray, 1.3, 5)
    
        for (ex,ey,ew,eh) in eyes:
            cv.rectangle(src,(ex,ey),(ex+ew+10,ey+eh),(0,255,0),2)

    #Level 6
    left = cv.imread("lefteye.png")
    right = cv.imread("righteye.png")

    face_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_frontalface_default.xml') #overflow
    eye_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_eye.xml')

    gray = cv.cvtColor(srcBkp, cv.COLOR_RGB2GRAY)    
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x,y,w,h) in faces:
        cv.rectangle(srcBkp, (x,y), (x+w, y+h), (255,0,0), 2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = srcBkp[y:y+h, x:x+w]
        eyes = eye_cascade.detectMultiScale(roi_gray, 1.3, 5)
    
        for (ex,ey,ew,eh) in eyes:
            offset = 20
            if(ex < (w/2)):
                for row in range(90):
                    for col in range(90):
                        srcBkp[row+y+ey-offset, col+x+ex-offset] = left[row,col]
            elif(ex > (w/2)):
                for row in range(90):
                    for col in range(90):
                        srcBkp[row+y+ey-offset, col+x+ex-offset] = right[row,col]
                    
            ew = 90
            eh = 90
            offset = 20
            cv.rectangle(roi_color,(ex-offset,ey-offset),(ex+ew-offset,ey+eh-offset),(0,255,0),2)

    

    cv.imshow("Walker_Ward", src)
    
    cv.imshow("Walker_Ward", srcBkp)

    
