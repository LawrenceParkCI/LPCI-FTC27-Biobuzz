package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeMotors {
    private DcMotorEx intake_motor;
    // Stores the motor's base counts per revolution
    private final double baseCPM = 28;
    // Stores the gear ratio of the motor
    private final double gearRatio = 5;
    // Calculates the total counts per motor revolution
    private final double totalCPM = baseCPM * gearRatio;

    // Initializes and configures the intake motor
    public void init(HardwareMap hwMap) {
        // Maps the intake motor to the hardware configuration
        intake_motor = hwMap.get(DcMotorEx.class, "intake_motor");

        // Allows the motor to spin freely when power is removed
        intake_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Resets the motor encoder
        intake_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Enables encoder-based motor control
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    // Sets the power of the intake motor
    public void setIntakeMotorPower(double power) {
        intake_motor.setPower(power);
    }
    // Returns the intake motor's velocity in ticks per second
    public double getIntakeTicksPerSec() {
        return intake_motor.getVelocity();
    }
    // Reverses the intake motor's direction
    public void intakeDirReverse() {
        intake_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    // Sets the intake motor's direction to forward
    public void intakeDirForward() {
        intake_motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    // Stops the intake motor
    public void stopIntakeMotor() {
        intake_motor.setPower(0);
    }
    // Converts the motor's velocity from ticks per second to RPM
    public double getIntakeRPM() {
        return ((getIntakeTicksPerSec() / totalCPM) * 60);
    }
}
