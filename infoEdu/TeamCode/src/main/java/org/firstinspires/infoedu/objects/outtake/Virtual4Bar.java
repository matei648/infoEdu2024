package org.firstinspires.infoedu.objects.outtake;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.infoedu.robot.RobotHardware;
public class Virtual4Bar {
    private RobotHardware robot;
    private Servo servoV4BLeft, servoV4BRight;

    public enum V4BState {
        INIT,
        TRANSFER_MID,
        TRANSFER_DOWN,
        TRANSFER_THIRD_UP,
        TRANSFER_MID_UP,
        RELEASE;
    }

    public static V4BState v4bState;

    private final double INIT_POSITION = 0.3;
    private final double TRANSFER_MID_POSITION = 0.2;
    private final double TRANSFER_DOWN_POSITION = 0.158;
    private final double TRANSFER_THIRD_UP_POSITION = 0.2;
    private final double TRANSFER_MID_UP_POSITION = 0.25;
    private final double RELEASE_POSITION = 0.8;

    public static double position = 0.3;


    private void setPosition(double position) {
        servoV4BRight.setPosition(position);
        servoV4BLeft.setPosition(position);
    }

    public Virtual4Bar(RobotHardware robot) {
        this.robot = robot;

        servoV4BLeft = robot.servoV4BLeft;
        servoV4BRight = robot.servoV4BRight;

        v4bState = V4BState.INIT;
        setPosition(INIT_POSITION);

    }

    public void update() {
        switch (v4bState) {
            case INIT:
                setPosition(INIT_POSITION);
                break;

            case TRANSFER_MID:
                setPosition(TRANSFER_MID_POSITION);
                break;

            case TRANSFER_DOWN:
                setPosition(TRANSFER_DOWN_POSITION);
                break;

            case TRANSFER_THIRD_UP:
                setPosition(TRANSFER_THIRD_UP_POSITION);
                break;

            case TRANSFER_MID_UP:
                setPosition(TRANSFER_MID_UP_POSITION);
                break;

            case RELEASE:
                setPosition(RELEASE_POSITION);
                break;
        }
    }

    public void test_update() {
        setPosition(position);
    }
}
