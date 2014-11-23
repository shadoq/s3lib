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
package mobi.shad.s3lib.gfx.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Simple dummy effect
 */
public class Wait extends AbstractEffect{

	/**
	 *
	 */
	public Wait(){
		init();
	}

	/**
	 *
	 */
	@Override
	public final void init(){
	}

	/**
	 *
	 */
	@Override
	public void stop(){
	}

	/**
	 *
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
	}

	/**
	 *
	 * @param batch
	 * @param parentAlpha
	 */
	@Override
	public void render(Batch batch, float parentAlpha){
	}

	/**
	 *
	 */
	@Override
	public void start(){
	}

	/**
	 *
	 */
	@Override
	public void preRender(){
	}

	/**
	 *
	 */
	@Override
	public void postRender(){
	}

	/**
	 *
	 * @param guiDef
	 */
	@Override
	public void getGuiDefinition(ArrayList<String[]> guiDef){

	}

	/**
	 *
	 * @param values
	 */
	@Override
	public void getValues(ArrayMap<String, String> values){

	}

	/**
	 *
	 * @param changeKey
	 * @param values
	 */
	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

	}

	/**
	 *
	 * @param json
	 * @param jsonData
	 */
	@Override
	public void read(Json json, JsonValue jsonData){

	}

	/**
	 *
	 * @param json
	 * @param objectWrite
	 */
	@Override
	public void write(Json json, Object objectWrite){

	}
}
