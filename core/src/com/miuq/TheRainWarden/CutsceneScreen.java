package com.miuq.TheRainWarden;

import java.util.ArrayList;

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

public class CutsceneScreen extends ScreenAdapter{
    private Stage stage;
    private SpriteBatch batch;
    private GameScreen gameScreen;
    private CutsceneScreen cutsceneScreen;
    private TheRainWarden game;

    private ArrayList<Texture> cutsceneImages;
    private int frame;

    private ImageButton continueButton;
    private Drawable continueButtonUp;
    private Drawable continueButtonHover;
    private ImageButtonStyle continueButtonStyle;
    
    public CutsceneScreen(OrthographicCamera camera, FitViewport viewport, TheRainWarden game){
        this.game = game;
        camera.setToOrtho(false, 0, 0);

        this.stage = new Stage(viewport);
        this.batch = new SpriteBatch();
        this.gameScreen = new GameScreen(camera, viewport, game);
        this.cutsceneScreen = this;

        this.cutsceneImages = new ArrayList<Texture>();
        this.frame = 0;

        //TODO add cutscene continue button textures
        this.continueButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture(""))); 
        this.continueButtonHover = new TextureRegionDrawable(new TextureRegion(new Texture("")));
        this.continueButtonStyle = new ImageButtonStyle();
        this.continueButtonStyle.up = continueButtonUp;
        this.continueButtonStyle.over = continueButtonHover;
        this.continueButton = new ImageButton(continueButtonStyle);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(cutsceneImages.get(frame), 0, 0);
        batch.end();

        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                frame++;
            }
        });

        if(frame > cutsceneImages.size()){
            game.setScreen(gameScreen);
            cutsceneScreen.dispose();
        }
    }
}