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
import mobi.shad.s3lib.main.S3Log;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author Jarek
 */
public class DebugObject{

	public static void debugMesh(Mesh mesh){

		int numVertices = mesh.getNumVertices();
		int numIndices = mesh.getNumIndices();
		ShortBuffer indicesBuffer = mesh.getIndicesBuffer();
		FloatBuffer verticesBuffer = mesh.getVerticesBuffer();

		S3Log.log("debugMesh", "NumVertices: " + numVertices);
		S3Log.log("debugMesh", "NumIndices: " + numIndices);
		S3Log.log("debugMesh", "vertexSize (bytes): " + mesh.getVertexSize());

		S3Log.log("debugMesh", "Indices Buffer:", 1);
		StringBuilder buffer = new StringBuilder(32);
		buffer.append('[');
		buffer.append(indicesBuffer.get(0));
		for (int i = 1; i < numIndices; i++){
			buffer.append(", ");
			buffer.append(indicesBuffer.get(i));
		}
		buffer.append(']');
		S3Log.log("debugMesh", buffer.toString());

		S3Log.log("debugMesh", "Vertices Buffer:", 1);
		StringBuilder buffer2 = new StringBuilder(32);
		buffer2.append('[');
		buffer2.append(verticesBuffer.get(0));
		for (int i = 1; i < numIndices; i++){
			buffer2.append(", ");
			buffer2.append(verticesBuffer.get(i));
		}
		buffer.append(']');
		S3Log.log("debugMesh", buffer2.toString());
	}
}
