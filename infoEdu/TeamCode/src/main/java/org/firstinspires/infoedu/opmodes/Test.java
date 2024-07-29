package org.firstinspires.infoedu.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.infoedu.objects.intake.ActiveIntake;
import org.firstinspires.infoedu.objects.intake.Extendo;
import org.firstinspires.infoedu.robot.RobotHardware;
import org.firstinspires.infoedu.robot.StaticVariables;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Test")
public class Test extends OpMode {
    private RobotHardware robot;
    private Extendo extendo;
    private ActiveIntake activeIntake;

    FtcDashboard dashboard= FtcDashboard.getInstance();
    Telemetry dashboardTelemetry=dashboard.getTelemetry();

    private void updateObjects() {
        extendo.update();
        activeIntake.update();
    }

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        //extendo = new Extendo(robot);
        //activeIntake = new ActiveIntake(robot);
    }

    @Override
    public void loop() {

        //updateObjects();

        robot.motorIntake.setPower(0.6);

        robot.update();

        telemetry.update();
    }
}
