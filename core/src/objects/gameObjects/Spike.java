package objects.gameObjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Spike extends InteractiveTileObject {

    public Spike(World world, Body body, Fixture sensorFixture) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
    }

    @Override
    public void onCollision() {
        // TODO make collision logic (reset player to start position for the level, play death animation, so on)

    }

    @Override
    public void update() {

    }

}