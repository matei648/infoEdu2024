package org.firstinspires.infoedu.objects.chassis;

import static org.firstinspires.infoedu.robot.StaticVariables.gamepad1;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.infoedu.control.PIDF;
public class SwerveModuleTest {
    private DcMotor motor;
    private CRServo servo;
    private AnalogInput encoder;
    private double offset;

    private double error, tolerance = Math.PI / 180, last_target;
    private boolean invert;

    private PIDF pidf;
    public static double P = 0, I = 0, D = 0, F = 0;

    public SwerveModuleTest (DcMotor motor, CRServo servo, AnalogInput encoder, double offset) {
        this.motor = motor;
        this. servo = servo;
        this.encoder = encoder;
        this.offset = offset;

        pidf.setCoefficients(P,I,D,F);
        pidf.setTargetSpeed(1);
    }

    private double getPosition() {
        double position = encoder.getVoltage() / 3.3 * 2 * Math.PI - offset + Math.PI / 2;

        if (position < 0) position = position + 2 * Math.PI;
        else if (position > 2 * Math.PI) position = position - 2 * Math.PI;

        return position;
    }
    private int getCadran(double angle) {
        if (0 <= angle && angle<= Math.PI / 2) return 1;
        else if (Math.PI / 2 <= angle && angle <= Math.PI) return 2;
        else if (Math.PI <= angle && angle <= 3 * Math.PI / 2) return 3;
        else return 4;
    }
    private double getDifference(double target, double position) {
        double difference = target - position;

        if (difference > Math.PI) difference = difference - 2 * Math.PI;
        else if (difference < -Math.PI) difference = difference + 2 * Math.PI;

        return difference;
    }

    private boolean noInput() {
        if (Math.abs(gamepad1.left_stick_x) < 0.01 && Math.abs(gamepad1.left_stick_y) < 0.01 && Math.abs(gamepad1.right_stick_x) < 0.01)
            return true;
        else return false;
    }

    public void drive(double motorPower, double target) {
        double position = getPosition();

        if (Math.abs(target - last_target) > tolerance) {
            pidf.resetReference();
        }

        last_target = target;
        invert = false;

        int positionCadran = getCadran(position);
        int targetCadran = getCadran(target);

        if(positionCadran == targetCadran) {
            error = target - position;
        }
        else if (Math.abs(targetCadran - positionCadran) == 2) {
            target = target + Math.PI;
            if(target > 2 * Math.PI) target = target - 2 * Math.PI;

            error = target - position;
            invert = true;
        }
        else {
            error = getDifference(target, position);

            if(error > Math.PI / 2) {
                error = error - Math.PI;
                invert = true;
            }

            if(error < -Math.PI / 2) {
                error = error + Math.PI;
                invert = true;
            }
        }

        if (Math.abs(error) > tolerance && noInput() == false) {
            if (invert == false) motor.setPower(motorPower);
            else motor.setPower(-motorPower);

            servo.setPower(pidf.getOutput(error));
        }

    }
}
