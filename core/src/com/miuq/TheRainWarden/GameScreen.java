package com.miuq.TheRainWarden;

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
import com.miuq.animation.AnimationRenderer;
import com.miuq.helper.Constants;
import com.miuq.helper.TileMapHelper;
import com.miuq.helper.WorldContactListener;
import com.miuq.objects.entities.Player;
import com.miuq.objects.gameObjects.CameraSwitchTrigger;
import com.miuq.objects.gameObjects.Mango;
import com.miuq.objects.gameObjects.ShinyRaindrop;
import com.miuq.objects.gameObjects.Spike;
import com.miuq.objects.gameObjects.WindCurrent;

/**
 * The GameScreen class holds, combines, and manages all logic for the game when it is being played. It also uses helper classes to 
 * draw required assets.
 * @author Luqman Patel
 */
public class GameScreen extends ScreenAdapter{
    //Game Neccesities
    /**
     * Used to indicate that the game is in the run state.
     */
    private final int GAME_RUNNING = 1;

    /**
     * Used to indicate that the game is in the paused state.
     */
    private final int GAME_PAUSED = 2;

    /**
     * Keeps track of the current state of the game.
     */
    private int gameState;

    /**
     * Camera that the gameScreen uses.
     */
    private OrthographicCamera camera;

    /**
     * Viewport that the game uses.
     */
    private FitViewport viewport;

    /**
     * SpriteBatch that the game uses to draw assets.
     */
    private SpriteBatch batch;

    /**
     * Keeps track of all positions that the camera will switch to during the game.
     */
    private Array<Vector3> cameraPositions;

    /**
     * Keeps track of all positions that the player will need to be moved to, from either starting a level or dying.
     */
    private Array<Vector2> startPositions;

    /**
     * Keeps track of the current level that the player is on.
     */
    private int level;
    
    /**
     * World that the game uses.
     */
    private World world;

    /**
     * Game object used to switch screens.
     */
    private TheRainWarden game;

    /**
     * Used for debugging (draws collision boxes).
     */
    private Box2DDebugRenderer box2dDebugRenderer;

    /**
     * Draws tile map.
     */
    private OrthogonalTiledMapRenderer orthoganalTiledMapRenderer;

    /**
     * Helper class used to parse tile map from tile map file.
     */
    private TileMapHelper tileMapHelper;

    //Game Objects
    /**
     * Player object.
     */
    private Player player;
    /**
     * Keeps all shiny raindrop objects.
     */
    private Array<ShinyRaindrop> shinyRaindrops;
    /**
     * Keeps all destroyed shiny raindrop objects so that they can be drawn again after the player dies.
     */
    private Array<ShinyRaindrop> destroyedRaindrops;
    /**
     * Keeps all mango objects.
     */
    private Array<Mango> mangos;
    /**
     * Keeps all camera switches.
     */
    private Array<CameraSwitchTrigger> cameraSwitches;
    /**
     * Keeps all spikes.
     */
    private Array<Spike> spikes;
    /**
     * Keeps all wind currents.
     */
    private Array<WindCurrent> windCurrents;
    /**
     * Keeps track of how many mangos have been collected by the player.
     */
    private int mangosCollected;
    /**
     * Image drawn when the game is paused.
     */
    private Texture pauseImage;
    
    /**
     * Helper class used to draw all animations.
     */
    private AnimationRenderer animationRenderer;
    
