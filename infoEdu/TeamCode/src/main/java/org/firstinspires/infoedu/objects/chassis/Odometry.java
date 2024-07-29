package org.firstinspires.infoedu.objects.chassis;

import static org.firstinspires.infoedu.robot.StaticVariables.robotTheta;
import static org.firstinspires.infoedu.robot.StaticVariables.robotX;
import static org.firstinspires.infoedu.robot.StaticVariables.robotY;
import static org.firstinspires.infoedu.robot.StaticVariables.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.infoedu.robot.RobotHardware;

public class Odometry {
    private RobotHardware robot;
    private DcMotor encoderLeft, encoderRight, encoderFront;
    private final double L = 31.5, W = 19, WHEEL_RADIUS = 1.75, CPR = 8192 ;
    private final double CT = Math.PI * 2 * WHEEL_RADIUS / CPR; // CM / COUNT
    private double ticksLeft, ticksRight, ticksFront;
    private double lastTicksLeft, lastTicksRight, lastTicksFront;
    private double deltaLeft, deltaRight, deltaFront;
    private double deltaX, deltaY, deltaTheta;

    public Odometry(RobotHardware robot) {
        this.robot = robot;

        encoderLeft = robot.motorFrontLeft;
        encoderRight = robot.motorIntake;
        encoderFront = robot.motorBackLeft;
    }

    public void update () {
        ticksLeft = encoderLeft.getCurrentPosition();
        ticksRight = -encoderRight.getCurrentPosition();
        ticksFront = encoderFront.getCurrentPosition();

        deltaLeft = ticksLeft - lastTicksLeft;
        deltaRight = ticksRight - lastTicksRight;
        deltaFront = ticksFront - lastTicksFront;

        deltaX = CT * 0.5 * (deltaLeft + deltaRight);
        deltaY = CT * (deltaFront - W / L * (deltaRight - deltaLeft));
        deltaTheta = CT / L * (deltaRight - deltaLeft);

        robotX = robotX + deltaX * Math.cos(robotTheta) - deltaY * Math.sin(robotTheta);
        robotY = robotY + deltaX * Math.sin(robotTheta) + deltaY * Math.cos(robotTheta);
        robotTheta = robotTheta + deltaTheta;

        if(robotTheta < 0) robotTheta = robotTheta + 2 * Math.PI;
        if(robotTheta > 2 * Math.PI) robotTheta = robotTheta - 2 * Math.PI;

        lastTicksLeft = ticksLeft;
        lastTicksRight = ticksRight;
        lastTicksFront = ticksFront;

        telemetry.addData("HEADING", Math.toDegrees(robotTheta));
        telemetry.addData("X", robotX);
        telemetry.addData("Y", robotY);
        telemetry.addLine();
    }
}
