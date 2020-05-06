from nets.yolo3 import yolo_body
from keras.layers import Input
from yolo import YOLO
from PIL import Image
import numpy as np
from datetime import datetime



if __name__ == '__main__':
    yolo = YOLO()
    # x = 10
    # photo = []
    # with open('2007_test.txt') as f:
    #     file = f.readlines()
    #     # print(file[0])
    # for line in file:
    #     photo.append(line.split()[0])
    # np.random.seed(int(datetime.timestamp(datetime.now())))
    # np.random.shuffle(photo)
    # np.random.seed(None)
    # for i in range(x):
    if True:
        # img = input('Input image filename:')
        img ='E:/CMPE_master_project/photo/v1780.jpg'
        # print(photo[i])
        try:
            image = Image.open(img)
        except:
            print('Open Error! Try again!')
            # continue
        else:
            # [[type,[top,left,bottom,right],score]
            boxes = yolo.detect_image_boxes(image)
            print(boxes)
            r_image = yolo.detect_image(image)
            r_image.show()
    yolo.close_session()
