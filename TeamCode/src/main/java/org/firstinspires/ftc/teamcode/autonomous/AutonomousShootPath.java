package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;
import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeMotors;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutonomousShootPath extends LinearOpMode {

    ChassisMotors motors = new ChassisMotors();
    FlywheelMotors flyWheel = new FlywheelMotors();
    IntakeMotors intake = new IntakeMotors();

    @Override
    public void runOpMode() {
        motors.init(hardwareMap);
        flyWheel.init(hardwareMap);
        intake.init(hardwareMap);

        waitForStart();

        if (opModeIsActive()){
            flyWheel.setBothMotorsRPM(50);
            sleep(2000);
            flyWheel.stopMotors();
            motors.turnLeft(10, 90);
            sleep(1000);
            motors.moveForward(10);
            sleep(1000);
            motors.turnLeft(10, 180);
            sleep(1000);
            intake.setIntakeMotorPower(50);
            intake.intakeDirForward();
            motors.moveForward(10);
            sleep(1000);
        }
    }
}
