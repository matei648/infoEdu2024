package org.firstinspires.infoedu.robot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.infoedu.objects.chassis.AbsoluteAnalogEncoder;
import org.firstinspires.infoedu.objects.chassis.SwerveModule;

import java.util.List;

public class RobotHardware {

    // HUBS
    public List<LynxModule> allHubs;

    // ACTIVE INTAKE
    public DcMotor motorExtendo;
    public DcMotor motorIntake;
    public Servo servoIntake;

    // OUTTAKE
    public DcMotor motorLiftLeft, motorLiftRight;
    public Servo servoV4BLeft, servoV4BRight;
    public Servo servoClaw, servoStRelease, servoNdRelease;


    // SWERVE
    public DcMotor motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft;
    public CRServo servoFrontRight, servoFrontLeft, servoBackRight, servoBackLeft;
    public AnalogInput aencoderFrontRight, aencoderFrontLeft, aencoderBackRight, aencoderBackLeft;
    public AbsoluteAnalogEncoder encoderFrontRight, encoderFrontLeft, encoderBackLeft, encoderBackRight;
    public SwerveModule moduleFrontRight, moduleFrontLeft, moduleBackLeft, moduleBackRight;


    // OFFSETS
    public double FrontRight = 211;

    public double BackRight = 191;

    public double FrontLeft = 330;

    public double BackLeft = 215;

    public void init() {

        // HUBS
        allHubs = StaticVariables.hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        // ACTIVE INTAKE
        motorExtendo = StaticVariables.hardwareMap.get(DcMotor.class, "motorExtendo");

        motorExtendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorExtendo.setTargetPosition(15);
        motorExtendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorExtendo.setPower(1);

        motorIntake = StaticVariables.hardwareMap.get(DcMotor.class, "motorIntake");
        motorIntake.setDirection(DcMotorSimple.Direction.REVERSE);

        servoIntake = StaticVariables.hardwareMap.get(Servo.class, "servoIntake");

        // OUTTAKE
        motorLiftLeft = StaticVariables.hardwareMap.get(DcMotor.class, "motorLiftLeft");
        motorLiftRight = StaticVariables.hardwareMap.get(DcMotor.class, "motorLiftRight");

        motorLiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLiftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLiftLeft.setTargetPosition(0);
        motorLiftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLiftRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLiftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLiftRight.setTargetPosition(0);
        motorLiftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        servoV4BLeft = StaticVariables.hardwareMap.get(Servo.class, "servoV4BLeft");
        servoV4BRight = StaticVariables.hardwareMap.get(Servo.class, "servoV4BRight");

        servoClaw = StaticVariables.hardwareMap.get(Servo.class, "servoClaw");
        servoStRelease = StaticVariables.hardwareMap.get(Servo.class, "servoStRelease");
        servoNdRelease = StaticVariables.hardwareMap.get(Servo.class, "servoNdRelease");

        // SWERVE
        motorFrontRight = StaticVariables.hardwareMap.get(DcMotor.class, "motorFrontRight");
        motorFrontLeft = StaticVariables.hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorBackRight = StaticVariables.hardwareMap.get(DcMotor.class, "motorBackRight");
        motorBackLeft = StaticVariables.hardwareMap.get(DcMotor.class, "motorBackLeft");

        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servoFrontRight = StaticVariables.hardwareMap.get(CRServo.class, "servoFrontRight");
        servoFrontLeft = StaticVariables.hardwareMap.get(CRServo.class, "servoFrontLeft");
        servoBackRight = StaticVariables.hardwareMap.get(CRServo.class, "servoBackRight");
        servoBackLeft = StaticVariables.hardwareMap.get(CRServo.class, "servoBackLeft");

        aencoderFrontRight = StaticVariables.hardwareMap.get(AnalogInput.class, "encoderFrontRight");
        aencoderFrontLeft = StaticVariables.hardwareMap.get(AnalogInput.class, "encoderFrontLeft");
        aencoderBackRight = StaticVariables.hardwareMap.get(AnalogInput.class, "encoderBackRight");
        aencoderBackLeft = StaticVariables.hardwareMap.get(AnalogInput.class, "encoderBackLeft");

        encoderFrontRight = new AbsoluteAnalogEncoder(aencoderFrontRight, FrontRight, true);
        encoderFrontLeft = new AbsoluteAnalogEncoder(aencoderFrontLeft, FrontLeft, true);
        encoderBackLeft = new AbsoluteAnalogEncoder(aencoderBackLeft, BackLeft, true);
        encoderBackRight = new AbsoluteAnalogEncoder(aencoderBackRight, BackRight, true);

        moduleFrontRight = new SwerveModule(motorFrontRight, servoFrontRight, encoderFrontRight);
        moduleFrontLeft = new SwerveModule(motorFrontLeft, servoFrontLeft, encoderFrontLeft);
        moduleBackLeft = new SwerveModule(motorBackLeft, servoBackLeft, encoderBackLeft);
        moduleBackRight = new SwerveModule(motorBackRight, servoBackRight, encoderBackRight);

        // ODOMETRY
        StaticVariables.robotX = 0; StaticVariables.robotY = 0; StaticVariables.robotTheta = 0;

        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void update() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        StaticVariables.lastgamepad1.copy(StaticVariables.gamepad1);
        StaticVariables.lastgamepad2.copy(StaticVariables.gamepad2);
    }

}


// ip 192.168.43.1