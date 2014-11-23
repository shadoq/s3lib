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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.g3d.shaders.EffectShader;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3ResourceManager;

/**
 * @author Jarek
 */
public class FxShader extends Node{

	private String[] listOfShader;
	private EffectShader.ShaderParameter params = new EffectShader.ShaderParameter();
	private int shaderId;
	private String shaderName;

	public FxShader(){
		this(null, null);
	}

	public FxShader(GuiForm effectData, ChangeListener changeListener){
		super("FxShader_" + countId, Type.SHADER, effectData, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;
		formGui.addLabel(S3Lang.get("fxShader"), S3Lang.get("fxShader"), Color.YELLOW);

		listOfShader = S3ResourceManager.getShaderList();
		formGui.addListIndex("FxShaderId", "Shader", 0, listOfShader, localChangeListener);

		//
		// Main Speed
		//
		formGui.add("shaderMainSpeedX", "Speed X", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderMainSpeedY", "Speed Y", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderMainSpeedZ", "Speed Z", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.addRandomButton("fxShaderrandomSpeed", S3Lang.get("random"), new String[]{"shaderMainSpeedX", "shaderMainSpeedY", "shaderMainSpeedZ"},
								localChangeListener);
		formGui.addResetButton("fxShaderresetSpeed", S3Lang.get("reset"), new String[]{"shaderMainSpeedX", "shaderMainSpeedY", "shaderMainSpeedZ"},
							   localChangeListener);

		//
		// Main Amplitude
		//
		formGui.add("shaderMainAmplitudeX", "Amplitude X", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderMainAmplitudeY", "Amplitude Y", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderMainAmplitudeZ", "Amplitude Z", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.addRandomButton("fxShaderrandomAmplitude", S3Lang.get("random"),
								new String[]{"shaderMainAmplitudeX", "shaderMainAmplitudeY", "shaderMainAmplitudeZ"}, localChangeListener);
		formGui.addResetButton("fxShaderresetAmplitude", S3Lang.get("reset"),
							   new String[]{"shaderMainAmplitudeX", "shaderMainAmplitudeY", "shaderMainAmplitudeZ"}, localChangeListener);

		//
		// Main Step
		//
		formGui.add("shaderMainStepX", "Step X", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderMainStepY", "Step Y", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderMainStepZ", "Step Z", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.addRandomButton("fxShaderrandomStep", S3Lang.get("random"), new String[]{"shaderMainStepX", "shaderMainStepY", "shaderMainStepZ"},
								localChangeListener);
		formGui.addResetButton("fxShaderresetStep", S3Lang.get("reset"), new String[]{"shaderMainStepX", "shaderMainStepY", "shaderMainStepZ"},
							   localChangeListener);

		//
		// Main Light
		//
		formGui.add("shaderMainIterations", "Iterations", 1.0f, 1f, 128f, 1f, localChangeListener);
		formGui.add("shaderMainPower", "Power", 0.1f, -1f, 1f, 0.05f, localChangeListener);
		formGui.add("shaderMainLight", "Light", 0.1f, -1f, 1f, 0.05f, localChangeListener);
		formGui.addRandomButton("fxShaderrandomLight", S3Lang.get("random"), new String[]{"shaderMainIterations", "shaderMainPower", "shaderMainLight"},
								localChangeListener);
		formGui.addResetButton("fxShaderresetLight", S3Lang.get("reset"), new String[]{"shaderMainIterations", "shaderMainPower", "shaderMainLight"},
							   localChangeListener);

		//
		// Color process
		//
		formGui.add("shaderAmplitudeColourR", "Amplitude Process R", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderAmplitudeColourG", "Amplitude Process G", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderAmplitudeColourB", "Amplitude Process B", 1.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.addRandomButton("fxShaderrandomAmplitudeColour", S3Lang.get("random"),
								new String[]{"shaderAmplitudeColourR", "shaderAmplitudeColourG", "shaderAmplitudeColourB"}, localChangeListener);
		formGui.addResetButton("fxShaderresetAmplitudeColour", S3Lang.get("reset"),
							   new String[]{"shaderAmplitudeColourR", "shaderAmplitudeColourG", "shaderAmplitudeColourB"}, localChangeListener);

		//
		// Speed Color Flash
		//
		formGui.add("shaderSpeedFlashR", "Flash Speed R", 0.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderSpeedFlashG", "Flash Speed G", 0.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderSpeedFlashB", "Flash Speed B", 0.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.addRandomButton("fxShaderrandomSpeedFlash", S3Lang.get("random"), new String[]{"shaderSpeedFlashR", "shaderSpeedFlashG", "shaderSpeedFlashB"},
								localChangeListener);
		formGui.addResetButton("fxShaderresetSpeedFlash", S3Lang.get("reset"), new String[]{"shaderSpeedFlashR", "shaderSpeedFlashG", "shaderSpeedFlashB"},
							   localChangeListener);

		//
		// Amplitude Color Flash
		//
		formGui.add("shaderAmplitudeFlashR", "Flash Amplitude R", 0.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderAmplitudeFlashG", "Flash Amplitude G", 0.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.add("shaderAmplitudeFlashB", "Flash Amplitude B", 0.0f, -10f, 10f, 0.05f, localChangeListener);
		formGui.addRandomButton("fxShaderrandomAmplitudeFlash", S3Lang.get("random"),
								new String[]{"shaderAmplitudeFlashR", "shaderAmplitudeFlashG", "shaderAmplitudeFlashB"}, localChangeListener);
		formGui.addResetButton("fxShaderresetAmplitudeFlash", S3Lang.get("reset"),
							   new String[]{"shaderAmplitudeFlashR", "shaderAmplitudeFlashG", "shaderAmplitudeFlashB"}, localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){
			shaderId = formGui.getInt("FxShaderId");

			params.mainSpeedX = formGui.getFloat("shaderMainSpeedX");
			params.mainSpeedY = formGui.getFloat("shaderMainSpeedY");
			params.mainSpeedZ = formGui.getFloat("shaderMainSpeedZ");

			params.mainAmplitudeX = formGui.getFloat("shaderMainAmplitudeX");
			params.mainAmplitudeY = formGui.getFloat("shaderMainAmplitudeY");
			params.mainAmplitudeZ = formGui.getFloat("shaderMainAmplitudeZ");

			params.mainStepX = formGui.getFloat("shaderMainStepX");
			params.mainStepY = formGui.getFloat("shaderMainStepY");
			params.mainStepZ = formGui.getFloat("shaderMainStepZ");

			params.amplitudeColourR = formGui.getFloat("shaderAmplitudeColourR");
			params.amplitudeColourG = formGui.getFloat("shaderAmplitudeColourG");
			params.amplitudeColourB = formGui.getFloat("shaderAmplitudeColourB");

			params.mainIterations = formGui.getFloat("shaderMainIterations");
			params.mainPower = formGui.getFloat("shaderMainPower");
			params.mainLight = formGui.getFloat("shaderMainLight");

			params.speedFlashR = formGui.getFloat("shaderSpeedFlashR");
			params.speedFlashG = formGui.getFloat("shaderSpeedFlashG");
			params.speedFlashB = formGui.getFloat("shaderSpeedFlashB");

			params.amplitudeFlashR = formGui.getFloat("shaderAmplitudeFlashR");
			params.amplitudeFlashG = formGui.getFloat("shaderAmplitudeFlashG");
			params.amplitudeFlashB = formGui.getFloat("shaderAmplitudeFlashB");

			shaderName = listOfShader[shaderId];
		}
		if (data.objectMesh != null){
			data.shader = S3ResourceManager.getSimpleShader(shaderName, data.objectMesh.hasColor, data.objectMesh.hasNormal, data.objectMesh.numTexCoords);
		} else {
			if (data.pixmap == null){
				data.shader = S3ResourceManager.getSimpleShader(shaderName, 0);
			} else {
				data.shader = S3ResourceManager.getSimpleShader(shaderName, 1);
			}
		}
		if (data.shader instanceof EffectShader){
			((EffectShader) data.shader).setShaderParams(params);
		}
		data.shader.begin();
	}
}
