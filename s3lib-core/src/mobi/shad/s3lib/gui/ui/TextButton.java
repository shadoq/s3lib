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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;

import java.util.ArrayList;

/**
 * Created by Jarek on 2014-06-06.
 */
public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton implements GuiDefinition{

	protected String styleName = "default";
	protected Skin skin;
	protected boolean wrap = false;
	protected String align = "left";

	protected String fontName = "None";
	protected float fontScaleX = 1.0f;
	protected float fontScaleY = 1.0f;

	public TextButton(String text, Skin skin){
		super(text, skin);
		this.skin = skin;
		setLabelWrap(wrap);
	}

	public TextButton(String text, Skin skin, String styleName){
		super(text, skin, styleName);
		this.styleName = styleName;
		this.skin = skin;
		setLabelWrap(wrap);
		setLabelAlign(align);
	}

	public TextButton(String text, TextButtonStyle style){
		super(text, style);
		setLabelWrap(wrap);
		setLabelAlign(align);
	}

	public String getStyleName(){
		return styleName;
	}

	public void setStyleName(String styleName){
		this.styleName = styleName;
		if (skin == null){
			skin = S3.skin;
		}
		setStyle(skin.get(styleName, TextButtonStyle.class));
	}

	@Override
	public void setName(String name){
		super.setName(name);
		final SnapshotArray<Actor> children = getChildren();
		for (Actor child : children){
			child.setName(name);
		}
	}

	public boolean isLabelWrap(){
		return wrap;
	}

	public void setLabelWrap(boolean wrap){
		this.wrap = wrap;
		getLabel().setWrap(wrap);
	}

	public String getLabelAlign(){
		return align;
	}

	public void setLabelAlign(String align){
		if (align == null){
			align = "left";
		}
		this.align = align;
		if (align.equalsIgnoreCase("left")){
			getLabel().setAlignment(Align.left);
		} else if (align.equalsIgnoreCase("center")){
			getLabel().setAlignment(Align.center);
		} else if (align.equalsIgnoreCase("right")){
			getLabel().setAlignment(Align.right);
		}
	}

	public String getFontName(){
		return fontName;
	}

	public void setFontName(String fontName){
		this.fontName = fontName;

		this.fontName = fontName;
		if (fontName != null && !fontName.equals("None") && !fontName.equals("")){
			final TextButtonStyle textButtonStyle = getStyle();
			textButtonStyle.font = S3ResourceManager.getBitmapFont(fontName);
			setStyle(textButtonStyle);
		}
	}

	public float getFontScaleX(){
		return fontScaleX;
	}

	public void setFontScaleX(float fontScaleX){
		this.fontScaleX = fontScaleX;
		getLabel().setFontScaleX(fontScaleX);
	}

	public float getFontScaleY(){
		return fontScaleY;
	}

	public void setFontScaleY(float fontScaleY){
		this.fontScaleY = fontScaleY;
		getLabel().setFontScaleY(fontScaleY);
	}

	@Override
	public ArrayList<String[]> getGuiDefinition(){
		ArrayList<String[]> guiDef = new ArrayList<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"text", "text", "TEXT", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		guiDef.add(
		new String[]{"styleName", "styleName", "LIST", "default", "default,toggle,default-light,toggle-light,default-title,toggle-title,noborder,tab"});
		guiDef.add(new String[]{"wrap", "wrap", "CHECKBOX", "false"});
		guiDef.add(new String[]{"align", "align", "LIST", "left", "left,center,right"});

		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});
		guiDef.add(new String[]{"fontScaleX", "fontScaleX", "SPINNER", "1.0"});
		guiDef.add(new String[]{"fontScaleY", "fontScaleY", "SPINNER", "1.0"});

		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
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

		values.put("styleName", getStyleName());
		values.put("wrap", isLabelWrap() + "");
		values.put("align", getLabelAlign());

		values.put("font", getFontName());
		values.put("fontScaleX", getFontScaleX() + "");
		values.put("fontScaleY", getFontScaleY() + "");

		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}
		if (changeKey.equals("styleName")){
			setStyleName(values.get("styleName"));
		}

		if (changeKey.equals("font")){
			setFontName(values.get("font"));
		}
		if (changeKey.equals("fontScaleX")){
			fontScaleX = Float.valueOf(values.get("fontScaleX"));
			setFontScaleX(fontScaleX);
		}
		if (changeKey.equals("fontScaleY")){
			fontScaleY = Float.valueOf(values.get("fontScaleY"));
			setFontScaleY(fontScaleY);
		}

		setLabelAlign(values.get("align"));
		setLabelWrap(Boolean.valueOf(values.get("wrap")));

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
