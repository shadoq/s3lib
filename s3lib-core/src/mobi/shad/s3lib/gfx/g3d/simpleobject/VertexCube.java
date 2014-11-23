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
public class VertexCube extends Object3d{

	/**
	 * @return
	 */
	public static S3Mesh cube(){
		return cube(4.0f, 4.0f, 4.0f);
	}

	/**
	 * @param value
	 * @return
	 */
	public static S3Mesh cube(float value){
		return cube(value, value, value);
	}

	/**
	 * @param w
	 * @param h
	 * @param d
	 * @return
	 */
	public static S3Mesh cube(float w, float h, float d){

		init();

		w = w * 0.5f;
		h = h * 0.5f;
		d = d * 0.5f;

		S3Log.log("Model3DPrimitive::cube", "w=" + w + " h=" + h + " d=" + d);

		addQuad(new Vector3(-w, h, d), new Vector3(w, h, d), new Vector3(w, -h, d), new Vector3(-w, -h, d));
		addQuad(new Vector3(w, h, -d), new Vector3(-w, h, -d), new Vector3(-w, -h, -d), new Vector3(w, -h, -d));

		addQuad(new Vector3(-w, h, -d), new Vector3(-w, h, d), new Vector3(-w, -h, d), new Vector3(-w, -h, -d));
		addQuad(new Vector3(w, h, d), new Vector3(w, h, -d), new Vector3(w, -h, -d), new Vector3(w, -h, d));

		addQuad(new Vector3(w, h, d), new Vector3(-w, h, d), new Vector3(-w, h, -d), new Vector3(w, h, -d));
		addQuad(new Vector3(w, -h, d), new Vector3(w, -h, -d), new Vector3(-w, -h, -d), new Vector3(-w, -h, d));

		return dataToMesh();
	}
}
