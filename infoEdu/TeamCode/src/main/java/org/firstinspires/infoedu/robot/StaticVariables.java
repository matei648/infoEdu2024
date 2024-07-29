package org.firstinspires.infoedu.robot;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
public abstract class StaticVariables {
    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;
    public static Gamepad gamepad1, gamepad2, lastgamepad1, lastgamepad2;

    public static double robotX, robotY, robotTheta;

    public static double K = 1;

    public static final double chassisVelocity = 240, chassisAcceleration = 240 * K;

    public static void init(HardwareMap hhardwareMap, Telemetry ttelemetry, Gamepad ggamepad1, Gamepad ggamepad2) {
        hardwareMap = hhardwareMap;
        telemetry = ttelemetry;
        gamepad1 = ggamepad1;
        gamepad2 = ggamepad2;

        lastgamepad1 = new Gamepad();
        lastgamepad2 = new Gamepad();
    }

}
