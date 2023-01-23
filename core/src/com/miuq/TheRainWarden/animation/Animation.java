package com.miuq.TheRainWarden.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Credit: Brent Aureli Codes on YouTube
 * https://www.youtube.com/watch?v=tcH6Mp03KC0&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=12&ab_channel=BrentAureliCodes
 */
public class Animation {
    /**
     * Stores each frame as a seperate texture.
     */
    private Array<TextureRegion> frames;
    /**
     * The maximum amount of time to spend on each frame.
     */
    private float maxFrameTime;
    /**
     * How much time has been spent on the current frame.
     */
    private float currentFrameTime;
    /**
     * The total number of frames.
     */
    private int frameCount;
    /**
     * The number of the current frame.
     */
    private int frame;

    /**
	 * Creates an array of texture regions based on a PNG image with multiple frames of animation.
	 * @param region The TextureRegion of the PNG image that contains the animation.
     * @param frameCount The number of frames in the animation. Used to split the image into multiple texture regions for each frame.
     * @param cycleTime The amount of time it takes for one full cycle of the animation. Used to determine the speed at which the images change.
	 */
    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>(); //array of texture regions
        int frameWidth = region.getRegionWidth() / frameCount; //determines width of each frame based on number of frames
        for(int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i*frameWidth, 0, frameWidth, region.getRegionHeight())); //splices each frame and stores in array
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }
    /**
	 * Updates animation frame based on time.
	 * @param dt Game time.
	 */
    public void update(float dt){
        currentFrameTime += dt;
        //determines when to switch to next frame based on elapsed time
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        //determines when to reset the animation
        if(frame == frameCount){
            frame = 0;
        }
    }

    //setters
    /**
     * Sets the frame.
     * @param frame The new frame number.
     */
    public void setFrame(int frame){
        this.frame = frame;
    }

    //getters
    /**
     * Gets the total amount of frames.
     * @return The total amount of frames.
     */
    public int getFrameCount(){
        return frameCount;
    }

    /**
     * Gets the current frame number.
     * @return The current frame number.
     */
    public int getFrameNum(){
        return frame;
    }

    /**
     * Gets the current frame that should be drawn in the animation.
     * @return The current frame that should be drawn in the animation.
     */
    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
