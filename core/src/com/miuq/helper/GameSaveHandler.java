package com.miuq.helper;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Handles all functionality for the games save files. Save files contain two numbers
 * seperated by an "@". The first number represents the level that was saved, and
 * the second number represents the number of mangos that the player collected.
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
     * @author Luqman Patel
     */
    private void createSaveFiles(){
        for(FileHandle save : savePaths){
            if(!save.exists()){
                save.writeString("0@0@0", false);
            }
        }
    }

    /**
     * Updates save files with new data once the game has been exited.
     * @param saveNum The number of the save file to update (0, 1 or 2)
     * @param level The level that the player is on.
     * @param mangos The number of mangos that the player has collected.
     * @author Luqman Patel
     */
    public void updateSaveFiles(int saveNum, int level, int mangos, int deaths){
        String levelString = Integer.toString(level);
        String mangosString = Integer.toString(mangos);
        String deathsString = Integer.toString(deaths);
        String data = levelString + "@" + mangosString + "@" + deathsString;
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

    public int getDeathsFromSave(int saveNum){
        String[] data = savePaths.get(saveNum).readString().split("@");
        int deaths = Integer.parseInt(data[2]);
        return deaths;
    }
}
