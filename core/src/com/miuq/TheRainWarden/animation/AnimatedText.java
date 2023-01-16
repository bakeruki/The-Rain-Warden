package com.miuq.TheRainWarden.animation;

public class AnimatedText {
    private String text;
    private String textToRender;
    private int charCounter;
    private float renderTime;
    private float currentCharTime;
    
    public AnimatedText(String text, float renderTime){
        this.text = text;
        this.textToRender = "";
        this.renderTime = renderTime;
    }

    public void update(float delta){
        if(textToRender.equals(text)){
            return;
        }

        currentCharTime += delta;
        if(currentCharTime >= renderTime){
            textToRender = textToRender + Character.toString(text.charAt(charCounter));
            charCounter++;
            currentCharTime = 0;
        }
    }

    public String getText(){
        return textToRender;
    }

    public void skipAnimation(){
        textToRender = text;
    }

    public boolean hasFinished(){
        if(textToRender.equals(text)){
            return true;
        }
        return false;
    }
}
