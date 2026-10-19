package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelMotors {
    private DcMotorEx top_motor, bottom_motor; //Flywheel motors
    private double kV = 0.000151, kS = 0.068, kP = 0.00002;
    private double kV1 = 0.0001, kS1 = 0.154, kP1 = 0.000001;
    double encoderCPM = 28;

    public void init(HardwareMap hwMap) {
        //--------------SHOOTER---------------//
        top_motor = hwMap.get(DcMotorEx.class, "top_motor");
        bottom_motor = hwMap.get(DcMotorEx.class, "bottom_motor");

        top_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bottom_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        top_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        bottom_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    //----------SET SHOOTER POWER----------//
    public void setTopMotorPower (double power) { top_motor.setPower(power);}
    public void setBotMotorPower (double power) { bottom_motor.setPower(power);}
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

    public void stopMotors () {
        top_motor.setPower(0);
        bottom_motor.setPower(0);
    }

    //----------GET SHOOTER INFO----------//
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
