package org.firstinspires.infoedu.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.objects.intake.Extendo;
import org.firstinspires.infoedu.objects.outtake.Claw;
import org.firstinspires.infoedu.objects.outtake.Virtual4Bar;

public class Transfer {

    private ElapsedTime timer = new ElapsedTime();

    public enum TransferState {
        OFF,
        MIDDLE_DOWN,
        DOWN,
        PICK_UP,
        THIRD_UP,
        MIDDLE_UP,
        UP,
        FINISH,
        WAITING;

    }
    public TransferState transferState, nextState;

    public static boolean initiateTransfer = false;

    public Transfer() {

        transferState = TransferState.OFF;
        nextState = TransferState.OFF;
    }

    public void update() {
        if (initiateTransfer == true) {
            transferState = TransferState.WAITING;
            nextState = TransferState.MIDDLE_DOWN;

            Virtual4Bar.v4bState = Virtual4Bar.V4BState.INIT;
            Claw.clawState = Claw.ClawState.INIT;
            Claw.stReleaseState = Claw.StReleaseState.BASIC;
            Claw.ndReleaseState = Claw.NdReleaseState.INIT;
            Extendo.extendoState = Extendo.ExtendoState.READY_FOR_TRANSFER;

            initiateTransfer = false;
            timer.reset();
        }

        if(transferState == TransferState.WAITING) {
            if (nextState == TransferState.MIDDLE_DOWN ) {
                if (Extendo.extendoState == Extendo.ExtendoState.TRANSFER) transferState = nextState;
            }
            else if (timer.seconds() > 0.15) transferState = nextState;

        }

        switch (transferState) {
            case MIDDLE_DOWN:
                Virtual4Bar.v4bState = Virtual4Bar.V4BState.TRANSFER_MID;
                Claw.clawState = Claw.ClawState.TRANSFER_MID;

                transferState = TransferState.WAITING;
                nextState = TransferState.DOWN;
                timer.reset();
                break;

            case DOWN:
                Virtual4Bar.v4bState = Virtual4Bar.V4BState.TRANSFER_DOWN;
                Claw.clawState = Claw.ClawState.TRANSFER_DOWN;
                Claw.stReleaseState = Claw.StReleaseState.TRANSFER;


                transferState = TransferState.WAITING;
                nextState = TransferState.PICK_UP;
                timer.reset();
                break;

            case PICK_UP:
                Claw.stReleaseState = Claw.StReleaseState.CLOSED;

                transferState = TransferState.WAITING;
                nextState = TransferState.THIRD_UP;
                timer.reset();
                break;

            case THIRD_UP:
                Virtual4Bar.v4bState = Virtual4Bar.V4BState.TRANSFER_THIRD_UP;
                Claw.clawState = Claw.ClawState.TRANSFER_THIRD_UP;

                transferState = TransferState.WAITING;
                nextState = TransferState.MIDDLE_UP;
                timer.reset();
                break;

            case MIDDLE_UP:
                Virtual4Bar.v4bState = Virtual4Bar.V4BState.TRANSFER_MID_UP;
                Claw.clawState = Claw.ClawState.TRANSFER_MID_UP;

                transferState = TransferState.WAITING;
                nextState = TransferState.UP;
                timer.reset();
                break;

            case UP:
                Virtual4Bar.v4bState = Virtual4Bar.V4BState.INIT;
                Claw.clawState = Claw.ClawState.INIT;

                transferState = TransferState.WAITING;
                nextState = TransferState.FINISH;
                timer.reset();
                break;

            case FINISH:
                Extendo.extendoState = Extendo.ExtendoState.DOWN;

                transferState = TransferState.OFF;
                break;
        }
    }
}
