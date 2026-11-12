package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;

@TeleOp
public class BasicDriveOpMode extends OpMode {
    ChassisMotors chassisMotors = new ChassisMotors();

    @Override
    public void init() {
        chassisMotors.init(hardwareMap);
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
        chassisMotors.setFrontLeftMotor(frontLeftPower);
        chassisMotors.setFrontRightMotor(frontRightPower);
        chassisMotors.setBackRightMotor(backRightPower);
        chassisMotors.setBackLeftMotor(backLeftPower);
    }
}
