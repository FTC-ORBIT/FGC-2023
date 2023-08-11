package org.firstinspires.ftc.teamcode.robotSubSystems.Shooting;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooting {
    private static DcMotor shootingMotor1;
    private static DcMotor shootingMotor2;

    public Shooting(HardwareMap hardwareMap){
        shootingMotor1 = hardwareMap.get(DcMotor.class, "shootingMotor1");
        shootingMotor2 = hardwareMap.get(DcMotor.class, "shootingMotor2");

        shootingMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        shootingMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shootingMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    public void operate(ShootingState state){
        switch (state){
            case SHOT:
                shootingMotor1.setPower(ShootingConstants.shootingPower);
                shootingMotor2.setPower(ShootingConstants.shootingPower);
                break;
            case STOP:
                shootingMotor1.setPower(0);
                shootingMotor2.setPower(0);
        }

    }

}
