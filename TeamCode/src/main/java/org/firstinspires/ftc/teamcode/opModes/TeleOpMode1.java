package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;
import org.firstinspires.ftc.teamcode.mechanisms.FlywheelMotors;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeMotors;
import org.firstinspires.ftc.teamcode.mechanisms.Servos;

@TeleOp(name = "TeleOpMode1", group = "TeleOp")
public class TeleOpMode1 extends OpMode {

    // Mechanisms
    ChassisMotors chassisMotors = new ChassisMotors();
    IntakeMotors intakeMotors = new IntakeMotors();
    FlywheelMotors flywheelMotors = new FlywheelMotors();
    Servos servos = new Servos();

    // Drive mode: false = normal front, true = reversed front
    private boolean reversedDrive = false;
    private boolean lastToggleButton = false;

    @Override
    public void init() {
        chassisMotors.init(hardwareMap);
        intakeMotors.init(hardwareMap);
        flywheelMotors.init(hardwareMap);
        servos.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // -------------- INTAKE --------------- //
        //intake balls
        if (gamepad2.left_bumper) {
            intakeMotors.intakeDirForward();
            intakeMotors.setIntakeMotorPower(0.6);
        //reverse intake
        } else if (gamepad2.right_bumper) {
            intakeMotors.intakeDirReverse();
            intakeMotors.setIntakeMotorPower(0.6);
        //stop intake
        } else if (gamepad2.left_trigger > 0.1) {
            intakeMotors.stopIntakeMotor();
        }



        // ------------- SHOOTERS ----------- //
        double nectarGoalRPM = 1500;
        double pollenGoalRPM = 2850;
        double idleGoal = 800;

        //pollen motor
        if (gamepad2.a && !gamepad2.start) {
            flywheelMotors.setBotMotorRPM(pollenGoalRPM);
        } else if (gamepad2.x) {
            flywheelMotors.stopBotMotor();
        }
        //nectar motor
        if (gamepad2.b && !gamepad2.start) {
            flywheelMotors.setTopMotorRPM(nectarGoalRPM);
        } else if (gamepad2.y) {
            flywheelMotors.stopTopMotor();
        }
        //idling
        if (gamepad2.dpad_left) {
            flywheelMotors.setBothMotorsRPM(idleGoal);
        } else if (gamepad2.dpad_right) {
            flywheelMotors.stopMotors();
        }



        // -------------- GATE --------------- //
        if (gamepad2.dpad_up) {
            servos.openGate();
        } else if (gamepad2.dpad_down) {
            servos.closeGate();
        }


        
        // ------------- DRIVE ----------- //
        //toggle drive mode on each press of left bumper (flips once per press)
        boolean toggleButton = gamepad1.left_bumper;
        if (toggleButton && !lastToggleButton) {
            reversedDrive = !reversedDrive;
        }
        lastToggleButton = toggleButton;

        double axial = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = -gamepad1.right_stick_x;

        //swap the front (forward/back and strafe flip, turning stays the same)
        if (reversedDrive) {
            axial = -axial;
            lateral = -lateral;
        }

        double frontLeftPower  = axial + lateral - yaw;
        double frontRightPower = axial - lateral + yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral - yaw;

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
        //setting speeds
        chassisMotors.setFrontLeftMotor(frontLeftPower);
        chassisMotors.setFrontRightMotor(frontRightPower);
        chassisMotors.setBackRightMotor(backRightPower);
        chassisMotors.setBackLeftMotor(backLeftPower);

        telemetry.addData("Status", "Running");
        telemetry.addData("Drive Mode", reversedDrive ? "REVERSED" : "NORMAL");
    }
}
