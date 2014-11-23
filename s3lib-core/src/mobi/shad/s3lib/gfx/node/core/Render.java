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
package mobi.shad.s3lib.gfx.node.core;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import mobi.shad.s3lib.gfx.g3d.shaders.BaseShader;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class Render{

	private static BaseShader defaultShader;
	private static BaseShader defaultColorShader;
	private static BaseShader shader = null;
	private static Texture texture = new Texture(1, 1, Pixmap.Format.RGBA4444);
	private static PerspectiveCamera perspectiveCamera;
	private static Camera camera = null;
	private static Matrix4 modelViewMatrix = new Matrix4();
	private static Matrix4 projectionMatrix = new Matrix4();
	private static boolean debug = false;
	private static ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static SpriteBatch spriteBatch = new SpriteBatch();
	private static Mesh renderMesh;

	static{
		//
		// Init GL2.0
		//
		defaultShader = new BaseShader();
		defaultColorShader = new BaseShader("", true, false, 1);

		spriteBatch = new SpriteBatch();
		Pixmap pixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill();
		texture = new Texture(pixmap);
		modelViewMatrix = new Matrix4();
		projectionMatrix = new Matrix4();
		projectionMatrix.setToOrtho2D(0, 0, S3Screen.width, S3Screen.height);

		perspectiveCamera = new PerspectiveCamera(67, S3Screen.width, S3Screen.height);
		perspectiveCamera.position.set(5f, 5f, 5f);
		perspectiveCamera.lookAt(0, 0, 0);
		perspectiveCamera.update();
	}

	/**
	 *
	 */
	public static void render(final Data data){

		//
		// Update data
		//
		if (data.textureChange && data.pixmap != null){
			if (data.texture == null){
				data.texture = new Texture(data.pixmap);
				data.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			} else {
				data.texture.draw(data.pixmap, 0, 0);
			}
			data.textureChange = false;
		}

		if (data.type == Data.Type.EFFECT_2D || data.type == Data.Type.NONE){
			camera = null;
		} else {
			if (data.perspectiveCamera != null){
				camera = data.perspectiveCamera;
			} else {
				camera = perspectiveCamera;
			}
		}

		//
		// ObjectMesh
		//
		if (data.objectMesh != null){

			if (data.shader != null){
				shader = data.shader;
			} else {
				if (data.shader != null){
					shader = data.shader;
				} else {

					if (data.objectMesh != null){
						if (data.objectMesh.hasColor){
							shader = defaultColorShader;
						} else {
							shader = defaultShader;
						}
					} else {
						shader = defaultShader;
					}
				}
			}

			shader.begin();
			if (camera != null){
				shader.setModelViewMatrix(camera.view);
				shader.setProjectionMatrix(camera.projection);
			} else {
				shader.setModelViewMatrix(modelViewMatrix);
				shader.setProjectionMatrix(projectionMatrix);
			}
			shader.setResolutionShader(S3Screen.width, S3Screen.height);

			if (data.texture != null){
				data.texture.bind(0);
				S3Screen.gl20.glEnable(GL20.GL_TEXTURE_2D);
				shader.setUniformi("u_sampler0", 0);
			} else {
				texture.bind(0);
				S3Screen.gl20.glEnable(GL20.GL_TEXTURE_2D);
				shader.setUniformi("u_sampler0", 0);
			}
			renderMesh = data.objectMesh.getMesh();
			renderMesh.render(shader.getShader(), data.primitiveType);
			shader.end();
		} else if (data.spritePosition != null){

			//
			// Sprite Batch
			//
			spriteBatch.begin();
			spriteBatch.setColor(Color.WHITE);
			spriteBatch.disableBlending();

			if (data.spriteColor != null && data.spriteSize != null){
				int size = data.spritePosition.length - 2;
				if (data.spriteBlend){
					spriteBatch.enableBlending();
				} else {
					spriteBatch.disableBlending();
				}
				if (data.texture != null){
					for (int i = 0, j = 0; i < size; i += 2, j += 4){
						spriteBatch
						.setColor(data.spriteColor[j], data.spriteColor[j + 1], data.spriteColor[j + 2], data.spriteColor[j + 3]);
						spriteBatch
						.draw(data.texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteSize[i], data.spriteSize[i + 1]);
					}
				} else if (data.spriteTexture != null){
					for (int i = 0, j = 0; i < size; i += 2, j += 4){
						spriteBatch
						.setColor(data.spriteColor[j], data.spriteColor[j + 1], data.spriteColor[j + 2], data.spriteColor[j + 3]);
						spriteBatch
						.draw(data.spriteTexture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteSize[i], data.spriteSize[i + 1]);
					}
				} else {
					for (int i = 0, j = 0; i < size; i += 2, j += 4){
						spriteBatch
						.setColor(data.spriteColor[j], data.spriteColor[j + 1], data.spriteColor[j + 2], data.spriteColor[j + 3]);
						spriteBatch
						.draw(texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteSize[i], data.spriteSize[i + 1]);
					}
				}
			} else if (data.spriteColor != null){
				int size = data.spritePosition.length - 2;
				if (data.spriteBlend){
					spriteBatch.enableBlending();
				} else {
					spriteBatch.disableBlending();
				}
				if (data.texture != null){
					for (int i = 0, j = 0; i < size; i += 2, j += 4){
						spriteBatch
						.setColor(data.spriteColor[j], data.spriteColor[j + 1], data.spriteColor[j + 2], data.spriteColor[j + 3]);
						spriteBatch
						.draw(data.texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteWidth, data.spriteHeight);
					}
				} else if (data.spriteTexture != null){
					for (int i = 0, j = 0; i < size; i += 2, j += 4){
						spriteBatch
						.setColor(data.spriteColor[j], data.spriteColor[j + 1], data.spriteColor[j + 2], data.spriteColor[j + 3]);
						spriteBatch
						.draw(data.spriteTexture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteWidth, data.spriteHeight);
					}
				} else {
					for (int i = 0, j = 0; i < size; i += 2, j += 4){
						spriteBatch
						.setColor(data.spriteColor[j], data.spriteColor[j + 1], data.spriteColor[j + 2], data.spriteColor[j + 3]);
						spriteBatch
						.draw(texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteWidth, data.spriteHeight);
					}
				}
			} else if (data.spriteSize != null){
				int size = data.spritePosition.length - 2;
				if (data.spriteBlend){
					spriteBatch.enableBlending();
				} else {
					spriteBatch.disableBlending();
				}
				spriteBatch.setColor(data.color);
				if (data.texture != null){
					for (int i = 0; i < size; i += 2){
						spriteBatch
						.draw(data.texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteSize[i], data.spriteSize[i + 1]);
					}
				} else if (data.spriteTexture != null){
					for (int i = 0; i < size; i += 2){
						spriteBatch
						.draw(data.spriteTexture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteSize[i], data.spriteSize[i + 1]);
					}
				} else {
					for (int i = 0; i < size; i += 2){
						spriteBatch
						.draw(texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteSize[i], data.spriteSize[i + 1]);
					}
				}
			} else {
				int size = data.spritePosition.length - 2;
				if (data.spriteBlend){
					spriteBatch.enableBlending();
				} else {
					spriteBatch.disableBlending();
				}
				spriteBatch.setColor(data.color);
				if (data.texture != null){
					for (int i = 0; i < size; i += 2){
						spriteBatch
						.draw(data.texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteWidth, data.spriteHeight);
					}
				} else if (data.spriteTexture != null){
					for (int i = 0; i < size; i += 2){
						spriteBatch
						.draw(data.spriteTexture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteWidth, data.spriteHeight);
					}
				} else {
					for (int i = 0; i < size; i += 2){
						spriteBatch
						.draw(texture, data.spritePosition[i], data.spritePosition[i + 1], data.spriteWidth, data.spriteHeight);
					}
				}
			}
			spriteBatch.end();

		} else if (data.solidFont != null){

			//
			// SolidFont Graphics
			//
			data.solidFont.draw();

		} else if (data.bitmapFont != null){

			//
			// BitmapFont Graphics
			//
			if (data.bitmapFontWrite > -1){
				spriteBatch.begin();
				data.bitmapFont.draw(spriteBatch, 0, data.bitmapFontWrite);
				spriteBatch.end();
			} else {
				spriteBatch.begin();
				data.bitmapFont.draw(spriteBatch);
				spriteBatch.end();
			}

		} else if (data.type == Data.Type.EFFECT_2D || data.type == Data.Type.NONE){

			//
			// 2D Graphics
			//
			S3Gfx.drawBackground(data.texture, data.color, data.shader, data.startX, data.startY, data.endX, data.endY);
		}

		//
		// Draw debug
		//
		if (debug == true){

			if (data.type == Data.Type.EFFECT_3D){
				if (camera != null){
					shapeRenderer.setProjectionMatrix(camera.combined);
				}
				shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
				for (int z = -10; z <= 10; z++){
					shapeRenderer.setColor(Color.DARK_GRAY);
					shapeRenderer.line(-10, 0, z, 10, 0, z);
					shapeRenderer.setColor(Color.LIGHT_GRAY);
					shapeRenderer.line(z, 0, 10, z, 0, -10);
				}

				shapeRenderer.setColor(Color.RED);
				shapeRenderer.line(0, 0, 0, 100, 0, 0);
				shapeRenderer.setColor(Color.GREEN);
				shapeRenderer.line(0, 0, 0, 0, 100, 0);
				shapeRenderer.setColor(Color.BLUE);
				shapeRenderer.line(0, 0, 0, 0, 0, 100);

				shapeRenderer.end();
			} else {
				shapeRenderer.setTransformMatrix(modelViewMatrix);
				shapeRenderer.setProjectionMatrix(projectionMatrix);

				shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
				shapeRenderer.setColor(Color.DARK_GRAY);
				shapeRenderer.line(S3Constans.gridX1, 0, S3Constans.gridX1, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX2, 0, S3Constans.gridX2, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX3, 0, S3Constans.gridX3, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX4, 0, S3Constans.gridX4, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX5, 0, S3Constans.gridX5, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX6, 0, S3Constans.gridX6, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX7, 0, S3Constans.gridX7, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX8, 0, S3Constans.gridX8, S3Screen.height);
				shapeRenderer.line(S3Constans.gridX9, 0, S3Constans.gridX9, S3Screen.height);

				shapeRenderer.setColor(Color.LIGHT_GRAY);
				shapeRenderer.line(0, S3Constans.gridY1, S3Screen.width, S3Constans.gridY1);
				shapeRenderer.line(0, S3Constans.gridY2, S3Screen.width, S3Constans.gridY2);
				shapeRenderer.line(0, S3Constans.gridY3, S3Screen.width, S3Constans.gridY3);
				shapeRenderer.line(0, S3Constans.gridY4, S3Screen.width, S3Constans.gridY4);
				shapeRenderer.line(0, S3Constans.gridY5, S3Screen.width, S3Constans.gridY5);
				shapeRenderer.line(0, S3Constans.gridY6, S3Screen.width, S3Constans.gridY6);
				shapeRenderer.line(0, S3Constans.gridY7, S3Screen.width, S3Constans.gridY7);
				shapeRenderer.line(0, S3Constans.gridY8, S3Screen.width, S3Constans.gridY8);
				shapeRenderer.line(0, S3Constans.gridY9, S3Screen.width, S3Constans.gridY9);

				shapeRenderer.end();
			}

		}
	}
}
