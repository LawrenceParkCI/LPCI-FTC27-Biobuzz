package org.firstinspires.ftc.teamcode.pid;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;

@TeleOp
public class kVTuner extends OpMode {
    FlywheelMotors flywheel = new FlywheelMotors();
    public static double kV = 0;
    public double kS = 0.0;
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
            goalRPM = 1200;
        } else if (gamepad1.b) {
            goalRPM = 800;
        }

        double currentIdx = increments[incrementIdx];

        if (gamepad1.dpadUpWasPressed()) {kV += currentIdx;}
        if (gamepad1.dpadDownWasPressed()) {kV -= currentIdx;}

        double power = (kV * goalRPM) + kS;
        flywheel.setBothMotors(power);

        telemetry.addData("Step", "%.6f", currentIdx);
        telemetry.addData("kV", "%.6f", kV);
        telemetry.addData("RPM", flywheel.getRPM());
        telemetry.addData("Ticks per Sec", flywheel.getTicksPerSec());

    }
}
