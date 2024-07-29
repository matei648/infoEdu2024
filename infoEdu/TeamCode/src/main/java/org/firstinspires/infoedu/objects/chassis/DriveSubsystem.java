package org.firstinspires.infoedu.objects.chassis;

import static org.firstinspires.infoedu.robot.StaticVariables.gamepad1;
import static org.firstinspires.infoedu.robot.StaticVariables.lastgamepad1;
import static org.firstinspires.infoedu.robot.StaticVariables.robotTheta;
import static java.lang.Math.atan2;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.infoedu.robot.RobotHardware;

public class DriveSubsystem {

    private RobotHardware robot;
    private double L = 32, W=25;
    private double R = Math.hypot(L/2, W/2);
    private SwerveModule moduleFrontRight, moduleFrontLeft, moduleBackLeft, moduleBackRight;
    private double vx,vy,w,lastvx,lastvy,lastw,lastbotHeading,ax,ay,e,botHeading,dbotHeading;
    private double[] vm=new double[5];
    private double[] am=new double[5];
    private double[] wm=new double[5];
    private double[] thetam=new double[5];
    private double[] rx=new double[5];
    private double[] ry=new double[5];
    private double[] beta=new double[5];
    private static ElapsedTime dt=new ElapsedTime();

    public DriveSubsystem(RobotHardware robot){
        this.robot = robot;

        moduleBackRight = robot.moduleBackRight;
        moduleBackLeft = robot.moduleBackLeft;
        moduleFrontRight = robot.moduleFrontRight;
        moduleFrontLeft = robot.moduleFrontLeft;

        rx[0] = W / 2; ry[0] = L / 2; beta[0] = atan2(ry[0], rx[0]); if (beta[0] < 0) beta[0] = beta[0] + 2 * Math.PI;
        rx[1] = - W / 2; ry[1] = L / 2; beta[1] = atan2(ry[1], rx[1]); if (beta[1] < 0) beta[1] = beta[1] + 2 * Math.PI;
        rx[2] = - W / 2; ry[2] = - L / 2; beta[2] = atan2(ry[2], rx[2]); if (beta[2] < 0) beta[2] = beta[2] + 2 * Math.PI;
        rx[3] = W / 2; ry[3] = - L / 2; beta[3] = atan2(ry[3], rx[3]); if (beta[3] < 0) beta[3] = beta[3] + 2 * Math.PI;
        lastvx = 0; lastvy = 0;
        lastw = 0; lastbotHeading = 0;
        dt.reset();
    }
    public void setKinematics(int cnt)
    {
        beta[cnt] = beta[cnt] + dbotHeading; if (beta[cnt] < 0) beta[cnt] = beta[cnt] + 2 * Math.PI;
        rx[cnt] = Math.cos(beta[cnt]); ry[cnt] = Math.sin(beta[cnt]);

        double vmx = vx - ry[cnt] * w;
        double vmy = vy + rx[cnt] * w;

        vm[cnt] = Math.sqrt(vmx * vmx + vmy * vmy);
        thetam[cnt] = atan2(vmy, vmx);
        if (thetam[cnt] < 0) thetam[cnt] = thetam[cnt] + 2 * Math.PI;

        double amx = ax - e * ry[cnt] - w * w * rx[cnt];
        double amy = ay + e * rx[cnt] - w * w * ry[cnt];

        am[cnt] = Math.cos(thetam[cnt]) * amx + Math.sin(thetam[cnt]) * amy;
        wm[cnt] = - Math.sin(thetam[cnt]) * amx / vm[cnt] + Math.cos(thetam[cnt]) * amy / vm[cnt];

        double wr = dbotHeading / dt.time();
        wm[cnt] = wm[cnt] - wr;
        thetam[cnt] = thetam[cnt] - botHeading;
        if (thetam[cnt] < 0) thetam[cnt] = thetam[cnt] + 2 * Math.PI;

        vm[cnt] = vm[cnt] + am[cnt] * dt.time();
        thetam[cnt] = thetam[cnt] + wm[cnt] * dt.time();
        if (thetam[cnt] > 2 * Math.PI) thetam[cnt] = thetam[cnt] - 2 * Math.PI;
        if (thetam[cnt] < 0) thetam[cnt] = thetam[cnt] + 2 * Math.PI;

        thetam[cnt] = Math.toDegrees(thetam[cnt]);
        if ( 0 <= thetam[cnt] && thetam[cnt] <= 90) thetam[cnt] = 90 - thetam[cnt];
        else if (90 < thetam[cnt] && thetam[cnt] <= 270) thetam[cnt] = -(thetam[cnt] - 90);
        else thetam[cnt] = 360 - thetam[cnt] + 90;
    }
    public void update(){

        if(gamepad1.start == true && lastgamepad1.start == false) {

            rx[0] = W / 2; ry[0] = L / 2; beta[0] = atan2(ry[0], rx[0]); if (beta[0] < 0) beta[0] = beta[0] + 2 * Math.PI;
            rx[1] = - W / 2; ry[1] = L / 2; beta[1] = atan2(ry[1], rx[1]); if (beta[1] < 0) beta[1] = beta[1] + 2 * Math.PI;
            rx[2] = - W / 2; ry[2] = - L / 2; beta[2] = atan2(ry[2], rx[2]); if (beta[2] < 0) beta[2] = beta[2] + 2 * Math.PI;
            rx[3] = W / 2; ry[3] = - L / 2; beta[3] = atan2(ry[3], rx[3]); if (beta[3] < 0) beta[3] = beta[3] + 2 * Math.PI;

            lastvx = 0; lastvy = 0; lastw = 0;

            robotTheta = 0; lastbotHeading = 0;
        }

        vx = gamepad1.left_stick_x;
        vy = -gamepad1.left_stick_y;
        w = -gamepad1.right_stick_x;

        botHeading = robotTheta;

        dbotHeading = botHeading - lastbotHeading;
        if (dbotHeading > Math.PI) dbotHeading = dbotHeading - 2 * Math.PI;
        if (dbotHeading < -Math.PI) dbotHeading = dbotHeading + 2 * Math.PI;

        ax = (vx - lastvx) / dt.time();
        ay = (vy - lastvy) / dt.time();
        e = (w - lastw) / dt.time();

        setKinematics(0); setKinematics(1);
        setKinematics(2); setKinematics(3);

        double maxx=vm[0];
        if (vm[1] > maxx) maxx = vm[1]; if (vm[2] > maxx) maxx = vm[2]; if (vm[3] > maxx) maxx = vm[3];
        if (maxx > 1)
        {
            vm[0] = vm[0] / maxx; vm[1] = vm[1] / maxx;
            vm[2] = vm[2] / maxx; vm[3] = vm[3] / maxx;
        }

        //vm[0] = 0; vm[1] = 0; vm[2] = 0; vm[3] = 0;

        moduleFrontRight.drive(vm[0],thetam[0]);
        moduleFrontLeft.drive(vm[1],thetam[1]);
        moduleBackLeft.drive(vm[2],thetam[2]);
        moduleBackRight.drive(vm[3],thetam[3]);

        lastvx = vx; lastvy = vy; lastw = w;
        lastbotHeading = botHeading;
        dt.reset();
    }

