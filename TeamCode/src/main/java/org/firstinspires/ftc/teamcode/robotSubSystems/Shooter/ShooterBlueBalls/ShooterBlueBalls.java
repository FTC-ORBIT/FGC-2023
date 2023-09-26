package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterBlueBalls;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Sensors.OrbitColorSensor;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterState;

public class ShooterBlueBalls extends Shooter {
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
    private static ShooterState lastState = ShooterState.STOP;
    private static double startedShootingTime = 0;

    public void init(HardwareMap hardwareMap){
        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor2");
        shooterServo = hardwareMap.get(Servo.class, "shooterBlueBallsServo");
//        colorSensor = new OrbitColorSensor(hardwareMap, "blueBallsColorSensor");

        // reverse the correct motors/servo if needed
        shooterMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public void operate(ShooterState state, Gamepad gamepad){


        switch (state){
            case SHOOT:
                if (!state.equals(lastState)){
                    startedShootingTime = GlobalData.currentTime;
                }
                power = ShooterBlueBallsConstants.shooterPower * (12 / GlobalData.currentVoltage);
                if (GlobalData.currentTime - ShooterBlueBallsConstants.shooterDelaySec >= startedShootingTime) {
                    shooterServo.setPosition(ShooterBlueBallsConstants.openDoorPos);
                } else {
                    shooterServo.setPosition(ShooterBlueBallsConstants.closedDoorPos);
                }
                break;
            case STOP:
                power = 0;
                wantedServoPos = ShooterBlueBallsConstants.closedDoorPos;
                shooterServo.setPosition(ShooterBlueBallsConstants.closedDoorPos);
                break;
        }


        shooterMotor1.setPower(power);
        shooterMotor2.setPower(power);

        lastState = state;
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

    public void firstTime(Gamepad gamepad){ //only for the first time for the configuration
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
