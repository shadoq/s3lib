/*******************************************************************************
 * Copyright 2012
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
package mobi.shad.s3libTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.gfx.g3d.simpleobject.VertexCube;
import mobi.shad.s3lib.gfx.g3d.simpleobject.VertexCylinder;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class Gl20Test extends S3App{

	FrameBuffer frameBuffer;
	Mesh mesh;
	ShaderProgram meshShader;
	Texture texture;
	Texture texture2;
	SpriteBatch spriteBatch;
	S3Mesh s3mesh;
	S3Mesh s3mesh2;
	S3Mesh s3mesh3;

	public void update(){
	}

	@Override
	public void render(S3Gfx g){

		frameBuffer.begin();
		S3Screen.gl20.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
		S3Screen.gl20.glClearColor(0.4f, 0.2f, 0.1f, 1f);
		S3Screen.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		S3Screen.gl20.glEnable(GL20.GL_TEXTURE_2D);
		texture.bind();
		meshShader.begin();
		meshShader.setUniformi("u_texture", 0);
		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();
		frameBuffer.end();
		//
		S3Screen.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		S3Screen.gl20.glClearColor(0.4f, 0.2f, 0.6f, 1f);
		S3Screen.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		spriteBatch.draw(frameBuffer.getColorBufferTexture(), 0, 0, 256, 256, 0, 0, frameBuffer.getColorBufferTexture()
																							   .getWidth(), frameBuffer.getColorBufferTexture().getHeight(),
						 false, true);
		spriteBatch.end();

		S3Gfx.render2DBegin();
		s3mesh.render(GL20.GL_TRIANGLES, texture2);
		S3Gfx.render2DEnd();

		s3mesh2.render(GL20.GL_TRIANGLES, texture2);
		s3mesh3.render(GL20.GL_TRIANGLES, texture2);
	}

	@Override
	public void initalize(){
		mesh = new Mesh(true, 3, 0, new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_Position"),
						new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "a_Color"), new VertexAttribute(
		VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoords"));
		float c1 = Color.toFloatBits(255, 0, 0, 255);
		float c2 = Color.toFloatBits(255, 0, 0, 255);
		float c3 = Color.toFloatBits(0, 0, 255, 255);

		mesh.setVertices(new float[]{-0.5f, -0.5f, 0, c1, 0, 0, 0.5f, -0.5f, 0, c2, 1, 0, 0, 0.5f, 0, c3, 0.5f, 1});

		texture = new Texture(512, 512, Pixmap.Format.RGBA8888);

		spriteBatch = new SpriteBatch();
		frameBuffer = new FrameBuffer(Pixmap.Format.RGB565, 128, 128, false);
		createShader();

		//
		//
		//
		texture2 = new Texture(S3File.getFileHandle("texture/def256.png"));

		s3mesh = new S3Mesh(100, true, true, true, false);
		s3mesh.addVertex(-0.5f, -0.5f, 0, 0, 1, 0, 1, 1, 1);
		s3mesh.addVertex(0.5f, -0.5f, 0, 1, 1, 1, 0, 1, 1);
		s3mesh.addVertex(0.5f, 0.5f, 0, 1, 0, 1, 1, 0, 1);

		s3mesh.addQuad(new Vector3(400, 400, 0), new Vector3(600, 400, 0), new Vector3(600, 200, 0), new Vector3(400,
																												 200, 0));

		s3mesh.addVertex(20, 20, 0, 0, 1, 0, 1, 1, 1);
		s3mesh.addVertex(200, 300, 0, 1, 1, 1, 0, 1, 1);
		s3mesh.addVertex(400, 100, 0, 1, 0, 1, 1, 0, 1);

		s3mesh.addTrilange(new Vector3(120, 120, 0), new Vector3(400, 400, 0), new Vector3(400, 30, 0), new Vector2(0,
																													1), new Vector2(1, 1), new Vector2(1f, 0f),
						   Color.WHITE, Color.GREEN, Color.YELLOW);

		s3mesh2 = VertexCube.cube();
		s3mesh3 = VertexCylinder.cylinder();
	}

	private void createShader(){
		String vertexShader = "attribute vec4 a_Position;    \n" + "attribute vec4 a_Color;\n"
		+ "attribute vec2 a_texCoords;\n" + "varying vec4 v_Color;" + "varying vec2 v_texCoords; \n"
		+ "void main()                  \n" + "{                            \n" + "   v_Color = a_Color;"
		+ "   v_texCoords = a_texCoords;\n" + "   gl_Position =   a_Position;  \n"
		+ "}                            \n";
		String fragmentShader = "#ifdef GL_ES\n" + "precision mediump float;\n" + "#endif\n"
		+ "varying vec4 v_Color;\n" + "varying vec2 v_texCoords; \n" + "uniform sampler2D u_texture;\n"
		+ "void main()                                  \n" + "{                                            \n"
		+ "  gl_FragColor = v_Color * texture2D(u_texture, v_texCoords);\n" + "}";

		meshShader = new ShaderProgram(vertexShader, fragmentShader);
		if (meshShader.isCompiled() == false){
			throw new IllegalStateException(meshShader.getLog());
		}
	}
}
