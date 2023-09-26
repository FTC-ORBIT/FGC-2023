package org.firstinspires.ftc.teamcode.robotSubSystems.Elevator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.OrbitUtils.PID;

public class Elevator {

    private static DcMotor elevatorMotor;
    private static double wanted = 0;
    private static final PID elevatorPID = new PID(ElevatorConstants.kp, ElevatorConstants.ki, ElevatorConstants.kd, 0, 0);
    private static float height = 0;

    public static void init(HardwareMap hardwareMap){
        elevatorMotor = hardwareMap.get(DcMotor.class, "elevatorMotor");
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
        }
    }

    public static void setPower (float power){
        elevatorMotor.setPower(power);            //for the override. this will be implemented in the subSystemManager
    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        elevatorMotor.setPower(gamepad.right_stick_y);
    }

}
