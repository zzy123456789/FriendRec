package FriendRec.Core;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;

import common.SimpleTool;

/**
 **********************
 * @ClassName:  DataModel   
 * @Description: Design a data model to store as some matrix.
 * @author ziyin
 **********************
 */
public class DataModel {
	public int userNum;
	public int itemNum;

	/**
	 * User training set
	 */
	public int[][] uTrRatings; // The rating matrix for the user training
	public int[][] uTrRateInds;// The rating indices for the user training
	public int[] uTrDgr; // The degrees for the user training
	public double[] uTrTotRatings;// The total ratings for the user training
	public double[] uTrAveRatings;// The average ratings for the user training

	/**
	 * Item training set
	 */
	public int[][] iTrRatings;// The rating matrix for the item training
	public int[][] iTrRateInds;// The rating indices for the item training
	public int[] iTrDgr; // The degrees for the item training
	double[] iTrTotRatings;// The total ratings for the item training
	double[] iTrAveRatings;// The average ratings for the item training

	public double[] standarddeviation;// The standard deviation for the item training
	public double[][] everyItemClassfication;

	/**
	 * User testing set
	 */
	public int[][] uTeRatings;// The rating matrix for the user testing
	public int[][] uTeRateInds;// The rating indices for the user testing
	public int[] uTeDgr; // The degrees for the user testing

	/**
	 * User-user relations
	 */
	public int[][] uRelations; // The user-user relation matrix
	public int[] uFriends; // The number of user friends
	public int[] uLeaders; // The leader for circle of user's friends.
	public int[][] uOrderFriends; // The order of the friends by friends' number.
	public int[][] uSecRels; // The user-user second-hand relation matrix
	public int[] uSecFriends; // The number of user-user second-hand friends
	public int[][] uThrRels; // The user-user second-hand relation matrix
	public int[] uThrFriends; // The number of user-user second-hand friends

