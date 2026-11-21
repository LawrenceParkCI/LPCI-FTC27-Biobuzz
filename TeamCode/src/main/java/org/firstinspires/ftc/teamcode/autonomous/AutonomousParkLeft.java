package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;

public class AutonomousParkLeft extends LinearOpMode {


    ChassisMotors motors = new ChassisMotors();
    @Override
    public void runOpMode() {
        motors.init(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            motors.stopAllMotors();
            motors.moveBackward(0.2);
            sleep(3500);
            motors.stopAllMotors();
            motors.turnLeft(0.45, 90);
            sleep(500);
            motors.stopAllMotors();
            motors.moveBackward(0.2);
            sleep(4500);
            motors.stopAllMotors();
        }
    }
}
