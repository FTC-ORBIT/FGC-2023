package org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Conveyor {
    private static DcMotor conveyorMotor;
    private static float power = 0;
    public static void init(HardwareMap hardwareMap){
        conveyorMotor = hardwareMap.get(DcMotor.class, "conveyorMotor");
        conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void operate(ConveyorState state){
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

    public void override (Gamepad gamepad){
        conveyorMotor.setPower(gamepad.left_stick_y);
    }
}
