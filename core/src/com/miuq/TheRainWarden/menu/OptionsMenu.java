package com.miuq.TheRainWarden.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.helper.GameOptionsHandler;
import com.miuq.TheRainWarden.main.TheRainWarden;

/**
 * This class holds the option menu which allows the player to change 
 * certain settings that affect how the game runs.
 * @author Michelle Vuong
 */
public class OptionsMenu extends ScreenAdapter{
    /**
     * Holds the game used to switch screens.
     */
    private TheRainWarden game;
    /**
     * Holds the viewport of the game.
     */
    private FitViewport viewport;
    /**
     * Holds the camera of the game.
     */
    private OrthographicCamera camera;
    /**
     * Helper class used to load and save options so that they remain the same after the game is restarted.
     */
    private GameOptionsHandler options;
    /**
     * Batch used to draw textures.
     */
    private SpriteBatch batch;

    //initialize button assets
    private Drawable backButtonDrawable;
    private Drawable backButtonHoverDrawable;
    private Drawable speedrunButtonCheckedDrawable;
    private Drawable speedrunButtonUncheckedDrawable;
    private Drawable retryButtonCheckedDrawable;
    private Drawable retryButtonUncheckedDrawable;
    private Drawable godButtonCheckedDrawable;
    private Drawable godButtonUncheckedDrawable;

    private ImageButtonStyle backButtonStyle;
    private ImageButtonStyle speedrunButtonStyle;
    private ImageButtonStyle retryButtonStyle;
    private ImageButtonStyle godButtonStyle;
    private ImageButton backButton;
    private ImageButton speedrunButton;
    private ImageButton retryButton;
    private ImageButton godButton;

    private Texture speedrunDescription;
    private Texture retryDescription;
    private Texture godDescription;

    /**
     * Holds the stage for the options menu.
     */
    private Stage stage;

    public OptionsMenu(TheRainWarden game, OrthographicCamera camera, FitViewport viewport){
        this.game = game;
        this.viewport = viewport;
        this.camera = camera;
        this.options = new GameOptionsHandler();
        this.batch = new SpriteBatch();
        
        this.backButtonDrawable = new TextureRegionDrawable(new Texture("buttons/backSaveButton/backButton.png"));
        this.backButtonHoverDrawable = new TextureRegionDrawable(new Texture("buttons/backSaveButton/backButtonHover.png"));
        this.speedrunButtonCheckedDrawable = new TextureRegionDrawable(new Texture("buttons/speedrun/speedrunModeOn.png"));
        this.speedrunButtonUncheckedDrawable = new TextureRegionDrawable(new Texture("buttons/speedrun/speedrunModeOff.png"));
        this.retryButtonCheckedDrawable = new TextureRegionDrawable(new Texture("buttons/retry/retryCounterOn.png"));
        this.retryButtonUncheckedDrawable = new TextureRegionDrawable(new Texture("buttons/retry/retryCounterOff.png"));
        this.godButtonCheckedDrawable = new TextureRegionDrawable(new Texture("buttons/godmode/godModeOn.png"));
        this.godButtonUncheckedDrawable = new TextureRegionDrawable(new Texture("buttons/godmode/godModeOff.png"));

        this.backButtonStyle = new ImageButtonStyle();
        this.backButtonStyle.up = backButtonDrawable;
        this.backButtonStyle.over = backButtonHoverDrawable;

        this.speedrunButtonStyle = new ImageButtonStyle();
        this.speedrunButtonStyle.up = speedrunButtonUncheckedDrawable;
        this.retryButtonStyle = new ImageButtonStyle();
        this.retryButtonStyle.up = retryButtonUncheckedDrawable;
        this.godButtonStyle = new ImageButtonStyle();
        this.godButtonStyle.up = godButtonCheckedDrawable;

        this.backButton = new ImageButton(backButtonStyle);
        this.speedrunButton = new ImageButton(speedrunButtonStyle);
        this.retryButton = new ImageButton(retryButtonStyle);
        this.godButton = new ImageButton(godButtonStyle);

        this.speedrunDescription = new Texture("buttons/speedrun/speedrunDescription.png");
        this.retryDescription = new Texture("buttons/retry/retryCounterDescription.png");
        this.godDescription = new Texture("buttons/godmode/godModeDescription.png");

        this.stage = new Stage();
        this.stage.addActor(backButton);
        this.stage.addActor(speedrunButton);
        this.stage.addActor(retryButton);
        this.stage.addActor(godButton);

        this.backButton.center();
        this.backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleBackClick();
            }
        });

        this.speedrunButton.center();
        this.speedrunButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleSpeedrunClick();
            }
        });

        this.retryButton.center();
        this.retryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleRetryClick();
            }
        });
        
        this.godButton.center();
        this.godButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleGodModeClick();
            }
        });

        this.speedrunButton.setPosition(Gdx.graphics.getWidth() / 2 - 250, 900);
        this.retryButton.setPosition(Gdx.graphics.getWidth() / 2 - 250, 600);
        this.godButton.setPosition(Gdx.graphics.getWidth() / 2 - 250, 300);
        this.backButton.setPosition(Gdx.graphics.getWidth() / 2 - 170, 100);
        
    }

    /**
     * Code to be executed when the back button is clicked.
     * @author Michelle Vuong
     */
    private void handleBackClick(){
        MainMenu mainMenu = new MainMenu(camera, viewport, game, false);
        game.setScreen(mainMenu);
        Gdx.input.setInputProcessor(mainMenu.getStage());
        stage.dispose();
        batch.dispose();
        this.dispose();
    }

    /**
     * Code to be executed when the speedrun toggle button is clicked.
     * @author Michelle Vuong
     */
    private void handleSpeedrunClick(){
        options.updateOptions(0);
    }

    /**
     * Code to be executed when the retry toggle button is clicked.
     * @author Michelle Vuong
     */
    private void handleRetryClick(){
        options.updateOptions(1);
    }

    /**
     * Code to be executed when the godmode toggle button is clicked.
     * @author Michelle Vuong
     */
    private void handleGodModeClick(){
        options.updateOptions(2);
    }

    /**
     * Updates all buttons so that they display the correct image based on what the option
     * is currently set to.
     * @author Michelle Vuong
     */
    private void updateButtons(){
        if(options.cutscenesDisabled()){
            speedrunButtonStyle.up = speedrunButtonCheckedDrawable;
        }else{
            speedrunButtonStyle.up = speedrunButtonUncheckedDrawable;
        }

        if(options.showDeathCounter()){
            retryButtonStyle.up = retryButtonCheckedDrawable;
        }else{
            retryButtonStyle.up = retryButtonUncheckedDrawable;
        }

        if(options.godEnabled()){
            godButtonStyle.up = godButtonCheckedDrawable;
        }else{
            godButtonStyle.up = godButtonUncheckedDrawable;
        }
    }

    /**
     * Draws descriptions for all settings.
     * @author Michelle Vuong
     */
    private void drawDescriptions(){
        batch.draw(speedrunDescription, Gdx.graphics.getWidth() / 2 - 600, 850);
        batch.draw(retryDescription, Gdx.graphics.getWidth() / 2 - 600, 550);
        batch.draw(godDescription, Gdx.graphics.getWidth() / 2 - 600, 250);
    }

    /**
     * Gets the stage of the options menu.
     * @return Stage of options menu.
     * @author Michelle Vuong
     */
    public Stage getStage(){
        return stage;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        drawDescriptions();
        batch.end();

        updateButtons();
        stage.act(delta);
        stage.draw();
    }
}
