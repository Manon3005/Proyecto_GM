package spacenav.code.domain;

import com.badlogic.gdx.graphics.Texture;

public class ShieldPowerUp extends PowerUp {

	public ShieldPowerUp(float x, float y, float size, Texture tx, float duration) {
		super(x, y, size, tx, duration);
	}

	@Override
	protected void applyEffect(Spaceship ship) {
		ship.activateShield(duration);
	}


}
