package com.ajoscram.flappy_bird;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.SimpleDateFormat;
import java.util.Date;

//this class is only a collection of functions
//and therefore is marked as abstract to avoid
//unnecesary instantiation
public abstract class Scores {

    private static final String SCORES_FILE = "scores.dat";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String SCORE_SPLITTER = "\n";
    private static final String FIELD_SPLITTER = ",";
    private static final int MAX_SCORES = 5;

    public static String[][] get(){
        if(Gdx.files.isLocalStorageAvailable()) {
            FileHandle file = Gdx.files.local(SCORES_FILE);
            if (file.exists()) {
                String text = file.readString();
                if(text.isEmpty())
                    return null;
                String[] rows = text.split(SCORE_SPLITTER);
                String[][] scores = new String[rows.length][2];
                int count = 0;
                for (String row : rows) {
                    String score = row.split(FIELD_SPLITTER)[0];
                    String date = row.split(FIELD_SPLITTER)[1];
                    scores[count][0] = score;
                    scores[count][1] = date;
                    count++;
                }
                return scores;
            }
        }
        return null;
    }

    //returns true if the score is added to high scores
    //false otherwise
    public static boolean add(int score){
        if(Gdx.files.isLocalStorageAvailable()) {
            FileHandle file = Gdx.files.local(SCORES_FILE);
            String scoreStr = Integer.toString(score);
            String dateStr = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            Gdx.app.log("FLAPPER", scoreStr + FIELD_SPLITTER + dateStr);

            //if the file doesn't exist, then create it
            if (!file.exists()){
                file.writeString(scoreStr + FIELD_SPLITTER + dateStr, true);
                return true;
            } else {
                String[][] scores = get();
                String overwrite = "";
                int numberOfScores = scores.length;
                boolean isHighScore = false;
                for(int i = 0; i < numberOfScores; i++){
                    int oldScore = Integer.parseInt(scores[i][0]);

                    //if a high score is found for the first time
                    // add it and set the highscore flag
                    if(!isHighScore && oldScore < score) {
                        overwrite += SCORE_SPLITTER + scoreStr + FIELD_SPLITTER + dateStr;
                        isHighScore = true;

                        //if the file is full then ommit the last entry
                        if(numberOfScores == MAX_SCORES)
                            numberOfScores--;
                    }

                    //this if checks if a high score is added on the last
                    //entry of the highscores file
                    if(i < numberOfScores)
                        overwrite += SCORE_SPLITTER + scores[i][0] + FIELD_SPLITTER + scores[i][1];
                }
                if(isHighScore) {
                    file.writeString(overwrite.trim(), false);
                    return true;
                }
            }
        }
        return true;
    }
}
