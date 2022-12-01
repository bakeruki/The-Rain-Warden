package com.miuq.TheRainWarden;

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


public class MenuScreen extends ScreenAdapter{
    private TheRainWarden game;

    private ImageButton startButton;
    private ImageButton exitButton;

    private Drawable startDownDrawable;
    private Drawable startDrawable;
    private Drawable exitDownDrawable;
    private Drawable exitDrawable;

    private ImageButtonStyle startStyle;
    private ImageButtonStyle exitStyle;

    private Image backgroundImage;

    private Stage stage;
    // private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private CutsceneScreen cutsceneScreen;

    public MenuScreen(OrthographicCamera camera, FitViewport viewport, TheRainWarden game){
        camera.setToOrtho(false, 0, 0);

        this.startDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/startButton/start.png")));
        this.startDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/startButton/startDown.png")));
        this.startStyle = new ImageButtonStyle();
        this.startStyle.up = startDrawable;
        this.startStyle.over = startDownDrawable;
        this.startButton = new ImageButton(startStyle);
        this.startButton.setPosition(870, 300);

        this.exitDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/exitButton/exitButton.png")));
        this.exitDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/exitButton/exitButtonDown.png")));
        this.exitStyle = new ImageButtonStyle();
        this.exitStyle.up = exitDrawable;
        this.exitStyle.over = exitDownDrawable;
        this.exitButton = new ImageButton(exitStyle);
        this.exitButton.setPosition(startButton.getX(), startButton.getY() - 150);

        this.backgroundImage = new Image(new Texture("assets/menuBackground.png"));

        this.game = game;
        // this.gameScreen = new GameScreen(camera, viewport, game); leave this here in case we get annoyed with watching the cutscene every time
        this.menuScreen = this;
        this.cutsceneScreen = new CutsceneScreen(camera, viewport, game);

        this.stage = new Stage(viewport);

        this.stage.addActor(backgroundImage);
        this.stage.addActor(startButton); 
        this.stage.addActor(exitButton);
        this.stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        startButton.center();
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(2), Actions.run(new Runnable(){
                    @Override
                    public void run(){
                        game.setScreen(cutsceneScreen);
                    Gdx.input.setInputProcessor(cutsceneScreen.getStage());
                    stage.dispose();
                    menuScreen.dispose();
                    }
                })));
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
