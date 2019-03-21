package FriendRec.RelationRec;

import java.lang.reflect.Array;
import java.util.Arrays;

import common.SimpleTool;
import FriendRec.Core.DataModel;
import FriendRec.Core.Distances;
import FriendRec.Core.SetOperator;

/**   
 * @ClassName:  FriendRecTop   
 * @Description: According to the friends/neighbors to get the recommend result.
 * @author: ziyin   
 */

public class FriendRecTop {
	public DataModel dataModel;
	public double recall;
	public double precision;
	public double fMeasure;
	public double NDCG;
	public double max;
	
	//The preference of the target item by friends/neighbors.
	double[] itemLikeRatio;
	int[] itemLike;


	public FriendRecTop() {
		try {
		
		} catch (Exception ee) {
			ee.printStackTrace();
		} // Of try
	}// Of the first constructor	
	
	/**
	 * Find my friends who buy the same item with me.
	 * @param paraUserIndex
	 * @param paraItemIndex
	 *                the same item
	 * @return  Find users to buy the items
	 */
	int[] findRatedUsers(int paraUserIndex, int paraItemIndex) {
		// Step 1. Find users to buy the items
		int[] tempUsers = dataModel.iTrRateInds[paraItemIndex];
		if (tempUsers == null || tempUsers.length == 0) {
			return null;
		} // Of if

		return tempUsers;
	}// Of findFriends
	
	/**
	 *  Get the distribution of the top k'th friends' rating, the friends are who buy the same item with the target user.
	 *  
	 * @param paraUserIndex
	 *                The target user.
	 * @param paraItemIndex
	 *                The target item.
	 * @param paraK
	 *                The number of neighbors from KNN.
	 * @return The distribution of the ratings.
	 */
	int[] predict(int paraUserIndex, int paraItemIndex, int paraK) {
		//The distribution of the ratings.
		int[] tempDistribution = new int[5];
		//Step 1. Find my friends
		int[] tempFriends = dataModel.uSecRels[paraUserIndex];
//		System.out.println("the friends is " + Arrays.toString(tempFriends));
		
		//Step 1.1 If the user has not friends, we can't predict the object.
		if(tempFriends == null || tempFriends.length == 0){
			tempDistribution = predictForNoneFriendUser(paraUserIndex, paraItemIndex, paraK);
			return tempDistribution;
		}//Of if
		
		//Step 2. Find users to buy the items
		int[] tempUsers = dataModel.iTrRateInds[paraItemIndex];
//		System.out.println("the tempUsers is " + Arrays.toString(tempUsers));
		
		//Step 3. Find my friends who rated the item.
		int[] tempIndicesOfFriendsRatedItem = SetOperator.interSection(tempFriends, tempUsers);
		
		//If the intersection is null, we can't predict the object.
		if(tempIndicesOfFriendsRatedItem == null){
			tempDistribution = predictForNoneFriendUser(paraUserIndex, paraItemIndex, paraK);
			return tempDistribution;
		}//Of if
		
		//Step 4. Compute the distribution of the predicting item
		for(int i = 0; i < tempIndicesOfFriendsRatedItem.length; i ++){
			int tempIndex = 
					dataModel.iTrRatings[paraItemIndex][tempIndicesOfFriendsRatedItem[i]];
			tempDistribution[tempIndex - 1] ++;
		}//Of for i
		
		return tempDistribution;
	}// Of predict

	/**
	 * Sorting the items' preference.
	 * 
	 * @param paraItemLikeRatio
	 *                The vector of items' preference
	 * @param paraItemLike
	 *                The vector of items
	 * @param paraN
	 *                The number of recommend item.
	 * @return The sorting of item's preference.
	 */
	int[] orderItemLike(double[] paraItemLikeRatio, int[] paraItemLike, int paraN) {		
		double tempRatio = -1.0;
		int tempItem = -1;
		for (int i = 0; i < paraItemLikeRatio.length - 1; i++) {
			for (int j = 0; j < paraItemLikeRatio.length - i - 1; j++) {
				if(paraItemLikeRatio[j] < paraItemLikeRatio[j + 1]) {
					tempRatio = paraItemLikeRatio[j];
					tempItem = paraItemLike[j];
					paraItemLikeRatio[j] = paraItemLikeRatio[j + 1];
					paraItemLike[j] = paraItemLike[j + 1];
					paraItemLikeRatio[j + 1] = tempRatio;
					paraItemLike[j + 1] = tempItem;
				}//Of if
			}//Of for j
		}//Of for i
		
//		System.out.println("the paraItemLikeRatio  is " + Arrays.toString(paraItemLikeRatio));
//		System.out.println("the paraItemLike  is " + Arrays.toString(paraItemLike));
//		Arrays.sort(paraItemLike);
//		System.out.println("the paraItemLike  is " + Arrays.toString(paraItemLike));
		
		 return paraItemLike;
	}//Of orderItemLike
	
