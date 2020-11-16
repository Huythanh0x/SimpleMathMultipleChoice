package com.example.notforkid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    TextView textViewScore;
    TextView textViewTimer;
    TextView textViewQuestion;
    int positionAnswer;
    int minutesTimer;
    int secondTimer;
    int countCorrectAnswer = 0;
    int countAllAnswer = 0;
    Button button;
    MediaPlayer mMediaPlayerCorrect ;
    MediaPlayer mMediaPlayerWrong;
    ArrayList<Button> mButtonArrayList = new ArrayList<Button>();
    public void createQuestion(){
        Random random = new Random();
        int firstNumber = random.nextInt(20);
        int secondNumber = random.nextInt(20);
        textViewQuestion.setText(firstNumber + " + " + secondNumber);
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        mButtonArrayList.add(button0);
        mButtonArrayList.add(button1);
        mButtonArrayList.add(button2);
        mButtonArrayList.add(button3);
        for (int i = 0;i < 4;i++){
            int sum ;
            do {
                sum = random.nextInt(41);
                mButtonArrayList.get(i).setText(String.valueOf(sum));
            }while(sum == firstNumber + secondNumber);
            mButtonArrayList.get(i).setText(String.valueOf(sum));
        }
        positionAnswer = random.nextInt(4);
        mButtonArrayList.get(positionAnswer).setText(String.valueOf(firstNumber + secondNumber));
    }
    public void countTime(){

    }
    public void playAgain(View view){
        countCorrectAnswer = 0;
        countAllAnswer = 0;
        mButtonArrayList.clear();

    }
    public void chooseAnswer(View view){
        Button chosenButton = (Button) view;
        if (chosenButton.getTag().toString().equals(Integer.toString(positionAnswer))){
            countCorrectAnswer++;
            if(mMediaPlayerWrong.isPlaying()){
                mMediaPlayerWrong.pause();
                mMediaPlayerWrong.seekTo(0);
            }
            if(mMediaPlayerCorrect.isPlaying()){
                mMediaPlayerCorrect.pause();
                mMediaPlayerCorrect.seekTo(0);
            }
            mMediaPlayerCorrect.start();

        }
        else {
            if(mMediaPlayerWrong.isPlaying()){
                mMediaPlayerWrong.pause();
                mMediaPlayerWrong.seekTo(0);
            }
            if(mMediaPlayerCorrect.isPlaying()){
                mMediaPlayerCorrect.pause();
                mMediaPlayerCorrect.seekTo(0);
            }
            mMediaPlayerWrong.start();
        }
        countAllAnswer++;
        textViewScore.setText(countCorrectAnswer + "/" + countAllAnswer);
        createQuestion();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewScore = findViewById(R.id.textViewScore);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewTimer = findViewById(R.id.textViewTimer);
        mMediaPlayerCorrect = MediaPlayer.create(this,R.raw.correctanswer);
        mMediaPlayerWrong = MediaPlayer.create(this,R.raw.wronganswer);
        button = findViewById(R.id.buttonAgain);
        createQuestion();
        //countTime();
        new CountDownTimer(60000,500){
            public void onTick(long millisUntilFinished) {
                minutesTimer = (int)(millisUntilFinished / 60000);
                secondTimer = (int) ((millisUntilFinished - minutesTimer * 1000) / 1000);
                textViewTimer.setText(minutesTimer + ":" + secondTimer );
            }

            public void onFinish() {
                ConstraintLayout constraintPlay = findViewById(R.id.constraintLayoutPlay);
                constraintPlay.setVisibility(View.INVISIBLE);
                ConstraintLayout constraintShow = findViewById(R.id.constraintLayoutShow);
                TextView showResult = findViewById(R.id.textViewShow);
                showResult.setText(countCorrectAnswer +"//"+countAllAnswer);
                constraintShow.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}