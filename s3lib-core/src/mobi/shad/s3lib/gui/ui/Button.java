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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;

import java.util.ArrayList;

/**
 * Created by Jarek on 2014-05-18.
 */
public class Button extends com.badlogic.gdx.scenes.scene2d.ui.Button implements GuiDefinition{
	public Button(Skin skin){
		super(skin);
	}

	public Button(Skin skin, String styleName){
		super(skin, styleName);
	}

	public Button(Actor child, Skin skin, String styleName){
		super(child, skin, styleName);
	}

	public Button(Actor child, ButtonStyle style){
		super(child, style);
	}

	public Button(ButtonStyle style){
		super(style);
	}

	public Button(){
		super();
	}

	public Button(Drawable up){
		super(up);
	}

	public Button(Drawable up, Drawable down){
		super(up, down);
	}

	public Button(Drawable up, Drawable down, Drawable checked){
		super(up, down, checked);
	}

	public Button(Actor child, Skin skin){
		super(child, skin);
	}

	@Override
	public void setName(String name){
		super.setName(name);
		final SnapshotArray<Actor> children = getChildren();
		for (Actor child : children){
			child.setName(name);
		}

	}

	@Override
	public ArrayList<String[]> getGuiDefinition(){

		ArrayList<String[]> guiDef = new ArrayList<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
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
