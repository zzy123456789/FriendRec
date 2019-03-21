/*
 * @(#)SimpleTool.java
 *
 */

package common;

import java.util.*;

/**
 * Frequently used methods to convert strings, integers, codings, etc.
 * <p>
 * Author: <b>Fan Min</b> minfanphd@163.com <br>
 * Copyright: The source code and all documents are open and free. PLEASE keep
 * this header while revising the program. <br>
 * Organization: <a href=http://grc.fjzs.edu.cn/>Lab of Granular Computing</a>,
 * Zhangzhou Normal University, Fujian 363000, China.<br>
 * Project: The granular association rules project.
 * <p>
 * Progress: OK. <br>
 * Written time: April 03, 2013. <br>
 * Last modify time: December 23, 2013.
 */
public class SimpleTool extends Object {
	/**
	 * Maximal length of the parsed array (for int and double).
	 */
	public static final int MAX_PARSE_ARRAY_LENGTH = 100;

	/**
	 * A global random object.
	 */
	public static final Random random = new Random();

	/**
	 ********************************** 
	 * Convert a double value into a shorter string.
	 * 
	 * @param paraDouble
	 *            the double value to be converted.
	 ********************************** 
	 */
	public static String shorterDouble(double paraDouble) {
		return shorterDouble(paraDouble, 7);
	}// Of shorterDouble

	/**
	 ********************************** 
	 * Convert a double value into a shorter string.
	 * 
	 * @param paraDouble
	 *            the double value to be converted.
	 * @param paraLength
	 *            the length of reserved double.
	 ********************************** 
	 */
	public static String shorterDouble(double paraDouble, int paraLength) {
		// double absValue = Math.abs(paraDouble);
		// double least = 0.001;
		// for (int i = 2; i < paraLength; i++)
		// least *= 0.1;
		// if (absValue < least)
		// return "0.0";

		String shorterString = new Double(paraDouble).toString();
		// For uncontrollable data, just output itself
		if ((paraDouble < 0.001) && (-0.001 < paraDouble)
				|| (paraDouble < -9999) || (9999 < paraDouble))
			return shorterString;

		if ((paraDouble > 0) && (shorterString.length() > paraLength))
			shorterString = shorterString.substring(0, paraLength);
		if ((paraDouble < 0) && (shorterString.length() > paraLength + 1))
			shorterString = shorterString.substring(0, paraLength + 1);

		return shorterString;
	}// Of shorterDouble

	/**
	 ********************************** 
	 * Convert a string with commas into a string array, blanks adjacent with
	 * commas are deleted. If the string bewteen two adjacent commas are blank
	 * or contains only space char ' ', then an exception will be thrown.<br>
	 * For example, "a, bc, def, g" will be converted into a string array with 4
	 * elements "a", "bc", "def" and "g".
	 * 
	 * @param prmString
	 *            The source string
	 * @return A string array.
	 * @throws Exception
	 *             Exception for illegal data.
	 * @see #stringArrayToString(java.lang.String[])
	 ********************************** 
	 */
	public static String[] stringToStringArray(String prmString)
			throws Exception {
		/*
		 * Convert this string into an array such that another method could be
		 * invoked.
		 */
		int tempCounter = 1;
		for (int i = 0; i < prmString.length(); i++) {
			if (prmString.charAt(i) == ',') {
				tempCounter++;
			}// Of if
		}// Of for i

		String[] theStringArray = new String[tempCounter];

		String remainString = new String(prmString) + ",";
		for (int i = 0; i < tempCounter; i++) {
			theStringArray[i] = remainString.substring(0,
					remainString.indexOf(",")).trim();
			if (theStringArray[i].equals("")) {
				throw new Exception(
						"Error occurred in common.SimpleTool.stringToStringArray()."
								+ "\n\tBlank attribute or data is not allowed as a data. "
								+ "\n\tThe string is:" + prmString);
			}// Of if
				// Common.println(theStringArray[i]);
			remainString = remainString
					.substring(remainString.indexOf(",") + 1);
			// Common.println("remainString: " + remainString);
		}// Of for i

		return theStringArray;
	}// Of stringToStringArray

	/**
	 ********************************** 
	 * Convert a string array into a string, elements are separated by commas.
	 * 
	 * @param prmStringArray
	 *            The source string array
	 * @return converted string.
	 * @see #stringToStringArray(java.lang.String)
	 ********************************** 
	 */
	public static String stringArrayToString(String[] prmStringArray) {
		String newString = "";
		for (int i = 0; i < prmStringArray.length; i++) {
			newString += new String(prmStringArray[i]) + ",";
		}// Of for

		// Delete the last comma
		newString = newString.substring(0, newString.length() - 1);

		return newString;
	}// Of stringArrayToString

	/**
	 ********************************** 
	 * Add single quotes for a string (may be an array, elements are separated
	 * by commas). This is needed in some SQL statements. For example, "ab,c,d"
	 * will be converted into "ab','c','d".
	 * 
	 * @param prmStringArray
	 *            The source string
	 * @return converted string.
	 ********************************** 
	 */
	public static String addSingleQuotes(String prmStringArray) {
		int tempIndexInString = prmStringArray.indexOf(",");
		while (tempIndexInString > -1) {
			prmStringArray = prmStringArray.substring(0, tempIndexInString)
					+ "\';\'"
					+ prmStringArray.substring(tempIndexInString + 1,
							prmStringArray.length());
			tempIndexInString = prmStringArray.indexOf(",");
		}// Of while

		prmStringArray = "\'" + prmStringArray.replace(';', ',') + "\'";

		return prmStringArray;
	}// Of addSingleQuotes

	/**
	 ********************************** 
	 * If the string quisi-array (elements are separated by commas) contains
	 * respective attribute.<br>
	 * For exampe "abc, de" contains "de", but it does not contain "d".
	 * 
	 * @param prmStringArray
	 *            The string quisi-array (elements are separated by commas).
	 * @param prmString
	 *            Respect string.
	 * @return If it is contained.
	 * @see #stringToStringArray(java.lang.String)
	 ********************************** 
	 */
	public static boolean stringArrayContainsString(String prmStringArray,
			String prmString) {
		String[] realStringArray = null;
		try {
			realStringArray = stringToStringArray(prmStringArray);
		} catch (Exception ee) {
			return false;
		}// Of try

		for (int i = 0; i < realStringArray.length; i++) {
			if (realStringArray[i].equals(prmString)) {
				return true;
			}// Of if
		}// Of for
		return false;
	}// Of stringArrayContainsString

	/**
	 ********************************** 
	 * Join two attribute strings, separated by a comma.<br>
	 * 
	 * @param prmFirstString
	 *            The first attribute string
	 * @param prmSecondString
	 *            The second attribute string
	 * @return concated string.
	 ********************************** 
	 */
	public static String joinString(String prmFirstString,
			String prmSecondString) {
		if (prmFirstString.equals(""))
			return prmSecondString + "";
		if (prmSecondString.equals(""))
			return prmFirstString + "";

		return prmFirstString + "," + prmSecondString;
	}// Of joinString

	/**
	 ********************************** 
	 * Convert a string with delimiters (such as commas or semi-commas) into a
	 * string array.<br>
	 * This method is more generalized than stringToStringArray because the
	 * latter only permits commas to be delimiters. For more detail please
	 * contact <A href="mailto:qiheliu@uestc.edu.cn">Liu Qihe</A>
	 * 
	 * @param prmString
	 *            The given string
	 * @param prmDelimiter
	 *            The given delimiter
	 * @param prmReturnTokens
	 *            Is the delimiter permitted after convertion.
	 * @return string array separated by commas
	 ********************************** 
	 */
	public static String[] parseString(String prmString, String prmDelimiter,
			boolean prmReturnTokens) {
		String[] returnString = null;
		StringTokenizer token;
		if (prmString != null) {
			token = new StringTokenizer(prmString.trim(), prmDelimiter,
					prmReturnTokens);
			returnString = new String[token.countTokens()];
			int i = 0;
			while (token.hasMoreTokens()) {
				returnString[i] = (String) token.nextToken();
				i++;
			}// Of while
		}// Of if

		return returnString;
	}// end of parseString

