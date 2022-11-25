package objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import helper.Constants;
import objects.entities.Player;

public class ShinyRaindrop extends InteractiveTileObject {

    private Player player;
    private Body body;
    private Body toBeDestroyed;

    private float x;
    private float y;


    private boolean isRemoved;

    public ShinyRaindrop(World world, Body body, Fixture sensorFixture, Player player) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.world = world;
        this.player = player;
        this.body = body;
        this.sensorFixture = sensorFixture;
        this.isRemoved = false;

        this.x = body.getPosition().x * Constants.PPM;
        this.y = body.getPosition().y * Constants.PPM;
    }

    @Override
    public void onCollision(){
        if(!isRemoved){
            Gdx.app.log("Shiny Raindrop", "Collision");
            player.resetDashCounter();
            removeBody(body);
        }
    }

    @Override
    public void update(){
        if(toBeDestroyed != null){
            toBeDestroyed = null;
            isRemoved = true;
        }
    }

    public boolean isRemoved(){
        if(isRemoved){
            return true;
        }else{
            return false;
        }
    }

    public void respawn(){
        isRemoved = false;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void removeBody(Body body){
        toBeDestroyed = body;
    }
}
