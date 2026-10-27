package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeMotors {
    private DcMotorEx intake_motor;
    double baseCPM = 28;
    double gearRatio = 5;
    double totalCPM = baseCPM * gearRatio;


    public void init(HardwareMap hwMap) {
        intake_motor = hwMap.get(DcMotorEx.class, "intake_motor");
    }

    public void setIntakeMotorPower(double power) {
        intake_motor.setPower(power);
    }
    public double getIntakeTicksPerSec() {
        return intake_motor.getVelocity();
    }
    public void stopIntakeMotor(){
        intake_motor.setPower(0.0);
    }
    public double getIntakeRPM() {
        return ((getIntakeTicksPerSec()/totalCPM) * 60);
    }
}
