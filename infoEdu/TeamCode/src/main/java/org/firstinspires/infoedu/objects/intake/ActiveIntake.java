package org.firstinspires.infoedu.objects.intake;

import static org.firstinspires.infoedu.robot.StaticVariables.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.robot.RobotHardware;
public class ActiveIntake {
    private enum MotorState {
        FORWARD,
        REVERSE,
        OFF;
    }

    public enum ServoState {
        INTAKE,
        PIXEL_2,
        PIXEL_3,
        PIXEL_4,
        PIXEL_5,
        OUTTAKE,
        RAISE;
    }

    private MotorState motorState;
    public static ServoState servoState;
    private RobotHardware robot;
    private DcMotor motorIntake;
    private Servo servoIntake;

    private final double INTAKE_POSITION = 0.65;
    private final double PIXEL_2_POSITION = 0.65; // DE CALCULAT
    private final double PIXEL_3_POSITION = 0.65; // DE CALCULAT
    private final double PIXEL_4_POSITION = 0.65; // DE CALCULAT
    private final double PIXEL_5_POSITION = 0.65; // DE CALCULAT
    private final double OUTTAKE_POSITION = 0.15; // DE CALCULAT
    private final double RAISE_POSITION = 0.45;
    private final double power = 0.6;
    public static double position = 0;


    private ElapsedTime timer = new ElapsedTime();
    private boolean pressed;
    public static boolean changeIntake;

    public ActiveIntake(RobotHardware robot) {
        this.robot = robot;

        motorIntake = robot.motorIntake;
        servoIntake = robot.servoIntake;

        motorState = MotorState.OFF;
        servoState = ServoState.RAISE;

        servoIntake.setPosition(RAISE_POSITION);

        pressed = false;
        changeIntake = false;
    }

    public void update() {

        if (changeIntake) {
            if (servoState == ServoState.RAISE) servoState = ServoState.INTAKE;
            else servoState = ServoState.RAISE;

            changeIntake = false;
        }

        switch (servoState) {
            case INTAKE:
                servoIntake.setPosition(INTAKE_POSITION);
                motorState = MotorState.FORWARD;
                break;
            case RAISE:
                servoIntake.setPosition(RAISE_POSITION);
                motorState = MotorState.OFF;
                break;
        }

        switch(motorState) {
            case OFF:
                motorIntake.setPower(0);
                break;
            case FORWARD:
                motorIntake.setPower(0.6);
                telemetry.addLine("f");
                break;
            case REVERSE:
                motorIntake.setPower(-power);
                break;
        }
    }
}
