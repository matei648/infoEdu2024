package org.firstinspires.infoedu.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.commands.Commands;
import org.firstinspires.infoedu.objects.chassis.Odometry;
import org.firstinspires.infoedu.objects.intake.ActiveIntake;
import org.firstinspires.infoedu.objects.outtake.Claw;
import org.firstinspires.infoedu.objects.outtake.Lift;
import org.firstinspires.infoedu.objects.outtake.Virtual4Bar;
import org.firstinspires.infoedu.robot.RobotHardware;
import org.firstinspires.infoedu.objects.intake.Extendo;
import org.firstinspires.infoedu.objects.chassis.DriveSubsystem;
import org.firstinspires.infoedu.robot.StaticVariables;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends OpMode {
    private RobotHardware robot;

    private Extendo extendo;
    private ActiveIntake activeIntake;
    private DriveSubsystem driveSubsystem;
    private Odometry odometry;
    private Lift lift;
    private Virtual4Bar v4b;
    private Claw claw;

    private Commands commands;

    private ElapsedTime timer = new ElapsedTime();
    private int cnt, lastcnt;

    private void updateObjects() {
        driveSubsystem.update();
        odometry.update();
        extendo.update();
        activeIntake.update();
        v4b.update();
        claw.update();
        lift.update();
    }

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        extendo = new Extendo(robot);
        activeIntake = new ActiveIntake(robot);
        driveSubsystem = new DriveSubsystem(robot);
        odometry = new Odometry(robot);
        lift = new Lift(robot);
        v4b = new Virtual4Bar(robot);
        claw = new Claw(robot);

        commands = new Commands();

        timer.reset(); cnt = 0; lastcnt = 0;
    }

    @Override
    public void loop() {
        commands.update();

        updateObjects();

        robot.update(); cnt++;

        if(timer.seconds() > 1) {
            timer.reset();
            lastcnt = cnt;
            cnt=0;
        }

        telemetry.addData("FPS", lastcnt);
        telemetry.update();
    }
}
