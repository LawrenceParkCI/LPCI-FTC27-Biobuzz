package org.firstinspires.ftc.teamcode.shooter.pidTesting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;

@TeleOp

public class KPTunerBot extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();
    public static double kV1 = 0.000125;
    public double kS1 = 0.154;
    public double kP1 = 0.0;
    public double goalRPM = 1200;
    double []increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementIdx = 2;

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

        if (gamepad1.a) {
            goalRPM = 1500;
        } else if (gamepad1.b) {
            goalRPM = 1200;
        } else if (gamepad1.x) {
            goalRPM = 500;
        }

        double currentIdx = increments[incrementIdx];

        if (gamepad1.dpadUpWasPressed()) {kP1 += currentIdx;}
        if (gamepad1.dpadDownWasPressed()) {kP1 -= currentIdx;}

        double feedForward = (kV1 * goalRPM) + kS1;
        double error = goalRPM - flywheel.getBotRPM();
        double feedBack = error * kP1;

        flywheel.setBotMotorPower(feedForward + feedBack);

        telemetry.addData("Step", "%.6f", currentIdx);
        telemetry.addData("kP", "%.6f", kP1);
        telemetry.addData("RPM", flywheel.getBotRPM());
        telemetry.addData("Ticks per Sec", flywheel.getBotTicksPerSec());

    }
}
