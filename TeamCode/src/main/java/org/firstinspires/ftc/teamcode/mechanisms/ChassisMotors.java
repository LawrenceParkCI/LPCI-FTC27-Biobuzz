package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Disabled

public class ChassisMotors {
    private DcMotor front_right_motor, front_left_motor, back_right_motor, back_left_motor; //Chassis motors

    public void init(HardwareMap hwMap) {
        //---------------CHASSIS---------------//
        front_right_motor = hwMap.get(DcMotor.class, "front_right_motor");
        front_left_motor = hwMap.get(DcMotor.class, "front_left_motor");
        back_right_motor = hwMap.get(DcMotor.class, "back_right_motor");
        back_left_motor = hwMap.get(DcMotor.class, "back_left_motor");

        front_right_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        front_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        //--------------IMU-----------------//2
        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot orient = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        );
        imu.initialize(new IMU.Parameters(orient));
    }

    //----------SET CHASSIS POWER------------//
    public void setFrontRightMotor (double power) {
        front_right_motor.setPower(power);
    }
    public void setFrontLeftMotor (double power) {
        front_left_motor.setPower(power);
    }
    public void setBackRightMotor (double power) {
        back_right_motor.setPower(power);
    }
    public void setBackLeftMotor (double power) {
        back_left_motor.setPower(power);
    }
    public void stopAllMotors() {
        setBackLeftMotor(0);
        setFrontLeftMotor(0);
        setFrontRightMotor(0);
        setBackRightMotor(0);
    }

    //------- AUTONOMOUS METHODS -------
    IMU imu;

    public void turnLeft(double turnSpeed, double turnAngle) {
        stopAllMotors();
        imu.resetYaw();
        double currentDegrees = 0;
        while (currentDegrees < -turnAngle) {
            currentDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            setBackLeftMotor(-turnSpeed);
            setFrontLeftMotor(-turnSpeed);

            setBackLeftMotor(turnSpeed);
            setFrontLeftMotor(turnSpeed);
        }

        stopAllMotors();
    }

    public void turnRight(double turnSpeed, double turnAngle) {
        stopAllMotors();
        imu.resetYaw();
        double currentDegrees = 0;
        while (currentDegrees < turnAngle) {
            currentDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            setBackLeftMotor(turnSpeed);
            setFrontLeftMotor(turnSpeed);

            setBackLeftMotor(-turnSpeed);
            setFrontLeftMotor(-turnSpeed);
        }

        stopAllMotors();
    }

    public void moveForward(double speed) {
        stopAllMotors();
        setBackRightMotor(speed);
        setBackLeftMotor(speed);

        setFrontRightMotor(speed);
        setBackLeftMotor(speed);
    }

    public void moveBackward(double speed) {
        stopAllMotors();
        setBackLeftMotor(-speed);
        setBackRightMotor(-speed);

        setFrontLeftMotor(-speed);
        setFrontRightMotor(-speed);
    }
}
