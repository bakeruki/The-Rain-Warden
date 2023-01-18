package com.miuq.TheRainWarden.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.animation.AnimationRenderer;
import com.miuq.TheRainWarden.helper.Constants;
import com.miuq.TheRainWarden.helper.GameCutsceneLoader;
import com.miuq.TheRainWarden.helper.GameDialogueHandler;
import com.miuq.TheRainWarden.helper.GameOptionsHandler;
import com.miuq.TheRainWarden.helper.GameSaveHandler;
import com.miuq.TheRainWarden.helper.TileMapHelper;
import com.miuq.TheRainWarden.helper.WorldContactListener;
import com.miuq.TheRainWarden.menu.MainMenu;
import com.miuq.TheRainWarden.objects.entities.Player;
import com.miuq.TheRainWarden.objects.gameObjects.CameraSwitchTrigger;
import com.miuq.TheRainWarden.objects.gameObjects.Mango;
import com.miuq.TheRainWarden.objects.gameObjects.ShinyRaindrop;
import com.miuq.TheRainWarden.objects.gameObjects.Spike;
import com.miuq.TheRainWarden.objects.gameObjects.WindCurrent;

/**
 * The GameScreen class holds, combines, and manages all logic for the game when 
 * it is being played. It also uses helper classes to draw required assets.
 * @author Luqman Patel
 */
public class GameScreen extends ScreenAdapter{
    //Game Neccesities
    /**
     * Used to indicate that the game is in the run state.
     */
    public final int GAME_RUNNING = 1;

    /**
     * Used to indicate that the game is in a dialogue event.
     */
    public final int GAME_DIALOGUE = 2;

    /**
     * Used to indicate that the game is in the paused state.
     */
    public final int GAME_PAUSED = 0;

    /**
     * Keeps track of the current state of the game.
     */
    private int gameState;

    /**
     * Camera that the gameScreen uses.
     */
    private OrthographicCamera camera;

    /**
     * Tells the game if a new map is currently being loaded.
     */
    private boolean switchingLevels;

    /**
     * Keeps track of all map file paths.
     */
    private Array<String> mapPaths;

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
     * Used to check if the level has been switched.
     */
    private int tempLevel;
    
    /**
     * Keeps track of the selected save file's number.
     */
    private int saveNum;

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

    /**
     * Helper class that holds the games options settings.
     */
    private GameOptionsHandler options;

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
    /**
     * Helper class used to control all cutscene logic.
     */
    private GameCutsceneLoader cutscene;

    /**
     * Helper class used to save the game data to a text file.
     */
    private GameSaveHandler gameSaveHandler;

    private GameDialogueHandler dialogue;

    private BitmapFont font;

    private boolean godMode;

    private Stage dialogueStage;

    private Drawable dialogueButtonDrawable;
    private ImageButtonStyle dialogueButtonStyle;
    private ImageButton dialogueContinueButton;
    
