package com.miuq.TheRainWarden.animation;

/**
 * This class creates text that appears letter by letter over
 * a set amount of time.
 * @author Michelle Vuong
 */
public class AnimatedText {
    /**
     * The entire string of text that will eventually be drawn.
     */
    private String text;
    /**
     * The current string of text that should be rendered.
     */
    private String textToRender;
    /**
     * The number of characters that are currently being rendered.
     */
    private int charCounter;
    /**
     * How much time it should take for each letter to be rendered.
     */
    private float renderTime;
    /**
     * How much time has passed on the current character.
     */
    private float currentCharTime;
    
    public AnimatedText(String text, float renderTime){
        this.text = text;
        this.textToRender = "";
        this.renderTime = renderTime;
    }

    /**
     * Updates the current text.
     * @param delta Time since last render.
     * @author Michelle Vuong
     */
    public void update(float delta){
        //if the entire string has already been rendered, nothing needs to be updated.
        if(textToRender.equals(text)){
            return;
        }
        //add the time since the last render to the time spent on the current character
        currentCharTime += delta;
        //if it is time to render a new letter
        if(currentCharTime >= renderTime){
            //gets the next letter of the entire string and adds it to the render string
            textToRender = textToRender + Character.toString(text.charAt(charCounter));
            charCounter++;
            //resets character timer
            currentCharTime = 0;
        }
    }

    /**
     * Returns the partial String that should be rendered for the animated effect.
     * @author Michelle Vuong
     */
    public String getText(){
        return textToRender;
    }

    /**
     * Skips the entire animation of the animated text.
     * @author Michelle Vuong
     */
    public void skipAnimation(){
        textToRender = text;
    }

    /**
     * Tells if the current text has finished rendering.
     * @return True if the text has finished rendering, false if it has not finished rendering.
     * @author Michelle Vuong
     */
    public boolean hasFinished(){
        if(textToRender.equals(text)){
            return true;
        }
        return false;
    }
}
