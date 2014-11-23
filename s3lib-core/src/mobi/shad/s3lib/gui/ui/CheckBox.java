/*******************************************************************************
 * Copyright 2014
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
package mobi.shad.s3lib.gui.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ArrayMap;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;

import java.util.ArrayList;

/**
 * Created by Jarek on 2014-06-13.
 */
public class CheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox implements GuiDefinition{

	protected String styleName = "default";

	public CheckBox(String text, Skin skin){
		super(text, skin);
	}

	public CheckBox(String text, Skin skin, String styleName){
		super(text, skin, styleName);
		this.styleName = styleName;
	}

	public CheckBox(String text, CheckBoxStyle style){
		super(text, style);
	}

	public String getStyleName(){
		return styleName;
	}

	public void setStyleName(String styleName){
		this.styleName = styleName;
	}

	@Override
	public ArrayList<String[]> getGuiDefinition(){

		ArrayList<String[]> guiDef = new ArrayList<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"state", "state", "CHECKBOX", "false"});
		guiDef.add(new String[]{"text", "text", "TEXT", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("state", isChecked() + "");
		values.put("text", getText().toString());
		values.put("name", getName());
		values.put("x", getX() + "");
		values.put("y", getY() + "");
		values.put("width", getWidth() + "");
		values.put("height", getHeight() + "");
		values.put("originX", getOriginX() + "");
		values.put("originY", getOriginY() + "");
		values.put("rotation", getRotation() + "");
		values.put("color", getColor().toString());
		values.put("touchable", getTouchable() + "");
		values.put("visible", isVisible() + "");
		values.put("zindex", getZIndex() + "");
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		setChecked(Boolean.valueOf(values.get("state")));
		setText(values.get("text"));
		setName(values.get("name"));
		setX(Float.valueOf(values.get("x")));
		setY(Float.valueOf(values.get("y")));
		setWidth(Float.valueOf(values.get("width")));
		setHeight(Float.valueOf(values.get("height")));
		setOriginX(Float.valueOf(values.get("originX")));
		setOriginY(Float.valueOf(values.get("originY")));
		setRotation(Float.valueOf(values.get("rotation")));
		setColor(Color.valueOf(values.get("color")));
		setTouchable(Touchable.valueOf(values.get("touchable")));
		setVisible(Boolean.valueOf(values.get("visible")));
		setZIndex((int) (float) (Integer.valueOf(values.get("zindex"))));
	}
}
