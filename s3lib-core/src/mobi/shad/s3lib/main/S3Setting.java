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
package mobi.shad.s3lib.main;

import com.badlogic.gdx.utils.GdxRuntimeException;
import mobi.shad.s3lib.utils.XorEncrypter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Class to Read/Write settings ini file,
 * can encode key and data value using XOR encryption
 */
public class S3Setting{

	private String propFile;
	private Properties properties = new Properties();

	/**
	 * Create setting instance with properties file name,
	 * storage in data app directory
	 *
	 * @param fileName
	 */
	public S3Setting(final String fileName){

		if (fileName == null || fileName.equals("")){
			throw new GdxRuntimeException("Wrong INI file Name ...");
		}
		propFile = fileName + ".ini";
		S3Log.log("S3Setting", "Load file: " + propFile);
		try {
			if (S3File.getFileHandle(propFile).exists()){
				properties.load(S3File.getFileHandle(propFile).read());
			}
		} catch (Exception ex){
			try {
				properties.store(S3File.getFileHandle(propFile, false, true).write(false), "/* properties create */");
			} catch (Exception ex1){
				S3Log.error(S3Setting.class.getName(), "Error create propFile", ex1);
			}
		}
	}

	/**
	 * Save setting data to file
	 */
	public void save(){
		try {
			if (!S3File.getFileHandle(propFile).exists()){
				properties.store(S3File.getFileHandle(propFile, false, true).write(false), "/* properties create */");
			}
			properties.store(S3File.getFileHandle(propFile, false, true).write(false), "/* properties update */");
		} catch (IOException ex){
			S3Log.error(S3Setting.class.getName(), "Error save propFile", ex);
		}
	}

	public Object get(Object key){
		return properties.get(key);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value){
		properties.put(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value){
		properties.setProperty(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setInt(String key, int value){
		properties.setProperty(key, "" + value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setFloat(String key, float value){
		properties.setProperty(key, "" + value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setEncodeString(String key, String value){
		properties.setProperty(XorEncrypter.encode(key), XorEncrypter.encode(value));
	}

	/**
	 * @param key
	 * @return
	 */
	public String getEncodeString(String key){
		return XorEncrypter.encode(properties.getProperty(XorEncrypter.encode(key)));
	}

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key){
		return properties.getProperty(key);
	}

	/**
	 * @param key
	 * @return
	 */
	public int getInt(String key){
		return new Integer(properties.getProperty(key)).intValue();
	}

	/**
	 * @param key
	 * @return
	 */
	public float getFloat(String key){
		return new Float(properties.getProperty(key)).floatValue();
	}

	/**
	 * @return
	 */
	public HashMap<String, String> getHashMap(){
		Set<Object> keySet = properties.keySet();
		Iterator<Object> iterator = keySet.iterator();
		HashMap<String, String> out = new HashMap<String, String>();
		while (iterator.hasNext()){
			String key = (String) iterator.next();
			String value = properties.getProperty(key, "-");
			out.put(key, value);
		}
		return out;
	}

	/**
	 *
	 */
	public void setTopScore(){
		setTopScore(null);
	}

	/**
	 * @return
	 */
	public HashMap<String, String> getTopScore(){
		HashMap<String, String> score = new HashMap<>(10);
		for (int i = 1; i < 11; i++){
			String keyName = "place" + i;
			String encodeString = getEncodeString(keyName);
			if (encodeString == null){
				encodeString = "0";
				setEncodeString(keyName, "0");
			}
			score.put(keyName, encodeString);
		}
		return score;
	}

	/**
	 * @param score
	 */
	public void setTopScore(HashMap<String, String> score){
		if (score == null){
			score = new HashMap<>(10);
			score.put("place1", "0");
			score.put("place2", "0");
			score.put("place3", "0");
			score.put("place4", "0");
			score.put("place5", "0");
			score.put("place6", "0");
			score.put("place7", "0");
			score.put("place8", "0");
			score.put("place9", "0");
			score.put("place10", "0");
		}

		for (int i = 1; i < 11; i++){
			String keyName = "place" + i;
			if (score.containsKey(keyName)){
				score.put(keyName, score.get(keyName));
			} else {
				score.put(keyName, "0");
				setEncodeString(keyName, "0");
			}
		}
		save();
	}

	/**
	 * @param topScoreValue
	 */
	public void addTopScore(int topScoreValue){
		for (int i = 1; i < 11; i++){
			String keyName = "place" + i;
			String encodeString = getEncodeString(keyName);
			if (encodeString == null){
				encodeString = "0";
			}
			int value = Integer.valueOf(encodeString);
			if (topScoreValue > value){
				for (int j = 9; j >= i; j--){
					if (getEncodeString("place" + j) != null){
						setEncodeString("place" + (j + 1), getEncodeString("place" + j));
					} else {
						setEncodeString("place" + (j + 1), "0");
					}
				}
				setEncodeString(keyName, "" + topScoreValue);
				break;
			}
		}
	}
}
