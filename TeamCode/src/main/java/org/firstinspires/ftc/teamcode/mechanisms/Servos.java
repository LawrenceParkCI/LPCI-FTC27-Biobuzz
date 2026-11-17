package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    private Servo gate_servo;

    private static final double SERVO_RANGE_DEGREES = 300.0;
    private static final double GATE_TRAVEL_DEGREES = 90.0;
    public static final double GATE_OPEN_POS = 0.0;
    public static final double GATE_CLOSED_POS = GATE_OPEN_POS + (GATE_TRAVEL_DEGREES / SERVO_RANGE_DEGREES);

    public void init(HardwareMap hwMap) {
        gate_servo = hwMap.get(Servo.class, "gate_servo");
        closeGate();
    }

    public void openGate() {
        gate_servo.setPosition(GATE_OPEN_POS);
    }

    public void closeGate() {
        gate_servo.setPosition(GATE_CLOSED_POS);
    }

    public double getServoPos() {
        return gate_servo.getPosition();
    }

    public void setServoPos(double position) {
        gate_servo.setPosition(position);
    }
}