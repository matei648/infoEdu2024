package org.firstinspires.infoedu.commands;

import static org.firstinspires.infoedu.robot.StaticVariables.lastgamepad1;
import static org.firstinspires.infoedu.robot.StaticVariables.gamepad1;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.objects.intake.ActiveIntake;
import org.firstinspires.infoedu.objects.intake.Extendo;

public class Commands {
    private Transfer transfer;
    private Release release;

    private boolean pressed, doubleClick;
    private ElapsedTime timer = new ElapsedTime();
    public Commands () {

        transfer = new Transfer();
        release = new Release();

        pressed = false;
        doubleClick = false;
    }

    public void update() {
        if (gamepad1.square == true && lastgamepad1.square == false) {
            Transfer.initiateTransfer = true;
        }

        if (pressed) {
            if (doubleClick) {
                if (Extendo.extendoState == Extendo.ExtendoState.DOWN) {
                    Extendo.extendoState = Extendo.ExtendoState.UP;
                    ActiveIntake.servoState = ActiveIntake.ServoState.INTAKE;
                }
                else {
                    ActiveIntake.servoState = ActiveIntake.ServoState.RAISE;
                    Transfer.initiateTransfer = true;
                }

                pressed = false;
                doubleClick = false;
            }
            else if (timer.seconds() > 0.25) {
                ActiveIntake.changeIntake = true;

                pressed = false;
            }

        }

        if (gamepad1.right_stick_button && !lastgamepad1.right_stick_button && Release.releaseState == Release.ReleaseState.OFF) {
            if (!pressed) {
                pressed = true;
                timer.reset();
            }
            else
            {
                doubleClick = true;
            }
        }

        transfer.update();
        release.update();
    }
}
