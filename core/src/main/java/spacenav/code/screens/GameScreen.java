
package spacenav.code.screens;

import spacenav.code.domain.LevelParams;
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
import com.badlogic.gdx.math.MathUtils;

import spacenav.code.SpaceNavigation;
import spacenav.code.domain.Asteroid;
import spacenav.code.domain.Bullet;
import spacenav.code.domain.PowerUp;
import spacenav.code.domain.PowerUpFactory;
import spacenav.code.domain.Spaceship;
import spacenav.code.interfaces.DifficultyStrategy;
import spacenav.code.utils.AssetLoader;

public class GameScreen implements Screen {
	
	private DifficultyStrategy difficultyStrategy;

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
    private final float bulletSpeed = 4f;

    // Power-ups
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private final float DROP_CHANCE = 0.30f;  // probabilidad de drop por asteroide

    private Spaceship spaceship;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public GameScreen(SpaceNavigation game, int round, int lives, int score) {
        this.game = game;
        this.round = round;
        this.score = score;
        
        this.difficultyStrategy = game.getDifficultyStrategy();
        LevelParams lp = this.difficultyStrategy.next(round);
        
        
        this.speedXAsteroids = lp.speedXAsteroids;
        this.speedYAsteroids = lp.speedYAsteroids;
        this.nbAsteroids = lp.nAsteroids;
        
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

        // asteroids creation (tamaños variables)
        Random r = new Random();
        while (asteroids.size() < nbAsteroids) {
            int size = 20 + r.nextInt(30); // 20..50
            int sx = speedXAsteroids + r.nextInt(4);
            int sy = speedYAsteroids + r.nextInt(4);

            Asteroid a = new Asteroid(
                r.nextInt(Gdx.graphics.getWidth()),
                50 + r.nextInt(Gdx.graphics.getHeight() - 50),
                size,
                sx,
                sy,
                assets.get(AssetLoader.ASTEROID_TEXTURE, Texture.class)
            );
            
            boolean existCollision = false;
            for (int j = 0; j < asteroids.size(); j++) {
            	if (a.checkCollision(asteroids.get(j))) {
            		existCollision = true;
            	}
            }
            if (!existCollision) {
            	asteroids.add(a);
            }
        }
    }

    private void drawHeader() {
        CharSequence str = "Lives: " + spaceship.getLives() + "  Round: " + (round + 1);
        defaultFont.draw(batch, str, 10, 30);
        defaultFont.draw(batch, "Score: " + score, Gdx.graphics.getWidth() - 180, 30);
        defaultFont.draw(batch, "HighScore: " + game.getHighScore(), Gdx.graphics.getWidth() / 2f - 100, 30);
    }

    @Override
    public void render(float delta) {
        // LIMPIAR Y COMENZAR DIBUJO 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // HUD
        drawHeader();

        if (!spaceship.isHurt()) {
            // collisions between bullets and asteroids
            for (int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                b.update();

                for (int j = 0; j < asteroids.size(); j++) {
                    Asteroid a = asteroids.get(j);

                    if (b.checkCollision(a)) {
                        // 1) aplicar daño
                        a.takeDamage(1);

                        // 2) feedback y destruir la bala (1 bala = 1 impacto)
                        explosionSound.play();
                        b.destroy();

                        // 3) si el asteroide llegó a 0, recién entonces removerlo y sumar puntaje + drop
                        if (a.isDestroyed()) {
                            // Puntaje base
                            score += 10;

                            // Chance de drop
                            if (Math.random() < DROP_CHANCE) {
                                PowerUp p = PowerUpFactory.createRandom(a.getX() + a.getSize()/2f, a.getY() + a.getSize()/2f);
                                powerUps.add(p);
                            }

                            asteroids.remove(j);
                            j--;
                        }

                        // Esta bala ya colisionó
                        break;
                    }
                }

                // remover la bala si ya no está activa
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

        // disparo normal (click) — reutiliza spawnBullet
        if (spaceship.wasBulletSent()) {
            spawnBullet();
            bulletSound.play();
            spaceship.setBulletSent();
        }

        // draw asteroids and check collision with the spaceship
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            a.draw(batch);

            if (spaceship.checkCollision(a)) {
                spaceship.doBounce(a);
                spaceship.takeDamage(1);
                asteroids.remove(i);
                i--;
            }
        }

        // actualizar/dibujar powerups
        for (int k = 0; k < powerUps.size(); k++) {
            PowerUp pu = powerUps.get(k);
            pu.update(delta);
            pu.draw(batch);
        }
        
        // recoger powerups
        for (int k = 0; k < powerUps.size(); k++) {
            PowerUp pu = powerUps.get(k);
            if (!pu.isCollected() && spaceship.checkCollision(pu)) {
                pu.applyTo(spaceship);
                powerUps.remove(k);
                k--;
            }
        }

        // end of game handling
        if (spaceship.isDestroyed()) {
            if (score > game.getHighScore()) game.setHighScore(score);
            Screen ss = new GameOverScreen(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        batch.end();

        // next level
        if (asteroids.isEmpty() && !spaceship.isDestroyed()) {       	
        	Screen ss = new GameScreen(
                    game,
                    round + 1,
                    spaceship.getLives(),
                    score
            );
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }

    private void spawnBullet() {
        float angleDeg = spaceship.getRotation();
        float angleRad = (float) Math.toRadians(angleDeg + 90f);

        float bulletXSpeed = MathUtils.cos(angleRad) * bulletSpeed;
        float bulletYSpeed = MathUtils.sin(angleRad) * bulletSpeed;

        float startX = spaceship.getX() + spaceship.getWidth() / 2f;
        float startY = spaceship.getY() + spaceship.getHeight() / 2f;

        float offset = spaceship.getHeight() / 2f;
        startX += MathUtils.cos(angleRad) * offset;
        startY += MathUtils.sin(angleRad) * offset;

        Bullet bullet = new Bullet(
            startX,
            startY,
            (int) bulletXSpeed,
            (int) bulletYSpeed,
            bulletTx,
            angleDeg
        );

        addBullet(bullet);
    }

    private boolean addBullet(Bullet bb) { return bullets.add(bb); }

    @Override public void show() { gameMusic.play(); }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { explosionSound.dispose(); gameMusic.dispose(); }
}
