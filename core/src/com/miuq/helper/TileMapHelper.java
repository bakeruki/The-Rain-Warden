package com.miuq.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.objects.entities.Player;
import com.miuq.objects.gameObjects.CameraSwitchTrigger;
import com.miuq.objects.gameObjects.Mango;
import com.miuq.objects.gameObjects.ShinyRaindrop;
import com.miuq.objects.gameObjects.Spike;
import com.miuq.objects.gameObjects.WindCurrent;

public class TileMapHelper {
    
    private TiledMap tiledMap;
    private GameScreen gameScreen;
    private Player player;

    public TileMapHelper(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap(){
        tiledMap = new TmxMapLoader().load("assets/maps/Map.tmx");
        parseMapObjects(tiledMap.getLayers().get("Object Layer 1").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
    /**
	 * Initializes objects from the tilemap file and creates Body and Fixture objects
     * to allow the player (and other objects) to collide with them.
	 * @param mapObjects The objects in the tilemap (use parseMapObjects)
     * @author Luqman Patel
	 */
    private void parseMapObjects(MapObjects mapObjects){
        for(MapObject mapObject : mapObjects){
            if(mapObject instanceof PolygonMapObject){
                createMapBounds((PolygonMapObject)mapObject);
            }
            if(mapObject instanceof RectangleMapObject){
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if(rectangleName.equals("player")){
                    Body body = BodyHelperService.createBody(
                        rectangle.getX() + rectangle.getWidth() / 2, 
                        rectangle.getY() + rectangle.getHeight() / 2 , 
                        rectangle.getWidth(), rectangle.getHeight(), 
                        false, 
                        gameScreen.getWorld());

                    Fixture sensorFixture = BodyHelperService.createSensorFixture(
                        rectangle.getX(), 
                        rectangle.getY(), 
                        rectangle.getWidth(), 
                        rectangle.getHeight(), 
                        false, 
                        gameScreen.getWorld(),
                        body);
                    sensorFixture.setUserData("player");
                    player = new Player(rectangle.getWidth(), rectangle.getHeight(), body);
                    gameScreen.setPlayer(player);
                }
                if(rectangleName.equals("shinyRaindrop")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();
                    
                    gameScreen.addShinyRaindrop(new ShinyRaindrop(gameScreen.getWorld(), body, fixture, gameScreen.getPlayer())); 
                }
                if(rectangleName.equals("mango")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();

                    gameScreen.addMango(new Mango(gameScreen.getWorld(), body, fixture));
                }
                if(rectangleName.equals("cameraSwitch")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();

                    gameScreen.addCameraSwitch(new CameraSwitchTrigger(gameScreen.getWorld(), body, fixture));
                }
                if(rectangleName.equals("spike")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();

                    gameScreen.addSpike(new Spike(gameScreen.getWorld(), body, fixture, player));
                }
                if(rectangleName.equals("windCurrent")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();

                    gameScreen.addWindCurrents(new WindCurrent(gameScreen.getWorld(), body, fixture, player));
                }
            }
        }
    }

    private void createMapBounds(PolygonMapObject polygonMapObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Body createBody(Rectangle rectangle){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set((rectangle.getX() / Constants.PPM) + (rectangle.getWidth() / 2 / Constants.PPM), (rectangle.getY() / Constants.PPM) + (rectangle.getHeight() / 2 / Constants.PPM));
        bodyDef.fixedRotation = true;
        return gameScreen.getWorld().createBody(bodyDef);
    }

    private Fixture createSensorFixture(PolygonShape shape, Body body){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.isSensor = true;
        return body.createFixture(fixtureDef);
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject){
        float[] verticies = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVerticies = new Vector2[verticies.length/2];

        for(int i = 0; i < verticies.length / 2; i++){
            Vector2 current = new Vector2(verticies[i * 2 ] / Constants.PPM, verticies[i * 2 + 1] / Constants.PPM);
            worldVerticies[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVerticies);
        return shape;
    }
}
