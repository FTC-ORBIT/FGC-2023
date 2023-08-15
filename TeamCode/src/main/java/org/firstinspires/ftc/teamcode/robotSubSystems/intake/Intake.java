package org.firstinspires.ftc.teamcode.robotSubSystems.intake;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private static DcMotor intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void operate(IntakeState state) {
        double power = 0;
        switch (state){
            case INTAKE:
                power = 1; //Needs to be checked on the robot
                break;
            case STOP:
                power = 0;
                break;

        }
        intakeMotor.setPower(power);
    }

}
