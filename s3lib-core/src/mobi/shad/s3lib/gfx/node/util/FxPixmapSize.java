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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxPixmapSize extends Node{

	private int pixmapSize = 2;
	private int xSize = S3Constans.proceduralTextureSize;
	private int ySize = xSize = S3Constans.proceduralTextureSize;

	public FxPixmapSize(){
		this(null, null);
	}

	/**
	 * @param effectData
	 * @param changeListener
	 */
	public FxPixmapSize(GuiForm effectData, ChangeListener changeListener){
		super("FxPixmapSize_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("fxPixmapSize"), S3Lang.get("fxPixmapSize"), Color.YELLOW);
		formGui.addSelectIndex("FxPixmapSize", S3Lang.get("pixmapSize"), pixmapSize,
							   new String[]{S3Lang.get("16x16"), S3Lang.get("32x32"), S3Lang.get("64x64"), S3Lang.get("128x128"), S3Lang.get(
							   "256x256"), S3Lang.get("512x512"), S3Lang.get("1024x1024"), S3Lang.get("64x32"), S3Lang.get("128x64"), S3Lang.get("256x128")},
							   localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			pixmapSize = formGui.getInt("FxPixmapSize");
		}
		data.type = Data.Type.EFFECT_2D;

		switch (pixmapSize){
			default:
				xSize = 16;
				ySize = 16;
				break;
			case 1:
				xSize = 32;
				ySize = 32;
				break;
			case 2:
				xSize = 64;
				ySize = 64;
				break;
			case 3:
				xSize = 128;
				ySize = 128;
				break;
			case 4:
				xSize = 256;
				ySize = 256;
				break;
			case 5:
				xSize = 512;
				ySize = 512;
				break;
			case 6:
				xSize = 1024;
				ySize = 1024;
				break;
			case 7:
				xSize = 64;
				ySize = 32;
				break;
			case 8:
				xSize = 128;
				ySize = 64;
				break;
			case 9:
				xSize = 256;
				ySize = 128;
				break;
		}
		data.pixmap = new Pixmap(xSize, ySize, Pixmap.Format.RGBA8888);
		data.pixmap.setColor(Color.BLACK);
		data.pixmap.fill();
		data.texture = null;
		data.textureChange = true;
	}
}
