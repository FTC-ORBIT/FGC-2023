package org.firstinspires.ftc.teamcode.robotSubSystems.TankGrabber;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TankGrabber {

    private static Servo tankGrabber;

    private static double wantedPos = TankGrabberConstants.closedPos;

    public static void init (HardwareMap hardwareMap){
        tankGrabber = hardwareMap.get(Servo.class, "tankGrabber");
    }

    public static void operate (TankGrabberStates state){
        switch (state){
            case OPEN:
                wantedPos = TankGrabberConstants.openPos;
                break;
            case CLOSED:
                wantedPos = TankGrabberConstants.closedPos;
                break;
        }
        tankGrabber.setPosition(wantedPos);
    }
}
