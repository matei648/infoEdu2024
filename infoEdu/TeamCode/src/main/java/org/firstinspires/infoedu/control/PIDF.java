package org.firstinspires.infoedu.control;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDF {
    private double P, I, D, F, alpha = 0.8;

    private double output, integralSum, derivative, last_error, delta_error, current_filter, last_filter, speed, feedforward;
    private boolean reset = true;

    private ElapsedTime timer = new ElapsedTime();

    public void setCoefficients(double P, double I, double D, double F) {
        this.P = P; this.I = I; this.D = D; this.F = F;
    }
    public void setTargetSpeed(double speed) {
        this.speed = speed;
    }
    public void resetReference() {
        reset = true;
    }

    public double getOutput(double error) {

        if (reset == true) {
            integralSum = 0;
            last_error = error;
            last_filter = 0;
            timer.reset();
        }

        delta_error = error - last_error;
        current_filter = last_filter * alpha + delta_error * (1 - alpha);

        derivative = current_filter / timer.seconds();

        integralSum = integralSum + error * timer.seconds();

        if (error < 0) feedforward = -speed;
        else feedforward = speed;

        output = error * P + integralSum * I + derivative * D + feedforward * F;

        last_error = error;
        last_filter = current_filter;
        reset = false;
        timer.reset();

        return output;
    }
}
