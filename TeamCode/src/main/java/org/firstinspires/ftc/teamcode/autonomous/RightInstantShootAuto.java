package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;
import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;

public class RightInstantShootAuto extends LinearOpMode {
    FlywheelMotors flyWheel = new FlywheelMotors();
    ChassisMotors motors = new ChassisMotors();

    @Override
    public void runOpMode() {
        motors.init(hardwareMap);
        flyWheel.init(hardwareMap);
        waitForStart();
        double nectarGoalRPM = 1500;
        double pollenGoalRPM = 2850;

        if (opModeIsActive()) {
            sleep(3000);
            motors.moveBackward(0.2);
            sleep(1000);
            motors.turnLeft(0.5, 90);
            sleep(500);
            motors.moveBackward(0.2);
            sleep(500);
            motors.turnRight(0.5, 90);
            sleep(500);
            motors.stopAllMotors();
            flyWheel.setBotMotorRPM(pollenGoalRPM);
            flyWheel.setTopMotorRPM(nectarGoalRPM);
            sleep(7000);
            flyWheel.stopMotors();
        }
    }
}
