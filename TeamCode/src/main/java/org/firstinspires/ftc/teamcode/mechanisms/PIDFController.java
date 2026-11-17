package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDFController {
    private double kP, kI, kD, kF;

    private double integralSum = 0;
    private double lastError = 0;
    private double error = 0;
    private boolean firstRun = true;

    private double outputMin = -1.0;
    private double outputMax = 1.0;
    private double integralLimit = 1.0; // max contribution of I term to output
    private double tolerance = 0;
    private boolean angleWrap = false;

    private final ElapsedTime timer = new ElapsedTime();

    public PIDFController(double kP, double kI, double kD, double kF) {
        setCoefficients(kP, kI, kD, kF);
    }

    // P only
    public PIDFController(double kP) {
        this(kP, 0, 0, 0);
    }

    public void setCoefficients(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public void setOutputLimits(double min, double max) {
        this.outputMin = min;
        this.outputMax = max;
    }

    public void setIntegralLimit(double limit) {
        this.integralLimit = Math.abs(limit);
    }

    public void setTolerance(double tolerance) {
        this.tolerance = Math.abs(tolerance);
    }

    public void setAngleWrap(boolean angleWrap) {
        this.angleWrap = angleWrap;
    }

    public double calculate(double target, double current) {
        error = target - current;
        if (angleWrap) {
            error = wrapDegrees(error);
        }

        double dt = timer.seconds();
        timer.reset();

        double derivative = 0;
        if (!firstRun && dt > 0) {
            integralSum += error * dt;
            derivative = (error - lastError) / dt;

            // anti-windup: keep kI * integralSum inside +/- integralLimit
            if (kI != 0) {
                double maxSum = integralLimit / Math.abs(kI);
                integralSum = clamp(integralSum, -maxSum, maxSum);
            }
        }

        lastError = error;
        firstRun = false;

        double output = kP * error + kI * integralSum + kD * derivative + kF * target;
        return clamp(output, outputMin, outputMax);
    }

    public boolean atSetpoint() {
        return Math.abs(error) <= tolerance;
    }

    public double getError() {
        return error;
    }

    public void reset() {
        integralSum = 0;
        lastError = 0;
        error = 0;
        firstRun = true;
        timer.reset();
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static double wrapDegrees(double degrees) {
        while (degrees > 180) degrees -= 360;
        while (degrees < -180) degrees += 360;
        return degrees;
    }
}