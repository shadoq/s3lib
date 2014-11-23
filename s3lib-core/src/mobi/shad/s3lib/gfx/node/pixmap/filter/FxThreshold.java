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
import mobi.shad.s3lib.gfx.pixmap.filter.Threshold;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxThreshold extends Node{

	private int threshold = 127;
	private int ratio = 127;
	private int mode = 0;

	public FxThreshold(){
		this(null, null);
	}

	/**
	 * @param formGui
	 * @param changeListener
	 */
	public FxThreshold(GuiForm effectData, ChangeListener changeListener){
		super("FxThreshold_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxThreshold"), S3Lang.get("FxThreshold"), Color.YELLOW);
		formGui.add("FxThresholdThreshold", S3Lang.get("threshold"), threshold, 0.0f, 255.0f, 1.0f, localChangeListener);
		formGui.add("FxThresholdRatio", S3Lang.get("ratio"), ratio, 0.0f, 255.0f, 1.0f, localChangeListener);
		formGui.addSelectIndex("FxThresholdMode", S3Lang.get("mode"), mode,
							   new String[]{S3Lang.get("expand_downwards"), S3Lang.get("expand_upwards"), S3Lang.get("compress_below"), S3Lang.get(
							   "compress_above")}, localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			threshold = formGui.getInt("FxThresholdThreshold");
			ratio = formGui.getInt("FxThresholdRatio");
			mode = formGui.getInt("FxThresholdMode");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.RED);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		Threshold.generate(data.pixmap, threshold, ratio, mode);
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
