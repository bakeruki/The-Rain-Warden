package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import helper.Constants;

public class Player extends GameEntity {

    private int jumpCounter = 0;
    private int dashCounter = 0;
    private int countDash = 0;
    private boolean left = false;

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
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCounter++;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && dashCounter < 1){
            countDash = 1;
            dashCounter++;
        }
        
        body.setLinearVelocity(velX*speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);

        if(body.getLinearVelocity().y == 0){
            jumpCounter = 0;
            dashCounter = 0;
        }
    }

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

    public boolean isLeft(){
        return left;
    }

    public void resetDashCounter(){
        dashCounter = 0;
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
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;
        
        if(countDash == 0){
            checkUserInput();
        }else{
            dash(25);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        
    }
    
}
