package org.firstinspires.ftc.teamcode.robotSubSystems.Elevator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OrbitUtils.PID;

public class Elevator {

    private static DcMotor elevatorMotor;
    public static Servo elevatorServo;
    private static double wanted = 0;
    private static final PID elevatorPID = new PID(ElevatorConstants.kp, ElevatorConstants.ki, ElevatorConstants.kd, 0, 0);
    private static float height = 0;
    private static boolean servoToggle = false;
    private static boolean lastDpad = false;


    public static void init(HardwareMap hardwareMap){
        elevatorMotor = hardwareMap.get(DcMotor.class, "elevatorMotor");
        elevatorServo = hardwareMap.get(Servo.class, "elevatorServo");


        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static void operate (Gamepad gamepad){
//        height = elevatorMotor.getCurrentPosition();
//        switch (state){
//            case CLOSED:
//                wanted = ElevatorConstants.homeHeight;
//                break;
//            case TANK:
//                wanted = ElevatorConstants.tankHeight;  // may be useless
//                break;
//            case CLIMB:
//                wanted = ElevatorConstants.climbHeight;
//                break;
//        }
//        elevatorPID.setWanted(wanted);
//        double elevatorMotorPower = elevatorPID.update(height);
//
//        elevatorMotor.setPower(elevatorMotorPower + ElevatorConstants.constantPower);

        if (gamepad.right_bumper){
            elevatorMotor.setPower(ElevatorConstants.powerUp);
        } else if (gamepad.left_bumper){
            elevatorMotor.setPower(ElevatorConstants.powerDown);
        } else elevatorMotor.setPower(0);

        if (gamepad.dpad_right && !lastDpad) servoToggle = !servoToggle;

        if (servoToggle){
            elevatorServo.setPosition(ElevatorConstants.servoOpen);
        } else elevatorServo.setPosition(ElevatorConstants.servoClose);

//        if (gamepad.dpad_up) wanted += 0.05;
//        else if (gamepad.dpad_down) wanted -= 0.05;
//
//        if (wanted < 0) wanted = 0;
//        if (wanted > 1) wanted = 1;
//
//        elevatorServo.setPosition(wanted);
        lastDpad = gamepad.dpad_right;
    }

    public static void setPower (float power){
        elevatorMotor.setPower(power);            //for the override. this will be implemented in the subSystemManager
    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        elevatorMotor.setPower(gamepad.right_stick_y);
    }

}
