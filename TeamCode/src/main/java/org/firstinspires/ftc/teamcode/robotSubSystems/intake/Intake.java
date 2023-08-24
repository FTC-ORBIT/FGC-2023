package org.firstinspires.ftc.teamcode.robotSubSystems.intake;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    private static DcMotor intakeMotor;

    public static void init(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void operate(IntakeState state) {
        double power = 0;
        switch (state){
            case INTAKE:
                power = IntakeConstants.intakePower; //Needs to be checked on the robot
                break;
            case STOP:
                power = 0;
                break;
        }
        intakeMotor.setPower(power);
    }

    public static void firstTime(Gamepad gamepad){ //only for the first time for the configuration
        intakeMotor.setPower(gamepad.left_stick_y);
    }

}

