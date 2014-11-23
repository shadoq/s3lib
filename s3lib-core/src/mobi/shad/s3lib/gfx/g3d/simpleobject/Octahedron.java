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

/**
 * @author Jarek
 */
public class Octahedron extends ObjectPolyhedron{

	/**
	 * @param radius
	 * @return
	 */
	public static ObjectMesh octahedron(float radius){

		Vector3[] vertices = {
		new Vector3(1f, 0f, 0f),
		new Vector3(-1f, 0f, 0f),
		new Vector3(0f, 1f, 0f),
		new Vector3(0f, -1f, 0f),
		new Vector3(0f, 0f, 1f),
		new Vector3(0f, 0f, -1f),};

		int[][] faces = {
		{0, 2, 4},
		{0, 4, 3},
		{0, 3, 5},
		{0, 5, 2},
		{1, 2, 5},
		{1, 5, 3},
		{1, 3, 4},
		{1, 4, 2}
		};

		return polyhedron(vertices, faces, radius, 0);
	}
}
