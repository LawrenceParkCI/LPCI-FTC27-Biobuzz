package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class FlywheelMotors {
    private DcMotorEx nectar_motor, pollen_motor; //Flywheel motors
    //pollen motor
    private double kV = 0.000152, kS = 0.097, kP = 0.001;
    //nectar motor
    private double kV1 = 0.000157, kS1 = 0.068, kP1 = 0.000022;
    double encoderCPM = 28;



    public void init(HardwareMap hwMap) {
        //--------------SHOOTER---------------//
        nectar_motor = hwMap.get(DcMotorEx.class, "nectar_motor");
        pollen_motor = hwMap.get(DcMotorEx.class, "pollen_motor");

        nectar_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        pollen_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        nectar_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        pollen_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        nectar_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        pollen_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    //----------SET MOTOR POWER----------//
    public void setTopMotorPower (double power) { nectar_motor.setPower(power);}
    public void setBotMotorPower (double power) { pollen_motor.setPower(power);}

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
        nectar_motor.setPower(0);
        pollen_motor.setPower(0);
    }
    public void stopTopMotor() {
        nectar_motor.setPower(0);
    }
    public void stopBotMotor() {
        pollen_motor.setPower(0);
    }
    //----------GET MOTOR RPM----------//
    public double getTopTicksPerSec() {
        return nectar_motor.getVelocity();
    }
    public double getBotTicksPerSec() {
        return pollen_motor.getVelocity();
    }
    public double getTopRPM() {
        return ((getTopTicksPerSec()/encoderCPM) * 60);
    }
    public double getBotRPM() {
        return ((getBotTicksPerSec()/encoderCPM) * 60);
    }
}
