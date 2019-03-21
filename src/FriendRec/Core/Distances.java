package FriendRec.Core;

import java.util.Arrays;

import javax.xml.crypto.Data;

import FriendRec.Core.DataModel;

public class Distances {
	public static DataModel dataModel;
//	public static void Distances() {
//		try {
//		
//		} catch (Exception ee) {
//			ee.printStackTrace();
//		} // Of try
//	}// Of the first constructor

	/**
	 ************************* 
	 * Compute the jaccard value of two vectors.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double jaccard(int[] paraRow1, int[] paraRow2){
		double tempJaccard = 0;
		double tempInterCount = 0;
		
		int i = 0; 
		int j = 0;
		//Step 1. Obtain the number of intersection.
		while(i < paraRow1.length && j < paraRow2.length){
			if(paraRow1[i] < paraRow2[j]){
				i ++;
			}else if(paraRow1[i] > paraRow2[j]){
				j ++;
			}else{
				tempInterCount ++;
				i ++;
				j ++;
			}//Of if
		}//Of while
		
		//Step 2. Obtain the number of union
		double tempOuterCount = paraRow1.length + paraRow2.length - tempInterCount - 1;
		
		if(tempOuterCount > 1e-6){
			tempJaccard = tempInterCount / tempOuterCount;
		}//Of if
		return tempJaccard;
	}//Of jaccard
	
	/**
	 ************************* 
	 * Compute the jaccard value of two vectors.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double Jaccard(int paraRow1, int paraRow2, DataModel dataModel) {
		double tempJ = 0;
//		System.out.println("the utrrateinds is " + Arrays.deepToString(dataModel.uTrRateInds));
//		System.out.println("paraRow1's length is " + dataModel.uTrRateInds[paraRow1].length + "paraRow2 is" + paraRow2 );
	
		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {
	
			return 0.0;
		} // Of if
	
		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {
	
			return 0.0;
		} // Of if
			// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {
	
				j++;
			} else {
	
				tempJ++;
	
				// System.out.print(itemRatingIndices[paraRow1][i] +",");
				i++;
				j++;
			} // Of if
		} // Of while
			// System.out.println("test1.3");
		tempJ = tempJ / (dataModel.uTrRateInds[paraRow1].length + dataModel.uTrRateInds[paraRow2].length - tempJ);
		return tempJ;
	}// Of Jaccard
	/**
	 ************************* 
	 * Compute the Manhattan distance between two rows. 0s are not considered.
	 *	 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double manhattanDistance(int paraRow1, int paraRow2, DataModel dataModel) {
		double tempDistance = 0;
		double tempCount = 0;
		for (int i = 0; i < dataModel.uTrRatings[paraRow1].length; i++) {
			for (int j = 0; j < dataModel.uTrRatings[paraRow2].length; j++) {
				if (dataModel.uTrRateInds[paraRow1][i] == dataModel.uTrRateInds[paraRow2][j]) {
					tempCount++;
					tempDistance += Math.abs(dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j]);
				} // Of if
			} // Of for j
		} // Of for i

		if (tempCount == 0) {
			return 0;
		} // Of if
		return tempDistance / tempCount;
	}// Of manhattanDistance
	
	public static double oDistance(int paraRow1, int paraRow2, DataModel dataModel) {
		double tempDistance = 0;
		double tempResult = 0;
		double tempCount = 0;
		for (int i = 0; i < dataModel.uTrRatings[paraRow1].length; i++) {
			for (int j = 0; j < dataModel.uTrRatings[paraRow2].length; j++) {
				if (dataModel.uTrRateInds[paraRow1][i] == dataModel.uTrRateInds[paraRow2][j]) {
					tempCount++;
					tempDistance = Math.pow(Math.abs(dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j]), 2);
					tempResult += tempDistance;
				} // Of if
			} // Of for j
		} // Of for i

		if (tempCount == 0) {
			return 0;
		} // Of if
		tempResult = Math.sqrt(tempDistance);
		return tempResult;
	}// Of manhattanDistance
	
	/**
	 ************************* 
	 * Compute the Euclidean distance.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double Euclidean(int paraRow1, int paraRow2, DataModel dataModel) {
		double tempE = 0;
		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if
			// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {

				tempE += (dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j])
						* (dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j]);
				i++;
				j++;
			} // Of if
		} // Of while
			// System.out.println("test1.3");

		tempE = Math.sqrt(tempE);
		// System.out.println("tempE: "+tempE);

		return tempE;
	}// Of Euclidean

	/**
	 ************************* 
	 * Compute the TMJ similarity.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double TMJ(int paraRow1, int paraRow2, DataModel dataModel) {
		double tempDistance = 0;
		double tempJ = 0;
		double tempT = 0;
		double tempParaRow1 = 0;
		double tempParaRow2 = 0;
		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if
			// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					||dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {
				tempParaRow1 += dataModel.uTrRatings[paraRow1][i] * dataModel.uTrRatings[paraRow1][i];
				tempParaRow2 += dataModel.uTrRatings[paraRow2][j] * dataModel.uTrRatings[paraRow2][j];
				tempT += (dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j])
						* (dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j]);
				tempJ++;

				// System.out.print(itemRatingIndices[paraRow1][i] +",");
				i++;
				j++;
			} // Of if
		} // Of while
		tempJ = tempJ / (dataModel.uTrRateInds[paraRow1].length + dataModel.uTrRateInds[paraRow2].length - tempJ - 1);
		if (tempParaRow1 == 0 || tempParaRow2 == 0) {
			tempT = 0;
		} else {
			tempT = 1 - Math.sqrt(tempT) / (Math.sqrt(tempParaRow1) + Math.sqrt(tempParaRow2));
		}
		tempDistance = tempJ * tempT;
		return tempDistance;
	}// Of TMJ

	/**
	 ************************* 
	 * Compute the cosine similarity.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double Cosine(int paraRow1, int paraRow2, DataModel dataModel) {
		double tempC = 0;
		double tempParaRow1 = 0;
		double tempParaRow2 = 0;
		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if
			// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {
				tempParaRow1 += dataModel.uTrRatings[paraRow1][i] * dataModel.uTrRatings[paraRow1][i];
				tempParaRow2 += dataModel.uTrRatings[paraRow2][j] * dataModel.uTrRatings[paraRow2][j];
				tempC += dataModel.uTrRatings[paraRow1][i] * dataModel.uTrRatings[paraRow2][j];

				// System.out.print(itemRatingIndices[paraRow1][i] +",");
				i++;
				j++;
			} // Of if
		} // Of while
			// System.out.println("test1.3");
		if (tempParaRow1 == 0 || tempParaRow2 == 0) {
			tempC = 0;

		} else {
			tempC = tempC / (Math.sqrt(tempParaRow1) * Math.sqrt(tempParaRow2));

		}
		return tempC;
	}// Of cosine

	/**
	 ************************* 
	 * Compute the Triangle similarity.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double Triangle(int paraRow1, int paraRow2, DataModel dataModel){
		double tempT = 0;
		double tempParaRow1 = 0;
		double tempParaRow2 = 0;
		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if
			// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {
				tempParaRow1 += dataModel.uTrRatings[paraRow1][i] * dataModel.uTrRatings[paraRow1][i];
				tempParaRow2 += dataModel.uTrRatings[paraRow2][j] * dataModel.uTrRatings[paraRow2][j];

				tempT += (dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j])
						* (dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j]);

				i++;
				j++;

			} // Of if
		} // Of while

		if (tempParaRow1 == 0 || tempParaRow2 == 0) {
			tempT = 0;
		} else {
			tempT = 1 - Math.sqrt(tempT) / (Math.sqrt(tempParaRow1) + Math.sqrt(tempParaRow2));
		}
		return tempT;
	}// Of Triangle

	/**
	 ************************* 
	 * Compute the PIP similarity.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double PIP(int paraRow1, int paraRow2, DataModel dataModel){
		double tempEachPiP = 0;
		double tempPiP = 0;
		if (dataModel.uTrRateInds[paraRow1].length == 0 ||dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if
			// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] <dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {
				boolean agreeMent = true;
				if ((dataModel.uTrRatings[paraRow1][i] > 2.5 && dataModel.uTrRatings[paraRow2][j] < 2.5)
						|| (dataModel.uTrRatings[paraRow1][i] < 2.5 && dataModel.uTrRatings[paraRow2][j] > 2.5)) {
					agreeMent = false;
				}
				double tempProximity = 0;
				double absoluteDiatance = 0;
				if (agreeMent) {
					absoluteDiatance = Math
							.abs(dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j]);
				} else {
					absoluteDiatance = 2
							* Math.abs(dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j]);
				}
				tempProximity = (9 - absoluteDiatance) * (9 - absoluteDiatance);
				// System.out.println("tempProximity: "+tempProximity);
				double tempImpact = 0;
				if (agreeMent) {
					tempImpact = (Math.abs(dataModel.uTrRatings[paraRow1][i] - 2.5) + 1)
							* (Math.abs(dataModel.uTrRatings[paraRow2][j] - 2.5) + 1);
				} else {
					tempImpact = 1 / ((Math.abs(dataModel.uTrRatings[paraRow1][i] - 2.5) + 1)
							* (Math.abs(dataModel.uTrRatings[paraRow2][j] - 2.5) + 1));
				}
				// System.out.println("tempImpact: "+tempImpact);
				double tempPopularity = 0;
				if ((dataModel.uTrRatings[paraRow1][i] > dataModel.uTrAveRatings[dataModel.uTrRateInds[paraRow1][i]]
						&& dataModel.uTrRatings[paraRow2][j] > dataModel.uTrAveRatings[dataModel.uTrRateInds[paraRow1][i]])
						|| (dataModel.uTrRatings[paraRow1][i] < dataModel.uTrAveRatings[dataModel.uTrRateInds[paraRow1][i]]
								&& dataModel.uTrRatings[paraRow2][j] < dataModel.uTrAveRatings[dataModel.uTrRateInds[paraRow1][i]])) {
					double tempPP = (dataModel.uTrRatings[paraRow1][i] + dataModel.uTrRatings[paraRow2][j]) / 2
							- dataModel.uTrAveRatings[dataModel.uTrRateInds[paraRow1][i]];
					tempPopularity = 1 + tempPP * tempPP;
				} else {
					tempPopularity = 1;
				}
				// System.out.println("tempPopularity: "+tempPopularity);
				tempEachPiP = tempProximity * tempImpact * tempPopularity;
				tempPiP += tempEachPiP;

				i++;
				j++;
			} // Of if
		} // Of while
			// System.out.println("tempPiP: " + tempPiP);

		return tempPiP;
	}// Of PIP

	/**
	 ************************* 
	 * Compute the NHSM similarity
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double NHSM(int paraRow1, int paraRow2, DataModel dataModel) {

		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if
			// System.out.println("test1.1");
		double tempJ = 0;
		double tempPSS = 0;
		double tempURP = 0;
		double tempChangedeviation = 0;

		for (int i = 0; i < dataModel.uTrRatings[paraRow1].length; i++) {
			if (dataModel.uTrRatings[paraRow1][i] > 0) {
				tempChangedeviation += (dataModel.uTrRatings[paraRow1][i] - dataModel.iTrAveRatings[paraRow1])
						* (dataModel.uTrRatings[paraRow1][i] - dataModel.iTrAveRatings[paraRow1]);
				// System.out.println("ratingMatrix[paraRow][i]"+itemRatingInformation[paraRow1][i]);
				// System.out.println("averageScoreArray[paraRow]"+itemAveRating[paraRow1]);
			}
		} // of if i
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {
				tempJ++;
				double tempEachPSS = 0;
				double Proximity = 0;
				Proximity = 1 - 1 / (1
						+ Math.exp(-Math.abs(dataModel.uTrRatings[paraRow1][i] - dataModel.uTrRatings[paraRow2][j])));
				// Impact
				double Significance = 0;
				Significance = 1 / (1 + Math.exp(-Math.abs(dataModel.uTrRatings[paraRow1][i] - 2.5)
						* Math.abs(dataModel.uTrRatings[paraRow2][j] - 2.5)));
				// Popularity
				double Singularity = 0;
				Singularity = 1 - 1 / (1 + Math
						.exp(-Math.abs((dataModel.uTrRatings[paraRow1][i] + dataModel.uTrRatings[paraRow2][j]) / 2
								- dataModel.uTrAveRatings[dataModel.uTrRateInds[paraRow1][i]])));
				tempEachPSS = Proximity * Significance * Singularity;
				tempPSS += tempEachPSS;
				i++;
				j++;
			} // Of if
		} // Of while
		tempJ = tempJ / (dataModel.uTrRateInds[paraRow1].length + dataModel.uTrRateInds[paraRow2].length - tempJ - 1);
		double tempStandardDeviationPredition = Math
				.sqrt(tempChangedeviation / (dataModel.uTrRatings[paraRow1].length - 1));
		double tempStandardDeviationParaRow2 = Math
				.sqrt(dataModel.standarddeviation[paraRow2] / dataModel.uTrRatings[paraRow2].length);
		tempURP = 1 - 1 / (1 + Math.exp(-Math.abs(dataModel.iTrAveRatings[paraRow1] - dataModel.iTrAveRatings[paraRow2])
				* Math.abs(tempStandardDeviationPredition - tempStandardDeviationParaRow2)));

		double tempDistance = tempPSS * tempJ * tempURP;

		return tempDistance;
	}// Of NHSM

	/**
	 ************************* 
	 * Compute the BC similarity.
	 *
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double BC(int paraRow1, int paraRow2, DataModel dataModel) {

		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if
			// System.out.println("test1.1");

		dataModel.everyItemClassfication = new double[2][9];
		// System.out.println("R1: "+paraRow1 +", R2: "+paraRow2);
		for (int j = 0; j < dataModel.uTrRatings[paraRow1].length; j++) {
			if (dataModel.uTrRatings[paraRow1][j] == 0) {
				continue;
			}

			// System.out.println(itemRatingInformation[paraRow1][j]);
			dataModel.everyItemClassfication[0][(int) (dataModel.uTrRatings[paraRow1][j] * 2 - 1)]++;
			dataModel.everyItemClassfication[0][8]++;
		} // of for if

		for (int j = 0; j < dataModel.uTrRatings[paraRow2].length; j++) {
			if (dataModel.uTrRatings[paraRow2][j] == 0) {
				continue;
			}

			// System.out.println(itemRatingInformation[paraRow2][j]);
			dataModel.everyItemClassfication[1][(int) (dataModel.uTrRatings[paraRow2][j] * 2 - 1)]++;
			dataModel.everyItemClassfication[1][8]++;
		} // of for j

		double BSum = 0;

		for (int x = 0; x < 5; x++) {
			BSum += Math.sqrt((dataModel.everyItemClassfication[0][x] / dataModel.everyItemClassfication[0][8])
					* (dataModel.everyItemClassfication[1][x] / dataModel.everyItemClassfication[1][8]));
			// System.out.println("BSum: "+BSum);
		} // of if X

		return BSum;
	}// Of BC

	/**
	 *************************  
	 * Compute the PCC similarity.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 *************************
	 */
	public static double pearson(int paraRow1, int paraRow2, DataModel dataModel ) {
		double tempDistance = 0;
		double tempFirstUserRating = 0;
		double tempSecondUserRating = 0;
		double tempXYSum = 0;
		double tempXSum = 0;
		double tempYSum = 0;
		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if

		// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i < dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {
				tempFirstUserRating = dataModel.uTrRatings[paraRow1][i] - dataModel.uTrAveRatings[paraRow1];
				tempSecondUserRating = dataModel.uTrRatings[paraRow2][j] - dataModel.uTrAveRatings[paraRow2];
				tempXYSum += tempFirstUserRating * tempSecondUserRating;
				tempXSum += tempFirstUserRating * tempFirstUserRating;
				tempYSum += tempSecondUserRating * tempSecondUserRating;
				i++;
				j++;

			} // Of if
		} // Of while
		if (tempXSum == 0 || tempYSum == 0) {
			return -1;
		}
		// System.out.println("test1.3");
		tempDistance = tempXYSum / (Math.sqrt(tempXSum) * Math.sqrt(tempYSum));
		// System.out.println("distance: "+tempDistance);
		tempDistance = Math.abs(tempDistance);
		// System.out.println(tempDistance);
		return tempDistance;
	}// Of PCC

	/**
	 *************************  
	 * Compute the CPCC similarity.
	 * 
	 * @param paraRow1
	 *            the first row
	 * @param paraRow2
	 *            the second row
	 * @param dataModel 
	 *            the metrix of data          
	 * @author zhangziyin 2018/05/23
	 ************************* 
	 */
	public static double CPCC(int paraRow1, int paraRow2, DataModel dataModel) {
		double tempDistance = 0;
		double tempFirstUserRating = 0;
		double tempSecondUserRating = 0;
		double tempXYSum = 0;
		double tempXSum = 0;
		double tempYSum = 0;
		if (dataModel.uTrRateInds[paraRow1].length == 0 || dataModel.uTrRateInds[paraRow2].length == 0) {

			return 0.0;
		} // Of if

		if ((dataModel.uTrRateInds[paraRow1][0] > dataModel.uTrRateInds[paraRow2][dataModel.uTrRateInds[paraRow2].length - 1])
				|| (dataModel.uTrRateInds[paraRow2][0] > dataModel.uTrRateInds[paraRow1][dataModel.uTrRateInds[paraRow1].length
						- 1])) {

			return 0.0;
		} // Of if

		// System.out.println("test1.1");
		int i = 0, j = 0;
		while (i <dataModel.uTrRateInds[paraRow1].length && j < dataModel.uTrRateInds[paraRow2].length) {
			if (dataModel.uTrRateInds[paraRow1][i] < dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow1][i] == 0) {

				i++;
			} else if (dataModel.uTrRateInds[paraRow1][i] > dataModel.uTrRateInds[paraRow2][j]
					|| dataModel.uTrRatings[paraRow2][j] == 0) {

				j++;
			} else {

				tempFirstUserRating = dataModel.uTrRatings[paraRow1][i] - 2.5;
				tempSecondUserRating = dataModel.uTrRatings[paraRow2][j] - 2.5;
				tempXYSum += tempFirstUserRating * tempSecondUserRating;
				tempXSum += tempFirstUserRating * tempFirstUserRating;
				tempYSum += tempSecondUserRating * tempSecondUserRating;
				i++;
				j++;

			} // Of if
		} // Of while
			// System.out.println("test1.3");
		if (tempXSum == 0 || tempYSum == 0) {
			return -1;
		}
		tempDistance = tempXYSum / (Math.sqrt(tempXSum) * Math.sqrt(tempYSum));

		tempDistance = Math.abs(tempDistance);
		return tempDistance;
	}// Of CPCC
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// System.out.println("Main test 1");
			Distances tempFri = new Distances();
			String tempTrain = "data/u0.base";
			String tempTest = "data/u0.test";
			String tempRelation = "data/trust_data2.txt";
			tempFri.dataModel = new DataModel(49290, 139738);//49290, 139738  4,5
			tempFri.dataModel.getUserTrainSet(tempTrain);
			tempFri.dataModel.getItemTrainSet(tempTrain);
			tempFri.dataModel.getUserTestSet(tempTest);
			tempFri.dataModel.getUserRelations(tempRelation);
			tempFri.dataModel.getULeaders();			
			
//			int tempK = 10;
//			tempFri.computePrecisionAndRecall(0.6, 3, tempK);
//			tempFri.computeFMeasure();
//			System.out.println("Precision = " + tempFri.precision);
//			System.out.println("recall = " + tempFri.recall);
//			System.out.println("FMeasure = " + tempFri.fMeasure);
//			
//			double tempMae = tempFri.computeMAE(tempK);
//			System.out.println("MAE = " + tempMae);
//	
		} catch (Exception ee) {
			ee.printStackTrace();
		}// of try
	}//Of main


}//Of class Distances 
