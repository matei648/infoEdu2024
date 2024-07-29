package org.firstinspires.infoedu.objects.outtake;

import static org.firstinspires.infoedu.robot.StaticVariables.gamepad1;
import static org.firstinspires.infoedu.robot.StaticVariables.lastgamepad1;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.robot.RobotHardware;
public class Claw {
    private RobotHardware robot;
    private Servo servoClaw, servoStRelease, servoNdRelease;

    public enum ClawState {
        INIT,
        TRANSFER_MID,
        TRANSFER_DOWN,
        TRANSFER_THIRD_UP,
        TRANSFER_MID_UP,
        RELEASE;
    }
    public enum StReleaseState {
        INIT,
        BASIC,
        TRANSFER,
        CLOSED,
        RELEASE_MID,
        RELEASE_MID_OPEN,
        RELEASE_OPEN;

    }
    public enum NdReleaseState {
        INIT,
        RELEASE_CLOSED,
        RELEASE_OPEN;
    }

    public static ClawState clawState;
    public static StReleaseState stReleaseState;
    public static NdReleaseState ndReleaseState;

    private final double CLAW_INIT_POSITION = 0.7;
    private final double CLAW_TRANSFER_MID_POSITION = 0.78;
    private final double CLAW_TRANSFER_DOWN_POSITION = 0.9;
    private final double CLAW_TRANSFER_THIRD_UP_POSITION = 0.86;
    private final double CLAW_TRANSFER_MID_UP_POSITION = 0.81;
    private final double CLAW_RELEASE_POSITION = 0.05;

    private final double STRELEASE_INIT_POSITION = 0.3;
    private final double STRELEASE_BASIC_POSITION = 0.2;
    private final double STRELEASE_TRANSFER_POSITION = 0.24;
    private final double STRELEASE_RELEASE_CLOSED_POSITION = 0.32;
    private final double STRELEASE_RELEASE_MID_POSITION = 0.2;
    private final double STRELEASE_RELEASE_OPEN_POSITION = 0.15;
    private final double STRELEASE_RELEASE_MID_OPEN_POSITION = 0.28;

    private final double NDRELEASE_INIT_POSITION = 0.05;
    private final double NDRELEASE_RELEASE_CLOSED_POSITION = 0.46;
    private final double NDRELEASE_RELEASE_OPEN_POSITION = 0.38;

    public static double clawPosition = 0.7, stReleasePosition = 0.32, ndReleasePosition = 0.05;

    private ElapsedTime timer = new ElapsedTime();

    public Claw(RobotHardware robot) {
        this.robot = robot;

        servoClaw = robot.servoClaw;
        servoStRelease = robot.servoStRelease;
        servoNdRelease = robot.servoNdRelease;

        clawState = ClawState.INIT; servoClaw.setPosition(CLAW_INIT_POSITION);
        stReleaseState = StReleaseState.INIT; servoStRelease.setPosition(STRELEASE_INIT_POSITION);
        ndReleaseState = NdReleaseState.INIT; servoNdRelease.setPosition(NDRELEASE_INIT_POSITION);
    }

    public void update() {

        if(gamepad1.cross == true && lastgamepad1.cross == false) {
            stReleaseState = Claw.StReleaseState.BASIC;
        }

        switch (clawState) {
            case INIT:
                servoClaw.setPosition(CLAW_INIT_POSITION);
                break;

            case TRANSFER_MID:
                servoClaw.setPosition(CLAW_TRANSFER_MID_POSITION);
                break;

            case TRANSFER_DOWN:
                servoClaw.setPosition(CLAW_TRANSFER_DOWN_POSITION);
                break;

            case TRANSFER_THIRD_UP:
                servoClaw.setPosition(CLAW_TRANSFER_THIRD_UP_POSITION);
                break;

            case TRANSFER_MID_UP:
                servoClaw.setPosition(CLAW_TRANSFER_MID_UP_POSITION);
                break;

            case RELEASE:
                servoClaw.setPosition(CLAW_RELEASE_POSITION);
                break;
        }

        switch (stReleaseState) {
            case INIT:
                servoStRelease.setPosition(STRELEASE_INIT_POSITION);
                break;
            case BASIC:
                servoStRelease.setPosition(STRELEASE_BASIC_POSITION);
                break;

            case TRANSFER:
                servoStRelease.setPosition(STRELEASE_TRANSFER_POSITION);
                break;

            case CLOSED:
                servoStRelease.setPosition(STRELEASE_RELEASE_CLOSED_POSITION);
                break;

            case RELEASE_MID:
                servoStRelease.setPosition(STRELEASE_RELEASE_MID_POSITION);
                break;

            case RELEASE_OPEN:
                servoStRelease.setPosition(STRELEASE_RELEASE_OPEN_POSITION);
                break;

            case RELEASE_MID_OPEN:
                servoStRelease.setPosition(STRELEASE_RELEASE_MID_OPEN_POSITION);
        }

        switch (ndReleaseState) {
            case INIT:
                if (timer.seconds() > 0.1)
                    servoNdRelease.setPosition(NDRELEASE_INIT_POSITION);
                break;

            case RELEASE_CLOSED:
                servoNdRelease.setPosition(NDRELEASE_RELEASE_CLOSED_POSITION);
                timer.reset();
                break;

            case RELEASE_OPEN:
                servoNdRelease.setPosition(NDRELEASE_RELEASE_OPEN_POSITION);
                timer.reset();
                break;
        }
    }

    public void test_update() {
        servoClaw.setPosition(clawPosition);
        servoStRelease.setPosition(stReleasePosition);
        servoNdRelease.setPosition(ndReleasePosition);
    }
}
