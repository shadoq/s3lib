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
package mobi.shad.s3lib.gfx.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import mobi.shad.s3lib.gfx.node.core.Effect;
import mobi.shad.s3lib.gfx.util.CaptureTexture;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Jarek
 */
public class PostRender extends Effect{

	GuiForm effectData = new GuiForm(this.getClass().getSimpleName());
	CaptureTexture captureTexture = new CaptureTexture();
	CaptureTexture captureBloowTexture = new CaptureTexture(256, 256);
	private Texture sourceTexture;
	private Texture destTexture;
	//
	// Post Blur Setting
	//
	private boolean blurSwitch = false;
	private int blurGridMode = 0;
	private int blurMode = 0;
	private float blurSizeX = 1f;
	private float blurSizeY = 1f;
	private float blurStrength = 1f;
	private float blurThreshold = 0.75f;
	private float blurOpacity = 0.25f;
	private ShaderProgram blurShader;
	private ShaderProgram blurShaderX;
	private ShaderProgram blurShaderY;
	private Texture blurSourceTexture;
	private Texture blurDestTexture;
	//
	// Vignette Switch
	//
	private boolean vignetteSwitch = false;
	private float vignetteRadius = 0.2f;
	private float vignetteRadius2 = 0.8f;
	//
	// Sepia Switch
	//
	private boolean sepiaSwitch = false;
	private float sepiaOpacity = 0.75f;
	private Color sepiaColor = new Color(1.0f, 0.8f, 0.6f, 1.0f);
	//
	// levelSwitch
	//
	private boolean levelSwitch = false;
	private float levelGamma = 0.5f;
	// private Color sepiaColor=new Color(1.0f, 0.8f, 0.6f, 1.0f);
	//
	// Mix Shader
	//
	private ShaderProgram mixShader;
	private int blendMode = 0;
	//
	// Theshord Shader
	//
	private ShaderProgram thresholdShader;
	private boolean disableChange;

	/**
	 *
	 */
	public PostRender(){
		initData();
		init();
	}

	/**
	 * @param fadeMode
	 * @param fadeColor
	 */
	public PostRender(int mode, Color color){
		initData();
		init();
	}

	/**
	 * @param random
	 */
	public PostRender(boolean random){
		initData();
		effectData.set("blurSwitch", true);
		init();
	}

