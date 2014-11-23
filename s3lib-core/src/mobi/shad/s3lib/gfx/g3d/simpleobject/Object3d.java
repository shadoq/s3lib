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

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.main.S3Mesh;

/**
 * @author Jarek
 */
public class Object3d{

	protected static S3Mesh mesh;
	protected static int numTrilange = 0;
	protected static boolean useNormal = true;
	protected static boolean useColor = true;
	protected static boolean useUV = true;

	private static Vector3 tmpVec3a = new Vector3();
	private static Vector3 tmpVec3b = new Vector3();
	private static Vector3 tmpVec3c = new Vector3();
	private static Vector3 tmpVec3d = new Vector3();

	private static Vector3 tmpNormalVec3a = new Vector3();
	private static Vector3 tmpNormalVec3b = new Vector3();
	private static Vector3 tmpNormalVec3c = new Vector3();
	private static Vector3 tmpNormalVec3d = new Vector3();

	public Object3d(){
	}

	/**
	 *
	 */
	protected static void init(){
		mesh = new S3Mesh(20000, false, useUV, useColor, useNormal);
		numTrilange = 0;
	}

	/**
	 * @return
	 */
	protected static S3Mesh dataToMesh(){
		return mesh;
	}

	/**
	 * @return
	 */
	public static Mesh getMesh(){
		return mesh.getMesh();
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	protected static void addQuad(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4){
		addQuad(p1, p2, p3, p4, 0, 1, 0, 1);
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param minU
	 * @param maxU
	 * @param minV
	 * @param maxV
	 */
	protected static void addQuad(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, float minU, float maxU, float minV, float maxV){
		addTrilange(p1, p2, p3, new Vector2(minU, minV), new Vector2(maxU, minV), new Vector2(maxU, maxV));
		addTrilange(p1, p3, p4, new Vector2(minU, minV), new Vector2(maxU, maxV), new Vector2(minU, maxV));
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param normal
	 * @param n1
	 * @param n2
	 * @param n3
	 */
	protected static void addTrilange(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 normal, Vector2 n1, Vector2 n2, Vector2 n3){
		mesh.addVertex(p3.x, p3.y, p3.z, n3.x, n3.y, normal.x, normal.y, normal.z, 0f, 0f, 1f, 1f);
		mesh.addVertex(p2.x, p2.y, p2.z, n2.x, n2.y, normal.x, normal.y, normal.z, 0f, 1f, 0f, 1f);
		mesh.addVertex(p1.x, p1.y, p1.z, n1.x, n1.y, normal.x, normal.y, normal.z, 1f, 0f, 0f, 1f);
		numTrilange++;
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param normal1
	 * @param normal2
	 * @param normal3
	 * @param n1
	 * @param n2
	 * @param n3
	 */
	protected static void addTrilange(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 normal1, Vector3 normal2, Vector3 normal3, Vector2 n1, Vector2 n2,
									  Vector2 n3){
		mesh.addVertex(p3.x, p3.y, p3.z, n3.x, n3.y, normal3.x, normal3.y, normal3.z, normal3.x, normal3.y, normal3.z, 1f);
		mesh.addVertex(p2.x, p2.y, p2.z, n2.x, n2.y, normal2.x, normal2.y, normal2.z, normal2.x, normal2.y, normal2.z, 1f);
		mesh.addVertex(p1.x, p1.y, p1.z, n1.x, n1.y, normal1.x, normal1.y, normal1.z, normal1.x, normal1.y, normal1.z, 1f);
		numTrilange++;
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param n1
	 * @param n2
	 * @param n3
	 */
	protected static void addTrilangeAutoNormal(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 n1, Vector2 n2, Vector2 n3){

		tmpNormalVec3a.set(p1).nor();
		tmpNormalVec3b.set(p2).nor();
		tmpNormalVec3c.set(p3).nor();
		mesh.addVertex(p3.x, p3.y, p3.z, n3.x, n3.y, tmpNormalVec3a.x, tmpNormalVec3a.y, tmpNormalVec3a.z, tmpNormalVec3a.x, tmpNormalVec3a.y, tmpNormalVec3a.z,
					   1f);
		mesh.addVertex(p2.x, p2.y, p2.z, n2.x, n2.y, tmpNormalVec3b.x, tmpNormalVec3b.y, tmpNormalVec3b.z, tmpNormalVec3b.x, tmpNormalVec3b.y, tmpNormalVec3b.z,
					   1f);
		mesh.addVertex(p1.x, p1.y, p1.z, n1.x, n1.y, tmpNormalVec3a.x, tmpNormalVec3a.y, tmpNormalVec3a.z, tmpNormalVec3a.x, tmpNormalVec3a.y, tmpNormalVec3a.z,
					   1f);
		numTrilange++;
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param n1
	 * @param n2
	 * @param n3
	 */
	protected static void addTrilange(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 n1, Vector2 n2, Vector2 n3){
		//  Plane f1=new Plane(p1, p3, p2);
		Plane f1 = new Plane(p3, p2, p1);
		mesh.addVertex(p3.x, p3.y, p3.z, n3.x, n3.y, f1.normal.x, f1.normal.y, f1.normal.z, f1.normal.x, f1.normal.y, f1.normal.z, 1f);
		mesh.addVertex(p2.x, p2.y, p2.z, n2.x, n2.y, f1.normal.x, f1.normal.y, f1.normal.z, f1.normal.x, f1.normal.y, f1.normal.z, 1f);
		mesh.addVertex(p1.x, p1.y, p1.z, n1.x, n1.y, f1.normal.x, f1.normal.y, f1.normal.z, f1.normal.x, f1.normal.y, f1.normal.z, 1f);
		numTrilange++;
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	protected static void addTrilange(Vertex p1, Vertex p2, Vertex p3){

		Plane f1 = new Plane(p1.position, p2.position, p3.position);
		mesh.addVertex(p3.position.x, p3.position.y, p3.position.z, p3.uv.x, p3.uv.y, f1.normal.x, f1.normal.y, f1.normal.z, f1.normal.x, f1.normal.y,
					   f1.normal.z, 1f);
		mesh.addVertex(p2.position.x, p2.position.y, p2.position.z, p2.uv.x, p2.uv.y, f1.normal.x, f1.normal.y, f1.normal.z, f1.normal.x, f1.normal.y,
					   f1.normal.z, 1f);
		mesh.addVertex(p1.position.x, p1.position.y, p1.position.z, p1.uv.x, p1.uv.y, f1.normal.x, f1.normal.y, f1.normal.z, f1.normal.x, f1.normal.y,
					   f1.normal.z, 1f);
		numTrilange++;
	}
}
