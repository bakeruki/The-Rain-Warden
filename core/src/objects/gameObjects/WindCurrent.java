package objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import objects.entities.Player;

public class WindCurrent extends InteractiveTileObject{

    private Player player;

    public WindCurrent(World world, Body body, Fixture sensorFixture, Player player) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.player = player;
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Wind Current", "Collision");
        player.startWindCarry();
    }

    public void endContact(){
        player.endWindCarry();
        Gdx.app.log("Wind Current", "End Collision");
    }

    @Override
    public void update() {
        
    }
    
}
