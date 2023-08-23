package org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain {

    private static final DcMotor[] motors = new DcMotor[2];
    static ElapsedTime time = new ElapsedTime();

    public static void init(HardwareMap hardwareMap) {
        time.reset();
        motors[0] = hardwareMap.get(DcMotor.class, "leftMotor");
        motors[1] = hardwareMap.get(DcMotor.class, "rightMotor");
        //TODO make sure to reverse the correct motors according to your robot
        for (final DcMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }


    public static void operate(float y_Power, float right_trigger, float left_trigger) {
        float lMotorPower = (y_Power + right_trigger - left_trigger);
        float rMotorPower = (y_Power + left_trigger - right_trigger);

        motors[0].setPower(lMotorPower);
        motors[1].setPower(rMotorPower);
    }

    public static void overide (Gamepad gamepad){
        motors[0].setPower(gamepad.left_stick_y); //leftMotor
        motors[1].setPower(gamepad.right_stick_y); //rightMotor
    }
}
