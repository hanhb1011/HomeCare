from firebase import firebase
import pandas
#############################################
# FirebaseDatabase.py
# 오픈소스 라이브러리인 python-firebase를 이용함
# 유저 데이터를 불러온 뒤 적절하게 파싱하여 제공
#############################################


#firebase instance 생성
firebase = firebase.FirebaseApplication("https://homecareproject-15094.firebaseio.com/")
users = firebase.get("/user", None)
dataframe = pandas.DataFrame(list(users.items()))
inputData = pandas.DataFrame(list(dataframe[1])).loc[:, ['exceededPayments', 'homecareCount', 'star', 'suspensions']].as_matrix() #예측에 쓰일 유저데이터
uids = list(dataframe[0])

def refresh() :
    users = firebase.get("/user", None)
    dataframe = pandas.DataFrame(list(users.items()))
    inputData = pandas.DataFrame(list(dataframe[1])).loc[:, ['exceededPayments', 'homecareCount', 'star', 'suspensions']].as_matrix() #예측에 쓰일 유저데이터
    uids = list(dataframe[0])
    printUsers()


def printUsers() :
    print(pandas.DataFrame(list(dataframe[1])).loc[:, ['uid', 'exceededPayments', 'homecareCount', 'star', 'suspensions']])


#비정상 유저 리스트를 받아서 데이터베이스에 업데이트시킴
def writeAbnormalUsers(uids) :
    for uid in uids :
        firebase.put('user/'+uid,'type0',0)
        firebase.put('user/'+uid,'type1',1)

