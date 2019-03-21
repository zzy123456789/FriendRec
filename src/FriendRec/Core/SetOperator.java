package FriendRec.Core;

public class SetOperator {
	/**
	 * 
	 * @param paraIndices
	 * @param paraFocus
	 * @return
	 */
	public static int findIndex(int[] paraIndices, int paraFocus){
		for(int i = 0; i < paraIndices.length; i ++){
			if(paraIndices[i] == paraFocus){
				return i;// return the position
			}//Of if
		}//Of for i
		
		return -1;
	}//Of for i
	
	
	/**
	 * 
	 * @param paraFriends: my friends
	 * @param paraUsers: the users for the item
	 * @return the intersection of my friends and users for the item.
	 */
	public static int[] interSection(int[] paraFriends, int[] paraUsers) {
		//Step 1. Define the moving address
		int i = 0;
		int j = 0;
		
		//Step 2. Define the temporary intersection
		if(paraFriends == null || paraUsers == null){
			return null;
		}//Of if
		int[] tempSubSet = new int[paraFriends.length];
		int tempCount = 0;
		
		//Step 3. Find the intersection
		while(i < paraFriends.length && j < paraUsers.length){
			if(paraFriends[i] < paraUsers[j]){
				i ++;
			}else if(paraFriends[i] > paraUsers[j]){
				j++;
			}else{
				//The subset saves the indices of the users who rated the item.
				tempSubSet[tempCount] = j;
				tempCount ++;
				
				i ++;
				j ++;
			}//Of if
		}//Of while
		
		//Step 4. Compress
		if(tempCount == 0){
			return null;
		}//Of if
		int[] tempInterSection = new int[tempCount];
		for(int k = 0; k < tempCount; k ++){
			tempInterSection[k] = tempSubSet[k];
		}//Of for k
		
		return tempInterSection;
	}//Of interSectionbyOrder
	/**
	 * 
	 * @param paraFriends: my friends
	 * @param paraUsers: the users for the item
	 * @return the intersection of my friends and users for the item.
	 */
//	public static int[] interSection(int[] paraFriends, int[] paraUsers) {
//		//Step 1. Define the temporary intersection
//		if(paraFriends == null || paraUsers == null){
//			return null;
//		}//Of if
//		int[] tempSubSet = new int[paraFriends.length];
//		int tempCount = 0;
//		int tempUser = -1;
//		
//		//Step 2. Find the intersection
//		for (int i = 0; i < paraFriends.length; i++) {
//			tempUser = paraFriends[i];
//			for (int j = 0; j < paraUsers.length; j++) {
//				if(tempUser == paraUsers[j]) {
//					tempSubSet[i] = tempUser;
//					tempCount ++;
//				}//Of if
//			}//Of for j
//		}//Of for i
//		
//		//Step 3. Compress
//		if(tempCount == 0){
//			return null;
//		}//Of if
//		int[] tempInterSection = new int[tempCount];
//		for(int k = 0; k < tempCount; k ++){
//			tempInterSection[k] = tempSubSet[k];
//		}//Of for k
//		
//		return tempInterSection;
//	}//Of interSection
	
	/**
	 * 
	 * @param paraFriends: my friends
	 * @param paraUsers: the users for the item
	 * @return the intersection of my friends and users for the item.
	 */
	public static int[] unionSection(int[] paraFriends, int[] paraUsers) {
		//Step 1. Define the moving address
		int i = 0;
		int j = 0;
		
		//Step 2. Define the temporary intersection
		if(paraFriends == null || paraUsers == null){
			return null;
		}//Of if
		int[] tempSubSet = new int[paraFriends.length + paraUsers.length];
		int tempCount = 0;
		
		//Step 3. Find the intersection
		while(i < paraFriends.length || j < paraUsers.length){
			if(paraFriends[i] < paraUsers[j]){
				tempSubSet[tempCount] = i;
				tempCount ++;
				i ++;
			}else if(paraFriends[i] > paraUsers[j]){
				tempSubSet[tempCount] = j;
				tempCount ++;
				j++;
			}else{
				//The subset saves the indices of the users who rated the item.
				tempSubSet[tempCount] = j;
				tempCount ++;
				
				i ++;
				j ++;
			}//Of if
		}//Of while
		
		//Step 4. Compress
		if(tempCount == 0){
			return null;
		}//Of if
		int[] tempUnionSection = new int[tempCount];
		for(int k = 0; k < tempCount; k ++){
			tempUnionSection[k] = tempSubSet[k];
		}//Of for k
		
		return tempUnionSection;
	}//Of unionSection

	/**
	 * 
	 * @param paraFriends: my friends
	 * @param paraUsers: the users for the item
	 * @return the intersection of my friends and users for the item.
	 */
	public static int[] interSectionReturnFriend(int[] paraFriends, int[] paraUsers) {
		//Step 1. Define the moving address
		int i = 0;
		int j = 0;
		
		//Step 2. Define the temporary intersection
		if(paraFriends == null || paraUsers == null){
			return null;
		}//Of if
		int[] tempSubSet = new int[paraFriends.length];
		int tempCount = 0;
		
		//Step 3. Find the intersection
		while(i < paraFriends.length && j < paraUsers.length){
			if(paraFriends[i] < paraUsers[j]){
				i ++;
			}else if(paraFriends[i] > paraUsers[j]){
				j++;
			}else{
				//The subset saves the users who rated the item.
				tempSubSet[tempCount] = paraFriends[i];
				tempCount ++;
				
				i ++;
				j ++;
			}//Of if
		}//Of while
		
		//Step 4. Compress
		if(tempCount == 0){
			return null;
		}//Of if
		int[] tempInterSection = new int[tempCount];
		for(int k = 0; k < tempCount; k ++){
			tempInterSection[k] = tempSubSet[k];
		}//Of for k
		
		return tempInterSection;
	}//Of interSection
}//Of class SetOperator
