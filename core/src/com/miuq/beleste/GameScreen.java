package com.miuq.beleste;

import helper.Constants;
import helper.TileMapHelper;
import helper.WorldContactListener;
import objects.gameObjects.CameraSwitchTrigger;
import objects.gameObjects.Mango;
import objects.gameObjects.ShinyRaindrop;
import objects.gameObjects.Spike;
import objects.gameObjects.WindCurrent;
import objects.player.Player;
import animation.AnimationRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ScreenAdapter{
    private OrthographicCamera camera;
    private Array<Vector3> cameraPositions;
    private Array<Vector2> startPositions;
    private int level;
    private FitViewport viewport;
    private SpriteBatch batch;
    private World world;
    private Beleste game;
    private Texture backgroundImage;
    private Box2DDebugRenderer box2dDebugRenderer;

    private OrthogonalTiledMapRenderer orthoganalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    //objects
    private Player player;
    //these are public because they need to be accessed from the tilemaphelper to add each object from the tilemap
    private Array<ShinyRaindrop> shinyRaindrops;
    private Array<Mango> mangos;
    private Array<CameraSwitchTrigger> cameraSwitches;
    private Array<Spike> spikes;
    private Array<WindCurrent> windCurrents;
    private int mangosCollected;

    private AnimationRenderer animationRenderer;
    
    public GameScreen(OrthographicCamera camera, FitViewport viewport2, Beleste game){
        this.camera = camera;
        this.viewport = viewport2;
        //camera positions
        this.cameraPositions = new Array<Vector3>();
        this.cameraPositions.add(new Vector3(824,1149,0)); //level 1
        this.cameraPositions.add(new Vector3(2745, 1348, 0)); //level 2
        
        //spawn positions
        this.startPositions = new Array<Vector2>();
        this.startPositions.add(new Vector2(64 / Constants.PPM, 1664 / Constants.PPM)); //level 1
        this.startPositions.add(new Vector2(1810 / Constants.PPM, 1350 / Constants.PPM)); //level 2

        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25), false);
        this.game = game;
        
        this.box2dDebugRenderer = new Box2DDebugRenderer();

        //objects
        this.shinyRaindrops = new Array<ShinyRaindrop>();
        this.mangos = new Array<Mango>();
        this.cameraSwitches = new Array<CameraSwitchTrigger>();
        this.spikes = new Array<Spike>();
        this.windCurrents = new Array<WindCurrent>();

        this.mangosCollected = 0;
        this.level = 0;

        this.tileMapHelper = new TileMapHelper(this);
        this.orthoganalTiledMapRenderer = tileMapHelper.setupMap();
        this.backgroundImage = new Texture("maps/Background_Beleste.png");

        this.animationRenderer = new AnimationRenderer(player, batch);

        world.setContactListener(new WorldContactListener());
        Gdx.graphics.setSystemCursor(SystemCursor.None);
    }

    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();

        orthoganalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);

        if(player.isDead()){
            respawnPlayer();
        }

        player.update();

        for(int i = 0; i < shinyRaindrops.size; i++){
            ShinyRaindrop shinyRaindrop = shinyRaindrops.get(i);
            if(shinyRaindrop.isRemoved()){
                shinyRaindrops.removeIndex(i);
                animationRenderer.destroyedRaindropAnimation(shinyRaindrop);
            }
            shinyRaindrop.update();
        }
        
        for(int i = 0; i < mangos.size; i++){
            Mango mango = mangos.get(i);
            if(mango.isRemoved()){
                mangos.removeIndex(i);
                mangosCollected++;
            }
            mango.update();
        }

        for(int i = 0; i < cameraSwitches.size; i++){
            CameraSwitchTrigger cameraSwitch = cameraSwitches.get(i);
            if(cameraSwitch.isRemoved()){
                cameraSwitches.removeIndex(i);
                level++;
                player.body.setTransform(startPositions.get(level), player.body.getAngle());
            }
            cameraSwitch.update();
        }

        for(Spike spike: spikes){
            spike.update();
        }
        
        animationRenderer.clearRaindrops();

        for(int i = 0; i < shinyRaindrops.size; i++){
            animationRenderer.addRaindrops(shinyRaindrops.get(i));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setScreen(new MenuScreen(camera, viewport, game));
        }
    }

    @Override
    public void render(float delta){
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0);

        animationRenderer.drawAnimations(delta);

        batch.end();

        orthoganalTiledMapRenderer.render();
        box2dDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
        
    }

    private void cameraUpdate(){
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(cameraPositions.get(level));
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    public void respawnPlayer(){
        player.body.setTransform(startPositions.get(level), player.body.getAngle()); 
        player.respawn();
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public int getMangoCount(){
        return mangosCollected;
    }

    public World getWorld(){
        return world;
    }

    public Player getPlayer(){
        return player;
    }

    public void addCameraSwitch(CameraSwitchTrigger cameraSwitch){
        cameraSwitches.add(cameraSwitch);
    }

    public void addMango(Mango mango){
        mangos.add(mango);
    }

    public void addShinyRaindrop(ShinyRaindrop raindrop){
        shinyRaindrops.add(raindrop);
    }

    public void addSpike(Spike spike){
        spikes.add(spike);
    }

    public void addWindCurrents(WindCurrent windCurrent){
        windCurrents.add(windCurrent);
    }
}