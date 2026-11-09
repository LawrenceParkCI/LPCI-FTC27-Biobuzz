package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class FlywheelMotors {
    private DcMotorEx top_motor, bottom_motor; //Flywheel motors


    private double kV1 = 0.000157, kS1 = 0.068, kP1 = 0.000022;
    private double kV = 0.000133, kS = 0.097, kP = 0.000001;
    double encoderCPM = 28;



    public void init(HardwareMap hwMap) {
        //--------------SHOOTER---------------//
        top_motor = hwMap.get(DcMotorEx.class, "top_motor");
        bottom_motor = hwMap.get(DcMotorEx.class, "bottom_motor");

        top_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bottom_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        top_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bottom_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        top_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        bottom_motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    //----------SET MOTOR POWER----------//
    public void setTopMotorPower (double power) { top_motor.setPower(power);}
    public void setBotMotorPower (double power) { bottom_motor.setPower(power);}

    //--------------SET MOTOR RPM-----------------//
    public void setBothMotorsRPM (double targetRPM) {
        double error = targetRPM - getTopRPM();
        double error1 = targetRPM - getBotRPM();

        double ff = (kV * targetRPM) + kS;
        double ff1 = (kV1 * targetRPM) + kS1;

        double fb = error * kP;
        double fb1 = error1 * kP1;

        double power = ff + fb;
        double power1 = ff1 + fb1;

        setTopMotorPower(power);
        setBotMotorPower(power1);
    }
    public void setBotMotorRPM (double targetRPM) {
        double error1 = targetRPM - getBotRPM();

        double ff1 = (kV1 * targetRPM) + kS1;
        double fb1 = error1 * kP1;

        double power1 = ff1 + fb1;
        setBotMotorPower(power1);
    }
    public void setTopMotorRPM (double targetRPM) {
        double error = targetRPM - getTopRPM();

        double ff = (kV * targetRPM) + kS;
        double fb = error * kP;

        double power = ff + fb;
        setTopMotorPower(power);
    }

    //-------------------------STOP MOTOR--------------------//
    public void stopMotors () {
        top_motor.setPower(0);
        bottom_motor.setPower(0);
    }
    public void stopTopMotor() {
        top_motor.setPower(0);
    }
    public void stopBotMotor() {
        bottom_motor.setPower(0);
    }
    //----------GET MOTOR RPM----------//
    public double getTopTicksPerSec() {
        return top_motor.getVelocity();
    }
    public double getBotTicksPerSec() {
        return bottom_motor.getVelocity();
    }
    public double getTopRPM() {
        return ((getTopTicksPerSec()/encoderCPM) * 60);
    }
    public double getBotRPM() {
        return ((getBotTicksPerSec()/encoderCPM) * 60);
    }
}
