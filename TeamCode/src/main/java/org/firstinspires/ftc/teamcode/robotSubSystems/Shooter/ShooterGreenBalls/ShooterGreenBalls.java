package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterGreenBalls;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.RobotState;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterBlueBalls.ShooterBlueBallsConstants;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterState;

public class ShooterGreenBalls extends Shooter {

    private static DcMotor greenBallsMotor;
    public static CRServo shooterServo;
    private static double wantedPower = 0;
    public static double wantedServoPos = 0;
    private static boolean lastRightBumper;
    private static boolean lastLeftBumper;
    private static ShooterState lastState = ShooterState.STOP;
    private static double startedShootingTime = 0;

    @Override
    public void init(HardwareMap hardwareMap) {
        greenBallsMotor = hardwareMap.get(DcMotor.class, "greenBallsMotor");
        shooterServo = hardwareMap.get(CRServo.class, "greenBallsServo");

        greenBallsMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterServo.setDirection(DcMotorSimple.Direction.REVERSE);
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
                    shooterServo.setPower(1);
                    GlobalData.isReadyToShoot = true;
                } else {
                    shooterServo.setPower(0);
                    GlobalData.isReadyToShoot = false;
                }
                break;
            case STOP:
                wantedPower = ShooterGreenBallsConstants.stopPower;
                shooterServo.setPower(0);
                break;
        }

        greenBallsMotor.setPower(wantedPower);
//        greenBallsServo.setPosition(wantedServoPos);
        lastState = state;
    }


    @Override
    public void firstTime(Gamepad gamepad, Telemetry telemetry) {
        greenBallsMotor.setPower(gamepad.left_stick_y);

        double servoPos = 0;

        if (gamepad.b) {
            servoPos += 1;
        } else if (gamepad.x) {
            servoPos -= 1;
        } else servoPos = 0;

        shooterServo.setPower(servoPos);

        telemetry.addData("green", servoPos);

        lastRightBumper = gamepad.right_bumper;
        lastLeftBumper = gamepad.left_bumper;

    }
}