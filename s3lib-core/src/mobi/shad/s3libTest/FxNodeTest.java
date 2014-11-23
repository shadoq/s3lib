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
package mobi.shad.s3libTest;

import com.badlogic.gdx.Gdx;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Log;

/**
 * @author Jarek
 */
public class FxNodeTest extends S3App{

	@Override
	public void initalize(){

		Node fxSingle = new Node("Single0");


		//
		// L4 -> L3 -> L2 -> L1
		//
		Node fxMulti = new Node("Level0");
		fxMulti.addChild(new Node("Level1"))
			   .addChild(new Node("Level2"))
			   .addChild(new Node("Level3"))
			   .addChild(new Node("Level4"));

		//
		// L3A -> L2A -> L1A -> L0 -> Multi
		// L3B -> L2B -> L1B
		//
		Node fxMulti2Pass = new Node("Multi");
		Node level0 = fxMulti2Pass.addChild(new Node("Multi0"));
		Node level1a = level0.addChild(new Node("Level1A"));
		Node level1b = level0.addChild(new Node("Level1B"));
		level1a.addChild(new Node("Level2A")).addChild(new Node("Level3A")).addChild(new Node("Level4A"));
		level1b.addChild(new Node("Level2A")).addChild(new Node("Level3A"));

		//
		// Print Vars
		//
		S3Log.log(">>>>>>>>> ", "---------- Single ------------", 0);
		S3Log.log(">>>>>>>>> ", "Size: " + fxSingle.size(), 1);
		S3Log.log(">>>>>>>>> ", "maxDepth: " + fxSingle.maxDepth(), 1);
		S3Log.log(">>>>>>>>> ", fxSingle.printTree(), 0);
		S3Log.log(">>>>>>>>> ", "------------------------------", 0);

		S3Log.log(">>>>>>>>> ", "---------- Multi ------------", 0);
		S3Log.log(">>>>>>>>> ", "Size: " + fxMulti.size(), 1);
		S3Log.log(">>>>>>>>> ", "maxDepth: " + fxMulti.maxDepth(), 1);
		S3Log.log(">>>>>>>>> ", fxMulti.printTree(), 0);
		S3Log.log(">>>>>>>>> ", "------------------------------", 0);

		S3Log.log(">>>>>>>>> ", "---------- Multi 2 Pass ------------", 0);
		S3Log.log(">>>>>>>>> ", "Size: " + fxMulti2Pass.size(), 1);
		S3Log.log(">>>>>>>>> ", "maxDepth: " + fxMulti2Pass.maxDepth(), 1);
		S3Log.log(">>>>>>>>> ", fxMulti2Pass.printTree(), 0);
		S3Log.log(">>>>>>>>> ", "------------------------------------", 0);

		S3Log.log(">>>>>>>>> ", "------------------------------------", 0);
		S3Log.log(">>>>>>>>> ", "-- Post Order --", 0);
		S3Log.log(">>>>>>>>> ", "------------------------------------", 0);

		//
		// Post Order
		//
		S3Log.log(">>>>>>>>> ", "---------- Single ------------", 0);
		S3Log.log(">>>>>>>>> ", fxSingle.printPostorder(), 0);
		S3Log.log(">>>>>>>>> ", "------------------------------", 0);

		S3Log.log(">>>>>>>>> ", "---------- Multi ------------", 0);
		S3Log.log(">>>>>>>>> ", fxMulti.printPostorder(), 0);
		S3Log.log(">>>>>>>>> ", "------------------------------", 0);

		S3Log.log(">>>>>>>>> ", "---------- Multi 2 Pass ------------", 0);
		S3Log.log(">>>>>>>>> ", fxMulti2Pass.printPostorder(), 0);
		S3Log.log(">>>>>>>>> ", "------------------------------------", 0);

		//
		// Process
		//
		S3Log.log(">>>>>>>>> ", "---------- Single ------------", 0);
		fxSingle.process();
		S3Log.log(">>>>>>>>> ", "------------------------------", 0);

		S3Log.log(">>>>>>>>> ", "---------- Multi ------------", 0);
		fxMulti.process();
		S3Log.log(">>>>>>>>> ", "------------------------------", 0);

		S3Log.log(">>>>>>>>> ", "---------- Multi 2 Pass ------------", 0);
		fxMulti2Pass.process();
		S3Log.log(">>>>>>>>> ", "------------------------------------", 0);

		Gdx.app.exit();
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		g.clear();
	}
}
