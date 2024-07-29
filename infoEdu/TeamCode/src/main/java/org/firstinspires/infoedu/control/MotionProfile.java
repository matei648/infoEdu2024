package org.firstinspires.infoedu.control;

import com.qualcomm.robotcore.util.ElapsedTime;

public class MotionProfile {
    private ElapsedTime timer;

    private double distance;
    private double max_velocity, max_acceleration, elapsed_time;
    private double acceleration_time, decceleraion_time, cruise_time, entire_time;
    private double acceleration_distance, decceleration_distance, cruise_distance, halfway_distance;
    private double velocity, acceleration, position;

    private boolean firstLoop;
    public MotionProfile(double max_velocity, double max_acceleration, double distance) {
        this.max_velocity = max_velocity;
        this.max_acceleration = max_acceleration;
        this.distance = distance;

        halfway_distance = distance / 2;
        acceleration_time = this.max_velocity / max_acceleration;

        if (halfway_distance < 0.5 * max_acceleration * Math.pow(acceleration_time, 2)) {
            acceleration_time = Math.sqrt (2 * halfway_distance / max_acceleration);
        }

        acceleration_distance = 0.5 * max_acceleration * Math.pow(acceleration_time, 2);
        this.max_velocity = max_acceleration * acceleration_time;

        decceleraion_time = acceleration_time; decceleration_distance = acceleration_distance;

        cruise_distance = distance - acceleration_distance - decceleration_distance;
        cruise_time = cruise_distance / this.max_velocity;

        entire_time = acceleration_time + cruise_time + decceleraion_time;

        firstLoop = true;
        timer = new ElapsedTime();
        timer.reset();
    }
    public void update() {
        if(firstLoop == true) {
            timer.reset();
            firstLoop = false;
        }

        elapsed_time = timer.seconds();

        if (elapsed_time < acceleration_time) {
            position = 0.5 * max_acceleration * Math.pow (elapsed_time, 2);
            velocity = max_acceleration * elapsed_time;
            acceleration = max_acceleration;
        }

        else if (elapsed_time < acceleration_time + cruise_time) {
            position = acceleration_distance + max_velocity * (elapsed_time - acceleration_time);
            velocity = max_velocity;
            acceleration = 0;
        }

        else if (elapsed_time < entire_time) {
            position = acceleration_distance + cruise_distance + max_velocity * (elapsed_time - acceleration_time - cruise_time) - 0.5 * max_acceleration * Math.pow (elapsed_time - acceleration_time - cruise_time, 2);
            velocity = max_velocity - max_acceleration * (elapsed_time - acceleration_time - cruise_time);
            acceleration = -max_acceleration;
        }

        else {
            position = distance;
            velocity = 0;
            acceleration = 0;
        }
    }

    public double getPosition() {return position;}
    public double getVelocity() {return velocity;}
    public double getAcceleration() {return acceleration;}
}