	/**
	 * Set the user and item number.
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public DataModel(int paraUserNum, int paraItemNum) throws Exception {
		userNum = paraUserNum; // 49290;
		itemNum = paraItemNum; // 139738;
	}// Of the first constructor

	/**
	 * Read the data from the file, and get the user's train matrix.
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public void getUserTrainSet(String paraFileName) throws Exception {
		File tempFile = null;
		String tempString = null;
		String[] tempStrArray = null;

		// Compute values of arrays
		tempFile = new File(paraFileName);
		if (!tempFile.exists()) {
			System.out.println("File is not exist!");
			return;
		} // Of if

		RandomAccessFile tempRanFile = new RandomAccessFile(tempFile, "r");
		// ���ļ�����ʼλ��
		int tempBeginIndex = 0;
		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);

		// Step 1. count the item degree
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		int tempRating = 0;

		uTrDgr = new int[userNum];
		uTrRatings = new int[userNum][];
		uTrRateInds = new int[userNum][];
		uTrTotRatings = new double[userNum];
		uTrAveRatings = new double[userNum];
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);

			uTrTotRatings[tempUserIndex] += tempRating;
			uTrDgr[tempUserIndex]++;
		} // Of while

		for (int i = 0; i < uTrDgr.length; i++) {
			uTrRatings[i] = new int[uTrDgr[i]];
			uTrRateInds[i] = new int[uTrDgr[i]];

			if (uTrDgr[i] > 1e-6) {
				uTrAveRatings[i] = uTrTotRatings[i] / uTrDgr[i];
			} // Of if

			uTrDgr[i] = 0;
		} // Of for i

		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);

			uTrRatings[tempUserIndex][uTrDgr[tempUserIndex]] = tempRating;
			uTrRateInds[tempUserIndex][uTrDgr[tempUserIndex]] = tempItemIndex;
			uTrDgr[tempUserIndex]++;
		} // Of while

		tempRanFile.close();
	}// Of getUserTrainSet

	/**
	 * Read the data from the file, and get the item's train matrix.
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public void getItemTrainSet(String paraFileName) throws Exception {
		File tempFile = null;
		String tempString = null;
		String[] tempStrArray = null;

		// Compute values of arrays
		tempFile = new File(paraFileName);
		if (!tempFile.exists()) {
			System.out.println("File is not exist!");
			return;
		} // Of if

		RandomAccessFile tempRanFile = new RandomAccessFile(tempFile, "r");
		// ���ļ�����ʼλ��
		int tempBeginIndex = 0;
		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);

		// Step 1. count the item degree
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		int tempRating = 0;

		iTrDgr = new int[itemNum];
		iTrRatings = new int[itemNum][];
		iTrRateInds = new int[itemNum][];
		iTrTotRatings = new double[itemNum];
		iTrAveRatings = new double[itemNum];
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);

			iTrTotRatings[tempItemIndex] += tempRating;
			iTrDgr[tempItemIndex]++;
		} // Of while

		for (int i = 0; i < iTrDgr.length; i++) {
			iTrRatings[i] = new int[iTrDgr[i]];
			iTrRateInds[i] = new int[iTrDgr[i]];

			if (iTrDgr[i] > 1e-6) {
				iTrAveRatings[i] = iTrTotRatings[i] / iTrDgr[i];
			} // Of if
			iTrDgr[i] = 0;
		} // Of for i

		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);

			iTrRatings[tempItemIndex][iTrDgr[tempItemIndex]] = tempRating;
			iTrRateInds[tempItemIndex][iTrDgr[tempItemIndex]] = tempUserIndex;
			iTrDgr[tempItemIndex]++;
		} // Of while
			// SimpleTool.printMatrix(iTrRatings);
			// System.out.println(Arrays.toString(iTrAveRatings));
			// standarddeviation = new double[itemNum];
			// for (int i = 0; i < iTrDgr.length; i++) {
			// for (int j = 0; j < iTrDgr[i]; j++) {
			// System.out.println("iTrRatings[ "+ i + "][" + j + "] = " + iTrRatings[i][j] +
			// " - iTrAveRatings[" + i + "] = " + iTrAveRatings[i]);
			// standarddeviation[i] += (iTrRatings[i][j] - iTrAveRatings[i])
			// * (iTrRatings[i][j] - iTrAveRatings[i]);
			// System.out.println("standarddeviation[ "+ i + "] = " + standarddeviation[i]);
			//// SimpleTool.printMatrix(iTrRateInds);
		tempRanFile.close();
		// } // Of for j
		//
		// } // Of for i
	}// Of getItemTrainSet

	/**
	 * Read the data from the file, and get the user's test matrix.
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public void getUserTestSet(String paraFileName) throws Exception {
		File tempFile = null;
		String tempString = null;
		String[] tempStrArray = null;

		// Compute values of arrays
		tempFile = new File(paraFileName);
		if (!tempFile.exists()) {
			System.out.println("File is not exist!");
			return;
		} // Of if

		RandomAccessFile tempRanFile = new RandomAccessFile(tempFile, "r");
		// ���ļ�����ʼλ��
		int tempBeginIndex = 0;
		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);

		// Step 1. count the item degree
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		int tempRating = 0;

		uTeDgr = new int[userNum];
		uTeRatings = new int[userNum][];
		uTeRateInds = new int[userNum][];
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);

			uTeDgr[tempUserIndex]++;
		} // Of while

		for (int i = 0; i < uTeDgr.length; i++) {
			uTeRatings[i] = new int[uTeDgr[i]];
			uTeRateInds[i] = new int[uTeDgr[i]];
			uTeDgr[i] = 0;
		} // Of for i

		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);

			uTeRatings[tempUserIndex][uTeDgr[tempUserIndex]] = tempRating;
			uTeRateInds[tempUserIndex][uTeDgr[tempUserIndex]] = tempItemIndex;
			uTeDgr[tempUserIndex]++;
		} // Of while
		tempRanFile.close();
	}// Of getUserTestSet

	/**
	 * Read the data from the file, and get the user's relation matrix.
	 * 
	 * @param paraFileName
	 * @throws Exception
	 */
	public void getUserRelations(String paraFileName) throws Exception {
		File tempFile = null;
		String tempString = null;
		String[] tempStrArray = null;

		// Compute values of arrays
		tempFile = new File(paraFileName);
		if (!tempFile.exists()) {
			System.out.println("File is not exist!");
			return;
		} // Of if

		RandomAccessFile tempRanFile = new RandomAccessFile(tempFile, "r");
		// ���ļ�����ʼλ��
		int tempBeginIndex = 0;
		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);

		// Step 1. count the item degree
		int tempUserIndex = 0;
		int tempFriendIndex = 0;

		uRelations = new int[userNum][];
		uFriends = new int[userNum];
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(" ");
			tempUserIndex = Integer.parseInt(tempStrArray[0]) - 1;
			tempFriendIndex = Integer.parseInt(tempStrArray[1]) - 1;

