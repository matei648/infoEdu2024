package org.firstinspires.infoedu.control;

import static org.firstinspires.infoedu.robot.StaticVariables.chassisAcceleration;
import static org.firstinspires.infoedu.robot.StaticVariables.chassisVelocity;
import static org.firstinspires.infoedu.robot.StaticVariables.robotX;
import static org.firstinspires.infoedu.robot.StaticVariables.robotY;

public class LinearPath {


    private double destination_x, destination_y, destination_theta, tolerance = 3;
    private double distance, alpha;

    private double target_x, target_y, output_velocity_x, output_velocity_y, velocity;
    public static double K = 2, Kv = 1;

    private MotionProfile motionProfile;

    public LinearPath(double destination_x, double destination_y, double destination_theta) {
        this.destination_x = destination_x;
        this.destination_y = destination_y;
        this.destination_theta = destination_theta;

        distance = Math.sqrt(Math.pow(destination_x, 2) + Math.pow(destination_y, 2));
        alpha = Math.atan2(destination_y, destination_x); if (alpha < 0) alpha += 2 * Math.PI;

        motionProfile = new MotionProfile(chassisVelocity, chassisAcceleration, distance);
    }

    public void update() {
        if (Math.abs(robotX - destination_x) < tolerance && Math.abs(robotY - destination_y) < tolerance) return;

        motionProfile.update();

        target_x = Math.cos(alpha) * motionProfile.getPosition();
        target_y = Math.sin(alpha) * motionProfile.getPosition();

        output_velocity_x = Math.cos(alpha) * motionProfile.getVelocity() * Kv + (target_x - robotX) * K;
        output_velocity_y = Math.sin(alpha) * motionProfile.getVelocity() * Kv + (target_y - robotY) * K;

        output_velocity_x = output_velocity_x / chassisVelocity;
        output_velocity_y = output_velocity_y / chassisVelocity;

        velocity = Math.sqrt(Math.pow(output_velocity_x, 2) + Math.pow(output_velocity_y, 2));
        if (velocity > 1) {
            output_velocity_x /= velocity;
            output_velocity_y /= velocity;
        }

    }

    public double getOutput_velocity_x() {
        return output_velocity_x;
    }

    public double getOutput_velocity_y() {
        return output_velocity_y;
    }
}
