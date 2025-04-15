/*

    Morgan Hissim
    Devin Masic

    North Side High School Iron Legends FRC #9119
    2025 Reefscape Software

    Updated: February 26, 2025

 */

// Package declaration.
package frc.robot.main;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;


// WPI imports.
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.resources.Constants;
import frc.robot.subsystems.AlgaeIntake;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.TankDrive;

// The robot class.
public class Robot extends TimedRobot {

    // Declaration of Subsystems
    public Constants constants = new Constants();
    public TankDrive tankDrive = new TankDrive();
    public AlgaeIntake algaeIntake = new AlgaeIntake();
    public CoralIntake coralIntake = new CoralIntake();

    // Autonomous menu selection on Smart Dashboard.
    private static final String doNothingAuto = "Do nothing.";
    private static final String driveForward = "Drive forward.";
    private String autoSelected;
    private final SendableChooser<String> sendableChooser = new SendableChooser<>();
    
        // Initializes conditions required before robot is used in autonomous and teleop stages.
        @Override
        public void robotInit() {

            // Makes the Smart Dashboard buttons work and make the selectable.
            sendableChooser.setDefaultOption("Do nothing.", doNothingAuto);
            sendableChooser.addOption("Drive forward.", driveForward);
            SmartDashboard.putData("Auto choices:", sendableChooser);

        } // end robotInit method.

    // This function is called every 20 ms. This function is used as a potential diagnostic for problems.
    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Time (seconds)", Timer.getFPGATimestamp());
    } // end robotPeriodic method.

    // Competition auto start time declaration.
    double autonomousStartTime;

    // Initializes conditions needed for autonomous stage.
    @Override
    public void autonomousInit() {

        // Gets the selected autonomous mode.
        autoSelected = sendableChooser.getSelected();

        // If there are other functions besides the drive train, this is where you set them to zero.
        if (autoSelected.equals(doNothingAuto)) {

            // This is where you would set the other functions to be zero and leave blank if drive train is moving.

        } else if (autoSelected.equals(driveForward)) {

            // This is where you would set the other functions to be zero and leave blank if drive train is moving.

        }

        autonomousStartTime = Timer.getFPGATimestamp();

    } // end autonomousInit method.

    // This function is called periodically during autonomous.
    @Override
    public void autonomousPeriodic() {

        double timeElapsed = Timer.getFPGATimestamp() - autonomousStartTime;

        // Sample autonomous route.
        if (timeElapsed < constants.AUTO_DRIVE_DELAY_S) {

            // With a series of if, else if, and else statements, create the commands you want deployed during certain time intervals.

        }

    } // end autonomousPeriodic method. 

    // Initializes conditions needed for teleop stage.
    @Override
    public void teleopInit() {

        // DRIVE TRAIN
        // Sets motor idle modes to coast
        tankDrive.driveInit();

    } // end teleopInit method.

    // This function is called periodically during operator control.
    @Override
    public void teleopPeriodic() { 

        // DRIVE TRAIN
        tankDrive.joystickDrive();

        // ALGAE INTAKE
        algaeIntake.rollerControl();
        algaeIntake.hingeControl();

       // CORAL INTAKE
       coralIntake.coralControl();
        
    } // end teleopPeriodic method.
}