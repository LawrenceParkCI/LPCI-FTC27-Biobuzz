package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Motors;

@TeleOp
public class TwoWheelDrive extends OpMode {
    Motors motors = new Motors();

    @Override
    public void init() {
        motors.init(hardwareMap);
    }

    @Override
    public void loop() {
        double axial = -gamepad1.left_stick_y;
        double yaw = gamepad1.left_stick_x;

        double backRightPower = axial - yaw;
        double backLeftPower = axial + yaw;

        double max = Math.max(Math.abs(backLeftPower), Math.abs(backRightPower));

        if (max > 1.0) {
            backRightPower /= max;
            backLeftPower /= max;
        }

        motors.setBackLeftMotor(backLeftPower);
        motors.setBackRightMotor(backRightPower);
    }
}
