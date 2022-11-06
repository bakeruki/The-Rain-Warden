package com.miuq.beleste;

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

import animation.Animation;


public class MenuScreen extends ScreenAdapter{
    private SpriteBatch batch;
    private Animation backgroundAnimation;

    private Beleste game;

    private ImageButton startButton;
    private ImageButton exitButton;

    private Drawable startDownDrawable;
    private Drawable startDrawable;
    private Drawable exitDownDrawable;
    private Drawable exitDrawable;

    private ImageButtonStyle startStyle;
    private ImageButtonStyle exitStyle;

    private Stage stage;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;

    public MenuScreen(OrthographicCamera camera, FitViewport viewport, Beleste game){
        camera.setToOrtho(false, 0, 0);
        this.batch = new SpriteBatch();
        this.backgroundAnimation = new Animation(new TextureRegion(new Texture("menuBackground.png")), 13, 2.1f);

        this.startDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/startButton/start.png")));
        this.startDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/startButton/startDown.png")));
        this.startStyle = new ImageButtonStyle();
        startStyle.up = startDrawable;
        startStyle.over = startDownDrawable;
        this.startButton = new ImageButton(startStyle);
        this.startButton.setPosition(870, 300);

        this.exitDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/exitButton/exitButton.png")));
        this.exitDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/exitButton/exitButtonDown.png")));
        this.exitStyle = new ImageButtonStyle();
        exitStyle.up = exitDrawable;
        exitStyle.over = exitDownDrawable;
        this.exitButton = new ImageButton(exitStyle);
        this.exitButton.setPosition(startButton.getX(), startButton.getY() - 150);

        this.game = game;
        this.gameScreen = new GameScreen(camera, viewport, game);
        this.menuScreen = this;

        this.stage = new Stage(viewport);

        this.stage.addActor(startButton); 
        this.stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        backgroundAnimation.update(delta);
        
        batch.begin();
        batch.draw(backgroundAnimation.getFrame(), 0, 0);
        batch.end();

        startButton.center();
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(gameScreen);
                menuScreen.dispose();
            }
        });
        
        exitButton.center();
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        stage.act(delta);
        stage.draw();
    }
}
