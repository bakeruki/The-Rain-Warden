package com.miuq.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class GameOptionsHandler {
    private FileHandle options;

    public GameOptionsHandler(){
        this.options = Gdx.files.external("Documents/TRW/options/options.txt");
        createOptionsFile();
    }

    private void createOptionsFile(){
        if(!options.exists()){
            options.writeString("0@0@0", false);
        }
    }

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

    public void updateOptions(int option){
        String data[] = options.readString().split("@");
        int option1 = Integer.parseInt(data[0]);
        int option2 = Integer.parseInt(data[1]);
        int option3 = Integer.parseInt(data[2]);

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

    public boolean cutscenesDisabled(){
        return checkData(0);
    }

    public boolean showDeathCounter(){
        return checkData(1);
    }

    public boolean godEnabled(){
        return checkData(2);
    }
}
