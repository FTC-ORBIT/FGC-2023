package org.firstinspires.ftc.teamcode.robotSubSystems.intake;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private static DcMotor intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void operate (IntakeState state) {
        switch (state) {
            case INTAKE:
                intakeMotor.setPower(IntakeConstants.intakePower);
                break;
            case STOP:
                intakeMotor.setPower(0);
                break;
        }

    }
}

