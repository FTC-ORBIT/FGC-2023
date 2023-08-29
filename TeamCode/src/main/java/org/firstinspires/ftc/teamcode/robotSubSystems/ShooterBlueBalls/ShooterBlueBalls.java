package org.firstinspires.ftc.teamcode.robotSubSystems.ShooterBlueBalls;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Sensors.OrbitColorSensor;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;

public class ShooterBlueBalls {
    private static DcMotor shooterMotor1;
    private static DcMotor shooterMotor2;
    private static Servo shooterServo;
    private static double wantedServoPos = ShooterBlueBallsConstants.closedDoorPos;
    private static boolean fault = false;
    private static final double[] last11VoltageSensor = new double[11];
    private static double sumOfTheLast10Voltages = 0;

    private static int i = 0;

    private static boolean readyToShoot = false;

    private static boolean isShooting = false;
    private static double power = 0;
    private static boolean lastRightBumper = false;
    private static boolean lastLeftBumper = false;
    private static OrbitColorSensor colorSensor;
    private static boolean lastColorSensorState = false;
    private static float firstBlueBallTime = 0; //we don't want to shoot more than 3 balls one after another, this variable represents the time the first blue ball (from the 3) was shot.
    private static int blueBallsCounter = 0;

    public static void init(HardwareMap hardwareMap){
        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor2");
        shooterServo = hardwareMap.get(Servo.class, "shooterBlueBallsServo");
        colorSensor = new OrbitColorSensor(hardwareMap, "blueBallsColorSensor");

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


        shooterMotor1.setPower(power);
        shooterMotor2.setPower(power);

        if (isReadyToShoot()){
            shooterServo.setPosition(ShooterBlueBallsConstants.openDoorPos);
        } else {
            shooterServo.setPosition(ShooterBlueBallsConstants.closedDoorPos);
        }

        //I know most of the functions are now useless now. yet, I chose to leave them alone for now due to the frequent concept change

//        if (isShooting()){
//            lastBallTime.reset();
//        }
//
//        last11VoltageSensor[i] = GlobalData.currentVoltage;
//
//        for (int i = 1; i < 11; i++){
//            sumOfTheLast10Voltages += last11VoltageSensor[i];
//        }
//
//        double averageLast10Voltages = sumOfTheLast10Voltages/10;
//
//        isShooting = last11VoltageSensor[0] - ShooterBlueBallsConstants.voltageDownWhenShooting >= averageLast10Voltages;
//
//        if (isShooting()) {
//            lastBallTime.reset();
//        }
//
//        if (isReadyToShoot()){
//            wantedServoPos = ShooterBlueBallsConstants.openDoorPos;
//        }
//
//        shooterServo.setPosition(wantedServoPos);
//
        update();
//  \7
//  i++;
    }

    private void update(){
        if (colorSensor.isShootingBlueBall() && !lastColorSensorState){
            blueBallsCounter++;
            if (blueBallsCounter == 1){
                firstBlueBallTime = GlobalData.currentTime;
            }
        }

        if (firstBlueBallTime + ShooterBlueBallsConstants.minTimeSec <= GlobalData.currentTime){
            readyToShoot = true;
        } else {
            readyToShoot = false;
        }



        lastColorSensorState = colorSensor.isShootingBlueBall();
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

        double servoPos = 0;

        if (gamepad.right_bumper && !lastRightBumper) {
            servoPos += 0.05;
        } else if (gamepad.left_bumper && !lastLeftBumper) {
            servoPos -= 0.05;
        }

        lastRightBumper = gamepad.right_bumper;
        lastLeftBumper = gamepad.left_bumper;

    }


}
