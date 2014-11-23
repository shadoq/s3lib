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
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.pixmap.filter.Gradient;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxGradient extends Node{

	private Color topLeft = Color.RED;
	private Color topRight = Color.YELLOW;
	private Color bottomLeft = Color.BLUE;
	private Color bottomRight = Color.PINK;
	private float alpha = 1.0f;

	public FxGradient(){
		this(null, null);
	}

	/**
	 * @param formGui
	 * @param changeListener
	 */
	public FxGradient(GuiForm effectData, ChangeListener changeListener){
		super("FxGradient_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxGradient"), S3Lang.get("FxGradient"), Color.YELLOW);
		formGui.add("FxGradientAlpha", S3Lang.get("FxGradientAlpha"), alpha, 0.0f, 1.0f, 0.1f, localChangeListener);
		formGui.addColorSelect("FxGradientTopLeft", S3Lang.get("topLeft"), topLeft, localChangeListener);
		formGui.addColorSelect("FxGradientTopRight", S3Lang.get("topRight"), topRight, localChangeListener);
		formGui.addColorSelect("FxGradientBottomLeft", S3Lang.get("bottomLeft"), bottomLeft, localChangeListener);
		formGui.addColorSelect("FxGradientBottomRight", S3Lang.get("bottomRight"), bottomRight, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){
			alpha = formGui.getFloat("FxGradientAlpha");
			topLeft = formGui.getColor("FxGradientTopLeft");
			topRight = formGui.getColor("FxGradientTopRight");
			bottomLeft = formGui.getColor("FxGradientBottomLeft");
			bottomRight = formGui.getColor("FxGradientBottomRight");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.RED);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		Gradient.generate(data.pixmap, topLeft, topRight, bottomLeft, bottomRight, alpha);
		data.textureChange = true;
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
