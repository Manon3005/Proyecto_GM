package spacenav.code.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Spaceship extends Entity {
	
	private boolean destroyed = false;
    private int lives = 3;
    private boolean hurt = false;
    private int hurtTimeMax=50;
    private int hurtTime;
    private Sound hurtSound;
    private boolean wasBulletSent = false;
    
    public Spaceship(int x, int y, Texture tx, Sound hurtSound) {
    	super(x, y, 0, 0, tx);
    	this.hurtSound = hurtSound;
    	sprite.setBounds(x, y, 45, 45);
    	sprite.setOriginCenter();
    }
    
    @Override
    public void draw(SpriteBatch batch){
        float x =  sprite.getX();
        float y =  sprite.getY();
        
        if (!hurt) {
	        // keyboard moving
	        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xSpeed--;
	        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xSpeed++;
        	if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) ySpeed--;     
	        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) ySpeed++;
	        
	        // stay inside the borders of the window
	        if (x+xSpeed < 0 || x+xSpeed+sprite.getWidth() > Gdx.graphics.getWidth())
	        	xSpeed*=-1;
	        if (y+ySpeed < 0 || y+ySpeed+sprite.getHeight() > Gdx.graphics.getHeight())
	        	ySpeed*=-1;
	        
	        sprite.setPosition(x+xSpeed, y+ySpeed);
	        
	        // mouse orientation
	        float centerX = sprite.getX() + sprite.getWidth() / 2f;
	        float centerY = sprite.getY() + sprite.getHeight() / 2f;
	        float xInput = Gdx.input.getX();
	        float yInput = Gdx.graphics.getHeight() - Gdx.input.getY();
	        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - centerY, xInput - centerX) - 90f;
	        if (angle < 0) angle += 360f;
	        sprite.setRotation(angle);
         
 		    sprite.draw(batch);
        } else {
           sprite.setX(sprite.getX()+MathUtils.random(-2,2));
 		   sprite.draw(batch); 
 		   sprite.setX(x);
 		   hurtTime--;
 		   if (hurtTime <= 0) hurt = false;
 		 }
        
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) { 
        	this.wasBulletSent = true;
        }
    }
    
    public void hurt() {
    	lives--;
    	hurt = true;
		hurtTime=hurtTimeMax;
		hurtSound.play();
        if (lives <= 0) {
        	destroyed = true; 
        }
    }
    
    public void doBounce(Asteroid a) {
    	if (xSpeed ==0) xSpeed += a.getXSpeed()/2;
        if (a.getXSpeed() ==0) a.setXSpeed(a.getXSpeed() + (int)xSpeed/2);
        xSpeed = - xSpeed;
        a.setXSpeed(-a.getXSpeed());
        
        if (ySpeed ==0) ySpeed += a.getYSpeed()/2;
        if (a.getYSpeed() ==0) a.setYSpeed(a.getYSpeed() + (int)ySpeed/2);
        ySpeed = - ySpeed;
        a.setYSpeed(- a.getYSpeed());
    }
    
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
    
    public int getLives() {return lives;}
    public int getX() {return (int) sprite.getX();}
    public int getY() {return (int) sprite.getY();}
    public float getWidth() {return sprite.getWidth();};
    public float getHeight() {return sprite.getHeight();};
    public float getRotation() {return sprite.getRotation();};
	public void setLives(int lives2) {lives = lives2;}
}
