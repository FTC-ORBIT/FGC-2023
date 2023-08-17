package org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Conveyor {
    private static DcMotor conveyorMotor;
    public Conveyor(HardwareMap hardwareMap){
        conveyorMotor = hardwareMap.get(DcMotor.class, "conveyorMotor");
        conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void operate(ConveyorState state){
        double power = 0;
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
}
