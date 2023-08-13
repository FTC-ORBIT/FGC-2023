package org.firstinspires.ftc.teamcode.robotSubSystems.Elevator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OrbitUtils.PID;

public class Elevator {

    private static DcMotor elevatorMotor;
    private static float wanted = 0;
    private static PID elevatorPID = new PID(ElevatorConstants.kp, ElevatorConstants.ki, ElevatorConstants.kd, 0, 0);
    private static float height = 0;

    public Elevator(HardwareMap hardwareMap){
        elevatorMotor = hardwareMap.get(DcMotor.class, "elevatorMotor");
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static void operate (ElevatorState state, Telemetry telemetry){
        height = elevatorMotor.getCurrentPosition();
        switch (state){
            case CLOSED:
                wanted = ElevatorConstants.homeHeight;
                break;
            case TANK:
                wanted = ElevatorConstants.tankHeight;
                break;
            case CLIMB:
                wanted = ElevatorConstants.climbHeight;
                break;
        }
        elevatorPID.setWanted(wanted);
        double elevatorMotorPower = elevatorPID.update(height);

        elevatorMotor.setPower(elevatorMotorPower + ElevatorConstants.constantPower);

        telemetry.addData("height", height);
    }

}
