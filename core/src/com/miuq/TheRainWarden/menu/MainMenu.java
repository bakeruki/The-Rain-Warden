package com.miuq.TheRainWarden.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.main.TheRainWarden;

/**
 * This class holds the main menu that the player sees when they first start the game.
 * The main menu screen gives access to all other menus that are used to configure
 * and play the game.
 * @author Michelle Vuong
 */
public class MainMenu extends ScreenAdapter{
    /**
     * Stores game object use to switch screens.
     */
    private TheRainWarden game;

    //button assets
    private ImageButton startButton;
    private ImageButton optionsButton;
    private ImageButton exitButton;

    //button assets
    private Drawable startDownDrawable;
    private Drawable startDrawable;
    private Drawable optionsDrawable;
    private Drawable optionsDownDrawable;
    private Drawable exitDownDrawable;
    private Drawable exitDrawable;

    //button assets
    private ImageButtonStyle startStyle;
    private ImageButtonStyle optionsStyle;
    private ImageButtonStyle exitStyle;

    //backdrop of menu
    private Image backgroundImage;

    /**
     * Stage used to add interactivity to buttons
     */
    private Stage stage;
    /**
     * Holds current main menu.
     */
    private MainMenu menuScreen;
    /**
     * Holds options menu.
     */
    private OptionsMenu optionsMenu;
    /*
     * Holds start menu.
     */
    private StartMenu startMenu;

    public MainMenu(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, boolean doFade){
        camera.setToOrtho(false, 0, 0);

        //initialize button assets
        this.startDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/startButton/start.png")));
        this.startDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/startButton/startDown.png")));
        this.startStyle = new ImageButtonStyle();
        this.startStyle.up = startDrawable;
        this.startStyle.over = startDownDrawable;
        this.startButton = new ImageButton(startStyle);
        this.startButton.setPosition(870, 300);

        this.optionsDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/optionsButton/optionsButton.png")));
        this.optionsDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/optionsButton/optionsButtonDown.png")));
        this.optionsStyle = new ImageButtonStyle();
        this.optionsStyle.up = optionsDrawable;
        this.optionsStyle.over = optionsDownDrawable;
        this.optionsButton = new ImageButton(optionsStyle);
        this.optionsButton.setPosition(1720, 20);

        this.exitDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/exitButton/exitButton.png")));
        this.exitDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/exitButton/exitButtonDown.png")));
        this.exitStyle = new ImageButtonStyle();
        this.exitStyle.up = exitDrawable;
        this.exitStyle.over = exitDownDrawable;
        this.exitButton = new ImageButton(exitStyle);
        this.exitButton.setPosition(startButton.getX(), startButton.getY() - 150);

        this.backgroundImage = new Image(new Texture("screens/menuScreen.png"));

        this.game = game;
        this.menuScreen = this;
        this.startMenu = new StartMenu(game, camera, viewport);
        this.optionsMenu = new OptionsMenu(game, camera, viewport);

        this.stage = new Stage(viewport);

        this.stage.addActor(backgroundImage);
        this.stage.addActor(startButton); 
        this.stage.addActor(optionsButton);
        this.stage.addActor(exitButton);

        startButton.center();
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleStartClick();
            }
        });

        optionsButton.center();
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleOptionsClick();
            }
        });
        
        exitButton.center();
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleExitClick();
            }
        });

        /**
         * Causes the screen to fade in if the constructor tells it to.
         */
        if(doFade){
            this.stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
        }

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    /**
     * Executes code for when the start button is clicked.
     * @author Michelle Vuong
     */
    private void handleStartClick(){
        game.setScreen(startMenu);
        Gdx.input.setInputProcessor(startMenu.getStage());
        stage.dispose();
        menuScreen.dispose();
    }

    /**
     * Executes code for when the options button is clicked.
     * @author Michelle Vuong
     */
    private void handleOptionsClick(){
        game.setScreen(optionsMenu);
        Gdx.input.setInputProcessor(optionsMenu.getStage());
        stage.dispose();
        menuScreen.dispose();
    }

    /**
     * Executes code for when the exit button is clicked.
     * @author Michelle Vuong
     */
    private void handleExitClick(){
        Gdx.app.exit();
    }

    /**
     * Gets the stage of the menu screen.
     * @return Stage of menu screen.
     * @author Michelle Vuong
     */
    public Stage getStage(){
        return stage;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
}
