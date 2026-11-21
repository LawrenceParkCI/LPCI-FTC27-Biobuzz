package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutonomousParkRight extends LinearOpMode {

    ChassisMotors motors = new ChassisMotors();
    @Override
    public void runOpMode() {
        motors.init(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            motors.stopAllMotors();
            motors.moveBackward(0.7);
            sleep(750);
            motors.stopAllMotors();
            motors.turnLeft(0.8, 90);
            sleep(10);
            motors.stopAllMotors();
            motors.moveBackward(0.45);
            sleep(2500);
            motors.stopAllMotors();
        }
    }
}
