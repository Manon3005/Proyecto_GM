package spacenav.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import spacenav.code.SpaceNavigation;
import spacenav.code.domain.EasyDifficultyStrategy;
import spacenav.code.domain.HardDifficultyStrategy;
import spacenav.code.domain.NormalDifficultyStrategy;
import spacenav.code.interfaces.DifficultyStrategy;
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
		
		Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
		TextButton easyBtn   = new TextButton("Facil", skin);
        TextButton normalBtn = new TextButton("Normal", skin);
        TextButton hardBtn   = new TextButton("Dificil", skin);
        
        table.add(easyBtn).pad(10).row();
        table.add(normalBtn).pad(10).row();
        table.add(hardBtn).pad(10).row();
        
        easyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                start(new EasyDifficultyStrategy());
                dispose();
            }
        });

        normalBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                start(new NormalDifficultyStrategy());
                dispose();
            }
        });

        hardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                start(new HardDifficultyStrategy());
                dispose();
            }
        });
	}
	
	private void start(DifficultyStrategy e) {
        game.setDifficultyStrategy(e);
        game.setScreen(new GameScreen(game, 1, 3, 0, 1, 1, 5, 4f));
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

	    layout.setText(defaultFont, "Haz clic en un boton para comenzar una partida!");
	    float xSub = (1200 - layout.width) / 2f;
	    float ySub = 250;

	    defaultFont.draw(game.getBatch(), layout, xSub, ySub);
	    game.getBatch().end();

	    stage.act(delta);
	    stage.draw();
}
	
	
	@Override
	public void show() {
	    Gdx.input.setInputProcessor(stage);
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
	}
   
}
