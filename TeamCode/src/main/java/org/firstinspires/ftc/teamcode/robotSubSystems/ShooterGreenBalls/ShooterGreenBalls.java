package org.firstinspires.ftc.teamcode.robotSubSystems.ShooterGreenBalls;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ShooterGreenBalls {

    private static DcMotor greenBallsMotor;
    private static double wantedPower = 0;

    public static void init (HardwareMap hardwareMap){
        greenBallsMotor = hardwareMap.get(DcMotor.class, "greenBallsMotor");

        greenBallsMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //reverse the motor if needed
        //add servo if needed
    }

    public static void operate (ShooterGreenBallsStates state){
        switch (state){
            case SHOOT:
                wantedPower = ShooterGreenBallsConstants.shooterPower;
                break;
            case STOP:
                wantedPower = 0;
                break;
        }

        greenBallsMotor.setPower(wantedPower);
    }
}
