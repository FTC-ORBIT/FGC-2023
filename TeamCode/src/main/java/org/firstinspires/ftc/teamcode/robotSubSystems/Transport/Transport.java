package org.firstinspires.ftc.teamcode.robotSubSystems.Transport;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transport {
    private static DcMotor transportMotor;
    public Transport(HardwareMap hardwareMap){
        transportMotor = hardwareMap.get(DcMotor.class, "transportMotor");
        transportMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void operate(TransportState state){
        switch (state){
            case TRANSPORT:
                transportMotor.setPower(TransportConstants.transportPower);
            case STOP:
                transportMotor.setPower(0);
        }
    }
}
