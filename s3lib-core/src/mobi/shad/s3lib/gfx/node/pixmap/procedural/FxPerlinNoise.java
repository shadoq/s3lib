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
package mobi.shad.s3lib.gfx.node.pixmap.procedural;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.pixmap.procedural.PerlinNoise1D;
import mobi.shad.s3lib.gfx.pixmap.procedural.PerlinNoise2D;
import mobi.shad.s3lib.gfx.pixmap.procedural.PerlinNoise2Dv2;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxPerlinNoise extends Node{

	private int perlinType = 0;
	private float amplitude = 0.5f;
	private float frequency = 0.5f;
	private int octaves = 4;

	public FxPerlinNoise(){
		this(null, null);
	}

	/**
	 *
	 * @param effectData
	 * @param changeListener
	 */
	public FxPerlinNoise(GuiForm effectData, ChangeListener changeListener){
		super("FxPerlinNoise_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxPerlinNoise"), S3Lang.get("FxPerlinNoise"), Color.YELLOW);
		formGui.addSelectIndex("FxPerlinNoiseType", S3Lang.get("perlinType"), perlinType,
							   new String[]{S3Lang.get("PerlinNoise2D"), S3Lang.get("PerlinNoise2Dv2"), S3Lang.get("PerlinNoise1D")}, localChangeListener);

		formGui.add("FxPerlinNoiseAmplitude", S3Lang.get("amplitude"), amplitude, -0.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxPerlinNoiseFrequency", S3Lang.get("frequency"), frequency, -0.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxPerlinNoiseOctaves", S3Lang.get("octaves"), octaves, 2.0f, 32.0f, 1.0f, localChangeListener);
		formGui.addRandomButton("FxPerlinNoiseRandom", S3Lang.get("random"),
								new String[]{"FxPerlinNoiseAmplitude", "FxPerlinNoiseFrequency", "FxPerlinNoiseOctaves"}, localChangeListener);
		formGui.addResetButton("FxPerlinNoiseReset", S3Lang.get("reset"),
							   new String[]{"FxPerlinNoiseAmplitude", "FxPerlinNoiseFrequency", "FxPerlinNoiseOctaves"}, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			perlinType = formGui.getInt("FxPerlinNoiseType");
			amplitude = formGui.getFloat("FxPerlinNoiseAmplitude");
			frequency = formGui.getFloat("FxPerlinNoiseFrequency");
			octaves = formGui.getInt("FxPerlinNoiseOctaves");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.BLACK);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;

		switch (perlinType){
			default:
				PerlinNoise2D.generate(data.pixmap, amplitude, frequency, octaves);
				break;
			case 1:
				PerlinNoise2Dv2.generate(data.pixmap, amplitude, frequency, octaves);
				break;
			case 2:
				PerlinNoise1D.generate(data.pixmap, amplitude, frequency, octaves);
				break;
		}
		data.textureChange = true;
	}
}
