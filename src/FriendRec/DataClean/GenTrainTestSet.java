package FriendRec.DataClean;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import common.SimpleTool;

/**
 **********************
 * @ClassName:  GenTrainTestSet   
 * @Description: Get the data for the algorithm.
 * @author: zhangzy
 * @date:   2017年12月14日  
 **********************   
 */
public class GenTrainTestSet {
	final int userNum = 49290;
	final int itemNum = 139738;
	
	/**
	 * 
	 */
	int[][] userRatings;
	int[][] userRateIndices;
	int[] userDegree;
	double[] userTotRatings;
	double[] userAveRatings;
	int[][] itemRatings;
	int[][] itemRateIndices;
	int[] itemDegree;
	double[] itemTotRatings;
	double[] itemAveRatings;

	/**
	 * 
	 */
	int[][] userRelations;

	/**
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public GenTrainTestSet() throws Exception {
		String tempFileName = "E:/data/douban/movie/newRatings.txt";
		buildRatingModel(tempFileName);
	}// Of the first constructor

	/**
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public void buildRatingModel(String paraFileName) throws Exception{
		File tempFile = null;
		String tempString = null;
		String[] tempStrArray = null;

		// Compute values of arrays
		tempFile = new File(paraFileName);
		if (!tempFile.exists()) {
			System.out.println("File is not exist!");
			return;
		}// Of if

		RandomAccessFile tempRanFile = new RandomAccessFile(tempFile, "r");
		// ���ļ�����ʼλ��
		int tempBeginIndex = 0;
		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);

		// Step 1. count the item degree
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		int tempRating = 0;
		
		userDegree = new int[userNum];
		userRatings = new int[userNum][];
		userRateIndices = new int[userNum][];
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]); 
			tempRating = Integer.parseInt(tempStrArray[2]);
			
			userDegree[tempUserIndex] ++;
		}// Of while
		
		for(int i = 0; i < userDegree.length; i ++){
			userRatings[i] = new int[userDegree[i]];
			userRateIndices[i] = new int[userDegree[i]];
			userDegree[i] = 0;
		}//Of for i
		
		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]); 
			tempRating = Integer.parseInt(tempStrArray[2]);
			
			userRatings[tempUserIndex][userDegree[tempUserIndex]] = tempRating;
			userRateIndices[tempUserIndex][userDegree[tempUserIndex]] = tempItemIndex;
			userDegree[tempUserIndex] ++;
		}// Of while
		tempRanFile.close();
	}// Of buildRatingModel
	
	/**
	 * Generate training and testing set
	 */
	void genTrainTestSet(double paraProb, String paraTrain, String paraTest) throws Exception{
		PrintWriter fwr = new PrintWriter(new File(paraTrain));
		PrintWriter fwt = new PrintWriter(new File(paraTest));
		
		for(int i = 0; i < userDegree.length; i ++){
			int[] tempRnds = SimpleTool.generateRandomSequence(userDegree[i]);
			
			int tempTrainLen = (int)(userDegree[i] * paraProb);
			for(int j = 0; j < tempTrainLen; j ++){
				String tempStr = i + ","+ userRateIndices[i][tempRnds[j]] 
						+ "," + userRatings[i][tempRnds[j]] + "\r\n";
				fwr.write(tempStr);
				fwr.flush();
			}//Of for j
			
			for(int j = tempTrainLen; j < userDegree[i]; j ++){
				String tempStr = i + ","+ userRateIndices[i][tempRnds[j]] 
						+ "," + userRatings[i][tempRnds[j]] + "\r\n";
				fwt.write(tempStr);
				fwt.flush();
			}//Of for j
		}//Of for i
		
		fwr.close();
		fwt.close();
	}//Of genTrainTestSet
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GenTrainTestSet tempSoc = new GenTrainTestSet();
			String paraTrain = "E:/data/douban/movie/u0.base";
			String paraTest = "E:/data/douban/movie/u0.test";
			tempSoc.genTrainTestSet(0.8, paraTrain, paraTest);
		} catch (Exception ee) {
			ee.printStackTrace();
		} // Of try

	}// Of main
}// Of Class SocialDataModel
