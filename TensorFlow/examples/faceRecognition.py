import numpy as np
import cv2 as cv

face_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_frontalface_default.xml') #overflow
eye_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_eye.xml')

cv.namedWindow("image")

#src = cv.imread("ogTheOG.jpg", 1)
src = cv.imread("ogCSVisionTester.jpeg", 1)

if src is None:
    print("missing image")
else:
    gray = cv.cvtColor(src, cv.COLOR_RGB2GRAY)    

    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    print("faces ")
    print(faces)
    for (x,y,w,h) in faces:
        cv.rectangle(src, (x,y), (x+w, y+h), (255,0,0), 2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = src[y:y+h, x:x+w]
        eyes = eye_cascade.detectMultiScale(roi_gray, 1.3, 5)
    
        for (ex,ey,ew,eh) in eyes:
            cv.rectangle(roi_color,(ex,ey),(ex+ew+10,ey+eh),(0,255,0),2)
            

    print("eyes ")
    print(eyes)

    cv.imshow('image', src)
    cv.waitKey(0)
    cv.destroyAllWindows()
