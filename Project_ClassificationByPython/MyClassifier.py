import sys
import numpy as np
import operator
import math

def load_data(file_name,type):
	if type == 'train':
		X = []
		y = []
		with open(file_name,'r') as fr:
			for line in fr:
				data = line.split(',')
				tmp_X = [float(x) for x in data[0:-1]]
				X.append(tmp_X)
				if data[len(data)-1].strip() == 'yes':
					tmp_y = 1
				else:
					tmp_y = -1
				y.append(tmp_y)
		return X,y
	else:
		X = []
		with open(file_name,'r') as fr:
			for line in fr:
				data = line.split(',')
				tmp_X = [float(x) for x in data]
				X.append(tmp_X)
		return X
class NaiveBayes():
	def __init__(self):
		self.label_name={-1:'no',1:'yes'}

	def seperate_each_label(self,X,y):
		'''
		X: feature
		y: label
		Return: A dict , class name as key , a list of features as value
		'''
		data_each_label = {}
		for i in range(len(y)):
			if y[i] not in data_each_label:
				data_each_label[y[i]] = [X[i]]
			else:
				data_each_label[y[i]].append(X[i])
		return data_each_label

	def statistic_info(self,data_each_label):
		'''
		data_each_label: the data seperated by label
		Return: a dict, mean and standard derivation of each class
		'''
		mean = {}
		stdev  ={}

		for key in data_each_label:
			avg = np.sum(np.array(data_each_label[key]),0)/float(len(data_each_label[key]))
			variance = np.sum(np.array([pow(x-avg,2) for x in data_each_label[key]]),0)/float(len(data_each_label[key])-1)
			mean[key] = avg
			stdev[key] = np.sqrt(variance)
#		print(mean)
#		print(stdev)
		return mean, stdev
	def fit(self,X,y):
		'''
		training
		'''
		# seperate data base on label
		data_each_label = self.seperate_each_label(X,y)
		# get the mean and the standard deviation of the data seperated by label
		self.mean,self.stdev = self.statistic_info(data_each_label)

	def calculate_probability(self,x, mean, stdev):
		'''
		calculate prob by gaussian distribution
		'''
		exponent = math.exp(-(math.pow(x-mean,2)/(2.0*math.pow(stdev,2)+1e-6)))

		return (1.0 / (math.sqrt(2*math.pi) * stdev+1e-6)) * exponent

	def predict_prob(self,X):
		'''
		calculate predict probably of each class
		Return: [ {'label1': prob,'label2': prob,} , {'label1': prob,'label2': prob,}]
		'''
		prob_result = []
		for j in range(len(X)):
			probabilities = {}
			for label in self.mean:
				probabilities[label] = 1
				for i in range(len(self.mean[label])):
					x = X[j][i]
					probabilities[label] *= self.calculate_probability(x, self.mean[label][i], self.stdev[label][i])
			prob_result.append(probabilities)
		return prob_result
	def predict(self,X):
		'''
		get predict result
		Return: predict result like ['yes','no',...]
		'''
		prob_result = []
		for j in range(len(X)):
			probabilities = {}
			for label in self.mean:
				probabilities[label] = 1
				for i in range(len(self.mean[label])):
					x = X[j][i]
					probabilities[label] *= self.calculate_probability(x, self.mean[label][i], self.stdev[label][i])
			prob_result.append(probabilities)

		result = []

		for i in range(len(prob_result)):
			best_prob=0
			best_label = 1
			for label in prob_result[i]:
				if best_prob<prob_result[i][label]:
					best_prob = prob_result[i][label]
					best_label = label
			result.append(self.label_name[best_label])

		return result


class KNN():
	def euclideanDist(inst1,inst2,length):
		'''
		calculate the euclidean distance between 2 instance
		length: number of attributes
		'''
		distance = 0
		for x in range(length):
			distance += pow((inst1[x]-inst2[x]),2)
			euclidean_distance = math.sqrt(distance)
		return  euclidean_distance



	def getNeighbors(trainingSet, testInstance, k):
		'''
		Use the testInstance to compare with all training instance
		find k neighbors
		'''
		distanceSet = []
		length = len(testInstance)
		for y in range(len(trainingSet[0])):
			distance = KNN.euclideanDist(testInstance, trainingSet[0][y], length)
			distanceSet.append((trainingSet[0][y], distance))
		distanceSet.sort(key=operator.itemgetter(1))
		neighbors = []
		for x in range(k):
			neighbors.append(distanceSet[x][0])
		return neighbors

	def getNeighborsClass(neighbors,trainingSet):
		'''
		Find the class value for each neighbor
		'''
		neighborsClass = []
		for x in neighbors:
			for y in range(len(trainingSet[0])):
				if x == trainingSet[0][y]:
					neighborsClass.append(trainingSet[1][y])
		return neighborsClass

	def getPred(result):
		'''
		Predict the possible class for test instance by voting
		Number of Yes >= No then the prediction will be Yes
		Otherwise, the prediction will be No
		'''
		countY = 0
		countN = 0
		for x in result:
			if x == 1:
				countY += 1
			elif x == -1:
				countN += 1
		if countY >= countN:
			return 'Yes'
		else:
			return 'No'

if __name__ == "__main__":
	train_file_name = sys.argv[1]
	test_file_name = sys.argv[2]
	algorithm = sys.argv[3]

	#test_acc = sys.argv[4]
	#read data from file
	train_X,train_y = load_data(train_file_name,'train')
	test_X= load_data(test_file_name,'test')

	#test_X,test_y = load_data(test_acc, 'train')

	if algorithm == 'NB':
		# guassian naive bayes
		nb = NaiveBayes()
		#train
		nb.fit(train_X,train_y)
		#calculate predict probably of each class
		predict_prob = nb.predict_prob(test_X)
		#get predict result
		predict = nb.predict(test_X)

#		corr = 0
#		cnt = 0
		for result in predict:
			print(result)
#			if result == 'no' and test_y[cnt]==-1:
#				corr += 1
#			if result == 'yes' and test_y[cnt] == 1:
#				corr += 1
#			cnt+=1
		#print(1.0*corr/len(predict))

	else:

		k = int(algorithm.strip('NN'))
		trainingSet = load_data(train_file_name,'train')
		testSet = load_data(test_file_name,'test')
		for x in range(len(testSet)):
			print(KNN.getPred(KNN.getNeighborsClass(KNN.getNeighbors(trainingSet,testSet[x],k),trainingSet)))
