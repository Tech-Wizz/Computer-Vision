import pickle
import numpy as np
import cv2 as cv

face_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_frontalface_default.xml') #overflow

recognizer = cv.face.LBPHFaceRecognizer_create()
recognizer.read("trainer.yml")
lables = {}

with open("labels.pickle", 'rb') as f:
    of_labels = pickle.load(f)
    labels = {v:k for k, v in of_labels.items()}

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
        id, conf = recognizer.predict(roi_gray)

        if conf >= 45 and conf <= 85:
            name = labels[id]
            print(name)
            c = round(conf, 2)
            cv.putText(img, (str(labels[id]) + ": " + str(c) + "%"), (x,y), cv.FONT_HERSHEY_SIMPLEX, 1, (0,0,0), 2, cv.LINE_AA)

    cv.imshow('Image', img)
    if cv.waitKey(1) & 0xFF == ord('q'):
        break

cv.destroyAllWindows()
cap.release()
