package org.firstinspires.infoedu.commands;

import static org.firstinspires.infoedu.robot.StaticVariables.gamepad1;
import static org.firstinspires.infoedu.robot.StaticVariables.lastgamepad1;
import static org.firstinspires.infoedu.robot.StaticVariables.telemetry;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.objects.outtake.Claw;
import org.firstinspires.infoedu.objects.outtake.Lift;
import org.firstinspires.infoedu.objects.outtake.Virtual4Bar;

public class Release {

    public enum ReleaseState {
        OFF,
        READY,
        STPIXEL,
        NDPIXEL,
        WAITING,
        FINISH;
    }

    public static ReleaseState releaseState, nextState;

    public static boolean initiateRelease;
    private ElapsedTime timer = new ElapsedTime();

    public Release() {

        initiateRelease = false;
        releaseState = ReleaseState.OFF;
        nextState = ReleaseState.OFF;

        timer.reset();
    }

    public void update() {

        if (initiateRelease) {

            releaseState = ReleaseState.READY;
            initiateRelease = false;
        }

        if (releaseState == ReleaseState.WAITING && timer.seconds() > 0.3) {
            releaseState = nextState;
        }

        switch (releaseState) {
            case READY:
                Virtual4Bar.v4bState = Virtual4Bar.V4BState.RELEASE;
                Claw.clawState = Claw.ClawState.RELEASE;
                Claw.ndReleaseState = Claw.NdReleaseState.RELEASE_CLOSED;

                releaseState = ReleaseState.WAITING;
                nextState = ReleaseState.STPIXEL;
                timer.reset();
                break;

            case STPIXEL:
                if (gamepad1.right_stick_button && !lastgamepad1.right_stick_button) {
                    Claw.stReleaseState = Claw.StReleaseState.RELEASE_MID;

                    releaseState = ReleaseState.WAITING;
                    nextState = ReleaseState.NDPIXEL;
                    timer.reset();
                }
                break;

            case NDPIXEL:
                if (gamepad1.right_stick_button && !lastgamepad1.right_stick_button) {
                    Claw.stReleaseState = Claw.StReleaseState.RELEASE_MID_OPEN;
                    Claw.ndReleaseState = Claw.NdReleaseState.RELEASE_OPEN;

                    releaseState = ReleaseState.WAITING;
                    nextState = ReleaseState.FINISH;
                    timer.reset();
                }
                break;

            case FINISH:
                if (Lift.liftState == Lift.LiftState.MOVING_DOWN) {
                    Virtual4Bar.v4bState = Virtual4Bar.V4BState.INIT;
                    Claw.clawState = Claw.ClawState.INIT;
                    Claw.stReleaseState = Claw.StReleaseState.INIT;
                    Claw.ndReleaseState = Claw.NdReleaseState.INIT;

                    releaseState = ReleaseState.OFF;
                }
                break;
        }

        telemetry.addData("Rlease State", releaseState);
        telemetry.addLine();
    }
}
