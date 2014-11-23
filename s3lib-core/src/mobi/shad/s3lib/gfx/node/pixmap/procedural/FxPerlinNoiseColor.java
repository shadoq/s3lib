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
import mobi.shad.s3lib.gfx.math.PerlinNoise;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.util.GradientUtil;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxPerlinNoiseColor extends Node{

	private int octave;
	private int colorMode;
	private int frequence;

	public FxPerlinNoiseColor(){
		this(null, null);
	}

	public FxPerlinNoiseColor(GuiForm effectData, ChangeListener changeListener){
		super("FxTexturePerlinNoise_" + countId, Type.TEXTURE, effectData, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}
		disableChange = true;
		formGui.addLabel(S3Lang.get("fxTexturePerlinNoise"), S3Lang.get("fxTexturePerlinNoise"), Color.YELLOW);

		formGui.add("FxTexturePerlinNoiseOctave", "Octave", 1, 1, 14, 1, localChangeListener);
		formGui.add("FxTexturePerlinNoiseFrequence", "Frequence", 256, 1, 256, 1, localChangeListener);
		formGui.addColorList("FxTexturePerlinNoiseColor", "Color", localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			octave = formGui.getInt("FxTexturePerlinNoiseOctave");
			colorMode = formGui.getInt("FxTexturePerlinNoiseColor");
			frequence = formGui.getInt("FxTexturePerlinNoiseFrequence");
		}

		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSize, S3Constans.proceduralTextureSize, Pixmap.Format.RGBA8888);
		}
		int width = data.pixmap.getWidth();
		int height = data.pixmap.getHeight();

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				int fastNoise = PerlinNoise.fastNoise(x, y, 128, frequence, octave);
				Color col = GradientUtil.getColorPalleteInt(fastNoise, colorMode);
				data.pixmap.drawPixel(x, y, Color.rgba8888(col));
			}
		}
		data.textureChange = true;
	}
}
