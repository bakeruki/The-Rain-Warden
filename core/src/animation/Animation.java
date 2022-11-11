package animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    /**
	 * Creates an array of texture regions based on a PNG image with multiple frames of animation.
     * @author Michelle Vuong
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
     * @author Michelle Vuong
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
    public void setFrame(int frame){
        this.frame = frame;
    }

    //getters
    public int getFrameCount(){
        return frameCount;
    }

    public int getFrameNum(){
        return frame;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
