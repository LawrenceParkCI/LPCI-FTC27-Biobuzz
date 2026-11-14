package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.IntakeMotors;
import org.firstinspires.ftc.teamcode.mechanisms.Servos;

public class ServoGate extends OpMode {
    Servos servos = new Servos();

    @Override
    public void init() {
        servos.init(hardwareMap);
    }
    @Override
    public void loop() {
        if (gamepad2.dpad_up) {
            servos.openGate();
        } else if (gamepad2.dpad_down) {
            servos.closeGate();
        }
    }
}
