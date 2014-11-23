/*******************************************************************************
 * Copyright 2013
 *
 * Jaroslaw Czub
 * http://shad.mobi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ******************************************************************************/

package mobi.shad.s3lib.utils;

/**
 * Small class to text operations
 */
public class TextUtil{

	private TextUtil(){
	}

	/*
	 * Capitalizes the First Letter of a String
	 */
	public static String capitalize(String text){
		if (text != null && text != ""){
			return (text.substring(0, 1)).toUpperCase() + text.substring(1);
		} else {
			return "";
		}
	}

	/*
	 * UnCapitalizes the First Letter of a String
	 */
	public static String uncapitalize(String text){
		return text.substring(0, 1).toLowerCase() + text.substring(1);
	}


	/**
	 * Clear string for file name
	 *
	 * @param fileName
	 * @return
	 */
	public static String clearFileName(String fileName){
		return fileName.trim()
					   .replace(".json", "")
					   .replace(".java", "")
					   .replace(" ", "_")
					   .replace("/", "_")
					   .replace("\\", "_")
					   .replace("@", "_")
					   .replace("%", "_")
					   .replace(":", "_");
	}
}
