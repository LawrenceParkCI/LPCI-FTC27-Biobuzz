package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Disabled

public class Camera {
    private Camera camera;
    public void init(HardwareMap hwMap) {
        camera = hwMap.get(Camera.class, "camera");
    }
}
