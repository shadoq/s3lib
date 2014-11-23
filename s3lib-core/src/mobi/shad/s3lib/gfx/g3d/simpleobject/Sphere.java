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
import mobi.shad.s3lib.main.S3Log;

import java.util.ArrayList;

/**
 * @author Jarek
 */
public class Sphere extends ObjectMeshContainer{

	/**
	 * @return
	 */
	public static ObjectMesh sphere(){
		return sphere(3f, 3f, 3f);
	}

	/**
	 * @param radius
	 * @return
	 */
	public static ObjectMesh sphere(float radius){
		return sphere(radius, radius, radius, 20, 20);
	}

	/**
	 * @param radiusX
	 * @param radiusY
	 * @param radiusZ
	 * @return
	 */
	public static ObjectMesh sphere(float radiusX, float radiusY, float radiusZ){
		return sphere(radiusX, radiusY, radiusZ, 20, 20);
	}

	/**
	 * @param radiusX
	 * @param radiusY
	 * @param radiusZ
	 * @param segmentsWidth
	 * @param segmentsHeight
	 * @return
	 */
	public static ObjectMesh sphere(float radiusX, float radiusY, float radiusZ, float segmentsWidth, float segmentsHeight){

		init();

		float segmentsX = (float) Math.max(3, Math.floor(segmentsWidth));
		float segmentsY = (float) Math.max(3, Math.floor(segmentsHeight));

		S3Log.log("Model3DPrimitive::sphere",
				  "radiusX=" + radiusX + "radiusX=" + radiusY + "radiusX=" + radiusZ + " segmentsWidth=" + segmentsX + " segmentsHeight=" + segmentsY);

		float phiStart = 0;
		float phiLength = (float) (Math.PI * 2);

		float thetaStart = 0;
		float thetaLength = (float) Math.PI;

		int x, y;

		ArrayList<Vector3> vertices = new ArrayList<Vector3>();
		ArrayList<Vector2> uvs = new ArrayList<Vector2>();

		for (y = 0; y <= segmentsY; y++){

			for (x = 0; x <= segmentsX; x++){

				float u = x / segmentsX;
				float v = y / segmentsY;

				float xp = (float) (-radiusX * Math.cos(phiStart + u * phiLength) * Math.sin(thetaStart + v * thetaLength));
				float yp = (float) (radiusY * Math.cos(thetaStart + v * thetaLength));
				float zp = (float) (radiusZ * Math.sin(phiStart + u * phiLength) * Math.sin(thetaStart + v * thetaLength));

				vertices.add(new Vector3(xp, yp, zp));
				uvs.add(new Vector2(u, v));
			}
		}

		Vector3 vt1 = new Vector3();
		Vector3 vt2 = new Vector3();
		Vector3 vt3 = new Vector3();
		Vector3 vt4 = new Vector3();
		Vector2 uv1 = new Vector2();
		Vector2 uv2 = new Vector2();
		Vector2 uv3 = new Vector2();
		Vector2 uv4 = new Vector2();

		for (y = 0; y <= segmentsY; y++){

			for (x = 0; x < segmentsX; x++){
				try {
					vt1 = vertices.get((int) (y * segmentsX + x + 0));
					vt2 = vertices.get((int) (y * segmentsX + x + 1));
					vt3 = vertices.get((int) ((y + 1) * segmentsX + x + 1));
					vt4 = vertices.get((int) ((y + 1) * segmentsX + x + 0));

					uv1 = uvs.get((int) (y * segmentsX + x + 0));
					uv2 = uvs.get((int) (y * segmentsX + x + 1));
					uv3 = uvs.get((int) ((y + 1) * segmentsX + x + 1));
					uv4 = uvs.get((int) ((y + 1) * segmentsX + x + 0));

					mesh.addVertex(vt1, uv1);
					mesh.addVertex(vt2, uv2);
					mesh.addVertex(vt3, uv3);
					mesh.addVertex(vt4, uv4);

					mesh.indicesBuffer.ensureCapacity(6);
					mesh.indicesBuffer.add((short) (mesh.vertexIndex - 2));
					mesh.indicesBuffer.add((short) (mesh.vertexIndex - 3));
					mesh.indicesBuffer.add((short) (mesh.vertexIndex - 4));

					mesh.indicesBuffer.add((short) (mesh.vertexIndex - 4));
					mesh.indicesBuffer.add((short) (mesh.vertexIndex - 1));
					mesh.indicesBuffer.add((short) (mesh.vertexIndex - 2));
				} catch (Exception ex){
				}
			}
		}

		return dataToMesh();
	}
}
