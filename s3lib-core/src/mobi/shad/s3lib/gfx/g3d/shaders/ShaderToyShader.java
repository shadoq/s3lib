/*******************************************************************************
 * Copyright 2014
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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import mobi.shad.s3lib.main.*;
import mobi.shad.s3lib.main.constans.GuiType;

/**
 * Created by Jarek on 2014-09-24.
 */
public class ShaderToyShader extends BaseShader{

	private boolean isResolution = false;
	private int uniformResolution = 0;
	private boolean isIGlobalTime = false;
	private int uniformIGlobalTime = 0;
	private boolean isiMouse = false;
	private int uniformiMouse = 0;

	private Vector2 mousePos = new Vector2(S3Screen.width / 2, S3Screen.height / 2);
	private Vector3 resolution = new Vector3(S3Screen.width, S3Screen.height, 0);

	public ShaderToyShader(){
		this("", 0, false, false, 0);
	}

	public ShaderToyShader(boolean hasColor, boolean hasNormal, int numTexCoords){
		this("", 0, hasColor, hasNormal, numTexCoords);
	}

	public ShaderToyShader(String shaderName, int processIdx, boolean hasColor, boolean hasNormal, int numTexCoords){

		this.shaderName = shaderName;
		this.hasColor = hasColor;
		this.hasNormal = hasNormal;
		this.numTexCoords = numTexCoords;

		if (!shaderName.equalsIgnoreCase("") && !shaderName.equalsIgnoreCase("default") && !shaderName.equalsIgnoreCase("-= default =-")){

			String shaderFile = "shader_toy/" + shaderName + ".json";
			S3Log.log("SimpleShader::ReadShader", "Read file shader: " + shaderFile);

			JsonReader jsonReader = new JsonReader();
			JsonValue jsonValue = jsonReader.parse(S3File.getFileHandle(shaderFile));

			JsonValue jsonValueChild0;

			jsonValueChild0 = jsonValue.get(processIdx);
			if (jsonValueChild0 == null){
				jsonValueChild0 = jsonValue.get(0);
			}
			final String ver = jsonValueChild0.getString("ver");

			final JsonValue child1 = jsonValueChild0.get(1);
			final JsonValue child2 = jsonValueChild0.get(2);

			final JsonValue renderpass = jsonValueChild0.getChild("renderpass");

			final String id = child1.getString("id");
			final String date = child1.getString("date");
			final String viewed = child1.getString("viewed");
			final String name = child1.getString("name");
			final String username = child1.getString("username");
			final String description = child1.getString("description");

			final String code = renderpass.getString("code");
			final String type = renderpass.getString("type");

			S3Log.log("EffectShader", "Load shader: id: " + id + " name: " + name);

			vertexInit = "";
			vertexMain = "";
			fragmentInit = "";
			fragmentMain = code;
			fragmentFragColor = "";

		} else {
			vertexInit = "";
			vertexMain = "";
			fragmentInit = "";
			fragmentMain = "";
			fragmentFragColor = "";
		}
		compileShader();
	}

	@Override
	protected void createDefaultUniforms(){
		super.createDefaultUniforms();
		uniforms.removeKey("time");
		uniforms.removeKey("resolution");
		uniforms.removeKey("startPosition");
		uniforms.put("iResolution", new Paramets("iResolution", GuiType.NONE, ValueType.VECTOR3, ""));
		uniforms.put("iGlobalTime", new Paramets("iGlobalTime", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("iMouse", new Paramets("iMouse", GuiType.NONE, ValueType.VECTOR2, ""));
	}

	@Override
	protected void assingUniformLocation(){

		super.assingUniformLocation();

		if (shaderProgram.hasUniform("iResolution")){
			isResolution = true;
			uniformResolution = shaderProgram.fetchUniformLocation("iResolution", true);
		}
		if (shaderProgram.hasUniform("iGlobalTime")){
			isIGlobalTime = true;
			uniformIGlobalTime = shaderProgram.fetchUniformLocation("iGlobalTime", true);
		}
		if (shaderProgram.hasUniform("iMouse")){
			isiMouse = true;
			uniformiMouse = shaderProgram.fetchUniformLocation("iMouse", true);
		}
	}

	protected String createFragmentShader(){

		//precision highp int;
		//precision lowp sampler2D;
		//mediump

		String fragmentProgram =
		""
		+ "#ifdef GL_ES						\n"
		+ " precision mediump float;			\n"
		+ "#else								\n"
		+ " precision mediump float;			\n"
		+ "#endif								\n";

		if (hasColor){
			fragmentProgram += "varying vec4 v_col;					\n";
		}
		for (int i = 0; i < numTexCoords; i++){
			fragmentProgram += "varying vec2 v_tex" + i + ";			\n";
			fragmentProgram += "uniform sampler2D iChannel" + i + ";	\n";
		}

		for (ObjectMap.Entry<String, Paramets> parameter : uniforms){
			if (!parameter.value.isVertex){
				switch (parameter.value.valueType){
					case MATRIX4:
						fragmentProgram += "uniform mat4 " + parameter.key + ";\n";
						break;
					case FLOAT:
						fragmentProgram += "uniform float " + parameter.key + ";\n";
						break;
					case VECTOR2:
						fragmentProgram += "uniform vec2 " + parameter.key + ";\n";
						break;
					case VECTOR3:
						fragmentProgram += "uniform vec3 " + parameter.key + ";\n";
						break;
					case VECTOR4:
						fragmentProgram += "uniform vec4 " + parameter.key + ";\n";
						break;
				}
			}
		}

		if (fragmentMain != null && fragmentMain.length() > 0){
			fragmentProgram += "\n " + fragmentMain + "\n";
		} else {

			if (fragmentInit != null && fragmentInit.length() > 0){
				fragmentProgram += "\n" + fragmentInit + "\n";
			}

			fragmentProgram += "void main() {" + "\n";

			if (numTexCoords > 0){
				fragmentProgram += "vec2 position = -1.0 + 2.0*iChanel.xy;" + "\n";
			} else {
				fragmentProgram += "vec2 position = -1.0 + 2.0*gl_FragCoord.xy/resolution.xy;" + "\n";
			}

			fragmentProgram += "gl_FragColor = " + (hasColor ? "v_col" : "vec4(1, 1, 1, 1)");

			if (numTexCoords > 0){
				fragmentProgram += " * ";
			}

			for (int i = 0; i < numTexCoords; i++){
				if (i == numTexCoords - 1){
					fragmentProgram += " texture2D(iChanel" + i + ",  v_tex" + i + ")";
				} else {
					fragmentProgram += " texture2D(iChanel" + i + ",  v_tex" + i + ") *";
				}
			}

			if (fragmentFragColor != null && fragmentFragColor.length() > 0){
				fragmentProgram += " * " + fragmentFragColor;
			}

			fragmentProgram += ";" + "\n}";
		}

		if (S3Constans.DEBUG){
			S3Log.logNumerLines("TestShader::fragmentProgram", fragmentProgram);
		}

		return fragmentProgram;
	}

	@Override
	public void begin(){

		shaderProgram.begin();

		if (isIGlobalTime){
			shaderProgram.setUniformf(uniformIGlobalTime, S3.osTime);
		}
		if (isResolution){
			shaderProgram.setUniformf(uniformResolution, resolution);
		}
		if (isiMouse){
			shaderProgram.setUniformf(uniformiMouse, mousePos);
		}
	}

	public Vector2 getMousePos(){
		return mousePos;
	}

	public void setMousePos(Vector2 mousePos){
		this.mousePos = mousePos;
	}

	public void setMousePos(int x, int y){
		mousePos.set(x, y);
	}
}
