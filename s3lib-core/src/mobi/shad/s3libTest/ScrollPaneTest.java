package mobi.shad.s3libTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Skin;

public class ScrollPaneTest extends S3App{

	private Table container;

	@Override
	public void initalize(){

		container = new Table();
		S3.stage.addActor(container);
		container.setFillParent(true);

		Table table = new Table();
		table.debug();

		final ScrollPane scroll = new ScrollPane(table, S3Skin.skin);

		InputListener stopTouchDown = new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		};

		table.pad(10).defaults().expandX().space(4);
		for (int i = 0; i < 100; i++){
			table.row();
			table.add(new Label(i + "uno", S3Skin.skin)).expandX().fillX();

			TextButton button = new TextButton(i + "dos", S3Skin.skin);
			table.add(button);
			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					System.out.println("click " + x + ", " + y);
				}
			});

			Slider slider = new Slider(0, 100, 1, false, S3Skin.skin);
			slider.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
			table.add(slider);

			table.add(new Label(
			i + "tres long0 long1 long2 long3 long4 long5 long6 long7 long8 long9 long10 long11 long12 long13 long14 long15 long16 long17 long10 long11 long12 long13 long14 long15 long16 long17",
			S3Skin.skin));
		}

		final TextButton flickButton = new TextButton("Flick Scroll", S3Skin.skin.get("toggle", TextButton.TextButtonStyle.class));
		flickButton.setChecked(true);
		flickButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setFlickScroll(flickButton.isChecked());
			}
		});

		final TextButton fadeButton = new TextButton("Fade Scrollbars", S3Skin.skin.get("toggle", TextButton.TextButtonStyle.class));
		fadeButton.setChecked(true);
		fadeButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setFadeScrollBars(fadeButton.isChecked());
			}
		});

		final TextButton smoothButton = new TextButton("Smooth Scrolling", S3Skin.skin.get("toggle", TextButton.TextButtonStyle.class));
		smoothButton.setChecked(true);
		smoothButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setSmoothScrolling(smoothButton.isChecked());
			}
		});

		final TextButton onTopButton = new TextButton("Scrollbars On Top", S3Skin.skin.get("toggle", TextButton.TextButtonStyle.class));
		onTopButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				scroll.setScrollbarsOnTop(onTopButton.isChecked());
			}
		});

		container.add(scroll).expand().fill().colspan(4);
		container.row().space(10).padBottom(10);
		container.add(flickButton).right().expandX();
		container.add(onTopButton);
		container.add(smoothButton);
		container.add(fadeButton).left().expandX();
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		Gdx.gl.glClearColor(0.2f, 0.5f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
