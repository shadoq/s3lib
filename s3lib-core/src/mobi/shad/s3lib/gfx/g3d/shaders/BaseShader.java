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

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3ResourceManager;
import mobi.shad.s3lib.main.constans.GuiType;

/**
 * @author Jarek
 */
public class BaseShader{

	private static int compileCount = 0;
	protected String shaderName = "";
	protected String name = "";
	protected String author = "";
	protected ArrayMap<String, Paramets> uniforms = new ArrayMap<>();
	protected ShaderProgram shaderProgram = null;
	protected String vertexShader = "";
	protected String fragmentShader = "";
	protected String compileLog = "";
	protected String vertexInit = "";
	protected String vertexMain = "";
	protected String fragmentInit = "";
	protected String fragmentMain = "";
	protected String fragmentFragColor = "";
	protected boolean hasColor;
	protected boolean hasNormal;
	protected int numTexCoords;

	public static enum ValueType{
		FLOAT, MATRIX4, VECTOR2, VECTOR3, VECTOR4, COLOR
	}

	public static class Paramets{
		public String name = "";
		public GuiType guiType = GuiType.NONE;
		public ValueType valueType = ValueType.FLOAT;
		public String value = "";
		public boolean isVertex = false;
		public int uniformLocation = 0;
		public boolean activeUniform = false;

		public Paramets(String name, GuiType guiType, ValueType valueType, String value){
			this.name = name;
			this.guiType = guiType;
			this.valueType = valueType;
			this.value = value;
		}

		public Paramets(String name, GuiType guiType, ValueType valueType, String value, boolean isVertex){
			this.name = name;
			this.guiType = guiType;
			this.valueType = valueType;
			this.value = value;
			this.isVertex = isVertex;
		}

		@Override
		public String toString(){
			return "Paramets{" +
			"name='" + name + '\'' +
			", guiType=" + guiType +
			", valueType=" + valueType +
			", value='" + value + '\'' +
			", isVertex=" + isVertex +
			", uniformLocation=" + uniformLocation +
			", activeUniform=" + activeUniform +
			'}';
		}
	}

	public BaseShader(){
		this("", false, false, 1);
	}

	public BaseShader(boolean hasColor, boolean hasNormal, int numTexCoords){
		this("", hasColor, hasNormal, numTexCoords);
	}

	public BaseShader(String shaderName, boolean hasColor, boolean hasNormal, int numTexCoords){

		this.shaderName = shaderName;
		this.hasColor = hasColor;
		this.hasNormal = hasNormal;
		this.numTexCoords = numTexCoords;

		if (!shaderName.equalsIgnoreCase("") && !shaderName.equalsIgnoreCase("default") && !shaderName.equalsIgnoreCase("-= default =-")){

			String shaderFile = "shader/" + shaderName;
			S3Log.log("SimpleShader::ReadShader", "Read file shader: " + shaderFile);

			try {
				XmlReader.Element parse = S3ResourceManager.getXml(shaderFile);
				vertexInit = parse.getChildByName("vertex_init").getText().trim();
				vertexMain = parse.getChildByName("vertex_main").getText().trim();
				fragmentInit = parse.getChildByName("fragment_init").getText().trim();
				fragmentMain = parse.getChildByName("fragment_main").getText().trim();
				fragmentFragColor = parse.getChildByName("fragment_frag").getText().trim();

				name = parse.getChildByName("name").getText().trim();
				author = parse.getChildByName("author").getText().trim();

				if (S3Constans.NOTICE){
					S3Log.log("FileShaderFactory::ReadShader", "Read shader: " + shaderName);
				}
			} catch (Exception ex){
				S3Log.error(BaseShader.class.getName(), "Read error XML Shader", ex);
				shaderName = "Error ...";
			}
		} else {
			vertexInit = "";
			vertexMain = "";
			fragmentInit = "";
			fragmentMain = "";
			fragmentFragColor = "";
		}
	}

	public ShaderProgram getShader(){
		if (shaderProgram == null){
			compileShader();
		}
		return shaderProgram;
	}

	public void compileShader(){

		createDefaultUniforms();

		vertexShader = createVertexShader();
		fragmentShader = createFragmentShader();
		ShaderProgram.pedantic = false;
		if (shaderProgram != null){
			shaderProgram.dispose();
			shaderProgram = null;
		}
		shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
		compileCount++;

		compileLog = shaderProgram.getLog();

		S3Log.log("Shader::compileShader", "shader compile: " + compileLog);

		assingUniformLocation();
		printUniforms();
	}

	public void dispose(){
		if (shaderProgram != null){
			shaderProgram.dispose();
			shaderProgram = null;
		}
	}

