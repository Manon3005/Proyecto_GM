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
        	
	        /*   
	        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) sprite.setRotation(++rotacion);
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) sprite.setRotation(--rotacion);
	        
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        	xVel -=Math.sin(Math.toRadians(rotacion));
	        	yVel +=Math.cos(Math.toRadians(rotacion));
	        	System.out.println(rotacion+" - "+Math.sin(Math.toRadians(rotacion))+" - "+Math.cos(Math.toRadians(rotacion))) ;    
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	        	xVel +=Math.sin(Math.toRadians(rotacion));
	        	yVel -=Math.cos(Math.toRadians(rotacion));
	        	     
	        }
	        */
	        
	        // stay inside the borders of the window
	        if (x+xSpeed < 0 || x+xSpeed+sprite.getWidth() > Gdx.graphics.getWidth())
	        	xSpeed*=-1;
	        if (y+ySpeed < 0 || y+ySpeed+sprite.getHeight() > Gdx.graphics.getHeight())
	        	ySpeed*=-1;
	        
	        sprite.setPosition(x+xSpeed, y+ySpeed);   
         
 		    sprite.draw(batch);
        } else {
           sprite.setX(sprite.getX()+MathUtils.random(-2,2));
 		   sprite.draw(batch); 
 		   sprite.setX(x);
 		   hurtTime--;
 		   if (hurtTime <= 0) hurt = false;
 		 }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { 
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
	public void setLives(int lives2) {lives = lives2;}
}
