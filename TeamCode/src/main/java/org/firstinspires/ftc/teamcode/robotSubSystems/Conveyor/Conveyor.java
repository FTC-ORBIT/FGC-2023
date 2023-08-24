package org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Conveyor {
    private static DcMotor conveyorMotor;
    private static double power = 0;
    public static void init(HardwareMap hardwareMap){
        conveyorMotor = hardwareMap.get(DcMotor.class, "conveyorMotor");
        conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public static void operate(ConveyorState state){
        switch (state){
            case TRANSPORT:
                power = ConveyorConstants.conveyorPower;
                break;
            case STOP:
                power = 0;
                break;
        }
        conveyorMotor.setPower(power);
    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        conveyorMotor.setPower(gamepad.left_stick_y);
    }
}
