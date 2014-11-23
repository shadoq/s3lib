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
package mobi.shad.s3lib.gfx.node.pixmap.anim;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.pixmap.procedural.Julia;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxJuliaAnim extends Node{

	private float xCenter = 0.0f;
	private float yCenter = 0.0f;
	private float xSize = 2.0f;
	private float ySize = 2.0f;
	private float xIterations = 0.5f;
	private float yIterations = 0.5f;
	private float addXIterations = 0.01f;
	private float minXIterations = -1.5f;
	private float maxXIterations = 1.5f;
	private float minYIterations = -1.5f;
	private float maxYIterations = 1.5f;
	private float addYIterations = 0.01f;
	private int iterations = 64;

	public FxJuliaAnim(){
		this(null, null);
	}

	/**
	 * @param formGui
	 * @param changeListener
	 */
	public FxJuliaAnim(GuiForm effectData, ChangeListener changeListener){
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
		formGui.add("FxJuliaXCenter", S3Lang.get("xCenter"), 0, -100.0f, 100.0f, 2f, localChangeListener);
		formGui.add("FxJuliaYCenter", S3Lang.get("yCenter"), 0, -100.0f, 100.0f, 2f, localChangeListener);
		formGui.add("FxJuliaXSize", S3Lang.get("xSize"), 50, -100.0f, 100.0f, 2f, localChangeListener);
		formGui.add("FxJuliaYSize", S3Lang.get("ySize"), 50, -100.0f, 100.0f, 2f, localChangeListener);

		formGui.addRandomButton("FxJuliaRandom", S3Lang.get("random"), new String[]{"FxJuliaXCenter", "FxJuliaYCenter", "FxJuliaXSize", "FxJuliaYSize"},
								localChangeListener);
		formGui.addResetButton("FxJuliaReset", S3Lang.get("reset"), new String[]{"FxJuliaXCenter", "FxJuliaYCenter", "FxJuliaXSize", "FxJuliaYSize"},
							   localChangeListener);

		formGui.add("FxJuliaXIterations", S3Lang.get("xIterations"), 10, -100f, 100f, 2f, localChangeListener);
		formGui.add("FxJuliaYIterations", S3Lang.get("yIterations"), 10, -100f, 100f, 2f, localChangeListener);

		formGui.addRandomButton("FxJuliaRandom2", S3Lang.get("random"), new String[]{"FxJuliaXIterations", "FxJuliaYIterations"}, localChangeListener);
		formGui.addResetButton("FxJuliaReset2", S3Lang.get("reset"), new String[]{"FxJuliaXIterations", "FxJuliaYIterations"}, localChangeListener);

		formGui.add("FxJuliaAddXIterations", S3Lang.get("addXIterations"), 10, -100f, 100f, 2f, localChangeListener);
		formGui.add("FxJuliaAddYIterations", S3Lang.get("addYIterations"), 10, -100f, 100f, 2f, localChangeListener);

		formGui.addRandomButton("FxJuliaRandom2", S3Lang.get("random"), new String[]{"FxJuliaAddXIterations", "FxJuliaAddYIterations"}, localChangeListener);
		formGui.addResetButton("FxJuliaReset2", S3Lang.get("reset"), new String[]{"FxJuliaAddXIterations", "FxJuliaAddYIterations"}, localChangeListener);

		formGui.add("FxJuliaIterations", S3Lang.get("iterations"), 10, 2f, 100f, 1.0f, localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){
			xCenter = formGui.getFloat("FxJuliaXCenter") / 50;
			yCenter = formGui.getFloat("FxJuliaYCenter") / 50;
			xSize = formGui.getFloat("FxJuliaXSize") / 25;
			ySize = formGui.getFloat("FxJuliaYSize") / 25;
			xIterations = formGui.getFloat("FxJuliaXIterations") / 50;
			yIterations = formGui.getFloat("FxJuliaYIterations") / 50;
			iterations = formGui.getInt("FxJuliaIterations");

			addXIterations = formGui.getFloat("FxJuliaAddXIterations") / 50;
			addYIterations = formGui.getFloat("FxJuliaAddYIterations") / 50;
		}
		updateLocal(0, 0, 0, 0, true);
	}

	@Override
	protected void updateLocal(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
		if (isPause){
			return;
		}

		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSize, S3Constans.proceduralTextureSize, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.BLACK);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		data.textureChange = true;

		xIterations = xIterations + addXIterations * S3.osDeltaTime;
		yIterations = yIterations + addYIterations * S3.osDeltaTime;
		if (xIterations > maxXIterations || xIterations < minXIterations){
			addXIterations = -addXIterations;
		}
		if (yIterations > maxYIterations || yIterations < minYIterations){
			addYIterations = -addYIterations;
		}
		Julia.generate(data.pixmap, xCenter, yCenter, xSize, ySize, xIterations, yIterations, iterations);
	}
}
