package com.miuq.TheRainWarden.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Handles all functionality for the games options file. The options file stores
 * the options that the user has selected, which keeps their settings the same 
 * after they exit the game.
 * @author Michelle Vuong
 */
public class GameOptionsHandler {
    /**
     * Stores the file paths of the options file.
     */
    private FileHandle options;

    public GameOptionsHandler(){
        this.options = Gdx.files.external("Documents/TRW/options/options.txt");
        createOptionsFile();
    }

    /**
     * Creates the options file if it does not exist yet and fills it with default values.
     * The first number represents the Speedrun setting.
     * The second number represents the Retry Count setting.
     * The third number represents the God Mode setting.
     * @author Michelle Vuong
     */
    private void createOptionsFile(){
        if(!options.exists()){
            options.writeString("0@0@0", false);
        }
    }

    /**
     * Checks a specific option from the options file.
     * @param path The option to check. (1 for speedrun, 2 for retry counter, 3 for god mode)
     * @return Returns false if option is disabled, returns true if option is enabled.
     * @author Michelle Vuong
     */
    private boolean checkData(int path){
        String data[] = options.readString().split("@");
        if(path > 2){
            System.out.println("read options file error: path greater than 2: " + path);
            return false;
        }
        if(data[path].equals("0")){
            return false;
        }
        return true;
    }

    /**
     * Updates the options file with new selected options.
     * @param option The option that was changed. (1 for speedrun, 2 for retry counter, 3 for god mode)
     * @author Michelle Vuong
     */
    public void updateOptions(int option){
        String data[] = options.readString().split("@");
        int option1 = Integer.parseInt(data[0]);
        int option2 = Integer.parseInt(data[1]);
        int option3 = Integer.parseInt(data[2]);

        //since the options work like toggles, this code flips the current value of
        //the option
        switch(option){
            case 0:
                if(option1 == 0){
                    option1 = 1;
                }else{
                    option1 = 0;
                }
                break;
            case 1:
                if(option2 == 0){
                    option2 = 1;
                }else{
                    option2 = 0;
                }
                break;
            case 2:
                if(option3 == 0){
                    option3 = 1;
                }else{
                    option3 = 0;
                }
                break;
        }
        options.writeString(Integer.toString(option1) + "@" + Integer.toString(option2) + "@" +Integer.toString(option3), false);
    }

    /**
     * Checks if cutscenes are disabled.
     * @return False if cutscenes are not disabled, true if cutscenes are disabled
     * @author Michelle Vuong
     */
    public boolean cutscenesDisabled(){
        return checkData(0);
    }

    /**
     * Check if the death counter should be shown.
     * @return False if it should not be shown, true if it should be shown.
     * @author Michelle Vuong
     */
    public boolean showDeathCounter(){
        return checkData(1);
    }

    /**
     * Check if god mode should be enabled.
     * @return False if it should not be enabled, true if it should be enabled.
     * @author Michelle Vuong
     */
    public boolean godEnabled(){
        return checkData(2);
    }
}
