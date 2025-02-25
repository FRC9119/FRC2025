/*

    Morgan Hissim

    North Side High School Iron Legends FRC #9119
    2025 Reefscape Software

 */

// Package declaration.
package frc.robot;

// WPI import.

import edu.wpi.first.wpilibj.RobotBase;

// Main class that runs the overall program.
public final class Main {
    private Main() {
    }

    // Main method.
    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    } // end main method.
}
