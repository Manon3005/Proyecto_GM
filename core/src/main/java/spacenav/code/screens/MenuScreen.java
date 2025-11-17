package spacenav.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import spacenav.code.SpaceNavigation;
import spacenav.code.utils.AssetLoader;


public class MenuScreen implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private BitmapFont defaultFont;
	private BitmapFont titleFont;
	private Stage stage;
	private Skin skin;
	

	public MenuScreen(SpaceNavigation game) {
		this.game = game;
		
		AssetLoader assets = AssetLoader.getInstance();
		defaultFont = assets.get(AssetLoader.DEFAULT_FONT, BitmapFont.class);
		titleFont = assets.get(AssetLoader.TITLE_FONT, BitmapFont.class);
		skin = assets.get(AssetLoader.BUTTON_SKIN, Skin.class);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
		stage = new Stage();
	}

	@Override
	public void render(float delta) {
		 ScreenUtils.clear(0, 0, 0.2f, 1);

	    camera.update();
	    game.getBatch().setProjectionMatrix(camera.combined);

	    game.getBatch().begin();
	    GlyphLayout layout = new GlyphLayout();

	    layout.setText(titleFont, "SPACE NAVIGATION");
	    float xTitle = (1200 - layout.width) / 2f;
	    float yTitle = 700;
	    titleFont.draw(game.getBatch(), layout, xTitle, yTitle);

	    layout.setText(defaultFont, "Haz clic en el boton para comenzar una partida!");
	    float xSub = (1200 - layout.width) / 2f;
	    float ySub = 300;

	    defaultFont.draw(game.getBatch(), layout, xSub, ySub);
	    game.getBatch().end();

	    stage.act(delta);
	    stage.draw();
}
	
	
	@Override
	public void show() {
	    Gdx.input.setInputProcessor(stage);
	    
	    TextButton playButton = new TextButton("Jugar", skin);
	    playButton.setSize(400, 120);
	    playButton.setPosition(
	    	   stage.getWidth() / 2f - playButton.getWidth() / 2f,
	    	   stage.getHeight() / 2f - playButton.getHeight() / 2f
	    );
	    
	    playButton.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	            Screen ss = new GameScreen(game,1,3,0,1,1,10,3f);
	            ss.resize(1200, 800);
	            game.setScreen(ss);
	            dispose();
	        }
	    });
	    stage.addActor(playButton);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	    skin.dispose();
	}
   
}