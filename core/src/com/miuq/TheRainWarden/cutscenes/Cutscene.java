package com.miuq.TheRainWarden.cutscenes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.animation.AnimatedText;

/**
 * Cutscene is a superclass used to create cutscenes viewed by the player.
 * The superclass handles all operations and logic of the cutscene, meaning
 * that actually creating a cutscene is as simple as adding the images to
 * the cutsceneImages and cutsceneTexts ArrayLists of the child class.
 * @author Luqman Patel
 */
public abstract class Cutscene extends ScreenAdapter {
    /**
     * Holds the game object used to switch screens.
     */
    private TheRainWarden game;
    /**
     * Holds the SpriteBatch used to draw images.
     */
    private SpriteBatch batch;
    /**
     * Holds the active gameScreen.
     */
    private GameScreen gameScreen;

    /**
     * Access by child classes to add Images for the cutscene.
     */
    protected ArrayList<Texture> cutsceneImages;
    /**
     * Access by child classes to add Text for the cutscene.
     */
    protected ArrayList<AnimatedText> cutsceneTexts;

    /**
     * Holds the font used to write text.
     */
    private BitmapFont font;
    /**
     * Holds the layout used to format text.
     */
    private GlyphLayout layout;

    /**
     * Checks whether the frame has been incremented (prevents frame from incrementing too quickly).
     */
    private boolean hasIncremented;
    /**
     * Holds the current frame that the cutscene is on.
     */
    protected int frame;

    /**
     * Holds the stage used to add the continue button.
     */
    private Stage stage;

    //these variables hold the continue button object and its drawings
    private ImageButton continueButton;
    private Drawable continueButtonUp;
    private Drawable continueButtonHover;
    private ImageButtonStyle continueButtonStyle;

    public Cutscene(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen){
        camera.setToOrtho(false, 0, 0);
        
        this.game = game;
        this.batch = new SpriteBatch();
        this.gameScreen = gameScreen;

        this.cutsceneImages = new ArrayList<Texture>();
        this.cutsceneTexts = new ArrayList<AnimatedText>();

        this.font = new BitmapFont();
        this.layout = new GlyphLayout();

        this.hasIncremented = false;
        this.frame = 0;
        this.stage = new Stage(viewport);

        this.continueButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/continueButton/continue.png")));
        this.continueButtonHover = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/continueButton/continueHover.png")));
        this.continueButtonStyle = new ImageButtonStyle();
        this.continueButtonStyle.up = continueButtonUp;
        this.continueButtonStyle.over = continueButtonHover;
        this.continueButton = new ImageButton(continueButtonStyle);
        this.continueButton.center();
        this.continueButton.setPosition(Gdx.graphics.getWidth()/2 - 450, 50);

        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleContinueClick();
            }
        });

        this.stage.addActor(continueButton); 
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Handles code to be executed when the continue button is clicked.
     * @author Luqman Patel
     */
    private void handleContinueClick(){
        if(!cutsceneTexts.get(frame).hasFinished()){
            cutsceneTexts.get(frame).skipAnimation();
        }else{
            if(!hasIncremented){
                frame++;
                hasIncremented = true;
            }
        }
    }

    /**
     * Handles all rendering for cutscene images and text. Put this method in the
     * child class render method to get the images and text specified to show up.
     * @param delta Time since last render.
     * @author Luqman Patel
     */
    protected void renderCutscene(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.getData().setScale(2f);
        updateCutscene(delta);
        batch.end();

        if(frame == cutsceneImages.size()){
            batch.dispose();
            stage.dispose();
            game.setScreen(gameScreen);
            this.dispose();
        }

        hasIncremented = false;

        stage.act(delta);
        stage.draw();
    }

    /**
     * Updates current image and text being displayed in cutscene.
     * @param delta Time since last render.
     * @author Luqman Patel
     */
    private void updateCutscene(float delta){
        if(frame < cutsceneImages.size()){
            batch.draw(cutsceneImages.get(frame), Gdx.graphics.getWidth() / 2 - 640, 250);
            layout.setText(font,cutsceneTexts.get(frame).getText(), Color.WHITE, 500, Align.center, true);
            font.draw(batch, layout, Gdx.graphics.getWidth()/2-250, 300);
            cutsceneTexts.get(frame).update(delta);
        }
    }

    /**
     * Gets the stage.
     * @return The stage object.
     * @author Luqman Patel
     */
    public Stage getStage(){
        return stage;
    }
}
