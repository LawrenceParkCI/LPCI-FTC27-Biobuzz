package org.firstinspires.ftc.teamcode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;

public class InstantShootAuto extends LinearOpMode {
    FlywheelMotors flyWheel = new FlywheelMotors();
    @Override
    public void runOpMode() {
        waitForStart();
        double nectarGoalRPM = 1995;
        double pollenGoalRPM = 2670;
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