	/**
	 * Get the distribution of the top k'th neighbors' rating, the neighbors from the KNN.
	 *  
	 * @param paraUserIndex
	 *                The target user.
	 * @param paraItemIndex
	 *                The target item.
	 * @param paraK
	 *                The number of neighbors from KNN.
	 * @return  The distribution of the neighbors' rating.
	 */
	int[] predictForNoneFriendUser(int paraUserIndex, int paraItemIndex, int paraK) {
			//Step 1. Find the position of my friends who rate the item.
			int[] tempMyFriends = findRatedUsers(paraUserIndex, paraItemIndex);
//			System.out.println("the rated users are " + Arrays.toString(tempMyFriends));
			
			if(tempMyFriends == null){
				return null;
			}//Of if
				
			//Step 2. kNN recommendation
			int[] tempNeigborIndies = new int[paraK + 2];
			double[] tempFriDistances = new double[paraK + 2];
			for(int i = 0; i < tempNeigborIndies.length; i ++){
				tempNeigborIndies[i] = -1;
				if(i == 0){
					tempFriDistances[i] = 10; //Flag
				}else{
					tempFriDistances[i] = -10;
				}//of if
			}//of for i
			
			//Step 2.1 Find k-nearest neighbors
			for(int i = 0; i < tempMyFriends.length; i ++){
				//Step 2.1.1 Obtain one possible neighbor.
				int tempOneNeighbor = tempMyFriends[i];
//				System.out.println(paraUserIndex + ":" + paraItemIndex +"; possible neighor = " + tempOneNeighbor);
				
				//Step 2.1.2 Compute the distance between my neighbor and me.
				double tempDistance = Distances.Jaccard(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.manhattanDistance(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.Euclidean(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.TMJ(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.Cosine(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.Triangle(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.PIP(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.NHSM(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.BC(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.pearson(paraUserIndex,tempOneNeighbor, dataModel);
//				double tempDistance = Distances.CPCC(paraUserIndex,tempOneNeighbor, dataModel);
//				System.out.println(paraUserIndex + ":" + tempOneNeighbor + "; distance = " + tempDistance);
				
				//Step 2.1.3 Insert sort (Sort from largest to smallest)
				for(int j = paraK; j >= 0; j --){
//					System.out.println("Distance: " + tempDistance + " : " + tempFriDistances[j]);
					if(tempDistance >= tempFriDistances[j]){
						tempFriDistances[j] = tempFriDistances[j - 1];
						tempNeigborIndies[j] = tempNeigborIndies[j - 1];
					}else{
//						System.out.println("position: " + j + "; neighbor = " + tempMyFriends[i]);
						tempFriDistances[j + 1] = tempDistance;
						tempNeigborIndies[j + 1] =i; //the indices of users who rate the item.
						break;
					}//Of if
				}//Of for j
			}//of for i
//			System.out.println("the K-friend array is " + Arrays.toString(tempNeigborIndies));
//			System.out.println("the K-friend disrance array is " + Arrays.toString(tempFriDistances));
			
			// Step 3. Compute the distribution of the predicting item
			int[] tempDistribution = new int[5];
			tempNeigborIndies[paraK + 1] = -1;
			for (int i = 0; i < tempNeigborIndies.length; i++) {
				if(tempNeigborIndies[i] != -1){
					int tempIndex = dataModel.iTrRatings[paraItemIndex][tempNeigborIndies[i]];
					tempDistribution[tempIndex - 1]++;
				}//Of if
			} // Of for i
	
			return tempDistribution;
		}// Of predict

	/**
	 * Get the target user's recommend list, and the list length is N.
	 * 
	 * @param paraUserIndex
	 *                The target user.
	 * @param paraRecThreshold
	 *                The recommend threshold.
	 * @param paraLikeThreshold
	 *                The preference threshold.
	 * @param paraK
	 *                The number of neighbors from KNN.
	 * @param paraN
	 *                The number of recommend item.
	 * @return The recommend list for the target user.
	 */
	int[] recommendListForOneUser(int paraUserIndex, double paraRecThreshold, double paraLikeThreshold, int paraK, int paraN){
//		System.out.println("the user is " + paraUserIndex + " , the length is " + dataModel.uTeRateInds[paraUserIndex].length);
		int[] tempRecLists = new int[dataModel.uTeRateInds[paraUserIndex].length];
		double[] itemLikeRatio = new double[dataModel.uTeRateInds[paraUserIndex].length];
//		int[] itemLike = new int[dataModel.uTeRateInds[paraUserIndex].length];
//		for (int i = 0; i < itemLike.length; i++) {
//			itemLike[i] = -1;
//		}
		Arrays.fill(tempRecLists, -1);
		Arrays.fill(itemLikeRatio, -1);
//		Arrays.fill(itemLike, -1);
		int tempCount = 0;
		//Step 1. Predict the distribution
		for(int i = 0; i < dataModel.uTeRateInds[paraUserIndex].length; i ++){
			int[] tempDistribution = predict(paraUserIndex, 
					dataModel.uTeRateInds[paraUserIndex][i], paraK);
			
//			System.out.println("the distribution is " + Arrays.toString(tempDistribution));
		
			if(tempDistribution == null){
				continue;
			}//Of if
			double tempLikePro = 0;
			int tempTotal = 0;
			for(int j = 0; j < tempDistribution.length; j ++){
				if(j >= paraLikeThreshold){
					tempLikePro += tempDistribution[j];
				}//Of if
				tempTotal += tempDistribution[j];
			}//Of for i
			
			if(tempTotal > 1e-6){
				tempLikePro = tempLikePro / tempTotal;
			}//Of if
			
			//Step 3. Obtain the recommend lists.

			if(tempLikePro >= paraRecThreshold){
				tempRecLists[tempCount] = dataModel.uTeRateInds[paraUserIndex][i];
				itemLikeRatio[tempCount] = tempLikePro;
//				itemLike[tempCount] = dataModel.uTeRateInds[paraUserIndex][i];
				tempCount ++;
			}//Of if
		}//Of for i
		
//		System.out.println("the reclist = " + Arrays.toString(tempRecLists));
//		System.out.println("the itemLikeRatio = " + Arrays.toString(itemLikeRatio));
//		System.out.println("the itemLike = " + Arrays.toString(itemLike));
		
		//Step 4. Compress
//		if(tempCount == 0) {
//			return null;
//		}
//		if(tempCount < paraN){
//			int[] result = new int[tempCount];
//			for(int i = 0; i < tempCount; i ++){
//				result[i] = tempRecLists[i];
//			}//Of for i
//			return result;
//			return null;
//		}//Of if
		
		int[] tempCompRecLists = new int[tempCount];
		double[] tempCompRecRatio = new double[tempCount];
		for(int i = 0; i < tempCount; i ++){
			tempCompRecLists[i] = tempRecLists[i];
			tempCompRecRatio[i] = itemLikeRatio[i];
		}//Of for i
//		System.out.println("the recommend list's length is " + tempCompRecLists.length);
		
		itemLike = orderItemLike(tempCompRecRatio, tempCompRecLists, tempCount);
//		System.out.println("the itemLike = " + Arrays.toString(itemLike));
		
			int[] result = new int[tempCount];
			for(int i = 0; i < tempCount; i ++){
				result[i] = itemLike[i];
			}//Of for i
			Arrays.sort(result);
			return result;
		
	}//Of recommendListForOneUser
	
	/**
	 * The target user's like list as an evaluation criterion.
	 * 
	 * @param paraUserIndex
	 *                The target user.
	 * @param paraLikeThreshold
	 *                The preference threshold.
	 * @return The like list from the target user.
	 */
	int[] likeListForOneUser(int paraUserIndex, int paraLikeThreshold){
		int[] tempLikeLists = new int[dataModel.uTeRateInds[paraUserIndex].length];
		int tempCount = 0;
		
		//Step 1. Obtain the like items.
		for(int i = 0; i < dataModel.uTeRateInds[paraUserIndex].length; i ++){
			if(dataModel.uTeRatings[paraUserIndex][i] > paraLikeThreshold){
				tempLikeLists[tempCount] = dataModel.uTeRateInds[paraUserIndex][i];
				tempCount ++;
			}//Of for i
		}//of for i
		
		//Step 2. Compress
		if(tempCount == 0){
			return null;
		}//Of if
		int[] tempCompLikeLists = new int[tempCount];
		for(int i = 0; i < tempCount; i ++){
			tempCompLikeLists[i] = tempLikeLists[i];
		}//Of for i
		
		Arrays.sort(tempCompLikeLists);
		
		return tempCompLikeLists;
	}//Of likeListForOneUser
	
	/**
	 * Compute the NDCG.
	 * 
	 * @param paraUser
	 *                The target user.
	 * @param paraRecLists
	 *                The recommend list for the target user.
	 * @param paraMax
	 *                The constant in NDCG.
	 * @return The NDCG.
	 */
	double computeNDCG(int paraUser, int[] paraRecLists, double paraMax) {
		double tempNDCG = 0;
//		double max = 0;
		double Z = 1;
		for (int i = 1; i < paraRecLists.length; i++) {
//			System.out.println("the rating is " + dataModel.uTeRatings[paraUser][i] + " : " + ((double)Math.log10(i + 1)));
			tempNDCG += ((double)Math.pow(2, dataModel.uTeRatings[paraUser][i]) - 1)/((double)Math.log10(i + 1));
		}
		tempNDCG = (1.0/paraMax)*tempNDCG;
		if(max < tempNDCG) {
			max = tempNDCG;
		}
//		System.out.println(tempNDCG);
		return tempNDCG;
	}//Of computeNDCG
	
	/**
	 * Compute the MAE.
	 * 
	 * @param paraK
	 *                 The number of neighbors from KNN.
	 * @return The MAE.
	 */
	double computeMAE(int paraK){
		double tempMae = 0;
		int tempTotalCount = 0;
		//Step 1. Predict the distribution
		for(int i = 0; i < dataModel.uTeRateInds.length; i ++){
			for(int j = 0; j < dataModel.uTeRateInds[i].length; j ++){
				int[] tempDistribution = predict(i, 
						dataModel.uTeRateInds[i][j], paraK);
				if(tempDistribution == null){
					continue;
				}//Of if
				
				//Step 2. The formula of MAE.
				double tempPrediction = 0;
				int tempCount = 0;
				for(int k = 0; k < tempDistribution.length; k ++){
					tempPrediction += tempDistribution[k] * (k + 1);
					tempCount += tempDistribution[k];
				}//Of for k
				tempPrediction /= tempCount;
				
				tempMae += Math.abs(tempPrediction - dataModel.uTeRatings[i][j]);
				tempTotalCount ++;
			}//of for 
		}//of for i
		
		if(tempTotalCount > 1e-6){
			tempMae /= tempTotalCount;
		}//of 
		
		return tempMae;
	}//Of computeMAE
	
	/**
	 * Compute the precision and recall.
	 * 
	 * @param paraRecThreshold
	 *                The recommend threshold.
	 * @param paraLikeThreshold
	 *                The preference threshold.
	 * @param paraK
	 *                The number of neighbors from KNN.
	 * @param paraN
	 *                The number of recommend items.
	 * @param paraMax
	 *                The constant in NDCG.
	 */
	void computePrecisionAndRecall(double paraRecThreshold, int paraLikeThreshold, int paraK , int paraN, double paraMax){
//		System.out.println("the uTrRateInds is: ");
//		SimpleTool.printMatrix(dataModel.uTrRateInds);
//		System.out.println("the uTrRatings is : ");
//		SimpleTool.printMatrix(dataModel.uTrRatings);
//		System.out.println("the iTrRateInds is: ");
//		SimpleTool.printMatrix(dataModel.iTrRateInds);
//		System.out.println("the iTrRatings is : ");
//		SimpleTool.printMatrix(dataModel.iTrRatings);
//		System.out.println("the uTeRateInds is : ");
//		SimpleTool.printMatrix(dataModel.uTeRateInds);
//		System.out.println(Arrays.toString(dataModel.uTeDgr));
		
		int tempSucRecLen = 0;
		int tempRecLen = 0;
		int tempLikeLen = 0;
		int tempCount = 0;
		double tempNDCG = 0;
		for(int i = 0; i < dataModel.userNum; i ++){
//			System.out.println("the user is " + i  + " , the length is " + dataModel.uTeRateInds[i].length);
			int[] tempRecLists = recommendListForOneUser(i, paraRecThreshold, paraLikeThreshold, paraK, paraN);
//			System.out.println("the reclist is " + Arrays.toString(tempRecLists));
			int[] tempLikeLists = likeListForOneUser(i, paraLikeThreshold);
//			System.out.println("the likelist is " + Arrays.toString(tempLikeLists));
			int[] tempInterSection = SetOperator.interSection(tempRecLists, tempLikeLists);
			
//			System.out.println("User " + i + " recommend lists: ");
//			SimpleTool.printIntArray(tempRecLists);
			if(tempInterSection != null){
				tempSucRecLen = tempInterSection.length;
				tempRecLen = tempRecLists.length;
				tempLikeLen = tempLikeLists.length;
				if(tempRecLen > 1e-6){
					precision += (tempSucRecLen + 0.0)/ tempRecLen;
					tempNDCG = computeNDCG(i, tempRecLists, paraMax);
					NDCG += tempNDCG;
				}//Of if
				if(tempLikeLen > 1e-6){
					recall += (tempSucRecLen + 0.0) / tempLikeLen;
				}//Of if
			}else{
				tempCount ++;
				continue;
			}//Of if
		}//Of for i
		
//		System.out.println("the count is: " + tempCount);
		precision = precision/(dataModel.userNum - tempCount);
		recall =  recall/(dataModel.userNum - tempCount);
		NDCG = NDCG/(dataModel.userNum - tempCount);
	}//Of computePrecisionAndRecall
	
	/**
	 * Compute the FMeasure.
	 * 
	 * @param paraRecThreshold
	 *                The recommend threshold.
	 * @param paraLikeThreshold
	 *                The preference threshold.
	 * @return The FMeasure.
	 */
	double computeFMeasure(){		
		fMeasure = (2 * recall * precision) / (recall + precision);
		return fMeasure;
	}//Of computeFMeasure
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// System.out.println("Main test 1");
			FriendRecTop tempFri = new FriendRecTop();
			
			//Group 1 Experiments
//			String tempTrain = "data/10.base";
//			String tempTest = "data/10.test";
//			String tempRelation = "data/trust_data2.txt";
//			tempFri.dataModel = new DataModel(49290, 139738);
			
			//Group 2 Experiments
//			String tempTrain = "data/CiaoDVD/u1.base";
//			String tempTest = "data/CiaoDVD/u1.test";
//			String tempRelation = "data/CiaoDVD/trusts.txt";
//			tempFri.dataModel = new DataModel(30444,16121);
			
			//Group 3 Experiments
			String tempTrain = "data/filmtrust/10.base";
			String tempTest = "data/filmtrust/10.test";
			String tempRelation = "data/filmtrust/trust.txt";
			tempFri.dataModel = new DataModel(1642,2071);//49290, 139738 4, 5 30444,16121 1642,2071
			
			tempFri.dataModel.getUserTrainSet(tempTrain);
			tempFri.dataModel.getItemTrainSet(tempTrain);
			tempFri.dataModel.getUserTestSet(tempTest);
			tempFri.dataModel.getUserRelations(tempRelation);
			tempFri.dataModel.getuSecHandRels1();	
//			tempFri.dataModel.getuSecHandRels1_2();	
			
//			Is the trust matrix symmetric ? no!
//			System.out.println(Arrays.toString(tempFri.dataModel.uRelations[1]));
//			System.out.println(Arrays.toString(tempFri.dataModel.uRelations[59]));
//			System.out.println(Arrays.toString(tempFri.dataModel.uRelations[86]));
//			System.out.println(Arrays.toString(tempFri.dataModel.uRelations[393]));
			
			int tempK = 10;//the neighbors
			int tempN = 5;//@k
			tempFri.max = 1.0;//flag
			double tempRecThreshold = 0.5;
			int tempLikeThreshold = 3;
//			for (int tempK = 10; tempK < 100; ) {
//			for (tempRecThreshold = 0.1; tempRecThreshold < 1; ) {
//				System.out.println(tempRecThreshold);
			
//			System.out.println("max = " + tempFri.max);
			tempFri.computePrecisionAndRecall(tempRecThreshold, tempLikeThreshold, tempK, tempN, tempFri.max);
			tempFri.computeFMeasure();
//			System.out.println("Precision = " + tempFri.precision);
//			System.out.println("recall = " + tempFri.recall);
//			System.out.println("FMeasure = " + tempFri.fMeasure);
//			System.out.println("NDCG = " + tempFri.NDCG);
//			System.out.println("max = " + tempFri.max);
			
			tempFri.precision = 0;
			tempFri.recall = 0;
			tempFri.fMeasure = 0;
			tempFri.NDCG = 0;
			
			tempFri.computePrecisionAndRecall(tempRecThreshold, tempLikeThreshold, tempK, tempN, tempFri.max);
			tempFri.computeFMeasure();
			System.out.println("Precision = " + tempFri.precision);
			System.out.println("recall = " + tempFri.recall);
			System.out.println("FMeasure = " + tempFri.fMeasure);
			System.out.println("NDCG = " + tempFri.NDCG);
//			System.out.println("max = " + tempFri.max);
			
			double tempMae = tempFri.computeMAE(tempK);
			System.out.println("MAE = " + tempMae);
//			tempK += 10;
//			System.out.println(tempK);
//			tempRecThreshold += 0.1;
//			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}// of try
	}//Of main
}// Of class FriendRecTop
