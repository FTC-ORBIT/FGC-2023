package org.firstinspires.ftc.teamcode.robotSubSystems.Convertor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Convertor {
    private static DcMotor convertorMotor;
    public Convertor(HardwareMap hardwareMap){
        convertorMotor = hardwareMap.get(DcMotor.class, "transportMotor");
        convertorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void operate(ConvertorState state){
        double power = 0;
        switch (state){
            case TRANSPORT:
                power = 1;
                break;
            case STOP:
                power = 0;
                break;
        }
        convertorMotor.setPower(power);
    }
}
