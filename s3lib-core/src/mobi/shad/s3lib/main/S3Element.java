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

import java.lang.reflect.Field;

/**
 * @author Jarek
 */
public class S3Element{

	private final static String TAG = "S3Element";
	private Object obj = null;
	private Field fld = null;
	private String fieldName = "";

	/**
	 * @param obj       - A reference object to override
	 * @param fieldName - field name to modify the variable
	 */
	public S3Element(Object obj, String fieldName){
		this.obj = obj;
		this.fieldName = fieldName;
		this.fld = findField(obj.getClass(), fieldName);
	}

	private static Field findField(Class clazz, String filedName){

		if (S3Constans.NOTICE){
			S3Log.info(TAG, "Find field: " + clazz.getSimpleName() + " field: " + filedName);
		}

		if (clazz != null && !clazz.getSimpleName().equalsIgnoreCase("class")){
			Field[] declaredFields = clazz.getDeclaredFields();
			for (int i = 0; i < declaredFields.length; i++){
				Field field = declaredFields[i];
				if (field.getName().equals(filedName)){
					field.setAccessible(true);
					return field;
				}
			}

			Class superClass = clazz.getSuperclass();
			if (superClass != null){
				return findField(superClass, filedName);
			}
		}
		throw new RuntimeException("No find field: " + filedName);
	}

	/**
	 * @return
	 */
	public Object getValue(){
		try {
			return fld.get(obj);
		} catch (Exception e){
			throw new RuntimeException(
			"Error getValue for field: " + fieldName + " in object" + obj.getClass().getName(), e);
		}
	}

	/**
	 * Setting a new field value
	 *
	 * @param value
	 */
	public void setValue(Object value){
		try {
			if (S3Constans.NOTICE){
				System.out.println("setValue for field: " + fieldName + " in object  " + obj.getClass()
																							.getName() + " to " + value.toString());
			}
			fld.set(obj, value);
		} catch (Exception e){
			throw new RuntimeException(
			"Error setValue for field: " + fieldName + " in object" + obj.getClass().getName(), e);
		}
	}
}
