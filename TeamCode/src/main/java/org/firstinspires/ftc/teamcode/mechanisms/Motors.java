package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    private DcMotor front_right_motor, front_left_motor, back_right_motor, back_left_motor;
    public void init(HardwareMap hwMap) {
        front_right_motor = hwMap.get(DcMotor.class, "front_right_motor");
        front_left_motor = hwMap.get(DcMotor.class, "front_left_motor");
        back_right_motor = hwMap.get(DcMotor.class, "back_right_motor");
        back_left_motor = hwMap.get(DcMotor.class, "back_left_motor");

        front_right_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        front_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    //methods to set power for each motor
    public void setFrontRightMotor (double power) {
        front_right_motor.setPower(power);
    }
    public void setFrontLeftMotor (double power) {
        front_left_motor.setPower(power);
    }
    public void setBackRightMotor (double power) {
        back_right_motor.setPower(power);
    }
    public void setBackLeftMotor (double power) {
        back_left_motor.setPower(power);
    }
}
