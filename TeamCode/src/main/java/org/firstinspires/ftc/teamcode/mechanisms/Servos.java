package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    private Servo gate_servo;
    public static final double GATE_CLOSED_POS = 0.0;
    public static final double GATE_OPEN_POS = 0.5;

    public void init(HardwareMap hwMap) {
        gate_servo = hwMap.get(Servo.class, "intake_motor");
    }
    public void getServoPos() {
        gate_servo.getPosition();
    }
    public void setServoPos(double position) {
        gate_servo.setPosition(position);

    }
    public void openGate() {
        gate_servo.setPosition(GATE_OPEN_POS);
    }

    public void closeGate() {
        gate_servo.setPosition(GATE_CLOSED_POS);
    }
}
