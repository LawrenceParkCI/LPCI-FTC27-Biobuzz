package org.firstinspires.ftc.teamcode.shooter.pidTesting;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;
@TeleOp
@Disabled
public class kSTunerBot extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();
    public static double kS1 = 0.097;
    double []increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementIdx = 4;

    @Override
    public void init() {
        flywheel.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpadRightWasPressed() && incrementIdx < 4) {
            incrementIdx++;
        } else if (gamepad1.dpadLeftWasPressed() && incrementIdx > 0) {
            incrementIdx--;
        }

        double currentIdx = increments[incrementIdx];

        if (gamepad1.dpadUpWasPressed()) {kS1 += currentIdx;}
        if (gamepad1.dpadDownWasPressed()) {kS1 -= currentIdx;}

        flywheel.setBotMotorPower(kS1);

        telemetry.addData("Step", "%.6f", currentIdx);
        telemetry.addData("kS", "%.6f", kS1);
        telemetry.addData("RPM", flywheel.getBotRPM());
        telemetry.addData("Ticks per Sec", flywheel.getBotTicksPerSec());

    }
}
