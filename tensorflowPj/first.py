import tensorflow
import pandas
import numpy
import matplotlib.pyplot


#데이터 불러오기
dataframe = pandas.read_csv("dataset.csv")
dataframe = dataframe.drop(['uid'],axis=1)


#tensor 정의를 하기 위해 입력 데이터 정의
inputX = dataframe.loc[:, ['exceededPayments', 'homecareCount', 'star', 'suspensions']].as_matrix()
inputY = dataframe.loc[:, ['type0', 'type1']].as_matrix() #userType이 0에 가까울 수록 일반 유저, 1에 가까울 수록 비정상 유저
# -> 따라서
print(inputX)
print(inputY)

#파라미터 정의
learningRate = 0.000001 #데이터셋 크기가 작기 때문에, 정확도 향상을 위해 낮게 설정
iteration = 3000 #세션 반복 횟수 역시 데이터셋 크기가 작으므로 크게 설정
numOfSamples = inputX.size

#Tensorflow를 통한 Neural Network 구성
#input x, weights, bias
x =  tensorflow.placeholder(tensorflow.float32)
w = tensorflow.Variable(tensorflow.zeros([4,2])) #크기가 4*2인 가중치 matrix
b = tensorflow.Variable(tensorflow.zeros([2]))
#input y
y_values = tensorflow.add(tensorflow.matmul(x,w),b) #y값은 x inputs 와 weights w를 곱하고, bias b를 더한 것
y = tensorflow.nn.softmax(y_values)
y_ = tensorflow.placeholder(tensorflow.float32) #labels matrix

#cost -> GD
cost = tensorflow.reduce_sum(tensorflow.pow(y_ - y, 2))/(2*numOfSamples) #squared error
optimizer = tensorflow.train.GradientDescentOptimizer(learningRate).minimize(cost)

#Tensorflow 세션을 정의하고 트레이닝 시작
init = tensorflow.initialize_all_variables()
session = tensorflow.Session()
session.run(init)

for i in range(iteration): #train loop를 iteration만큼 돔
    session.run(optimizer,feed_dict={x:inputX, y_:inputY})
    if i%100 == 0:
        print ("cost : ", "{:.9f}".format(session.run(cost, feed_dict={x: inputX, y_: inputY})))

#output
#0 비정상
#1 정상
print(session.run(y, feed_dict= {x: inputX}))

prediction = tensorflow.arg_max(y, 1)
target = tensorflow.arg_max(y_, 1)

pre = session.run(prediction, feed_dict={x:inputX})
tar =  session.run(target, feed_dict={y_:inputY})

print("예측 : ", pre)
print("실제 : ", tar)

is_correct = tensorflow.equal(prediction, target)
accuracy = tensorflow.reduce_mean(tensorflow.cast(is_correct, tensorflow.float32))
print("정확도 : ", session.run(accuracy * 100, feed_dict={x: inputX, y_: inputY}),"%")

#마지막으로 비정상 유저의 uid를 찾아내어 출력한다.
