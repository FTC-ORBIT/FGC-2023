package org.firstinspires.ftc.teamcode.robotSubSystems.ShooterGreenBalls;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShooterGreenBalls {

    private static DcMotor greenBallsMotor;
    private static Servo greenBallsServo;
    private static double wantedPower = 0;
    private static double wantedServoPos = 0;
    private static boolean lastRightBumper;
    private static boolean lastLeftBumper;

    public static void init (HardwareMap hardwareMap){
        greenBallsMotor = hardwareMap.get(DcMotor.class, "greenBallsMotor");
        greenBallsServo = hardwareMap.get(Servo.class, "greenBallsServo");

        greenBallsMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //reverse the motor if needed
    }

    public static void operate (ShooterGreenBallsStates state){
        switch (state){
            case SHOOT:
                wantedPower = ShooterGreenBallsConstants.shooterPower;
                wantedServoPos = ShooterGreenBallsConstants.openServoPos;
                break;
            case STOP:
                wantedPower = 0;
                wantedServoPos = ShooterGreenBallsConstants.closedServoPos;
                break;
        }

        greenBallsMotor.setPower(wantedPower);
        greenBallsServo.setPosition(wantedServoPos);
    }

    public static void firstTime(Gamepad gamepad){
        greenBallsMotor.setPower(gamepad.left_stick_y);

        double servoPos = 0;

        if (gamepad.right_bumper && !lastRightBumper) {
            servoPos += 0.05;
        } else if (gamepad.left_bumper && !lastLeftBumper) {
            servoPos -= 0.05;
        }

        lastRightBumper = gamepad.right_bumper;
        lastLeftBumper = gamepad.left_bumper;}
}
