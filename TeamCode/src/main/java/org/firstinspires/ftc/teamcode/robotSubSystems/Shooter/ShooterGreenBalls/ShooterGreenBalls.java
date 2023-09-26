package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterGreenBalls;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.RobotState;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterBlueBalls.ShooterBlueBallsConstants;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterState;

public class ShooterGreenBalls extends Shooter {

    private static DcMotor greenBallsMotor;
    public static Servo greenBallsServo;
    private static double wantedPower = 0;
    public static double wantedServoPos = 0;
    private static boolean lastRightBumper;
    private static boolean lastLeftBumper;
    private static ShooterState lastState = ShooterState.STOP;
    private static double startedShootingTime = 0;

    @Override
    public void init(HardwareMap hardwareMap) {
        greenBallsMotor = hardwareMap.get(DcMotor.class, "greenBallsMotor");
        greenBallsServo = hardwareMap.get(Servo.class, "greenBallsServo");

        greenBallsMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //reverse the motor if needed
    }

    @Override
    public void operate(ShooterState state, Gamepad gamepad) {
        if (GlobalData.currentRobotState.equals(RobotState.SHOOT_BLUE)){
            wantedServoPos = ShooterGreenBallsConstants.closedServoPos;
        }
        switch (state){
            case SHOOT:
                if (!state.equals(lastState)){
                    startedShootingTime = GlobalData.currentTime;
                }
                wantedPower = ShooterGreenBallsConstants.shooterPower * (12 / GlobalData.currentVoltage);
                if (GlobalData.currentTime - ShooterBlueBallsConstants.shooterDelaySec >= startedShootingTime) {
                    wantedServoPos = ShooterGreenBallsConstants.openServoPos;
                    GlobalData.isReadyToShoot = true;
                } else {
                    wantedServoPos = ShooterGreenBallsConstants.closedServoPos;
                    GlobalData.isReadyToShoot = false;
                }
                break;
            case STOP:
                wantedPower = ShooterGreenBallsConstants.stopPower;
                break;
        }

        greenBallsMotor.setPower(wantedPower);
        greenBallsServo.setPosition(wantedServoPos);
        lastState = state;
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
