package org.firstinspires.ftc.teamcode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class InstantShootAuto extends LinearOpMode {
    FlywheelMotors flyWheel = new FlywheelMotors();
    @Override
    public void runOpMode() {
        waitForStart();
        double nectarGoalRPM = 3100;
        double pollenGoalRPM = 2950;
        flyWheel.init(hardwareMap);

        if (opModeIsActive()) {
            sleep(2000);
            flyWheel.setBotMotorRPM(pollenGoalRPM);
            flyWheel.setTopMotorRPM(nectarGoalRPM);
            sleep(10000);
            flyWheel.stopMotors();
        }
    }
}
