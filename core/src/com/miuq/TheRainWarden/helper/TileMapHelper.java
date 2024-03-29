package com.miuq.TheRainWarden.helper;

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
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.objects.entities.Player;
import com.miuq.TheRainWarden.objects.entities.npc.Lu;
import com.miuq.TheRainWarden.objects.entities.npc.Manny;
import com.miuq.TheRainWarden.objects.entities.npc.Mimir;
import com.miuq.TheRainWarden.objects.entities.npc.Persephone;
import com.miuq.TheRainWarden.objects.entities.npc.Renni;
import com.miuq.TheRainWarden.objects.entities.npc.Rose;
import com.miuq.TheRainWarden.objects.gameObjects.CameraSwitchTrigger;
import com.miuq.TheRainWarden.objects.gameObjects.Mango;
import com.miuq.TheRainWarden.objects.gameObjects.ShinyRaindrop;
import com.miuq.TheRainWarden.objects.gameObjects.Spike;
import com.miuq.TheRainWarden.objects.gameObjects.WindCurrent;

/**
 * Credit: Small Pixel Games on YouTube
 * https://www.youtube.com/watch?v=8rBG7IWdDis&ab_channel=SmallPixelGames
 * Code is not the exactly the same, but was mainly taken from this video.
 */
public class TileMapHelper {
    private TiledMap tiledMap;
    private GameScreen gameScreen;
    private GameDialogueHandler dialogue;
    private Player player;

    public TileMapHelper(GameScreen gameScreen, GameDialogueHandler dialogue){
        this.gameScreen = gameScreen;
        this.dialogue = dialogue;
    }

    /**
     * Generates a map renderer by parsing a map file.
     * @param mapPath The filepath of the map file to parse.
     * @return A new tile map renderer object.
     */
    public OrthogonalTiledMapRenderer setupMap(String mapPath){
        tiledMap = new TmxMapLoader().load(mapPath);
        parseMapObjects(tiledMap.getLayers().get("Object Layer 1").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
    /**
	 * Initializes objects from the tilemap file and creates Body and Fixture objects
     * to allow the player (and other objects) to collide with them.
     * In our tilemap, rectangle objects represent objects that have interactability, such
     * as the player or objects that can be collected in game. The polygon objects represent
     * the bounds of the map and define floors, walls, ceilings, etc. 
	 * @param mapObjects The objects in the tilemap.
	 */
    private void parseMapObjects(MapObjects mapObjects){
        for(MapObject mapObject : mapObjects){
            if(mapObject instanceof PolygonMapObject){
                //creates floors / walls from the polygon objects in the map file
                createMapBounds((PolygonMapObject)mapObject);
            }
            //creates interactive objects from the rectangle objects in the map file based on their names
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
                if(rectangleName.equals("renni")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();
                    //if an npc has already been created, simply update the body with the new body, fixture, and world. otherwise, make an entirely new object.
                    if(dialogue.getRenni() != null){
                        Renni renni = dialogue.getRenni();
                        renni.setBody(body);
                        renni.setWorld(gameScreen.getWorld());
                        renni.setFixture(fixture);
                    }else{
                        dialogue.setRenni(new Renni(gameScreen.getWorld(), body, fixture, gameScreen));
                    }
                }
                if(rectangleName.equals("mimir")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();
                    //if an npc has already been created, simply update the body with the new body, fixture, and world. otherwise, make an entirely new object.
                    if(dialogue.getMimir() != null){
                        Mimir mimir = dialogue.getMimir();
                        mimir.setBody(body);
                        mimir.setWorld(gameScreen.getWorld());
                        mimir.setFixture(fixture);
                    }else{
                        dialogue.setMimir(new Mimir(gameScreen.getWorld(), body, fixture, gameScreen));
                    }
                }
                if(rectangleName.equals("persephone")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();
                    //if an npc has already been created, simply update the body with the new body, fixture, and world. otherwise, make an entirely new object.
                    if(dialogue.getPersephone() != null){
                        Persephone persephone = dialogue.getPersephone();
                        persephone.setBody(body);
                        persephone.setWorld(gameScreen.getWorld());
                        persephone.setFixture(fixture);
                    }else{
                        dialogue.setPersephone(new Persephone(gameScreen.getWorld(), body, fixture, gameScreen));
                    }
                }
                if(rectangleName.equals("lu")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();
                    //if an npc has already been created, simply update the body with the new body, fixture, and world. otherwise, make an entirely new object.
                    if(dialogue.getLu() != null){
                        Lu lu = dialogue.getLu();
                        lu.setBody(body);
                        lu.setWorld(gameScreen.getWorld());
                        lu.setFixture(fixture);
                    }else{
                        dialogue.setLu(new Lu(gameScreen.getWorld(), body, fixture, gameScreen));
                    }
                }
                if(rectangleName.equals("manny")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();
                    //if an npc has already been created, simply update the body with the new body, fixture, and world. otherwise, make an entirely new object.
                    if(dialogue.getManny() != null){
                        Manny manny = dialogue.getManny();
                        manny.setBody(body);
                        manny.setWorld(gameScreen.getWorld());
                        manny.setFixture(fixture);
                    }else{
                        dialogue.setManny(new Manny(gameScreen.getWorld(), body, fixture, gameScreen));
                    }
                }
                if(rectangleName.equals("rose")){
                    Body body = createBody(rectangle);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(rectangle.getWidth() / 2 / Constants.PPM, rectangle.getHeight() / 2 / Constants.PPM);

                    Fixture fixture = createSensorFixture(shape, body);
                    shape.dispose();
                    //if an npc has already been created, simply update the body with the new body, fixture, and world. otherwise, make an entirely new object.
                    if(dialogue.getRose() != null){
                        Rose rose = dialogue.getRose();
                        rose.setBody(body);
                        rose.setWorld(gameScreen.getWorld());
                        rose.setFixture(fixture);
                    }else{
                        dialogue.setRose(new Rose(gameScreen.getWorld(), body, fixture, gameScreen));
                    }
                }
            }
        }
    }

    /**
     * Creates the floors, walls, and ceilings from polygon map objects.
     * @param polygonMapObject Polygon object to turn into map bound.
     */
    private void createMapBounds(PolygonMapObject polygonMapObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    /**
     * Creates a body for an object.
     * @param rectangle Rectangle to turn into a body.
     */
    private Body createBody(Rectangle rectangle){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set((rectangle.getX() / Constants.PPM) + (rectangle.getWidth() / 2 / Constants.PPM), (rectangle.getY() / Constants.PPM) + (rectangle.getHeight() / 2 / Constants.PPM));
        bodyDef.fixedRotation = true;
        return gameScreen.getWorld().createBody(bodyDef);
    }

    /**
     * Creates a fixture that is used as a sensor rather than a collider.
     * @param shape Shape to create the fixture from.
     * @param body Body to apply the fixture to.
     */
    private Fixture createSensorFixture(PolygonShape shape, Body body){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.isSensor = true;
        return body.createFixture(fixtureDef);
    }

    /**
     * Turns polygon object into polygon shape.
     * @param polygonMapObject Polygon map object to convert.
     * @return Returns new shape created from polygon map object.
     */
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
