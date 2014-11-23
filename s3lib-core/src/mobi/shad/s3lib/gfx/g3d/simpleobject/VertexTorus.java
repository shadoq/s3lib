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

import mobi.shad.s3lib.main.S3Mesh;

/**
 * @author Jarek
 */
public class VertexTorus extends Object3d{

	/**
	 * @param largeRadius
	 * @param smallRadius
	 * @param segmentsW
	 * @param segmentsH
	 * @return
	 */
	public static S3Mesh torus(float largeRadius, float smallRadius, float segmentsW, float segmentsH){

		init();

		float r1 = largeRadius * 0.5f;
		float r2 = smallRadius * 0.5f;
		int steps1 = (int) segmentsW;
		int steps2 = (int) segmentsH;
		float step1r = (float) ((2.0 * Math.PI) / steps1);
		float step2r = (float) ((2.0 * Math.PI) / steps2);
		float a1a = 0;
		float a1b = step1r;

		for (float s = 0; s < steps1; s++, a1a = a1b, a1b += step1r){
			float a2a = 0;
			float a2b = step2r;

			for (float s2 = 0; s2 < steps2; s2++, a2a = a2b, a2b += step2r){

				Vertex v0 = getTorusVertex(a1a, r1, a2a, r2);
				Vertex v1 = getTorusVertex(a1b, r1, a2a, r2);
				Vertex v2 = getTorusVertex(a1b, r1, a2b, r2);
				Vertex v3 = getTorusVertex(a1a, r1, a2b, r2);

				float ux1 = s / steps1;
				float ux0 = (s + 1) / steps1;
				float uy0 = s2 / steps2;
				float uy1 = (s2 + 1) / steps2;

				v0.uv.set(1 - ux1, uy0);
				v1.uv.set(1 - ux0, uy0);
				v2.uv.set(1 - ux0, uy1);
				v3.uv.set(1 - ux1, uy1);

				addTrilange(v0, v1, v2);
				addTrilange(v0, v2, v3);
			}
		}

		return dataToMesh();
	}

	/**
	 * @param a1
	 * @param r1
	 * @param a2
	 * @param r2
	 * @return
	 */
	private static Vertex getTorusVertex(float a1, float r1, float a2, float r2){

		Vertex vertex = new Vertex();

		float ca1 = (float) Math.cos(a1);
		float sa1 = (float) Math.sin(a1);
		float ca2 = (float) Math.cos(a2);
		float sa2 = (float) Math.sin(a2);

		float centerX = r1 * ca1;
		float centerZ = -r1 * sa1;

		vertex.normal.x = ca2 * ca1;
		vertex.normal.y = sa2;
		vertex.normal.z = -ca2 * sa1;

		vertex.position.x = centerX + r2 * vertex.normal.x;
		vertex.position.y = r2 * vertex.normal.y;
		vertex.position.z = centerZ + r2 * vertex.normal.z;

		return vertex;
	}
}
