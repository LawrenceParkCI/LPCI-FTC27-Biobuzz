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
            flywheel.setTopMotorPower(500);
            telemetry.addData("RPM:", flywheel.getTopRPM());

        } else if (gamepad1.b) {
            flywheel.setTopMotorPower(1000);
            telemetry.addData("RPM:", flywheel.getTopRPM());

        } else if (gamepad1.x) {
            flywheel.setTopMotorPower(1500);
            telemetry.addData("RPM:", flywheel.getTopRPM());
        }
    }
}
