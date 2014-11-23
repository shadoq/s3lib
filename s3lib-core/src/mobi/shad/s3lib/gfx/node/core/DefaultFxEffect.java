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
package mobi.shad.s3lib.gfx.node.core;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import mobi.shad.s3lib.gui.GuiForm;

import java.util.ArrayList;

/**
 * @author Jarek
 */
public final class DefaultFxEffect extends FxEffect{

	public DefaultFxEffect(){
		initData();
		init();
	}

	/**
	 *
	 */
	private void initData(){
		fxFilter = new Node("DefaultFxEffect");
	}

	/**
	 *
	 */
	public void clearFxFilter(){
		formData.clean();
		initData();
		init();
	}

	/**
	 * @param filter
	 */
	public Node addFxFilter(Node filter){
		if (filter != null){
			fxFilter.setFormGui(formData, changeListener);
			fxFilter.addChild(filter);
		}
		return filter;
	}

	public Node getFxFilter(){
		return fxFilter;
	}

	public GuiForm getFormData(){
		return formData;
	}

	public ChangeListener getChangeListener(){
		return changeListener;
	}

	@Override
	public void getGuiDefinition(ArrayList<String[]> guiDef){

	}

	@Override
	public void getValues(ArrayMap<String, String> values){

	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

	}

	@Override
	public void read(Json json, JsonValue jsonData){

	}

	@Override
	public void write(Json json, Object objectWrite){

	}
}
