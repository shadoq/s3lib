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

import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Mesh;

/**
 * @author Jarek
 */
public class VertexPlane extends Object3d{

	/**
	 * @return
	 */
	public static S3Mesh plane(){
		return plane(4.0f, 4.0f, 1, 1, 0, 0);
	}

	/**
	 * @param w
	 * @param h
	 * @return
	 */
	public static S3Mesh plane(float w, float h){
		return plane(w, h, 1, 1, 0, 0);
	}

	/**
	 * @param w
	 * @param h
	 * @param wd
	 * @param hd
	 * @param wo
	 * @param ho
	 * @return
	 */
	public static S3Mesh plane(float w, float h, float wd, float hd, float wo, float ho){

		S3Log.log("Model3DPrimitive::plane", "w=" + w + " h=" + h + " wd=" + wd + " hd=" + hd);

		init();

		w = w * 0.5f;
		h = h * 0.5f;

		float wStart = -w + wo;
		float wEnd = w + wo;
		float hStart = h + ho;
		float hEnd = -h + ho;
		float uStart = 0;
		float uEnd = 1;
		float vStart = 1;
		float vEnd = 0;

		float wb = ((w * 2) / wd);
		float hb = ((h * 2) / hd);

		for (float i = 0; i < wd; i++){
			for (float j = 0; j < hd; j++){

				float bvStart = wStart + i * wb;
				float bvEnd = bvStart + wb;
				float bhStart = hStart - j * hb;
				float bhEnd = bhStart - hb;

				Vector3 va = new Vector3(bvStart, bhStart, 0);
				Vector3 vb = new Vector3(bvEnd, bhStart, 0);
				Vector3 vc = new Vector3(bvEnd, bhEnd, 0);
				Vector3 vd = new Vector3(bvStart, bhEnd, 0);

				float us = 1 / wd * i;
				float ue = 1 / wd * (i + 1);
				float vs = 1 - (1 / hd * (j + 1));
				float ve = 1 - (1 / hd * j);

				addQuad(va, vb, vc, vd, us, ue, vs, ve);
			}
		}

		return dataToMesh();
	}
}
