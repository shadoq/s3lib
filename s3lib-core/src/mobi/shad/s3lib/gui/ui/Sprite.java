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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;

import java.util.ArrayList;
import java.util.Arrays;

public class Sprite extends Actor implements GuiDefinition{

	private static final String TAG = "Sprite";
	protected final Vector2 position = new Vector2();
	protected final Vector2 center = new Vector2();
	protected final Vector2 size = new Vector2();
	protected final Rectangle rectangle = new Rectangle();
	public boolean isAnimationActive = true;
	public boolean isAnimationLooping = true;
	public boolean isSingleImage = false;
	protected Array<String> textures = new Array<String>();
	protected Animation animation;
	protected TextureRegion keyFrame;
	protected float duration = 0;
	protected int frameCount = 1;

	public Sprite(String texName, int frameCount){
		textures.add(texName);
		setFrameCount(frameCount);
	}

	public Sprite(String texName, int frameCount, float duration){
		textures.add(texName);
		setFrameCount(frameCount, duration);
	}

	public Sprite(String... texNames){
		setTextures(texNames);
	}

	public Sprite(float duration, String... texNames){
		setTextures(duration, texNames);
		this.duration = duration;
	}

	public Sprite(String textureName, int frameCount, float duration, boolean single){
		isSingleImage = single;
		setTextures(textureName);
		setFrameCount(frameCount, duration);
	}

	public Sprite(float duration, boolean single, String... texNames){
		isSingleImage = single;
		setTextures(duration, texNames);
		this.duration = duration;
	}

	@Override
	public void draw(Batch batch, float parentAlpha){

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		if (isAnimationActive && animation != null){
			keyFrame = animation.getKeyFrame(S3.appTime, isAnimationLooping);
			batch.draw(keyFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					   getScaleX(), getScaleY(), getRotation());
		} else if (keyFrame != null){
			batch.draw(keyFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					   getScaleX(), getScaleY(), getRotation());
		}
	}

	public Array<String> getTextures(){
		return textures;
	}

	public void setTextures(String... texNames){
		setTextures(duration, texNames);
	}

	public void setTextures(float duration, String... texNames){

		S3Log.info(TAG, "Set texture: duration: " + duration + " names: " + Arrays.toString(texNames));

		if (texNames.length == 0){
			return;
		}

		textures.clear();

		if (isSingleImage && texNames.length == 1){
			textures.add(texNames[0].trim());
			Array<TextureRegion> textureRegions = new Array<TextureRegion>();
			int textCount = 1;
			if (frameCount == 0){
				frameCount = texNames.length;
			}
			for (int i = 1; i < frameCount + 1; i++){
				if (!texNames[0].trim().isEmpty()){
					if (S3ResourceManager.getTextureRegion(texNames[0].trim(), i) != null){
						textureRegions.add(S3ResourceManager.getTextureRegion(texNames[0].trim(), i));
						textCount++;
					}
				}
			}
			float tmpDuration = 0;
			if (duration > 0){
				tmpDuration = duration / 10;
			} else {
				tmpDuration = 1f / textCount;
			}
			animation = new Animation(tmpDuration, textureRegions);
			if (textureRegions.size == 0 && texNames.length == 1){
				textures.add(texNames[0]);
				animation = S3ResourceManager.getAnimation(texNames[0], frameCount, tmpDuration);
				setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
				return;
			}

			setSize(textureRegions.first().getRegionWidth(), textureRegions.first().getRegionHeight());
		} else {
			float tmpDuration = 0;
			if (duration > 0){
				tmpDuration = duration / 10;
			} else {
				tmpDuration = 1f / texNames.length;
			}

			if (texNames.length == 1){
				textures.add(texNames[0]);
				animation = S3ResourceManager.getAnimation(texNames[0], frameCount, tmpDuration);
				setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
				return;
			}
			Array<TextureRegion> textureRegions = new Array<TextureRegion>();
			for (int i = 0; i < texNames.length; i++){
				if (!texNames[i].trim().isEmpty()){
					textureRegions.add(S3ResourceManager.getTextureRegion(texNames[i]));
					textures.add(texNames[i]);
				}
			}
			animation = new Animation(tmpDuration, textureRegions);
			setSize(textureRegions.first().getRegionWidth(), textureRegions.first().getRegionHeight());
		}
	}