    public void updateAuto (double vxx, double vyy, double ww) {
        vx = vxx;
        vy = vyy;
        w = ww;

        botHeading = robotTheta;

        dbotHeading = botHeading - lastbotHeading;
        if (dbotHeading > Math.PI) dbotHeading = dbotHeading - 2 * Math.PI;
        if (dbotHeading < -Math.PI) dbotHeading = dbotHeading + 2 * Math.PI;

        ax = (vx - lastvx) / dt.time();
        ay = (vy - lastvy) / dt.time();
        e = (w - lastw) / dt.time();

        setKinematics(0); setKinematics(1);
        setKinematics(2); setKinematics(3);

        double maxx=vm[0];
        if (vm[1] > maxx) maxx = vm[1]; if (vm[2] > maxx) maxx = vm[2]; if (vm[3] > maxx) maxx = vm[3];
        if (maxx > 1)
        {
            vm[0] = vm[0] / maxx; vm[1] = vm[1] / maxx;
            vm[2] = vm[2] / maxx; vm[3] = vm[3] / maxx;
        }

        moduleFrontRight.drive(vm[0],thetam[0]);
        moduleFrontLeft.drive(vm[1],thetam[1]);
        moduleBackLeft.drive(vm[2],thetam[2]);
        moduleBackRight.drive(vm[3],thetam[3]);

        lastvx = vx; lastvy = vy; lastw = w;
        lastbotHeading = botHeading;
        dt.reset();
    }
}
