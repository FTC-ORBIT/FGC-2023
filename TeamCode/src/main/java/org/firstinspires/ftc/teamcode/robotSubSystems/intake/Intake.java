package org.firstinspires.ftc.teamcode.robotSubSystems.intake;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private static DcMotor intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
    }

    public void operate() {
        intakeMotor.setPower(IntakeConstants.intakePower);
    }

    public void stop() {
        intakeMotor.setPower(0);
    }

}
