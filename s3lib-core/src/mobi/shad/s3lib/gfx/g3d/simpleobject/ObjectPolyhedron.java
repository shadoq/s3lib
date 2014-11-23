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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Jarek
 */
public class ObjectPolyhedron extends ObjectMeshContainer{

	private static Vector3 tmpVec3a = new Vector3();
	private static Vector3 tmpVec3b = new Vector3();
	private static Vector3 tmpVec3c = new Vector3();
	private static Vector3 tmpVec3d = new Vector3();

	private static Vector3 tmpNormalVec3a = new Vector3();
	private static Vector3 tmpNormalVec3b = new Vector3();
	private static Vector3 tmpNormalVec3c = new Vector3();
	private static Vector3 tmpNormalVec3d = new Vector3();

	/**
	 * @param vertices
	 * @param faces
	 * @param radius
	 * @param detail
	 * @return
	 */
	protected static ObjectMesh polyhedron(Vector3[] vertices, int[][] faces, float radius, int detail){

		init();
		mesh.useIndicesIndex = false;

		int l = vertices.length;
		Vertex[] vertex = new Vertex[l];
		for (int i = 0; i < l; i++){
			vertex[i] = polyhedronPrepare(vertices[i]);
			vertex[i].position.scl(radius);
		}

		l = faces.length;
		for (int i = 0; i < l; i++){

			polyhedronMake(vertex[faces[i][0]], vertex[faces[i][1]], vertex[faces[i][2]], detail);
		}

		return mesh;
	}

	/**
	 * @param vector
	 * @return
	 */
	protected static Vertex polyhedronPrepare(Vector3 vector){

		Vertex vertex = new Vertex();
		vertex.position = vector.nor().cpy();

		float u = (float) (polyhedronAzimuth(vector) / 2 / Math.PI + 0.5);
		float v = (float) (polyhedronInclination(vector) / Math.PI + 0.5);
		vertex.uv = new Vector2(u, 1 - v);

		return vertex;
	}

	/**
	 * Angle around the Y axis, counter-clockwise when looking from above.
	 *
	 * @param vector
	 * @return
	 */
	protected static float polyhedronAzimuth(Vector3 vector){
		return (float) Math.atan2(vector.z, -vector.x);
	}

	/**
	 * Angle above the XZ plane.
	 *
	 * @param vector
	 * @return
	 */
	protected static float polyhedronInclination(Vector3 vector){
		return (float) Math.atan2(-vector.y, Math.sqrt((vector.x * vector.x) + (vector.z * vector.z)));
	}

	/**
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param detail
	 */
	protected static void polyhedronMake(Vertex v1, Vertex v2, Vertex v3, int detail){
		if (detail < 1){

			com.badlogic.gdx.math.Plane p1 = new com.badlogic.gdx.math.Plane(v1.position, v2.position, v3.position);
			v1.normal = p1.getNormal();
			v2.normal = p1.getNormal();
			v3.normal = p1.getNormal();

			Vector3 azimut = new Vector3(v1.position);
			azimut.add(v2.position).add(v3.position).scl(1f / 3f);
			float azi = polyhedronAzimuth(azimut);

			v1.uv = polyhedronCorrectUV(v1.uv, v1.position, azi);
			v2.uv = polyhedronCorrectUV(v2.uv, v2.position, azi);
			v3.uv = polyhedronCorrectUV(v3.uv, v3.position, azi);

			mesh.addTrilange(v2, v3, v1);
		} else {

			detail--;

			//
			// top quadrant
			//
			polyhedronMake(v1, polyhedronMidpoint(v1, v2), polyhedronMidpoint(v1, v3), detail);

			//
			// left quadrant
			//
			polyhedronMake(polyhedronMidpoint(v1, v2), v2, polyhedronMidpoint(v2, v3), detail);

			//
			// right quadrant
			//
			polyhedronMake(polyhedronMidpoint(v1, v3), polyhedronMidpoint(v2, v3), v3, detail);

			//
			// center quadrant
			//
			polyhedronMake(polyhedronMidpoint(v1, v3), polyhedronMidpoint(v2, v3), polyhedronMidpoint(v1, v3), detail);
		}
	}

	/**
	 * @param uv
	 * @param vector
	 * @param azimuth
	 * @return
	 */
	protected static Vector2 polyhedronCorrectUV(Vector2 uv, Vector3 vector, float azimuth){

		if ((azimuth < 0) && (uv.x == 1)){
			uv = new Vector2(uv.x - 1, uv.y);
		}
		if ((vector.x == 0) && (vector.z == 0)){
			uv = new Vector2((float) (azimuth / 2 / Math.PI + 0.5), uv.y);
		}
		return uv;

	}

	/**
	 * @param v1
	 * @param v2
	 * @return
	 */
	protected static Vertex polyhedronMidpoint(Vertex v1, Vertex v2){
		Vector3 m1 = v1.position;
		m1.add(v2.position).scl(1f / 2f);
		Vertex mid = polyhedronPrepare(m1);
		mid.position = m1;
		return mid;
	}
}
