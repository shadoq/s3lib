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
package mobi.shad.s3lib.gfx.g3d.simpleobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FloatArray;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.utils.ShortArray;

import java.util.ArrayList;

/**
 * @author Jarek
 */
public class ObjectMesh{
	private static Vector3 tmpVec3a = new Vector3();
	private static Vector3 tmpVec3b = new Vector3();
	private static Vector3 tmpVec3c = new Vector3();
	private static Vector3 tmpVec3d = new Vector3();

	private static Vector3 tmpNormalVec3a = new Vector3();
	private static Vector3 tmpNormalVec3b = new Vector3();
	private static Vector3 tmpNormalVec3c = new Vector3();
	private static Vector3 tmpNormalVec3d = new Vector3();

	public boolean hasTextureCoordinates = false;
	public boolean hasNormal = false;
	public boolean hasColor = false;
	public boolean isUpdate = false;
	public int componentsVertices = 0;
	public int componentsTextures = 0;
	public int componentsNormals = 0;
	public int componentsColor = 0;
	public int allDataOffset = 0;
	public int numTexCoords = 1;
	public ArrayList<VertexAttribute> attributes;
	public Mesh mesh;
	public float[] vertex;
	public FloatArray vertexBuffer = new FloatArray();
	public int vertexIndex = 0;
	public int oldVertexIndex = 0;
	public ShortArray indicesBuffer = new ShortArray();
	public int indicesIndex = 0;
	public boolean useIndicesIndex = true;

	//
	// Temporary
	//
	Vector3 temp1Vector3 = new Vector3();
	Vector3 temp2Vector3 = new Vector3();
	Vector3 temp3Vector3 = new Vector3();
	Vector3 temp4Vector3 = new Vector3();
	Vector2 temp1Vector2 = new Vector2();
	Vector2 temp2Vector2 = new Vector2();
	Vector2 temp3Vector2 = new Vector2();
	Vector2 temp4Vector2 = new Vector2();
	Plane tempPlane = new Plane(new Vector3(), new Vector3());

	/**
	 * @param texture
	 * @param color
	 * @param normal
	 */
	public ObjectMesh(boolean texture, boolean color, boolean normal){

		this.hasTextureCoordinates = texture;
		this.hasNormal = normal;
		this.hasColor = color;
		vertexBuffer = new FloatArray();
		indicesBuffer = new ShortArray();
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

		isUpdate = false;
		allDataOffset = componentsVertices + componentsTextures + componentsNormals + componentsColor;

		S3Log.log("S3Mesh:initMesh", "AllDataOffset: " + allDataOffset, 1);
		vertexBuffer.clear();
		vertexIndex = 0;
		indicesBuffer.clear();
		indicesIndex = 0;
		vertex = new float[allDataOffset];
		isUpdate = false;
	}

	/**
	 *
	 */
	private void dataToMesh(){
		if (vertexIndex > 0 && isUpdate == true){

			if (oldVertexIndex != vertexIndex || mesh == null){
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

				mesh = null;
				if (useIndicesIndex){
					mesh = new Mesh(true, vertexBuffer.items.length, indicesBuffer.items.length, attributes.toArray(new VertexAttribute[attributes.size()]));
					mesh.setVertices(vertexBuffer.items);
					mesh.setIndices(indicesBuffer.items);
				} else {
					mesh = new Mesh(true, vertexBuffer.items.length, 0, attributes.toArray(new VertexAttribute[attributes.size()]));
					mesh.setVertices(vertexBuffer.items);
				}
				oldVertexIndex = vertexIndex;
				S3Log.log("initMeshAttribute",
						  "VertexBuffer: " + vertexBuffer.size + " l: " + vertexBuffer.items.length + " IndicesBuffer: " + indicesBuffer.size + " l: " + indicesBuffer.items.length);
				S3Log.log("initMeshAttribute", "Vertex size: " + (mesh.getVertexAttributes().vertexSize / 4));

			} else {
				if (useIndicesIndex){
					mesh.setVertices(vertexBuffer.items);
					mesh.setIndices(indicesBuffer.items);
				} else {
					mesh.setVertices(vertexBuffer.items);
				}
			}
			isUpdate = false;
		}
	}

