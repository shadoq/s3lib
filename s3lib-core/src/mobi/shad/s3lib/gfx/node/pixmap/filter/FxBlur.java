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
package mobi.shad.s3lib.gfx.node.pixmap.filter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.math.Convolve;
import mobi.shad.s3lib.gfx.math.Kernel;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxBlur extends Node{

	private int matrixMode;
	private int blurMode;

	public FxBlur(){
		this(null, null);
	}

	public FxBlur(GuiForm effectData, ChangeListener changeListener){
		super("FxTextureOpBlur_" + countId, Type.TEXTURE_FILTER, effectData, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;
		formGui.addLabel(S3Lang.get("fxTextureOpBlur"), S3Lang.get("fxTextureOpBlur"), Color.YELLOW);

		formGui.addSelectIndex("FxTextureOpBlurMode", "Blur Mode", 0, new String[]{"None", "convolveHV", "convolveH", "convolveV"}, localChangeListener);
		formGui.addSelectIndex("FxTextureOpBlurMatrix", "Matrix Mode", 0,
							   new String[]{"Matrix Blur 3x3", "Matrix Blur 5x5", "Matrix Blur Cross", "Matrix Blur Star", "Matrix Blur Block"},
							   localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			matrixMode = formGui.getInt("FxTextureOpBlurMatrix");
			blurMode = formGui.getInt("FxTextureOpBlurMode");
		}

		Kernel kernel;
		switch (matrixMode){
			default:
				kernel = new Kernel(3, 3, Convolve.BLUR_MATRIX_3x3);
				break;
			case 1:
				kernel = new Kernel(5, 5, Convolve.BLUR_MATRIX_5x5);
				break;
			case 2:
				kernel = new Kernel(5, 5, Convolve.BLUR_MATRIX_CROSS);
				break;
			case 3:
				kernel = new Kernel(5, 5, Convolve.BLUR_MATRIX_STAR);
				break;
			case 4:
				kernel = new Kernel(5, 5, Convolve.BLUR_MATRIX_BLOCK);
				break;
		}

		Pixmap inPixmap = data.pixmap;
		if (inPixmap == null){
			inPixmap = new Pixmap(S3File.getFileHandle("texture/def256.jpg"));
		}

		data.textureChange = true;
		int width = inPixmap.getWidth();
		int height = inPixmap.getHeight();

		if (inPixmap != null){

			switch (blurMode){
				default:
					data.pixmap = inPixmap;
					break;
				case 1:
					Convolve.convolveHV(kernel, inPixmap, data.pixmap, width, height, true, Convolve.Edge.NONE);
					break;
				case 2:
					Convolve.convolveH(kernel, inPixmap, data.pixmap, width, height, true, Convolve.Edge.NONE);
					break;
				case 3:
					Convolve.convolveV(kernel, inPixmap, data.pixmap, width, height, true, Convolve.Edge.NONE);
					break;

			}
		}
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
		if (!data.textureChange || data.pixmap == null){
			return;
		}
		processLocal();
	}
}
