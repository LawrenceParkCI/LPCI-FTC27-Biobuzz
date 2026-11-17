package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutonomousPark extends LinearOpMode {

    ChassisMotors motors = new ChassisMotors();
    @Override
    public void runOpMode() {
        motors.init(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            motors.moveBackward(10);
            sleep(500);
            motors.stopAllMotors();
            sleep(1000);
            motors.turnLeft(20, 90);
            sleep(500);
            motors.stopAllMotors();
            sleep(1000);
            motors.moveBackward(20);
            sleep(2000);
            motors.stopAllMotors();
        }
    }
}
