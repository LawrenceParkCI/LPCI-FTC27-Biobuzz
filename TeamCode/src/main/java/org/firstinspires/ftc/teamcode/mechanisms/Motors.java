package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    private DcMotor front_right_motor, front_left_motor, back_right_motor, back_left_motor; //Chassis motors
    private DcMotor top_motor, bottom_motor;

    public void init(HardwareMap hwMap) {
        //---------------CHASSIS---------------//
        front_right_motor = hwMap.get(DcMotor.class, "front_right_motor");
        front_left_motor = hwMap.get(DcMotor.class, "front_left_motor");
        back_right_motor = hwMap.get(DcMotor.class, "back_right_motor");
        back_left_motor = hwMap.get(DcMotor.class, "back_left_motor");

        front_right_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        front_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        //--------------SHOOTER---------------//
        top_motor = hwMap.get(DcMotor.class, "top_motor");
        bottom_motor = hwMap.get(DcMotor.class, "bottom_motor");
    }

    //----------SET CHASSIS POWER------------//
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

    //----------SET SHOOTER POWER----------//
    public void setTopMotor (double power) { top_motor.setPower(power);}
    public void setBottomMotor (double power) { bottom_motor.setPower(power);}
}
