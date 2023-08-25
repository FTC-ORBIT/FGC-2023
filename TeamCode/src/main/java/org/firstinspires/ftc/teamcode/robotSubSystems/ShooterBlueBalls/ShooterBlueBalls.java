package org.firstinspires.ftc.teamcode.robotSubSystems.ShooterBlueBalls;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;

public class ShooterBlueBalls {
    private static DcMotor shooterMotor1;
    private static DcMotor shooterMotor2;
    private static Servo shooterServo;
    private static double wantedServoPos = ShooterBlueBallsConstants.closedDoorPos;
    private static boolean fault = false;
    private static final ElapsedTime lastBallTime = new ElapsedTime();

    private static final double[] last11VoltageSensor = new double[11];
    private static double sumOfTheLast10Voltages = 0;

    private static int i = 0;

    private static boolean readyToShoot = false;

    private static boolean isShooting = false;

    private static double power = 0;

    public static void init(HardwareMap hardwareMap){
        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor2");
        shooterServo = hardwareMap.get(Servo.class, "shooterBlueBallsServo");

        // reverse the correct motors/servo if needed
        shooterMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public void operate(ShooterBlueBallsState state){


        switch (state){
            case SHOOT:
                power = ShooterBlueBallsConstants.shooterPower * (12 / GlobalData.voltageSensor.getVoltage());
                break;
            case STOP:
                power = 0;
                wantedServoPos = ShooterBlueBallsConstants.closedDoorPos;
                break;
        }

        if (isReadyToShoot()){
            shooterMotor1.setPower(power);
            shooterMotor2.setPower(power);
        }

        if (isShooting()){
            lastBallTime.reset();
        }

        last11VoltageSensor[i] = GlobalData.currentVoltage;

        for (int i = 1; i < 11; i++){
            sumOfTheLast10Voltages += last11VoltageSensor[i];
        }

        double averageLast10Voltages = sumOfTheLast10Voltages/10;

        isShooting = last11VoltageSensor[0] - ShooterBlueBallsConstants.voltageDownWhenShooting >= averageLast10Voltages;

        if (isShooting()) {
            lastBallTime.reset();
        }

        if (isReadyToShoot()){
            wantedServoPos = ShooterBlueBallsConstants.openDoorPos;
        }

        shooterServo.setPosition(wantedServoPos);

        update();
        i++;
    }

    private void update(){
        if(GlobalData.voltageSensor.getVoltage() <= ShooterBlueBallsConstants.faultLimit){
            fault = true;
        } else {
            fault = false;
        }

        readyToShoot = !getFault() && (float) lastBallTime.milliseconds() >= ShooterBlueBallsConstants.faultMinTime && AprilTags.inplace || driverButtonPressed;
    }

    private boolean isReadyToShoot(){
        return readyToShoot;
    }

    public boolean getFault() {
        return fault;
    }

    public boolean isShooting(){
        return isShooting;
    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        shooterMotor1.setPower(gamepad.left_stick_y);
        shooterMotor2.setPower(gamepad.right_stick_y);
    }


}