	public float getDuration(){
		return this.duration;
	}

	public void setDuration(float duration){
		this.duration = duration;
		if (isSingleImage){
			setTextures(duration, textures.get(0));
		} else {
			StringBuilder sb = new StringBuilder();
			for (String s : textures){
				sb.append(s);
				sb.append(",");
			}
			setTextures(duration, sb.toString().split(","));
		}
	}

	public void setTexture(Texture texture){
		duration = 0;
		animation = null;
		keyFrame = new TextureRegion(texture);
	}

	public void setDrawable(TextureRegionDrawable drawable){
		duration = 0;
		animation = null;
		keyFrame = drawable.getRegion();
	}


	public int getFrameCount(){
		return frameCount;
	}

	public void setFrameCount(int count){
		frameCount = count;
		if (isSingleImage){
			setTextures(textures.get(0));
		} else {
			animation = S3ResourceManager.getAnimation(textures.get(0), frameCount);
			setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
		}
	}

	public void setFrameCount(int count, float duration){
		frameCount = count;
		animation = S3ResourceManager.getAnimation(textures.get(0), frameCount, duration);
		setSize(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (String tex : textures){
			sb.append(tex);
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}

	@Override
	public ArrayList<String[]> getGuiDefinition(){
		ArrayList<String[]> guiDef = new ArrayList<>();
		guiDef.add(new String[]{this.getClass().getSimpleName(), "", "LABEL", ""});
		guiDef.add(new String[]{"textures", "textures", "TEXTURE_LIST", ""});
		guiDef.add(new String[]{"duration", "duration", "SPINNER", "1.0"});
		guiDef.add(new String[]{"active", "active", "CHECKBOX", "true"});
		guiDef.add(new String[]{"looping", "looping", "CHECKBOX", "true"});
		guiDef.add(new String[]{"single", "single", "CHECKBOX", "true"});
		guiDef.add(new String[]{"frameCount", "frameCount", "SPINNER_INT", "1"});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();

		values.put("textures", toString());
		values.put("duration", getDuration() + "");
		values.put("active", isAnimationActive + "");
		values.put("looping", isAnimationLooping + "");
		values.put("single", isSingleImage + "");
		values.put("frameCount", getFrameCount() + "");

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

		isAnimationActive = Boolean.parseBoolean(values.get("active"));
		isAnimationLooping = Boolean.parseBoolean(values.get("looping"));
		isSingleImage = Boolean.parseBoolean(values.get("single"));

		if (changeKey.equals("textures")){
			if (values.get("textures").contains(",")){
				final String[] textureses = values.get("textures").split(",");
				setTextures(textureses);
			} else {
				setTextures(values.get("textures"));
			}
		}

		setFrameCount(Integer.parseInt(values.get("frameCount")));

		float tmpDur = Float.parseFloat(values.get("duration"));
		if (tmpDur < 0){
			tmpDur = 0.0f;
		}
		setDuration(tmpDur);

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

	@Override
	public void setX(float x){
		this.position.x = x;
		super.setX(x);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void setY(float y){
		this.position.y = y;
		super.setY(y);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void sizeBy(float size){
		super.sizeBy(size);
		this.size.add(size, size);
		center.set(position.x + this.size.x / 2, position.y + this.size.y / 2);
		this.rectangle.set(position.x, position.y, this.size.x, this.size.y);
	}

	@Override
	public void sizeBy(float width, float height){
		super.sizeBy(width, height);
		this.size.add(width, height);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void setHeight(float height){
		super.setHeight(height);
		this.size.y = height;
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void setWidth(float width){
		super.setWidth(width);
		this.size.x = width;
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}


	public void setCenter(float x, float y){
		center.set(x, y);
		position.set(x - size.x / 2, y - size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
		super.setX(position.x);
		super.setY(position.y);
	}

	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		position.set(x, y);
		center.set(position.x + size.x / 2, position.y + size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void setCenterPosition(float x, float y){
		super.setCenterPosition(x, y);
		center.set(x, y);
		position.set(x - size.x / 2, y - size.y / 2);
		this.rectangle.set(position.x, position.y, size.x, size.y);
	}

	public Vector2 getPosition(){
		return position;
	}

	public Vector2 getCenter(){
		return center;
	}

	public Vector2 getSize(){
		return size;
	}

	public Rectangle getRectangle(){
		return rectangle;
	}
}
