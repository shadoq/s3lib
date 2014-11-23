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
package mobi.shad.s3lib.gfx.g3d.shaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.main.S3Screen;
import mobi.shad.s3lib.main.constans.GuiType;

public class EffectShader extends BaseShader{

	protected ShaderDefinition shaderDefinition;
	protected ShaderParameter parameters;

	/**
	 * @author Jarek
	 */
	public static class ShaderParameter{

		public float time = 0;

		//
		//
		//
		public Color colourStart = Color.WHITE;
		public Color colourProcess = Color.WHITE;

		public float amplitudeColourR = 1f;
		public float amplitudeColourG = 1f;
		public float amplitudeColourB = 1f;

		//
		// Flash
		//
		public float speedFlashR = 1f;
		public float speedFlashG = 1f;
		public float speedFlashB = 1f;

		public float amplitudeFlashR = 0f;
		public float amplitudeFlashG = 0f;
		public float amplitudeFlashB = 0f;

		//
		//
		//
		public float mainSpeedX = 1f;
		public float mainSpeedY = 1f;
		public float mainSpeedZ = 1f;

		public float mainAmplitudeX = 1f;
		public float mainAmplitudeY = 1f;
		public float mainAmplitudeZ = 1f;

		public float mainStepX = 1f;
		public float mainStepY = 1f;
		public float mainStepZ = 1f;

		//
		//
		//
		public float mouseSpeedX = 1f;
		public float mouseSpeedY = 1f;

		public float mouseAmplitudeX = 1f;
		public float mouseAmplitudeY = 1f;

		public float mouseStepX = 1f;
		public float mouseStepY = 1f;

		//
		//
		//
		public float mainIterations = 1f;
		public float mainPower = 1f;
		public float mainLight = 1f;

	}

	protected static class ShaderDefinition{
		public boolean isTime = false;
		public boolean isColourStart = false;
		public boolean isColourProcess = false;
		public boolean isAmplitudeColourR = false;
		public boolean isAmplitudeColourG = false;
		public boolean isAmplitudeColourB = false;
		public boolean isSpeedFlashR = false;
		public boolean isSpeedFlashG = false;
		public boolean isSpeedFlashB = false;
		public boolean isAmplitudeFlashR = false;
		public boolean isAmplitudeFlashG = false;
		public boolean isAmplitudeFlashB = false;
		public boolean isMainSpeedX = false;
		public boolean isMainSpeedY = false;
		public boolean isMainSpeedZ = false;
		public boolean isMainAmplitudeX = false;
		public boolean isMainAmplitudeY = false;
		public boolean isMainAmplitudeZ = false;
		public boolean isMainStepX = false;
		public boolean isMainStepY = false;
		public boolean isMainStepZ = false;
		public boolean isMouseSpeedX = false;
		public boolean isMouseSpeedY = false;
		public boolean isMouseAmplitudeX = false;
		public boolean isMouseAmplitudeY = false;
		public boolean isMouseStepX = false;
		public boolean isMouseStepY = false;
		public boolean isMainIterations = false;
		public boolean isMainPower = false;
		public boolean isMainLight = false;
		public boolean isResolution = false;
		public boolean isProjModelViewMatrix = false;
		public boolean isProjectionMatrix = false;
		public boolean isModelViewMatrix = false;

