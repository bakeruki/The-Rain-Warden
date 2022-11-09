package objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.beleste.GameScreen;

public class Spike extends InteractiveTileObject {

    private GameScreen gameScreen;
    private boolean collided;

    public Spike(World world, Body body, Fixture sensorFixture, GameScreen gameScreen) {
        super(world, body, sensorFixture);
        this.gameScreen = gameScreen;
        sensorFixture.setUserData(this);
        collided = false;
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Spike", "Collision");
        collided = true;
    }

    @Override
    public void update() {
        //trying to move the player in the collision method causes issues
        if(collided){
            gameScreen.respawnPlayer();
            collided = false;
        }
    }

}