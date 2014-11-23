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
package mobi.shad.s3lib.gfx.node.filter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.pixmap.filter.MultiplyMask;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class FxTextureGalleryMask extends Node{

	private String fileTextureName;
	private String nextFileTextureName;
	private String[] filesList;
	private String maskTextureName;
	private float actionTime = 5;
	private float pauseTime = 5;
	private Interpolation interpolation;
	private int interpolationMode;
	private int currentImageIdx = 1;
	private float currentActionTime = 0;
	private float currentPauseTime = 0;
	private Pixmap srcTexture;
	private Pixmap src2Texture;
	private Pixmap maskTexture;
	private Pixmap tmpPix;
	private Pixmap clearPixmap;
	private MultiplyMask pixmapFilter = new MultiplyMask();

	public FxTextureGalleryMask(){
		this(null, null);
	}

	/**
	 *
	 * @param effectData
	 * @param changeListener
	 */
	public FxTextureGalleryMask(GuiForm effectData, ChangeListener changeListener){
		super("FxTextureGalleryMask_" + countId, Type.TEXTURE, effectData, changeListener);
		initData();
		initForm();
	}

	/**
	 *
	 */
	@Override
	protected final void initData(){
		currentActionTime = 0;
		currentPauseTime = 0;
		currentImageIdx = 0;
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}
		disableChange = true;

		formGui.addLabel(S3Lang.get("FxTextureGalleryMask"), S3Lang.get("FxTextureGalleryMask"), Color.YELLOW);

		String[] transformTextInterpolation = {S3Lang.get("interpolationLinear"),
											   S3Lang.get("interpolationBounce"), S3Lang.get("interpolationPow2"),
											   S3Lang.get("interpolationPow3"), S3Lang.get("interpolationElastic"),
											   S3Lang.get("interpolationSwing"), S3Lang.get("interpolationSine"),};
		formGui.addSelectIndex("interpolationMode", S3Lang.get("interpolationMode"), 0, transformTextInterpolation, localChangeListener);

		formGui.add("actionTime", S3Lang.get("action_time"), 4f, 0.5f, 10, 0.5f, localChangeListener);
		formGui.add("pauseTime", S3Lang.get("pause_time"), 8f, 0, 30, 0.5f, localChangeListener);

		formGui.addImageList("imageList", S3Lang.get("imageList"), null, localChangeListener);

		formGui.addFileBrowser("imageMask", S3Lang.get("imageMask"), "t2t_mask/m1.png", "t2t_mask", localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){

			actionTime = formGui.getFloat("actionTime");
			pauseTime = formGui.getFloat("pauseTime");
			filesList = formGui.getArrayString("imageList");

			filesList = formGui.getArrayString("imageList");
			maskTextureName = formGui.getString("imageMask");

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
			}

			data.type = Data.Type.EFFECT_2D;

			maskTexture = S3ResourceManager.getPixmap(maskTextureName, S3Constans.textureImageSizeHight);

			if (filesList != null){
				if (filesList.length > 1){
					srcTexture = S3ResourceManager.getPixmap(filesList[0], S3Constans.textureImageSizeHight);
					src2Texture = S3ResourceManager.getPixmap(filesList[1], S3Constans.textureImageSizeHight);
				} else {
					srcTexture = new Pixmap(S3Constans.textureImageSizeLow, S3Constans.textureImageSizeLow, Format.RGBA8888);
					srcTexture.setColor(Color.CLEAR);
					srcTexture.fill();
					if (filesList.length > 0){
						src2Texture = S3ResourceManager.getPixmap(filesList[0], S3Constans.textureImageSizeHight);
					} else {
						src2Texture = new Pixmap(S3Constans.textureImageSizeLow, S3Constans.textureImageSizeLow, Format.RGBA8888);
						src2Texture.setColor(Color.CLEAR);
						src2Texture.fill();
					}
				}
			} else {
				srcTexture = new Pixmap(S3Constans.textureImageSizeLow, S3Constans.textureImageSizeLow, Format.RGBA8888);
				srcTexture.setColor(Color.CLEAR);
				srcTexture.fill();

				src2Texture = new Pixmap(srcTexture.getWidth(), srcTexture.getHeight(), srcTexture.getFormat());
				src2Texture.setColor(Color.CLEAR);
				src2Texture.fill();
			}

			if (data.pixmap == null){
				data.pixmap = new Pixmap(S3Constans.textureImageSizeHight, S3Constans.textureImageSizeHight, Pixmap.Format.RGBA8888);
				data.pixmap.setColor(Color.CLEAR);
				data.pixmap.fill();
				data.textureChange = true;
			}

			clearPixmap = new Pixmap(S3Constans.textureImageSizeLow, S3Constans.textureImageSizeLow, Format.RGBA8888);
			clearPixmap.setColor(Color.CLEAR);
			clearPixmap.fill();

			pixmapFilter.setPixmap(data.pixmap, srcTexture, src2Texture, maskTexture);
		}
		currentActionTime = 0;
		currentPauseTime = 0;
		currentImageIdx = 0;
	}

	/**
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	@Override
	protected void updateLocal(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){

		if (isPause){
			return;
		}
		if (filesList == null){
			return;
		}

		currentActionTime += S3.osDeltaTime;
		float procentAction = currentActionTime / actionTime;
		if (procentAction > 1.0f){
			currentPauseTime += S3.osDeltaTime;
			if (currentPauseTime > pauseTime){

				if (S3Constans.NOTICE2){
					S3Log.log("FxTextureGalleryMask::updateProcess", "------> Swap Image");
				}

				//
				// Proces przeładowania zdjęć
				//
				currentActionTime = 0;
				currentPauseTime = 0;

				if (filesList.length == 1){
					tmpPix = srcTexture;
					srcTexture = src2Texture;
					src2Texture = tmpPix;
				} else if (filesList.length == 2){
					tmpPix = srcTexture;
					srcTexture = src2Texture;
					src2Texture = tmpPix;
				} else {

					if (S3Constans.NOTICE2){
						S3Log.log("FxTextureGalleryMask::updateProcess", "------> Old IDx: " + currentImageIdx);
					}

					currentImageIdx = (int) ((effectTime / (actionTime + pauseTime)) % filesList.length);

					if (S3Constans.NOTICE2){
						S3Log.log("FxTextureGalleryMask::updateProcess",
								  "------> Calc IDx: " + currentImageIdx + " effectTime: " + effectTime + " plus: " + (actionTime + pauseTime));
					}

					if (currentImageIdx >= filesList.length){
						currentImageIdx = 0;
					}

					if (S3Constans.NOTICE2){
						S3Log.log("FxTextureGalleryMask::updateProcess", "------> Next IDx: " + currentImageIdx);
					}

					srcTexture = src2Texture;
					src2Texture = S3ResourceManager.getPixmap(filesList[currentImageIdx], S3Constans.textureImageSizeHight);
				}
				pixmapFilter.setPixmap(data.pixmap, srcTexture, src2Texture, maskTexture);
			}

		} else {
			procentAction = interpolation.apply(0, 1, procentAction);
			pixmapFilter.generate(data.pixmap, procentAction);
			data.textureChange = true;
		}

		if (S3Constans.NOTICE2){
			S3Log.log("FxTextureGalleryMask::updateProcess",
					  "idx: " + currentImageIdx + " fl:" + filesList.length + " aT: " + currentActionTime + " pA: " + procentAction + " pTime: " + currentPauseTime + " eT: " + effectTime);
		}

	}
}
