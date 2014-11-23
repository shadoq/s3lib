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
import mobi.shad.s3lib.gfx.pixmap.procedural.*;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxManderbrot extends Node{

	private int fractalType = 0;
	private float xCenter = 0.0f;
	private float yCenter = 0.0f;
	private float xSize = 2.0f;
	private float ySize = 2.0f;
	private int iterations = 64;

	public FxManderbrot(){
		this(null, null);
	}

	/**
	 *
	 * @param effectData
	 * @param changeListener
	 */
	public FxManderbrot(GuiForm effectData, ChangeListener changeListener){
		super("FxManderbrot_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxManderbrot"), S3Lang.get("FxManderbrot"), Color.YELLOW);
		formGui.addSelectIndex("FxManderbrotFractalType", S3Lang.get("fractalType"), fractalType,
							   new String[]{S3Lang.get("fractal_1"), S3Lang.get("fractal_2"), S3Lang.get("fractal_3"), S3Lang.get("fractal_4"), S3Lang.get(
							   "fractal_5")}, localChangeListener);
		formGui.add("FxManderbrotXCenter", S3Lang.get("xCenter"), xCenter, -3.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxManderbrotYCenter", S3Lang.get("yCenter"), yCenter, -3.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxManderbrotXSize", S3Lang.get("xSize"), xSize, -3.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxManderbrotYSize", S3Lang.get("ySize"), ySize, -3.0f, 3.0f, 0.1f, localChangeListener);
		formGui.addRandomButton("FxManderbrotRandom", S3Lang.get("random"),
								new String[]{"FxManderbrotXCenter", "FxManderbrotYCenter", "FxManderbrotXSize", "FxManderbrotYSize"}, localChangeListener);
		formGui.addResetButton("FxManderbrotReset", S3Lang.get("reset"),
							   new String[]{"FxManderbrotXCenter", "FxManderbrotYCenter", "FxManderbrotXSize", "FxManderbrotYSize"}, localChangeListener);
		formGui.add("FxManderbrotIterations", S3Lang.get("iterations"), iterations, 2f, 255f, 1.0f, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			fractalType = formGui.getInt("FxManderbrotFractalType");
			xCenter = formGui.getFloat("FxManderbrotXCenter");
			yCenter = formGui.getFloat("FxManderbrotYCenter");
			xSize = formGui.getFloat("FxManderbrotXSize");
			ySize = formGui.getFloat("FxManderbrotYSize");
			iterations = formGui.getInt("FxManderbrotIterations");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.BLACK);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;

		switch (fractalType){
			default:
				Mandelbrot.generate(data.pixmap, xCenter, yCenter, xSize, ySize, iterations);
				break;
			case 1:
				Mandelbrot2.generate(data.pixmap, xCenter, yCenter, xSize, ySize, iterations);
				break;
			case 2:
				Mandelbrot3.generate(data.pixmap, xCenter, yCenter, xSize, ySize, iterations);
				break;
			case 3:
				Mandelbrot4.generate(data.pixmap, xCenter, yCenter, xSize, ySize, iterations);
				break;
			case 4:
				Mandelbrot5.generate(data.pixmap, xCenter, yCenter, xSize, ySize, iterations);
				break;
		}
		data.textureChange = true;
	}
}
