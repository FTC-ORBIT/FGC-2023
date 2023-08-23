package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OrbitUtils.CSVWriter;
import org.firstinspires.ftc.teamcode.OrbitUtils.Vector;
import org.firstinspires.ftc.teamcode.Sensors.OrbitGyro;
import org.firstinspires.ftc.teamcode.positionTracker.Pose2DP;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor.Conveyor;
import org.firstinspires.ftc.teamcode.robotSubSystems.Elevator.Elevator;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.robotSubSystems.intake.Intake;

@TeleOp(name = "main")
public class Robot extends LinearOpMode {
    // * set new robot pose to 0,0 and heading to 0


    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain.init(hardwareMap);
        Conveyor.init(hardwareMap);
        Elevator.init(hardwareMap);
        Intake.init(hardwareMap);
        Shooter.init(hardwareMap);

        OrbitGyro.init(this.hardwareMap);

        GlobalData.voltageSensor = hardwareMap.voltageSensor.get("Motor Controller 1");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (!isStopRequested()){
            Drivetrain.overide(gamepad1);
            Conveyor.override(gamepad1);
            Elevator.override(gamepad1);
            Intake.override(gamepad1);
            Shooter.override(gamepad1);
        }

    }

}