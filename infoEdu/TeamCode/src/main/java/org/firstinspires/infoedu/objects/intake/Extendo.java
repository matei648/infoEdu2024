package org.firstinspires.infoedu.objects.intake;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.infoedu.robot.RobotHardware;

public class Extendo {
    private RobotHardware robot;
    private DcMotor motorExtendo;
    public enum ExtendoState {
        DOWN,
        UP,
        READY_FOR_TRANSFER,
        TRANSFER,
    }
    public static ExtendoState extendoState;

    private final int CPR = 145 * 4, RPM = 1150;
    private final int VELOCITY = CPR * RPM / 60; // counts / second   (max power)

    private final int LOWER_BOUND = 15;
    private final int UPPER_BOUND = 1680;
    private final int TRANSFER_POSITION = 70;

    public Extendo (RobotHardware robot) {
        this.robot = robot;

        motorExtendo = robot.motorExtendo;

        extendoState = ExtendoState.DOWN;
    }

    public void update() {
        motorExtendo.setPower(1);

        switch (extendoState) {
            case DOWN:
                motorExtendo.setTargetPosition(LOWER_BOUND);

                break;

            case UP:
                motorExtendo.setTargetPosition(UPPER_BOUND);

                break;

            case READY_FOR_TRANSFER:
                motorExtendo.setTargetPosition(TRANSFER_POSITION);

                if (Math.abs(motorExtendo.getCurrentPosition() - TRANSFER_POSITION) < 5)
                    extendoState = ExtendoState.TRANSFER;

            case TRANSFER:
                motorExtendo.setTargetPosition(TRANSFER_POSITION);

                break;
        }

    }
}
