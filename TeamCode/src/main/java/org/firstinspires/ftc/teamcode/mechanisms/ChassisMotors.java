package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class ChassisMotors {
    private DcMotorEx front_right_motor, front_left_motor, back_right_motor, back_left_motor; //Chassis motors
    double encoderCPM = 28;
    private double ticksPerRev = 537.7;

    private DcMotorEx[] wheels; // 0 = FL, 1 = FR, 2 = BL, 3 = BR
    IMU imu;

    public void init(HardwareMap hwMap) {
        //---------------CHASSIS---------------//
        front_right_motor = hwMap.get(DcMotorEx.class, "front_right_motor");
        front_left_motor = hwMap.get(DcMotorEx.class, "front_left_motor");
        back_right_motor = hwMap.get(DcMotorEx.class, "back_right_motor");
        back_left_motor = hwMap.get(DcMotorEx.class, "back_left_motor");

        front_right_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        front_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_motor.setDirection(DcMotorSimple.Direction.FORWARD);

        //------------for my tuning method----------//
        wheels = new DcMotorEx[]{front_left_motor, front_right_motor, back_left_motor, back_right_motor};
        for (DcMotorEx m : wheels) {
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        double reported = wheels[0].getMotorType().getTicksPerRev();
        if (reported > 0) {
            ticksPerRev = reported;
        }


        //--------------IMU-----------------//2
        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot orient = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
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


    public void turnLeft(double turnSpeed, double turnAngle) {
        stopAllMotors();
        imu.resetYaw();
        double currentDegrees = 0;
        while (currentDegrees >= -turnAngle) {
            currentDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            setBackLeftMotor(-turnSpeed);
            setFrontLeftMotor(-turnSpeed);

            setBackRightMotor(turnSpeed);
            setFrontRightMotor(turnSpeed);
        }

        stopAllMotors();
    }

    public void turnRight(double turnSpeed, double turnAngle) {
        stopAllMotors();
        imu.resetYaw();
        double currentDegrees = 0;
        while (currentDegrees <= turnAngle) {
            currentDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            setBackLeftMotor(turnSpeed);
            setFrontLeftMotor(turnSpeed);

            setBackRightMotor(-turnSpeed);
            setFrontRightMotor(-turnSpeed);
        }

        stopAllMotors();
    }

    public void moveForward(double speed) {
        stopAllMotors();
        setBackRightMotor(speed);
        setBackLeftMotor(speed);

        setFrontRightMotor(speed);
        setFrontLeftMotor(speed);
    }

    public void moveBackward(double speed) {
        stopAllMotors();
        setBackLeftMotor(-speed);
        setBackRightMotor(-speed);

        setFrontLeftMotor(-speed);
        setFrontRightMotor(-speed);
    }
    public double getFrontLeftTicksPerSec() {
        return front_left_motor.getVelocity();
    }
    public double getFrontRightTicksPerSec() {
        return front_right_motor.getVelocity();
    }
    public double getBackLeftTicksPerSec() {
        return back_left_motor.getVelocity();
    }
    public double getBackRightTicksPerSec() {
        return back_right_motor.getVelocity();
    }

    public double getFrontLeftRPM() {
        return ((getFrontLeftTicksPerSec()/encoderCPM) * 60);
    }
    public double getFrontRightRPM() {
        return ((getFrontRightTicksPerSec()/encoderCPM) * 60);
    }
    public double getBackLefRPM() {
        return ((getBackLeftTicksPerSec()/encoderCPM) * 60);
    }
    public double getBackRightRPM() {
        return ((getBackRightTicksPerSec()/encoderCPM) * 60);
    }
    public double getWheelRPM(int wheel) {
        return wheels[wheel].getVelocity() * 60.0 / ticksPerRev;
    }
    public double getWheelTicksPerSec(int wheel) {
        return wheels[wheel].getVelocity();
    }
    public void setWheelPower(int wheel, double power) {
        wheels[wheel].setPower(power);
    }
}
