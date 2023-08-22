package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {
    private static DcMotor shooterMotor1;
    private static DcMotor shooterMotor2;
    private static VoltageSensor vs;
    private boolean fault = false;
    private ElapsedTime lastBallTime = new ElapsedTime();
    private boolean readyToShoot = false;

    public Shooter(HardwareMap hardwareMap){
        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooterMotor1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterMotor2");

        // reverse the correct motors if needed
        shooterMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        vs = hardwareMap.voltageSensor.get("Motor Controller 1");
    }

    public void operate(ShooterState state){
        double power = 0;
        switch (state){
            case SHOOT:
                power = ShooterConstants.shooterPower * (12 / vs.getVoltage());
                break;
            case STOP:
                power = 0;
                break;
        }
        shooterMotor1.setPower(power);
        shooterMotor2.setPower(power);

        update();
        readyToShoot();
    }

    private void update(){
        if(vs.getVoltage() <= ShooterConstants.faultLimit){
            fault = true;
            lastBallTime.reset();
        } else {
            fault = false;
        }
    }

    private void readyToShoot (){
        readyToShoot = !getFault() && (float) lastBallTime.milliseconds() >= ShooterConstants.faultMinTime && AprilTags.inplace || driverButtonPressed;
    }

    public boolean getFault() {
        return fault;
    }

    public boolean isReadyToShoot(){
        return readyToShoot;
    }

}
