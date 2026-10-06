package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Motors;

public class BasicDriveOpMode extends OpMode {
    Motors motors = new Motors();

    @Override
    public void init() {
        motors.init(hardwareMap);
    }

    @Override
    public void loop() {
        //gamepad inputs to direction
        double axial = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = -gamepad1.right_stick_x;

        //each motors power relative to direction
        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral - yaw;

        //normalize value
        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        //setting it up
        motors.setFrontLeftMotor(frontLeftPower);
        motors.setFrontRightMotor(frontRightPower);
        motors.setBackRightMotor(backRightPower);
        motors.setBackLeftMotor(backLeftPower);


    }
}
