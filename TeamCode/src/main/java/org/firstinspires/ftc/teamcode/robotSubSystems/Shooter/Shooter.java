package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private static DcMotor shooterMotor1;
    private static DcMotor shooterMotor2;

    public Shooter(HardwareMap hardwareMap){
        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooterMotor1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterMotor2");

        shooterMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


    }

    public void operate(ShooterState state){
        double power = 0;
        switch (state){
            case SHOT:
                power = 1;
                break;
            case STOP:
                power = 0;
        }
        shooterMotor1.setPower(power);
        shooterMotor2.setPower(power);

    }

}
