package org.firstinspires.ftc.teamcode.shooter.pidTesting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;

@TeleOp
public class kPTuner extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();
    public static double kV = 0.000171;
    public double kS = 0.068;
    public double kP = 0.00;
    public double goalRPM = 1200;
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

        if (gamepad1.a) {
            goalRPM = 1500;
        } else if (gamepad1.b) {
            goalRPM = 1200;
        } else if (gamepad1.x) {
            goalRPM = 500;
        }

        double currentIdx = increments[incrementIdx];

        if (gamepad1.dpadUpWasPressed()) {kP += currentIdx;}
        if (gamepad1.dpadDownWasPressed()) {kP -= currentIdx;}

        double feedForward = (kV * goalRPM) + kS;
        double error = goalRPM - flywheel.getTopRPM();
        double feedBack = error * kP;

        flywheel.setTopMotorPower(feedForward + feedBack);

        telemetry.addData("Step", "%.6f", currentIdx);
        telemetry.addData("kS", "%.6f", kS);
        telemetry.addData("kV", "%.6f", kV);
        telemetry.addData("RPM", flywheel.getTopRPM());
        telemetry.addData("Ticks per Sec", flywheel.getTopTicksPerSec());

    }
}
