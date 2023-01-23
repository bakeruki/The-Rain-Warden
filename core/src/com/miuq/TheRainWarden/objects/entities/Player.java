package com.miuq.TheRainWarden.objects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.miuq.TheRainWarden.helper.Constants;

/**
 * Note: Most of the player class was developed by Michelle, however some code including the movement was
 * borrowed from the following video.
 * 
 * @author Michelle Vuong
 * @author Credit: Small Pixel Games on YouTube
 * https://www.youtube.com/watch?v=6QKhSctuMcs&ab_channel=SmallPixelGames
 * 
 */
public class Player extends GameEntity {
    /**
     * Counts how many times the player has jumped.
     */
    private int jumpCounter = 0;
    /**
     * Counts how many times the player has dashed.
     */
    private int dashCounter = 0;
    /**
     * Counts the amount of force impulses to apply to the player (more impluses = further dash).
     */
    private int countDashImpulse = 0;
    /**
     * Counts how many times the player has died.
     */
    private int deathCounter = 0;
    /**
     * The amount of force that should be applied to the player when they are in the wind current.
     * (It starts as 0 and gets added gradually to give a momentum effect).
     */
    private float windForce = 0;
    /**
     * Tells if the player is facing left.
     */
    private boolean left = false;
    /**
     * Tells if the player is currently dead.
     */
    private boolean isDead = false;
    /**
     * Tells if the player is currently being carried by a wind current.
     */
    private boolean isCarried = false;
    /**
     * Tells if the player is currently holding the wind carry button.
     */
    private boolean isPressingCarryButton = false;
    /**
     * Tells if the player has unlocked the dash ability.
     */
    private boolean dashUnlocked = false;
    /**
     * Tells if the player is allowed to jump.
     */
    private boolean jumpEnabled = true;

    public Player(float width, float height, Body body){
        super(width, height, body);
        this.speed = 10f;
    }

    /**
     * Checks all user input to control the player.
     * @author Michelle Vuong
     * @author Credit: Small Pixel Games on YouTube
     */
    private void checkUserInput(){
        velX = 0;
        
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velX = 1;
            left = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velX = -1;
            left = true;
        }

