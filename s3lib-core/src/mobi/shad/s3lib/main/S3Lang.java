/*******************************************************************************
 * Copyright 2012
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
package mobi.shad.s3lib.main;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jarek
 */
public class S3Lang{

	private static Map enText = new HashMap<String, String>();
	private static Map plText = new HashMap<String, String>();
	//
	// 0 - En
	// 1 - Pl
	//
	private static int currentLang = 0;

	private S3Lang(){

	}

	/**
	 * @param text
	 * @return
	 */
	public static String get(String text){

		String out = null;
		switch (currentLang){
			default:
				out = (String) enText.get(text);
				break;
			case 1:
				out = (String) plText.get(text);
				break;
		}

		if (out == null){
			out = text;
			//			if (S3Config.DEBUG){
			S3Log.log("Lang:get", "Not find (" + currentLang + ") for: " + text, 3);
			//			}
		}
		return out;
	}

	/**
	 * @return
	 */
	public static int getLang(){
		return currentLang;
	}

	/**
	 * @param lang
	 */
	public static void setLang(int lang){
		S3Log.log("Lang:setLang", "Set lang to:" + lang);
		currentLang = lang;
	}

	public static void init(){

		S3Setting enLangIni = new S3Setting("lang/en");
		enText = enLangIni.getHashMap();

		S3Setting plLangIni = new S3Setting("lang/pl");
		plText = plLangIni.getHashMap();

		S3Log.log("S3Lang::init", "EN size: " + enText.size() + " PL size: " + plText.size());
	}
}
