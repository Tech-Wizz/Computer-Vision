import numpy as np
import cv2 as cv

face_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_frontalface_default.xml') #overflow
eye_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_eye.xml')
smile_cascade = cv.CascadeClassifier(cv.haarcascades + 'haarcascade_smile.xml')

cv.namedWindow("Image")
cap = cv.VideoCapture()
cap.open(0, cv.CAP_DSHOW)

while True:
    status, img = cap.read()
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x,y,w,h) in faces:
        cv.rectangle(img, (x,y), (x+w, y+h), (255,0,0), 2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = img[y:y+h, x:x+w]
        smile = smile_cascade.detectMultiScale(roi_gray, 1.5, 5)
        eyes = eye_cascade.detectMultiScale(roi_gray, 1.5, 5)

        for (ex,ey,ew,eh) in smile:
            print(ew)
            if ew > 175:
                cv.rectangle(roi_color,(ex,ey),(ex+ew+10,ey+eh),(0,255,0),2)

        for (ex,ey,ew,eh) in eyes:
            if ew > 55:
                cv.rectangle(roi_color,(ex,ey),(ex+ew+10,ey+eh),(0,255,0),2)

    cv.imshow('Image', img)
    if cv.waitKey(1) & 0xFF == ord('q'):
        break

cv.destroyAllWindows()
cap.release()
