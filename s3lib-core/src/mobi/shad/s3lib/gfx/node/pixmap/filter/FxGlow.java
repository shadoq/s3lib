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
import mobi.shad.s3lib.gfx.pixmap.filter.Glow;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxGlow extends Node{

	private Color color = Color.WHITE;
	private float centerX = 0.5f;
	private float centerY = 0.5f;
	private float rayX = 0.5f;
	private float rayY = 0.5f;
	private float gamma = 10.0f;
	private float alpha = 10.0f;

	public FxGlow(){
		this(null, null);
	}

	/**
	 * @param formGui
	 * @param changeListener
	 */
	public FxGlow(GuiForm effectData, ChangeListener changeListener){
		super("FxGlow_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxGlow"), S3Lang.get("FxGlow"), Color.YELLOW);
		formGui.add("FxGlowCenterX", S3Lang.get("centerX"), centerX, 0.0f, 1.0f, 0.1f, localChangeListener);
		formGui.add("FxGlowCenterY", S3Lang.get("centerY"), centerY, 0.0f, 1.0f, 0.1f, localChangeListener);
		formGui.add("FxGlowRayX", S3Lang.get("rayX"), rayX, 0.0f, 1.0f, 0.1f, localChangeListener);
		formGui.add("FxGlowRayY", S3Lang.get("rayY"), rayY, 0.0f, 1.0f, 0.1f, localChangeListener);
		formGui.addRandomButton("FxGlowRandom", S3Lang.get("random"), new String[]{"FxGlowCenterX", "FxGlowCenterY", "FxGlowRayX", "FxGlowRayY"},
								localChangeListener);
		formGui.addResetButton("FxGlowReset", S3Lang.get("reset"), new String[]{"FxGlowCenterX", "FxGlowCenterY", "FxGlowRayX", "FxGlowRayY"},
							   localChangeListener);

		formGui.add("FxGlowGamma", S3Lang.get("gamma"), gamma, 0.0f, 50.0f, 0.1f, localChangeListener);
		formGui.add("FxGlowAlpha", S3Lang.get("alpha"), alpha, 0.0f, 50.0f, 0.1f, localChangeListener);
		formGui.addColorSelect("color", S3Lang.get("color"), color, localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			centerX = formGui.getFloat("FxGlowCenterX");
			centerY = formGui.getFloat("FxGlowCenterY");
			rayX = formGui.getFloat("FxGlowRayX");
			rayY = formGui.getFloat("FxGlowRayY");

			gamma = formGui.getFloat("FxGlowGamma");
			alpha = formGui.getFloat("FxGlowAlpha");
			color = formGui.getColor("FxGlowColor");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.RED);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		Glow.generate(data.pixmap, color, centerX, centerY, rayX, rayY, gamma, alpha);
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
