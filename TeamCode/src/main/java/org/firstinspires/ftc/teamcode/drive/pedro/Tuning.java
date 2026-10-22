package org.firstinspires.ftc.teamcode.drive.pedro;

import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.drive.pedro.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.drive.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.drive.pedro.procedures.Tests;
import org.firstinspires.ftc.teamcode.drive.pedro.procedures.TwoWheelTuner;

public class Tuning {
    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }
    @Tuner
    public static Procedure twoWheelTuner() {
        return new TwoWheelTuner();
    }
}
