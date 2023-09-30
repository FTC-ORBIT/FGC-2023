package org.firstinspires.ftc.teamcode.robotSubSystems.Elevator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OrbitUtils.PID;
import org.firstinspires.ftc.teamcode.Sensors.OrbitGyro;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;

public class Elevator {

    private static DcMotor elevatorMotor;
    private static double motorPower = 0;
    public static Servo elevatorServo;
    private static boolean servoToggle = false;
    private static boolean lastDPadRight = true;


    public static void init(HardwareMap hardwareMap){
        elevatorMotor = hardwareMap.get(DcMotor.class, "elevatorMotor");
        elevatorServo = hardwareMap.get(Servo.class, "elevatorServo");


        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static void operate (ElevatorState state, Gamepad gamepad){

        switch (state){
            case CLIMB:
                motorPower = ElevatorConstants.powerUp;
                break;
            case CLOSED:
                motorPower = ElevatorConstants.powerDown;
                servoToggle = false;
                break;
            case STOP:
                motorPower = 0;
                break;
        }

        elevatorMotor.setPower(motorPower);

        if (gamepad.dpad_right && !lastDPadRight){
        servoToggle = !servoToggle;
}
        if (servoToggle){
            elevatorServo.setPosition(ElevatorConstants.servoOpen);
        } else {
            elevatorServo.setPosition(ElevatorConstants.servoClose);
        }
        lastDPadRight = gamepad.dpad_right;
    }

    public static void setPower (float power){
        elevatorMotor.setPower(power);            //for the override. this will be implemented in the subSystemManager
    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        elevatorMotor.setPower(gamepad.right_stick_y);
    }

}
