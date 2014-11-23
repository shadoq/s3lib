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
package mobi.shad.s3lib.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.gfx.g3d.shaders.BaseShader;
import mobi.shad.s3lib.gui.GuiResource;

import java.util.ArrayList;

/**
 * Class to store information about 3d object,
 * points, vertex, color, shader, etc.
 */
public class S3Mesh{

	public boolean hasTextureCoordinates = false;
	public boolean hasNormal = false;
	public boolean hasColor = false;
	public boolean is2dOrtho = false;
	public boolean isUpdate = false;
	public boolean isBlend = true;
	public int blendSrcFunc = GL20.GL_SRC_ALPHA;
	public int blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
	public int vertices = 0;
	public int verticesCount = 0;
	public int indices = 0;
	public int componentsVertices = 0;
	public int componentsTextures = 0;
	public int componentsNormals = 0;
	public int componentsColor = 0;
	public int allDataOffset = 0;
	public int numTexCoords = 1;
	public ArrayList<VertexAttribute> attributes;
	//
	// ShaderOld Matrix
	//
	public Matrix4 modelViewMatrix = new Matrix4();
	public Matrix4 projectionMatrix = new Matrix4();
	//
	// Mesh data
	//
	public Mesh mesh;
	public BaseShader shader;
	public float[] vertexBuffer;

	/**
	 * @param vertices
	 * @param is2dOrtho
	 * @param texture
	 * @param color
	 * @param normal
	 */
	public S3Mesh(int vertices, boolean is2dOrtho, boolean texture, boolean color, boolean normal){

		this.hasTextureCoordinates = texture;
		this.hasNormal = normal;
		this.hasColor = color;
		this.is2dOrtho = is2dOrtho;

		this.vertices = vertices;
		initMesh();
	}

	/**
	 *
	 */
	private void initMesh(){

		componentsVertices = 3;

		if (hasTextureCoordinates){
			componentsTextures = 2;
			numTexCoords = 1;
		} else {
			componentsTextures = 0;
			numTexCoords = 0;
		}

		if (hasNormal){
			componentsNormals = 3;
		} else {
			componentsNormals = 0;
		}

		if (hasColor){
			componentsColor = 4;
		} else {
			componentsColor = 0;
		}

		indices = vertices / 3;
		verticesCount = 0;
		isUpdate = false;
		allDataOffset = componentsVertices + componentsTextures + componentsNormals + componentsColor;
		try {
			vertexBuffer = new float[(vertices + 1) * allDataOffset];
		} catch (OutOfMemoryError ex){
			GuiResource.alertMemory(S3Lang.get("OutOfMemoryMesh"));
		}

		S3Log.log("S3Mesh:initMesh", "Buffer size: " + (vertices * allDataOffset) + " vertices: " + vertices
		+ " verticesCount: " + verticesCount + " allDataOffset: " + allDataOffset, 1);

		if (is2dOrtho){
			projectionMatrix.setToOrtho2D(0, 0, S3Screen.width, S3Screen.height);
		}

		//
		// Create shader
		//
		if (S3Constans.DEBUG){
			S3Log.log("S3Mesh:initMesh", "Create default shader ...", 2);
		}

		shader = S3ResourceManager.getSimpleShader("", hasColor, hasNormal, numTexCoords);

		attributes = new ArrayList<VertexAttribute>();
		attributes.add(VertexAttribute.Position());

		if (hasTextureCoordinates){
			attributes.add(VertexAttribute.TexCoords(0));
		}
		if (hasNormal){
			attributes.add(VertexAttribute.Normal());
		}
		if (hasColor){
			attributes.add(VertexAttribute.ColorUnpacked());
		}
		mesh = new Mesh(true, vertices, 0, attributes.toArray(new VertexAttribute[attributes.size()]));
		isUpdate = false;

		if (S3Constans.DEBUG){
			S3Log.log("S3Mesh:initMesh", "dataOffset=" + allDataOffset + " vertices=" + vertices + " vetextBufferSize="
			+ vertexBuffer.length, 1);
			S3Log.log("S3Mesh:initMesh", "projectionMatrix=" + projectionMatrix + " transformMatrix=" + modelViewMatrix, 2);
		}
	}

	public void setShader(String shaderName){
		shader = S3ResourceManager.getSimpleShader(shaderName, hasColor, hasNormal, numTexCoords);
		if (S3Constans.NOTICE){
			S3Log.log("S3Mesh:initMesh", "Shader compile: " + shader.getLog());
		}
	}

