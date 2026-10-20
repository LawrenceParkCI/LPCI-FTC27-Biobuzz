package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;

@TeleOp
public class ShooterTest extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();

    @Override
    public void init() {
        flywheel.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            flywheel.setBothMotorsRPM(500);
        } else if (gamepad1.b) {
            flywheel.setBothMotorsRPM(1200);
        } else if (gamepad1.x) {
            flywheel.setBothMotorsRPM(1500);
        }
        telemetry.addData(" Top RPM:", flywheel.getTopRPM());
        telemetry.addData("Top RPM:", flywheel.getBotRPM());
    }
}
