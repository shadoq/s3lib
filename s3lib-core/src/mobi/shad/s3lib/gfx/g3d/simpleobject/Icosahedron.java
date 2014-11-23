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
public class Icosahedron extends ObjectPolyhedron{

	public static ObjectMesh icosahedron(float radius){

		float t = (float) ((1 + Math.sqrt(5)) / 2);

		Vector3[] vertices = {
		new Vector3(-1f, t, 0f),
		new Vector3(1f, t, 0f),
		new Vector3(-1f, -t, 0f),
		new Vector3(1f, -t, 0f),
		new Vector3(0f, -1f, t),
		new Vector3(0f, 1f, t),
		new Vector3(0f, -1f, -t),
		new Vector3(0f, 1f, -t),
		new Vector3(t, 0f, -1f),
		new Vector3(t, 0f, 1f),
		new Vector3(-t, 0f, -1f),
		new Vector3(-t, 0f, 1f),};

		int[][] faces = {
		{0, 11, 5}, {0, 5, 1}, {0, 1, 7}, {0, 7, 10}, {0, 10, 11},
		{1, 5, 9}, {5, 11, 4}, {11, 10, 2}, {10, 7, 6}, {7, 1, 8},
		{3, 9, 4}, {3, 4, 2}, {3, 2, 6}, {3, 6, 8}, {3, 8, 9},
		{4, 9, 5}, {2, 4, 11}, {6, 2, 10}, {8, 6, 7}, {9, 8, 1}
		};


		return polyhedron(vertices, faces, radius, 0);
	}
}
