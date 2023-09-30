package org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;

public class Conveyor {
    private static DcMotor conveyorMotor;
    private static CRServo conveyorServo;
    private static double motorPower = 0;
    private static double servoPower = 0;
    private  static  double servoSwitchTime = 0;
    private  static  boolean forward = false;
    public static void init(HardwareMap hardwareMap){
        conveyorMotor = hardwareMap.get(DcMotor.class, "conveyorMotor");
        conveyorServo = hardwareMap.get(CRServo.class,"conveyorServo");

        conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        conveyorMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        conveyorServo.setDirection(DcMotorSimple.Direction.FORWARD);


         servoSwitchTime = 0;
    }
    public static void operate(ConveyorState state, Gamepad gamepad){
        switch (state){
            case TRANSPORT:
                motorPower = ConveyorConstants.conveyorTransportPower;
                servoPower = 1;
                break;
            case BACKWARDS:
                motorPower = ConveyorConstants.conveyorBackwardsPower;
                servoPower = -1;
                break;
            case INTAKE:
                if(GlobalData.currentTime - servoSwitchTime > 300){
                    forward = !forward;
                    servoSwitchTime = GlobalData.currentTime;
                }
                motorPower = forward ? 1 : -1;
                servoPower = -1;
                break;
            case STOP:
                motorPower = 0;
                servoPower = 0;
                break;
            case OVERRIDE:
                motorPower = -gamepad.right_stick_x;
                servoPower = -gamepad.right_stick_x;
                break;
        }

        conveyorMotor.setPower(motorPower);
        conveyorServo.setPower(servoPower);

    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        conveyorMotor.setPower(gamepad.left_stick_y);
    }
}
