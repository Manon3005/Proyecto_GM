package spacenav.code.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {

    private static AssetLoader instance;
    private static AssetManager assetManager;

    // Assets keys
    public static final String TITLE_FONT = "font/spacebar.fnt";
    public static final String DEFAULT_FONT = "font/default.fnt";
    public static final String EXPLOSION_SOUND = "sound/explosion.ogg";
    public static final String BULLET_SOUND = "sound/pop-sound.mp3";
    public static final String POWERUP_SOUND = "sound/power_up_sound_v1.ogg";
    public static final String HURT_SOUND = "sound/hurt.ogg";
    public static final String GAME_MUSIC = "music/piano-loops.wav";
    public static final String BULLET_TEXTURE = "texture/Rocket2.png";
    public static final String SHIP_TEXTURE = "texture/MainShip3.png";
    public static final String ASTEROID_TEXTURE = "texture/aGreyMedium4.png";
    public static final String EXTRA_LIFE_POWERUP_TEXTURE = "texture/heart.png";
    public static final String SHIELD_POWERUP_TEXTURE = "texture/shield4.png";

    private AssetLoader() {
        assetManager = new AssetManager();
    }
  
    public static AssetLoader getInstance() {
        if (instance == null) {
            instance = new AssetLoader();
        }
        return instance;
    }

    public void queueLoading() {
        assetManager.load(TITLE_FONT, BitmapFont.class);
        assetManager.load(DEFAULT_FONT, BitmapFont.class);
        
        assetManager.load(EXPLOSION_SOUND, Sound.class);
        assetManager.load(BULLET_SOUND, Sound.class);
        assetManager.load(HURT_SOUND, Sound.class);
        assetManager.load(POWERUP_SOUND, Sound.class);
        
        assetManager.load(GAME_MUSIC, Music.class);
        
        assetManager.load(BULLET_TEXTURE, Texture.class);
        assetManager.load(SHIP_TEXTURE, Texture.class);
        assetManager.load(ASTEROID_TEXTURE, Texture.class);
        assetManager.load(EXTRA_LIFE_POWERUP_TEXTURE, Texture.class);
        assetManager.load(SHIELD_POWERUP_TEXTURE, Texture.class);
    }

    public boolean update() {
        return assetManager.update();
    }

    public float getProgress() {
        return assetManager.getProgress();
    }

    public <T> T get(String assetPath, Class<T> type) {
        return assetManager.get(assetPath, type);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
