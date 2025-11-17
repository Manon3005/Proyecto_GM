package spacenav.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

import spacenav.code.SpaceNavigation;
import spacenav.code.utils.AssetLoader;


public class GameOverScreen implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private BitmapFont defaultFont;
	private BitmapFont titleFont;

	public GameOverScreen(SpaceNavigation game) {
		this.game = game;
		
		AssetLoader assets = AssetLoader.getInstance();
		defaultFont = assets.get(AssetLoader.DEFAULT_FONT, BitmapFont.class);
		titleFont = assets.get(AssetLoader.TITLE_FONT, BitmapFont.class);
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
	    GlyphLayout layout = new GlyphLayout();

	    layout.setText(titleFont, "GAME OVER");
	    float xTitle = (1200 - layout.width) / 2f;
	    float yTitle = 550;
	    titleFont.draw(game.getBatch(), layout, xTitle, yTitle);

	    layout.setText(defaultFont, "Pincha en cualquier lado para reiniciar...");
	    float xSub = (1200 - layout.width) / 2f;
	    float ySub = 300;

	    defaultFont.draw(game.getBatch(), layout, xSub, ySub);
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new GameScreen(game,1,3,0,1,1,10,3f);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
 
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
   
}