		public int uniformTime = -1;
		public int uniformColourStart = -1;
		public int uniformColourProcess = -1;
		public int uniformAmplitudeColourR = -1;
		public int uniformAmplitudeColourG = -1;
		public int uniformAmplitudeColourB = -1;
		public int uniformSpeedFlashR = -1;
		public int uniformSpeedFlashG = -1;
		public int uniformSpeedFlashB = -1;
		public int uniformAmplitudeFlashR = -1;
		public int uniformAmplitudeFlashG = -1;
		public int uniformAmplitudeFlashB = -1;
		public int uniformMainSpeedX = -1;
		public int uniformMainSpeedY = -1;
		public int uniformMainSpeedZ = -1;
		public int uniformMainAmplitudeX = -1;
		public int uniformMainAmplitudeY = -1;
		public int uniformMainAmplitudeZ = -1;
		public int uniformMainStepX = -1;
		public int uniformMainStepY = -1;
		public int uniformMainStepZ = -1;
		public int uniformMouseSpeedX = -1;
		public int uniformMouseSpeedY = -1;
		public int uniformMouseAmplitudeX = -1;
		public int uniformMouseAmplitudeY = -1;
		public int uniformMouseStepX = -1;
		public int uniformMouseStepY = -1;
		public int uniformMainIterations = -1;
		public int uniformMainPower = -1;
		public int uniformMainLight = -1;
		public int uniformResolution = -1;
		public int uniformProjModelViewMatrix = -1;
		public int uniformProjectionMatrix = -1;
		public int uniformModelViewMatrix = -1;
	}

	public EffectShader(){
		super();
	}

	public EffectShader(boolean hasColor, boolean hasNormal, int numTexCoords){
		super(hasColor, hasNormal, numTexCoords);
	}

	public EffectShader(String shaderName, boolean hasColor, boolean hasNormal, int numTexCoords){
		super(shaderName, hasColor, hasNormal, numTexCoords);
	}

