package org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Conveyor {
    private static DcMotor conveyorMotor;
    private static double power = 0;
    public static void init(HardwareMap hardwareMap){
        conveyorMotor = hardwareMap.get(DcMotor.class, "conveyorMotor");
        conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        conveyorMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public static void operate(ConveyorState state, Gamepad gamepad){
        switch (state){
            case TRANSPORT:
                power = ConveyorConstants.conveyorTransportPower;
                break;
            case BACKWARDS:
                power = ConveyorConstants.conveyorBackwardsPower;
                break;
            case STOP:
                power = 0;
                break;
            case OVERRIDE:
                power = -gamepad.right_stick_x;
                break;
        }
        conveyorMotor.setPower(power);
    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        conveyorMotor.setPower(gamepad.left_stick_y);
    }
}
