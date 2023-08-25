package org.firstinspires.ftc.teamcode.robotSubSystems.ShooterBlueBalls;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ShooterBlueBallsConstants {
    public static double shooterPower = 0.7f;
    public static double faultLimit = 7f;  //this is the min voltage for shooting
    public static double voltageDownWhenShooting = 0.2; //this is how much the voltage goes down when there is a ball
    public static double faultMinTime = 500f; //this is the minimum time in milliseconds it takes to shot one ball after another
    public static double openDoorPos = 1;
    public static double closedDoorPos = 0;

}