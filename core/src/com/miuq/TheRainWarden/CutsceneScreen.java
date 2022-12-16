package com.miuq.TheRainWarden;

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
import com.miuq.animation.AnimatedText;

public class CutsceneScreen extends ScreenAdapter{
    private SpriteBatch batch;
    private GameScreen gameScreen;
    private CutsceneScreen cutsceneScreen;
    private TheRainWarden game;

    private ArrayList<Texture> cutsceneImages;
    private int frame;

    private ArrayList<AnimatedText> cutsceneTexts;

    private ImageButton continueButton;
    private Drawable continueButtonUp;
    private Drawable continueButtonHover;
    private ImageButtonStyle continueButtonStyle;
    private BitmapFont font;
    private GlyphLayout layout;

    private Stage stage;

    private boolean hasIncremented = false;
    
    public CutsceneScreen(OrthographicCamera camera, FitViewport viewport, TheRainWarden game){
        camera.setToOrtho(false, 0, 0);
        this.batch = new SpriteBatch();

        this.game = game;
        this.gameScreen = new GameScreen(camera, viewport, game);
        this.cutsceneScreen = this;

        this.cutsceneImages = new ArrayList<Texture>();
        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene1/frame1.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene1/frame2.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene1/frame3.png")));
        this.font = new BitmapFont();
        this.layout = new GlyphLayout();
        this.cutsceneTexts = new ArrayList<AnimatedText>();
        this.cutsceneTexts.add(new AnimatedText("There is something I have been holding onto ever since I was a child...", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("...", 0.3f));
        this.cutsceneTexts.add(new AnimatedText("And as the rain pours, it is starting to glow...", 0.05f));
        this.frame = 0;

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
                if(!cutsceneTexts.get(frame).hasFinished()){
                    cutsceneTexts.get(frame).skipAnimation();
                }else{
                    if(!hasIncremented){
                        frame++;
                        hasIncremented = true;
                    }
                }
            }
        });

        this.stage = new Stage(viewport);
        this.stage.addActor(continueButton); 
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        font.getData().setScale(2f);
        if(frame < cutsceneImages.size()){
            batch.draw(cutsceneImages.get(frame), Gdx.graphics.getWidth() / 2 - 640, 250);
            layout.setText(font,cutsceneTexts.get(frame).getText(), Color.WHITE, 500, Align.center, true);
            font.draw(batch, layout, Gdx.graphics.getWidth()/2-250, 300);
            cutsceneTexts.get(frame).update(delta);
        }
        batch.end();

        if(frame == cutsceneImages.size()){
            game.setScreen(gameScreen);
            batch.dispose();
            cutsceneScreen.dispose();
        }

        hasIncremented = false;

        stage.act(delta);
        stage.draw();
    }

    public Stage getStage(){
        return stage;
    }
}