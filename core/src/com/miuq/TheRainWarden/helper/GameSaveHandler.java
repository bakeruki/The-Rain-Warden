package com.miuq.TheRainWarden.helper;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Handles all functionality for the games save files. Save files contain three numbers
 * seperated by an "@". The first number represents the level that was saved,
 * the second number represents the number of mangos that the player collected,
 * the third number represents the number of times the player died, and
 * the fourth number stores the dialogue counter which is used internally.
 * @author Luqman Patel
 */
public class GameSaveHandler {
    /**
     * Stores file paths of all save files.
     */
    private ArrayList<FileHandle> savePaths;

    public GameSaveHandler(){
        this.savePaths = new ArrayList<FileHandle>();
        this.savePaths.add(Gdx.files.external("Documents/TRW/savegame/save1.txt"));
        this.savePaths.add(Gdx.files.external("Documents/TRW/savegame/save2.txt"));
        this.savePaths.add(Gdx.files.external("Documents/TRW/savegame/save3.txt"));

        createSaveFiles();
    }

    /**
     * Creates save files if they do not exist and defaults them to level 0. 
     * Save files are created and stored in the current users /Documents folder.
     * First number = current level
     * Second number = number of mangos collected
     * Third number = number of deaths
     * Fourth number = dialogue number used internally
     * @author Luqman Patel
     */
    private void createSaveFiles(){
        //loops through each save path and checks to see if there is a save file there
        for(FileHandle save : savePaths){
            if(!save.exists()){
                save.writeString("0@0@0@0", false);
            }
        }
    }

    /**
     * Updates save files with new data.
     * @param saveNum The number of the save file to update (0, 1 or 2)
     * @param level The level that the player is on.
     * @param mangos The number of mangos that the player has collected.
     * @author Luqman Patel
     */
    public void updateSaveFiles(int saveNum, int level, int mangos, int deaths, int dialogueNum){
        //turn all game data into a string
        String levelString = Integer.toString(level);
        String mangosString = Integer.toString(mangos);
        String deathsString = Integer.toString(deaths);
        String dialogueString = Integer.toString(dialogueNum);
        //combine string with preset seperator
        String data = levelString + "@" + mangosString + "@" + deathsString + "@" + dialogueString;
        //write string into save file
        this.savePaths.get(saveNum).writeString(data, false);
    }

    /**
     * Retrieves the level that the player was on from a save file.
     * @param saveNum The number of the save file to retrieve (0, 1 or 2).
     * @return The level from the save file.
     * @author Luqman Patel
     */
    public int getLevelFromSave(int saveNum){
        String[] data = savePaths.get(saveNum).readString().split("@");
        int level = Integer.parseInt(data[0]);
        return level;
    }

    /**
     * Retrieves the number of mangos that the player collected.
     * @param saveNum The number of the save file to retrieve (0, 1 or 2)
     * @return The number of mangos from the save file.
     * @author Luqman Patel
     */
    public int getMangosFromSave(int saveNum){
        String[] data = savePaths.get(saveNum).readString().split("@");
        int mangos = Integer.parseInt(data[1]);
        return mangos;
    }

    /**
     * Retrieves the number of times that the player died.
     * @param saveNum The number of the save file to retrieve (0, 1 or 2)
     * @return The number of deaths from the save file.
     * @author Luqman Patel
     */
    public int getDeathsFromSave(int saveNum){
        String[] data = savePaths.get(saveNum).readString().split("@");
        int deaths = Integer.parseInt(data[2]);
        return deaths;
    }

    /**
     * Retrieves the dialogue number.
     * @param saveNum The number of the save file to retrieve (0, 1 or 2)
     * @return The dialogue number from the save file.
     * @author Luqman Patel
     */
    public int getDialogueNumFromSave(int saveNum){
        String[] data = savePaths.get(saveNum).readString().split("@");
        int dialogueNum = Integer.parseInt(data[3]);
        return dialogueNum;
    }
}
