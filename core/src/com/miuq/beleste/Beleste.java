package com.miuq.beleste;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Beleste extends Game{
	public static Beleste INSTANCE;
	private int widthScreen;
	private int heightScreen;
	private OrthographicCamera camera;
	private FitViewport viewport;

	public Beleste(){
		INSTANCE = this;
	}
	
	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, widthScreen, heightScreen);
		this.viewport = new FitViewport(widthScreen, heightScreen, camera);
		setScreen(new MenuScreen(camera, viewport, this));
	}
}
