package org.firstinspires.infoedu.objects.outtake;

import static org.firstinspires.infoedu.commands.Release.initiateRelease;
import static org.firstinspires.infoedu.commands.Release.releaseState;
import static org.firstinspires.infoedu.robot.StaticVariables.gamepad1;
import static org.firstinspires.infoedu.robot.StaticVariables.lastgamepad1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.commands.Release;
import org.firstinspires.infoedu.robot.RobotHardware;

public class Lift {
    private RobotHardware robot;
    private DcMotor motorLiftLeft, motorLiftRight;

    public enum LiftState {
        DOWN,
        MOVING_MID,
        MOVING_UP,
        MID,
        UP,
        MOVING_DOWN;
    }

    public static LiftState liftState;

    private ElapsedTime timer = new ElapsedTime();
    private boolean ok;

    private final int LOWER_BOUND = 10;
    private final int MIDDLE_BOUND = 950;
    private final int UPPER_BOUND = 1640;
    private final int tolerance = 10;

    public Lift(RobotHardware robot) {
        this.robot = robot;

        motorLiftLeft = robot.motorLiftLeft;
        motorLiftRight = robot.motorLiftRight;

        liftState = LiftState.DOWN;
        ok = false;
    }

    public void update() {
        motorLiftLeft.setPower(1);
        motorLiftRight.setPower(1);

        if (liftState == LiftState.MOVING_MID || liftState == LiftState.MOVING_UP && timer.seconds() > 0.1 && ok) {
            initiateRelease = true;
            ok = false;
        }

        switch (liftState) {
            case DOWN:
                if(gamepad1.left_stick_button == true && lastgamepad1.left_stick_button == false) {
                    motorLiftLeft.setTargetPosition(MIDDLE_BOUND);
                    motorLiftRight.setTargetPosition(MIDDLE_BOUND);
                    liftState = LiftState.MOVING_MID;
                    timer.reset(); ok = true;

                }
                else if(gamepad1.left_bumper == true && lastgamepad1.left_bumper == false) {
                    motorLiftLeft.setTargetPosition(UPPER_BOUND);
                    motorLiftRight.setTargetPosition(UPPER_BOUND);
                    liftState = LiftState.MOVING_UP;
                    timer.reset(); ok = true;
                }
                break;
            case MOVING_MID:
                if(Math.abs(motorLiftLeft.getCurrentPosition() - MIDDLE_BOUND) < tolerance) {
                    liftState = LiftState.UP;
                }
                break;
            case MOVING_UP:
                if(Math.abs(motorLiftLeft.getCurrentPosition() - UPPER_BOUND) < tolerance) {
                    liftState = LiftState.UP;
                }
                break;

            case MID:
                motorLiftLeft.setTargetPosition(MIDDLE_BOUND);
                motorLiftRight.setTargetPosition(MIDDLE_BOUND);
                break;

            case UP:
                if(gamepad1.right_bumper == true && lastgamepad1.right_bumper == false) {
                    motorLiftLeft.setTargetPosition(LOWER_BOUND);
                    motorLiftRight.setTargetPosition(LOWER_BOUND);
                    liftState = LiftState.MOVING_DOWN;

                    releaseState = Release.ReleaseState.FINISH;
                }
                break;
            case MOVING_DOWN:
                if(Math.abs(motorLiftLeft.getCurrentPosition() - LOWER_BOUND) < tolerance) {
                    liftState = LiftState.DOWN;
                }
                break;
        }
    }

}
