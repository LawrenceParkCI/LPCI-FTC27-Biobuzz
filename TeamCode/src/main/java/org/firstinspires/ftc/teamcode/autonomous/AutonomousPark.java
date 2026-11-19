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
            motors.stopAllMotors();
            motors.moveBackward(1);
            sleep(4000);
            motors.stopAllMotors();
            motors.turnLeft(0.2, 90);
            sleep(1000);
            motors.stopAllMotors();
            motors.moveBackward(0.5);
            sleep(2000);
            motors.stopAllMotors();
        }
    }
}
