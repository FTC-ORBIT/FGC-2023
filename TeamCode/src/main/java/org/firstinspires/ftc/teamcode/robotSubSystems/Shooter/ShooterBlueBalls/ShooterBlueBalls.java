package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterBlueBalls;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Sensors.OrbitColorSensor;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterState;

public class ShooterBlueBalls extends Shooter {
    private static DcMotor shooterMotor1;
    private static DcMotor shooterMotor2;
    private static CRServo shooterServo;
    private static double power = 0;
    private static ShooterState lastState = ShooterState.STOP;
    private static double startedShootingTime = 0;
    private static boolean lastRightBumper = true;
    private static boolean lastLeftBumper = true;

    public void init(HardwareMap hardwareMap){
        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterBlueBallsMotor2");
        shooterServo = hardwareMap.get(CRServo.class, "shooterBlueBallsServo");
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
                    shooterServo.setPower(1);
                } else {
                    shooterServo.setPower(0);
                }
                break;
            case STOP:
                power = 0;
                shooterServo.setPower(0);
                break;
        }


        shooterMotor1.setPower(power);
        shooterMotor2.setPower(power);

        lastState = state;
    }




    public void firstTime(Gamepad gamepad, Telemetry telemetry){ //only for the first time for the configuration
        shooterMotor1.setPower(gamepad.left_stick_y);
        shooterMotor2.setPower(gamepad.right_stick_y);

        double servoPos = 0;

        if (gamepad.right_bumper) {
            servoPos += 1;
        } else if (gamepad.left_bumper) {
            servoPos -= 1;
        } else servoPos = 0;

        shooterServo.setPower(servoPos);

        telemetry.addData("blue", servoPos);

        lastRightBumper = gamepad.right_bumper;
        lastLeftBumper = gamepad.left_bumper;

    }


}
