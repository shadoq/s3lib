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
import mobi.shad.s3lib.gfx.pixmap.filter.Noise;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxNoise extends Node{

	private int noiseRed = 64;
	private int noiseGreen = 64;
	private int noiseBlue = 64;
	private int seed;

	public FxNoise(){
		this(null, null);
	}

	/**
	 * @param formGui
	 * @param changeListener
	 */
	public FxNoise(GuiForm effectData, ChangeListener changeListener){
		super("FxNoise_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxNoise"), S3Lang.get("FxNoise"), Color.YELLOW);
		formGui.add("FxNoiseNoiseRed", S3Lang.get("noiseRed"), noiseRed, 0.0f, 255f, 1.0f, localChangeListener);
		formGui.add("FxNoiseNoiseGreen", S3Lang.get("noiseGreen"), noiseGreen, 0.0f, 255f, 1.0f, localChangeListener);
		formGui.add("FxNoiseNoiseBlue", S3Lang.get("noiseBlue"), noiseBlue, 0.0f, 255f, 1.0f, localChangeListener);
		formGui.add("FxNoiseNoiseSeed", S3Lang.get("seed"), seed, 1f, 512, 1f, localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			noiseRed = formGui.getInt("FxNoiseNoiseRed");
			noiseGreen = formGui.getInt("FxNoiseNoiseGreen");
			noiseBlue = formGui.getInt("FxNoiseNoiseBlue");
			seed = formGui.getInt("FxNoiseNoiseSeed");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.RED);
			data.pixmap.fill();
			data.texture = null;

		}
		data.type = Data.Type.EFFECT_2D;
		Noise.setSeed(seed);
		Noise.generate(data.pixmap, noiseRed, noiseGreen, noiseBlue);
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
