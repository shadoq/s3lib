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
package mobi.shad.s3lib.gfx.node.filter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.util.GradientUtil;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Math;

/**
 * @author Jarek
 */
public class FxPlasma3D extends Node{

	private float speedX1;
	private float speedY1;
	private float stepX1;
	private float stepY1;
	private float amplitudeX1;
	private float amplitudeY1;
	private float speedX2;
	private float speedY2;
	private float stepX2;
	private float stepY2;
	private float amplitudeX2;
	private float amplitudeY2;
	private float speedX3;
	private float speedY3;
	private float stepX3;
	private float stepY3;
	private float amplitudeX3;
	private float amplitudeY3;
	private int colorMode;
	private Color colorTmp;

	public FxPlasma3D(){
		this(null, null);
	}

	public FxPlasma3D(GuiForm formGui, ChangeListener changeListener){
		super("FxStarField_" + countId, Type.TEXTURE, formGui, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;

		formGui.addLabel(S3Lang.get("fxPlasma3D"), S3Lang.get("fxPlasma3D"), Color.YELLOW);

		formGui.addColorList("colorMode", S3Lang.get("plasma_color"), localChangeListener);

		formGui.add("speedX1", S3Lang.get("speedX1"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("speedY1", S3Lang.get("speedY1"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("stepX1", S3Lang.get("stepX1"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("stepY1", S3Lang.get("stepY1"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX1", S3Lang.get("amplitudeX1"), 1, -2, 2, 0.1f, localChangeListener);
		formGui.add("amplitudeY1", S3Lang.get("amplitudeY1"), 1, -2, 2, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPlasma3DRandom1", S3Lang.get("random"), new String[]{"speedX1", "speedY1", "stepX1", "stepY1", "amplitudeX1", "amplitudeY1"},
								localChangeListener);
		formGui.addResetButton("fxPlasma3DReset1", S3Lang.get("reset"), new String[]{"speedX1", "speedY1", "stepX1", "stepY1", "amplitudeX1", "amplitudeY1"},
							   localChangeListener);

		formGui.add("speedX2", S3Lang.get("speedX2"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("speedY2", S3Lang.get("speedY2"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("stepX2", S3Lang.get("stepX2"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("stepY2", S3Lang.get("stepY2"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX2", S3Lang.get("amplitudeX2"), 1, -2, 2, 0.1f, localChangeListener);
		formGui.add("amplitudeY2", S3Lang.get("amplitudeY2"), 1, -2, 2, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPlasma3DRandom2", S3Lang.get("random"), new String[]{"speedX2", "speedY2", "stepX2", "stepY2", "amplitudeX2", "amplitudeY2"},
								localChangeListener);
		formGui.addResetButton("fxPlasma3DReset2", S3Lang.get("reset"), new String[]{"speedX2", "speedY2", "stepX2", "stepY2", "amplitudeX2", "amplitudeY2"},
							   localChangeListener);

		formGui.add("speedX3", S3Lang.get("speedX3"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("speedY3", S3Lang.get("speedY3"), 1f, -80, 240, 0.1f, localChangeListener);
		formGui.add("stepX3", S3Lang.get("stepX3"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("stepY3", S3Lang.get("stepY3"), 1f, -10f, 20, 0.1f, localChangeListener);
		formGui.add("amplitudeX3", S3Lang.get("amplitudeX3"), 1, -2, 2, 0.1f, localChangeListener);
		formGui.add("amplitudeY3", S3Lang.get("amplitudeY3"), 1, -2, 2, 0.1f, localChangeListener);

		formGui.addRandomButton("fxPlasma3DRandom3", S3Lang.get("random"), new String[]{"speedX3", "speedY3", "stepX3", "stepY3", "amplitudeX3", "amplitudeY3"},
								localChangeListener);
		formGui.addResetButton("fxPlasma3DReset3", S3Lang.get("reset"), new String[]{"speedX3", "speedY3", "stepX3", "stepY3", "amplitudeX3", "amplitudeY3"},
							   localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		data.type = Data.Type.EFFECT_2D;

		if (formGui != null){

			colorMode = formGui.getInt("colorMode");

			speedX1 = formGui.getFloat("speedX1");
			speedY1 = formGui.getFloat("speedY1");
			stepX1 = formGui.getFloat("stepX1");
			stepY1 = formGui.getFloat("stepY1");
			amplitudeX1 = formGui.getFloat("amplitudeX1");
			amplitudeY1 = formGui.getFloat("amplitudeY1");

			speedX2 = formGui.getFloat("speedX2");
			speedY2 = formGui.getFloat("speedY2");
			stepX2 = formGui.getFloat("stepX2");
			stepY2 = formGui.getFloat("stepY2");
			amplitudeX2 = formGui.getFloat("amplitudeX2");
			amplitudeY2 = formGui.getFloat("amplitudeY2");

			speedX3 = formGui.getFloat("speedX3");
			speedY3 = formGui.getFloat("speedY3");
			stepX3 = formGui.getFloat("stepX3");
			stepY3 = formGui.getFloat("stepY3");
			amplitudeX3 = formGui.getFloat("amplitudeX3");
			amplitudeY3 = formGui.getFloat("amplitudeY3");
		}

		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSize, S3Constans.proceduralTextureSize, Pixmap.Format.RGBA8888);
		}
		updateLocal(0, 0, 0, 0, false);
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

		int offset = 0;

		float z = 0;
		float angleX = 0;
		float angleY = 0;
		float angleX2 = 0;
		float angleY2 = 0;
		float angleX3 = 0;
		float angleY3 = 0;

		float angleXStart = effectTime * speedX1;
		float angleYStart = effectTime * speedY1;

		float angleX2Start = effectTime * speedX2;
		float angleY2Start = effectTime * speedY2;

		float angleX3Start = effectTime * speedX3;
		float angleY3Start = effectTime * speedY3;

		int width = data.pixmap.getWidth();
		int height = data.pixmap.getHeight();

		for (int x = 0; x < width; x++){

			angleX = (angleXStart + x * stepX1) * S3Math.DIV_PI2_360;
			angleX2 = (angleX2Start + x * stepX2) * S3Math.DIV_PI2_360;
			angleX3 = (angleX3Start + x * stepX3) * S3Math.DIV_PI2_360;

			for (int y = 0; y < height; y++){
				angleY = (angleYStart + y * stepY1) * S3Math.DIV_PI2_360;
				angleY2 = (angleY2Start + y * stepY2) * S3Math.DIV_PI2_360;
				angleY3 = (angleY3Start + y * stepY3) * S3Math.DIV_PI2_360;
				z = S3Math.matchSinCos3D(
				angleX, angleY, amplitudeX1, amplitudeY1,
				angleX2, angleY2, amplitudeX2, amplitudeY2,
				angleX3, angleY3, amplitudeX3, amplitudeY3);

				colorTmp = GradientUtil.getColorPallete(z, colorMode);
				data.pixmap.drawPixel(x, y, Color.rgba8888(colorTmp.r, colorTmp.g, colorTmp.b, 1.0f));
			}
		}

		data.textureChange = true;
	}
}
