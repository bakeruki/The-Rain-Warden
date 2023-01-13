package com.miuq.TheRainWarden.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.TheRainWarden.cutscenes.CutsceneFour;
import com.miuq.TheRainWarden.cutscenes.CutsceneOne;
import com.miuq.TheRainWarden.cutscenes.CutsceneThree;
import com.miuq.TheRainWarden.cutscenes.CutsceneTwo;
import com.miuq.helper.GameOptionsHandler;
import com.miuq.helper.GameSaveHandler;

public class StartMenu extends ScreenAdapter{
    private TheRainWarden game;
    private Stage stage;
    private GameSaveHandler save;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private float alpha;
    private boolean fadingOut;
    private GameOptionsHandler options;

    private BitmapFont font;
    private GlyphLayout layout;
    private SpriteBatch batch;

    private ImageButton newGame1Button;
    private ImageButton newGame2Button;
    private ImageButton newGame3Button;
    private ImageButton backButton;

    private Drawable newGame1Drawable;
    private Drawable newGame1DownDrawable;
    private Drawable newGame2Drawable;
    private Drawable newGame2DownDrawable;
    private Drawable newGame3Drawable;
    private Drawable newGame3DownDrawable;
    private Drawable backButtonDrawable;
    private Drawable backButtonHoverDrawable;

    private ImageButtonStyle newGame1ButtonStyle;
    private ImageButtonStyle newGame2ButtonStyle;
    private ImageButtonStyle newGame3ButtonStyle;
    private ImageButtonStyle backButtonStyle;

