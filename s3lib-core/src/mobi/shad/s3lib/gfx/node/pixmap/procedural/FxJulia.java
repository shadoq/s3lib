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
import mobi.shad.s3lib.gfx.pixmap.procedural.Julia;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxJulia extends Node{

	private float xCenter = 0.0f;
	private float yCenter = 0.0f;
	private float xSize = 2.0f;
	private float ySize = 2.0f;
	private float xIterations = 0.5f;
	private float yIterations = 0.5f;
	private int iterations = 64;

	public FxJulia(){
		this(null, null);
	}

	/**
	 * @param effectData
	 * @param changeListener
	 */
	public FxJulia(GuiForm effectData, ChangeListener changeListener){
		super("FxJulia_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxJulia"), S3Lang.get("FxJulia"), Color.YELLOW);
		formGui.add("FxJuliaXCenter", S3Lang.get("xCenter"), xCenter, -3.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxJuliaYCenter", S3Lang.get("yCenter"), yCenter, -3.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxJuliaXSize", S3Lang.get("xSize"), xSize, -3.0f, 3.0f, 0.1f, localChangeListener);
		formGui.add("FxJuliaYSize", S3Lang.get("ySize"), ySize, -3.0f, 3.0f, 0.1f, localChangeListener);

		formGui.addRandomButton("FxJuliaRandom", S3Lang.get("random"), new String[]{"FxJuliaXCenter", "FxJuliaYCenter", "FxJuliaXSize", "FxJuliaYSize"},
								localChangeListener);
		formGui.addResetButton("FxJuliaReset", S3Lang.get("reset"), new String[]{"FxJuliaXCenter", "FxJuliaYCenter", "FxJuliaXSize", "FxJuliaYSize"},
							   localChangeListener);

		formGui.add("FxJuliaXIterations", S3Lang.get("xIterations"), xIterations, -1.5f, 1.5f, 0.02f, localChangeListener);
		formGui.add("FxJuliaYIterations", S3Lang.get("yIterations"), yIterations, -1.5f, 1.5f, 0.02f, localChangeListener);

		formGui.addRandomButton("FxJuliaRandom2", S3Lang.get("random"), new String[]{"FxJuliaXIterations", "FxJuliaYIterations"}, localChangeListener);
		formGui.addResetButton("FxJuliaReset2", S3Lang.get("reset"), new String[]{"FxJuliaXIterations", "FxJuliaYIterations"}, localChangeListener);

		formGui.add("FxJuliaIterations", S3Lang.get("iterations"), iterations, 2f, 255f, 1.0f, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			xCenter = formGui.getFloat("FxJuliaXCenter");
			yCenter = formGui.getFloat("FxJuliaYCenter");
			xSize = formGui.getFloat("FxJuliaXSize");
			ySize = formGui.getFloat("FxJuliaYSize");
			xIterations = formGui.getFloat("FxJuliaXIterations");
			yIterations = formGui.getFloat("FxJuliaYIterations");
			iterations = formGui.getInt("FxJuliaIterations");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.BLACK);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		Julia.generate(data.pixmap, xCenter, yCenter, xSize, ySize, xIterations, yIterations, iterations);
		data.textureChange = true;
	}
}
