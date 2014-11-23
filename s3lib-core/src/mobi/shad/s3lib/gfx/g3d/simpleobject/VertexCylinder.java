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
import mobi.shad.s3lib.main.S3Mesh;

import java.util.ArrayList;

/**
 * @author Jarek
 */
public class VertexCylinder extends Object3d{

	/**
	 * @return
	 */
	public static S3Mesh cylinder(){
		return cylinder(2, 4, 10, 5);
	}

	/**
	 * @param radius
	 * @param height
	 * @return
	 */
	public static S3Mesh cylinder(float radius, float height){
		return cylinder(radius, height, 10, 5);
	}

	/**
	 * @param radius
	 * @param height
	 * @param segmentsWidth
	 * @param segmentsHeight
	 * @return
	 */
	public static S3Mesh cylinder(float radius, float height, float segmentsWidth, float segmentsHeight){

		S3Log.log("Model3DPrimitive::cylinder",
				  "radius=" + radius + "height=" + height + "segmentsWidth=" + segmentsWidth + " segmentsHeight=" + segmentsHeight);
		init();

		radius = radius * .5f;
		float segmentsX = (float) Math.max(3, Math.floor(segmentsWidth));
		float segmentsY = (float) Math.max(3, Math.floor(segmentsHeight));

		float phiStart = 0;
		float phiLength = (float) (Math.PI * 2);

		float thetaStart = 0;
		float thetaLength_2 = (float) Math.PI / 2;
		float thetaLength = (float) Math.PI;

		int x, y, tempX, tempY;

		float u, v, xp, yp, zp, vt, ut;

		ArrayList<Vector3> vertices = new ArrayList<Vector3>();
		ArrayList<Vector2> uvs = new ArrayList<Vector2>();

		for (y = 0; y <= segmentsY; y++){
			for (x = 0; x <= segmentsX; x++){

				u = x / segmentsX;
				v = y / segmentsY;

				if (y == 0){
					u = 0.0f;
					v = 0.0f;
					xp = 0;
					yp = (float) (radius);
					zp = 0;
				} else if (y == 1){
					xp = (float) (-radius * Math.cos(phiStart + u * phiLength) * Math.sin(thetaStart + thetaLength_2));
					yp = (float) (radius);
					zp = (float) (radius * Math.sin(phiStart + u * phiLength) * Math.sin(thetaStart + thetaLength_2));
				} else if (y == segmentsY - 1){
					xp = (float) (-radius * Math.cos(phiStart + u * phiLength) * Math.sin(thetaStart + thetaLength_2));
					yp = (float) -(radius);
					zp = (float) (radius * Math.sin(phiStart + u * phiLength) * Math.sin(thetaStart + thetaLength_2));
				} else if (y == segmentsY){
					u = 1;
					v = 1;
					xp = 0;
					yp = (float) -(radius);
					zp = 0;
				} else {
					xp = (float) (-radius * Math.cos(phiStart + u * phiLength) * Math.sin(thetaStart + thetaLength_2));
					yp = (float) (radius * Math.cos(thetaStart + v * thetaLength));
					zp = (float) (radius * Math.sin(phiStart + u * phiLength) * Math.sin(thetaStart + thetaLength_2));
				}

				vertices.add(new Vector3(xp, yp, zp));
				uvs.add(new Vector2(u, v));
			}
		}

		for (y = 0; y <= segmentsY; y++){

			for (x = 0; x < segmentsX; x++){

				try {
					Vector3 vt1 = vertices.get((int) (y * segmentsX + x + 0));
					Vector3 vt2 = vertices.get((int) (y * segmentsX + x + 1));
					Vector3 vt3 = vertices.get((int) ((y + 1) * segmentsX + x + 1));
					Vector3 vt4 = vertices.get((int) ((y + 1) * segmentsX + x + 0));

					Vector2 uv1 = uvs.get((int) (y * segmentsX + x + 0));
					Vector2 uv2 = uvs.get((int) (y * segmentsX + x + 1));
					Vector2 uv3 = uvs.get((int) ((y + 1) * segmentsX + x + 1));
					Vector2 uv4 = uvs.get((int) ((y + 1) * segmentsX + x + 0));

					addTrilange(vt1, vt2, vt3, uv1, uv2, uv3);
					addTrilange(vt1, vt3, vt4, uv1, uv3, uv4);
				} catch (Exception ex){
				}
			}
		}

		return dataToMesh();
	}
}
