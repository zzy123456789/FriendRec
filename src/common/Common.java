/*
 * @(#)Common.java
 *
 */

package common;

import java.io.*;
import java.util.*;

/**
 * Provides basic settings along with some other important data for the whole
 * project.
 * <p>
 * Author: <b>Fan Min</b> minfanphd@163.com <br>
 * Copyright: The source code and all documents are open and free. PLEASE keep
 * this header while revising the program. <br>
 * Organization: <a href=http://grc.fjzs.edu.cn/>Lab of Granular Computing</a>,
 * Zhangzhou Normal University, Fujian 363000, China.<br>
 * Project: The granular association rules project.
 * <p>
 * Progress: OK. Copied from Coser.<br>
 * Written time: April 03, 2013. <br>
 * Last modify time: April 03, 2013.
 */

public class Common extends Object {

	/**
	 * Help the relative directory. In this way all file paths are correct while
	 * migrating to a new machine.
	 */
	public static String rootDirectory;

	static {
		File pathTestFile = new File("grale.java");
		rootDirectory = pathTestFile.getAbsolutePath().substring(0,
				pathTestFile.getAbsolutePath().length() - 10);
	}

	/**
	 * Anything changed?
	 */
	public static boolean somethingChanged = false;

	/**
	 * Unspecifed value. Used when a string is not available.
	 */
	public static final String unspecifiedString = "unspecified";

	/**
	 * To specify if the system is under debug, and avoid debugging output
	 * appear in formal versions.
	 */
	public static boolean ifDebug = false;

	/**
	 * Computation time.
	 */
	public static long computationTime = 0;

	/**
	 * The project header appears in any files generated.
	 */
	public static final String ProjectHeader = "%The cost-sensitive rough sets project."
			+ "\r\n%Corresponding author: Fan MIN, minfanphd@163.com\r\n";

	/**
	 * The system configuration filename.
	 */
	public static String configurationFilename = "config\\system.properties";

	/**
	 * Properties read from the configuration file.
	 */
	public static Properties property = new Properties();
	/**
	 * The file name of Sub-reduct
	 */
	public static String subReductsFileName = "";

	/**
	 ********************************** 
	 * Initialize. Read the configuration data from the system configuration
	 * file.
	 * 
	 * @throws Exception
	 *             Possible Exception.
	 ********************************** 
	 */
	public static void loadConfiguration() throws Exception {
		try {
			property.load(new FileInputStream(new File(configurationFilename)));
		} catch (Exception ee) {
			throw new Exception(
					"Exception occurred in Common.loadConfiguration()."
							+ "\n\tInvalid filename: " + configurationFilename
							+ ". " + "\n\t The initial Exception is: " + ee);
		}// Of try

		ifDebug = property.getProperty("if_debug").equals("true");
	}// Of loadConfiguration

	/**
	 ********************************** 
	 * Store configuration.
	 * 
	 * @throws Exception
	 *             May be incurred by writing configuration file.
	 ********************************** 
	 */
	public static void storeConfiguration() throws Exception {
		property.setProperty("if_debug", "" + ifDebug);

		try {
			property.store(
					new FileOutputStream(new File(configurationFilename)),
					"Basic properties of UYH. Author email: minfanphd@163.com");
		} catch (Exception ee) {
			throw new Exception(
					"Error occurred in common.Common.storeConfiguration()."
							+ "\n\t Invalid configuration filename: "
							+ configurationFilename
							+ "\n\t The initial Exception is: " + ee);
		}// Of try
	}// Of storeConfiguration

	/**
	 ********************************** 
	 * Exit system. No exception is thrown.
	 ********************************** 
	 */
	public static void exitSystem() {
		try {
			System.exit(0);
		} catch (Exception ee) {
			System.out.println(ee);
		}// Of try
	}// Of exitSystem
}// Of class Common
