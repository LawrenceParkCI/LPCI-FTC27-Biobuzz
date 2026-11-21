package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutonomousParkRightDistanceBased extends LinearOpMode {

    ChassisMotors motors = new ChassisMotors();
    @Override
    public void runOpMode() {
        motors.init(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            motors.stopAllMotors();
            sleep(500);
            motors.moveForwardDistance(0.2, 3);
            sleep(500);
            motors.stopAllMotors();
            motors.turnLeft(0.45, 90);
            motors.moveBackwardDistance(0.3, 5.5);
            sleep(500);
            motors.stopAllMotors();
        }
    }
}