    public StartMenu(TheRainWarden game, OrthographicCamera camera, FitViewport viewport){
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;

        this.layout = new GlyphLayout();
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.alpha = 1f;

        this.save = new GameSaveHandler();
        this.options = new GameOptionsHandler();
        
        this.newGame1Drawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame1Button/newGame1NotHovered.png"));
        this.newGame1DownDrawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame1Button/newGame1Hovered.png"));
        this.newGame2Drawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame2Button/newGame2NotHovered.png"));
        this.newGame2DownDrawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame2Button/newGame2Hovered.png"));
        this.newGame3Drawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame3Button/newGame3NotHovered.png"));
        this.newGame3DownDrawable = new TextureRegionDrawable(new Texture("assets/buttons/newGame3Button/newGame3Hovered.png"));
        this.backButtonDrawable = new TextureRegionDrawable(new Texture("assets/buttons/backSaveButton/backButton.png"));
        this.backButtonHoverDrawable = new TextureRegionDrawable(new Texture("assets/buttons/backSaveButton/backButtonHover.png"));

        this.newGame1ButtonStyle = new ImageButtonStyle();
        this.newGame1ButtonStyle.up = newGame1Drawable;
        this.newGame1ButtonStyle.over = newGame1DownDrawable;
        this.newGame2ButtonStyle = new ImageButtonStyle();
        this.newGame2ButtonStyle.up = newGame2Drawable;
        this.newGame2ButtonStyle.over = newGame2DownDrawable;
        this.newGame3ButtonStyle = new ImageButtonStyle();
        this.newGame3ButtonStyle.up = newGame3Drawable;
        this.newGame3ButtonStyle.over = newGame3DownDrawable;
        this.backButtonStyle = new ImageButtonStyle();
        this.backButtonStyle.up = backButtonDrawable;
        this.backButtonStyle.over = backButtonHoverDrawable;
        
        this.newGame1Button = new ImageButton(newGame1ButtonStyle);
        this.newGame2Button = new ImageButton(newGame2ButtonStyle);
        this.newGame3Button = new ImageButton(newGame3ButtonStyle);
        this.backButton = new ImageButton(backButtonStyle);

        this.stage = new Stage();
        this.stage.addActor(newGame1Button);
        this.stage.addActor(newGame2Button);
        this.stage.addActor(newGame3Button);
        this.stage.addActor(backButton);

        newGame1Button.center();
        newGame1Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                fadingOut = true;
                stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(1), Actions.run(new Runnable(){
                    @Override
                    public void run(){
                        int level = save.getLevelFromSave(0);
                        int mangos = save.getMangosFromSave(0);
                        playCutscene(level, mangos, 0);
                    }
                })));
            }
        });
        newGame2Button.center();
        newGame2Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                fadingOut = true;
                stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(1), Actions.run(new Runnable(){
                    @Override
                    public void run(){
                        int level = save.getLevelFromSave(1);
                        int mangos = save.getMangosFromSave(1);
                        playCutscene(level, mangos, 1);
                    }
                })));
            }
        });
        newGame3Button.center();
        newGame3Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                fadingOut = true;
                stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(1), Actions.run(new Runnable(){
                    @Override
                    public void run(){
                        int level = save.getLevelFromSave(2);
                        int mangos = save.getMangosFromSave(2);
                        playCutscene(level, mangos, 2);
                    }
                })));
            }
        });
        backButton.center();
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                handleBackClick();
            }
        });

        newGame1Button.setPosition(Gdx.graphics.getWidth() / 2 - 246, 900);
        newGame2Button.setPosition(Gdx.graphics.getWidth() / 2 - 246, 600);
        newGame3Button.setPosition(Gdx.graphics.getWidth() / 2 - 246, 300);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - 170, 100);

        Gdx.input.setInputProcessor(stage);
    }

    public Stage getStage(){
        return stage;
    }

    private void handleBackClick(){
        MainMenu mainMenu = new MainMenu(camera, viewport, game, false);
        game.setScreen(mainMenu);
        Gdx.input.setInputProcessor(mainMenu.getStage());
        stage.dispose();
        this.dispose();
    }

    private void playCutscene(int level, int mangos, int saveNum){
        GameScreen gameScreen = new GameScreen(camera, viewport, game, level, mangos);
        gameScreen.setSaveNum(saveNum);
        if(level < 2){
            if(options.cutscenesDisabled()){
                game.setScreen(gameScreen);
                batch.dispose();
                this.dispose();
            }else{
                game.setScreen(new CutsceneOne(camera, viewport, game, gameScreen));
                batch.dispose();
                this.dispose();
            }
        }else if(level < 5){
            if(options.cutscenesDisabled()){
                game.setScreen(gameScreen);
                batch.dispose();
                this.dispose();
            }else{
                game.setScreen(new CutsceneTwo(camera, viewport, game, gameScreen));
                batch.dispose();
                this.dispose();
            }
        }else if(level < 8){
            if(options.cutscenesDisabled()){
                game.setScreen(gameScreen);
                batch.dispose();
                this.dispose();
            }else{
                game.setScreen(new CutsceneThree(camera, viewport, game, gameScreen));
                batch.dispose();
                this.dispose();
            }
        }else if(level < 11){
            if(options.cutscenesDisabled()){
                game.setScreen(gameScreen);
                batch.dispose();
                this.dispose();
            }else{
                game.setScreen(new CutsceneFour(camera, viewport, game, gameScreen));
                batch.dispose();
                this.dispose();
            }
        }
    }
    
    private void drawSaveText(int saveNum, float x, float y){
        font.getData().setScale(2f);
        float level = save.getLevelFromSave(saveNum);
        font.setColor(1,1,1, alpha);
        font.draw(batch, "Level: " + ((int)level + 1) + "/17", x, y);
        font.draw(batch, "Mangos Collected: " + save.getMangosFromSave(saveNum) + "/2", x, y-75);

        float completion = level / 17;
        completion = completion * 100;
        font.draw(batch, "Completion: " + (int)completion + "%", x-300, y-35);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(fadingOut){
            alpha -= (1f / 60f) / 1;
        }

        batch.begin();
        drawSaveText(0, Gdx.graphics.getWidth()/2, 880);
        drawSaveText(1, Gdx.graphics.getWidth()/2, 580);
        drawSaveText(2, Gdx.graphics.getWidth()/2, 280);
        batch.end();

        stage.act(delta);
        stage.draw();
    }
}
