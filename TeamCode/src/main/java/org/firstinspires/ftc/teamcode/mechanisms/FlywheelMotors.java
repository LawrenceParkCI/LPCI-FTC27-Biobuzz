package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelMotors {
    private DcMotor top_motor, bottom_motor; //Flywheel motors
    private double kV, kS = 0.0, kP;
    double encoderCPM = 28;

    public void init(HardwareMap hwMap) {
        //--------------SHOOTER---------------//
        top_motor = hwMap.get(DcMotor.class, "top_motor");
        bottom_motor = hwMap.get(DcMotor.class, "bottom_motor");

        top_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bottom_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        top_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        bottom_motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    //----------SET SHOOTER POWER----------//
    public void setTopMotor (double power) { top_motor.setPower(power);}
    public void setBottomMotor (double power) { bottom_motor.setPower(power);}
    public void setBothMotors (double power) {
        top_motor.setPower(power);
        bottom_motor.setPower(power);
    }

    //----------GET SHOOTER INFO----------//
    public double getTicksPerSec() {
        return top_motor.getMotorType().getTicksPerRev();
    }
    public double getRPM() {
        return ((getTicksPerSec()/encoderCPM) * 60);
    }
}
