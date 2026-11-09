package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;
@TeleOp

public class doubleShooter extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();
    @Override
    public void init() {
        flywheel.init(hardwareMap);
    }

    @Override
    public void loop() {
        double nectarGoalRPM = 2500;
        double pollenGoalRPM = 3000;

        if (gamepad1.a) {
            flywheel.setTopMotorRPM(pollenGoalRPM);
        }
        if (gamepad1.b) {
            flywheel.setBotMotorRPM(nectarGoalRPM);
        }
        if (gamepad1.x) {
            flywheel.stopTopMotor();
        }
        if (gamepad1.y) {
            flywheel.stopBotMotor();
        }

        if (gamepad1.dpad_up) {
            nectarGoalRPM += 500;
            flywheel.setTopMotorRPM(nectarGoalRPM);
        }
        if (gamepad1.dpad_down) {
            nectarGoalRPM -= 500;
            flywheel.setTopMotorRPM(nectarGoalRPM);
        }
        if (gamepad1.dpad_right) {
            pollenGoalRPM += 500;
            flywheel.setTopMotorRPM(pollenGoalRPM);
        }
        if (gamepad1.dpad_left) {
            pollenGoalRPM -= 500;
            flywheel.setTopMotorRPM(pollenGoalRPM);
        }

        telemetry.addData("Top RPM:", flywheel.getTopRPM());
        telemetry.addData("Bot RPM:", flywheel.getBotRPM());
    }
}
