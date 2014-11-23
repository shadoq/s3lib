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
public class TorusKnot extends ObjectMeshContainer{

	/**
	 * @param radius
	 * @param tube
	 * @param radialSegments
	 * @param tubularSegments
	 * @param p
	 * @param q
	 * @param heightScale
	 * @return
	 */
	public static ObjectMesh torusKnot(float radius, float tube, int radialSegments, int tubularSegments, float p, float q, float heightScale){

		init();

		tube = tube * 0.1f;

		Vector3[][] grid = new Vector3[radialSegments][tubularSegments];
		Vector3 tang = new Vector3();
		Vector3 n = new Vector3();
		Vector3 bitan = new Vector3();

		float cx = 0;
		float cy = 0;

		for (int i = 0; i < radialSegments; ++i){
			for (int j = 0; j < tubularSegments; ++j){

				float u = (float) ((float) i / (float) radialSegments * 2 * p * Math.PI);
				float v = (float) ((float) j / (float) tubularSegments * 2 * q * Math.PI);

				Vector3 p1 = getTorusKnotVector(u, v, q, p, radius, heightScale);
				Vector3 p2 = getTorusKnotVector(u + 0.01f, v, q, p, radius, heightScale);

				tang.set(p2);
				tang.sub(p1);

				n.set(p2);
				n.add(p1);

				bitan.set(tang);
				bitan.crs(n);
				n.set(bitan);
				n.crs(tang);
				bitan.nor();
				n.nor();

				cx = (float) (-tube * Math.cos(v));
				cy = (float) (tube * Math.sin(v));

				p1.x += cx * n.x + cy * bitan.x;
				p1.y += cx * n.y + cy * bitan.y;
				p1.z += cx * n.z + cy * bitan.z;

				grid[i][j] = p1;
			}
		}

		for (int i = 0; i < radialSegments; ++i){
			for (int j = 0; j < tubularSegments; ++j){
				int ip = (i + 1) % radialSegments;
				int jp = (j + 1) % tubularSegments;

				Vector3 a = grid[i][j];
				Vector3 b = grid[ip][j];
				Vector3 c = grid[ip][jp];
				Vector3 d = grid[i][jp];

				Vector2 uva = new Vector2((float) i / (float) radialSegments, (float) j / (float) tubularSegments);
				Vector2 uvb = new Vector2((float) (i + 1) / (float) radialSegments, (float) j / (float) tubularSegments);
				Vector2 uvc = new Vector2((float) (i + 1) / (float) radialSegments, (float) (j + 1) / (float) tubularSegments);
				Vector2 uvd = new Vector2((float) i / (float) radialSegments, (float) (j + 1) / (float) tubularSegments);

				mesh.addVertex(a, uva);
				mesh.addVertex(b, uvb);
				mesh.addVertex(c, uvc);
				mesh.addVertex(d, uvd);

				//				// What wrong ?
				//				mesh.addVertex(a, uva);
				//				mesh.addVertex(b, uvb);
				//				mesh.addVertex(c, uvc);
				//				mesh.addVertex(d, uvd);

				mesh.indicesBuffer.ensureCapacity(6);
				mesh.indicesBuffer.add((short) (mesh.vertexIndex - 4));
				mesh.indicesBuffer.add((short) (mesh.vertexIndex - 3));
				mesh.indicesBuffer.add((short) (mesh.vertexIndex - 2));

				mesh.indicesBuffer.add((short) (mesh.vertexIndex - 2));
				mesh.indicesBuffer.add((short) (mesh.vertexIndex - 1));
				mesh.indicesBuffer.add((short) (mesh.vertexIndex - 4));
			}
		}

		return mesh;
	}

	/**
	 * @param u
	 * @param v
	 * @param in_q
	 * @param in_p
	 * @param radius
	 * @param heightScale
	 * @return
	 */
	private static Vector3 getTorusKnotVector(float u, float v, float in_q, float in_p, float radius, float heightScale){
		float cu = (float) Math.cos(u);
		float cv = (float) Math.cos(v);
		float su = (float) Math.sin(u);
		float quOverP = in_q / in_p * u;
		float cs = (float) Math.cos(quOverP);

		float tx = (float) (radius * (2 + cs) * 0.5 * cu);
		float ty = (float) (radius * (2 + cs) * su * 0.5);
		float tz = (float) (heightScale * radius * Math.sin(quOverP) * 0.5);

		return new Vector3(tx, ty, tz);
	}
}
