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
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Scaling;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;

import java.util.ArrayList;

/**
 * Created by Jarek on 2014-05-18.
 */
public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image implements GuiDefinition{

	private String textureName = "";

	public Image(){
		super();
	}

	public Image(NinePatch patch){
		super(patch);
	}

	public Image(TextureRegion region){
		super(region);
	}

	public Image(Texture texture){
		super(texture);
	}

	public Image(Texture texture, String textureName){
		super(texture);
		this.textureName = textureName;
	}

	public Image(TextureRegion region, String textureName){
		super(region);
		this.textureName = textureName;
	}

	public Image(Skin skin, String drawableName){
		super(skin, drawableName);
		this.textureName = drawableName;
	}

	public Image(Drawable drawable){
		super(drawable);
	}

	public Image(Drawable drawable, Scaling scaling){
		super(drawable, scaling);
	}

	public Image(Drawable drawable, Scaling scaling, int align){
		super(drawable, scaling, align);
	}

	public String getTextureName(){
		return textureName;
	}

	public void setTextureName(String textureName){
		this.textureName = textureName;
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
			setDrawable(new TextureRegionDrawable(new TextureRegion(S3ResourceManager.getTextureRegion(textureName))));
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
