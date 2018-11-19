package com.example.venkatmugesh.meetmrvalluvar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PlayGame extends AppCompatActivity {

    DataFetcher df ;
    OfflineDataBase myDb;
    int countValue;
    PlayGameDb myPlay;
    int finalvalue;
    String gameNumber = "";
    int finalNumber;
    ArrayList<Integer> dbValues;
    TextView scoreView;
    TextView scoreCard;
    TextView gameLine1;
    TextView gameLine2;
    TextView checkText;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    String[] answer;
    int locationOfAnswer;
    int score = 0;
    String qn = "";
    String qn2 = "";
    int attempts = 0;


    public void playAgain(int count){

        scoreView.setVisibility(View.VISIBLE);
        scoreCard.setVisibility(View.VISIBLE);
        gameLine1.setVisibility(View.VISIBLE);
        gameLine2.setVisibility(View.VISIBLE);
        checkText.setVisibility(View.VISIBLE);
        option1.setVisibility(View.VISIBLE);
        option2.setVisibility(View.VISIBLE);
        option3.setVisibility(View.VISIBLE);
        option4.setVisibility(View.VISIBLE);
        checkText.setText("gfcgcfhcf");
        scoreCard.setText("0/" + gameNumber);

       generateQuestion(count);

    }

    public void generateQuestion(int count){
        Random rand = new Random();
        int idValue = rand.nextInt(count);

        while (dbValues.contains(idValue)){
            idValue = rand.nextInt(count);
        }


        Cursor data = myDb.getData(idValue);
        while (data.moveToNext()){
            qn = data.getString(2);
            qn2 = data.getString(3);
        }

        Log.i("1" , qn);
        Log.i("2" , qn2);
        int select = rand.nextInt(2);

        Log.i("select" , String.valueOf(select));

        if (select == 0){
            gameLine1.setText(qn);
            gameLine2.setText("?_?_?_?_?_?_?_?_?_?_?_?_?_?_?");

            locationOfAnswer = rand.nextInt(4);
            Log.i("location of answer" , String.valueOf(locationOfAnswer));
            int inCorrectAnswer;

            for (int i = 0 ; i<4 ; i++){
                if (i == locationOfAnswer){
                    answer[i] = qn2;
                }else{
                    inCorrectAnswer = rand.nextInt(count);
                    while (inCorrectAnswer == idValue && dbValues.contains(inCorrectAnswer)){
                        inCorrectAnswer = rand.nextInt(count);
                    }
                    Log.i("incorrect" , String.valueOf(inCorrectAnswer));
                    Cursor data2 = myDb.getLine2(inCorrectAnswer);

                    while (data2.moveToNext()){
                        answer[i] = data2.getString(0);
                    }
                }
            }
        }else{
            gameLine2.setText(qn2);
            gameLine1.setText("?_?_?_?_?_?_?_?_?_?_?_?_?_?_?");

            locationOfAnswer = rand.nextInt(4);
            Log.i("location of answer" , String.valueOf(locationOfAnswer));
            int inCorrectAnswer;

            for (int i = 0 ; i<4 ; i++){
                if (i == locationOfAnswer){
                    answer[i] = qn;
                }else{
                    inCorrectAnswer = rand.nextInt(count);
                    while (inCorrectAnswer == idValue && dbValues.contains(inCorrectAnswer)){
                        inCorrectAnswer = rand.nextInt(count);
                    }
                    Log.i("incorrect" , String.valueOf(inCorrectAnswer));
                    Cursor data2 = myDb.getLine1(inCorrectAnswer);

                    while (data2.moveToNext()){
                        answer[i] = data2.getString(0);
                    }
                }
        }}


        for (int i = 0 ; i<dbValues.size(); i++){
            Log.i("values of db" , String.valueOf(dbValues.get(i)));
        }
        option1.setText(answer[0]);
        option2.setText(answer[1]);
        option3.setText(answer[2]);
        option4.setText(answer[3]);

    }

    public void chooseAnswer(View view){
        attempts++;
        if (view.getTag().toString().equals(Integer.toString(locationOfAnswer))){
            checkText.setText("Correct..!");
            score++;
            scoreCard.setText(score + "/" + gameNumber);
        }else{
            checkText.setText("Wrong..!!");
        }if(attempts == Integer.parseInt(gameNumber)){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View myView = getLayoutInflater().inflate(R.layout.play_again_dialog, null);
            mBuilder.setView(myView);

            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            dialog.setCancelable(false);

            TextView scoreView = (TextView) myView.findViewById(R.id.dialog_score);
            final Button playAgainButton = (Button) myView.findViewById(R.id.playAgain);
            Button notAgain = (Button) myView.findViewById(R.id.notAgain);

            scoreView.setText("YOUR SCORE: " + score + "/" + gameNumber);

            playAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAgain(Integer.parseInt(gameNumber));
                    dialog.dismiss();
                }
            });

            notAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    dialog.dismiss();
                }
            });
            Log.i("finished" , "game");
        }else {
            generateQuestion(finalvalue);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

            df = new DataFetcher();
            myDb = new OfflineDataBase(this);
            dbValues = new ArrayList<Integer>();
            answer = new String[4];
            myPlay = new PlayGameDb(this);
            countValue = df.putCount();
            Button button = (Button)findViewById(R.id.button2);
            scoreCard = (TextView) findViewById(R.id.scoreText);
            scoreView = (TextView) findViewById(R.id.scoreViewText);
            gameLine1 = (TextView) findViewById(R.id.gameLine1);
            gameLine2 = (TextView) findViewById(R.id.gameLine2);
            checkText = (TextView) findViewById(R.id.checkText);
            option1 = (Button)findViewById(R.id.option1);
            option2 = (Button)findViewById(R.id.option2);
            option3 = (Button)findViewById(R.id.option3);
            option4 = (Button)findViewById(R.id.option4);
            if(countValue == 0){

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                View myView = getLayoutInflater().inflate(R.layout.dialog_box_custom , null);
                mBuilder.setView(myView);

                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.setCancelable(false);

                Button selectButton = (Button)myView.findViewById(R.id.btn_dialog);
                selectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(PlayGame.this , offlineListView.class);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });

                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(PlayGame.this , PlayGame.class);
                        startActivity(i);

                    }
                });//ஊடல் உணர்தல் புணர்தல் இவைகாமம்
            }else{
                Log.i("Success" , String.valueOf(countValue));

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                View myView = getLayoutInflater().inflate(R.layout.select_dialog , null);
                mBuilder.setView(myView);

                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.setCancelable(false);

                TextView offText = (TextView)myView.findViewById(R.id.offSelect);
                final EditText selEdit = (EditText) myView.findViewById(R.id.selEdit);
                Button goButton  = (Button) myView.findViewById(R.id.goButton);

                gameNumber = new String();
                gameNumber = String.valueOf(countValue);
                offText.setText(gameNumber);

                goButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameNumber = String.valueOf(selEdit.getText());
                        finalNumber = Integer.parseInt(gameNumber);
                        if(finalNumber > countValue && finalNumber == 0 && gameNumber == ""){
                            Toast.makeText(PlayGame.this , "No Zeros and Enter a value within " + String.valueOf(countValue) , Toast.LENGTH_LONG ).show();
                        }else {
                            Toast.makeText(PlayGame.this  , "Lets Start" , Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Cursor data = myPlay.getData();
                            while(data.moveToNext()){
                                dbValues.add(data.getInt(1));
                            }
                            Log.i("values" , String.valueOf(dbValues.size()));
                            finalvalue = countValue + dbValues.size();
                            playAgain(finalvalue);

                        }
                    }
                });


            }
        }

}
