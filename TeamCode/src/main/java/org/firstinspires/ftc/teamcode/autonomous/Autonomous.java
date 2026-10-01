package org.firstinspires.ftc.teamcode.autonomous;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

//pedro imports
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.follower.Follower;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;

//ivy imports
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;




@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends OpMode {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    //starting position
    private final Pose startPos = p.of(0, 0, 0);

    //parking position
    private final Pose park = p.of(0, 0, 0);

    //scoring position
    private final Pose score = p.of(0,0,0);
    private Path park() {
        return line(startPos, park).linear(startPos, park);
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPos);
    }

    @Override
    public void start() {
        schedule(follow(follower, park()));
    }
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
