package common;

public class ItemInformation {
	public int userIndex;
	public int itemIndex;
	public double rating;
	public int time;
	
	/**
	 * 
	 */
	public ItemInformation() {
		// TODO 自动生成的构造函数存根
	}//Of the first constructor

	/**
	 * Find the minimum value of the time stamp.
	 */
	public static int minTime(ItemInformation[] paraArray) {
		int tempMinTime = Integer.MAX_VALUE;

		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i].time < tempMinTime) {
				tempMinTime = paraArray[i].time;
			}// Of if
		}// Of for i

		return tempMinTime;
	}// Of minTime

	/**
	 * Find the minimum value of the time stamp.
	 */
	public static int minTime(ItemInformation[][] paraMatrix) {
		int tempMinTime = Integer.MAX_VALUE;

		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[i].length; j++) {
				if (paraMatrix[i][j].time < tempMinTime) {
					tempMinTime = paraMatrix[i][j].time;
				}// Of if
			}// Of for j
		}// Of for i

		return tempMinTime;
	}// Of minTime

	/**
	 * Find the maximum value of the time stamp.
	 */
	public static int maxTime(ItemInformation[] paraArray) {
		int tempMaxTime = Integer.MIN_VALUE;

		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i].time > tempMaxTime) {
				tempMaxTime = paraArray[i].time;
			}// Of if
		}// Of for i

		return tempMaxTime;
	}// Of maxTime
	
	/**
	 * Find the maximum value of the time stamp.
	 */
	public static int maxTime(ItemInformation[][] paraMatrix) {
		int tempMaxTime = Integer.MIN_VALUE;

		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[i].length; j++) {
				if (paraMatrix[i][j].time > tempMaxTime) {
					tempMaxTime = paraMatrix[i][j].time;
				}// Of if
			}// Of for j
		}// Of for i

		return tempMaxTime;
	}// Of maxTime
}// Of class ItemInformation