	@Override
	protected void createDefaultUniforms(){
		super.createDefaultUniforms();

		uniforms.put("colourStart", new Paramets("colourStart", GuiType.NONE, ValueType.VECTOR3, ""));
		uniforms.put("colourProcess", new Paramets("colourProcess", GuiType.NONE, ValueType.VECTOR3, ""));

		uniforms.put("amplitudeColourR", new Paramets("amplitudeColourR", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("amplitudeColourG", new Paramets("amplitudeColourG", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("amplitudeColourB", new Paramets("amplitudeColourB", GuiType.NONE, ValueType.FLOAT, ""));

		uniforms.put("speedFlashR", new Paramets("speedFlashR", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("speedFlashG", new Paramets("speedFlashG", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("speedFlashB", new Paramets("speedFlashB", GuiType.NONE, ValueType.FLOAT, ""));

		uniforms.put("amplitudeFlashR", new Paramets("amplitudeFlashR", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("amplitudeFlashG", new Paramets("amplitudeFlashG", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("amplitudeFlashB", new Paramets("amplitudeFlashB", GuiType.NONE, ValueType.FLOAT, ""));

		uniforms.put("mainSpeedX", new Paramets("mainSpeedX", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainSpeedY", new Paramets("mainSpeedY", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainSpeedZ", new Paramets("mainSpeedZ", GuiType.NONE, ValueType.FLOAT, ""));

		uniforms.put("mainAmplitudeX", new Paramets("mainAmplitudeX", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainAmplitudeY", new Paramets("mainAmplitudeY", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainAmplitudeZ", new Paramets("mainAmplitudeZ", GuiType.NONE, ValueType.FLOAT, ""));

		uniforms.put("mainStepX", new Paramets("mainStepX", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainStepY", new Paramets("mainStepY", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainStepZ", new Paramets("mainStepZ", GuiType.NONE, ValueType.FLOAT, ""));

		uniforms.put("mainIterations", new Paramets("mainIterations", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainPower", new Paramets("mainPower", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("mainLight", new Paramets("mainLight", GuiType.NONE, ValueType.FLOAT, ""));
	}

	@Override
	protected void assingUniformLocation(){

		super.assingUniformLocation();

		if (shaderDefinition == null){
			shaderDefinition = new ShaderDefinition();
		}

		if (parameters == null){
			parameters = new ShaderParameter();
		}

		if (shaderProgram.hasUniform("resolution")){
			shaderDefinition.isResolution = true;
			shaderDefinition.uniformResolution = shaderProgram.fetchUniformLocation("resolution", true);
		}
		if (shaderProgram.hasUniform("u_projModelViewMatrix")){
			shaderDefinition.isProjModelViewMatrix = true;
			shaderDefinition.uniformProjModelViewMatrix = shaderProgram.fetchUniformLocation("u_projModelViewMatrix", true);
		}
		if (shaderProgram.hasUniform("u_projectionMatrix")){
			shaderDefinition.isProjectionMatrix = true;
			shaderDefinition.uniformProjectionMatrix = shaderProgram.fetchUniformLocation("u_projectionMatrix", true);
		}
		if (shaderProgram.hasUniform("u_modelViewMatrix")){
			shaderDefinition.isModelViewMatrix = true;
			shaderDefinition.uniformModelViewMatrix = shaderProgram.fetchUniformLocation("u_modelViewMatrix", true);
		}

		if (shaderProgram.hasUniform("time")){
			shaderDefinition.isTime = true;
			shaderDefinition.uniformTime = shaderProgram.fetchUniformLocation("time", true);
		}

		if (shaderProgram.hasUniform("colourStart")){
			shaderDefinition.isColourStart = true;
			shaderDefinition.uniformColourStart = shaderProgram.fetchUniformLocation("colourStart", true);
		}
		if (shaderProgram.hasUniform("colourProcess")){
			shaderDefinition.isColourProcess = true;
			shaderDefinition.uniformColourProcess = shaderProgram.fetchUniformLocation("colourProcess", true);
		}

		if (shaderProgram.hasUniform("amplitudeColourR")){
			shaderDefinition.isAmplitudeColourR = true;
			shaderDefinition.uniformAmplitudeColourR = shaderProgram.fetchUniformLocation("amplitudeColourR", true);
		}
		if (shaderProgram.hasUniform("amplitudeColourG")){
			shaderDefinition.isAmplitudeColourG = true;
			shaderDefinition.uniformAmplitudeColourG = shaderProgram.fetchUniformLocation("amplitudeColourG", true);
		}
		if (shaderProgram.hasUniform("amplitudeColourB")){
			shaderDefinition.isAmplitudeColourB = true;
			shaderDefinition.uniformAmplitudeColourB = shaderProgram.fetchUniformLocation("amplitudeColourB", true);
		}

		if (shaderProgram.hasUniform("speedFlashR")){
			shaderDefinition.isSpeedFlashR = true;
			shaderDefinition.uniformSpeedFlashR = shaderProgram.fetchUniformLocation("speedFlashR", true);
		}
		if (shaderProgram.hasUniform("speedFlashG")){
			shaderDefinition.isSpeedFlashG = true;
			shaderDefinition.uniformSpeedFlashG = shaderProgram.fetchUniformLocation("speedFlashG", true);
		}
		if (shaderProgram.hasUniform("speedFlashB")){
			shaderDefinition.isSpeedFlashB = true;
			shaderDefinition.uniformSpeedFlashB = shaderProgram.fetchUniformLocation("speedFlashB", true);
		}

		if (shaderProgram.hasUniform("amplitudeFlashR")){
			shaderDefinition.isAmplitudeFlashR = true;
			shaderDefinition.uniformAmplitudeFlashR = shaderProgram.fetchUniformLocation("amplitudeFlashR", true);
		}
		if (shaderProgram.hasUniform("amplitudeFlashG")){
			shaderDefinition.isAmplitudeFlashG = true;
			shaderDefinition.uniformAmplitudeFlashG = shaderProgram.fetchUniformLocation("amplitudeFlashG", true);
		}
		if (shaderProgram.hasUniform("amplitudeFlashB")){
			shaderDefinition.isAmplitudeFlashB = true;
			shaderDefinition.uniformAmplitudeFlashB = shaderProgram.fetchUniformLocation("amplitudeFlashB", true);
		}

		if (shaderProgram.hasUniform("mainSpeedX")){
			shaderDefinition.isMainSpeedX = true;
			shaderDefinition.uniformMainSpeedX = shaderProgram.fetchUniformLocation("mainSpeedX", true);
		}
		if (shaderProgram.hasUniform("mainSpeedY")){
			shaderDefinition.isMainSpeedY = true;
			shaderDefinition.uniformMainSpeedY = shaderProgram.fetchUniformLocation("mainSpeedY", true);
		}
		if (shaderProgram.hasUniform("mainSpeedZ")){
			shaderDefinition.isMainSpeedZ = true;
			shaderDefinition.uniformMainSpeedZ = shaderProgram.fetchUniformLocation("mainSpeedZ", true);
		}

		if (shaderProgram.hasUniform("mainAmplitudeX")){
			shaderDefinition.isMainAmplitudeX = true;
			shaderDefinition.uniformMainAmplitudeX = shaderProgram.fetchUniformLocation("mainAmplitudeX", true);
		}
		if (shaderProgram.hasUniform("mainAmplitudeY")){
			shaderDefinition.isMainAmplitudeY = true;
			shaderDefinition.uniformMainAmplitudeY = shaderProgram.fetchUniformLocation("mainAmplitudeY", true);
		}
		if (shaderProgram.hasUniform("mainAmplitudeZ")){
			shaderDefinition.isMainAmplitudeZ = true;
			shaderDefinition.uniformMainAmplitudeZ = shaderProgram.fetchUniformLocation("mainAmplitudeZ", true);
		}

		if (shaderProgram.hasUniform("mainStepX")){
			shaderDefinition.isMainStepX = true;
			shaderDefinition.uniformMainStepX = shaderProgram.fetchUniformLocation("mainStepX", true);
		}
		if (shaderProgram.hasUniform("mainStepY")){
			shaderDefinition.isMainStepY = true;
			shaderDefinition.uniformMainStepY = shaderProgram.fetchUniformLocation("mainStepY", true);
		}
		if (shaderProgram.hasUniform("mainStepZ")){
			shaderDefinition.isMainStepZ = true;
			shaderDefinition.uniformMainStepZ = shaderProgram.fetchUniformLocation("mainStepZ", true);
		}

		if (shaderProgram.hasUniform("mainIterations")){
			shaderDefinition.isMainIterations = true;
			shaderDefinition.uniformMainIterations = shaderProgram.fetchUniformLocation("mainIterations", true);
		}
		if (shaderProgram.hasUniform("mainPower")){
			shaderDefinition.isMainPower = true;
			shaderDefinition.uniformMainPower = shaderProgram.fetchUniformLocation("mainPower", true);
		}
		if (shaderProgram.hasUniform("mainLight")){
			shaderDefinition.isMainLight = true;
			shaderDefinition.uniformMainLight = shaderProgram.fetchUniformLocation("mainLight", true);
		}
	}

	public void setShaderParams(
	Color color, float time,
	float speedFlashR, float speedFlashG, float speedFlashB,
	float amplitudeFlashR, float amplitudeFlashG, float amplitudeFlashB,
	float amplitudeColourR, float amplitudeColourG, float amplitudeColourB,
	float mainSpeedX, float mainAmplitudeX, float mainStepX,
	float mainSpeedY, float mainAmplitudeY, float mainStepY,
	float mainSpeedZ, float mainAmplitudeZ, float mainStepZ){
		parameters.colourProcess = color;
		parameters.time = time;

		parameters.speedFlashR = speedFlashR;
		parameters.speedFlashG = speedFlashG;
		parameters.speedFlashB = speedFlashB;

		parameters.amplitudeFlashR = amplitudeFlashR;
		parameters.amplitudeFlashG = amplitudeFlashG;
		parameters.amplitudeFlashB = amplitudeFlashB;

		parameters.amplitudeColourR = amplitudeColourR;
		parameters.amplitudeColourG = amplitudeColourG;
		parameters.amplitudeColourB = amplitudeColourB;

		parameters.mainSpeedX = mainSpeedX;
		parameters.mainAmplitudeX = mainAmplitudeX;
		parameters.mainStepX = mainStepX;

		parameters.mainSpeedY = mainSpeedY;
		parameters.mainAmplitudeY = mainAmplitudeY;
		parameters.mainStepY = mainStepY;

		parameters.mainSpeedZ = mainSpeedZ;
		parameters.mainAmplitudeZ = mainAmplitudeZ;
		parameters.mainStepZ = mainStepZ;
	}

	public void setShaderParams(EffectShader.ShaderParameter parameters){
		this.parameters = parameters;
	}

	@Override
	public void begin(){

		shaderProgram.begin();

		if (shaderDefinition.isTime){
			shaderProgram.setUniformf(shaderDefinition.uniformTime, this.parameters.time);
		}
		if (shaderDefinition.isResolution){
			shaderProgram.setUniformf(shaderDefinition.uniformResolution, new Vector2(S3Screen.width, S3Screen.height));
		}

		//
		// Colour
		//
		if (shaderDefinition.isColourStart){
			shaderProgram.setUniformf("colourStart", new Vector3(parameters.colourStart.r, parameters.colourStart.g, parameters.colourStart.b));
		}
		if (shaderDefinition.isColourProcess){
			shaderProgram.setUniformf("colourProcess", new Vector3(parameters.colourProcess.r, parameters.colourProcess.g, parameters.colourProcess.b));
		}

		if (shaderDefinition.isAmplitudeColourR){
			shaderProgram.setUniformf("amplitudeColourR", parameters.amplitudeColourR);
		}
		if (shaderDefinition.isAmplitudeColourG){
			shaderProgram.setUniformf("amplitudeColourG", parameters.amplitudeColourG);
		}
		if (shaderDefinition.isAmplitudeColourB){
			shaderProgram.setUniformf("amplitudeColourB", parameters.amplitudeColourB);
		}

		//
		// Colour Flash
		//
		if (shaderDefinition.isSpeedFlashR){
			shaderProgram.setUniformf("speedFlashR", parameters.speedFlashR);
		}
		if (shaderDefinition.isSpeedFlashG){
			shaderProgram.setUniformf("speedFlashG", parameters.speedFlashG);
		}
		if (shaderDefinition.isSpeedFlashB){
			shaderProgram.setUniformf("speedFlashB", parameters.speedFlashB);
		}

		if (shaderDefinition.isAmplitudeFlashR){
			shaderProgram.setUniformf("amplitudeFlashR", parameters.amplitudeFlashR);
		}
		if (shaderDefinition.isAmplitudeFlashG){
			shaderProgram.setUniformf("amplitudeFlashG", parameters.amplitudeFlashG);
		}
		if (shaderDefinition.isAmplitudeFlashB){
			shaderProgram.setUniformf("amplitudeFlashB", parameters.amplitudeFlashB);
		}

		//
		// Main shaderProgram vars
		//
		if (shaderDefinition.isMainSpeedX){
			shaderProgram.setUniformf("mainSpeedX", parameters.mainSpeedX);
		}
		if (shaderDefinition.isMainSpeedY){
			shaderProgram.setUniformf("mainSpeedY", parameters.mainSpeedY);
		}
		if (shaderDefinition.isMainSpeedZ){
			shaderProgram.setUniformf("mainSpeedZ", parameters.mainSpeedZ);
		}

		if (shaderDefinition.isMainAmplitudeX){
			shaderProgram.setUniformf("mainAmplitudeX", parameters.mainAmplitudeX);
		}
		if (shaderDefinition.isMainAmplitudeY){
			shaderProgram.setUniformf("mainAmplitudeY", parameters.mainAmplitudeY);
		}
		if (shaderDefinition.isMainAmplitudeZ){
			shaderProgram.setUniformf("mainAmplitudeZ", parameters.mainAmplitudeZ);
		}

		if (shaderDefinition.isMainStepX){
			shaderProgram.setUniformf("mainStepX", parameters.mainStepX);
		}
		if (shaderDefinition.isMainStepY){
			shaderProgram.setUniformf("mainStepY", parameters.mainStepY);
		}
		if (shaderDefinition.isMainStepZ){
			shaderProgram.setUniformf("mainStepZ", parameters.mainStepZ);
		}

		if (shaderDefinition.isMainIterations){
			shaderProgram.setUniformf("mainIterations", parameters.mainIterations);
		}
		if (shaderDefinition.isMainPower){
			shaderProgram.setUniformf("mainPower", parameters.mainPower);
		}
		if (shaderDefinition.isMainLight){
			shaderProgram.setUniformf("mainLight", parameters.mainLight);
		}
	}
}
