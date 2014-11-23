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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;

import java.util.ArrayList;

/**
 * Created by Jarek on 2014-05-18.
 */
public class Label extends com.badlogic.gdx.scenes.scene2d.ui.Label implements GuiDefinition{

	private String styleName = "default";
	private Skin skin;
	private boolean wrap = false;
	private String align = "left";

	private String fontName = "None";
	private float scaleX = 1.0f;
	private float scaleY = 1.0f;

	public Label(CharSequence text, Skin skin){
		super(text, skin);
		this.skin = skin;
		setWrap(wrap);
	}

	public Label(CharSequence text, Skin skin, String styleName){
		super(text, skin, styleName);
		this.skin = skin;
		this.styleName = styleName;
		setWrap(wrap);
		setAlign(align);
	}

	public Label(CharSequence text, Skin skin, String fontName, Color color){
		super(text, skin, fontName, color);
		this.skin = skin;
		setWrap(wrap);
		setAlign(align);
	}

	public Label(CharSequence text, Skin skin, String fontName, String colorName){
		super(text, skin, fontName, colorName);
		this.skin = skin;
		setWrap(wrap);
		setAlign(align);
	}

	public Label(CharSequence text, LabelStyle style){
		super(text, style);
		setWrap(wrap);
		setAlign(align);
	}

	public Label(CharSequence text, LabelStyle style, String styleName){
		super(text, style);
		setWrap(wrap);
		this.styleName = styleName;
		setAlign(align);
	}

	public static void getGuiDefinition(ArrayList<String[]> guiDef){
		guiDef.add(new String[]{"Actor", "", "LABEL", ""});
		guiDef.add(new String[]{"x", "x", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"y", "y", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"width", "width", "SPINNER_INT", "10"});
		guiDef.add(new String[]{"height", "height", "SPINNER_INT", "10"});
		guiDef.add(new String[]{"originX", "originX", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"originY", "originY", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"rotation", "rotation", "SPINNER_INT", "0"});
		guiDef.add(new String[]{"color", "color", "COLOR", "000000ff"});
		guiDef.add(new String[]{"touchable", "touchable", "LIST", "true", "enabled,disabled,childrenOnly"});
		guiDef.add(new String[]{"visible", "visible", "CHECKBOX", "true"});
		guiDef.add(new String[]{"zindex", "zindex", "SPINNER_INT", "0"});
	}

	public String getStyleName(){
		return styleName;
	}

	public void setStyleName(String styleName){
		this.styleName = styleName;
		if (skin == null){
			skin = S3.skin;
		}
		setStyle(skin.get(styleName, LabelStyle.class));
	}

	public boolean isWrap(){
		return wrap;
	}

	@Override
	public void setWrap(boolean wrap){
		this.wrap = wrap;
		super.setWrap(wrap);
	}

	public String getAlign(){
		return align;
	}

	public void setAlign(String align){
		if (align == null){
			align = "left";
		}
		this.align = align;
		if (align.equalsIgnoreCase("left")){
			setAlignment(Align.left);
		} else if (align.equalsIgnoreCase("center")){
			setAlignment(Align.center);
		} else if (align.equalsIgnoreCase("right")){
			setAlignment(Align.right);
		}
	}

	public String getFontName(){
		return fontName;
	}

	public void setFontName(String fontName){
		this.fontName = fontName;
		if (fontName != null && !fontName.equals("None") && !fontName.equals("")){
			final LabelStyle style = getStyle();
			style.font = S3ResourceManager.getBitmapFont(fontName);
			if (style.font != null){
				setStyle(style);
			}
		} else {
			setStyleName(styleName);
		}
	}

	public float getScaleX(){
		return scaleX;
	}

	public void setScaleX(float scaleX){
		this.scaleX = scaleX;
	}

	public float getScaleY(){
		return scaleY;
	}

	public void setScaleY(float scaleY){
		this.scaleY = scaleY;
	}

	@Override
	public ArrayList<String[]> getGuiDefinition(){

		ArrayList<String[]> guiDef = new ArrayList<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"text", "text", "TEXT", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		guiDef.add(
		new String[]{"styleName", "styleName", "LIST", "default", "default,default-light,default-opaque,default-light-opaque,default-title,default-title-opaque"});
		guiDef.add(new String[]{"wrap", "wrap", "CHECKBOX", "false"});
		guiDef.add(new String[]{"align", "align", "LIST", "left", "left,center,right"});

		guiDef.add(new String[]{"font", "font", "FONT_LIST", ""});
		guiDef.add(new String[]{"fontScaleX", "fontScaleX", "SPINNER", "1.0"});
		guiDef.add(new String[]{"fontScaleY", "fontScaleY", "SPINNER", "1.0"});

		getGuiDefinition(guiDef);
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
		values.put("wrap", isWrap() + "");
		values.put("align", getAlign());

		values.put("font", fontName);
		values.put("fontScaleX", scaleX + "");
		values.put("fontScaleY", scaleY + "");

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
			scaleX = Float.valueOf(values.get("fontScaleX"));
			setFontScaleX(scaleX);
		}
		if (changeKey.equals("fontScaleY")){
			scaleY = Float.valueOf(values.get("fontScaleY"));
			setFontScaleY(scaleY);
		}

		setAlign(values.get("align"));
		setWrap(Boolean.valueOf(values.get("wrap")));
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
