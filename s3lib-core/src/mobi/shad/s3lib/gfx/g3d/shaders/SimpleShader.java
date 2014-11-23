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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.constans.GuiType;

/**
 * Created by Jarek on 2014-09-27.
 */
public class SimpleShader extends BaseShader{

	private final static String vertexDefault = "void main(){\n" +
	"	gl_Position = projectionMatrix * modelViewMatrix * a_position;\n" +
	"}\n\n";
	private final static String fragmentDefault = "void main(){\n" +
	"	vec2 position = gl_FragCoord.xy / iResolution.xy;\n" +
	"	gl_FragColor = vec4(position, 0.5+0.5*sin(iGlobalTime),1.0);" +
	"}\n\n";

	protected float time = 0;
	protected Vector2 resolution = new Vector2();
	protected Vector2 mouse = new Vector2();
	protected Vector2 posistion = new Vector2();

	public static class ShaderData{
		public String type;
		public String id;
		public String name;
		public String date;
		public String author;
		public String description;
		public String tags;

		public boolean color = false;
		public boolean normal = false;
		public int textures = 0;

		public String vertex;
		public String fragment;
	}

	public SimpleShader(){
		this(vertexDefault, fragmentDefault, false, false, 0);
	}

	public SimpleShader(SimpleShader.ShaderData shaderData){

		this.hasColor = shaderData.color;
		this.hasNormal = shaderData.normal;
		this.numTexCoords = shaderData.textures;

		name = shaderData.name;
		vertexMain = shaderData.vertex;
		fragmentMain = shaderData.fragment;
		compileShader();

	}

	public SimpleShader(final String vertex, final String fragment, final boolean hasColor, final boolean hasNormal, final int numTexCoords){

		this.hasColor = hasColor;
		this.hasNormal = hasNormal;
		this.numTexCoords = numTexCoords;

		vertexMain = vertex;
		fragmentMain = fragment;
		compileShader();
	}

	@Override
	public void setProjectionMatrix(Matrix4 projectionMatrix){
		shaderProgram.setUniformMatrix("projectionMatrix", projectionMatrix);
	}

	@Override
	public void setModelViewMatrix(Matrix4 modelViewMatrix){
		shaderProgram.setUniformMatrix("modelViewMatrix", modelViewMatrix);
	}

	@Override
	public void setTimeShader(final float timeShader){
		time = timeShader;
	}

	@Override
	public void setResolutionShader(final float sizeX, final float sizeY){
		resolution.set(sizeX, sizeY);
	}

	@Override
	public void setStartPosition(float startX, float startY){
		posistion.set(startX, startY);
	}

	public void setMouse(float x, float y){
		mouse.set(x, y);
	}

	@Override
	public void begin(){
		super.begin();
		shaderProgram.setUniformf("iGlobalTime", time);
		shaderProgram.setUniformf("iResolution", resolution);
		shaderProgram.setUniformf("iPosition", posistion);
		shaderProgram.setUniformf("iMouse", mouse);
	}

	@Override
	protected void createDefaultUniforms(){
		super.createDefaultUniforms();

		uniforms.removeKey("u_modelViewMatrix");
		uniforms.removeKey("u_projectionMatrix");
		uniforms.removeKey("time");
		uniforms.removeKey("resolution");
		uniforms.removeKey("startPosition");

		uniforms.put("modelViewMatrix", new Paramets("modelViewMatrix", GuiType.NONE, ValueType.MATRIX4, "", true));
		uniforms.put("projectionMatrix", new Paramets("projectionMatrix", GuiType.NONE, ValueType.MATRIX4, "", true));

		uniforms.put("iGlobalTime", new Paramets("iGlobalTime", GuiType.NONE, ValueType.FLOAT, ""));
		uniforms.put("iResolution", new Paramets("iResolution", GuiType.NONE, ValueType.VECTOR2, ""));
		uniforms.put("iPosition", new Paramets("iResolution", GuiType.NONE, ValueType.VECTOR2, ""));
		uniforms.put("iMouse", new Paramets("iMouse", GuiType.NONE, ValueType.VECTOR2, ""));
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

		if (vertexMain != null && vertexMain.length() > 0){
			vertexProgram += vertexMain;
		}

		if (S3Constans.DEBUG){
			S3Log.logNumerLines("DefaultShader::vertexProgram", vertexProgram);
		}

		return vertexProgram;
	}

	/**
	 * @return
	 */
	protected String createFragmentShader(){

		String fragmentProgram =
		""
		+ "#ifdef GL_ES							\n"
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

		if (fragmentMain != null && fragmentMain.length() > 0){
			fragmentProgram += "\n " + fragmentMain + "\n";
		}

		if (S3Constans.DEBUG){
			S3Log.logNumerLines("TestShader::fragmentProgram", fragmentProgram);
		}

		return fragmentProgram;
	}

}
