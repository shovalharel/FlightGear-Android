package com.example.flightgearapp.views;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flightgearapp.R;
import com.example.flightgearapp.model.FGPlayer;
import com.example.flightgearapp.view_model.ViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Joystick joystick;
    private FGPlayer model;
    private SeekBar throttle;
    private SeekBar rudder;
    private ViewModel vm;
    private Snackbar succeeded;
    private Snackbar NotSucceeded;

    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (joystick.isPressed(event.getX(), event.getY())) {
                        joystick.setIsPressed(true);
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (joystick.getIsPressed()) {
                        joystick.setActurator((int) event.getX(), (int) event.getY());
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    joystick.setIsPressed(false);
                    joystick.resetActurator();
                    return true;
            }
            return MainActivity.super.onTouchEvent(event);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.model =  new FGPlayer();
        this.vm = new ViewModel(this.model);
        setContentView(R.layout.activity_main);
        joystick = (Joystick) findViewById(R.id.Joystick);
        joystick.setVM(vm);
        throttle = (SeekBar) findViewById(R.id.throttle2);
        rudder = (SeekBar) findViewById(R.id.rudder);
        joystick.setOnTouchListener(handleTouch);
        succeeded = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.connected,
                Snackbar.LENGTH_SHORT);
        NotSucceeded = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.notConnected,
                Snackbar.LENGTH_LONG);
        vm.throttleSetting(throttle);
        vm.rudderSetting(rudder);

        // Connect
        Button ConnectBtn = (Button) findViewById(R.id.ConnectBtn);
        ConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText IPeditText = (EditText) findViewById(R.id.ipEditText);
                EditText PortEditText = (EditText) findViewById(R.id.PortEditText);
                String IP = IPeditText.getText().toString();
                int Port = 0;
                if (PortEditText.getText().toString().compareTo("")!=0) {
                    Port = Integer.parseInt(PortEditText.getText().toString());
                }
                try {
                    vm.connectToSocket(IP, Port);
                    TimeUnit.SECONDS.sleep(1);
                    if (vm.checkConnection()){
                        succeeded.show();
                    }
                    else {
                        NotSucceeded.show();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}