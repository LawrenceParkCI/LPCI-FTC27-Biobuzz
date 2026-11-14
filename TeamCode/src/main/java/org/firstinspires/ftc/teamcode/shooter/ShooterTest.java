package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;

@TeleOp
@Disabled
public class ShooterTest extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();

    @Override
    public void init() {
        flywheel.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            flywheel.setBothMotorsRPM(2000);
        }
        if (gamepad1.b) {
            flywheel.setBothMotorsRPM(3000);
        }
        if (gamepad1.x) {
            flywheel.setBothMotorsRPM(4000);
        }
        if (gamepad1.y) {
            flywheel.setBothMotorsRPM(5000);
        }
        if (gamepad1.dpad_up) {
            flywheel.stopMotors();
        }

        telemetry.addData("Top RPM:", flywheel.getTopRPM());
        telemetry.addData("Bot RPM:", flywheel.getBotRPM());
    }
}
