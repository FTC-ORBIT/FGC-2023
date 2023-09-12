package org.firstinspires.ftc.teamcode.robotSubSystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.Conveyor.ConveyorState;
import org.firstinspires.ftc.teamcode.robotSubSystems.Elevator.Elevator;
import org.firstinspires.ftc.teamcode.robotSubSystems.Elevator.ElevatorState;
import org.firstinspires.ftc.teamcode.robotSubSystems.intake.IntakeState;

public class SubSystemManager {

    ConveyorState conveyorState = ConveyorState.STOP;
    ElevatorState elevatorState = ElevatorState.CLOSED;
    IntakeState intakeState = IntakeState.STOP;
    


    public static RobotState getStateFromJoystick(Gamepad gamepad) {
        return gamepad.b ? RobotState.TRAVEL
                : gamepad.a ? RobotState.INTAKE
                : gamepad.x ? RobotState.SHOOT_BLUE
                : gamepad.y ? RobotState.SHOOT_GREEN : null;
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

    public static void setSubsystemToState(final RobotState robotState) {
        switch (robotState) {
            case TRAVEL:
            default:
                // Intake.set(IntakeState.CLOSED);
                break;

            case INTAKE:
                // Intake.set(IntakeState.COLLECT);
                break;
        }


        prevRobotState = robotState;
    }
}
