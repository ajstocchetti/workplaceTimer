package com.example.meetingtimer.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

public class MainActivity extends Activity {

    private Button timerButton;
    private Button resetBtn;
    private EditText payRate;

    private Chronometer timerChrono;
    private Boolean isRunning = false;
    private long elapsedTime = 0L;
    private TextView costDisplay;
    private double payRateDbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerButton = (Button) findViewById(R.id.startPauseBtn);
        payRate = (EditText) findViewById(R.id.payRateInput);
        timerChrono = (Chronometer) findViewById(R.id.myTimer);
        costDisplay = (TextView) findViewById(R.id.costLabel);
        resetBtn = (Button) findViewById(R.id.resetBtn);

        payRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if( !hasFocus)
                {   String rateStr = payRate.getText().toString();
                    if( rateStr.length() > 0)
                    {   payRateDbl = Double.parseDouble(rateStr);
                        timerButton.setEnabled(true);
                        resetBtn.setEnabled(true);
                    }
                    else
                    {   timerButton.setEnabled(false);
                        resetBtn.setEnabled(false);
                    }
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pauseTimer(true);
                // reset timer to zero
                elapsedTime = 0L;
            }
        }
        );
        timerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)  {
                if( isRunning == true)
                {   pauseTimer();   }
                else
                {   startTimer();   }
            }
        });
        timerChrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
        {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long millis = SystemClock.elapsedRealtime() - timerChrono.getBase();
                double cost = millis/3600000;
                costDisplay.setText(getResources().getString(R.string.costAmount).concat(String.valueOf(cost)));
            }
        });
    }


    public void startTimer()
    {   isRunning = true;
        timerChrono.setBase((SystemClock.elapsedRealtime()-elapsedTime));
        timerChrono.start();
        timerButton.setText(R.string.pause);
    }
    public void pauseTimer(boolean reset)
    {   isRunning = false;
        elapsedTime = SystemClock.elapsedRealtime() - timerChrono.getBase();
        timerChrono.stop();
        if( reset)
            timerButton.setText(R.string.restart);
        else
            timerButton.setText(R.string.start);
    }
    public void pauseTimer()
    {   pauseTimer(false);  }
}
