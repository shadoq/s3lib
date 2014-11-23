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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class FxPixmapToSprite extends Node{

	private int gridX = 16;
	private int gridY = 16;
	private int oldWidth = 0;
	private int oldHeight = 0;
	private int borderWidth = 1;

	public FxPixmapToSprite(){
		this(null, null);
	}

	/**
	 * @param effectData
	 * @param changeListener
	 */
	public FxPixmapToSprite(GuiForm effectData, ChangeListener changeListener){
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
		formGui.add("FxPixmapToSprite_borderWidth", S3Lang.get("borderWidth"), borderWidth, -0f, 10f, 1f, localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){
			borderWidth = formGui.getInt("FxPixmapToSprite_borderWidth");
		}
		if (data.pixmap == null){
			return;
		}

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
			oldWidth = width;
			oldHeight = height;

			for (int y = 0; y < height; y++){
				for (int x = 0; x < width; x++){
					int index = x + y * width;
					data.spritePosition[index * 2] = x * data.spriteWidth;
					data.spritePosition[index * 2 + 1] = y * data.spriteHeight;
				}
			}

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
		data.spriteWidth = data.spriteWidth - borderWidth;
		data.spriteHeight = data.spriteHeight - borderWidth;
	}
}