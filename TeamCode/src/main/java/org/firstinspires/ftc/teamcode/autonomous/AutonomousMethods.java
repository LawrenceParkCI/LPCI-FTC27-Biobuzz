package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ChassisMotors;

public class AutonomousMethods {
    ChassisMotors motors = new ChassisMotors();
    IMU imu;

    public void init(HardwareMap hwMap) {

        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot orient = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        );

        imu.initialize(new IMU.Parameters(orient));
    }

    public void turnLeft(double turnSpeed, double turnAngle) {
        imu.resetYaw();
        double currentDegrees = 0;
        while (currentDegrees < -turnAngle) {
            currentDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            motors.setBackLeftMotor(-turnSpeed);
            motors.setFrontLeftMotor(-turnSpeed);

            motors.setBackLeftMotor(turnSpeed);
            motors.setFrontLeftMotor(turnSpeed);
        }

        motors.stopAllMotors();
    }

    public void turnRight(double turnSpeed, double turnAngle) {
        imu.resetYaw();
        double currentDegrees = 0;
        while (currentDegrees < turnAngle) {
            currentDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            motors.setBackLeftMotor(turnSpeed);
            motors.setFrontLeftMotor(turnSpeed);

            motors.setBackLeftMotor(-turnSpeed);
            motors.setFrontLeftMotor(-turnSpeed);
        }

        motors.stopAllMotors();
    }
}
