package spacenav.code.domain;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

import spacenav.code.utils.AssetLoader;

public class PowerUpFactory {

    private static final Random random = new Random();
    private static final float SIZE = 28f;

    public static PowerUp createRandom(float x, float y) {
    	
    	AssetLoader assets = AssetLoader.getInstance();
    	Texture shieldTx = assets.get(AssetLoader.SHIELD_POWERUP_TEXTURE, Texture.class);
    	Texture extraLifeTx = assets.get(AssetLoader.EXTRA_LIFE_POWERUP_TEXTURE, Texture.class);

        int r = random.nextInt(2);

        switch (r) {
            case 0:
                return new ShieldPowerUp(x - SIZE/2f, y - SIZE/2f, SIZE, shieldTx, 5f);
            case 1:
                return new ExtraLifePowerUp(x - SIZE/2f, y - SIZE/2f, SIZE, extraLifeTx, 0f);
        }

        return null;
    }
}