    public GameScreen(OrthographicCamera camera, FitViewport viewport2, TheRainWarden game, int level, int mangosCollected){
        this.camera = camera;
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.viewport = viewport2;
        this.switchingLevels = false;
        this.mapPaths = new Array<String>();
        //all map file paths
        this.mapPaths.add("maps/TutorialMap.tmx"); //world 0
        this.mapPaths.add("maps/SnowStage.tmx"); //world 1
        this.mapPaths.add("maps/ForestStage.tmx"); //world 2
        this.mapPaths.add("maps/SpringkeepStage.tmx"); //world 3

        //camera positions
        this.cameraPositions = new Array<Vector3>();
        //tutorial positions------------------------------------
        this.cameraPositions.add(new Vector3(1000, 735, 0)); //level 0
        this.cameraPositions.add(new Vector3(3005, 735, 0)); //level 1
        //world 1 positions-------------------------------------
        this.cameraPositions.add(new Vector3(1000, 735, 0)); //level 2
        this.cameraPositions.add(new Vector3(3080, 735, 0)); //level 3
        this.cameraPositions.add(new Vector3(5080, 735, 0)); //level 4
        //world 2 positions-------------------------------------
        this.cameraPositions.add(new Vector3(1000, 735,0)); //level 5
        this.cameraPositions.add(new Vector3(3005, 735, 0));//level 6
        this.cameraPositions.add(new Vector3(4995, 735, 0));//level 7
        //world 3 positions-------------------------------------
        this.cameraPositions.add(new Vector3(960, 735,0)); //level 8
        this.cameraPositions.add(new Vector3(3008, 735, 0));//level 9
        this.cameraPositions.add(new Vector3(5056, 735, 0));//level 10

        //spawn positions
        this.startPositions = new Array<Vector2>();
        //tutorial positions------------------------------------
        this.startPositions.add(new Vector2(256 / Constants.PPM, 704 / Constants.PPM)); //level 0
        this.startPositions.add(new Vector2(2050 / Constants.PPM, 450 / Constants.PPM)); //level 1
        //world 1 positions-------------------------------------
        this.startPositions.add(new Vector2(200 / Constants.PPM, 500 / Constants.PPM)); //level 2
        this.startPositions.add(new Vector2(2136 / Constants.PPM, 800 / Constants.PPM)); //level 3
        this.startPositions.add(new Vector2(4142 / Constants.PPM, 800 / Constants.PPM)); //level 4
        //world 2 positions-------------------------------------
        this.startPositions.add(new Vector2(200 / Constants.PPM, 500 / Constants.PPM));//level 5
        this.startPositions.add(new Vector2(2300 / Constants.PPM, 400 / Constants.PPM));//level 6
        this.startPositions.add(new Vector2(4142 / Constants.PPM, 1100 / Constants.PPM));//level 7
        //world 3 positions-------------------------------------
        this.startPositions.add(new Vector2(256 / Constants.PPM, 400 / Constants.PPM));//level 8
        this.startPositions.add(new Vector2(2300 / Constants.PPM, 400 / Constants.PPM));//level 9
        this.startPositions.add(new Vector2(4142 / Constants.PPM, 400 / Constants.PPM));//level 10

        this.pauseImage = new Texture("screens/pauseScreen.png");
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -35), false);
        this.game = game;
        this.gameState = GAME_RUNNING;
        this.font = new BitmapFont();
        
        this.box2dDebugRenderer = new Box2DDebugRenderer();

        //objects
        this.shinyRaindrops = new Array<ShinyRaindrop>();
        this.destroyedRaindrops = new Array<ShinyRaindrop>();
        this.mangos = new Array<Mango>();
        this.cameraSwitches = new Array<CameraSwitchTrigger>();
        this.spikes = new Array<Spike>();
        this.windCurrents = new Array<WindCurrent>();

        this.mangosCollected = mangosCollected;
        this.level = level;

        this.gameSaveHandler = new GameSaveHandler();

        this.options = new GameOptionsHandler();

        this.dialogue = new GameDialogueHandler(this);

        this.cutscene = new GameCutsceneLoader(camera, viewport2, game);
        this.cutscene.recentlyLoadedSave();

        if(options.cutscenesDisabled()){
            cutscene.disableCutscene();
        }

        if(options.godEnabled()){
            godMode = true;
        }else{
            godMode = false;
        }

        this.dialogueStage = new Stage();
        this.dialogueButtonDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("dialogue/border.png")));
        this.dialogueButtonStyle = new ImageButtonStyle();
        this.dialogueButtonStyle.up = dialogueButtonDrawable;
        this.dialogueContinueButton = new ImageButton(dialogueButtonStyle);
        this.dialogueStage.addActor(dialogueContinueButton);
        this.dialogueContinueButton.setPosition(Gdx.graphics.getWidth()/2 - 600, 750);
        this.dialogueContinueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                dialogue.nextFrame();
            }
        });

        this.tileMapHelper = new TileMapHelper(this, dialogue);
        initializeMap(level);

        this.animationRenderer = new AnimationRenderer(player, batch, this);
        this.animationRenderer.setMimir(dialogue.getMimir());

        world.setContactListener(new WorldContactListener());

        updateAllObjectClasses(player, world);
    }

    public void setSaveNum(int saveNum){
        this.saveNum = saveNum;
        this.player.setDeathCounter(gameSaveHandler.getDeathsFromSave(saveNum));
        this.dialogue.setDialogueCount(gameSaveHandler.getDialogueNumFromSave(saveNum));
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
            case GAME_DIALOGUE:
                updateDialogue();
                break;
        }
    }

    private void updateDialogue(){
        if(dialogue.isDialogueEventFinished()){
            gameState = GAME_RUNNING;
        }
        Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
    }

    /**
     * Updates the running state of the game.
     * @author Luqman Patel
     */
    private void updateRunning(){
        world.step(1/60f, 6, 2);
        Gdx.graphics.setSystemCursor(SystemCursor.None);

        checkLevelSwitch();
        cameraUpdate();

        orthoganalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        
        if(player.isDead()){
            if(animationRenderer.isDeathAnimationFinished()){
                respawnPlayer();
            }
        }

        player.update();
        updateGameObjects();
        
        loadMap(level);
        levelSpecificTasks(level);
        animationRenderer.setMimir(dialogue.getMimir());
        cutscene.update(level, this);
        dialogue.update();

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
            gameSaveHandler.updateSaveFiles(saveNum, level, mangosCollected, player.getDeathCounter(), dialogue.getDialogueCount());
            game.setScreen(new MainMenu(camera, viewport, game, true));
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
    private void respawnPlayer(){
        player.body.setTransform(startPositions.get(level), player.body.getAngle()); 
        player.respawn();

        for(ShinyRaindrop shinyRaindrop : destroyedRaindrops){
            shinyRaindrop.respawn();
            shinyRaindrops.add(shinyRaindrop);
        }
    }

    private void checkLevelSwitch(){
        if(tempLevel != level){
            dialogue.enableDialogueEvent();
            System.out.println("level swtich");
            tempLevel = level;
        }
    }

    /**
     * This function handles all level specific tasks (events that will be triggered based on the level
     * that the player is on).
     * @author Luqman Patel
     */
    private void levelSpecificTasks(int level){
        if(godMode){
            player.unlockDash();
            return;
        }
        
        if(level >= 4){
            player.unlockDash();
        }

        if(level >= 8 && level <= 10){
            player.disableJump();
        }else{
            player.enableJump();
        }
    }

    private void initializeMap(int level){
        if(level != 0){
            if(level < 2){
                this.orthoganalTiledMapRenderer = tileMapHelper.setupMap(mapPaths.get(0));
            }else if(level < 5){
                this.orthoganalTiledMapRenderer = tileMapHelper.setupMap(mapPaths.get(1));
            }else if(level < 8){
                this.orthoganalTiledMapRenderer = tileMapHelper.setupMap(mapPaths.get(2));
            }else if(level < 11){
                this.orthoganalTiledMapRenderer = tileMapHelper.setupMap(mapPaths.get(3));
            }
            player.body.setTransform(startPositions.get(level), player.body.getAngle()); 
            this.camera.position.set(cameraPositions.get(level));
        }else{
            this.orthoganalTiledMapRenderer = tileMapHelper.setupMap(mapPaths.get(0));
        }
    }

    /**
     * Loads the map of each world based on the current level.
     * @param level The current level
     * @author Luqman Patel
     */
    private void loadMap(int level){
        if(switchingLevels){
            System.out.println("switching levels");
            if(level < 2){
                System.out.println("switching to tutorial world");
                setMap(mapPaths.get(0));
                switchingLevels = false;
            }else if(level < 5){
                System.out.println("switching to ice world");
                setMap(mapPaths.get(1));
                switchingLevels = false;
            }else if(level < 8){
                System.out.println("switching to forest world");
                setMap(mapPaths.get(2));
                switchingLevels = false;   
            }else if(level < 11){
                System.out.println("switching to springkeep");
                setMap(mapPaths.get(3));
                switchingLevels = false;
            }
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

    /**
     * Disposes the current world and tilemap and replaces them with a new world and tilemap.
     * Note: this method will result in a new player object! ensure that all objects are updated
     * with the most recent player, or collisions wont work!!
     * @param mapPath The file handle of the new map to be loaded.
     * @author Luqman Patel
     */
    private void setMap(String mapPath){
        clearObjectArrays();
        world.dispose();
        world = new World(new Vector2(0, -35), false);

        orthoganalTiledMapRenderer.dispose();

        int deaths = player.getDeathCounter();

        orthoganalTiledMapRenderer = tileMapHelper.setupMap(mapPath);

        player.setDeathCounter(deaths);
    
        world.setContactListener(new WorldContactListener());
        updateAllObjectClasses(player, world);

        dialogue.enableDialogueEvent();
    }

    public void setGameState(int gameState){
        this.gameState = gameState;
    }

    /**
     * Updates all object classes with the most recent variables. Used when new map is loaded.
     * @param player The current player object.
     * @param world The current world object.
     * @author Luqman Patel
     */
    public void updateAllObjectClasses(Player player, World world){
        animationRenderer.setPlayer(player);

        for(Spike spike : spikes){
            spike.setPlayer(player);
        }
    }

    //getters
    /**
     * Returns number of mangos collected.
     * @return Number of mangos collected.
     * @author Luqman Patel
     */
    public int getMangoCount(){
        return mangosCollected;
    }

    /**
     * Returns the current world.
     * @return The current world object.
     * @author Luqman Patel
     */
    public World getWorld(){
        return world;
    }

    public int getLevel(){
        return level;
    }

    /**
     * Returns the current player.
     * @return The current player object.
     * @author Luqman Patel
     */
    public Player getPlayer(){
        return player;
    }

    //adders
    /**
     * Adds cameraSwitch objects to an arrayList.
     * @param cameraSwitch Camera switch to add.
     * @author Luqman Patel
     */
    public void addCameraSwitch(CameraSwitchTrigger cameraSwitch){
        cameraSwitches.add(cameraSwitch);
    }

    /**
     * Adds mango objects to an arrayList.
     * @param mango Mango to add.
     * @author Luqman Patel
     */
    public void addMango(Mango mango){
        mangos.add(mango);
    }

    /**
     * Adds shiny raindrop objects to an arrayList.
     * @param raindrop Raindrop to add.
     * @author Luqman Patel
     */
    public void addShinyRaindrop(ShinyRaindrop raindrop){
        shinyRaindrops.add(raindrop);
    }

    /**
     * Adds spike objects to an arrayList.
     * @param spike Spike to add.
     * @author Luqman Patel
     */
    public void addSpike(Spike spike){
        spikes.add(spike);
    }

    /**
     * Adds wind current objects to an arrayList.
     * @param windCurrent Wind current to add.
     * @author Luqman Patel
     */
    public void addWindCurrents(WindCurrent windCurrent){
        windCurrents.add(windCurrent);
    }

    /**
     * Updates all game objects in the world.
     */
    public void updateGameObjects(){
        animationRenderer.updateGameObjects(shinyRaindrops, mangos);
        updateRaindrops();
        updateMangos();
        updateCameraSwitches();
        updateSpikes();
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
                checkWorldSwitch(level);
                player.body.setTransform(startPositions.get(level), player.body.getAngle());
            }
            cameraSwitch.update();
        }
    }

    /**
     * Checks whether it is time to switch maps (using this function as a buffer
     * ensures that the game will not try to switch worlds multiple times in the 
     * update methods)
     * @param level The current level.
     */
    private void checkWorldSwitch(int level){
        switch(level){
            case 2:
                switchingLevels = true;
                break;
            case 5:
                switchingLevels = true;
                break;
            case 8:
                switchingLevels = true;
                break;
            default:
                switchingLevels = false;
                break;
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
            case GAME_DIALOGUE:
                drawDialogue(delta);
                break;
        }   

        batch.end();
        
        box2dDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }

    private void drawDialogue(float delta){
        Gdx.input.setInputProcessor(dialogueStage);
        dialogueStage.act();
        dialogueStage.draw();
        dialogue.draw(delta, batch);
        float x = startPositions.get(level).x + 64;
        float y = startPositions.get(level).y + 150;
        animationRenderer.drawDialogueAnimations(delta, x, y);
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

    private void clearObjectArrays(){
        shinyRaindrops.clear();
        destroyedRaindrops.clear();
        mangos.clear();
        cameraSwitches.clear();
        spikes.clear();
    }

    /**
     * Draws all assets required for the game screen that are not drawn by the tilemap.
     * @author Luqman Patel
     */
    private void drawRunning(float delta){
        animationRenderer.drawAnimations(delta);
        levelSpecificDraw();

        if(options.showDeathCounter()){
            Vector3 position = cameraPositions.get(level);
            float x = position.x;
            float y = position.y;
            font.getData().setScale(2f);
            font.setColor(255, 255, 255, 1f);
            font.draw(batch, "Retries: " + player.getDeathCounter(), x + 820, y + 515);
        }
    }

    private void levelSpecificDraw(){
        if(level == 0){
            batch.draw(new Texture("tutorial/WASD.png"), 250, 500);
        }
    }

    @Override
    public void render(float delta){
        this.update();
        this.draw(delta);
    }
}