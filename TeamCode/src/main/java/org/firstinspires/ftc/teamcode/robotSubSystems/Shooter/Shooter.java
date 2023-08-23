package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;

public class Shooter {
    private static DcMotor shooterMotor1;
    private static DcMotor shooterMotor2;
    private static boolean fault = false;
    private static ElapsedTime lastBallTime = new ElapsedTime();

    private static double power = 0;
    private static boolean readyToShoot = false;

    public Shooter(HardwareMap hardwareMap){
        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooterMotor1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterMotor2");

        // reverse the correct motors if needed
        shooterMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public void operate(ShooterState state){


        switch (state){
            case SHOOT:
                power = ShooterConstants.shooterPower * (12 / GlobalData.voltageSensor.getVoltage());
                break;
            case STOP:
                power = 0;
                break;
        }

        if (isReadyToShoot()){
            shooterMotor1.setPower(power);
            shooterMotor2.setPower(power);
        }

        if (isShooting()){
            lastBallTime.reset();
        }
        update();
    }

    private void update(){
        if(GlobalData.voltageSensor.getVoltage() <= ShooterConstants.faultLimit){
            fault = true;
        } else {
            fault = false;
        }
    }

    private boolean isReadyToShoot(){
        return readyToShoot = !getFault() && (float) lastBallTime.milliseconds() >= ShooterConstants.faultMinTime && AprilTags.inplace || driverButtonPressed;
    }

    public boolean getFault() {
        return fault;
    }

    public boolean isShooting(){
        return GlobalData.voltageSensor.getVoltage() > ShooterConstants.minVoltageRangeWhenShooting && GlobalData.voltageSensor.getVoltage() < ShooterConstants.maxVoltageRangeWhenShooting;
    }


}
