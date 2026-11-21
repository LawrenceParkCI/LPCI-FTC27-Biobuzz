package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Servos;

@TeleOp
public class ServoGate extends OpMode {
    Servos servos = new Servos();
    double startPos;
    @Override
    public void init() {
        servos.init(hardwareMap);

        startPos = servos.getServoPos();
    }
    @Override
    public void loop() {
        if (gamepad2.dpad_up) {
            servos.openGate();
        } else if (gamepad2.dpad_down) {
            servos.closeGate();
        }

        telemetry.addData("Servo Pos", servos.getServoPos());
    }
}
