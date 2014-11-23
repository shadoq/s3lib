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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;

import java.util.ArrayList;

/**
 * Created by Jarek on 2014-06-06.
 */
public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton implements GuiDefinition{

	private String textureName = "";

	public ImageButton(Skin skin){
		super(skin);
	}

	public ImageButton(Skin skin, String styleName){
		super(skin, styleName);
	}

	public ImageButton(ImageButtonStyle style){
		super(style);
	}

	public ImageButton(Drawable imageUp){
		super(imageUp);
	}

	public ImageButton(Drawable imageUp, Drawable imageDown){
		super(imageUp, imageDown);
	}

	public ImageButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked){
		super(imageUp, imageDown, imageChecked);
	}

	public ImageButton(Skin skin, Texture textureUp, String textureName, String styleName){
		super(skin, styleName);
		this.textureName = textureName;
		final ImageButtonStyle style = getStyle();
		style.imageUp = new TextureRegionDrawable(new TextureRegion(textureUp));
		setStyle(style);
	}

	public ImageButton(Skin skin, TextureRegion textureUp, String textureName, String styleName){
		super(skin, styleName);
		this.textureName = textureName;
		final ImageButtonStyle style = getStyle();
		style.imageUp = new TextureRegionDrawable(textureUp);
		setStyle(style);
	}

	public String getTextureName(){
		return textureName;
	}

	public void setTextureName(String textureName){
		this.textureName = textureName;
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
		guiDef.add(new String[]{"texture", "texture", "TEXTURE_LIST", ""});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();

		values.put("texture", getTextureName());
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

		if (changeKey.equals("texture")){
			textureName = values.get("texture");
			final ImageButtonStyle style = getStyle();
			style.imageUp = new TextureRegionDrawable(S3ResourceManager.getTextureRegion(textureName));
			setStyle(style);
		}

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
