package spacenav.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;

import spacenav.code.SpaceNavigation;
import spacenav.code.utils.AssetLoader;

public class GameOverScreen implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;

    private BitmapFont defaultFont;
    private BitmapFont titleFont;

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    public GameOverScreen(SpaceNavigation game) {
        this.game = game;

        AssetLoader assets = AssetLoader.getInstance();
        defaultFont = assets.get(AssetLoader.DEFAULT_FONT, BitmapFont.class);
        titleFont = assets.get(AssetLoader.TITLE_FONT, BitmapFont.class);
        skin = assets.get(AssetLoader.BUTTON_SKIN, Skin.class);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        TextButton menuButton = new TextButton("Volver al menu", skin);
        menuButton.setSize(400, 120);
        menuButton.setPosition(
                WIDTH / 2f - menuButton.getWidth() / 2f,
                HEIGHT / 2f - menuButton.getHeight() / 2f - 200
        );

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screen menu = new MenuScreen(game);
                game.setScreen(menu);
                dispose();
            }
        });

        stage.addActor(menuButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();

        GlyphLayout layout = new GlyphLayout();

        layout.setText(titleFont, "GAME OVER");
        float xTitle = (WIDTH - layout.width) / 2f;
        titleFont.draw(game.getBatch(), layout, xTitle, 600);

        layout.setText(defaultFont, "Pincha en cualquier lado para reiniciar...");
        float xSub = (WIDTH - layout.width) / 2f;
        defaultFont.draw(game.getBatch(), layout, xSub, 400);

        game.getBatch().end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
        	Screen ss = new GameScreen(game,0,3,0);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