        if(jumpEnabled){
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < 2){
                float force = body.getMass() * 18;
                body.setLinearVelocity(body.getLinearVelocity().x, 0.1f);
                body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
                jumpCounter++;
            }
        }

        //all code after this made by Michelle Vuong-----------------------------------------------
        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && dashCounter < 1 && dashUnlocked){
            countDashImpulse = 1;
            dashCounter++;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            isPressingCarryButton = true;
        }else{
            isPressingCarryButton = false;
        }
        
        body.setLinearVelocity(velX*speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);

        if(body.getLinearVelocity().y == 0){
            jumpCounter = 0;
            //only sets the dash count to 0 if a dash has been completed (prevents ability to get extra dash if you dash off a ledge)
            if(countDashImpulse == 0){
                dashCounter = 0;
            } 
        }
    }

    //getters
    /**
     * Gets the x position of the player.
     * @return X position of the player.
     * @author Michelle Vuong
     */
    public float getX(){
        return (body.getPosition().x * Constants.PPM);
    }

    /**
     * Gets the Y position of the player.
     * @return Y position of the player.
     * @author Michelle Vuong
     */
    public float getY(){
        return (body.getPosition().y * Constants.PPM);
    }

    /**
     * Gets the X velocity of the player.
     * @return X velocity of the player.
     * @author Michelle Vuong
     */
    public float getVelX(){
        return(body.getLinearVelocity().x);
    }

    /**
     * Gets the Y velocity of the player.
     * @return Y velocity of the player.
     * @author Michelle Vuong
     */
    public float getVelY(){
        return(body.getLinearVelocity().y);
    }

    /**
     * Gets the width of the player.
     * @return width of the player.
     * @author Michelle Vuong
     */
    public float getWidth(){
        return this.width;
    }

    /**
     * Gets the height of the player.
     * @return height of the player.
     * @author Michelle Vuong
     */
    public float getHeight(){
        return this.height;
    }

    /**
     * Gets the dash impulse counter.
     * @return Number of dash impulses that have been applied.
     * @author Michelle Vuong
     */
    public int getDashCounter(){
        return countDashImpulse;
    }

    /**
     * Gets the jump counter.
     * @return Number of jumps that the player has used.
     * @author Michelle Vuong
     */
    public int getJumpCount(){
        return jumpCounter;
    }

    /**
     * Gets the death counter.
     * @return Number of deaths that have occured.
     * @author Michelle Vuong
     */
    public int getDeathCounter(){
        return deathCounter;
    }

    /**
     * Gets the direction of the player.
     * @return True if the player is facing left, false if the player is facing right.
     * @author Michelle Vuong
     */
    public boolean isLeft(){
        return left;
    }

    /**
     * Gets whether the player is alive or dead.
     * @return True if the player is dead, false if the player is alive.
     * @author Michelle Vuong
     */
    public boolean isDead(){
        return isDead;
    }

    /**
     * Gets whether the player is being carried by a wind current.
     * @return True if the player is currently being carried, false if the player is not currently being carried.
     * @author Michelle Vuong
     */
    public boolean isCarried(){
        return isCarried;
    }

    /**
     * Gets whether the player is currently falling.
     * @return True if the player is currently falling, false if the player is not currently falling.
     * @author Michelle Vuong
     */
    public boolean isFalling(){
        if(body.getLinearVelocity().y < 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Gets whether the player is currently jumping.
     * @return True if the player is currently jumping, false if the player is not currently jumping.
     * @author Michelle Vuong
     */
    public boolean isJumping(){
        if(body.getLinearVelocity().y > 0){
            return true;
        }else{
            return false;
        }
    }

    //setters
    /**
     * Kills the player.
     * @author Michelle Vuong
     */
    public void kill(){
        isDead = true;
        deathCounter++;
    }

    /**
     * Respawns the player.
     * @author Michelle Vuong
     */
    public void respawn(){
        isDead = false;
    }

    /**
     * Sets variables to allow player to be carried by wind.
     * @author Michelle Vuong
     */
    public void startWindCarry(){
        isCarried = true;
    }

    /**
     * Sets variables to disallow player to be carried by wind.
     * @author Michelle Vuong
     */
    public void endWindCarry(){
        isCarried = false;
        windForce = 0;
    }

    /**
     * Resets the player's dash counter.
     * @author Michelle Vuong
     */
    public void resetDashCounter(){
        dashCounter = 0;
    }

    /**
     * Sets the player's death counter.
     * @param deaths Number of deaths.
     * @author Michelle Vuong
     */
    public void setDeathCounter(int deaths){
        deathCounter = deaths;
    }

    /**
     * Sets the player's x velocity.
     * @param vX New x velocity.
     * @author Credit: Small Pixel Games on YouTube
     */
    public void setVelocityX(int vX){
        body.setLinearVelocity(vX, body.getLinearVelocity().y);
    }

    /**
     * Sets the player's y velocity.
     * @param vY New y velocity.
     * @author Credit: Small Pixel Games on YouTube
     */
    public void setVelocityY(int vY){
        body.setLinearVelocity(body.getLinearVelocity().x, vY);
    }

    /**
     * Allows the player to dash.
     * @author Michelle Vuong
     */
    public void unlockDash(){
        dashUnlocked = true;
    }

    /**
     * Allows the player to jump.
     * @author Michelle Vuong
     */
    public void enableJump(){
        jumpEnabled = true;
    }

    /**
     * Prevents the player from jumping.
     * @author Michelle Vuong
     */
    public void disableJump(){
        jumpEnabled = false;
    }

    /**
     * The player's dash ability.
     * @param distance How far the dash should take the player.
     * @author Michelle Vuong
     */
    private void dash(int distance){
        if(countDashImpulse < distance){
            if(left){
                body.setLinearVelocity(-25, 0);
            }else{
                body.setLinearVelocity(25, 0);
            }
            countDashImpulse++;
        }else{
            countDashImpulse = 0;
            left = false;
        }
    }

    @Override
    public void update() {  
        //these variables just keep track of the players position so it is easy to get them in other classes
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;
        
        //prevents movement while dead
        if(!isDead){
            //prevents movement during dash
            if(countDashImpulse == 0){
                checkUserInput();
            }else{
                dash(25);
            }
        }else{
            //makes sure the player doesnt keep moving if they were already moving before they died
            body.setLinearVelocity(0,0);
        }
        
        //checks if the player is in a wind current and applies force if they are.
        // a boolean is used here to make sure the animationRenderer class can play the correct animation (umbrella).
        if(isCarried && isPressingCarryButton){
            if(windForce < 20){
                windForce += 0.5;
            }
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, windForce), body.getPosition(), true);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        
    }
    
}