			uFriends[tempUserIndex]++;
		} // Of while

		for (int i = 0; i < uFriends.length; i++) {
			uRelations[i] = new int[uFriends[i]];
			Arrays.fill(uRelations[i], -1);
			uFriends[i] = 0;
		} // Of for i

		// �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
		tempRanFile.seek(tempBeginIndex);
		while ((tempString = tempRanFile.readLine()) != null) {
			tempStrArray = tempString.split(" ");
			tempUserIndex = Integer.parseInt(tempStrArray[0]) - 1;
			tempFriendIndex = Integer.parseInt(tempStrArray[1]) - 1;

//			System.out.println(tempUserIndex+ " : " + tempFriendIndex);
//			System.out.println(Arrays.toString(uRelations[tempUserIndex]));
//			uRelations[tempUserIndex][uFriends[tempUserIndex]] = tempFriendIndex;
			insert(tempUserIndex, tempFriendIndex, uRelations);
//			System.out.println(Arrays.toString(uRelations[tempUserIndex]));
			uFriends[tempUserIndex]++;
		} // Of while
		tempRanFile.close();
	}// Of getUserTestSet

	/**
	 * Just get the sec-hand friends set
	 * @return The sec-hand friends matrix.
	 */
	public int[][] getuSecHandRels2() {
		int[][] uSecHandRels = new int[uRelations.length][];
		int[] uSecHandFriends = new int[uFriends.length];
		uSecRels = new int[uRelations.length][];
		uSecFriends = new int[uFriends.length];
		int[] tempFriends = new int[uFriends.length];

		int tempUser = -1;
		int tempFriend = -1;
		boolean flag = true;
		// int tempCount = 0;

		for (int i = 0; i < uRelations.length; i++) {
//			tempFriends[i] = uFriends[i];
			// System.out.println("the uFriends is " + Arrays.toString(uFriends));
			// System.out.println("the uSecHandFriends is " +
			// Arrays.toString(uSecHandFriends));
			for (int j = 0; j < uRelations[i].length; j++) {
				tempFriends[i] += uFriends[uRelations[i][j]];
			} // Of for j
			uSecHandRels[i] = new int[tempFriends[i]];
			
		} // Of for i
//		System.out.println("the uSecHandFriends is " + Arrays.toString(uSecHandFriends));

		for (int i = 0; i < uRelations.length; i++) {
			if(uRelations[i] == null) {
				break;
			}//Of if
			Arrays.fill(uSecHandRels[i], -1);
			// System.out.println(Arrays.toString(uSecHandRels[i]));
			// get the direct friends.
//			for (int j = 0; j < uRelations[i].length; j++) {
//				uSecHandRels[i][j] = uRelations[i][j];
//			} // Of for j
			

//			System.out.println("the begin is" + Arrays.toString(uSecHandRels[i]));
			// get the second hands friends.
			for (int j = 0; j < uRelations[i].length; j++) {
				tempUser = uRelations[i][j];
//				System.out.println("the director friend is " + tempUser);
				for (int k = 0; k < uRelations[tempUser].length; k++) {
					tempFriend = uRelations[tempUser][k];

//					System.out.println(tempFriend);

					for (int g = 0; g < uSecHandRels[i].length; g++) {
//						System.out.println("test g");
						flag = true;
						if (uSecHandRels[i][g] == tempFriend) {
//							System.out.println("Traverse!");
							flag = false;
							break;
						}else if(uSecHandRels[i][g] == -1 && uSecHandRels[i][g] > tempFriend ) {
							break;
						}
					} // Of for g
					if (flag && uRelations[tempUser][k] != i) {
//						System.out.println("test insert");
						insert(i, uRelations[tempUser][k], uSecHandRels);
						uSecFriends[i]++;
					} // Of if
				} // Of for k
				
//				System.out.println("next!");
			} // Of for j
			
//			System.out.println("one line is over" + Arrays.toString(uSecHandRels[i]));
		} // Of for i
//		System.out.println(Arrays.toString(uSecFriends));
		//Compress
		for (int i = 0; i < uRelations.length; i++) {
			uSecRels[i] = new int[uSecFriends[i]];
		}
		for (int i = 0; i < uSecRels.length; i++) {
//			System.out.println( uSecRels[i].length);
//			System.out.println(Arrays.toString(uSecHandRels[i]));
			for (int j = 0; j < uSecRels[i].length; j++) {
				
				uSecRels[i][j] = uSecHandRels[i][j];
			}
		}
		return uSecHandRels;
	}// Of getuSecHandRels
	
	/**
	 * Get the director and sec-hand friends set.
	 * @return The director and sec-hand friends set.
	 */
	public int[][] getuSecHandRels1() {
		int[][] uSecHandRels = new int[uRelations.length][];
//		int[] uSecHandFriends = new int[uFriends.length];
		uSecRels = new int[uRelations.length][];
		uSecFriends = new int[uFriends.length];
		int[] tempFriends = new int[uFriends.length];

		int tempUser = -1;
		int tempFriend = -1;
		boolean flag = true;
		// int tempCount = 0;
//		System.out.println("the uFriends is " + Arrays.toString(uFriends));

		for (int i = 0; i < uRelations.length; i++) {
			tempFriends[i] = uFriends[i];
			for (int j = 0; j < uRelations[i].length; j++) {
				tempFriends[i] += uFriends[uRelations[i][j]];
			} // Of for j
//			uSecHandFriends[i] = uFriends[i];
			uSecFriends[i] = uFriends[i];
			uSecHandRels[i] = new int[tempFriends[i]];
			
		} // Of for i
//		System.out.println("the tempFriends is " + Arrays.toString(tempFriends));

		for (int i = 0; i < uRelations.length; i++) {
			if(uRelations[i] == null) {
				break;
			}//Of if
			Arrays.fill(uSecHandRels[i], -1);
			// System.out.println(Arrays.toString(uSecHandRels[i]));
			// get the direct friends.
			
			for (int j = 0; j < uRelations[i].length; j++) {
				uSecHandRels[i][j] = uRelations[i][j];
			} // Of for j

//			System.out.println("the begin is" + Arrays.toString(uSecHandRels[i]));
			// get the second hands friends.
			for (int j = 0; j < uRelations[i].length; j++) {
				tempUser = uRelations[i][j];
//				System.out.println("the director friend is " + tempUser);
				for (int k = 0; k < uRelations[tempUser].length; k++) {
					tempFriend = uRelations[tempUser][k];

//					System.out.println(tempFriend);

					for (int g = 0; g < uSecHandRels[i].length; g++) {
//						System.out.println("test g");
						flag = true;
						if (uSecHandRels[i][g] == tempFriend) {
//							System.out.println("Traverse!");
							flag = false;
							break;
						}else if(uSecHandRels[i][g] == -1 && uSecHandRels[i][g] > tempFriend ) {
							break;
						}
					} // Of for g
					if (flag) {
//						System.out.println("test insert");
						insert(i, uRelations[tempUser][k], uSecHandRels);
						uSecFriends[i]++;
					} // Of if
				} // Of for k
//				System.out.println("next!");
			} // Of for j
			
//			System.out.println("one line is over" + Arrays.toString(uSecHandRels[i]));
		} // Of for i
//		System.out.println(Arrays.toString(uSecFriends));
		//Compress
		for (int i = 0; i < uRelations.length; i++) {
			uSecRels[i] = new int[uSecFriends[i]];
		}
		for (int i = 0; i < uSecRels.length; i++) {
			for (int j = 0; j < uSecRels[i].length; j++) {
				uSecRels[i][j] = uSecHandRels[i][j];
			}
		}
		return uSecHandRels;
	}// Of getuSecHandRels
	/**
	 * Get the director and sec-hand friends set, and it could transform again.
	 * @return The director and sec-hand friends set.
	 */
	public int[][] getuSecHandRels1_2() {
		int[][] uSecHandRels = new int[uSecRels.length][];
//		int[] uSecHandFriends = new int[uFriends.length];
		uThrRels = new int[uSecRels.length][];
		uThrFriends = new int[uSecRels.length];
		int[] tempFriends = new int[uSecRels.length];

		int tempUser = -1;
		int tempFriend = -1;
		boolean flag = true;
		// int tempCount = 0;
//		System.out.println("the uFriends is " + Arrays.toString(uFriends));

		for (int i = 0; i < uSecRels.length; i++) {
			tempFriends[i] = uSecFriends[i];
			for (int j = 0; j < uSecRels[i].length; j++) {
				tempFriends[i] += uSecFriends[uSecRels[i][j]];
			} // Of for j
//			uSecHandFriends[i] = uFriends[i];
			uThrFriends[i] = uSecFriends[i];
			uSecHandRels[i] = new int[tempFriends[i]];
			
		} // Of for i
//		System.out.println("the tempFriends is " + Arrays.toString(tempFriends));

		for (int i = 0; i < uSecRels.length; i++) {
			if(uSecRels[i] == null) {
				break;
			}//Of if
			Arrays.fill(uSecHandRels[i], -1);
			// System.out.println(Arrays.toString(uSecHandRels[i]));
			// get the direct friends.
			
			for (int j = 0; j < uSecRels[i].length; j++) {
				uSecHandRels[i][j] = uSecRels[i][j];
			} // Of for j

//			System.out.println("the begin is" + Arrays.toString(uSecHandRels[i]));
			// get the second hands friends.
			for (int j = 0; j < uSecRels[i].length; j++) {
				tempUser = uSecRels[i][j];
//				System.out.println("the director friend is " + tempUser);
				for (int k = 0; k < uSecRels[tempUser].length; k++) {
					tempFriend = uSecRels[tempUser][k];

//					System.out.println(tempFriend);

					for (int g = 0; g < uSecHandRels[i].length; g++) {
//						System.out.println("test g");
						flag = true;
						if (uSecHandRels[i][g] == tempFriend) {
//							System.out.println("Traverse!");
							flag = false;
							break;
						}else if(uSecHandRels[i][g] == -1 && uSecHandRels[i][g] > tempFriend ) {
							break;
						}
					} // Of for g
					if (flag) {
//						System.out.println("test insert");
						insert(i, uSecRels[tempUser][k], uSecHandRels);
						uThrFriends[i]++;
					} // Of if
				} // Of for k
//				System.out.println("next!");
			} // Of for j
			
//			System.out.println("one line is over" + Arrays.toString(uSecHandRels[i]));
		} // Of for i
//		System.out.println(Arrays.toString(uSecFriends));
		//Compress
		for (int i = 0; i < uSecRels.length; i++) {
			uThrRels[i] = new int[uThrFriends[i]];
		}
		for (int i = 0; i < uSecRels.length; i++) {
			for (int j = 0; j < uThrRels[i].length; j++) {
				uThrRels[i][j] = uSecHandRels[i][j];
			}
		}
		return uSecHandRels;
	}// Of getuSecHandRels

	/**
	 * Insert a User to the target user's friend circle.
	 * 
	 * @param paraTargetUser
	 *                The target user.
	 * @param paraUser
	 *                The user who ready be inserting in the sec-hands friends matrix.
	 * @param paraSecRels
	 *                The sec-hands friends matrix.
	 */
	public void insert(int paraTargetUser, int paraUser, int[][] paraSecRels) {
		// System.out.println(Arrays.toString(tempUserArray) +
		// Arrays.toString(uRelations[paraTargetUser]));
//		System.out.println(Arrays.toString(paraSecRels[paraTargetUser]));
//		 System.out.println("the user is" + paraUser);
		// int tempUser = -1;
		for (int i = 0; i < paraSecRels[paraTargetUser].length; i++) {
			if (paraUser < paraSecRels[paraTargetUser][i] || paraSecRels[paraTargetUser][i] == -1) {
				// tempUser = uSecHandRels[paraTargetUser][i];
				for (int j = paraSecRels[paraTargetUser].length - 1; j > i; j--) {
					paraSecRels[paraTargetUser][j] = paraSecRels[paraTargetUser][j - 1];
				}
				paraSecRels[paraTargetUser][i] = paraUser;
				break;
			} // Of if
		} // Of for i

		// for (int i = 0; i < paraFriends[paraTargetUser]; i++) {
		// if (uSecHandRels[paraTargetUser][i] > paraUser) {
		// for (int j = paraFriends[paraTargetUser] - 1; j > i; j--) {
		// uSecHandRels[paraTargetUser][j] = uSecHandRels[paraTargetUser][j - 1];
		// // System.out.println( Arrays.toString(uSecHandRels[paraTargetUser]));
		// } // Of for j
		// uSecHandRels[paraTargetUser][i] = paraUser;
		// break;
		// } // Of if
		// } // Of for i
//		System.out.println(Arrays.toString(paraSecRels[paraTargetUser]));
	}// Of insert

	/**
	 * Copying arrays.
	 */
	void copyArrays(int[] paraFirArrays, int[] paraSecArrays) {
		for (int i = 0; i < paraSecArrays.length; i++) {
			paraFirArrays[i] = paraSecArrays[i];
		}
	}// Of copyArrays

	/**
	 * Get the friends who are be ordered.
	 */
	public void getUOrderFriends() {
		uOrderFriends = new int[userNum][];
		int temp = 0;
		// int[] tempArrays = new int[userNum];
		// System.out.println("the initial uLeaders is " + Arrays.toString(uLeaders));
		// System.out.println("the initial uFriends is " + Arrays.toString(uFriends));
		for (int i = 0; i < uSecRels.length; i++) {
			uOrderFriends[i] = new int[uSecFriends[i]];
			copyArrays(uOrderFriends[i], uSecRels[i]);
			// System.out.println("the " + i + " orignal array is " + uOrderFriends[i]);
			for (int j = 0; j < uSecRels[i].length; j++) {
				if (uSecFriends[i] == 1) {
					uOrderFriends[i][j] = uSecRels[i][j];
					break;
				} else if (uSecFriends[i] == 0) {
					break;
				} // Of if
				for (int k = j + 1; k < uSecRels[i].length; k++) {
					// System.out.println(uOrderFriends[i][j] + " : " + uOrderFriends[i][k]);
					// System.out.println(uFriends[uOrderFriends[i][j]] + " : " +
					// uFriends[uOrderFriends[i][k]]);
					if (uSecFriends[uOrderFriends[i][j]] < uSecFriends[uOrderFriends[i][k]]) {
						temp = uOrderFriends[i][j];
						uOrderFriends[i][j] = uOrderFriends[i][k];
						uOrderFriends[i][k] = temp;
					} // Of if
						// System.out.println("the " + i + " array is " +
						// Arrays.toString(uOrderFriends[i]));
				} // Of for k
			} // of for j
				// System.out.println("the " + i + " array is " +
				// Arrays.toString(uOrderFriends[i]));
		} // of for i

		// System.out.println("the initial uLeaders is " + Arrays.toString(uLeaders));
	}// Of getUOrderFriends1

	/**
	 * Get the top influence friend in a user's friends.
	 */
	public void getULeaders() {
		uLeaders = new int[userNum];
		int tempMin = 0;
		int tempIndex = -1;
		// System.out.println("the initial uLeaders is " + Arrays.toString(uLeaders));
		// System.out.println("the initial uFriends is " + Arrays.toString(uFriends));
		for (int i = 0; i < uRelations.length; i++) {
			for (int j = 0; j < uRelations[i].length; j++) {
				if (uFriends[uRelations[i][j]] > tempMin) {
					// System.out.println(uFriends[uRelations[i][j]] + " : " + tempMin);
					tempMin = uFriends[uRelations[i][j]];
					tempIndex = uRelations[i][j];
				} // Of if
			} // of for j
			uLeaders[i] = tempIndex;
			tempMin = 0;
			tempIndex = -1;
		} // of for i

		// System.out.println("the initial uLeaders is " + Arrays.toString(uLeaders));
	}// Of getULeaders

	/**
	 * 
	 * @throws Exception
	 */
	public void buildDataModel() throws Exception {
//		 String tempTrain = "data/CiaoDVD/u1.base";
//		 String tempTest = "data/CiaoDVD/u1.test";
		String tempRelation = "data/ltrust1.txt";
//		String tempRelation = "data/CiaoDVD/trusts.txt";
//		 getUserTrainSet(tempTrain);
//		 getUserTestSet(tempTest);
		getUserRelations(tempRelation);
		SimpleTool.printMatrix(uRelations);
		int count = 0;
		for (int i = 0; i < uRelations.length; i++) {
			if(uRelations[i].length == 0) {
				count ++;
			}//Of if
		}//Of for i
//		System.out.println(count);
//		getULeaders();
		getuSecHandRels1();
//		getuSecHandRels2();
//		getUOrderFriends();


		System.out.println("the second hand friends are" );
		SimpleTool.printMatrix(uSecRels);

		getuSecHandRels1_2();

		System.out.println("the three hand friends are" );
		SimpleTool.printMatrix(uThrRels);
//		SimpleTool.printMatrix(uOrderFriends);
	}// of buildDataModel

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DataModel tempSoc = new DataModel(4, 5); // 49290, 139738 4, 5 30444,16121
			tempSoc.buildDataModel();
		} catch (Exception ee) {
			ee.printStackTrace();
		} // Of try

	}// Of main
}// Of Class SocialDataModel

