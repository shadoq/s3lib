package mobi.shad.s3libTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3ResourceManager;

public class G3DNewBatchRenderTest extends S3App{

	int TEXTURE_COUNT = 30;
	int BOX_COUNT = 500;
	int UNIT_OFFSET = 2;
	int MAX_TEXTURES = Math.min(16 /*GL10.GL_MAX_TEXTURE_UNITS*/ - UNIT_OFFSET, DefaultTextureBinder.MAX_GLES_UNITS - UNIT_OFFSET);
	int BIND_METHOD = DefaultTextureBinder.WEIGHTED;
	float MIN_X = -10f, MIN_Y = -10f, MIN_Z = -10f;
	float SIZE_X = 20f, SIZE_Y = 20f, SIZE_Z = 20f;
	PerspectiveCamera cam;
	Array<ModelInstance> instances = new Array<ModelInstance>();
	Model sphereModel;
	Model sceneModel;
	Model cubeModel;
	Model carModel;
	Model testModel;
	Array<Model> cubes = new Array<Model>();
	Array<Texture> textures = new Array<Texture>();
	ModelBatch renderBatch;
	DefaultTextureBinder exclusiveTextures;
	Environment lights;
	float touchStartX = 0;
	float touchStartY = 0;
	float dbgTimer = 0f;
	boolean test = false;
	private DirectionalLight light1;
	private float angleY;
	private PointLight light2;
	private PointLight light3;
	private ModelBatch modelBatch;

	@Override
	public void initalize(){

		// need more higher resolution textures for this test...
		String[] TEXTURES = {"g3d/badlogic.jpg", "g3d/egg.png", "g3d/particle-fire.png", "g3d/planet_earth.png", "g3d/planet_heavyclouds.jpg",
							 "g3d/resource1.jpg", "g3d/stones.jpg", "g3d/sys.png", "g3d/wheel.png"};

		for (int i = 0; i < TEXTURE_COUNT; i++){
			textures.add(S3ResourceManager.getTexture(TEXTURES[i % TEXTURES.length], 512));
		}

		ObjLoader objLoader = new ObjLoader();
		sphereModel = objLoader.loadObj(S3File.getFileHandle("g3d/sphere.obj"));
		sceneModel = objLoader.loadObj(S3File.getFileHandle("g3d/scene.obj"));
		cubeModel = objLoader.loadObj(S3File.getFileHandle("g3d/cube.obj"));
		carModel = objLoader.loadObj(S3File.getFileHandle("g3d/car.obj"));

		final G3dModelLoader loader = new G3dModelLoader(new JsonReader());
		testModel = loader.loadModel(S3File.getFileHandle("g3d/head.g3dj"));

		//StillSubMesh mesh = (StillSubMesh)(cubeModel.subMeshes[0]);
		for (int i = 0; i < textures.size; i++){
			cubes.add(cubeModel);
		}

		createScene2();

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = 1f;
		cam.far = 100f;
		cam.position.set(0f, 10f, -15f);
		cam.lookAt(0, 0, 0);
		cam.update();

		renderBatch = new ModelBatch();

		light1 = new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.0f, -0.0f);
		light2 = new PointLight().set(0.9f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 10);
		light3 = new PointLight().set(0.0f, 0.0f, 1.0f, 0.0f, 5.0f, 0.0f, 10);

		modelBatch = new ModelBatch();
		lights = new Environment();
		lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.0f, 0.0f, 0.0f, 1.f));
		lights.set(new ColorAttribute(ColorAttribute.Fog, 0.13f, 0.13f, 0.13f, 1f));
		lights.add(light1);
		lights.add(light2);
		lights.add(light3);
	}

	public void createScene1(){
		for (int i = 0; i < BOX_COUNT; i++){
			instances.add(new ModelInstance(cubes.get((int) (Math.random() * cubes.size)),
											(new Matrix4()).setToTranslation(MIN_X + (float) Math.random() * SIZE_X, MIN_Y + (float) Math.random() * SIZE_Y,
																			 MIN_Z + (float) Math.random() * SIZE_Z).scl(0.05f + (float) Math.random())));
		}
	}

	public void createScene2(){
		instances.add(new ModelInstance(sceneModel, new Matrix4()));
		instances.add(new ModelInstance(testModel, (new Matrix4()).setToTranslation(0, 5, 4)));
		instances.add(new ModelInstance(carModel, (new Matrix4()).setToTranslation(6, 0, -4)));

		for (int i = 0; i < 10; i++){
			instances.add(new ModelInstance(sphereModel,
											(new Matrix4()).setToTranslation(MIN_X + (float) Math.random() * SIZE_X, MIN_Y + (float) Math.random() * SIZE_Y,
																			 MIN_Z + (float) Math.random() * SIZE_Z).scl(0.25f + (float) Math.random())));
		}
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){

		final float delta = Gdx.graphics.getDeltaTime();
		angleY = angleY + delta;
		light1.direction.rotate(Vector3.Y, delta * 500);
		light2.intensity = (float) (Math.sin(angleY) * 10) + 10;
		light3.position.x = (float) (Math.sin(angleY * 3) * 4);
		light3.position.z = (float) (Math.cos(angleY * 2) * 4);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);

		renderBatch.begin(cam);
		for (int i = 0; i < instances.size; i++){
			renderBatch.render(instances.get(i), lights);
		}
		renderBatch.end();
	}

	//	@Override
	//	public boolean on (int x, int y, int pointer, int newParam){
	//		touchStartX=x;
	//		touchStartY=y;
	//		return false;
	//	}
	//
	//	@Override
	//	public boolean touchDragged(int x, int y, int pointer){
	//		cam.rotateAround(Vector3.Zero, Vector3.X, (x - touchStartX));
	//		cam.rotateAround(Vector3.Zero, Vector3.Y, (y - touchStartY));
	//		touchStartX=x;
	//		touchStartY=y;
	//		cam.update();
	//		return false;
	//	}
	//
	//	@Override
	//	public boolean scrolled(int amount){
	//		cam.fieldOfView-=-amount * Gdx.graphics.getDeltaTime() * 100;
	//		cam.update();
	//		return false;
	//	}
}
