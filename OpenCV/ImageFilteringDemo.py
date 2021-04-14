import cv2 as cv
import numpy as np

src = cv.imread("japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    averaging_kernel = np.ones((3,3),np.float32)/9 
    filtered_image = cv.filter2D(src,-1,averaging_kernel) 
    #.imshow(dst) 
    #get a one dimensional Gaussian Kernel 
    gaussian_kernel_x = cv.getGaussianKernel(5,1) 
    gaussian_kernel_y = cv.getGaussianKernel(5,1) 
	#converting to two dimensional kernel using matrix multiplication 
    gaussian_kernel = gaussian_kernel_x * gaussian_kernel_y.T 
	#you can also use cv2.GaussianBLurring(image,(shape of kernel),standard deviation) instead of cv2.filter2D 
    filtered_image = cv.filter2D(src,-1,gaussian_kernel) 
    

    cv.imshow("Original", src)
    cv.imshow("adaptive", filtered_image)            
                
                
