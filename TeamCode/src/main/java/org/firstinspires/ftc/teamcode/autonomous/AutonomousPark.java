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
            motors.moveForward(10);
            sleep(1000);
            motors.turnLeft(10, 90);
            sleep(1000);
            motors.moveForward(10);
            sleep(2000);
            motors.stopAllMotors();
        }
    }
}
