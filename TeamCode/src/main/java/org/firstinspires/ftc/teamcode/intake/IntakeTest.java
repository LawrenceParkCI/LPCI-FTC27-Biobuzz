package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.IntakeMotors;

@TeleOp
public class IntakeTest extends OpMode {
    IntakeMotors intake = new IntakeMotors();

    @Override
    public void init() {
        intake.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            intake.intakeDirForward();
            intake.setIntakeMotorPower(0.6);
        }
        if (gamepad1.b) {
            intake.intakeDirReverse();
            intake.setIntakeMotorPower(0.6);
        }
        if (gamepad1.x) {
            intake.stopIntakeMotor();
        }

        telemetry.addData("RPM:", intake.getIntakeRPM());
    }
}