	protected void createDefaultUniforms(){
		uniforms.clear();

		uniforms.put("u_modelViewMatrix", new Paramets("u_modelViewMatrix", GuiType.NONE, ValueType.MATRIX4, "", true));
		uniforms.put("u_projectionMatrix", new Paramets("u_projectionMatrix", GuiType.NONE, ValueType.MATRIX4, "", true));

		uniforms.put("time", new Paramets("time", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("resolution", new Paramets("resolution", GuiType.NONE, ValueType.VECTOR2, ""));
		uniforms.put("startPosition", new Paramets("startPosition", GuiType.NONE, ValueType.VECTOR2, ""));
	}

	protected void assingUniformLocation(){
		for (ObjectMap.Entry<String, Paramets> parameter : uniforms){
			if (shaderProgram.hasUniform(parameter.key)){
				parameter.value.uniformLocation = shaderProgram.fetchUniformLocation(parameter.key, true);
				parameter.value.activeUniform = true;
			}
		}
	}

	protected void printUniforms(){
		for (ObjectMap.Entry<String, Paramets> parameter : uniforms){
			if (S3Constans.DEBUG){
				S3Log.log("printUniforms", parameter.toString());
			}
		}
	}

	public String getLog(){
		if (shaderProgram == null){
			compileShader();
		}
		return shaderProgram.getLog();
	}

	public String getName(){
		return name;
	}

	public String getAuthor(){
		return author;
	}

	public void setUniformi(String name, int value){
		shaderProgram.setUniformi(name, value);
	}

	public void setProjectionMatrix(Matrix4 projectionMatrix){
		shaderProgram.setUniformMatrix("u_projectionMatrix", projectionMatrix);
	}

	public void setModelViewMatrix(Matrix4 modelViewMatrix){
		shaderProgram.setUniformMatrix("u_modelViewMatrix", modelViewMatrix);
	}

	public void setTimeShader(final float timeShader){
		shaderProgram.setUniformf("time", timeShader);
	}

	public void setResolutionShader(final float sizeX, final float sizeY){
		shaderProgram.setUniformf("resolution", sizeX, sizeY);
	}

	public void setStartPosition(final float startX, final float startY){
		shaderProgram.setUniformf("startPosition", startX, startY);
	}

	public void begin(){
		shaderProgram.begin();
	}

	public void end(){
		shaderProgram.end();
	}

	/**
	 * @return
	 */
	protected String createVertexShader(){
		String vertexProgram = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n"
		+ (hasNormal ? "attribute vec3 " + ShaderProgram.NORMAL_ATTRIBUTE + ";	\n" : "")
		+ (hasColor ? "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";		\n" : "");

		for (int i = 0; i < numTexCoords; i++){
			vertexProgram += "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + i + ";\n";
		}

		vertexProgram += (hasColor ? "varying vec4 v_col;								\n" : "");

		for (int i = 0; i < numTexCoords; i++){
			vertexProgram += "varying vec2 v_tex" + i + ";								\n";
		}

		for (ObjectMap.Entry<String, Paramets> parameter : uniforms){
			if (parameter.value.isVertex){
				switch (parameter.value.valueType){
					case MATRIX4:
						vertexProgram += "uniform mat4 " + parameter.key + ";\n";
						break;
				}
			}
		}
		if (vertexInit != null && vertexInit.length() > 0){
			vertexProgram += vertexInit;
		}

		vertexProgram +=
		"void main() {															\n"
		+ "   gl_Position = u_projectionMatrix * u_modelViewMatrix * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n"
		+ (hasColor ? "   v_col = " + ShaderProgram.COLOR_ATTRIBUTE + ";			\n" : "");

		for (int i = 0; i < numTexCoords; i++){
			vertexProgram += "   v_tex" + i + " = " + ShaderProgram.TEXCOORD_ATTRIBUTE + i + ";\n";
		}

		if (vertexMain != null && vertexMain.length() > 0){
			vertexProgram += vertexMain;
		}

		vertexProgram += "}				  \n";

		if (S3Constans.DEBUG){
			S3Log.logNumerLines("DefaultShader::vertexProgram", vertexProgram);
		}

		return vertexProgram;
	}

	/**
	 * @return
	 */
	protected String createFragmentShader(){

		//precision highp int;
		//precision lowp sampler2D;
		//mediump

		String fragmentProgram =
		""
		+ "#ifdef GL_ES						\n"
		+ " precision mediump float;			\n"
		+ "#endif								\n";

		if (hasColor){
			fragmentProgram += "varying vec4 v_col;					\n";
		}
		for (int i = 0; i < numTexCoords; i++){
			fragmentProgram += "varying vec2 v_tex" + i + ";			\n";
			fragmentProgram += "uniform sampler2D u_sampler" + i + ";	\n";
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


		if (fragmentInit != null && fragmentInit.length() > 0){
			fragmentProgram += "\n" + fragmentInit + "\n";
		}

		fragmentProgram += "void main() {" + "\n";

		if (numTexCoords > 0){
			fragmentProgram += "vec2 position = -1.0 + 2.0*v_tex0.xy;" + "\n";
		} else {
			fragmentProgram += "vec2 position = -1.0 + 2.0*gl_FragCoord.xy/resolution.xy;" + "\n";
		}

		if (fragmentMain != null && fragmentMain.length() > 0){
			fragmentProgram += "\n " + fragmentMain + "\n";
		}

		fragmentProgram += "gl_FragColor = " + (hasColor ? "v_col" : "vec4(1, 1, 1, 1)");

		if (numTexCoords > 0){
			fragmentProgram += " * ";
		}

		for (int i = 0; i < numTexCoords; i++){
			if (i == numTexCoords - 1){
				fragmentProgram += " texture2D(u_sampler" + i + ",  v_tex" + i + ")";
			} else {
				fragmentProgram += " texture2D(u_sampler" + i + ",  v_tex" + i + ") *";
			}
		}

		if (fragmentFragColor != null && fragmentFragColor.length() > 0){
			fragmentProgram += " * " + fragmentFragColor;
		}

		fragmentProgram += ";" + "\n}";

		if (S3Constans.DEBUG){
			S3Log.logNumerLines("TestShader::fragmentProgram", fragmentProgram);
		}

		return fragmentProgram;
	}

}
