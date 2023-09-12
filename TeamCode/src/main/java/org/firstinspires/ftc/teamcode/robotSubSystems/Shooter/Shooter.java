package org.firstinspires.ftc.teamcode.robotSubSystems.Shooter;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Shooter {
    public abstract void init(HardwareMap hardwareMap);
    public abstract void operate(ShooterState state);
    public abstract void update();
    public abstract void firstTime(Gamepad gamepad);
}