	private void initData(){

		disableChange = true;
		//
		// Gui Element
		//
		ChangeListener listener = new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){

				if (disableChange){
					return;
				}
				if (S3Constans.NOTICE){
					S3Log.log("effectStarFieldWindow::changed", "event: " + event.toString() + " actor: "
					+ actor.toString());
				}
				disableChange = true;
				stop();
				init();
				disableChange = false;
			}
		};
		effectData = new GuiForm(this.getClass().getSimpleName());

		effectData.addCheckBox("blurSwitch", S3Lang.get("blurSwitch"), true, listener);
		effectData.addCheckBox("vignetteSwitch", S3Lang.get("vignetteSwitch"), true, listener);
		effectData.addCheckBox("sepiaSwitch", S3Lang.get("sepiaSwitch"), false, listener);

		effectData.addCheckBox("levelSwitch", S3Lang.get("levelSwitch"), false, listener);
		effectData.add("levelGamma", S3Lang.get("levelGamma"), 0.50f, -1.0f, 1.0f, 0.1f, listener);

		String[] blurGridModeList = new String[]{"32x32", "64x64", "128x128", "256x256", "512x512"};
		effectData.addSelectIndex("blurGridMode", S3Lang.get("blurGridMode"), 0, blurGridModeList, listener);

		String[] blurModeList = new String[]{"blurClamp", "blurClampx2", "blurClampx3", "blurBox", "blurX", "blurY"};
		effectData.addSelectIndex("blurMode", S3Lang.get("blurMode"), 0, blurModeList, listener);

		String[] blendModeList = new String[]{"Soft", "Add", "Add2", "Dst", "Src"};
		effectData.addSelectIndex("blendMode", S3Lang.get("blendMode"), 0, blendModeList, listener);

		effectData.add("blurThreshold", S3Lang.get("blurThreshold"), 0.50f, 0.0f, 1.0f, 0.1f, listener);
		effectData.add("blurOpacity", S3Lang.get("blurOpacity"), 1.00f, 0.0f, 1.0f, 0.1f, listener);
		effectData.add("blurSizeX", S3Lang.get("blurSizeX"), 0.5f, 0.1f, 2.0f, 0.1f, listener);
		effectData.add("blurSizeY", S3Lang.get("blurSizeY"), 0.5f, 0.1f, 2.0f, 0.1f, listener);
		effectData.add("blurStrength", S3Lang.get("blurStrength"), 1.0f, 0, 10.0f, 0.1f, listener);

		effectData.add("sepiaOpacity", S3Lang.get("sepiaOpacity"), 0.75f, 0.0f, 1.0f, 0.05f, listener);
		effectData.addColorSelect("sepiaColor", S3Lang.get("sepiaColor"), sepiaColor, listener);

		effectData.add("vignetteRadius", S3Lang.get("vignetteRadius"), 0.2f, 0.0f, 1.0f, 0.05f, listener);
		effectData.add("vignetteRadius2", S3Lang.get("vignetteRadius2"), 0.8f, 0.0f, 1.0f, 0.05f, listener);

		effectData.addSavePresentButton("save_present", S3Lang.get("save_present"), listener);
		effectData.addReadPresentButton("read_present", S3Lang.get("read_present"), listener);

		if (thresholdShader == null){
			thresholdShader = S3ResourceManager.getShaderProgram("shader_simple/threshold");
		}
		if (blurShader == null){
			blurShader = S3ResourceManager.getShaderProgram("shader_simple/blur");
		}
		if (blurShaderY == null){
			blurShaderY = S3ResourceManager.getShaderProgram("shader_simple/blur_y");
		}
		if (blurShaderX == null){
			blurShaderX = S3ResourceManager.getShaderProgram("shader_simple/blur_x");
		}
		if (mixShader == null){
			mixShader = S3ResourceManager.getShaderProgram("shader_simple/mix");
		}

		disableChange = false;

	}

	/**
	 *
	 */
	@Override
	public final void init(){

		//
		// Setting
		//
		blurSwitch = effectData.getBoolean("blurSwitch");
		blurGridMode = effectData.getInt("blurGridMode");
		blurMode = effectData.getInt("blurMode");
		blurThreshold = effectData.getFloat("blurThreshold");
		blurOpacity = effectData.getFloat("blurOpacity");
		blurSizeX = effectData.getFloat("blurSizeX");
		blurSizeY = effectData.getFloat("blurSizeY");
		blurStrength = effectData.getFloat("blurStrength");
		blendMode = effectData.getInt("blendMode");

		vignetteSwitch = effectData.getBoolean("vignetteSwitch");
		vignetteRadius = effectData.getFloat("vignetteRadius");
		vignetteRadius2 = effectData.getFloat("vignetteRadius2");

		sepiaSwitch = effectData.getBoolean("sepiaSwitch");
		sepiaOpacity = effectData.getFloat("sepiaOpacity");
		sepiaColor = effectData.getColor("sepiaColor");

		levelSwitch = effectData.getBoolean("levelSwitch");
		levelGamma = 1.0f - effectData.getFloat("levelGamma");

		//
		//
		//
		S3Log.log("PostRenderEffect::init", "gridMode: " + blurGridMode + " blurMode: " + blurMode, 3);

		switch (blurGridMode){
			case 0:
				captureBloowTexture = new CaptureTexture(32, 32);
				break;
			case 1:
				captureBloowTexture = new CaptureTexture(64, 64);
				break;
			case 2:
				captureBloowTexture = new CaptureTexture(128, 128);
				break;
			case 3:
				captureBloowTexture = new CaptureTexture(256, 256);
				break;
			case 4:
				captureBloowTexture = new CaptureTexture(512, 512);
				break;
		}

		//
		// Create threshold shader
		//
		thresholdShader.begin();
		{
			thresholdShader.setUniformf("blurThreshold", blurThreshold);
		}
		thresholdShader.end();
		//
		// Create blur shaders
		//
		blurShader.begin();
		{
			blurShader.setUniformf("blurSizeX", blurSizeX / 64);
			blurShader.setUniformf("blurSizeY", blurSizeY / 64);
			blurShader.setUniformf("blurStrength", blurStrength);
		}
		blurShader.end();

		blurShaderX.begin();
		{
			blurShaderX.setUniformf("blurSizeX", blurSizeX / 64);
			blurShaderX.setUniformf("blurStrength", blurStrength);
		}
		blurShaderX.end();

		blurShaderY.begin();
		{
			blurShaderY.setUniformf("blurSizeY", blurSizeY / 64);
			blurShaderY.setUniformf("blurStrength", blurStrength);
		}
		blurShaderY.end();

		//
		// Create PostProcessing End
		//
		mixShader.begin();
		{
			mixShader.setUniformi("u_texture0", 0);
			mixShader.setUniformi("u_texture1", 1);

			if (blurSwitch){
				mixShader.setUniformi("blurSwitch", 1);
			} else {
				mixShader.setUniformi("blurSwitch", 0);
			}
			mixShader.setUniformi("blendMode", blendMode);
			mixShader.setUniformf("blurOpacity", blurOpacity);

			Vector2 res = new Vector2(S3Screen.width, S3Screen.height);
			mixShader.setUniformf("resolution", res);

			if (vignetteSwitch){
				mixShader.setUniformi("vignetteSwitch", 1);
			} else {
				mixShader.setUniformi("vignetteSwitch", 0);
			}
			mixShader.setUniformf("vignetteRadius", vignetteRadius);
			mixShader.setUniformf("vignetteRadius2", vignetteRadius2);

			if (sepiaSwitch){
				mixShader.setUniformi("sepiaSwitch", 1);
			} else {
				mixShader.setUniformi("sepiaSwitch", 0);
			}
			mixShader.setUniformf("sepiaOpacity", sepiaOpacity);
			mixShader.setUniformf("sepiaColor", new Vector3(sepiaColor.r, sepiaColor.g, sepiaColor.b));

			if (levelSwitch){
				mixShader.setUniformi("levelSwitch", 1);
			} else {
				mixShader.setUniformi("levelSwitch", 0);
			}
			mixShader.setUniformf("levelGamma", levelGamma);
		}
		mixShader.end();
	}

	@Override
	public void start(){
		init();
	}

	/**
	 *
	 */
	@Override
	public void stop(){
	}

	/**
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 */
	@Override
	public void update(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
	}

	/**
	 *
	 */
	@Override
	public void render(Batch batch, float parentAlpha){
	}

	/**
	 * @return
	 */
	@Override
	public Table gui(){
		disableChange = true;
		Table table = effectData.getTable();
		disableChange = false;
		return table;
	}

	/**
	 * @param writer
	 * @return
	 */
	public XmlWriter savePrefs(XmlWriter writer){
		try {
			writer.element("effect").attribute("class", this.getClass().getName());
			effectData.save(writer);
			writer.pop();
			return writer;
		} catch (IOException ex){
			S3Log.log("savePrefsToJson", ex.toString() + " Exception in " + this.getClass().getName());
			return writer;
		}
	}

	/**
	 * @param effectElement
	 */
	@Override
	public void readPrefs(Element effectElement){

		if (effectElement == null){
			return;
		}
		String effectClass = effectElement.getAttribute("class", "null");

		S3Log.log("readPrefs", " Effect class:" + effectClass);
		if (!effectClass.equalsIgnoreCase(this.getClass().getName())){
			S3Log.log("readPrefs", " !!! Class is not the same.");
			return;
		}
		effectData.read(effectElement);
		init();
	}

	@Override
	public void preRender(){
		captureTexture.begin();
	}

	@Override
	public void postRender(){

		sourceTexture = captureTexture.getResultTexture();
		if (blurSwitch){
			processBlurFilter();
		} else {
			captureTexture.end();
			sourceTexture = captureTexture.getResultTexture();
			destTexture = captureTexture.getResultTexture();
		}

		//
		// Draw result
		//
		S3.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		S3.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (blurSwitch || sepiaSwitch || vignetteSwitch || levelSwitch){

			S3.gl.glEnable(GL20.GL_TEXTURE);
			S3Gfx.drawFlipMeshQuad(sourceTexture, destTexture, mixShader);

			//
			// Clear texture unit
			//
			S3.gl.glActiveTexture(GL20.GL_TEXTURE0);
			S3.gl.glDisable(GL20.GL_TEXTURE);
		} else {
			S3Gfx.drawFlipBackground(sourceTexture);
		}

		//		S3Gfx.drawBackground(sourceTexture, 0, 0, 300, 300);
		//		S3Gfx.drawBackground(destTexture, 300, 600, 0, 300);

	}

	/**
	 *
	 */
	private void processBlurFilter(){

		//
		// Create LowRes Blum Texture
		//
		captureTexture.end();
		captureBloowTexture.begin();

		S3.gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		S3.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		S3Gfx.drawBackground(captureTexture.getResultTexture(), thresholdShader);
		captureBloowTexture.pingPong();

		switch (blurMode){
			default:
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderX);
				captureBloowTexture.pingPong();
				S3Gfx.drawFlipBackground(captureBloowTexture.getResultTexture(), blurShaderY);
				break;
			case 1:
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderX);
				captureBloowTexture.pingPong();
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderY);
				captureBloowTexture.pingPong();
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderX);
				captureBloowTexture.pingPong();
				S3Gfx.drawFlipBackground(captureBloowTexture.getResultTexture(), blurShaderY);
				break;
			case 2:
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderX);
				captureBloowTexture.pingPong();
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderY);
				captureBloowTexture.pingPong();
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderX);
				captureBloowTexture.pingPong();
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderY);
				captureBloowTexture.pingPong();
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderX);
				captureBloowTexture.pingPong();
				S3Gfx.drawFlipBackground(captureBloowTexture.getResultTexture(), blurShaderY);
				break;
			case 3:
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShader);
				break;
			case 4:
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderX);
				break;
			case 5:
				S3Gfx.drawBackground(captureBloowTexture.getResultTexture(), blurShaderY);
				break;
		}

		captureBloowTexture.end();
		destTexture = captureBloowTexture.getResultTexture();
	}

	@Override
	public boolean isRender(){
		return false;
	}

	@Override
	public void getGuiDefinition(ArrayList<String[]> guiDef){

	}

	@Override
	public void getValues(ArrayMap<String, String> values){

	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

	}

	@Override
	public void read(Json json, JsonValue jsonData){

	}

	@Override
	public void write(Json json, Object objectWrite){

	}
}
