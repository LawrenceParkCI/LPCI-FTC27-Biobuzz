package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;

@TeleOp(name = "WheelTester", group = "Test")
public class WheelTester extends OpMode {
    ChassisMotors chassisMotors = new ChassisMotors();

    //button indexes for edge detection
    private static final int UP = 0, RIGHT = 1, LEFT = 2, DOWN = 3, X = 4, Y = 5, A = 6, LB = 7, RB = 8;
    private final boolean[] now = new boolean[9];
    private final boolean[] last = new boolean[9];

    //wheel flips (reversing the motor)
    private boolean flipFL, flipFR, flipBL, flipBR;

    //sign of each stick input
    private boolean negAxial, negLateral, negYaw;

    //1 = BL lateral, 2 = BL yaw, 4 = BR lateral, 8 = BR yaw. 0 = standard.
    private int backCombo = 0;

    @Override
    public void init() {
        chassisMotors.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        //buttons
        now[UP] = gamepad1.dpad_up;
        now[RIGHT] = gamepad1.dpad_right;
        now[LEFT] = gamepad1.dpad_left;
        now[DOWN] = gamepad1.dpad_down;
        now[X] = gamepad1.x;
        now[Y] = gamepad1.y;
        now[A] = gamepad1.a && !gamepad1.start;
        now[LB] = gamepad1.left_bumper;
        now[RB] = gamepad1.right_bumper;

        if (rising(UP)) flipFL = !flipFL;
        if (rising(RIGHT)) flipFR = !flipFR;
        if (rising(LEFT)) flipBL = !flipBL;
        if (rising(DOWN)) flipBR = !flipBR;
        if (rising(X)) negAxial = !negAxial;
        if (rising(Y)) negLateral = !negLateral;
        if (rising(A)) negYaw = !negYaw;
        if (rising(LB)) backCombo = (backCombo + 1) % 16;
        if (rising(RB)) {
            flipFL = flipFR = flipBL = flipBR = false;
            negAxial = negLateral = negYaw = false;
            backCombo = 0;
        }

        System.arraycopy(now, 0, last, 0, now.length);

        //stick directions
        double axial = (negAxial ? -1 : 1) * -gamepad1.left_stick_y;
        double lateral = (negLateral ? -1 : 1) * gamepad1.left_stick_x;
        double yaw = (negYaw ? -1 : 1) * gamepad1.right_stick_x;

        //wheel formulas
        double flLat = 1, flYaw = 1;
        double frLat = -1, frYaw = -1;
        double blLat = -1, blYaw = 1;
        double brLat = 1, brYaw = -1;

        if ((backCombo & 1) != 0) blLat = -blLat;
        if ((backCombo & 2) != 0) blYaw = -blYaw;
        if ((backCombo & 4) != 0) brLat = -brLat;
        if ((backCombo & 8) != 0) brYaw = -brYaw;

        double frontLeftPower = axial + flLat * lateral + flYaw * yaw;
        double frontRightPower = axial + frLat * lateral + frYaw * yaw;
        double backLeftPower = axial + blLat * lateral + blYaw * yaw;
        double backRightPower = axial + brLat * lateral + brYaw * yaw;

        if (flipFL) frontLeftPower = -frontLeftPower;
        if (flipFR) frontRightPower = -frontRightPower;
        if (flipBL) backLeftPower = -backLeftPower;
        if (flipBR) backRightPower = -backRightPower;

        //normalizing values
        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        chassisMotors.setFrontLeftMotor(frontLeftPower);
        chassisMotors.setFrontRightMotor(frontRightPower);
        chassisMotors.setBackLeftMotor(backLeftPower);
        chassisMotors.setBackRightMotor(backRightPower);

        telemetry.addLine("---INPUT SIGNS---");
        telemetry.addData("axial (X)", negAxial ? "gamepad1.left_stick_y" : "-gamepad1.left_stick_y");
        telemetry.addData("lateral (Y)", negLateral ? "-gamepad1.left_stick_x" : "gamepad1.left_stick_x");
        telemetry.addData("yaw (A)", negYaw ? "-gamepad1.right_stick_x" : "gamepad1.right_stick_x");
        telemetry.addLine("---FORMULAS---");
        telemetry.addData("FL", "axial" + term(flLat, "lateral") + term(flYaw, "yaw") + (flipFL ? "   (then negated)" : ""));
        telemetry.addData("FR", "axial" + term(frLat, "lateral") + term(frYaw, "yaw") + (flipFR ? "   (then negated)" : ""));
        telemetry.addData("BL", "axial" + term(blLat, "lateral") + term(blYaw, "yaw") + (flipBL ? "   (then negated)" : ""));
        telemetry.addData("BR", "axial" + term(brLat, "lateral") + term(brYaw, "yaw") + (flipBR ? "   (then negated)" : ""));
        telemetry.addData("Back combo", backCombo + " of 15 (0 = standard)");
        telemetry.addLine("---POWERS---");
        telemetry.addData("FL / FR", "%.2f / %.2f", frontLeftPower, frontRightPower);
        telemetry.addData("BL / BR", "%.2f / %.2f", backLeftPower, backRightPower);
        telemetry.addLine("Dpad: up=FL right=FR left=BL down=BR | X/Y/A = axial/lateral/yaw | LB cycle | RB reset");
    }

    private boolean rising(int i) {
        return now[i] && !last[i];
    }

    private String term(double sign, String name) {
        return (sign < 0 ? " - " : " + ") + name;
    }
}