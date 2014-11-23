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

/**
 * @author Jarek
 */
public class ObjectMeshContainer{
	protected static ObjectMesh mesh;

	/**
	 *
	 */
	protected static void init(){
		mesh = new ObjectMesh(true, false, true);
	}

	/**
	 * @return
	 */
	protected static ObjectMesh dataToMesh(){
		return mesh;
	}

	/**
	 * @return
	 */
	public static Mesh getMesh(){
		return mesh.getMesh();
	}
}
