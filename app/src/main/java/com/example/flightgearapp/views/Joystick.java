package com.example.flightgearapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.flightgearapp.view_model.ViewModel;

public class Joystick extends View {

    private int outerCircleRadius;
    private double outerCircleCenterX;
    private double outerCircleCenterY;
    private double innerCircleCenterX;
    private double innerCircleCenterY;
    private int actualCenterX;
    private int actualCenterY;
    private boolean flag;
    private double distance;
    private boolean isPressed;
    private double tempX;
    private double tempY;
    private ViewModel vm;

    public Joystick(Context context) {
        super(context);
        setOnTouchListener(this);
        actualCenterX = getWidth();
        actualCenterY = getHeight();
        flag = true;

    }

    private void setOnTouchListener(Joystick joystick) {

    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        actualCenterX = getWidth();
        actualCenterY = getHeight();
        flag = true;
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        actualCenterX = getWidth();
        actualCenterY = getHeight();
        flag = true;
    }

    public void setVM(ViewModel vm){
        this.vm = vm;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (flag) {
            actualCenterX = getWidth();
            actualCenterY = getHeight();
            innerCircleCenterX = getWidth();
            innerCircleCenterY = getHeight();
            outerCircleRadius = 220;
            outerCircleCenterX = actualCenterX / 2;
            outerCircleCenterY = actualCenterY / 2;
            flag = false;
        }
        Paint paint = new Paint();
        paint.setARGB(255, 189, 189, 189); // Outer Circle color
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
        Paint paint4 = new Paint();
        paint4.setARGB(150,235,235,235);
        paint3.setARGB(150,255,255,255);
        paint2.setARGB(255, 55, 63, 66); // inner circle color
        canvas.drawCircle((int)((actualCenterX / 2)+13), (int)((actualCenterY / 2)+13), outerCircleRadius, paint2);
        canvas.drawCircle(actualCenterX / 2, actualCenterY / 2, outerCircleRadius, paint);
        canvas.drawCircle((int)innerCircleCenterX / 2, (int)innerCircleCenterY / 2, 100, paint2);
        canvas.drawCircle((int)((innerCircleCenterX / 2)-30), (int)((innerCircleCenterY / 2)-30), 24, paint4);
        canvas.drawCircle((int)((innerCircleCenterX / 2)-30), (int)((innerCircleCenterY / 2)-30), 20, paint3);
        invalidate();
    }

    public boolean isPressed(double touchedPosX, double touchedPosY){
        distance = Math.sqrt(Math.pow(outerCircleCenterX-touchedPosX, 2)+Math.pow(outerCircleCenterY-touchedPosY,2));
        return distance<outerCircleRadius;
    }

    public void setIsPressed(boolean bool){
        isPressed = bool;
    }

    public boolean getIsPressed(){
        return this.isPressed;
    }

    public void resetActurator(){
        innerCircleCenterX = actualCenterX;
        innerCircleCenterY = actualCenterY;
    }

    public void setActurator(double touchedPosX, double touchedPosY){
        double deltaX = touchedPosX-outerCircleCenterX;
        double deltaY = touchedPosY-outerCircleCenterY;
        double deltaDist = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));
        if(deltaDist<(outerCircleRadius)){
            update(touchedPosX, touchedPosY);
        }
        else {
            tempX = deltaX/deltaDist;
            tempY = deltaY/deltaDist;
            update();
        }

    }

    public void update(double x, double y){
        innerCircleCenterX = x+outerCircleCenterX;
        innerCircleCenterY = y+outerCircleCenterY;
        vm.setAileron(calculateChange(innerCircleCenterX));
        vm.setElevator(-1*calculateChange(innerCircleCenterY));
    }

    public void update(){
        innerCircleCenterX = outerCircleCenterX+tempX*outerCircleRadius+outerCircleCenterX;
        innerCircleCenterY = outerCircleCenterY+tempY*outerCircleRadius+outerCircleCenterY;
        vm.setAileron(calculateChange(innerCircleCenterX));
        vm.setElevator(calculateChange(innerCircleCenterY));
    }

    public double calculateChange(double touchedPos){
        return (touchedPos-actualCenterX)/outerCircleRadius;
    }

}

