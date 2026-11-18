package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.mechanisms.PIDFController;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;


@TeleOp(name = "Tuning", group = "Test")
public class WheelPIDFTuning extends OpMode {
    ChassisMotors chassis = new ChassisMotors();
    private static final double GOAL_A = 250;
    private static final double GOAL_B = 150;
    private static final double GOAL_X = 75;

    private static final String[] WHEEL_NAMES = {"Front Left", "Front Right", "Back Left", "Back Right"};
    private static final String[] WHEEL_SHORT = {"FL", "FR", "BL", "BR"};
    private static final String[] PARAM_NAMES = {"kS", "kV", "kP", "kI", "kD"};
    private static final int KS = 0, KV = 1, KP = 2, KI = 3, KD = 4;

    // gains[param][wheel]
    private final double[][] gains = {
            {0.011, 0.036, 0.011, 0.011}, //kS
            {0.003042, 0.003, 0.003040, 0.003050}, // kV
            {0.000001, 0.000001, 0.000001, 0.000001}, // kP
            {0, 0, 0, 0}, // kI
            {0, 0, 0, 0} // kD
    };

    private final PIDFController[] pids = {
            new PIDFController(0), new PIDFController(0), new PIDFController(0), new PIDFController(0)
    };

    private final double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    private int incrementIdx = 2;

    private int wheel = 0;
    private int param = KS;

    private double goalRPM = 0;
    private double lastGoal = 0;
    private int lastWheel = 0;

    @Override
    public void init() {
        chassis.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        //----------selection----------
        if (gamepad1.leftBumperWasPressed()) wheel = (wheel + 3) % 4;
        if (gamepad1.rightBumperWasPressed()) wheel = (wheel + 1) % 4;
        if (gamepad1.yWasPressed()) param = (param + 1) % PARAM_NAMES.length;

        if (gamepad1.dpadRightWasPressed() && incrementIdx < increments.length - 1) {
            incrementIdx++;
        } else if (gamepad1.dpadLeftWasPressed() && incrementIdx > 0) {
            incrementIdx--;
        }
        double step = increments[incrementIdx];

        //----------change selected value----------
        if (gamepad1.dpadUpWasPressed()) {
            gains[param][wheel] += step;
        }
        if (gamepad1.dpadDownWasPressed()) {
            gains[param][wheel] = Math.max(0, gains[param][wheel] - step);
        }

        //----------goal RPM----------
        if (gamepad1.a && !gamepad1.start) {
            goalRPM = GOAL_A;
        } else if (gamepad1.b && !gamepad1.start) {
            goalRPM = GOAL_B;
        } else if (gamepad1.x) {
            goalRPM = GOAL_X;
        } else if (gamepad1.left_trigger > 0.5) {
            goalRPM = 0;
        }

        //clear the PID history when wheel changes
        if (goalRPM != lastGoal || wheel != lastWheel) {
            pids[wheel].reset();
            lastGoal = goalRPM;
            lastWheel = wheel;
        }

        //----------run the selected wheel----------
        double rpm = chassis.getWheelRPM(wheel);
        double power = 0;

        if (goalRPM > 0) {
            pids[wheel].setCoefficients(gains[KP][wheel], gains[KI][wheel], gains[KD][wheel], gains[KV][wheel]);
            power = pids[wheel].calculate(goalRPM, rpm) + gains[KS][wheel] * Math.signum(goalRPM);
            power = Math.max(-1.0, Math.min(1.0, power));
        }

        for (int i = 0; i < 4; i++) {
            chassis.setWheelPower(i, i == wheel ? power : 0);
        }

        //----------telemetry----------
        telemetry.addData("Wheel", WHEEL_NAMES[wheel]);
        telemetry.addData("Tuning", PARAM_NAMES[param] + " = " + String.format("%.6f", gains[param][wheel]));
        telemetry.addData("Step", "%.6f", step);
        telemetry.addData("Goal RPM", goalRPM);
        telemetry.addData("RPM", "%.1f", rpm);
        telemetry.addData("Error", "%.1f", goalRPM - rpm);
        telemetry.addData("Power", "%.3f", power);
        telemetry.addData("Ticks per Sec", "%.0f", chassis.getWheelTicksPerSec(wheel));
        telemetry.addLine("--- ALL WHEELS (copy these when done) ---");
        for (int i = 0; i < 4; i++) {
            telemetry.addData(WHEEL_SHORT[i], "kS %.4f  kV %.6f  kP %.6f  kI %.6f  kD %.6f",
                    gains[KS][i], gains[KV][i], gains[KP][i], gains[KI][i], gains[KD][i]);
        }
        telemetry.addLine("Bumpers=wheel  Y=value  Dpad U/D=change  L/R=step  A/B/X=goal  LT=stop");
    }

    @Override
    public void stop() {
        chassis.stopAllMotors();
    }
}