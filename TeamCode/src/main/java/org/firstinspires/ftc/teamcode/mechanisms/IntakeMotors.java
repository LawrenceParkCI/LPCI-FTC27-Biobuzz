package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeMotors {
    private DcMotorEx intake_motor;
    private final double baseCPM = 28;
    private final double gearRatio = 5;
    private final double totalCPM = baseCPM * gearRatio;

    public void init(HardwareMap hwMap) {
        intake_motor = hwMap.get(DcMotorEx.class, "intake_motor");

        intake_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        intake_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setIntakeMotorPower(double power) {
        intake_motor.setPower(power);
    }

    public double getIntakeTicksPerSec() {
        return intake_motor.getVelocity();
    }

    public void intakeDirReverse() {
        intake_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void intakeDirForward() {
        intake_motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void stopIntakeMotor() {
        intake_motor.setPower(0);
    }

    public double getIntakeRPM() {
        return ((getIntakeTicksPerSec() / totalCPM) * 60);
    }
}
