package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ShooterConstants {
    public static float shooterPower = 0.7f;
    public static float faultLimit = 7f;  //this is the min voltage for shooting

    public static float minVoltageRangeWhenShooting = 5f; //this is the min Voltage the motor gets to when there is a ball


    public static float maxVoltageRangeWhenShooting = 8f; //this is the min Voltage the motor gets to when there is a ball
    public static float faultMinTime = 500f; //this is the minimum time in milliseconds it takes to shot one ball after another

}
