import pickle
import numpy as np
import cv2 as cv

def runner():

    face_cascade = cv.CascadeClassifier(cv.data.haarcascades + 'haarcascade_frontalface_default.xml') #overflow

    recognizer = cv.face.LBPHFaceRecognizer_create()
    recognizer.read("trainer.yml")
    lables = {}

    with open("labels.pickle", 'rb') as f:
        of_labels = pickle.load(f)
        labels = {v:k for k, v in of_labels.items()}

    cv.namedWindow("Image")

    img = cv.imread("threeHeads.jpg", 1)
    #img = cv.imread("six.jpg", 1)  #larry
    #img = cv.imread("ten.jpg", 1) #michael
    #img = cv.imread("eleven.jpg", 1) #magic
    if img is None:
        print("missing image")
    else:
        gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
        faces = face_cascade.detectMultiScale(gray, 1.3, 5)

        for (x,y,w,h) in faces:
            cv.rectangle(img, (x,y), (x+w, y+h), (255,0,0), 2)
            roi_gray = gray[y:y+h, x:x+w]
            roi_color = img[y:y+h, x:x+w]
            id, conf = recognizer.predict(roi_gray)

            if conf >= 20 and conf <= 100:
                name = labels[id]
                c = round(conf, 2)
                print(name + " " + str(c))
                #cv.putText(img, (str(labels[id]) + ": " + str(c) + "%"), (x,y), cv.FONT_HERSHEY_SIMPLEX, 1, (255,255,255), 2, cv.LINE_AA)
                cv.putText(img, (str(labels[id])), (x,y), cv.FONT_HERSHEY_SIMPLEX, 1, (255,255,255), 2, cv.LINE_AA)

        cv.imshow('Image', img)

runner()
