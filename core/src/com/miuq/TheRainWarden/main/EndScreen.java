package com.miuq.TheRainWarden.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.menu.MainMenu;

/**
 * Final screen that is displayed at the end of the game.
 * @author Luqman Patel
 */
public class EndScreen extends ScreenAdapter{
    /**
     * Stores new main menu object to return player to main menu after game is completed.
     */
    private MainMenu menu;
    /**
     * Stores game object.
     */
    private TheRainWarden game;
    /**
     * Stores spritebatch used to draw images.
     */
    private SpriteBatch batch;

    /*
     * Stage used to add button.
     */
    private Stage stage;
    //button assets
    private ImageButton continueButton;
    private Drawable continueButtonUp;
    private Drawable continueButtonHover;
    private ImageButtonStyle continueButtonStyle;
    //text asset
    private Texture endTexture;
    
    
    public EndScreen(OrthographicCamera camera, FitViewport viewport, TheRainWarden game){
        this.menu = new MainMenu(camera, viewport, game, true);
        this.game = game;
        this.batch = new SpriteBatch();
        
        this.continueButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/continueButton/continue.png")));
        this.continueButtonHover = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/continueButton/continueHover.png")));
        this.continueButtonStyle = new ImageButtonStyle();
        this.continueButtonStyle.up = continueButtonUp;
        this.continueButtonStyle.over = continueButtonHover;
        this.continueButton = new ImageButton(continueButtonStyle);
        this.stage = new Stage();
        this.stage.addActor(continueButton);
        this.continueButton.center();
        this.continueButton.setPosition(Gdx.graphics.getWidth()/2 - 450, 50);
        this.continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                handleContinueClick();
            }  
        });

        this.endTexture = new Texture("cutscene/end/thanks.png");
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Code to be executed when the continue button is clicked.
     * @author Luqman Patel
     */
    private void handleContinueClick(){
        game.setScreen(menu);
        Gdx.input.setInputProcessor(menu.getStage());
        this.dispose();
    }
    
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(endTexture, Gdx.graphics.getWidth() / 2 - 601, Gdx.graphics.getHeight() / 2 - 127);
        batch.end();

        stage.act(delta);
        stage.draw();
    }
}
