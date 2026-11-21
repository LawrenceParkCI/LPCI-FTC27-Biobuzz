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
        double nectarGoalRPM = 3100;
        double pollenGoalRPM = 2950;

        waitForStart();

        if (opModeIsActive()){
            flyWheel.setBotMotorRPM(pollenGoalRPM);
            flyWheel.setTopMotorRPM(nectarGoalRPM);
            sleep(2000);
            flyWheel.stopMotors();
            motors.turnRight(0.45, 90);
            sleep(500);
            motors.moveForward(0.2);
            sleep(1000);
            intake.setIntakeMotorPower(0.5);
            intake.intakeDirForward();
            motors.moveForward(0.2);
            sleep(1000);
            intake.setIntakeMotorPower(0);
            motors.moveBackward(0.2);
            sleep(1000);
            motors.turnRight(0.45, 90);
            sleep(1000);
            flyWheel.setBotMotorRPM(pollenGoalRPM);
            flyWheel.setTopMotorRPM(nectarGoalRPM);

            sleep(2000);
            flyWheel.stopMotors();
            motors.moveBackward(0.2);
            sleep(3500);
            motors.turnLeft(0.45, 90);
            sleep(500);
            motors.moveBackward(0.2);
            sleep(5000);
            motors.stopAllMotors();

        }
    }
}
