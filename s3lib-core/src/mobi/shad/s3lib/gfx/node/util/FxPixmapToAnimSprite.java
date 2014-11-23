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
package mobi.shad.s3lib.gfx.node.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.g2d.SpriteAnim;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class FxPixmapToAnimSprite extends Node{

	private int gridX = 16;
	private int gridY = 16;
	private int oldWidth = 0;
	private int oldHeight = 0;
	private int borderWidth = 1;
	private SpriteAnim spriteAnim;
	private float speedSizeX = 1.0f;
	private float speedSizeY = 1.0f;
	private float startSizeX = 5.0f;
	private float startSizeY = 5.0f;
	private float amplitudeSizeX = 10.0f;
	private float amplitudeSizeY = 10.0f;
	private float speedPositionX = 1.0f;
	private float speedPositionY = 1.0f;
	private float amplitudePositionX = 10.0f;
	private float amplitudePositionY = 10.0f;
	private int interpolationMode = 0;
	private int transformMode = 0;
	private Interpolation interpolation = Interpolation.linear;
	private String fileTextureName;

	public FxPixmapToAnimSprite(){
		this(null, null);
	}

	/**
	 * @param effectData
	 * @param changeListener
	 */
	public FxPixmapToAnimSprite(GuiForm effectData, ChangeListener changeListener){
		super("FxPixmapToSprite_" + countId, Type.TEXTURE, effectData, changeListener);
		initData();
		initForm();
	}

	/**
	 *
	 */
	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;
		formGui.addLabel(S3Lang.get("FxPixmapToSprite"), S3Lang.get("FxPixmapToSprite"), Color.YELLOW);

		String[] transformTextMode = {S3Lang.get("noTransform"),
									  S3Lang.get("transformSize"), S3Lang.get("transformPosistion"),
									  S3Lang.get("transformWobbingSize"), S3Lang.get("transformWobbingPosistion"),
									  S3Lang.get("transformWobbingSizeAndPosistion")
		};
		formGui.addSelectIndex("transformMode", S3Lang.get("transformMode"), 0, transformTextMode, localChangeListener);

		String[] transformTextInterpolation = {S3Lang.get("interpolationLinear"),
											   S3Lang.get("interpolationBounce"), S3Lang.get("interpolationPow2"),
											   S3Lang.get("interpolationPow3"), S3Lang.get("interpolationElastic"),
											   S3Lang.get("interpolationSwing"), S3Lang.get("interpolationSine"),
											   S3Lang.get("interpolationBounceIn"), S3Lang.get("interpolationBounceOut"),};
		formGui.addSelectIndex("interpolationMode", S3Lang.get("interpolationMode"), 0, transformTextInterpolation, localChangeListener);

		formGui.add("speedSizeX", S3Lang.get("speedSizeX"), 0, -5f, 5f, 0.1f, localChangeListener);
		formGui.add("speedSizeY", S3Lang.get("speedSizeY"), 0, -5f, 5f, 0.1f, localChangeListener);

		formGui.add("startSizeX", S3Lang.get("startSizeX"), 0, 0f, 30f, 0.1f, localChangeListener);
		formGui.add("startSizeY", S3Lang.get("startSizeY"), 0, 0f, 30f, 0.1f, localChangeListener);

		formGui.add("amplitudeSizeX", S3Lang.get("amplitudeSizeX"), 0, 0f, 60f, 0.1f, localChangeListener);
		formGui.add("amplitudeSizeY", S3Lang.get("amplitudeSizeY"), 0, 0f, 60f, 0.1f, localChangeListener);

		formGui.addRandomButton("fxTextPlanerandom1", S3Lang.get("random"),
								new String[]{"speedSizeX", "speedSizeY", "startSizeX", "startSizeY", "amplitudeSizeX", "amplitudeSizeY"}, localChangeListener);
		formGui.addResetButton("fxTextPlanereset1", S3Lang.get("reset"),
							   new String[]{"speedSizeX", "speedSizeY", "startSizeX", "startSizeY", "amplitudeSizeX", "amplitudeSizeY"}, localChangeListener);

		formGui.add("speedPositionX", S3Lang.get("speedPositionX"), 0, -5f, 5f, 0.1f, localChangeListener);
		formGui.add("speedPositionY", S3Lang.get("speedPositionY"), 0, -5f, 5f, 0.1f, localChangeListener);

		formGui.add("amplitudePositionX", S3Lang.get("amplitudePositionX"), 0, -30f, 30f, 0.1f, localChangeListener);
		formGui.add("amplitudePositionY", S3Lang.get("amplitudePositionY"), 0, -30f, 30f, 0.1f, localChangeListener);

		formGui.addRandomButton("fxTextPlanerandom2", S3Lang.get("random"),
								new String[]{"speedPositionX", "speedPositionY", "amplitudePositionX", "amplitudePositionY"}, localChangeListener);
		formGui.addResetButton("fxTextPlanereset2", S3Lang.get("reset"),
							   new String[]{"speedPositionX", "speedPositionY", "amplitudePositionX", "amplitudePositionY"}, localChangeListener);

		formGui.addFileBrowser("FxTextureLoaderFile", "Texture File", "sprite/bobs8.png", "sprite", localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){

			speedSizeX = formGui.getFloat("speedSizeX") * S3Screen.aspectRatioX;
			speedSizeY = formGui.getFloat("speedSizeY") * S3Screen.aspectRatioY;

			startSizeX = formGui.getFloat("startSizeX") * S3Screen.aspectRatioX;
			startSizeY = formGui.getFloat("startSizeY") * S3Screen.aspectRatioY;

			amplitudeSizeX = formGui.getFloat("amplitudeSizeX") * S3Screen.aspectRatioX;
			amplitudeSizeY = formGui.getFloat("amplitudeSizeY") * S3Screen.aspectRatioY;

			speedPositionX = formGui.getFloat("speedPositionX") * S3Screen.aspectRatioX;
			speedPositionY = formGui.getFloat("speedPositionY") * S3Screen.aspectRatioY;

			amplitudePositionX = formGui.getFloat("amplitudePositionX") * S3Screen.aspectRatioX;
			amplitudePositionY = formGui.getFloat("amplitudePositionY") * S3Screen.aspectRatioY;

			transformMode = formGui.getInt("transformMode");
			interpolationMode = formGui.getInt("interpolationMode");

			switch (interpolationMode){
				default:
					interpolation = Interpolation.linear;
					break;
				case 1:
					interpolation = Interpolation.bounce;
					break;
				case 2:
					interpolation = Interpolation.pow2;
					break;
				case 3:
					interpolation = Interpolation.pow3;
					break;
				case 4:
					interpolation = Interpolation.elastic;
					break;
				case 5:
					interpolation = Interpolation.swing;
					break;
				case 6:
					interpolation = Interpolation.sine;
					break;
				case 7:
					interpolation = Interpolation.bounceIn;
					break;
				case 8:
					interpolation = Interpolation.bounceOut;
					break;
			}
			fileTextureName = formGui.getString("FxTextureLoaderFile");

		}
		if (data.pixmap == null){
			return;
		}

		data.spriteTexture = S3ResourceManager.getTexture(fileTextureName, 32);
		updateLocal(0, 0, 0, 0, false);
	}

	@Override
	protected void updateLocal(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
		if (isPause){
			return;
		}
		if (!data.textureChange || data.pixmap == null){
			return;
		}

		data.type = Data.Type.EFFECT_2D;
		data.textureChange = false;

		int width = data.pixmap.getWidth();
		int height = data.pixmap.getHeight();

		data.spriteWidth = (int) (S3Screen.width / width) + 1;
		data.spriteHeight = (int) (S3Screen.height / height) + 1;

		if (width != oldWidth || height != oldHeight){
			data.spritePosition = new float[width * height * 2];
			data.spriteColor = new float[width * height * 4];
			data.spriteSize = new float[width * height * 4];
			oldWidth = width;
			oldHeight = height;

			for (int y = 0; y < height; y++){
				for (int x = 0; x < width; x++){
					int index = x + y * width;
					data.spritePosition[index * 2] = x * data.spriteWidth;
					data.spritePosition[index * 2 + 1] = y * data.spriteHeight;

					data.spriteSize[index * 2] = data.spriteWidth;
					data.spriteSize[index * 2 + 1] = data.spriteHeight;
				}
			}

			spriteAnim = new SpriteAnim(data.spritePosition, data.spriteSize);
		}

		switch (transformMode){
			default:
				break;
			case 1:
				spriteAnim.size(effectTime * speedSizeX, startSizeX, amplitudeSizeX, interpolation);
				break;
			case 2:
				spriteAnim.position(effectTime * speedPositionX, effectTime * speedPositionY, 0, 0, amplitudePositionX, amplitudePositionY, interpolation);
				break;
			case 3:
				spriteAnim.wobblingSize(effectTime, speedSizeX, startSizeX, amplitudeSizeX, interpolation);
				break;
			case 4:
				spriteAnim.wobblingPosition(effectTime, speedPositionX, speedPositionY, 0, 0, amplitudePositionX, amplitudePositionY, interpolation);
				break;
			case 5:
				spriteAnim.wobblingSizeAndPosition(effectTime, speedSizeX, speedSizeY, speedPositionX, speedPositionY, startSizeX, startSizeY, amplitudeSizeX,
												   amplitudeSizeY, 0, 0, amplitudePositionX, amplitudePositionY, interpolation, interpolation);
				break;
		}

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				int rgb = data.pixmap.getPixel(x, y);
				int r = (rgb & 0xff000000) >>> 24;
				int g = (rgb & 0x00ff0000) >>> 16;
				int b = (rgb & 0x0000ff00) >>> 8;
				int a = (rgb & 0x000000ff);

				int index = x + y * width;
				data.spriteColor[index * 4] = (float) r / 255;
				data.spriteColor[index * 4 + 1] = (float) g / 255;
				data.spriteColor[index * 4 + 2] = (float) b / 255;
				data.spriteColor[index * 4 + 3] = (float) a / 255;
			}
		}
	}
}