	/**
	 ********************************** 
	 * Remove string from a string array, and return a string. For more detail
	 * please contact <A href="mailto:qiheliu@uestc.edu.cn">Liu Qihe</A>
	 * 
	 * @param prmString
	 *            �ַ���
	 * @param index
	 *            ȥ������λ\uFFFD
	 * @return �ַ���������ֵ֮���ö��ŷָ�.
	 ***********************************/
	public static String generateString(String[] prmString, int index) {
		String result = "";
		for (int i = 0; i < prmString.length; i++) {
			if (i == index)
				continue;
			result += prmString[i] + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;

	}// of generateString

	/**
	 ********************************* 
	 * GB2312 to UNICODE. Use this one if Chinese characters is a mess.
	 * 
	 * @param paraString
	 *            a GB2312 string
	 * @return a UNICODE string.
	 * @see #UNICODEToGB2312(java.lang.String)
	 ********************************* 
	 */
	public static String GB2312ToUNICODE(String paraString) {
		char[] tempCharArray = paraString.toCharArray();
		int tempLength = tempCharArray.length;
		byte[] tempByteArray = new byte[tempLength];
		for (int i = 0; i < tempLength; i++) {
			tempByteArray[i] = (byte) tempCharArray[i];
		}// Of for.

		String returnString = new String(tempByteArray);
		return returnString;
	}// Of GB2312ToUNICODE.

	/**
	 ********************************* 
	 * UNICODE to GB2312. Use this one if Chinese characters is a mess.
	 * 
	 * @param paraString
	 *            a UNICODE string.
	 * @return a GB2312 string.
	 * @see #GB2312ToUNICODE(java.lang.String)
	 ********************************* 
	 */
	public static String UNICODEToGB2312(String paraString) {
		// Convert the string into a byte array.
		byte[] byteArray = paraString.getBytes();

		int arrayLength = byteArray.length;

		// Store converted char array.
		char[] charArray = new char[arrayLength];

		// Convert chars one by one.
		for (int i = 0; i < arrayLength; i++) {
			// Add an all 0 byte.
			charArray[i] = (char) byteArray[i];
		}// Of for.

		// Get a new string according to the converted array.
		String convertedString = new String(charArray);
		return convertedString;
	}// Of UNICODEToGB2312.

	/**
	 ********************************** 
	 * One string minus another, essentially corresponding string arrays.<br>
	 * E.g., "ab,cd,efa" minus "ab,efa" gets "cd"
	 * 
	 * @param prmFirstString
	 *            The first string.
	 * @param prmSecondString
	 *            The second string.
	 * @return A result string.
	 * @throws Exception
	 *             if the first string does not contain an element of the second
	 *             string, e.g., "ab, cd" minus "ab, de". #see
	 *             scheme.SymbolicPartition
	 *             .computeOptimalPartitionReduct(java.lang.String,
	 *             java.lang.String, int)
	 ********************************** 
	 */
	public static String stringMinusString(String prmFirstString,
			String prmSecondString) throws Exception {
		String[] firstArray = stringToStringArray(prmFirstString);
		String[] secondArray = stringToStringArray(prmSecondString);

		boolean[] includeArray = new boolean[firstArray.length];
		for (int i = 0; i < includeArray.length; i++) {
			includeArray[i] = true;
		}// Of for i

		// Given an element of secondArray, is there an identical element of
		// firstArray?
		boolean found = false;
		for (int i = 0; i < secondArray.length; i++) {
			found = false;
			for (int j = 0; j < firstArray.length; j++) {
				if (secondArray[i].equals(firstArray[j])) {
					includeArray[j] = false;
					found = true;
					break;
				}
			}// Of for j
			if (!found)
				throw new Exception(
						"Error occured in SimpleTool.stringMinusString(), \n"
								+ "\t" + secondArray[i] + "is not included in "
								+ prmFirstString);
		}// Of for i

		String returnString = "";
		for (int i = 0; i < includeArray.length; i++) {
			if (includeArray[i])
				returnString += firstArray[i] + ",";
		}// Of for i

		if (returnString.length() > 0)
			returnString = returnString.substring(0, returnString.length() - 1);
		return returnString;
	}// Of stringMinusString

	/**
	 ********************************** 
	 * One string union another, essentially corresponding string sets.<br>
	 * E.g., "ab,cd,efa" union "ab,ee" gets "ab,cd,efa, ee"
	 * 
	 * @param prmFirstString
	 *            The first string.
	 * @param prmSecondString
	 *            The second string.
	 * @return A result string. #see
	 *         scheme.Reduction.computeOptimalMReductByEntropy(java.lang.String,
	 *         java.lang.String, java.lang.String)
	 ********************************** 
	 */
	public static String stringUnionString(String prmFirstString,
			String prmSecondString) throws Exception {
		if (prmFirstString.equals(""))
			return prmSecondString + "";
		if (prmSecondString.equals(""))
			return prmFirstString + "";

		String[] firstArray = stringToStringArray(prmFirstString);
		String[] secondArray = stringToStringArray(prmSecondString);

		String unionString = new String(prmFirstString);

		boolean found = false;
		for (int i = 0; i < secondArray.length; i++) {
			found = false;
			for (int j = 0; j < firstArray.length; j++) {
				if (secondArray[i].equals(firstArray[j])) {
					found = true;
					break;
				}
			}// Of for j
			if (!found)
				if (unionString.equals(""))
					unionString += secondArray[i];
				else
					unionString += "," + secondArray[i];
		}// Of for i

		return unionString;
	}// Of stringUnionString

	/**
	 *************************** 
	 * Judge whether or not the elements of the given array are zero totally.
	 *************************** 
	 */
	public static boolean isZeroElementsOfArray(double[] paraArray) {
		if (paraArray == null) {
			System.out.println("The given array is null!");
			return true;
		}// of if
		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i] > 1E-6) {
				return false;
			}// of if
		}// of for i

		return true;
	}// Of isZeroElementsOfArray

	/**
	 *************************** 
	 * Judge whether or not the given string is null/empty.
	 *************************** 
	 */
	public static boolean isEmptyStr(String paraString) {
		if (paraString == null)
			return true;
		if (paraString.equals(""))
			return true;
		return false;
	}// Of isEmptyStr

	/**
	 *************************** 
	 * Judge whether or not the given string is specified.
	 *************************** 
	 */
	public static boolean isUnspecifiedStr(String paraString) {
		if (isEmptyStr(paraString))
			return true;
		if (paraString.equals(Common.unspecifiedString))
			return true;
		return false;
	}// Of isEmptyStr

	/**
	 *************************** 
	 * Read an integer array from a given string. Integers are separated by
	 * separators. Author Fan Min.
	 * 
	 * @param paraString
	 *            The given string.
	 * @param paraNumberOfInts
	 *            The number of integer to read from the string. If there are
	 *            more integers, just ignore them.
	 * @param paraSeparator
	 *            The separator of data, blank and commas are most commonly uses
	 *            ones.
	 * @return The constructed array.
	 *************************** 
	 */
	public static int[] parseIntArray(String paraString, int paraNumberOfInts,
			char paraSeparator) throws Exception {
		int[] returnArray = new int[paraNumberOfInts];
		String currentString = null;
		String remainingString = new String(paraString);
		try {
			for (int i = 0; i < paraNumberOfInts - 1; i++) {
				currentString = remainingString.substring(0,
						remainingString.indexOf(paraSeparator)).trim();
				returnArray[i] = Integer.parseInt(currentString);
				remainingString = remainingString.substring(
						remainingString.indexOf(paraSeparator) + 1).trim();
			}// Of for i

			// The last one may have no blank after it.
			if (remainingString.indexOf(paraSeparator) < 0)
				currentString = remainingString;
			else
				currentString = remainingString.substring(0,
						remainingString.indexOf(paraSeparator));

			returnArray[paraNumberOfInts - 1] = Integer.parseInt(currentString);
		} catch (java.lang.StringIndexOutOfBoundsException sie) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseIntArray.\r\n"
							+ "May caused by the number of int value required exceeds those in the string.\r\n"
							+ sie);
		} catch (java.lang.NumberFormatException nfe) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseIntArray.\r\n"
							+ "May caused by incorrect separator (e.g., comma, blank).\r\n"
							+ nfe);
		} catch (Exception ee) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseIntArray.\r\n"
							+ ee);
		}

		return returnArray;
	}// Of parseIntArray

	/**
	 *************************** 
	 * An int to an attribute subset. Author Fan Min.
	 * 
	 * @param paraInt
	 *            The given integer representing an attribute subset.
	 * @return The attribute subset in a string.
	 *************************** 
	 */
	public static String intToAttributeSetString(int paraInt) {
		String resultString = "";
		if (paraInt < 1) {
			return resultString; // No need to throw an exception.
		}

		int currentIndex = 0;
		int tempInt = paraInt;
		while (tempInt > 0) {
			if (tempInt % 2 == 1) {
				resultString += currentIndex + ",";
			}

			tempInt /= 2;
			currentIndex++;
		}// Of while

		resultString = resultString.substring(0, resultString.length() - 1);
		return resultString;
	}// Of intToAttributeSetString

	/**
	 *************************** 
	 * An int array to an attribute subset. Author Fan Min.
	 * 
	 * @param paraIntArray
	 *            The given integer array an attribute subset.
	 * @param paraLength
	 *            The size of the subset.
	 * @return The attribute subset in a string.
	 *************************** 
	 */
	public static String intArrayToAttributeSetString(int[] paraIntArray,
			int paraLength) {
		String resultString = "";
		if (paraLength < 1) {
			return resultString; // No need to throw an exception.
		}
		for (int i = 0; i < paraLength; i++) {
			resultString += paraIntArray[i] + ",";

		}// Of for i
		resultString = resultString.substring(0, resultString.length() - 1);
		return resultString;
	}// Of intArrayToAttributeSetString

	/**
	 *************************** 
	 * An int array to an int value. For example, [0, 2] will be binary 101 = 5.
	 * 
	 * @param paraIntArray
	 *            The given integer array indicating which positions is 1.
	 * @return An int value.
	 *************************** 
	 */
	public static int intArrayToInt(int[] paraIntArray) {
		if (paraIntArray == null) {
			return 0;
		}

		int tempValue = 0;
		for (int i = 0; i < paraIntArray.length; i++) {
			tempValue += (int) Math.pow(2, paraIntArray[i]);
		}
		return tempValue;
	}// Of intArrayToInt

	/**
	 *************************** 
	 * An int array to an long value. For example, [0, 2] will be binary 101 =
	 * 5.
	 * 
	 * @param paraIntArray
	 *            The given integer representing an attribute subset.
	 * @return An int value.
	 * @see #intArrayToInt(int[])
	 *************************** 
	 */
	public static long intArrayToLong(int[] paraIntArray) {
		if (paraIntArray == null) {
			return 0;
		}

		long tempValue = 0;
		for (int i = 0; i < paraIntArray.length; i++) {
			tempValue += (long) Math.pow(2, paraIntArray[i]);
		}
		return tempValue;
	}// Of intArrayToLong

	/**
	 *************************** 
	 * A long to an attribute subset.
	 * 
	 * @param paraLong
	 *            The given integer representing an attribute subset.
	 * @return The attribute subset in a string.
	 *************************** 
	 */
	public static String longToAttributeSetString(long paraLong) {
		String resultString = "";
		if (paraLong < 1) {
			return resultString; // No need to throw an exception.
		}

		int currentIndex = 0;
		long tempLong = paraLong;
		while (tempLong > 0) {
			if (tempLong % 2 == 1) {
				resultString += currentIndex + ",";
			}

			tempLong /= 2;
			currentIndex++;
		}// Of while

		resultString = resultString.substring(0, resultString.length() - 1);
		return resultString;
	}// Of longToAttributeSetString

	/**
	 *************************** 
	 * Convert an integer array into a string. Integers are separated by
	 * separators. Author Fan Min.
	 * 
	 * @param paraArray
	 *            The given array.
	 * @param paraSeparator
	 *            The separator of data, blank and commas are most commonly uses
	 *            ones.
	 * @return The constructed String.
	 *************************** 
	 */
	public static String arrayToString(int[] paraArray, char paraSeparator)
			throws Exception {
		String returnString = "[]";
		if ((paraArray == null) || (paraArray.length < 1))
			return returnString;
		// throw new Exception(
		// "Error occurred in common.SimpleTool. Cannot convert an empty array into a string.");
		returnString = "";
		for (int i = 0; i < paraArray.length - 1; i++) {
			returnString += "" + paraArray[i] + paraSeparator;
		}// Of for i
		returnString += paraArray[paraArray.length - 1];
		returnString = "[" + returnString + "]";

		return returnString;
	}// Of arrayToString

	/**
	 *************************** 
	 * Read a double array from a given string. Double values are separated by
	 * separators. <br>
	 * Author Fan Min.
	 * 
	 * @param paraString
	 *            The given string.
	 * @param paraNumberOfDoubles
	 *            The number of integer to read from the string. If there are
	 *            more integers, just ignore them.
	 * @param paraSeparator
	 *            The separator of data, blank and commas are most commonly uses
	 *            ones.
	 * @return The constructed array.
	 *************************** 
	 */
	public static double[] parseDoubleArray(String paraString,
			int paraNumberOfDoubles, char paraSeparator) throws Exception {
		double[] returnArray = new double[paraNumberOfDoubles];
		String currentString = null;
		String remainingString = new String(paraString);

		try {
			for (int i = 0; i < paraNumberOfDoubles - 1; i++) {
				currentString = remainingString.substring(0,
						remainingString.indexOf(paraSeparator)).trim();
				returnArray[i] = Double.parseDouble(currentString);
				remainingString = remainingString.substring(
						remainingString.indexOf(paraSeparator) + 1).trim();
			}// Of for i

			// The last one may have no blank after it.
			if (remainingString.indexOf(paraSeparator) < 0)
				currentString = remainingString;
			else
				currentString = remainingString.substring(0,
						remainingString.indexOf(paraSeparator));

			returnArray[paraNumberOfDoubles - 1] = Double
					.parseDouble(currentString);
		} catch (java.lang.StringIndexOutOfBoundsException sie) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseDoubleArray.\r\n"
							+ "May caused by the number of double value required exceeds those in the string,\r\n"
							+ "or invalid separator.\r\n" + sie);
		} catch (java.lang.NumberFormatException nfe) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseDoubleArray.\r\n"
							+ "May caused by incorrect separator (e.g., comma, blank).\r\n"
							+ nfe);
		} catch (Exception ee) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseDoubleArray.\r\n"
							+ ee);
		}

		return returnArray;
	}// Of parseDoubleArray

	/**
	 *************************** 
	 * Read a double array from a given string. The number of doubles are not
	 * given, so I will parse as many double values as possible. The seperator
	 * is not indicated, so comma is the first candidate, and blank is the
	 * second. <br>
	 * Author Fan Min.
	 * 
	 * @param paraString
	 *            The given string.
	 * @return The constructed array.
	 *************************** 
	 */
	public static double[] parseDoubleArray(String paraString) throws Exception {
		char separator = ' ';
		if (paraString.indexOf(',') > 0)
			separator = ',';
		// String tempString = new String(paraString);

		double[] tempArray = new double[MAX_PARSE_ARRAY_LENGTH];
		int arrayLength = 0;

		String currentString = null;
		String remainingString = new String(paraString);

		try {
			while (remainingString.indexOf(separator) > 0) {
				currentString = remainingString.substring(0,
						remainingString.indexOf(separator)).trim();
				tempArray[arrayLength] = Double.parseDouble(currentString);
				arrayLength++;
				if (arrayLength >= MAX_PARSE_ARRAY_LENGTH)
					throw new Exception("The array length should not exceed "
							+ MAX_PARSE_ARRAY_LENGTH);
				remainingString = remainingString.substring(
						remainingString.indexOf(separator) + 1).trim();
			}// Of while

			tempArray[arrayLength] = Double.parseDouble(remainingString);
		} catch (java.lang.StringIndexOutOfBoundsException sie) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseDoubleArray.\r\n"
							+ "May caused by the number of double value required exceeds those in the string,\r\n"
							+ "or invalid separator.\r\n" + sie);
		} catch (java.lang.NumberFormatException nfe) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseDoubleArray.\r\n"
							+ "May caused by incorrect separator (e.g., comma, blank).\r\n"
							+ nfe);
		} catch (Exception ee) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseDoubleArray.\r\n"
							+ ee);
		}

		double[] returnArray = new double[arrayLength + 1];

		// The arrayLength is starting from 0
		for (int i = 0; i <= arrayLength; i++)
			returnArray[i] = tempArray[i];

		return returnArray;
	}// Of parseDoubleArray

	/**
	 *************************** 
	 * Conver a double matrix into a string. Doubles are separated by \t. Author
	 * Fan Min.
	 * 
	 * @param paraMatrix
	 *            The given matrix.
	 * @param paraLength
	 *            make the double shorter according to the length.
	 * @return The constructed String.
	 *************************** 
	 */
	public static String doubleMatrixToString(double[][] paraMatrix,
			int paraLength) throws Exception {
		String returnString = "";
		for (int i = 0; i < paraMatrix.length; i++) {
			returnString += doubleArrayToString(paraMatrix[i], '\t', paraLength)
					+ "\r\n";
		}// Of for i
		return returnString;
	}// Of doubleMatrixToString

	/**
	 *************************** 
	 * Conver a double array into a string. Doubles are separated by blanks.
	 * Author Fan Min.
	 * 
	 * @param paraArray
	 *            The given array.
	 * @return The constructed String.
	 *************************** 
	 */
	public static String doubleArrayToString(double[] paraArray)
			throws Exception {
		return doubleArrayToString(paraArray, ' ');
	}// Of doubleArrayToString

	/**
	 *************************** 
	 * Conver a double array into a string. Doubles are separated by separators.
	 * Author Fan Min.
	 * 
	 * @param paraArray
	 *            The given array.
	 * @param paraSeparator
	 *            The separator of data, blank and commas are most commonly uses
	 *            ones.
	 * @return The constructed String.
	 *************************** 
	 */
	public static String doubleArrayToString(double[] paraArray,
			char paraSeparator) throws Exception {
		if ((paraArray == null) || (paraArray.length < 1))
			throw new Exception(
					"Error occurred in common.SimpleTool. Cannot convert an empty array into a string.");
		String returnString = "";
		for (int i = 0; i < paraArray.length - 1; i++) {
			returnString += "" + paraArray[i] + paraSeparator;
		}// Of for i
		returnString += paraArray[paraArray.length - 1];

		return returnString;
	}// Of doubleArrayToString

	/**
	 *************************** 
	 * Conver a double array into a string. Doubles are separated by separators.
	 * Author Fan Min.
	 * 
	 * @param paraArray
	 *            The given array.
	 * @param paraSeparator
	 *            The separator of data, blank and commas are most commonly uses
	 *            ones.
	 * @param paraLength
	 *            make the double shorter according to the length.
	 * @return The constructed String.
	 *************************** 
	 */
	public static String doubleArrayToString(double[] paraArray,
			char paraSeparator, int paraLength) throws Exception {
		if ((paraArray == null) || (paraArray.length < 1))
			throw new Exception(
					"Error occurred in common.SimpleTool. Cannot convert an empty array into a string.");
		String returnString = "";
		for (int i = 0; i < paraArray.length - 1; i++) {
			returnString += "" + shorterDouble(paraArray[i], paraLength)
					+ paraSeparator;
		}// Of for i
		returnString += shorterDouble(paraArray[paraArray.length - 1],
				paraLength);

		return returnString;
	}// Of doubleArrayToString

	/**
	 *************************** 
	 * Convert a boolean array into a string. Booleans are separated by
	 * separators.
	 * 
	 * @param paraArray
	 *            The given array.
	 * @param paraSeparator
	 *            The separator of data, blank and commas are most commonly uses
	 *            ones.
	 * @return The constructed String.
	 *************************** 
	 */
	public static String booleanArrayToString(boolean[] paraArray,
			char paraSeparator) {
		if ((paraArray == null) || (paraArray.length < 1)) {
			return "[]";
		}// Of if

		String returnString = "[";
		for (int i = 0; i < paraArray.length; i++) {
			returnString += "" + paraArray[i] + paraSeparator;
		}// Of for i
		returnString = returnString.substring(0, returnString.length() - 1);
		returnString += "]";

		return returnString;
	}// Of booleanArrayToString

	/**
	 *************************** 
	 * Convert a string array into a boolean array. Booleans are separated by
	 * separators. Only support 0 and 1 in the string. TRUE and FALSE are not
	 * supported.
	 * 
	 * @param paraString
	 *            The given string.
	 * @param paraSeparator
	 *            The separator of data, blank and commas are most commonly uses
	 *            ones.
	 * @return The constructed String.
	 * @throws Exception
	 *             for wrong format.
	 *************************** 
	 */
	public static boolean[] stringToBooleanArray(String paraString,
			char paraSeparator) throws Exception {
		paraString.trim();
		if ((paraString == null) || (paraString.length() < 1)) {
			throw new Exception(
					"Error occurred in SimpleTool.stringToBooleanArray(String, char)."
							+ "\r\nThe given string is null.");
		}// Of if

		// Scan separators to find the size of the array
		int tempLength = 1;
		for (int i = 0; i < paraString.length(); i++) {
			if (paraString.charAt(i) == paraSeparator) {
				tempLength++;
			}// Of if
		}// Of for i

		// parse boolean values
		boolean[] resultArray = new boolean[tempLength];
		for (int i = 0; i < tempLength; i++) {
			if (paraString.charAt(i * 2) == '0') {
				resultArray[i] = false;
			} else if (paraString.charAt(i * 2) == '1') {
				resultArray[i] = true;
			} else {
				throw new Exception(
						"Error occurred in SimpleTool.stringToBooleanArray(String, char)."
								+ "\r\n Unsupported boolean value '"
								+ paraString.charAt(i * 2)
								+ "'. It should be 0 or 1.");
			}
		}// Of for i
		return resultArray;
	}// Of booleanArrayToString

	/**
	 *************************** 
	 * Conver a boolean array into a string. Booleans are separated by commas.
	 * 
	 * @param paraArray
	 *            The given array.
	 * @return The constructed String.
	 *************************** 
	 */
	public static String booleanArrayToAttributeSetString(boolean[] paraArray) {
		String returnString = "[";
		if ((paraArray == null) || (paraArray.length < 1)) {
			returnString += "]";
			return returnString;
		}// Of if

		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i]) {
				returnString += i + ",";
			}
		}// Of for i
		returnString = returnString.substring(0, returnString.length() - 1);
		returnString += "]";

		return returnString;
	}// Of booleanArrayToAttributeSetString

	/**
	 *************************** 
	 * Read a int array from a given string. The number of ints are not given,
	 * so I will parse as many int values as possible. The seperator is not
	 * indicated, so comma is the first candidate, and blank is the second. <br>
	 * Author Fan Min.
	 * 
	 * @param paraString
	 *            The given string.
	 * @return The constructed array.
	 *************************** 
	 */
	public static int[] parseIntArray(String paraString) throws Exception {
		char separator = ' ';
		String tempString = new String(paraString);
		String remainingString = new String(paraString);
		// System.out.println(tempString);

		if (tempString.lastIndexOf(":") != -1) {
			remainingString = tempString.substring(0,
					tempString.lastIndexOf(":"));
			// System.out.println(tempString.indexOf(":"));
		}
		if (paraString.indexOf(',') > 0)
			separator = ',';

		int[] tempArray = new int[MAX_PARSE_ARRAY_LENGTH];
		int arrayLength = 0;

		String currentString = null;
		// System.out.println("Test parseIntArray before");
		try {

			while (remainingString.indexOf(separator) > 0) {
				currentString = remainingString.substring(0,
						remainingString.indexOf(separator)).trim();
				tempArray[arrayLength] = Integer.parseInt(currentString);
				arrayLength++;
				if (arrayLength >= MAX_PARSE_ARRAY_LENGTH)
					throw new Exception("The array length should not exceed "
							+ MAX_PARSE_ARRAY_LENGTH);
				remainingString = remainingString.substring(
						remainingString.indexOf(separator) + 1).trim();
			}// Of while

			tempArray[arrayLength] = Integer.parseInt(remainingString);
		} catch (java.lang.StringIndexOutOfBoundsException sie) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseIntegerArray.\r\n"
							+ "May caused by the number of int value required exceeds those in the string,\r\n"
							+ "or invalid separator.\r\n" + sie);
		} catch (java.lang.NumberFormatException nfe) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseIntegerArray.\r\n"
							+ "May caused by incorrect separator (e.g., comma, blank).\r\n"
							+ nfe);
		} catch (Exception ee) {
			throw new Exception(
					"Error occurred in common.SimpleTool.parseIntegerArray.\r\n"
							+ ee);
		}

		int[] returnArray = new int[arrayLength + 1];

		// The arrayLength is starting from 0
		for (int i = 0; i <= arrayLength; i++)
			returnArray[i] = tempArray[i];
		// System.out.println("Test parseIntArray after");
		return returnArray;
	}// Of parseIntArray

	/**
	 *************************** 
	 * Read a double value from a given string after the colon. <br>
	 * 
	 * @param paraString
	 *            The given string.
	 * @return A double value.
	 *************************** 
	 */
	public static double parseDoubleValueAfterColon(String paraString)
			throws Exception {
		// char separator = ' ';
		double tempValue = 0;
		String tempString = new String(paraString);
		String currentString = null;
		// String remainingString = new String(paraString);
		currentString = tempString.substring(tempString.indexOf(":") + 1)
				.trim();
		tempValue = Double.parseDouble(currentString);
		return tempValue;
	}// Of parseDoubleValueAfterColon

	/**
	 *************************** 
	 * Read an int value from a given string after the colon. <br>
	 * 
	 * @param paraString
	 *            The given string.
	 * @return A double value.
	 *************************** 
	 */
	public static int parseIntValueAfterColon(String paraString)
			throws Exception {
		// char separator = ' ';
		int tempValue = 0;
		String tempString = new String(paraString);
		String currentString = null;
		// String remainingString = new String(paraString);
		currentString = tempString.substring(tempString.indexOf(":") + 1)
				.trim();
		tempValue = Integer.parseInt(currentString);
		return tempValue;
	}// Of parseIntValueAfterColon

	/**
	 *************************** 
	 * Bubble sort. <br>
	 * ��С��������
	 * 
	 * @param paraArray
	 *            The given array.
	 * @author Zhanghr 2014/06/14
	 * @return The constructed array.
	 *************************** 
	 */
	public static double[] bubbleSort(double[] paraArray) {
		int tempLength = paraArray.length;
		double tempValue = 0;
		for (int i = 0; i <= tempLength - 1; i++) {
			for (int j = tempLength - 1; j > i; j--) {
				if (paraArray[j] < paraArray[j - 1]) {
					tempValue = paraArray[j];
					paraArray[j] = paraArray[j - 1];
					paraArray[j - 1] = tempValue;
				}// of if
			}// of for j
		}// of for i
		return paraArray;
	}// of bubbleSort

	/**
	 *************************** 
	 * Bubble sort. <br>
	 * �Ӵ�С����
	 * 
	 * @param paraArray
	 *            The given array.
	 * @param paraItemsInices
	 *            The indices of items corresponding to paraArray
	 * 
	 * @author Zhanghr 2014/06/18
	 *************************** 
	 */
	public static void bubbleSortBasedOnIndices(double[] paraArray,
			int[] paraItemsInices) {
		int tempLength = paraArray.length;
		double tempValue = 0;
		int tempIndex = 0;
		// ���˶�Ӧ��ֵ���н����⣬����Ҫ������Ӧ����ĿID(�Ӵ�С����)
		for (int i = 0; i <= tempLength - 1; i++) {
			for (int j = tempLength - 1; j > i; j--) {
				if (paraArray[j] > paraArray[j - 1]) {
					tempValue = paraArray[j];
					tempIndex = paraItemsInices[j];
					paraArray[j] = paraArray[j - 1];
					paraItemsInices[j] = paraItemsInices[j - 1];
					paraArray[j - 1] = tempValue;
					paraItemsInices[j - 1] = tempIndex;
				}// of if
			}// of for j
		}// of for i
			// return paraUnbrowsedItems;
	}// of overload bubbleSortBasedOnIndices

	/**
	 *************************** 
	 * Compress an int array so that no duplicate elements, no redundant elemnts
	 * exist, and it is in an ascendent order. <br>
	 * 
	 * @param paraIntArray
	 *            The given int array.
	 * @param paraLength
	 *            The effective length of the given int array.
	 * @return The constructed array.
	 *************************** 
	 */
	public static int[] compressAndSortIntArray(int[] paraIntArray,
			int paraLength) {
		int[] noDuplicateArray = new int[paraLength];
		int realLength = 0;
		int currentLeast = 0;
		int currentLeastIndex = 0;
		for (int i = 0; i < paraLength; i++) {
			if (paraIntArray[i] == Integer.MAX_VALUE) {
				continue;
			}//Of if

			currentLeast = paraIntArray[i];
			currentLeastIndex = i;

			for (int j = i + 1; j < paraLength; j++) {
				if (paraIntArray[j] < currentLeast) {
					currentLeast = paraIntArray[j];
					currentLeastIndex = j;
				}// Of if
			}// Of for j

			// Swap. The element of [i] should be stored in another place.
			paraIntArray[currentLeastIndex] = paraIntArray[i];

			noDuplicateArray[realLength] = currentLeast;
			realLength++;

			// Don't process this data any more.
			for (int j = i + 1; j < paraLength; j++) {
				if (paraIntArray[j] == currentLeast) {
					paraIntArray[j] = Integer.MAX_VALUE;
				}// Of if
			}// Of for j
		}// Of for i

		int[] compressedArray = new int[realLength];
		for (int i = 0; i < realLength; i++) {
			compressedArray[i] = noDuplicateArray[i];
		}// Of for i

		return compressedArray;
	}// Of compressAndSortIntArray

	/**
	 *************************** 
	 * Compress a long array so that no duplicate elements, no redundant elemnts
	 * exist, and it is in an ascendent order. <br>
	 * 
	 * @param paraLongArray
	 *            The given long array.
	 * @param paraLength
	 *            The effecitive length of the given long array.
	 * @return The constructed array.
	 *************************** 
	 */
	public static long[] compressAndSortLongArray(long[] paraLongArray,
			int paraLength) {
		long[] noDuplicateArray = new long[paraLength];
		int realLength = 0;
		long currentLeast = 0;
		int currentLeastIndex = 0;
		for (int i = 0; i < paraLength; i++) {
			if (paraLongArray[i] == Long.MAX_VALUE) {
				continue;
			}

			currentLeast = paraLongArray[i];
			currentLeastIndex = i;

			for (int j = i + 1; j < paraLength; j++) {
				if (paraLongArray[j] < currentLeast) {
					currentLeast = paraLongArray[j];
					currentLeastIndex = j;
				}// Of if
			}// Of for j

			// Swap. The element of [i] should be stored in another place.
			paraLongArray[currentLeastIndex] = paraLongArray[i];

			noDuplicateArray[realLength] = currentLeast;
			realLength++;

			// Don't process this data any more.
			for (int j = i + 1; j < paraLength; j++) {
				if (paraLongArray[j] == currentLeast) {
					paraLongArray[j] = Long.MAX_VALUE;
				}// Of if
			}// Of for j
		}// Of for i

		long[] compressedLongArray = new long[realLength];
		for (int i = 0; i < realLength; i++) {
			compressedLongArray[i] = noDuplicateArray[i];
		}// Of for i

		return compressedLongArray;
	}// Of compressAndSortLongArray

	/**
	 ********************************** 
	 * Subreduct sort according to respective measure.
	 * 
	 * @param paraData
	 *            The data, it may represent a subreduct
	 * @param paraMeasuredValues
	 *            The measured values of the data
	 * @param paraLeft
	 *            The left index.
	 * @param paraRight
	 *            The right index.
	 ********************************** 
	 */
	public static void measureBasedQuickSort(long[] paraData,
			int[] paraMeasuredValues, int paraLeft, int paraRight)
			throws Exception {
		int pivotLoc = 0;
		if (paraLeft < paraRight) {
			pivotLoc = valueBasedLongArrayPartition(paraData,
					paraMeasuredValues, paraLeft, paraRight);
			measureBasedQuickSort(paraData, paraMeasuredValues, paraLeft,
					pivotLoc - 1);// For the left
			measureBasedQuickSort(paraData, paraMeasuredValues, pivotLoc + 1,
					paraRight);// For the right
		}// Of if
	}// Of measureBasedQuickSort

	/**
	 ********************************** 
	 * Invoked only by measureBasedQuickSort.
	 * 
	 * @see #measureBasedQuickSort(long[], double[], int, int)
	 ********************************** 
	 */
	private static int valueBasedLongArrayPartition(long[] paraData,
			int[] paraMeasuredValues, int paraLeft, int paraRight)
			throws Exception {
		double key = paraMeasuredValues[paraLeft];
		int i = paraLeft;
		int j = paraRight + 1;

		while (true) {
			while (paraMeasuredValues[++i] < key && i < paraRight)
				;
			while (paraMeasuredValues[--j] > key)
				;
			if (i >= j)
				break;
			swap(paraMeasuredValues, i, j);
			swap(paraData, i, j);
		}// Of while

		swap(paraMeasuredValues, paraLeft, j);
		swap(paraData, paraLeft, j);
		return j;
	}// Of valueBasedLongArrayPartition

	/**
	 *************************** 
	 * Adjust a long array length.
	 * 
	 * @param paraLongArray
	 *            The given long array.
	 * @param length
	 *            The real length of the given long array.
	 * @return An identical long array.
	 *************************** 
	 */
	public static long[] adjustLongArrayLength(long[] paraLongArray, int length) {
		long[] arrayAim = new long[length];
		for (int i = 0; i < length; i++) {
			arrayAim[i] = paraLongArray[i];
		}// Of for i
		return arrayAim;
	}// Of adjustArrayLength

	/**
	 *************************** 
	 * Copy boolean array
	 * 
	 * @param paramBooleanArray
	 *            The given boolean array.
	 * @return An identical boolean array.
	 *************************** 
	 */
	public static boolean[] copyBooleanArray(boolean[] paramBooleanArray) {
		boolean[] newBooleanArray = new boolean[paramBooleanArray.length];
		for (int i = 0; i < paramBooleanArray.length; i++) {
			newBooleanArray[i] = paramBooleanArray[i];
		}// Of for
		return newBooleanArray;
	}// Of copyBooleanArray

	/**
	 *************************** 
	 * Copy double array
	 * 
	 * @param paraArray
	 *            The given double array.
	 * @return An identical double array.
	 *************************** 
	 */
	public static double[] copyDoubleArray(double[] paraArray) {
		double[] tempArray = new double[paraArray.length];
		for (int i = 0; i < paraArray.length; i++) {
			tempArray[i] = paraArray[i];
		}// Of for
		return tempArray;
	}// Of copyArray

	/**
	 *************************** 
	 * Copy int array
	 * 
	 * @param paraArray
	 *            The given int array.
	 * @return An identical int array.
	 *************************** 
	 */
	public static int[] copyArray(int[] paraArray) {
		int[] tempArray = new int[paraArray.length];
		for (int i = 0; i < paraArray.length; i++) {
			tempArray[i] = paraArray[i];
		}// Of for
		return tempArray;
	}// Of copyArray

	/**
	 *************************** 
	 * Copy int matrix
	 * 
	 * @param paraIntMatrx
	 *            The given int matrix.
	 * @return An identical int matrix.
	 *************************** 
	 */
	public static int[][] copyIntMatrix(int[][] paraIntMatrix) {
		int[][] newIntMatrix = new int[paraIntMatrix.length][paraIntMatrix[0].length];
		for (int i = 0; i < paraIntMatrix.length; i++) {
			for (int j = 0; j < paraIntMatrix[0].length; j++) {
				newIntMatrix[i][j] = paraIntMatrix[i][j];
			}// Of for j
		}// Of for
		return newIntMatrix;
	}// Of copyIntMatrix

	/**
	 *************************** 
	 * Copy double matrix
	 * 
	 * @param paraDoubleMatrx
	 *            The given double matrix.
	 * @return An identical double matrix.
	 * 
	 * @author Hengru Zhang 2013/12/30
	 *************************** 
	 */
	public static double[][] copyDoubleMatrix(int[][] paraDoubleMatrix) {
		double[][] newIntMatrix = new double[paraDoubleMatrix.length][paraDoubleMatrix[0].length];
		for (int i = 0; i < paraDoubleMatrix.length; i++) {
			for (int j = 0; j < paraDoubleMatrix[0].length; j++) {
				newIntMatrix[i][j] = paraDoubleMatrix[i][j];
			}// Of for j
		}// Of for
		return newIntMatrix;
	}// Of copyDoubleMatrix

	/**
	 *************************** 
	 * Copy double matrix
	 * 
	 * @param paraDoubleMatrx
	 *            The given double matrix.
	 * @return An identical double matrix.
	 * 
	 * @author Hengru Zhang 2013/12/30
	 *************************** 
	 */
	public static double[][] copyDoubleMatrix(double[][] paraDoubleMatrix) {
		double[][] newIntMatrix = new double[paraDoubleMatrix.length][paraDoubleMatrix[0].length];
		for (int i = 0; i < paraDoubleMatrix.length; i++) {
			for (int j = 0; j < paraDoubleMatrix[0].length; j++) {
				newIntMatrix[i][j] = paraDoubleMatrix[i][j];
			}// Of for j
		}// Of for
		return newIntMatrix;
	}// Of copyIntMatrix

	/**
	 *************************** 
	 * Transpose boolean matrix
	 * 
	 * @param paraMatrx
	 *            The given matrix.
	 * @return A reverted matrix.
	 *************************** 
	 */
	public static boolean[][] transposeMatrix(boolean[][] paraMatrix) {
		boolean[][] newMatrix = new boolean[paraMatrix[0].length][paraMatrix.length];
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				newMatrix[j][i] = paraMatrix[i][j];
			}// Of for j
		}// Of for
		return newMatrix;
	}// Of transposeMatrix

	/**
	 *************************** 
	 * Transpose int matrix
	 * 
	 * @param paraMatrx
	 *            The given matrix.
	 * @return A reverted matrix.
	 *************************** 
	 */
	public static int[][] transposeMatrix(int[][] paraMatrix) {
		int[][] newMatrix = new int[paraMatrix[0].length][paraMatrix.length];
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				newMatrix[j][i] = paraMatrix[i][j];
			}// Of for j
		}// Of for
		return newMatrix;
	}// Of transposeMatrix
	
	/**
	 *************************** 
	 * Transpose int matrix
	 * 
	 * @param paraMatrx
	 *            The given matrix.
	 * @return A reverted matrix.
	 *************************** 
	 */
	public static float[][] transposeMatrix(float[][] paraMatrix) {
		float[][] newMatrix = new float[paraMatrix[0].length][paraMatrix.length];
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				newMatrix[j][i] = paraMatrix[i][j];
			}// Of for j
		}// Of for
		return newMatrix;
	}// Of transposeMatrix

	/**
	 *************************** 
	 * Transpose int matrix
	 * 
	 * @param paraMatrx
	 *            The given matrix.
	 * @return A reverted matrix.
	 *************************** 
	 */
	public static double[][] transposeMatrix(double[][] paraMatrix) {
		double[][] newMatrix = new double[paraMatrix[0].length][paraMatrix.length];
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				newMatrix[j][i] = paraMatrix[i][j];
			}// Of for j
		}// Of for
		return newMatrix;
	}// Of transposeMatrix

	/**
	 *************************** 
	 * Transpose the matrix
	 * 
	 * @param paraBooleanMatrx
	 *            The given boolean matrix.
	 * @return An identical boolean matrix.
	 *************************** 
	 */
	public static boolean[][] copyBooleanMatrix(boolean[][] paraMatrix) {
		boolean[][] newMatrix = new boolean[paraMatrix.length][paraMatrix[0].length];
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				newMatrix[i][j] = paraMatrix[i][j];
			}// Of for j
		}// Of for
		return newMatrix;
	}// Of copyBooleanMatrix

	/**
	 *************************** 
	 * The exponential of an int.
	 * 
	 * @param paraExponential
	 *            the exponential.
	 * @return An identical boolean array.
	 *************************** 
	 */
	public static int exponential(int paraExponential) {
		int results = 1;
		for (int i = 0; i < paraExponential; i++) {
			results *= 2;
		}// Of if
		return results;
	}// Of exponential

	/**
	 *************************** 
	 * The exponential of an int.
	 * 
	 * @param paraExponential
	 *            the exponential.
	 * @return An identical boolean array.
	 *************************** 
	 */
	public static long exponentialLong(int paraExponential) {
		long results = 1;
		for (int i = 0; i < paraExponential; i++) {
			results *= 2;
		}// Of if
		return results;
	}// Of exponentialLong

	/**
	 *************************** 
	 * Who is who's child. Nodes are by integers, and attributes are indicated
	 * by bits.
	 * 
	 * @param paraBits
	 *            How many bits (attributes).
	 * @return An array of children.
	 *************************** 
	 */
	public static boolean[][] children(int paraBits) {
		int sizes = exponential(paraBits);

		boolean[][] children = new boolean[sizes][sizes];
		for (int i = 0; i < sizes; i++) {
			int j = i;
			int m = 1;
			while (j > 0) {
				if (j % 2 == 1) {
					children[i][i - m] = true;
				}// Of if
				j /= 2;
				m *= 2;
			}// Of while
		}// Of for

		return children;
	}// Of children

	/**
	 *************************** 
	 * Convert a long value to a integer array.
	 * 
	 * @param paraLong
	 *            The given long value.
	 * @param paraLength
	 *            The given length of the array.
	 * @return An integer array to indicate which positions (bits) are included.
	 *************************** 
	 */
	public static int[] longToIntArray(long paraLong, int paraLength) {
		int[] result = new int[paraLength];
		/*
		 * if (paraLong < 0) { return null; //No need to throw an exception. }
		 */
		int currentIndex = 0;
		long tempLong = paraLong;
		while (tempLong > 0) {
			if (tempLong % 2 == 1) {
				result[currentIndex] = 1;
			}

			tempLong /= 2;
			currentIndex++;
		}// Of while

		return result;
	}// Of longToIntArray

	/**
	 *************************** 
	 * Convert a long value to a boolean array.
	 * 
	 * @param paraLong
	 *            The given long value.
	 * @param paraLength
	 *            The given length of the array.
	 * @return A boolean array to indicate which positions (bits) are included.
	 *************************** 
	 */
	public static boolean[] longToBooleanArray(long paraLong, int paraLength) {
		long tempLong = paraLong;
		boolean[] returnArray = new boolean[paraLength];
		for (int i = 0; i < paraLength; i++) {
			if (tempLong % 2 == 1) {
				returnArray[i] = true;
			} else {
				returnArray[i] = false;
			}// Of if

			tempLong /= 2;
		}// Of for i

		return returnArray;
	}// Of longToBooleanArray

	/**
	 *************************** 
	 * Convert a boolean array to a long value.
	 * 
	 * @param paraBooleanArray
	 *            The given boolean array.
	 * @return A long to indicate which positions (bits) are included.
	 *************************** 
	 */
	public static long booleanArrayToLong(boolean[] paraBooleanArray)
			throws Exception {
		if (paraBooleanArray.length > 63) {
			throw new Exception(
					"Cannot support the array with length more than 63.");
		}// Of if
		long resultLong = 0;
		long currentPositionValue = 1;
		for (int i = 0; i < paraBooleanArray.length; i++) {
			if (paraBooleanArray[i]) {
				resultLong += currentPositionValue;
			}// Of if

			currentPositionValue *= 2;
		}// Of for i

		return resultLong;
	}// Of booleanArrayToLong

	/**
	 *************************** 
	 * Compute the attribute subset size.
	 * 
	 * @param paraLong
	 *            The given long value.
	 * @param paraLength
	 *            The size of all attributes.
	 * @return the size of the attribute subset.
	 *************************** 
	 */
	public static int attributeSubsetSize(long paraLong, int paraLength) {
		long tempLong = paraLong;
		int returnSize = 0;
		for (int i = 0; i < paraLength; i++) {
			if (tempLong % 2 == 1) {
				returnSize++;
			}// Of if

			tempLong /= 2;
		}// Of for i

		return returnSize;
	}// Of attributeSubsetSize

	/**
	 *************************** 
	 * Compute the attribute subset size.
	 * 
	 * @param paraBooleanArray
	 *            The given boolean array.
	 * @return the size of the attribute subset.
	 *************************** 
	 */
	public static int attributeSubsetSize(boolean[] paraBooleanArray) {
		int returnSize = 0;
		for (int i = 0; i < paraBooleanArray.length; i++) {
			if (paraBooleanArray[i]) {
				returnSize++;
			}// Of if
		}// Of for i

		return returnSize;
	}// Of attributeSubsetSize

	/**
	 *************************** 
	 * Convert an integer value to a boolean array.
	 * 
	 * @param paraInt
	 *            The given long value.
	 * @param paraLength
	 *            The given length of the array.
	 * @return A boolean array to indicate which positions (bits) are included.
	 *************************** 
	 */
	public static boolean[] intToBooleanArray(int paraInt, int paraLength) {
		long tempInt = paraInt;
		boolean[] returnArray = new boolean[paraLength];
		for (int i = 0; i < paraLength; i++) {
			if (tempInt % 2 == 1) {
				returnArray[i] = true;
			} else {
				returnArray[i] = false;
			}// Of if

			tempInt /= 2;
		}// Of for i

		return returnArray;
	}// Of intToBooleanArray

	/**
	 *************************** 
	 * Convert a boolean array to an int value.
	 * 
	 * @param paraBooleanArray
	 *            The given boolean array.
	 * @return An integer to indicate which positions (bits) are included.
	 *************************** 
	 */
	public static int booleanArrayToInt(boolean[] paraBooleanArray)
			throws Exception {
		if (paraBooleanArray.length > 31) {
			throw new Exception(
					"Cannot support the array with length more than 31.");
		}// Of if

		int resultInt = 0;
		int currentPositionValue = 1;
		for (int i = 0; i < paraBooleanArray.length; i++) {
			if (paraBooleanArray[i]) {
				resultInt += currentPositionValue;
			}// Of if

			currentPositionValue *= 2;
		}// Of for i

		return resultInt;
	}// Of booleanArrayToInt

	/**
	 *************************** 
	 * Print an int array, simply for test.
	 * 
	 * @param paraIntArray
	 *            The given int array.
	 *************************** 
	 */
	public static void printIntArray(int[] paraIntArray) {
		if (paraIntArray == null || paraIntArray.length == 0) {
			System.out.println("This is an empty int array.");
			return;
		} else {
			System.out.print("This is an int array: ");
		}
		for (int i = 0; i < paraIntArray.length; i++) {
			System.out.print("" + paraIntArray[i] + "\t");
		}// Of for i
		System.out.println();
	}// Of paraIntArray

	/**
	 *************************** 
	 * Print an int array, simply for test.
	 * 
	 * @param paraIntArray
	 *            The given int array.
	 *************************** 
	 */
	public static void printIntMatrix(int[][] paraIntMatrix) {
		if ((paraIntMatrix == null) || (paraIntMatrix.length == 0)) {
			System.out.println("This is an empty int matrix.");
			return;
		} else {
			System.out.print("This is an int matrix: ");
		}// Of if

		for (int i = 0; i < paraIntMatrix.length; i++) {
			// System.out.println("Length")
			for (int j = 0; j < paraIntMatrix[i].length; j++) {
				System.out.print("" + paraIntMatrix[i][j] + ", ");
			}// Of for j
			System.out.println();
		}// Of for i
		System.out.println();
	}// Of printIntMatrix

	/**
	 *************************** 
	 * Print an int matrix, simply for test.
	 * 
	 * @param paraMatrix
	 *            The given matrix. Different rows may contain different number
	 *            of values.
	 *************************** 
	 */
	public static void printMatrix(int[][] paraMatrix) {
		if (paraMatrix.length == 0) {
			System.out.println("This is an empty matrix.");
			return;
		} else {
			System.out.println("This is an int matrix: ");
		}// Of if

		for (int i = 0; i < paraMatrix.length; i++) {
			if (paraMatrix[i] == null) { // modified by zhr 2014/3/30
				continue;
			}// of if
			for (int j = 0; j < paraMatrix[i].length; j++) {
				System.out.print("" + paraMatrix[i][j] + ",");
			}// Of for j
			System.out.println();
		}// Of for i
	}// Of printMatrix

	/**
	 *************************** 
	 * Print an int matrix, simply for test.
	 * 
	 * @param paraMatrix
	 *            The given matrix. Different rows may contain different number
	 *            of values.
	 *************************** 
	 */
	public static void printMatrix(double[][] paraMatrix) {
		if (paraMatrix.length == 0) {
			System.out.println("This is an empty matrix.");
			return;
		} else {
			System.out.println("This is a matrix: ");
		}// Of if

		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[i].length; j++) {
				System.out.print("" + paraMatrix[i][j] + "\t");
			}// Of for j
			System.out.println();
		}// Of for i
	}// Of printMatrix

	/**
	 *************************** 
	 * Print an int matrix, simply for test.
	 * 
	 * @param paraMatrix
	 *            The given matrix. Different rows may contain different number
	 *            of values.
	 *************************** 
	 */
	public static void printMatrix(boolean[][] paraMatrix) {
		if (paraMatrix.length == 0) {
			System.out.println("This is an empty matrix.");
			return;
		} else {
			System.out.println("This is an boolean matrix: ");
		}// Of if

		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[i].length; j++) {
				System.out.print("" + paraMatrix[i][j] + "\t");
			}// Of for j
			System.out.println();
		}// Of for i
	}// Of printMatrix

	/**
	 *************************** 
	 * Print a long array, simply for test.
	 * 
	 * @param paraLongArray
	 *            The given long array.
	 *************************** 
	 */
	public static void printLongArray(long[] paraLongArray) {
		for (int i = 0; i < paraLongArray.length; i++) {
			System.out.print("" + paraLongArray[i] + "\t");
		}// Of for i
	}// Of printLongArray

	/**
	 *************************** 
	 * Print a long array, zero is not printed. Simply for test.
	 * 
	 * @param paraLongArray
	 *            The given long array.
	 *************************** 
	 */
	public static void printLongArrayNoZero(long[] paraLongArray) {
		for (int i = 0; i < paraLongArray.length; i++) {
			if (paraLongArray[i] == 0)
				continue;
			System.out.print("" + paraLongArray[i] + "\t");
		}// Of for i
	}// Of printLongArrayNoZero

	/**
	 ************************* 
	 * Print all reducts.
	 * 
	 * @param paraAllReducts
	 *            The given array to print.
	 ************************* 
	 */
	public static void printAllReducts(boolean[][] paraAllReducts) {
		for (int i = 0; i < paraAllReducts.length; i++) {
			System.out.println();
			for (int j = 0; j < paraAllReducts[0].length; j++) {
				if (paraAllReducts[i][j]) {
					System.out.print("" + 1 + ",");
				} else {
					System.out.print("" + 0 + ",");
				}
			}// Of for j
		}// Of for i
	}// Of printAllReducts

	/**
	 ************************* 
	 * Print a boolean array.
	 ************************* 
	 */
	public static void printBooleanArray(boolean[] paraBooleanArray) {
		for (int i = 0; i < paraBooleanArray.length; i++) {
			if (paraBooleanArray[i]) {
				System.out.print("" + 1 + ",");
			} else {
				System.out.print("" + 0 + ",");
			}

		}// Of for i
		System.out.println();
	}// Of printBooleanArray

	/**
	 ************************* 
	 * Print a boolean matrix.
	 ************************* 
	 */
	public static void printBooleanMatrix(boolean[][] paraBooleanMatrix) {
		for (int i = 0; i < paraBooleanMatrix.length; i++) {
			for (int j = 0; j < paraBooleanMatrix[i].length; j++) {
				if (paraBooleanMatrix[i][j]) {
					System.out.print("1,");
				} else {
					System.out.print("0,");
				}// Of if
			}// Of for j
			System.out.println();
		}// Of for i
		System.out.println();
	}// Of printBooleanMatrix
	
	/**
	 ************************* 
	 * Print a double array.
	 ************************* 
	 */
	public static void printFloatArray(float[] paraFloatArray) {
		for (int i = 0; i < paraFloatArray.length; i++) {
			System.out.print(paraFloatArray[i] + " ");
		}// Of for i
		System.out.println();
	}// Of printAllReducts

	/**
	 ************************* 
	 * Print a double array.
	 ************************* 
	 */
	public static void printDoubleArray(double[] paraDoubleArray) {
		for (int i = 0; i < paraDoubleArray.length; i++) {
			System.out.print(paraDoubleArray[i] + " ");
		}// Of for i
		System.out.println();
	}// Of printAllReducts

	/**
	 ********************************** 
	 * Swap two value in a double array.
	 * 
	 * @param paraDoubleArray
	 *            The given double array.
	 * @param src
	 *            The first index of the double array.
	 * @param dest
	 *            The second index of the double array.
	 ********************************** 
	 */
	public static void swap(double[] paraDoubleArray, int src, int dest)
			throws Exception {
		double tempDoubleArray = 0;
		tempDoubleArray = paraDoubleArray[src];
		paraDoubleArray[src] = paraDoubleArray[dest];
		paraDoubleArray[dest] = tempDoubleArray;
	}// Of swap

	/**
	 ********************************** 
	 * Swap two value in a long array.
	 * 
	 * @param paraLongArray
	 *            The given long array.
	 * @param src
	 *            The first index of the long array.
	 * @param dest
	 *            The second index of the long array.
	 ********************************** 
	 */
	public static void swap(long[] paraLongArray, int src, int dest)
			throws Exception {
		long tempIntArray = 0;
		tempIntArray = paraLongArray[src];
		paraLongArray[src] = paraLongArray[dest];
		paraLongArray[dest] = tempIntArray;
	}// Of swap

	/**
	 ********************************** 
	 * Swap two value in a long array.
	 * 
	 * @param paraLongArray
	 *            The given long array.
	 * @param src
	 *            The first index of the long array.
	 * @param dest
	 *            The second index of the long array.
	 ********************************** 
	 */
	public static void swap(int[] paraLongArray, int src, int dest)
			throws Exception {
		int tempIntArray = 0;
		tempIntArray = paraLongArray[src];
		paraLongArray[src] = paraLongArray[dest];
		paraLongArray[dest] = tempIntArray;
	}// Of swap

	/**
	 ********************************** 
	 * Long array to boolean matrix.
	 * 
	 * @param paraLongArray
	 *            The given long array.
	 * @param paraLength
	 *            The length of each long.
	 * @return the boolean matrix
	 ********************************** 
	 */
	public static boolean[][] longArrayToBooleanMatrix(long[] paraLongArray,
			int paraLength) {
		boolean[] availableAttribute = new boolean[paraLength];
		boolean[][] paraAllSubreducts = new boolean[paraLongArray.length][paraLength];

		for (int i = 0; i < paraLongArray.length; i++) {
			availableAttribute = longToBooleanArray(paraLongArray[i],
					paraLength);
			for (int j = 0; j < paraLength; j++) {
				if (availableAttribute[j]) {
					paraAllSubreducts[i][j] = true;
				}// Of if
			}// Of for j
		}// of for i
		return paraAllSubreducts;
	}// Of swap

	/**
	 * Write a message to a new file.
	 * 
	 * @paraFilename The given filename.
	 * @paraMessage The givean message string. public static void
	 *              writeFile(String paraFilename,String paraMessage) throws
	 *              Exception{
	 * 
	 *              File resultFile = new File(paraFilename); if
	 *              (resultFile.exists()) { resultFile.delete(); }
	 *              resultFile.createNewFile(); PrintWriter writer = new
	 *              PrintWriter(new FileOutputStream(resultFile));
	 *              writer.print(paraMessage); writer.flush(); writer.close();
	 *              }//Of writeFile
	 */

	/**
	 ********************************** 
	 * Is the first set a subset of the second one.
	 * 
	 * @param paraFirstSet
	 *            The first set in long.
	 * @param paraSecondSet
	 *            The second set in long.
	 * @param paraAttributes
	 *            Number of attributes.
	 * @return is the first set a subset of the second one?
	 ********************************** 
	 */
	public static boolean isSubset(long paraFirstSet, long paraSecondSet,
			int paraAttributes) throws Exception {
		boolean[] firstSetBooleanArray = longToBooleanArray(paraFirstSet,
				paraAttributes);
		boolean[] secondSetBooleanArray = longToBooleanArray(paraSecondSet,
				paraAttributes);
		return isSubset(firstSetBooleanArray, secondSetBooleanArray);
	}// Of isSubset

	/**
	 ********************************** 
	 * Is the first set a subset of the second one.
	 * 
	 * @param paraFirstSet
	 *            The first set in int array.
	 * @param paraSecondSet
	 *            The second set in int array.
	 * @return is the first set a subset of the second one?
	 ********************************** 
	 */
	public static boolean isSubset(int[] paraFirstSet, int[] paraSecondSet)
			throws Exception {
		Common.computationTime++;
		if ((paraFirstSet.length > paraSecondSet.length)
				|| (paraFirstSet[paraFirstSet.length - 1] > paraSecondSet[paraSecondSet.length - 1])
				|| paraSecondSet[paraSecondSet.length - 1] > 100) {
			return false;
		}// Of if

		int indexInTheFirstSet = 0;
		int indexInTheSecondSet = 0;
		while (indexInTheFirstSet < paraFirstSet.length) {
			Common.computationTime++;
			if (paraFirstSet[indexInTheFirstSet] > paraSecondSet[indexInTheSecondSet]) {
				indexInTheSecondSet++;
			} else if (paraFirstSet[indexInTheFirstSet] < paraSecondSet[indexInTheSecondSet]) {
				return false;
			} else {
				indexInTheFirstSet++;
				indexInTheSecondSet++;
			}// Of if
		}// Of while

		return true;
	}// Of isSubset

	/**
	 ********************************** 
	 * Is the first set a subset of the second one.
	 * 
	 * @param paraFirstSet
	 *            The first set in boolean array.
	 * @param paraSecondSet
	 *            The second set in boolean array.
	 * @return is the first set a subset of the second one?
	 ********************************** 
	 */
	public static boolean isSubset(boolean[] paraFirstSet,
			boolean[] paraSecondSet) throws Exception {
		if (paraFirstSet.length != paraFirstSet.length) {
			throw new Exception(
					"Error occurred in SimpleTool.isSubset(). Boolean arrays should"
							+ " have the same length");
		}// Of if

		for (int i = 0; i < paraFirstSet.length; i++) {
			if (paraFirstSet[i] && !paraSecondSet[i]) {
				return false;
			}// Of if
		}// Of for i
		return true;
	}// Of isSubset

	/**
	 ********************************** 
	 * Convert a long value to a bit string.
	 * 
	 * @param paraLong
	 *            The given long value.
	 * @return the bit string.
	 ********************************** 
	 */
	public static String longToBitString(long paraLong) {
		String returnString = "";
		while (paraLong > 0) {
			if (paraLong % 2 == 1) {
				returnString = "1" + returnString;
			} else {
				returnString = "0" + returnString;
			}// Of if
			paraLong /= 2;
		}// Of while
		return returnString;
	}// Of longToBitString

	/**
	 ********************************** 
	 * Return the size of the subset. It is the true values in the array.
	 * 
	 * @param paraSubset
	 *            A subset with the form of a boolean array.
	 * @return The size.
	 ********************************** 
	 */
	public static int getSubsetSize(boolean[] paraSubset) {
		int tempCounter = 0;
		for (int i = 0; i < paraSubset.length; i++) {
			if (paraSubset[i]) {
				tempCounter++;
			}
		}// Of if
		return tempCounter;
	}// Of getSubsetSize

	/**
	 ********************************** 
	 * Return the size of the subset. It is the true values in the array.
	 * 
	 * @param paraSubset
	 *            A subset with the form of a long.
	 * @return The size.
	 ********************************** 
	 */
	public static int getSubsetSize(long paraSubset) {
		long tempLong = paraSubset;
		int tempCounter = 0;
		while (tempLong > 0) {
			if (tempLong % 2 == 1) {
				tempCounter++;
			}// Of if

			tempLong /= 2;
		}// Of for i

		return tempCounter;
	}// Of getSubsetSize

	/**
	 *************************** 
	 * Check Who is who's subset.
	 * 
	 * @param paraFirstSet
	 *            the first set in long.
	 * @param paraSecondSet
	 *            the second set in long.
	 * @param paraNumberOfConditions
	 *            the number of conditions.
	 * @return '0' means no relationship, '1' means the second is the subset,
	 *         '2' means the first is the subset, '3' means equal.
	 *************************** 
	 */
	public static char subSetCheck(long paraFirstSet, long paraSecondSet,
			int paraNumberOfConditions) {
		boolean supportPositive = true;
		boolean supportNegative = true;

		int[] firstSubset = SimpleTool.longToIntArray(paraFirstSet,
				paraNumberOfConditions);
		int[] secondSubset = SimpleTool.longToIntArray(paraSecondSet,
				paraNumberOfConditions);

		for (int i = 0; i < paraNumberOfConditions; i++) {
			if (firstSubset[i] - secondSubset[i] > 0) {
				supportPositive = false;
			} else if (firstSubset[i] - secondSubset[i] < 0) {
				supportNegative = false;
			}// Of if
		}// Of for i

		if (!supportPositive && !supportNegative) {
			return '0'; // The two sets with not inclusion raletionship
		}// Of if
		if (!supportPositive) {
			return '1'; // The second set is the child of the first set.
		}// Of if
		if (!supportNegative) {
			return '2'; // The first set is the child of the second set.
		}// Of if

		return '3'; // The two sets is equal to each other.
	}// Of subSetCheck

	/**
	 ********************************** 
	 * Convert a boolean matrix to string
	 ********************************** 
	 */
	public static String matrixToString(boolean[][] tempMatrix) {
		String tempString = "";
		for (int i = 0; i < tempMatrix.length; i++) {
			for (int j = 0; j < tempMatrix[0].length; j++) {
				if (tempMatrix[i][j]) {
					tempString += " 1";
				} else {
					tempString += " 0";
				}// Of if
			}// Of for j
			tempString += "\r\n";
		}// Of for i
		return tempString;
	}// Of matrixToString

	/**
	 ********************************** 
	 * Convert an int matrix to string
	 ********************************** 
	 */
	public static String matrixToString(int[][] paraMatrix) {
		String tempString = "";
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				tempString += " " + paraMatrix[i][j];
			}// Of for j
			tempString += "\r\n";
		}// Of for i
		return tempString;
	}// Of matrixToString

	/**
	 ********************************** 
	 * Convert an double matrix to string
	 ********************************** 
	 */
	public static String matrixToString(double[][] paraMatrix) {
		String tempString = "";
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				tempString += " " + paraMatrix[i][j];
			}// Of for j
			tempString += "\r\n";
		}// Of for i
		return tempString;
	}// Of matrixToString

	/**
	 ********************************** 
	 * Convert an int matrix to string
	 ********************************** 
	 */
	public static String intMatrixToString(int[][] paraMatrix,
			char paraSeparator) {
		String tempString = "";
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				tempString += "" + paraMatrix[i][j] + paraSeparator;
			}// Of for j
			tempString += "\r\n";
		}// Of for i
		return tempString;
	}// Of intMatrixToString

	/**
	 ********************************** 
	 * Convert an int matrix to string
	 * 
	 * @author Hengru Zhang 2013/12/30
	 * @see intMatrixToString
	 ********************************** 
	 */
	public static String doubleMatrixToString(double[][] paraMatrix,
			char paraSeparator) {
		String tempString = "";
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				tempString += "" + paraMatrix[i][j] + paraSeparator;
			}// Of for j
			tempString += "\r\n";
		}// Of for i
		return tempString;
	}// Of doubleMatrixToString

	/**
	 ********************************** 
	 * Generate a boolean array to divide a dataset in two
	 * 
	 * @param paraDatasetSize
	 *            the dataset size
	 * @param paraPercentage
	 *            the percentage of the first subset
	 * @throws Exception
	 ********************************** 
	 */
	public static boolean[] generateBooleanArrayForDivision(
			int paraDatasetSize, double paraPercentage) throws Exception {
		double percentageLowerBound = 1.0 / paraDatasetSize;
		double percentageUpperBound = 1 - percentageLowerBound;
		if ((paraPercentage < percentageLowerBound)
				|| (paraPercentage > percentageUpperBound)) {
			throw new Exception(
					"Error occurred in SimpleTool.generateForPartitionInTwo().\r\n"
							+ "The valid bound of the percentage should be in ["
							+ percentageLowerBound + ", "
							+ percentageUpperBound + "].");
		}// Of if

		boolean[] tempBooleanArray = new boolean[paraDatasetSize];
		int firstSetSize = (int) (paraDatasetSize * paraPercentage);
		int secondSetSize = paraDatasetSize - firstSetSize;

		int firstSetCurrentSize = 0;
		int secondSetCurrentSize = 0;

		int tempInt;
		for (int i = 0; i < paraDatasetSize; i++) {
			tempInt = random.nextInt(paraDatasetSize);
			if (tempInt < firstSetSize) {
				tempBooleanArray[i] = true;
				firstSetCurrentSize++;
			} else {
				tempBooleanArray[i] = false;
				secondSetCurrentSize++;
			}// Of if

			// Keep the remaining part to false (the default value)
			if (firstSetCurrentSize >= firstSetSize) {
				break;
			}// Of if

			// Set the remaining part to true
			if (secondSetCurrentSize >= secondSetSize) {
				for (i++; i < paraDatasetSize; i++) {
					tempBooleanArray[i] = true;
				}// Of for i
				break;
			}// Of if
		}// Of for i

		return tempBooleanArray;
	}// Of generateBooleanArrayForDivision

	/**
	 ********************************** 
	 * Revert a boolean array.
	 * 
	 * @param paraArray
	 *            the given array
	 * @return reverted array.
	 ********************************** 
	 */
	public static boolean[] revertBooleanArray(boolean[] paraArray) {
		boolean[] tempArray = new boolean[paraArray.length];
		for (int i = 0; i < paraArray.length; i++) {
			tempArray[i] = !paraArray[i];
		}// Of for i
		return tempArray;
	}// Of revertBooleanArray

	/**
	 ********************************** 
	 * Remove supersets, used to obtain reducts.
	 * 
	 * @param paraSets
	 *            the subsets with each one represented by a long value.
	 * @param paraNumberOfConditions
	 *            the number of condition of the dataset
	 * @return a set of reducts without superflous attributes.
	 ********************************** 
	 */
	public static long[] removeSupersets(long[] paraSets,
			int paraNumberOfConditions) throws Exception {
		char check = 0;

		// Check who is who's child.
		for (int i = 0; i < paraSets.length - 1; i++) {
			if (paraSets[i] == -1) {
				continue;
			}// Of if
			for (int j = i + 1; j < paraSets.length; j++) {
				if (paraSets[j] == -1) {
					continue;
				}// Of if

				check = SimpleTool.subSetCheck(paraSets[i], paraSets[j],
						paraNumberOfConditions);
				if (check == '1') {
					paraSets[i] = -1;
					break;
				}// Of if

				if (check == '2') {
					paraSets[j] = -1;
				}// Of if
			}// Of for j
		}// Of for i

		int numberRemainingSubsets = 0;
		for (int i = 0; i < paraSets.length; i++) {
			if (paraSets[i] > 0) {
				numberRemainingSubsets++;
			}// Of if
		}// Of for i

		long[] returnArray = new long[numberRemainingSubsets];
		int m = 0;
		for (int i = 0; i < paraSets.length; i++) {
			if (paraSets[i] > 0) {
				returnArray[m] = paraSets[i];
				m++;
			}// Of if
		}// Of for i

		return returnArray;
	}// Of removeSupersets

	/**
	 ********************************** 
	 * Set intersection. The sets should be sorted.
	 * 
	 * @param paraFirstSet
	 *            The first set indicated by numbers for indices.
	 * @param paraSecondSet
	 *            The second set indicated by numbers for indices.
	 * @return a set for the intersection.
	 ********************************** 
	 */
	public static int[] setIntersection(int[] paraFirstSet, int[] paraSecondSet)
			throws Exception {
		int[] emptyArray = new int[0];
		if ((paraFirstSet.length == 0) || (paraSecondSet.length == 0)) {
			return emptyArray;
		}

		if (!isAscendingOrder(paraFirstSet)) {
			throw new Exception(
					"The first array is not in an ascending order: "
							+ arrayToString(paraFirstSet, ','));
		}
		if (!isAscendingOrder(paraSecondSet)) {
			throw new Exception(
					"The second array is not in an ascending order: "
							+ arrayToString(paraSecondSet, ','));
		}

		int[] tempSet = new int[paraFirstSet.length + paraSecondSet.length];
		int tempLength = 0;
		int firstIndex = 0;
		int secondIndex = 0;
		while ((firstIndex < paraFirstSet.length)
				&& (secondIndex < paraSecondSet.length)) {
			Common.computationTime++;
			if (paraFirstSet[firstIndex] < paraSecondSet[secondIndex]) {
				firstIndex++;
			} else if (paraFirstSet[firstIndex] > paraSecondSet[secondIndex]) {
				secondIndex++;
			} else {
				tempSet[tempLength] = paraFirstSet[firstIndex];
				firstIndex++;
				secondIndex++;
				tempLength++;
			}// Of if
		}// Of while

		int[] resultSet = new int[tempLength];
		for (int i = 0; i < tempLength; i++) {
			resultSet[i] = tempSet[i];
		}// Of for

		return resultSet;
	}// Of setIntersection

	/**
	 ********************************** 
	 * Is the given array in an ascending order?
	 * 
	 * @param paraArray
	 *            The int array.
	 * @return true if it is ascending (equal values permitted).
	 ********************************** 
	 */
	public static boolean isAscendingOrder(int[] paraArray) {
		for (int i = 0; i < paraArray.length - 1; i++) {
			if (paraArray[i] > paraArray[i + 1]) {
				return false;
			}// Of if
		}// Of for
		return true;
	}// Of isAscendingOrder

	/**
	 *************************** 
	 * Judge whether or not the given boolean matrix is upper triangle one.
	 * 
	 * @param paraMatrix
	 *            The given boolean matrix.
	 * @return The constructed String.
	 *************************** 
	 */
	public static boolean isUpperTriangleBooleanMatrix(boolean[][] paraMatrix) {
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j <= i; j++) {
				if (paraMatrix[i][j]) {
					return false;
				}// Of if
			}// Of for j
		}// Of for i

		return true;
	}// Of isUpperTriangleBooleanMatrix

	/**
	 *************************** 
	 * Compute the entropy of the given array.
	 * 
	 * @see #computeEntropy(double[])
	 *************************** 
	 */
	public static double computeEntropy(int[] paraIntArray) throws Exception {
		double[] tempDoubleArray = new double[paraIntArray.length];
		for (int i = 0; i < paraIntArray.length; i++) {
			tempDoubleArray[i] = paraIntArray[i];
		}// Of for i
		return computeEntropy(tempDoubleArray);
	}// Of computeEntropy

	/**
	 *************************** 
	 * Compute the entropy of the given array. If the sum of all elements are
	 * not equal to 1, elements will be normalized.
	 * 
	 * @param paraDoubleArray
	 *            The given array.
	 * @return The information entropy.
	 * @throws Exception
	 *             if negative values exist
	 *************************** 
	 */
	public static double computeEntropy(double[] paraDoubleArray)
			throws Exception {
		// Step 1. Normalize
		double[] tempArray = new double[paraDoubleArray.length];
		double tempSum = 0;
		for (int i = 0; i < paraDoubleArray.length; i++) {
			if (paraDoubleArray[i] < 0) {
				throw new Exception(
						"Error occurred in SimpleTool.computeEntropy(double)\r\n"
								+ "The element should not be negative: "
								+ paraDoubleArray[i] + ".");
			}// Of if

			tempArray[i] = paraDoubleArray[i];
			tempSum += paraDoubleArray[i];
		}// Of for i

		// Normalize now
		if (Math.abs(tempSum - 1) > 1e-6) {
			for (int i = 0; i < tempArray.length; i++) {
				tempArray[i] /= tempSum;
			}// Of for i
		}// Of if

		// Step 2. Compute the entropy
		double tempEntropy = 0;
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i] < 1e-6) {
				continue;
			}// Of if

			tempEntropy -= tempArray[i] * Math.log(tempArray[i])
					/ Math.log(Math.E);
		}// Of for i

		return tempEntropy;
	}// Of computeEntropy

	/**
	 *************************** 
	 * Recommending times array to distribution array. For example, given the
	 * times each movie has been rated, obtain the number of movies that has
	 * been rated respective times.
	 * 
	 * @param paraTimesArray
	 *            the array indicating how many times each item has been rated
	 * @return a distribution array, the length is determined by the maximal
	 *         time rated
	 *************************** 
	 */
	public static int[] recommendTimesToDistribution(int[] paraTimesArray)
			throws Exception {
		// Scan one time to obtain the maximal value
		int tempMaxTimes = 0;
		for (int i = 0; i < paraTimesArray.length; i++) {
			if (tempMaxTimes < paraTimesArray[i]) {
				tempMaxTimes = paraTimesArray[i];
			}// Of if
		}// Of for i

		// Scan the second time for statistics
		int[] returnArray = new int[tempMaxTimes + 1];
		for (int i = 0; i < paraTimesArray.length; i++) {
			returnArray[paraTimesArray[i]]++;
		}// Of for i

		return returnArray;
	}// Of recommendTimesToDistribution

	/**
	 ********************************** 
	 * Generate a random sequence of [0, n - 1].
	 * 
	 * @author Hengru Zhang, Revised by Fan Min 2013/12/24
	 * 
	 * @param paraLength
	 *            the length of the sequence
	 * @return an array of non-repeat random numbers in [0, paraLength - 1].
	 ********************************** 
	 */
	public static int[] generateRandomSequence(int paraLength) {
		// Initialize
		int[] tempResultArray = new int[paraLength];
		for (int i = 0; i < paraLength; i++) {
			tempResultArray[i] = i;
		}// Of for i

		// Swap some elements
		int tempFirstIndex, tempSecondIndex, tempValue;
		for (int i = 0; i < paraLength / 2; i++) {
			tempFirstIndex = random.nextInt(paraLength);
			tempSecondIndex = random.nextInt(paraLength);

			// Really swap elements in these two indices
			tempValue = tempResultArray[tempFirstIndex];
			tempResultArray[tempFirstIndex] = tempResultArray[tempSecondIndex];
			tempResultArray[tempSecondIndex] = tempValue;
		}// Of for i

		return tempResultArray;
	}// Of generateRandomSequence

	/**
	 ********************************** 
	 * Generating random indices.
	 * 
	 * @author Fan Min 2013/12/24
	 * 
	 * @param paraSequenceLength
	 *            the length of the sequence
	 * @param paraSelectionLength
	 *            the length of the selection
	 * @return a sorted array of non-repeat random numbers in [0,
	 *         paraSequenceLength - 1].
	 ********************************** 
	 */
	public static int[] generateRandomIndices(int paraSequenceLength,
			int paraSelectionLength) throws Exception {
		if (paraSequenceLength < paraSequenceLength) {
			throw new Exception(
					"Error occurred in SimpleTool.generateRandomIndices()\r\n"
							+ "The selection length is greater than the sequence length.");
		}// Of if

		// Generate a random sequence
		int[] tempSequence = generateRandomSequence(paraSequenceLength);

		// Initialize
		int[] tempResultArray = new int[paraSelectionLength];

		// Sort and pickup the first paraSelectionLength elements
		boolean[] tempSelectedArray = new boolean[paraSelectionLength];
		int tempMinimalIndex = 0;
		int tempMinimalValue;
		for (int i = 0; i < paraSelectionLength; i++) {
			tempMinimalValue = Integer.MAX_VALUE;
			for (int j = 0; j < paraSelectionLength; j++) {
				if (!tempSelectedArray[j]
						&& (tempSequence[j] < tempMinimalValue)) {
					tempMinimalValue = tempSequence[j];
					tempMinimalIndex = j;
				}// Of if
			}// Of for j

			tempResultArray[i] = tempMinimalValue;
			tempSelectedArray[tempMinimalIndex] = true;
		}// Of for i

		return tempResultArray;
	}// Of generateRandomIndices

	/**
	 ********************************** 
	 * Select some elements from the given int array.
	 * 
	 * @author Hengru Zhang Revised by Fan Min 2013/12/24
	 * 
	 * @param paraArray
	 *            The array used to select.
	 * @param paraSelected
	 *            The number of values to be selected.
	 ********************************** 
	 */
	public static int[] randomSelect(int[] paraArray, int paraSelected)
			throws Exception {
		if ((paraArray == null) || (paraArray.length == 0)) {
			throw new Exception(
					"Error occurred in SimpleTool.randomSelect().\r\n"
							+ "The given array is null or empty.");
		}// Of if

		if (paraSelected > paraArray.length) {
			paraSelected = paraArray.length;
		}// of if

		int[] tempSelectedIndices = generateRandomIndices(paraArray.length,
				paraSelected);

		// Copy elements
		int[] tempArray = new int[paraSelected];
		for (int i = 0; i < paraSelected; i++) {
			tempArray[i] = paraArray[tempSelectedIndices[i]];
		}// of for i

		return tempArray;
	}// randomSelect

	/**
	 ********************************** 
	 * Select some non-zero elements to remove.
	 * 
	 * @param paraArray
	 *            The array used to select.
	 * @param paraSelected
	 *            The number of values to be selected.
	 * @return a boolean array indicating which ones are removed
	 * @author Fan Min 2013/12/26
	 ********************************** 
	 */
	public static boolean[] randomSelectNonzeros(int[] paraArray,
			int paraSelected) throws Exception {
		if ((paraArray == null) || (paraArray.length == 0)) {
			throw new Exception(
					"Error occurred in SimpleTool.randomSelectNonzeros().\r\n"
							+ "The given array is null or empty.");
		}// Of if

		// Step 1. Count non-zero elements
		int tempCount = 0;
		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i] > 0) {
				tempCount++;
			}// Of if
		}// Of for i

		// No element to remove
		if (tempCount == 0) {
			return new boolean[paraArray.length];
		}// Of if

		// Select as many element as is
		if (tempCount < paraSelected) {
			tempCount = paraSelected;
		}// Of if

		// Step 2. Generate a random indice sequence
		int[] tempRemovedIndices = generateRandomIndices(tempCount,
				paraSelected);

		// Step 3. Decide which ones to remove
		boolean[] tempRemovedElements = new boolean[paraArray.length];
		int tempIndex = 0;
		tempCount = 0;
		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i] > 0) {
				if (tempCount == tempRemovedIndices[tempIndex]) {
					tempRemovedElements[i] = true;
					tempIndex++;
				}// Of if
				tempCount++;
			}// Of if

			if (tempIndex >= tempRemovedIndices.length) {
				break;
			}// Of if
		}// Of for i

		return tempRemovedElements;
	}// randomSelectNonzeros

	/**
	 ********************************** 
	 * Select some non-zero elements to remove.
	 * 
	 * @param paraArray
	 *            The array used to select.
	 * @param paraSelected
	 *            The number of values to be selected.
	 * @return a boolean array indicating which ones are removed
	 * @author Fan Min 2013/12/31
	 ********************************** 
	 */
	public static boolean[] randomSelectNonzeros(double[] paraArray,
			int paraSelected) throws Exception {
		if ((paraArray == null) || (paraArray.length == 0)) {
			throw new Exception(
					"Error occurred in SimpleTool.randomSelectNonzeros().\r\n"
							+ "The given array is null or empty.");
		}// Of if

		// Step 1. Count non-zero elements
		int tempNonzeroElements = 0;
		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i] > 0) {
				tempNonzeroElements++;
			}// Of if
		}// Of for i

		// No element to remove
		if (tempNonzeroElements == 0) {
			return new boolean[paraArray.length];
		}// Of if

		// Select as many element as is
		int tempSelection = 0;
		if (tempNonzeroElements < paraSelected) {
			tempSelection = tempNonzeroElements;
		} else {
			tempSelection = paraSelected;
		}// Of if

		// Step 2. Generate a random indice sequence
		int[] tempRemovedIndices = generateRandomIndices(tempNonzeroElements,
				tempSelection);

		// Step 3. Decide which ones to remove
		boolean[] tempRemovedElements = new boolean[paraArray.length];
		int tempIndex = 0;
		int tempCount = 0;
		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i] > 0) {
				if (tempCount == tempRemovedIndices[tempIndex]) {
					tempRemovedElements[i] = true;
					tempIndex++;
					if (tempIndex >= tempRemovedIndices.length) {
						break;
					}// Of if
				}// Of if
				tempCount++;
			}// Of if
		}// Of for i

		return tempRemovedElements;
	}// randomSelectNonzeros

	/**
	 ********************************** 
	 * Assign fold number randomly to non-zero elements.
	 * 
	 * @param paraArray
	 *            The array used to select.
	 * @param paraFolds
	 *            The number of folds.
	 * @return an int array to indicate which element is assigned to the
	 *         corresponding fold.
	 * @author Fan Min 2013/12/26
	 ********************************** 
	 */
	public static int[] randomAssignFold(double[] paraArray, int paraFolds)
			throws Exception {
		if ((paraArray == null) || (paraArray.length == 0)) {
			throw new Exception(
					"Error occurred in SimpleTool.randomAssignFold().\r\n"
							+ "The given array is null or empty.");
		}// Of if
		if (paraFolds < 2) {
			throw new Exception(
					"Error occurred in SimpleTool.randomAssignFold().\r\n"
							+ "The number of folds should be at least 2.");
		}// Of if

		// Step 1. Initialize, the default value is -1
		int[] returnArray = new int[paraArray.length];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = -1;
		}// Of for i

		// Step 2. Count non-zero elements
		int tempNonzeroCount = 0;
		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i] > 0) {
				tempNonzeroCount++;
			}// Of if
		}// Of for i

		// No element to assign
		if (tempNonzeroCount == 0) {
			return returnArray;
		}// Of if

		// Step 3. Generate a random indice sequence and the fold index
		int[] tempIndexSequence = generateRandomSequence(tempNonzeroCount);
		for (int i = 0; i < tempIndexSequence.length; i++) {
			tempIndexSequence[i] %= paraFolds;
		}// Of for i

		// Step 4. Assign fold index
		int tempCount = 0;
		for (int i = 0; i < paraArray.length; i++) {
			if (paraArray[i] > 0) {
				returnArray[i] = tempIndexSequence[tempCount];
				tempCount++;
			}// Of if
		}// Of for i

		return returnArray;
	}// randomAssignFold

	/**
	 ************************* 
	 * Compute the cosine value of two vectors. The method is the same as
	 * cosine(double[], double[]), however we cannot invoke that method for
	 * efficiency.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws exception
	 *             if there are negative values the algorithm ID
	 * @author Fan Min 2013/12/26
	 ************************* 
	 */
	public static double cosine(int[] paraFirstVector, int[] paraSecondVector)
			throws Exception {
		// Check length
		if (paraFirstVector.length != paraSecondVector.length) {
			throw new Exception(
					"Error occurred in SimpleTool.cosine(). The arrays should have the same length.");
		}// Of if

		// Check elements, might be unuseful
		for (int i = 0; i < paraFirstVector.length; i++) {
			if ((paraFirstVector[i] < 0) || (paraSecondVector[i] < 0)) {
				throw new Exception(
						"Error occurred in SimpleTool.cosine(). Elements should be non-negative.");
			}// Of if
		}// Of for i

		double tempNumerator = 0;
		double tempFirstModule = 0;
		double tempSecondModule = 0;
		for (int i = 0; i < paraFirstVector.length; i++) {
			tempNumerator += paraFirstVector[i] * paraSecondVector[i];
			tempFirstModule += paraFirstVector[i] * paraFirstVector[i];
			tempSecondModule += paraSecondVector[i] * paraSecondVector[i];
		}// Of for i
		double tempDenominator = Math.sqrt(tempFirstModule)
				* Math.sqrt(tempSecondModule);

		if (tempDenominator == 0) {
			return 9999;
		}// Of if
		return tempNumerator / tempDenominator;
	}// Of cosine

	/**
	 ************************* 
	 * Compute the cosine value of two vectors. The method is the same as
	 * cosine(double[], double[]), however we cannot invoke that method for
	 * efficiency.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws exception
	 *             if there are negative values the algorithm ID
	 * @author Hengru Zhang 2014/06/04
	 ************************* 
	 */
	public static double cosine(boolean[] paraFirstVector,
			boolean[] paraSecondVector) throws Exception {
		// Check length
		if (paraFirstVector.length != paraSecondVector.length) {
			throw new Exception(
					"Error occurred in SimpleTool.cosine(). The arrays should have the same length.");
		}// Of if

		double tempNumerator = 0;
		double tempFirstModule = 0;
		double tempSecondModule = 0;
		int tempFirstValue = 0;
		int tempSecondValue = 0;
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (paraFirstVector[i]) {
				tempFirstValue = 1;
			} else {
				tempFirstValue = 0;
			}// of if
			if (paraSecondVector[i]) {
				tempSecondValue = 1;
			} else {
				tempSecondValue = 0;
			}// of if
			tempNumerator += tempFirstValue * tempSecondValue;
			tempFirstModule += tempFirstValue * tempFirstValue;
			tempSecondModule += tempSecondValue * tempSecondValue;
		}// Of for i
		double tempDenominator = Math.sqrt(tempFirstModule)
				* Math.sqrt(tempSecondModule);

		if (tempDenominator == 0) {
			// return 9999;
			return -2; // modified by zhanghr 2014/06/09
		}// Of if
		return tempNumerator / tempDenominator;
	}// Of cosine

	/**
	 ************************* 
	 * Compute the cosine value of two vectors.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws exception
	 *             if there are negative values the algorithm ID
	 * @author Fan Min 2013/12/30
	 * @modified by Henry 2015/06/25 zero rating-->null
	 ************************* 
	 */
	public static double cosine(double[] paraFirstVector,
			double[] paraSecondVector) throws Exception {
		// Check length
		if (paraFirstVector.length != paraSecondVector.length) {
			throw new Exception(
					"Error occurred in SimpleTool.cosine(). The arrays should have the same length.");
		}// Of if

		// Check elements, might be unuseful
		for (int i = 0; i < paraFirstVector.length; i++) {
			if ((paraFirstVector[i] < 0) || (paraSecondVector[i] < 0)) {
				throw new Exception(
						"Error occurred in SimpleTool.cosine(). Elements should be non-negative.");
			}// Of if
		}// Of for i

		double tempNumerator = 0;
		double tempFirstModule = 0;
		double tempSecondModule = 0;
		for (int i = 0; i < paraFirstVector.length; i++) {
			// zero rating --> null
			// for examle: paraFirstVector = (0, 2, 5, 0), paraSecondVector = (1, 3, 0, 4)
			// only {2} and {3}
			if (paraFirstVector[i] > 1e-6 && paraSecondVector[i] > 1e-6) {
				tempNumerator += paraFirstVector[i] * paraSecondVector[i];
				tempFirstModule += paraFirstVector[i] * paraFirstVector[i];
				tempSecondModule += paraSecondVector[i] * paraSecondVector[i];
			}// of if
		}// Of for i
		double tempDenominator = Math.sqrt(tempFirstModule)
				* Math.sqrt(tempSecondModule);

		if (tempDenominator == 0) {
			return -9999; //ԭ������0���˴��޸�Ϊ����-9999
		}// of if

		return tempNumerator / tempDenominator;
	}// Of cosine
	
	/**
	 ************************* 
	 * Compute the cosine value of two vectors.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws exception
	 *             if there are negative values the algorithm ID
	 * @author Fan Min 2013/12/30
	 * @modified by Henry 2015/06/25 zero rating-->null
	 ************************* 
	 */
	public static double adjustedCosine(double[] paraFirstVector,
			double[] paraSecondVector) throws Exception {
		// Check length
		if (paraFirstVector.length != paraSecondVector.length) {
			throw new Exception(
					"Error occurred in SimpleTool.cosine(). The arrays should have the same length.");
		}// Of if

		double tempNumerator = 0;
		double tempFirstModule = 0;
		double tempSecondModule = 0;
		for (int i = 0; i < paraFirstVector.length; i++) {
			// zero rating --> null
			// for examle: paraFirstVector = (0, 2, 5, 0), paraSecondVector = (1, 3, 0, 4)
			// only {2} and {3}
			if (paraFirstVector[i] == 0 || paraSecondVector[i] == 0) {
				continue;
			}// Of if
			tempNumerator += paraFirstVector[i] * paraSecondVector[i];
			tempFirstModule += paraFirstVector[i] * paraFirstVector[i];
			tempSecondModule += paraSecondVector[i] * paraSecondVector[i];
		}// Of for i
		double tempDenominator = Math.sqrt(tempFirstModule)
				* Math.sqrt(tempSecondModule);

		if (tempDenominator == 0) {
			return -9999; //ԭ������0���˴��޸�Ϊ����-9999
		}// of if

		return tempNumerator / tempDenominator;
	}// Of cosine

	/**
	 ************************* 
	 * Remove elements in indices with zeros which indicate missing values, and
	 * compute the cosine value of two vectors. For example, {0, 1, 2, 4} and
	 * {1, 2, 0, 5} with be changed to {1, 4} and {2, 5}, and then the cosine
	 * value is computed.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws Exception
	 *             if there is no non-zero value pair.
	 * @author Fan Min 2013/12/26
	 ************************* 
	 */
	public static double cosineRemoveZeros(int[] paraFirstVector,
			int[] paraSecondVector) throws Exception {
		boolean[] tempHasZeroValues = new boolean[paraFirstVector.length];
		int tempValidValues = paraFirstVector.length;
		for (int i = 0; i < paraFirstVector.length; i++) {
			if ((paraFirstVector[i] == 0) || (paraSecondVector[i] == 0)) {
				tempHasZeroValues[i] = true;
				tempValidValues--;
			}// Of if
		}// Of for i

		if (tempValidValues <= 0) {
			throw new Exception(
					"Error occurred in SimpleTool.cosineRemoveZeros()\r\n"
							+ "There is no non-zero value pair for the two given arrays.");
		}// Of if

		int[] tempFirstVector = new int[tempValidValues];
		int[] tempSecondVector = new int[tempValidValues];
		int tempCounter = 0;
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (!tempHasZeroValues[i]) {
				tempFirstVector[tempCounter] = paraFirstVector[i];
				tempSecondVector[tempCounter] = paraSecondVector[i];
				tempCounter++;
			}// Of if
		}// Of for i

		return cosine(tempFirstVector, tempSecondVector);
	}// Of cosineRemoveZeros

	/**
	 ************************* 
	 * Remove elements in indices with zeros which indicate missing values, and
	 * compute the cosine value of two vectors. For example, {0, 1, 2, 4} and
	 * {1, 2, 0, 5} with be changed to {1, 4} and {2, 5}, and then the cosine
	 * value is computed.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws Exception
	 *             if there is no non-zero value pair.
	 * @author Fan Min 2013/12/30
	 ************************* 
	 */
	public static double cosineRemoveZeros(double[] paraFirstVector,
			double[] paraSecondVector) throws Exception {
		boolean[] tempHasZeroValues = new boolean[paraFirstVector.length];
		int tempValidValues = paraFirstVector.length;
		for (int i = 0; i < paraFirstVector.length; i++) {
			if ((paraFirstVector[i] == 0) || (paraSecondVector[i] == 0)) {
				tempHasZeroValues[i] = true;
				tempValidValues--;
			}// Of if
		}// Of for i

		if (tempValidValues <= 0) {
			throw new Exception(
					"Error occurred in SimpleTool.cosineRemoveZeros()\r\n"
							+ "There is no non-zero value pair for the two given arrays.");
		}// Of if

		double[] tempFirstVector = new double[tempValidValues];
		double[] tempSecondVector = new double[tempValidValues];
		int tempCounter = 0;
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (!tempHasZeroValues[i]) {
				tempFirstVector[tempCounter] = paraFirstVector[i];
				tempSecondVector[tempCounter] = paraSecondVector[i];
				tempCounter++;
			}// Of if
		}// Of for i

		return cosine(tempFirstVector, tempSecondVector);
	}// Of cosineRemoveZeros

	/**
	 ************************* 
	 * Remove elements in indices with zeros which indicate missing values, and
	 * compute the cosine value of two vectors. For example, {0, 1, 2, 4} and
	 * {1, 2, 0, 5} with be changed to {1, 4} and {2, 5}, and then the cosine
	 * value is computed.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws Exception
	 *             if there is no non-zero value pair.
	 * @author Fan Min 2013/12/26
	 ************************* 
	 */
	public static double cosineDefaultValue(int[] paraFirstVector,
			int[] paraSecondVector, int paraDefaultValue) throws Exception {
		int[] tempFirstVector = new int[paraFirstVector.length];
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (paraFirstVector[i] == 0) {
				tempFirstVector[i] = paraDefaultValue;
			} else {
				tempFirstVector[i] = paraFirstVector[i];
			}// Of if
		}// Of for i

		int[] tempSecondVector = new int[paraSecondVector.length];
		for (int i = 0; i < paraSecondVector.length; i++) {
			if (paraSecondVector[i] == 0) {
				tempSecondVector[i] = paraDefaultValue;
			} else {
				tempSecondVector[i] = paraSecondVector[i];
			}// Of if
		}// Of for i

		return cosine(tempFirstVector, tempSecondVector);
	}// Of cosineDefaultValue

	/**
	 ************************* 
	 * Remove elements in indices with zeros which indicate missing values, and
	 * compute the cosine value of two vectors. For example, {0, 1, 2, 4} and
	 * {1, 2, 0, 5} with be changed to {1, 4} and {2, 5}, and then the cosine
	 * value is computed.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws Exception
	 *             if there is no non-zero value pair.
	 * @author Fan Min 2013/12/30
	 ************************* 
	 */
	public static double cosineDefaultValue(double[] paraFirstVector,
			double[] paraSecondVector, double paraDefaultValue)
			throws Exception {
		double[] tempFirstVector = new double[paraFirstVector.length];
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (paraFirstVector[i] == 0) {
				tempFirstVector[i] = paraDefaultValue;
			} else {
				tempFirstVector[i] = paraFirstVector[i];
			}// Of if
		}// Of for i

		double[] tempSecondVector = new double[paraSecondVector.length];
		for (int i = 0; i < paraSecondVector.length; i++) {
			if (paraSecondVector[i] == 0) {
				tempSecondVector[i] = paraDefaultValue;
			} else {
				tempSecondVector[i] = paraSecondVector[i];
			}// Of if
		}// Of for i

		return cosine(tempFirstVector, tempSecondVector);
	}// Of cosineDefaultValue

	/**
	 ************************* 
	 * Weight common rating item and set a default elements in indices with
	 * zeros , and compute the cosine value of two vectors. For example, {0, 1,
	 * 2, 4} and {1, 2, 0, 5} is two common rating items.{1,4} and {2,5} will be
	 * changed to {1*2,4*2} and {2*2,5*2} ,then the cosine value is computed.
	 * 
	 * @param paraFirstVector
	 *            the first vector
	 * @param paraSecondVector
	 *            the second vector
	 * @throws Exception
	 *             if there is no non-zero value pair.
	 * @author Hengru Zhang 2014/1/2
	 ************************* 
	 */
	public static double cosineDefaultValueAndWeighted(
			double[] paraFirstVector, double[] paraSecondVector,
			double paraDefaultValue) throws Exception {
		// Step 1.Compute number of common rating items
		int tempCommonItems = 0;
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (paraFirstVector[i] > 1e-6 && paraSecondVector[i] > 1e-6) {
				tempCommonItems++;
			}// of if
		}// of for i

		// Step 2. Set default value
		double[] tempFirstVector = new double[paraFirstVector.length];
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (paraFirstVector[i] == 0) {
				tempFirstVector[i] = paraDefaultValue;
			} else {
				tempFirstVector[i] = paraFirstVector[i];
			}// Of if
		}// Of for i

		double[] tempSecondVector = new double[paraSecondVector.length];
		for (int i = 0; i < paraSecondVector.length; i++) {
			if (paraSecondVector[i] == 0) {
				tempSecondVector[i] = paraDefaultValue;
			} else {
				tempSecondVector[i] = paraSecondVector[i];
			}// Of if
		}// Of for i

		// Step 3.Weight common rating.
		for (int i = 0; i < paraFirstVector.length; i++) {
			if (paraFirstVector[i] > 1e-6 && paraSecondVector[i] > 1e-6) {
				tempFirstVector[i] = paraFirstVector[i]
						* Math.pow(tempCommonItems, 1);
				tempSecondVector[i] = paraSecondVector[i]
						* Math.pow(tempCommonItems, 1);
			}// of if
		}// of for i

		// Step 4. Compute cosine value with weighted rating
		return cosine(tempFirstVector, tempSecondVector);
	}// Of cosineDefaultValue

	/**
	 ************************* 
	 * Get the minimal value of a given array.
	 * 
	 * @param paraArray
	 *            the given array vector
	 * 
	 * @author Heng-Ru Zhang 2015/1/19
	 ************************* 
	 */
	public static double minimalValueOfArray(double[] paraArray) {
		if (paraArray == null || paraArray.length < 2) {
			return 0;
		}// of if
		double tempMin = paraArray[0];
		for (int i = 1; i < paraArray.length; i++) {
			if (tempMin > paraArray[i]) {
				tempMin = paraArray[i];
			}// of if
		}// of for i

		return tempMin;
	}// of minimalValueOfArray
	
	/**
	 ************************* 
	 * Get the minimal value of a given array.
	 * 
	 * @param paraArray
	 *            the given array vector
	 * 
	 * @author Heng-Ru Zhang 2015/1/19
	 ************************* 
	 */
	public static int minimalValueOfArray(int[] paraArray) {
		if (paraArray == null || paraArray.length < 2) {
			return 0;
		}// of if
		int tempMin = paraArray[0];
		for (int i = 1; i < paraArray.length; i++) {
			if (tempMin > paraArray[i]) {
				tempMin = paraArray[i];
			}// of if
		}// of for i

		return tempMin;
	}// of minimalValueOfArray
	
	/**
	 ************************* 
	 * Get the minimal value of a given array.
	 * 
	 * @param paraArray
	 *            the given array vector
	 * 
	 * @author Heng-Ru Zhang 2015/1/19
	 ************************* 
	 */
	public static int maximumValueOfArray(int[] paraArray) {
		if (paraArray == null || paraArray.length < 2) {
			return 0;
		}// of if
		int tempMax = paraArray[0];
		for (int i = 1; i < paraArray.length; i++) {
			if (tempMax < paraArray[i]) {
				tempMax = paraArray[i];
			}// of if
		}// of for i

		return tempMax;
	}// of maximumValueOfArray
	
	/**
	 ************************* 
	 * Get the minimal value of a given array.
	 * 
	 * @param paraArray
	 *            the given array vector
	 * 
	 * @author Heng-Ru Zhang 2015/1/19
	 ************************* 
	 */
	public static double maximumValueOfArray(double[] paraArray) {
		if (paraArray == null || paraArray.length < 2) {
			return 0;
		}// of if
		double tempMax = paraArray[0];
		for (int i = 1; i < paraArray.length; i++) {
			if (tempMax < paraArray[i]) {
				tempMax = paraArray[i];
			}// of if
		}// of for i

		return tempMax;
	}// of maximumValueOfArray

	/**
	 ************************* 
	 * Get the minimal value of a given array.
	 * 
	 * @param paraArray
	 *            the given array vector
	 * 
	 * @author Heng-Ru Zhang 2015/1/19
	 ************************* 
	 */
	public static int minimalValueIndexOfArray(double[] paraArray) {
		if (paraArray == null || paraArray.length < 2) {
			return 0;
		}// of if
		double tempMin = paraArray[0];
		int tempMinIndex = 0;
		for (int i = 1; i < paraArray.length; i++) {
			if (tempMin > paraArray[i]) {
				tempMin = paraArray[i];
				tempMinIndex = i;
			}// of if
		}// of for i

		return tempMinIndex;
	}// of minimalValueOfArray

	/**
	 ********************************** 
	 * Testing method.
	 * 
	 * @param args
	 ********************************** 
	 */
	public static void main(String args[]) {
		// int[] firstSet = {1, 2, 6};
		// int[] firstSet = {2};
		// int[] firstSet = {};
		// int[] firstSet = {1};
		/*
		 * int[] firstSet = { 2, 3, 6 }; int[] secondSet = { 2, 3, 6 }; int[]
		 * thirdSet = {}; try { thirdSet = setIntersection(firstSet, secondSet);
		 * } catch (Exception ee) { System.out.println(ee); return; }
		 * printIntArray(thirdSet);
		 * 
		 * 
		 * int[] tempArray = null; try { tempArray = generateRandomIndices(10,
		 * 5); printIntArray(tempArray); } catch (Exception ee) {
		 * System.out.print(ee.toString()); }
		 * 
		 * int[] tempArray2 = {2, 6, 8, 12, 18, 23}; try { int[]
		 * tempSelectedArray = randomSelect(tempArray2, 3);
		 * printIntArray(tempSelectedArray); } catch (Exception ee) {
		 * System.out.print(ee.toString()); } int[] tempArray1 = { 1, 0, 1 };
		 * 
		 * int[] tempArray2 = { 1, 2, 1 };
		 * 
		 * try { System.out.print("Array 1: "); printIntArray(tempArray1);
		 * System.out.print("Array 2: "); printIntArray(tempArray2);
		 * 
		 * double tempCosine = cosine(tempArray1, tempArray2);
		 * System.out.println("The cosine value is: " + tempCosine);
		 * 
		 * tempCosine = cosineRemoveZeros(tempArray1, tempArray2);
		 * System.out.println("The zero-removed cosine value is: " +
		 * tempCosine); } catch (Exception ee) {
		 * System.out.print(ee.toString()); }
		 */

		/*
		 * int[] tempArray = { 1, 0, 2, 3, 0, 5 };
		 * 
		 * try { System.out.print("Array 1: "); printIntArray(tempArray);
		 * 
		 * boolean[] tempRemoved = randomSelectNonzeros(tempArray, 2);
		 * 
		 * System.out.println("removing");
		 * 
		 * printBooleanArray(tempRemoved); } catch (Exception ee) {
		 * System.out.print(ee.toString()); }
		 */
		double[] tempArray = { 1, 0, 2, 3, 0, 5, 3 };

		try {
			System.out.print("Array 1: ");
			printDoubleArray(tempArray);

			// boolean[] tempRemoved = randomSelectNonzeros(tempArray, 2);
			int[] tempIndices = randomAssignFold(tempArray, 3);

			System.out.println("removing");

			// printBooleanArray(tempRemoved);
			printIntArray(tempIndices);
		} catch (Exception ee) {
			System.out.print(ee.toString());
		}
	}// Of main
}// Of class SimpleTool
