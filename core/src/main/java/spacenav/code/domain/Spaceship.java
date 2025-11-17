package spacenav.code.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import spacenav.code.interfaces.Damageable;

public class Spaceship extends Entity implements Damageable {
	
	    private boolean destroyed = false;
	    private int lives = 3;
	    private boolean hurt = false;
	    private int hurtTimeMax = 50;
	    private int hurtTime;
	    private Sound hurtSound;
	    private boolean wasBulletSent = false;
	    
	    private float speed = 4f;  
	    private boolean moving = false;

	    // Escudo
	    private boolean shielded = false;
	    private float shieldTimer = 0f;

	    public Spaceship(int x, int y, Texture tx, Sound hurtSound) {
	        super(x, y, 0, 0, tx);
	        this.hurtSound = hurtSound;
	        sprite.setBounds(x, y, 45, 45);
	        sprite.setOriginCenter();
	    }

	    @Override
	    public void draw(SpriteBatch batch) {
	        // --- actualizar escudo por frame ---
	        if (shielded) {
	            shieldTimer -= Gdx.graphics.getDeltaTime();
	            if (shieldTimer <= 0f) {
	                shielded = false;
	            }
	        }

	        float x = sprite.getX();
	        float y = sprite.getY();
	        
	        if (!hurt) {

	            if (!moving) {
	                if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) ||
	                    Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) ||
	                    Gdx.input.isKeyJustPressed(Input.Keys.UP) ||
	                    Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
	                    moving = true;
	                }
	            }

	            xSpeed = 0;
	            ySpeed = 0;

	            if (moving) {
	                if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  xSpeed = -speed;
	                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xSpeed = speed;
	                if (Gdx.input.isKeyPressed(Input.Keys.UP))    ySpeed = speed;
	                if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  ySpeed = -speed;
	            }

	            float nextX = x + xSpeed;
	            float nextY = y + ySpeed;

	            if (nextX < 0) nextX = 0;
	            if (nextX + sprite.getWidth() > Gdx.graphics.getWidth())
	                nextX = Gdx.graphics.getWidth() - sprite.getWidth();

	            if (nextY < 0) nextY = 0;
	            if (nextY + sprite.getHeight() > Gdx.graphics.getHeight())
	                nextY = Gdx.graphics.getHeight() - sprite.getHeight();

	            sprite.setPosition(nextX, nextY);
	            
	         // mouse orientation
	            float centerX = sprite.getX() + sprite.getWidth() / 2f;
	            float centerY = sprite.getY() + sprite.getHeight() / 2f;
	            float xInput = Gdx.input.getX();
	            float yInput = Gdx.graphics.getHeight() - Gdx.input.getY();
	            float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - centerY, xInput - centerX) - 90f;
	            if (angle < 0) angle += 360f;
	            sprite.setRotation(angle);

	            if (shielded) sprite.setColor(0.7f, 0.85f, 1f, 1f);
	            else sprite.setColor(1f, 1f, 1f, 1f);
	            
	            sprite.draw(batch);     
	        } else {
	            // feedback de daño (temblor)
	            sprite.setX(sprite.getX() + MathUtils.random(-2, 2));
	            if (shielded) sprite.setColor(0.7f, 0.85f, 1f, 1f); else sprite.setColor(1f, 1f, 1f, 1f);
	            sprite.draw(batch);
	            sprite.setX(x);

	            hurtTime--;
	            if (hurtTime <= 0) hurt = false;
	        }

	        // input de disparo
	        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
	            this.wasBulletSent = true;
	        }
	    }

	    // ===== Damageable =====
	    @Override
	    public void takeDamage(int damage) {
	        if (damage <= 0) return;        // ignorar valores inválidos
	        if (shielded) return;           // escudo activo => no recibe daño
	        if (hurt || destroyed) return;  // i-frames o ya destruida

	        lives -= damage;

	        // activar invulnerabilidad temporal (i-frames)
	        hurt = true;
	        hurtTime = hurtTimeMax;
	        if (hurtSound != null) hurtSound.play();

	        if (lives <= 0) {
	            destroyed = true;
	        }
	    }

	    public void doBounce(Asteroid a) {
	        if (xSpeed == 0) xSpeed += a.getXSpeed() / 2;
	        if (a.getXSpeed() == 0) a.setXSpeed(a.getXSpeed() + (int) xSpeed / 2);
	        xSpeed = -xSpeed;
	        a.setXSpeed(-a.getXSpeed());

	        if (ySpeed == 0) ySpeed += a.getYSpeed() / 2;
	        if (a.getYSpeed() == 0) a.setYSpeed(a.getYSpeed() + (int) ySpeed / 2);
	        ySpeed = -ySpeed;
	        a.setYSpeed(-a.getYSpeed());
	    }

	    @Override
	    public boolean isDestroyed() {
	        return !hurt && destroyed;
	    }

	    public boolean isHurt() {
	        return hurt;
	    }

	    public void setBulletSent() {
	        wasBulletSent = false;
	    }

	    public boolean wasBulletSent() {
	        return wasBulletSent;
	    }

	    // ===== Escudo / Power-up =====
	    public void activateShield(float seconds) {
	        shielded = true;
	        shieldTimer = Math.max(0f, seconds);
	    }

	    public boolean hasShield() {
	        return shielded;
	    }

	    // ===== Getters / setters =====
	    public int getLives() { return lives; }
	    public int getX() { return (int) sprite.getX(); }
	    public int getY() { return (int) sprite.getY(); }
	    public float getWidth() { return sprite.getWidth(); }
	    public float getHeight() { return sprite.getHeight(); }
	    public float getRotation() { return sprite.getRotation(); }
	    public void setLives(int lives2) { lives = lives2; }
}
