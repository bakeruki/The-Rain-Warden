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
import com.miuq.TheRainWarden.TheRainWarden;

public class MainMenu extends ScreenAdapter{
    private TheRainWarden game;

    private ImageButton startButton;
    private ImageButton optionsButton;
    private ImageButton exitButton;

    private Drawable startDownDrawable;
    private Drawable startDrawable;
    private Drawable optionsDrawable;
    private Drawable optionsDownDrawable;
    private Drawable exitDownDrawable;
    private Drawable exitDrawable;

    private ImageButtonStyle startStyle;
    private ImageButtonStyle optionsStyle;
    private ImageButtonStyle exitStyle;

    private Image backgroundImage;

    private Stage stage;
    // private GameScreen gameScreen;
    private MainMenu menuScreen;
    private OptionsMenu optionsMenu;
    private StartMenu startMenu;

    public MainMenu(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, boolean doFade){
        camera.setToOrtho(false, 0, 0);

        this.startDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/startButton/start.png")));
        this.startDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/startButton/startDown.png")));
        this.startStyle = new ImageButtonStyle();
        this.startStyle.up = startDrawable;
        this.startStyle.over = startDownDrawable;
        this.startButton = new ImageButton(startStyle);
        this.startButton.setPosition(870, 300);

        this.optionsDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/optionsButton/optionsButton.png")));
        this.optionsDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/optionsButton/optionsButtonDown.png")));
        this.optionsStyle = new ImageButtonStyle();
        this.optionsStyle.up = optionsDrawable;
        this.optionsStyle.over = optionsDownDrawable;
        this.optionsButton = new ImageButton(optionsStyle);
        this.optionsButton.setPosition(1720, 20);

        this.exitDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/exitButton/exitButton.png")));
        this.exitDownDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("assets/buttons/exitButton/exitButtonDown.png")));
        this.exitStyle = new ImageButtonStyle();
        this.exitStyle.up = exitDrawable;
        this.exitStyle.over = exitDownDrawable;
        this.exitButton = new ImageButton(exitStyle);
        this.exitButton.setPosition(startButton.getX(), startButton.getY() - 150);

        this.backgroundImage = new Image(new Texture("assets/screens/menuScreen.png"));

        this.game = game;
        // this.gameScreen = new GameScreen(camera, viewport, game); leave this here in case we get annoyed with watching the cutscene every time
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

        if(doFade){
            this.stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
        }
        

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    private void handleStartClick(){
        game.setScreen(startMenu);
        Gdx.input.setInputProcessor(startMenu.getStage());
        stage.dispose();
        menuScreen.dispose();
    }

    private void handleOptionsClick(){
        game.setScreen(optionsMenu);
        Gdx.input.setInputProcessor(optionsMenu.getStage());
        stage.dispose();
        menuScreen.dispose();
    }

    private void handleExitClick(){
        Gdx.app.exit();
    }

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
