package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterGreenBalls;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterState;

public class ShooterGreenBalls extends Shooter {

    private static DcMotor greenBallsMotor;
    private static Servo greenBallsServo;
    private static double wantedPower = 0;
    private static double wantedServoPos = 0;
    private static boolean lastRightBumper;
    private static boolean lastLeftBumper;

    @Override
    public void init(HardwareMap hardwareMap) {
        greenBallsMotor = hardwareMap.get(DcMotor.class, "greenBallsMotor");
        greenBallsServo = hardwareMap.get(Servo.class, "greenBallsServo");

        greenBallsMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //reverse the motor if needed
    }

    @Override
    public void operate(ShooterState state) {
        switch (state){
            case SHOOT:
                wantedPower = ShooterGreenBallsConstants.shooterPower;
                wantedServoPos = ShooterGreenBallsConstants.openServoPos;
                break;
            case STOP:
                wantedServoPos = ShooterGreenBallsConstants.closedServoPos;
                break;
        }

        greenBallsMotor.setPower(wantedPower);
        greenBallsServo.setPosition(wantedServoPos);

    }

    @Override
    public void update() {

    }

    @Override
    public void firstTime(Gamepad gamepad) {
        greenBallsMotor.setPower(gamepad.left_stick_y);

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
