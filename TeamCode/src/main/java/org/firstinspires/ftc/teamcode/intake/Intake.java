package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.IntakeMotors;

@TeleOp
@Disabled
public class Intake extends OpMode {
    IntakeMotors intakeMotors = new IntakeMotors();

    @Override
    public void init() {
        intakeMotors.init(hardwareMap);
    }

    @Override
    public void loop() {
        //Run intake
        if (gamepad2.left_bumper) {
            intakeMotors.intakeDirForward();
            intakeMotors.setIntakeMotorPower(0.6);
        }
        //Run intake in reverse
        if (gamepad2.right_bumper) {
            intakeMotors.intakeDirReverse();
            intakeMotors.setIntakeMotorPower(0.6);
        }
        //Stop intake
        if (gamepad2.left_trigger_pressed) {
            intakeMotors.stopIntakeMotor();
        }
        telemetry.addData("RPM:", intakeMotors.getIntakeRPM());
    }
}
