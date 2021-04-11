import csv
import sys
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


def euclideanDist(inst1,inst2,length):
	distance = 0
	for x in range(length):
		distance += pow((inst1[x]-inst2[x]),2)
	euclidean_distance = math.sqrt(distance)
	return  euclidean_distance



def getNeighbors(trainingSet, testInstance, k):
	distanceSet = []
	length = len(testInstance)
	for y in range(len(trainingSet[0])):
		distance = euclideanDist(testInstance, trainingSet[0][y], length)

		distanceSet.append((trainingSet[0][y], distance))

	distanceSet.sort(key=operator.itemgetter(1))
	neighbors = []
	for x in range(k):
		neighbors.append(distanceSet[x][0])
	return neighbors

def getNeighborsClass(neighbors,trainingSet):
	neighborsClass = []
	for x in neighbors:
		for y in range(len(trainingSet[0])):
			if x == trainingSet[0][y]:
				neighborsClass.append(trainingSet[1][y])
	return neighborsClass
def getPred(result):
	countY = 0
	countN = 0
	for x in result:
		if x == 1:
			countY += 1
		elif x == -1:
			countN += 1
	if countY > countN:
		return 'Yes'
	else:
		return 'No'
def main():
	train_file_name = sys.argv[1]
	test_file_name = sys.argv[2]
	algorithm = sys.argv[3]
	if algorithm == 'NB':
		print('NB')
	elif algorithm == 'KNN':
		k = 3
		trainingSet = load_data(train_file_name,'train')
		testSet = load_data(test_file_name,'test')
		for x in range(len(testSet)):
			print(getPred(getNeighborsClass(getNeighbors(trainingSet,testSet[x],k),trainingSet)))


	else:
		return


main()
