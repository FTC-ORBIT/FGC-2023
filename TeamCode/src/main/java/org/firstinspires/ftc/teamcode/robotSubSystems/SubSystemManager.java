package org.firstinspires.ftc.teamcode.robotSubSystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor.Conveyor;
import org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor.ConveyorState;
import org.firstinspires.ftc.teamcode.robotSubSystems.Elevator.Elevator;
import org.firstinspires.ftc.teamcode.robotSubSystems.Elevator.ElevatorState;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterBlueBalls.ShooterBlueBalls;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterGreenBalls.ShooterGreenBalls;
import org.firstinspires.ftc.teamcode.robotSubSystems.Shooter.ShooterState;
import org.firstinspires.ftc.teamcode.robotSubSystems.TankGrabber.TankGrabber;
import org.firstinspires.ftc.teamcode.robotSubSystems.TankGrabber.TankGrabberStates;
import org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.robotSubSystems.intake.Intake;
import org.firstinspires.ftc.teamcode.robotSubSystems.intake.IntakeState;

public class SubSystemManager {

   private static ConveyorState conveyorState = ConveyorState.STOP;
   private static IntakeState intakeState = IntakeState.STOP;
   private static ElevatorState elevatorState = ElevatorState.CLOSED;
   private static TankGrabberStates tankGrabberState = TankGrabberStates.OPEN;
   private static ShooterState shooterBlueBallsState = ShooterState.STOP;
   private static ShooterState shooterGreenBallsState = ShooterState.STOP;
   private static boolean lastRightBumper;
   private static Shooter shooterBlueBalls = new ShooterBlueBalls();
   private static Shooter shooterGreenBalls = new ShooterGreenBalls();



    public static RobotState getStateFromJoystick(Gamepad gamepad) {
        return gamepad.b ? RobotState.TRAVEL
                : gamepad.a ? RobotState.INTAKE
                : gamepad.x ? RobotState.SHOOT_BLUE
                : gamepad.y ? RobotState.SHOOT_GREEN
                : gamepad.dpad_up || gamepad.dpad_down ? RobotState.CLIMB : null;
    }

    private static RobotState getStateFromWantedAndCurrent(final boolean[] buttons,
                                                           final RobotState robotStateFromJoystick) {     // what this function is supposed to do?
        RobotState state_W = robotStateFromJoystick;
        switch (GlobalData.robotState) {
            case INTAKE:
                break;

            default:
                break;

        }
        return state_W;
    }

    private static RobotState prevRobotState = GlobalData.robotState;

    public static void setSubsystemToState(final RobotState robotState, Gamepad gamepad, Telemetry telemetry) {
        switch (robotState) {
            case TRAVEL:
            default:
                conveyorState = ConveyorState.TRANSPORT;
                intakeState = IntakeState.STOP;
                break;
            case INTAKE:
                conveyorState = ConveyorState.TRANSPORT;
                intakeState = IntakeState.INTAKE;
                break;
            case SHOOT_BLUE:
                conveyorState = ConveyorState.TRANSPORT;
                intakeState = IntakeState.STOP;
                shooterBlueBallsState = ShooterState.SHOOT;
                break;
            case SHOOT_GREEN:
                conveyorState = ConveyorState.TRANSPORT;
                intakeState = IntakeState.STOP;
                shooterGreenBallsState = ShooterState.SHOOT;
                break;
            case CLIMB:
                conveyorState = ConveyorState.STOP;
                intakeState = IntakeState.STOP;
                shooterBlueBallsState = ShooterState.STOP;
                shooterGreenBallsState = ShooterState.STOP;
                if (gamepad.dpad_up){ // I'm aware that this is not the best way to handle this situation, yet, I didn't find a better solution and due to the lack of time i'm not sure it's worth finding the best solution
                 elevatorState = ElevatorState.CLIMB;
                } else if (gamepad.dpad_down){
                    elevatorState = ElevatorState.CLOSED;
                }
        }

        if (!lastRightBumper && gamepad.right_bumper){
            tankGrabberState = tankGrabberState.equals(TankGrabberStates.OPEN) ? TankGrabberStates.CLOSED : TankGrabberStates.OPEN;
        }

        Conveyor.operate(conveyorState);
        Drivetrain.operate(-gamepad.left_stick_y, gamepad.right_trigger, gamepad.left_trigger);
        Elevator.operate(elevatorState, telemetry);
        Intake.operate(intakeState);
        shooterBlueBalls.operate(shooterBlueBallsState);
        shooterGreenBalls.operate(shooterGreenBallsState);
        TankGrabber.operate(tankGrabberState);




        prevRobotState = robotState;
    }
}
