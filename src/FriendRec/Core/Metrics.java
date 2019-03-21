package FriendRec.Core;

import FriendRec.Core.DataModel;
import FriendRec.Core.SetOperator;

/**
 * Metrics.
 * <p>
 * Author: <b>Henry</b> zhanghrswpu@163.com <br>
 * Copyright: The source code and all documents are open and free. PLEASE keep
 * this header while revising the program. <br>
 * Organization: School of Computer Science, Southwest Petroleum University,
 * Chengdu 610500, China.<br>
 * Project: The recommender system project.
 * <p>
 * Progress: Almost finished.<br>
 * Written time: July 27, 2017. <br>
 * Last modify time: July 27, 2017.
 */
public class Metrics {
	public DataModel dataModel;
	double precision;
	double recall;
	double fMeasure;
	
	/**
	 * 
	 * @param paraRecThreshold
	 * @param paraLikeThreshold
	 * @return
	 */

	void computePrecisionAndRecall(int[] paraRecLists, int[] paraLikeLists){
		int tempSucRecLen = 0;
		int tempRecLen = 0;
		int tempLikeLen = 0;
		for(int i = 0; i < paraRecLists.length; i ++){	
			int[] tempInterSection = SetOperator.interSection(paraRecLists, paraLikeLists);
			
	//		System.out.println("User " + i + " recommend lists: ");
	//		SimpleTool.printIntArray(tempRecLists);
			if(tempInterSection != null){
				tempRecLen += paraRecLists.length;
				tempLikeLen += paraLikeLists.length;
				tempSucRecLen += tempInterSection.length;
			}//Of if
		}//Of for i
		
		if(tempRecLen > 1e-6){
			precision = (tempSucRecLen + 0.0) / tempRecLen;
		}//Of if
		
		if(tempLikeLen > 1e-6){
			recall = (tempSucRecLen + 0.0) / tempLikeLen;
		}//Of if
	}//Of computePrecisionAndRecall
	
	/**
	 * 
	 * @param paraRecThreshold
	 * @param paraLikeThreshold
	 * @return
	 */

	void computePrecisionAndRecall2(int[] paraRecLists, int[] paraLikeLists){
		int tempSucRecLen = 0;
		int tempRecLen = 0;
		int tempLikeLen = 0;
		for(int i = 0; i < paraRecLists.length; i ++){
			int[] tempInterSection = SetOperator.interSection(paraRecLists, paraLikeLists);
			
	//		System.out.println("User " + i + " recommend lists: ");
	//		SimpleTool.printIntArray(tempRecLists);
			if(tempInterSection != null){
				tempSucRecLen = tempInterSection.length;
				tempRecLen = paraRecLists.length;
				tempLikeLen = paraLikeLists.length;
			}//Of if
			
			if(tempRecLen > 1e-6){
				precision += (tempSucRecLen + 0.0)/ tempRecLen;
			}//Of if
			if(tempLikeLen > 1e-6){
				recall += (tempSucRecLen + 0.0) / tempLikeLen;
			}//Of if
		}//Of for i
		
		precision /= dataModel.userNum;
		recall /= dataModel.userNum;
	}//Of computePrecisionAndRecall2
	
	/**
	 * 
	 * @param paraRecThreshold
	 * @param paraLikeThreshold
	 * @return
	 */

	double computeFMeasure(){		
		fMeasure = (2 * recall * precision) / (recall + precision);
		 
		return fMeasure;
		
	}//Of computeFMeasure
}//Of class Metrics
