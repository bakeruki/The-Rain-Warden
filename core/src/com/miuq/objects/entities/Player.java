package com.miuq.objects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.miuq.helper.Constants;

public class Player extends GameEntity {

    private int jumpCounter = 0;
    private int dashCounter = 0;
    private int countDash = 0;
    private int deathCounter = 0;
    private float windForce = 0;
    private boolean left = false;
    private boolean isDead = false;
    private boolean isCarried = false;
    private boolean isPressingCarryButton = false;

    public Player(float width, float height, Body body){
        super(width, height, body);
        this.speed = 10f;
    }

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < 2){
            float force = body.getMass() * 18;
            body.setLinearVelocity(body.getLinearVelocity().x, 0.1f);
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCounter++;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && dashCounter < 1){
            countDash = 1;
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
            if(countDash == 0){
                dashCounter = 0;
            } 
        }
    }

    //getters
    public float getX(){
        return (body.getPosition().x * Constants.PPM);
    }

    public float getY(){
        return (body.getPosition().y * Constants.PPM);
    }

    public float getVelX(){
        return(body.getLinearVelocity().x);
    }
    public float getVelY(){
        return(body.getLinearVelocity().y);
    }

    public float getWidth(){
        return this.width;
    }

    public float getHeight(){
        return this.height;
    }

    public int getDashCounter(){
        return countDash;
    }

    public int getJumpCount(){
        return jumpCounter;
    }

    public int getDeathCounter(){
        return deathCounter;
    }

    public boolean isLeft(){
        return left;
    }

    public boolean isDead(){
        return isDead;
    }

    public boolean isCarried(){
        return isCarried;
    }

    public boolean isFalling(){
        if(body.getLinearVelocity().y < 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isJumping(){
        if(body.getLinearVelocity().y > 0){
            return true;
        }else{
            return false;
        }
    }

    //setters
    public void kill(){
        isDead = true;
        deathCounter++;
    }

    public void respawn(){
        isDead = false;
    }

    public void startWindCarry(){
        isCarried = true;
    }

    public void endWindCarry(){
        isCarried = false;
        windForce = 0;
    }

    public void resetDashCounter(){
        dashCounter = 0;
    }

    public void setVelocityX(int vX){
        body.setLinearVelocity(vX, body.getLinearVelocity().y);
    }

    public void setVelocityY(int vY){
        body.setLinearVelocity(body.getLinearVelocity().x, vY);
    }

    private void dash(int distance){
        if(countDash < distance){
            if(left){
                body.setLinearVelocity(-25, 0);
            }else{
                body.setLinearVelocity(25, 0);
            }
            countDash++;
        }else{
            countDash = 0;
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
            if(countDash == 0){
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