	/**
	 * @param position
	 * @param vertex
	 * @return
	 */
	private int calculateVertexPosition(int position, int vertex){
		return position * allDataOffset + vertex;
	}

	/**
	 * @param position
	 * @param vertex
	 * @return
	 */
	private int calculateTexturePosition(int position, int vertex){
		return position * allDataOffset + componentsVertices + vertex;
	}

	/**
	 * @param position
	 * @param vertex
	 * @return
	 */
	private int calculateNormalPosition(int position, int vertex){
		return position * allDataOffset + componentsVertices + componentsTextures + vertex;
	}

	/**
	 * @param position
	 * @param vertex
	 * @return
	 */
	private int calculateColorPosition(int position, int vertex){
		return position * allDataOffset + componentsVertices + componentsTextures + componentsNormals + vertex;
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

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param textureX
	 * @param textureY
	 */
	public void addVertex(float x, float y, float z, float textureX, float textureY){
		addVertex(x, y, z, textureX, textureY, 0, 0, 0, 1, 1, 1, 1);
	}

	/**
	 * @param vertex
	 */
	public void addVertex(Vertex vertex){
		addVertex(
		vertex.position.x,
		vertex.position.y,
		vertex.position.z,
		vertex.uv.x,
		vertex.uv.y,
		vertex.normal.x,
		vertex.normal.y,
		vertex.normal.z,
		vertex.color.r,
		vertex.color.g,
		vertex.color.b,
		vertex.color.a);
	}

	/**
	 * @param x
	 * @param y
	 * @param colorR
	 * @param colorG
	 * @param colorB
	 * @param colorA
	 */
	public int addVertex(float x, float y, float colorR, float colorG, float colorB, float colorA){
		return addVertex(x, y, 0, 0, 0, 0, 0, 0, colorR, colorG, colorB, colorA);
	}

	public int addVertex(float x, float y, float z, float colorR, float colorG, float colorB, float colorA){
		return addVertex(x, y, z, 0, 0, 0, 0, 0, colorR, colorG, colorB, colorA);
	}

	public int addVertex(float x, float y, float z, float textureX, float textureY, float colorR, float colorG, float colorB, float colorA){
		return addVertex(x, y, z, textureX, textureY, 0, 0, 0, colorR, colorG, colorB, colorA);
	}

	public int addVertex(Vector3 coordinates, Vector2 textureCoord, Vector3 normal){
		return addVertex(coordinates.x, coordinates.y, coordinates.z, textureCoord.x, textureCoord.y, normal.x, normal.y, normal.z, 1.0f, 1.0f, 1.0f, 1.0f);
	}

	public int addVertex(Vector3 coordinates, Vector2 textureCoord){
		tmpNormalVec3a.set(coordinates).nor();
		return addVertex(coordinates.x, coordinates.y, coordinates.z, textureCoord.x, textureCoord.y, tmpNormalVec3a.x, tmpNormalVec3a.y, tmpNormalVec3a.z,
						 1.0f, 1.0f, 1.0f, 1.0f);
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

		//
		// Add Vertex
		//
		vertex[0] = x;
		vertex[1] = y;
		vertex[2] = z;
		if (hasTextureCoordinates){
			vertex[componentsVertices] = textureX;
			vertex[componentsVertices + 1] = textureY;
		}
		if (hasNormal){
			vertex[componentsVertices + componentsTextures] = normalX;
			vertex[componentsVertices + componentsTextures + 1] = normalY;
			vertex[componentsVertices + componentsTextures + 2] = normalZ;
		}
		if (hasColor){
			vertex[componentsVertices + componentsTextures + componentsNormals] = colorR;
			vertex[componentsVertices + componentsTextures + componentsNormals + 1] = colorG;
			vertex[componentsVertices + componentsTextures + componentsNormals + 2] = colorB;
			vertex[componentsVertices + componentsTextures + componentsNormals + 3] = colorA;
		}

		vertexBuffer.addAll(vertex);
		isUpdate = true;
		vertexIndex++;
		return vertexIndex;
	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getVertex(int position){
		position = calculateVertexPosition(position, 0);
		float[] out = {vertexBuffer.items[position], vertexBuffer.items[position + 1], vertexBuffer.items[position + 2]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getVertexX(int position){
		position = calculateVertexPosition(position, 0);
		return vertexBuffer.items[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getVertexY(int position){
		position = calculateVertexPosition(position, 0);
		return vertexBuffer.items[position + 1];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getVertexZ(int position){
		position = calculateVertexPosition(position, 0);
		return vertexBuffer.items[position + 2];
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 */
	public void setVertex(int position, float x, float y){
		position = calculateVertexPosition(position, 0);
		vertexBuffer.items[position] = x;
		vertexBuffer.items[position + 1] = y;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setVertex(int position, float x, float y, float z){
		position = calculateVertexPosition(position, 0);
		vertexBuffer.items[position] = x;
		vertexBuffer.items[position + 1] = y;
		vertexBuffer.items[position + 2] = z;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getColor(int position){
		position = calculateColorPosition(position, 0);
		float[] out = {vertexBuffer.items[position], vertexBuffer.items[position + 1], vertexBuffer.items[position + 2], vertexBuffer.items[position + 3]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorR(int position){
		position = calculateColorPosition(position, 0);
		return vertexBuffer.items[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorG(int position){
		position = calculateColorPosition(position, 0);
		return vertexBuffer.items[position + 1];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorB(int position){
		position = calculateColorPosition(position, 0);
		return vertexBuffer.items[position + 2];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getColorA(int position){
		position = calculateColorPosition(position, 0);
		return vertexBuffer.items[position + 3];
	}

	/**
	 * @param position
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColor(int position, float r, float g, float b, float a){
		position = calculateColorPosition(position, 0);
		vertexBuffer.items[position] = r;
		vertexBuffer.items[position + 1] = g;
		vertexBuffer.items[position + 2] = b;
		vertexBuffer.items[position + 3] = a;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setColor(int position, float r, float g, float b){
		position = calculateColorPosition(position, 0);
		vertexBuffer.items[position] = r;
		vertexBuffer.items[position + 1] = g;
		vertexBuffer.items[position + 2] = b;
		isUpdate = true;

	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getTextureCoord(int position){
		position = calculateTexturePosition(position, 0);
		float[] out = {vertexBuffer.items[position], vertexBuffer.items[position + 1]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getTextureCoordX(int position){
		position = calculateTexturePosition(position, 0);
		return vertexBuffer.items[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getTextureCoordY(int position){
		position = calculateTexturePosition(position, 0);
		return vertexBuffer.items[position + 1];
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 */
	public void setTextureCoord(int position, float x, float y){
		position = calculateTexturePosition(position, 0);
		vertexBuffer.items[position] = x;
		vertexBuffer.items[position + 1] = y;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 */
	public void setNormal(int position, float x, float y){
		position = calculateNormalPosition(position, 0);
		vertexBuffer.items[position] = x;
		vertexBuffer.items[position + 1] = y;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setNormal(int position, float x, float y, float z){
		position = calculateNormalPosition(position, 0);

		vertexBuffer.items[position] = x;
		vertexBuffer.items[position + 1] = y;
		vertexBuffer.items[position + 2] = z;
		isUpdate = true;
	}

	/**
	 * @param position
	 * @return
	 */
	public float[] getNormal(int position){
		position = calculateNormalPosition(position, 0);
		float[] out = {vertexBuffer.items[position], vertexBuffer.items[position + 1], vertexBuffer.items[position + 2]};
		return out;
	}

	/**
	 * @param position
	 * @return
	 */
	public float getNormalX(int position){
		position = calculateNormalPosition(position, 0);
		return vertexBuffer.items[position];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getNormalY(int position){
		position = calculateNormalPosition(position, 0);
		return vertexBuffer.items[position + 1];
	}

	/**
	 * @param position
	 * @return
	 */
	public float getNormalZ(int position){
		position = calculateNormalPosition(position, 0);
		return vertexBuffer.items[position + 2];
	}

	/**
	 * Dodaje Quada p1 ------------- p2 | \ | | \ | p4 ------------- p3
	 *
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public void addQuad(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4){
		addQuad(p1, p2, p3, p4, new Vector2(0, 0), new Vector2(1, 1));
	}

	public void addQuad(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, Vector2 textCoordMin, Vector2 textCoordMax){

		//
		// Normal
		//
		tempPlane = new Plane(p1, p3, p2);

		//
		// UV Coords
		//
		temp1Vector2.set(textCoordMin.x, textCoordMin.y);
		temp2Vector2.set(textCoordMax.x, textCoordMin.y);
		temp3Vector2.set(textCoordMax.x, textCoordMax.y);
		temp4Vector2.set(textCoordMin.x, textCoordMax.y);

		//
		// Add Vertex
		//
		addVertex(p1, temp1Vector2, tempPlane.normal);
		addVertex(p2, temp2Vector2, tempPlane.normal);
		addVertex(p3, temp3Vector2, tempPlane.normal);
		addVertex(p4, temp4Vector2, tempPlane.normal);

		//
		// Add Indeices
		//
		indicesBuffer.ensureCapacity(3);
		indicesBuffer.add((short) (vertexIndex - 2));
		indicesBuffer.add((short) (vertexIndex - 3));
		indicesBuffer.add((short) (vertexIndex - 4));

		indicesBuffer.add((short) (vertexIndex - 4));
		indicesBuffer.add((short) (vertexIndex - 1));
		indicesBuffer.add((short) (vertexIndex - 2));

		indicesIndex = indicesBuffer.size;
		isUpdate = true;
	}

	public void addTrilange(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 t1, Vector2 t2, Vector2 t3, Color c1, Color c2, Color c3){
		Plane f1 = new Plane(p1, p3, p2);
		addVertex(p1.x, p1.y, p1.z, t1.x, t1.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, c1.r, c1.g, c1.b, c1.a);
		addVertex(p2.x, p2.y, p2.z, t2.x, t2.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, c2.r, c2.g, c2.b, c2.a);
		addVertex(p3.x, p3.y, p3.z, t3.x, t3.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, c3.r, c3.g, c3.b, c3.a);
		isUpdate = true;
	}

	public void addTrilange(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 t1, Vector2 t2, Vector2 t3){
		Plane f1 = new Plane(p1, p3, p2);
		addVertex(p1.x, p1.y, p1.z, t1.x, t1.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, 1.0f, 1.0f, 1.0f, 1.0f);
		addVertex(p2.x, p2.y, p2.z, t2.x, t2.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, 1.0f, 1.0f, 1.0f, 1.0f);
		addVertex(p3.x, p3.y, p3.z, t3.x, t3.y, f1.getNormal().x, f1.getNormal().y, f1.getNormal().z, 1.0f, 1.0f, 1.0f, 1.0f);
		isUpdate = true;
	}

	public void addTrilange(Vertex p1, Vertex p2, Vertex p3){
		addVertex(p1.position.x, p1.position.y, p1.position.z, p1.uv.x, p1.uv.y, p1.normal.x, p1.normal.y, p1.normal.z, p1.color.r, p1.color.g, p1.color.b,
				  p1.color.a);
		addVertex(p2.position.x, p2.position.y, p2.position.z, p2.uv.x, p2.uv.y, p2.normal.x, p2.normal.y, p2.normal.z, p2.color.r, p2.color.g, p2.color.b,
				  p2.color.a);
		addVertex(p3.position.x, p3.position.y, p3.position.z, p3.uv.x, p3.uv.y, p3.normal.x, p3.normal.y, p3.normal.z, p3.color.r, p3.color.g, p3.color.b,
				  p3.color.a);
		isUpdate = true;
	}

	public void updateTrilange(int position, Vector3 p1, Vector3 p2, Vector3 p3){
		setVertex(position + 0, p1.x, p1.y, p1.z);
		setVertex(position + 1, p2.x, p2.y, p2.z);
		setVertex(position + 2, p3.x, p3.y, p3.z);
		isUpdate = true;
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

	@Override
	public String toString(){
		return "ObjectMesh{" + "hasTextureCoordinates=" + hasTextureCoordinates + ", hasNormal=" + hasNormal + ", hasColor=" + hasColor + ", isUpdate=" + isUpdate + ", allDataOffset=" + allDataOffset + ", numTexCoords=" + numTexCoords + ", vertexIndex=" + vertexIndex + ", oldVertexIndex=" + oldVertexIndex + ", indicesIndex=" + indicesIndex + ", useIndicesIndex=" + useIndicesIndex + '}';
	}
}
