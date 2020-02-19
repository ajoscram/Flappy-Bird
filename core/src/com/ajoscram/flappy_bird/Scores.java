package com.ajoscram.flappy_bird;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Date;

//this class is only a collection of functions
//and therefore is marked as abstract to avoid
//unnecesary instantiation
public abstract class Scores {

    private static final String SCORES_FILE = "scores.dat";
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    public static String[][] get(){
        if(Gdx.files.isLocalStorageAvailable()) {
            FileHandle file = Gdx.files.local(SCORES_FILE);
            if (file.exists()) {
                String text = file.readString();
                if(text.isEmpty())
                    return null;
                String[] rows = text.split("\n");
                String[][] scores = new String[rows.length][2];
                int count = 0;
                for (String row : rows) {
                    String score = row.split(",")[0];
                    String date = row.split(",")[1];
                    scores[count][0] = score;
                    scores[count][1] = date;
                    count++;
                }
                return scores;
            }
        }
        return null;
    }

    public static void add(int score, Date date){

    }
}
