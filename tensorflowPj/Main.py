import TFProjects.HomeCare.UserClassifier as classifier
from tkinter import *

#############################################
# Main.py
# I. 사전 데이터셋을 학습시킨다. 'dataset3.csv'
# II. 파이어베이스에서 유저 데이터를 불러온다
# III. 유저 데이터를 데이터프레임으로 파싱한다.
# IV. 예측 후 예측 값을 디비에 갱신한다
#############################################
classifier.printTrainingResult()

window = Tk()
window.title("HomeCare")

lblTitle = Label(window, text=" HomeCare User Classifier ")

btnPredict = Button(window, text="유저 분류하기",command=classifier.predict)
btnRefresh = Button(window, text="유저 다시 받아오기", command = classifier.refresh)
btnAutoRun = Button(window, text="Auto Run (주기 : 1 hour)", command = classifier.autorun)

lblTitle.pack(pady=10, padx=30)
btnPredict.pack(pady=10, padx=30)
btnRefresh.pack(pady=10, padx=30)
btnAutoRun.pack(pady=10, padx=30)

window.mainloop()