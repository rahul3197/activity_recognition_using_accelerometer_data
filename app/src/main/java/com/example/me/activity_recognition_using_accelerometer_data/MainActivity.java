package com.example.me.activity_recognition_using_accelerometer_data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.lang.*;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView activity,r_x,r_y,r_z;
    private Sensor accelerometer,gyroscope,gravity_sensor,magnetic_sensor;
    private SensorManager manager;
    private double rx,ry,rz,magnitude_of_velocity=0;
    private int sampling_timeus=2000000,sampling_time_forgyro=1000000;
    //private double velocity_x=0,velocity_y=0,y,velocity_z=0,mag_velocity=0;
//    private float[] mRotationMatrix = new float[9];
    private float[] accel_data= new float[3];
    private float[] accel_data_prev=new float[3];
    private float[] gyro_dat =  new float[3];
    private float[] velocity_data=new float[3];
    private double magnitude_of_acceleration;
    private boolean flag;

    //    private float[] mag_data=new float[3];
//    private float[] accel_data_g=  new float[3];
//    private float[] grav_data  = new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting views
       activity=(TextView) findViewById(R.id.activity);
       r_x=(TextView) findViewById(R.id.rotation_x);
       r_y =(TextView) findViewById(R.id.rotation_y);
       r_z=(TextView)findViewById(R.id.rotation_z);
    //get sensor manager from system services
        manager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //get default sensors
        accelerometer=manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gyroscope= manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        gravity_sensor=manager.getDefaultSensor(Sensor.TYPE_GRAVITY);
//        magnetic_sensor=manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        //register listener

        manager.registerListener(this, accelerometer,sampling_timeus);
        manager.registerListener(this,gyroscope,sampling_time_forgyro);
//        manager.registerListener(this,magnetic_sensor,sampling_timeus);
//     manager.registerListener(this,gravity_sensor,sampling_timeus);
    }
   @Override
   public void onStart(){
        super.onStart();


   }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(flag) {
            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {


                accel_data = event.values;
                magnitude_of_acceleration = Math.sqrt(accel_data[0] * accel_data[0] + accel_data[1] * accel_data[1] + accel_data[2] * accel_data[2]);
                activity_detection(magnitude_of_acceleration);
            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

                r_x.setText(Math.round(event.values[0] * 10000) / 10000.0 + "");
                r_y.setText(Math.round(event.values[1] * 10000) / 10000.0 + "");
                r_z.setText(Math.round(event.values[2] * 10000) / 10000.0 + "");

            }
        }

    }

    private void activity_detection(double magnitude_of_velocity) {


        //double mag_acc  = Math.sqrt(accel_data[0]*accel_data[0]+accel_data[1]*accel_data[1]+accel_data[2]*accel_data[2]);

        if(magnitude_of_velocity>2.5)
           activity.setText("running");
       else if(magnitude_of_velocity>1)
             activity.setText("walking");
       else
            activity.setText("sleeping");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onstartclick(View v)
    {
        flag=true;

    }
    public void  onstopclick(View v)
    {
        flag=false;
        r_x.setText(0+"");
        r_y.setText(0+"");
        r_z.setText(0+"");
        activity.setText("sleeping");
    }


}
