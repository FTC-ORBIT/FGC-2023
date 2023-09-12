package org.firstinspires.ftc.teamcode.robotData;

import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.robotSubSystems.RobotState;

public class GlobalData {
    public static final boolean inComp = false;

    public static VoltageSensor voltageSensor;

    public static double currentVoltage = 0;

    public static RobotState robotState = RobotState.TRAVEL;

    public static float currentTime = 0;


}