package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;
@TeleOp
@Disabled
public class doubleShooter extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();

    @Override
    public void init() {
        flywheel.init(hardwareMap);
    }

    @Override
    public void loop() {
        double nectarGoalRPM = 4000;
        double pollenGoalRPM = 4000;

        if (gamepad1.a) {
            flywheel.setBotMotorRPM(pollenGoalRPM);
        }
        if (gamepad1.b) {

            flywheel.setTopMotorRPM(nectarGoalRPM);
        }
        if (gamepad1.x) {
            flywheel.stopBotMotor();
        }
        if (gamepad1.y) {
            flywheel.stopTopMotor();

            telemetry.addData("Top RPM:", flywheel.getTopRPM());
            telemetry.addData("Bot RPM:", flywheel.getBotRPM());
        }
    }
}
