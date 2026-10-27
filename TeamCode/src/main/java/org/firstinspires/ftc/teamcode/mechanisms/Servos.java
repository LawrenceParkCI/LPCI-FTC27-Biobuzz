package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    private Servo gate_servo;

    public void init(HardwareMap hwMap) {
        gate_servo = hwMap.get(Servo.class, "intake_motor");
    }
    public void getServoPos() {
        gate_servo.getPosition();
    }
    public void setServoPos(double position) {
        gate_servo.setPosition(position);

    }
}