    public GameScreen(OrthographicCamera camera, FitViewport viewport2, TheRainWarden game){
        this.camera = camera;
        this.viewport = viewport2;
        //camera positions
        this.cameraPositions = new Array<Vector3>();
        //tutorial positions-------------------------------------
        this.cameraPositions.add(new Vector3(1000, 735, 0)); //level 1
        this.cameraPositions.add(new Vector3(3005, 735, 0)); //level 2
        //world 1 positions-------------------------------------
        
        //spawn positions
        this.startPositions = new Array<Vector2>();
        //tutorial positions-------------------------------------
        this.startPositions.add(new Vector2(256 / Constants.PPM, 704 / Constants.PPM)); //level 1
        this.startPositions.add(new Vector2(2050 / Constants.PPM, 450 / Constants.PPM)); //level 2
        //world 1 positions-------------------------------------

        this.pauseImage = new Texture("assets/screens/pauseScreen.png");
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -35), false);
        this.game = game;
        this.gameState = GAME_RUNNING;
        
        this.box2dDebugRenderer = new Box2DDebugRenderer();

        //objects
        this.shinyRaindrops = new Array<ShinyRaindrop>();
        this.destroyedRaindrops = new Array<ShinyRaindrop>();
        this.mangos = new Array<Mango>();
        this.cameraSwitches = new Array<CameraSwitchTrigger>();
        this.spikes = new Array<Spike>();
        this.windCurrents = new Array<WindCurrent>();

        this.mangosCollected = 0;
        this.level = 0;

        this.tileMapHelper = new TileMapHelper(this);
        this.orthoganalTiledMapRenderer = tileMapHelper.setupMap("assets/maps/TutorialMap.tmx");

        this.animationRenderer = new AnimationRenderer(player, batch);

        world.setContactListener(new WorldContactListener());
        Gdx.graphics.setSystemCursor(SystemCursor.None);
    }


    /**
     * Updates all states of the game while the gameScreen is the active screen.
     * Uses a switch statement to check which screen is currently active, and then executes that screen's
     * update method respectively.
     * @author Luqman Patel
     */
    private void update(){
        switch(gameState){
            case GAME_RUNNING:
                updateRunning();
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
        }
    }

    /**
     * Updates the running state of the game.
     * @author Luqman Patel
     */
    private void updateRunning(){
        world.step(1/60f, 6, 2);
        cameraUpdate();

        orthoganalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        
        if(player.isDead()){
            if(animationRenderer.isDeathAnimationFinished()){
                respawnPlayer();
            }
        }

        player.update();

        updateRaindrops();
        updateMangos();
        updateCameraSwitches();
        updateSpikes();
        
        animationRenderer.clearRaindrops();

        for(ShinyRaindrop shinyRaindrop: shinyRaindrops){
            animationRenderer.addRaindrops(shinyRaindrop);
        }

        animationRenderer.clearMangos();

        for(Mango mango: mangos){
            animationRenderer.addMangos(mango);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            gameState = GAME_PAUSED;
        }
    }

    /**
     * Updates the paused state of the game.
     * @author Luqman Patel
     */
    private void updatePaused(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            gameState = GAME_RUNNING;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            game.setScreen(new MenuScreen(camera, viewport, game));
        }
    }

    /**
     * Updates the camera's position, ortho, and the viewport.
     * @author Luqman Patel
     */
    private void cameraUpdate(){
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(cameraPositions.get(level));
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    /**
     * Moves the player to the current level's starting position as specified in the startPositions arrayList.
     * @author Luqman Patel
     */
    public void respawnPlayer(){
        player.body.setTransform(startPositions.get(level), player.body.getAngle()); 
        player.respawn();

        for(ShinyRaindrop shinyRaindrop : destroyedRaindrops){
            shinyRaindrop.respawn();
            shinyRaindrops.add(shinyRaindrop);
        }
    }

    //setters
    /**
     * Sets the player.
     * @param player The player object that the gameScreen will use as the player.
     * @author Luqman Patel
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    private void setMap(String mapPath){
        orthoganalTiledMapRenderer = tileMapHelper.setupMap(mapPath);
    }

    //getters
    public int getMangoCount(){
        return mangosCollected;
    }

    public World getWorld(){
        return world;
    }

    public Player getPlayer(){
        return player;
    }

    //adders
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

    /**
     * Loops through each shinyRaindrop in the shinyRaindrop arrayList and calls their update methods. Also removes
     * shinyRaindrops from the array that have been removed from the world.
     * @author Luqman Patel
     */
    private void updateRaindrops(){
        for(int i = 0; i < shinyRaindrops.size; i++){
            ShinyRaindrop shinyRaindrop = shinyRaindrops.get(i);
            if(shinyRaindrop.isRemoved()){
                shinyRaindrops.removeIndex(i);
                animationRenderer.destroyedRaindropAnimation(shinyRaindrop);
                destroyedRaindrops.add(shinyRaindrop);
            }
            shinyRaindrop.update();
        }
    }

    /**
     * Loops through each cameraSwitch in the cameraSwitches arrayList and calls their update methods. Also removes
     * mangos from the array that have been removed from the world.
     * @author Luqman Patel
     */
    private void updateMangos(){
        for(int i = 0; i < mangos.size; i++){
            Mango mango = mangos.get(i);
            if(mango.isRemoved()){
                mangos.removeIndex(i);
                mangosCollected++;
            }
            mango.update();
        }
    }

    /**
     * Loops through each cameraSwitch in the cameraSwitches arrayList and calls their update methods. If a cameraSwitch has 
     * been touched, it increases the level counter by 1 and moves the player to the next levels starting position.
     * @author Luqman Patel
     */
    private void updateCameraSwitches(){
        for(int i = 0; i < cameraSwitches.size; i++){
            CameraSwitchTrigger cameraSwitch = cameraSwitches.get(i);
            if(cameraSwitch.isRemoved()){
                cameraSwitches.removeIndex(i);
                level++;
                player.body.setTransform(startPositions.get(level), player.body.getAngle());
            }
            cameraSwitch.update();
        }
    }

    /**
     * Loops through each spike and calls its update method.
     * @author Luqman Patel
     */
    private void updateSpikes(){
        for(Spike spike: spikes){
            spike.update();
        }
    }

    /**
     * Main draw function of the entire game. Responsible for drawing all sprites seen on screen. Uses a switch
     * statement to check which game state is active, and draws the assets required for that state.
     * @param delta Time in seconds since last render.
     * @author Luqman Patel
     */
    private void draw(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthoganalTiledMapRenderer.render();
        batch.begin();

        switch(gameState){
            case GAME_RUNNING:
                drawRunning(delta);
                break;
            case GAME_PAUSED:
                drawPaused();
                break;
        }   

        batch.end();
        
        box2dDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }

    /**
     * Draws all assets required for the pause screen.
     * @author Luqman Patel
     */
    private void drawPaused(){
        Vector3 position = cameraPositions.get(level);
        float x = position.x;
        float y = position.y;
        batch.draw(pauseImage, x - 600, y - 300);
    }

    /**
     * Draws all assets required for the game screen that are not drawn by the tilemap.
     * @author Luqman Patel
     */
    private void drawRunning(float delta){
        animationRenderer.drawAnimations(delta);
    }

    @Override
    public void render(float delta){
        this.update();
        this.draw(delta);
    }
}