	public void setShader(BaseShader shader){
		this.shader = shader;
	}

	/**
	 *
	 */
	private void dataToMesh(){
		if (verticesCount > 0 && isUpdate == true){
			mesh.setVertices(vertexBuffer, 0, verticesCount * allDataOffset);
		}
		isUpdate = false;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void addVertex(float x, float y){
		addVertex(x, y, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1);
	}

	/**
	 * @param x
	 * @param y
	 */
	public void addVertex(float x, float y, float z){
		addVertex(x, y, z, 0, 0, 0, 0, 0, 1, 1, 1, 1);
	}

	public void addVertex(float x, float y, float z, float textureX, float textureY){
		addVertex(x, y, z, textureX, textureY, 0, 0, 0, 1, 1, 1, 1);
	}

	/**
	 * @param x
	 * @param y
	 * @param colorR
	 * @param colorG
	 * @param colorB
	 * @param colorA
	 */
	public void addVertex(float x, float y, float colorR, float colorG, float colorB, float colorA){
		addVertex(x, y, 0, 0, 0, 0, 0, 0, colorR, colorG, colorB, colorA);
	}

	public void addVertex(float x, float y, float z, float colorR, float colorG, float colorB, float colorA){
		addVertex(x, y, z, 0, 0, 0, 0, 0, colorR, colorG, colorB, colorA);
	}

	public void addVertex(float x, float y, float z, float textureX, float textureY, float colorR, float colorG, float colorB, float colorA){
		addVertex(x, y, z, textureX, textureY, 0, 0, 0, colorR, colorG, colorB, colorA);
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param textureX
	 * @param textureY
	 * @param normalX
	 * @param normalY
	 * @param normalZ
	 * @param colorR
	 * @param colorG
	 * @param colorB
	 * @param colorA
	 */
	public int addVertex(float x, float y, float z, float textureX, float textureY, float normalX, float normalY, float normalZ, float colorR, float colorG,
						 float colorB, float colorA){

		int offset = verticesCount * allDataOffset;
		//
		// Add Vertex
		//
		vertexBuffer[offset] = x;
		vertexBuffer[offset + 1] = y;
		vertexBuffer[offset + 2] = z;
		if (hasTextureCoordinates){
			vertexBuffer[offset + componentsVertices] = textureX;
			vertexBuffer[offset + componentsVertices + 1] = textureY;
		}
		if (hasNormal){
			vertexBuffer[offset + componentsVertices + componentsTextures] = normalX;
			vertexBuffer[offset + componentsVertices + componentsTextures + 1] = normalY;
			vertexBuffer[offset + componentsVertices + componentsTextures + 2] = normalZ;
		}
		if (hasColor){
			vertexBuffer[offset + componentsVertices + componentsTextures + componentsNormals] = colorR;
			vertexBuffer[offset + componentsVertices + componentsTextures + componentsNormals + 1] = colorG;
			vertexBuffer[offset + componentsVertices + componentsTextures + componentsNormals + 2] = colorB;
			vertexBuffer[offset + componentsVertices + componentsTextures + componentsNormals + 3] = colorA;
		}
		isUpdate = true;
		verticesCount++;
		return verticesCount;
	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getVertex(int position){
		position = position * allDataOffset;
		float[] out = {vertexBuffer[position], vertexBuffer[position + 1], vertexBuffer[position + 2]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getVertexX(int position){
		position = position * allDataOffset;
		return vertexBuffer[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getVertexY(int position){
		position = position * allDataOffset;
		return vertexBuffer[position + 1];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getVertexZ(int position){
		position = position * allDataOffset;
		return vertexBuffer[position + 2];
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 */
	public void setVertex(int position, float x, float y){
		position = position * allDataOffset;
		vertexBuffer[position] = x;
		vertexBuffer[position + 1] = y;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setVertex(int position, float x, float y, float z){
		position = position * allDataOffset;
		vertexBuffer[position] = x;
		vertexBuffer[position + 1] = y;
		vertexBuffer[position + 2] = z;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getColor(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures + componentsNormals;
		float[] out = {vertexBuffer[position], vertexBuffer[position + 1], vertexBuffer[position + 2],
					   vertexBuffer[position + 3]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorR(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures + componentsNormals;
		return vertexBuffer[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorG(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures + componentsNormals;
		return vertexBuffer[position + 1];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorB(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures + componentsNormals;
		return vertexBuffer[position + 2];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorA(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures + componentsNormals;
		return vertexBuffer[position + 3];
	}

	/**
	 * @param position
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColor(int position, float r, float g, float b, float a){
		position = position * allDataOffset + componentsVertices + componentsTextures + componentsNormals;
		vertexBuffer[position] = r;
		vertexBuffer[position + 1] = g;
		vertexBuffer[position + 2] = b;
		vertexBuffer[position + 3] = a;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setColor(int position, float r, float g, float b){
		position = position * allDataOffset + componentsVertices + componentsTextures + componentsNormals;
		vertexBuffer[position] = r;
		vertexBuffer[position + 1] = g;
		vertexBuffer[position + 2] = b;
		isUpdate = true;

	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getTextureCoord(int position){
		position = position * allDataOffset + componentsVertices;
		float[] out = {vertexBuffer[position], vertexBuffer[position + 1]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getTextureCoordX(int position){
		position = position * allDataOffset + componentsVertices;
		return vertexBuffer[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getTextureCoordY(int position){
		position = position * allDataOffset + componentsVertices;
		return vertexBuffer[position + 1];
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 */
	public void setTextureCoord(int position, float x, float y){
		position = position * allDataOffset + componentsVertices;
		vertexBuffer[position] = x;
		vertexBuffer[position + 1] = y;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 */
	public void setNormal(int position, float x, float y){
		position = position * allDataOffset + componentsVertices + componentsTextures;
		vertexBuffer[position] = x;
		vertexBuffer[position + 1] = y;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setNormal(int position, float x, float y, float z){
		position = position * allDataOffset + componentsVertices + componentsTextures;
		vertexBuffer[position] = x;
		vertexBuffer[position + 1] = y;
		vertexBuffer[position + 2] = z;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getNormal(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures;
		float[] out = {vertexBuffer[position], vertexBuffer[position + 1], vertexBuffer[position + 2]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getNormalX(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures;
		return vertexBuffer[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getNormalY(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures;
		return vertexBuffer[position + 1];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getNormalZ(int position){
		position = position * allDataOffset + componentsVertices + componentsTextures;
		return vertexBuffer[position + 2];
	}

	/**
	 * Dodaje Quada P2---------P3 | | P1---------P4
	 *
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public void addQuad(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4){
		addTrilange(p1, p2, p3, new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1), new Color(Color.RED), new Color(
		Color.YELLOW), new Color(Color.GREEN));
		addTrilange(p1, p3, p4, new Vector2(0, 0), new Vector2(1, 1), new Vector2(0, 1), new Color(Color.RED), new Color(
		Color.GREEN), new Color(Color.CYAN));
		isUpdate = true;
	}

	public void addQuad(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, Color c1, Color c2, Color c3, Color c4){
		addTrilange(p1, p2, p3, new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1), c1, c2, c3);
		addTrilange(p1, p3, p4, new Vector2(0, 0), new Vector2(1, 1), new Vector2(0, 1), c1, c3, c4);
		isUpdate = true;
	}

	public void addQuad(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, float minU, float maxU, float minV, float maxV){
		addTrilange(p1, p2, p3, new Vector2(minU, minV), new Vector2(maxU, minV), new Vector2(maxU, maxV), new Color(
		Color.RED), new Color(Color.YELLOW), new Color(Color.GREEN));
		addTrilange(p1, p3, p4, new Vector2(minU, minV), new Vector2(maxU, maxV), new Vector2(minU, maxV), new Color(
		Color.RED), new Color(Color.GREEN), new Color(Color.CYAN));
		isUpdate = true;
	}

	public void updateQuad(int position, Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4){
		updateTrilange(position, p1, p2, p3);
		updateTrilange(position + 3, p1, p3, p4);
		isUpdate = true;
	}

	public void addTrilange(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 t1, Vector2 t2, Vector2 t3, Color c1, Color c2, Color c3){
		Plane f1 = new Plane(p1, p3, p2);
		addVertex(p1.x, p1.y, p1.z, t1.x, t1.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, c1.r, c1.g, c1.b, c1.a);
		addVertex(p2.x, p2.y, p2.z, t2.x, t2.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, c2.r, c2.g, c2.b, c2.a);
		addVertex(p3.x, p3.y, p3.z, t3.x, t3.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, c3.r, c3.g, c3.b, c3.a);
		isUpdate = true;
	}

	public void updateTrilange(int position, Vector3 p1, Vector3 p2, Vector3 p3){
		setVertex(position, p1.x, p1.y, p1.z);
		setVertex(position + 1, p2.x, p2.y, p2.z);
		setVertex(position + 2, p3.x, p3.y, p3.z);
		isUpdate = true;
	}

	public Matrix4 getModelViewMatrix(){
		return modelViewMatrix;
	}

	public void setModelViewMatrix(Matrix4 modelViewMatrix){
		this.modelViewMatrix = modelViewMatrix;
	}

	public Matrix4 getProjectionMatrix(){
		return projectionMatrix;
	}

	public void setProjectionMatrix(Matrix4 projectionMatrix){
		this.projectionMatrix = projectionMatrix;
	}

	public Mesh getMesh(){

		if (mesh == null){
			dataToMesh();
		}
		if (isUpdate){
			dataToMesh();
		}
		return mesh;
	}

	/**
	 * @return
	 */
	public Model getModel(){
		dataToMesh();
		Material mat = new Material(ColorAttribute.createDiffuse(Color.WHITE));
		return getModel(mesh, 0, mesh.getNumVertices(), GL20.GL_TRIANGLES, mat);
	}

	/**
	 * @param mat
	 * @return
	 */
	public Model getModel(Material mat){
		dataToMesh();
		return getModel(mesh, 0, mesh.getNumVertices(), GL20.GL_TRIANGLES, mat);
	}

	/**
	 * @return
	 */
	public Model getModel(final Mesh mesh, int indexOffset, int vertexCount, int primitiveType, final Material material){

		Model result = new Model();
		MeshPart meshPart = new MeshPart();
		meshPart.id = "part1";
		meshPart.indexOffset = indexOffset;
		meshPart.numVertices = vertexCount;
		meshPart.primitiveType = primitiveType;
		meshPart.mesh = mesh;

		NodePart partMaterial = new NodePart();
		partMaterial.material = material;
		partMaterial.meshPart = meshPart;
		Node node = new Node();
		node.id = "node1";
		node.parts.add(partMaterial);

		result.meshes.add(mesh);
		result.materials.add(material);
		result.nodes.add(node);
		result.meshParts.add(meshPart);
		result.manageDisposable(mesh);
		return result;
	}

	public void render(int primitiveType){
		this.render(primitiveType, null, 0);
	}

	public void render(int primitiveType, Texture texture){
		this.render(primitiveType, texture, 0);
	}

	/**
	 * @param primitiveType
	 */
	public void render(int primitiveType, Texture texture, float time){

		if (mesh == null){
			dataToMesh();
		}
		if (isUpdate){
			dataToMesh();
		}

		if (hasTextureCoordinates){
			Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
			texture.bind();
		}
		shader.begin();

		shader.setModelViewMatrix(modelViewMatrix);
		shader.setProjectionMatrix(projectionMatrix);
		shader.setResolutionShader(S3Screen.width, S3Screen.height);

		for (int i = 0; i < numTexCoords; i++){
			shader.setUniformi("u_sampler" + i, i);
		}

		mesh.render(shader.getShader(), primitiveType);
		shader.end();

	}

	/**
	 * @param primitiveType
	 */
	public void render(int primitiveType, BaseShader shader, Texture texture, float time){

		if (mesh == null){
			dataToMesh();
		}
		if (isUpdate){
			dataToMesh();
		}

		if (hasTextureCoordinates){
			Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
			texture.bind();
		}

		shader.begin();
		shader.setModelViewMatrix(modelViewMatrix);
		shader.setProjectionMatrix(projectionMatrix);
		shader.setResolutionShader(S3Screen.width, S3Screen.height);

		for (int i = 0; i < numTexCoords; i++){
			shader.setUniformi("u_sampler" + i, i);
		}

		if (isBlend){
			S3Screen.gl20.glEnable(GL20.GL_BLEND);
			S3Screen.gl20.glBlendFunc(blendSrcFunc, blendDstFunc);
		} else {
			S3Screen.gl20.glDisable(GL20.GL_BLEND);
		}

		mesh.render(shader.getShader(), primitiveType);
		shader.end();
	}
}
