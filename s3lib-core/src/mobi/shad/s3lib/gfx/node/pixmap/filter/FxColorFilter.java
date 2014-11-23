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
import mobi.shad.s3lib.gfx.pixmap.filter.ColorFilter;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxColorFilter extends Node{

	private Color colorBase = Color.BLACK;
	private int colorPercentRed = 255;
	private int colorPercentGreen = 255;
	private int colorPercentBlue = 255;
	private int brithness = 127;
	private int contrast = 127;
	private int saturation = 127;
	private int alpha = 127;

	public FxColorFilter(){
		this(null, null);
	}

	/**
	 * @param formGui
	 * @param changeListener
	 */
	public FxColorFilter(GuiForm effectData, ChangeListener changeListener){
		super("FxColorFilter_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxColorFilter"), S3Lang.get("FxColorFilter"), Color.YELLOW);

		formGui.add("FxColorFilterBrithness", S3Lang.get("brithness"), brithness, 0.0f, 255.0f, 1.0f, localChangeListener);
		formGui.add("FxColorFilterContrast", S3Lang.get("contrast"), contrast, 0.0f, 255.0f, 1.0f, localChangeListener);
		formGui.add("FxColorFilterSaturation", S3Lang.get("saturation"), saturation, 0.0f, 255.0f, 1.0f, localChangeListener);

		formGui.addRandomButton("FxColorRandom", S3Lang.get("random"),
								new String[]{"FxColorFilterBrithness", "FxColorFilterContrast", "FxColorFilterSaturation"}, localChangeListener);
		formGui.addResetButton("FxColorReset", S3Lang.get("reset"), new String[]{"FxColorFilterBrithness", "FxColorFilterContrast", "FxColorFilterSaturation"},
							   localChangeListener);

		formGui.addColorSelect("FxColorFilterColorBase", S3Lang.get("colorBase"), colorBase, localChangeListener);

		formGui.add("FxColorFilterColorPercentRed", S3Lang.get("colorPercentRed"), colorPercentRed, 0.0f, 255.0f, 1.0f, localChangeListener);
		formGui.add("FxColorFilterColorPercentGreen", S3Lang.get("colorPercentGreen"), colorPercentGreen, 0.0f, 255.0f, 1.0f, localChangeListener);
		formGui.add("FxColorFilterColorPercentBlue", S3Lang.get("colorPercentBlue"), colorPercentBlue, 0.0f, 255.0f, 1.0f, localChangeListener);

		formGui.add("FxColorFilterAlpha", S3Lang.get("alpha"), alpha, 0.0f, 255.0f, 1.0f, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){

			brithness = formGui.getInt("FxColorFilterBrithness");
			contrast = formGui.getInt("FxColorFilterContrast");
			saturation = formGui.getInt("FxColorFilterSaturation");

			colorBase = formGui.getColor("FxColorFilterColorBase");

			colorPercentRed = formGui.getInt("FxColorFilterColorPercentRed");
			colorPercentGreen = formGui.getInt("FxColorFilterColorPercentGreen");
			colorPercentBlue = formGui.getInt("FxColorFilterColorPercentBlue");

			alpha = formGui.getInt("FxColorFilterAlpha");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.RED);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		ColorFilter.generate(data.pixmap, colorBase, colorPercentRed, colorPercentGreen, colorPercentBlue, brithness, contrast, saturation, alpha);
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
