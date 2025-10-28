package spacenav.code.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import spacenav.code.SpaceNavigation;
import spacenav.code.domain.Asteroid;
import spacenav.code.domain.Bullet;
import spacenav.code.domain.Spaceship;
import spacenav.code.utils.AssetLoader;

public class GameScreen implements Screen {

    private SpaceNavigation game;
    private BitmapFont defaultFont;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Sound bulletSound;
    private Texture bulletTx;
    private Music gameMusic;
    private int score;
    private int round;
    private int speedXAsteroids;
    private int speedYAsteroids;
    private int nbAsteroids;

    private Spaceship spaceship;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public GameScreen(SpaceNavigation game, int round, int lives, int score,
                      int speedXAsteroids, int speedYAsteroids, int nbAsteroids) {
        this.game = game;
        this.round = round;
        this.score = score;
        this.speedXAsteroids = speedXAsteroids;
        this.speedYAsteroids = speedYAsteroids;
        this.nbAsteroids = nbAsteroids;
        
        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        
        AssetLoader assets = AssetLoader.getInstance();
		defaultFont = assets.get(AssetLoader.DEFAULT_FONT, BitmapFont.class);
        bulletTx = assets.get(AssetLoader.BULLET_TEXTURE, Texture.class);
        bulletSound = assets.get(AssetLoader.BULLET_SOUND, Sound.class);
        explosionSound = assets.get(AssetLoader.EXPLOSION_SOUND, Sound.class);
        explosionSound.setVolume(1, 0.5f);
        gameMusic = assets.get(AssetLoader.GAME_MUSIC, Music.class);
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        // spaceship creation
        spaceship = new Spaceship(
            Gdx.graphics.getWidth() / 2 - 50,
            30,
            assets.get(AssetLoader.SHIP_TEXTURE, Texture.class),
            assets.get(AssetLoader.HURT_SOUND, Sound.class)
        );
        spaceship.setLives(lives);

        // asteroids creation
        Random r = new Random();
        for (int i = 0; i < nbAsteroids; i++) {
            Asteroid a = new Asteroid(
                r.nextInt(Gdx.graphics.getWidth()),
                50 + r.nextInt(Gdx.graphics.getHeight() - 50),
                20 + r.nextInt(10),
                speedXAsteroids + r.nextInt(4),
                speedYAsteroids + r.nextInt(4),
                assets.get(AssetLoader.ASTEROID_TEXTURE, Texture.class)
            );
            asteroids.add(a);
        }
    }

    public void drawHeader() {
        CharSequence str = "Lives: " + spaceship.getLives() + " Round: " + round;
        defaultFont.draw(batch, str, 10, 30);
        defaultFont.draw(batch, "Score:" + score, Gdx.graphics.getWidth() - 150, 30);
        defaultFont.draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawHeader();

        if (!spaceship.isHurt()) {
            // collisions between bullets and asteroids
            for (int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                b.update();

                for (int j = 0; j < asteroids.size(); j++) {
                    if (b.checkCollision(asteroids.get(j))) {
                        explosionSound.play();
                        asteroids.remove(j);
                        b.destroy();
                        j--;
                        score += 10;
                    }
                }

                if (b.isDestroyed()) {
                    bullets.remove(i);
                    i--;
                }
            }

            // movement update of asteroids
            for (Asteroid a : asteroids) {
                a.update();
            }

            // collisions between asteroids
            for (int i = 0; i < asteroids.size(); i++) {
                for (int j = i + 1; j < asteroids.size(); j++) {
                    if (asteroids.get(i).checkCollision(asteroids.get(j))) {
                    	asteroids.get(i).doBounce(asteroids.get(j));
                    }
                }
            }
        }

        // draw bullets
        for (Bullet b : bullets) {
            b.draw(batch);
        }

        // draw spaceship
        spaceship.draw(batch);
        
        if (spaceship.wasBulletSent()) {
        	Bullet bullet = new Bullet(spaceship.getX()+spaceship.getWidth()/2-5,spaceship.getY()+ spaceship.getHeight()-5,0,3, bulletTx);
        	addBullet(bullet);
  	      	bulletSound.play();
  	      	spaceship.setBulletSent();
        }

        // draw asteroids and check collision with the spaceship
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            a.draw(batch);

            if (spaceship.checkCollision(a)) {
            	spaceship.doBounce(a);
            	spaceship.hurt();
                asteroids.remove(i);
                i--;
            }
        }

        // end of game handling
        if (spaceship.isDestroyed()) {
            if (score > game.getHighScore())
                game.setHighScore(score);
            Screen ss = new GameOverScreen(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        batch.end();

        // next level
        if (asteroids.isEmpty()) {
            Screen ss = new GameScreen(
                game,
                round + 1,
                spaceship.getLives(),
                score,
                speedXAsteroids + 3,
                speedYAsteroids + 3,
                nbAsteroids + 10
            );
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }

    public boolean addBullet(Bullet bb) {
        return bullets.add(bb);
    }

    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        explosionSound.dispose();
        gameMusic.dispose();
    }
}
