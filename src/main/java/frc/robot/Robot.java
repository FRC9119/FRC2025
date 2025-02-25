/*

    Morgan Hissim
    Devin Masic

    North Side High School Iron Legends FRC #9119
    2025 Reefscape Software

    Updated: February 19, 2025

 */

// Package declaration.
package frc.robot;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkFlex;
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

// The robot class.
public class Robot extends TimedRobot {

    // Autonomous menu selection on Smart Dashboard.
    private static final String doNothingAuto = "Do nothing.";
    private static final String driveForward = "Drive forward.";
    private String autoSelected;
    private final SendableChooser<String> sendableChooser = new SendableChooser<>();

    // Drivetrain motor declaration
    private  SparkFlex leftRearMotor;
    private  SparkFlex leftFrontMotor;
    private  SparkFlex rightRearMotor;
    private  SparkFlex rightFrontMotor;
    private  SparkFlex centerLeftMotor;
    private  SparkFlex centerRightMotor;

    // Algae intake motor declaration
    private SparkFlex algaeIntakeRollersMotor;
    private SparkFlex algaeIntakeHingeMotor;

    // Coral intake motor declaration
    private SparkFlex coralIntakeLeftMotor;
    private SparkFlex coralIntakeRightMotor;
    
    // Drivetrain configuration declaration
    private final SparkMaxConfig leftFrontMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig leftRearMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig rightFrontMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig rightRearMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig centerLeftMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig centerRightMotorConfig = new SparkMaxConfig();
        
    // Algae configuration declaration
    private final SparkMaxConfig algaeIntakeRollersMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig algaeIntakeHingeMotorConfig = new SparkMaxConfig();

    // Coral configuration declaration
    private final SparkMaxConfig coralIntakeLeftMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig coralIntakeRightMotorConfig = new SparkMaxConfig();
    
    // Differential drive train declaration.
    DifferentialDrive tankDriveTrain;
    
    // Object creation of joystick controllers.
    Joystick leftJoystick = new Joystick(0);       // Left vertical and horizontal driving.
    Joystick rightJoystick = new Joystick(1);      // Right vertical driving.
    Joystick operatorController = new Joystick(2); // Operator controller.
    
        // Initializes conditions required before robot is used in autonomous and teleop stages.
        @Override
        public void robotInit() {
    
            // Common config
            SparkMaxConfig commonConfig = new SparkMaxConfig();
    
            // Drivetrain motor initialization
            leftRearMotor = new SparkFlex(3, MotorType.kBrushed);
            leftFrontMotor = new SparkFlex(2, MotorType.kBrushed);
            rightRearMotor = new SparkFlex(4, MotorType.kBrushed);
            rightFrontMotor = new SparkFlex(5, MotorType.kBrushed);
            centerLeftMotor = new SparkFlex(6, MotorType.kBrushed);
            centerRightMotor = new SparkFlex(7, MotorType.kBrushed);
    
            // Algae intake motor initialization
            // February 24th, 2025 || Changed algaeIntakeHingeMotor to kBrushless because it is indeed a brushless motor - Devin
            algaeIntakeRollersMotor = new SparkFlex(9, MotorType.kBrushed);
            algaeIntakeHingeMotor = new SparkFlex(8, MotorType.kBrushless);

            // Coral intake motor initialization
            coralIntakeLeftMotor = new SparkFlex(10, MotorType.kBrushed);
            coralIntakeRightMotor = new SparkFlex(11, MotorType.kBrushed);

            // Drivetrain configuration intitialization
            leftFrontMotorConfig.apply(commonConfig).idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).inverted(true);
            leftRearMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).follow(2);
            rightFrontMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).inverted(false);
            rightRearMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).follow(5);
            centerLeftMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).inverted(true);
            centerRightMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).inverted(true).follow(6);

            // Algae intake configuration initialization
            algaeIntakeRollersMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30);
            algaeIntakeHingeMotorConfig.idleMode(IdleMode.kCoast).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30);

            // Coral intake configuration initialization
            coralIntakeLeftMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30);
            coralIntakeRightMotorConfig.idleMode(IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30);

            // Drivetrain motor configuration with safe
            leftRearMotor.configure(leftRearMotorConfig, null, PersistMode.kNoPersistParameters);
            leftFrontMotor.configure(leftFrontMotorConfig, null, PersistMode.kNoPersistParameters);
            rightRearMotor.configure(rightRearMotorConfig, null, PersistMode.kNoPersistParameters);
            rightFrontMotor.configure(rightFrontMotorConfig, null, PersistMode.kNoPersistParameters);
            centerLeftMotor.configure(centerLeftMotorConfig, null, PersistMode.kNoPersistParameters);
            centerRightMotor.configure(centerRightMotorConfig, null, PersistMode.kNoPersistParameters);

            // Algae intake motor configuration with safe
            algaeIntakeRollersMotor.configure(algaeIntakeRollersMotorConfig, null, PersistMode.kNoPersistParameters);
            algaeIntakeHingeMotor.configure(algaeIntakeHingeMotorConfig, null, PersistMode.kNoPersistParameters);

            // Coral intake motor configuration with safe
            coralIntakeLeftMotor.configure(coralIntakeLeftMotorConfig, null, PersistMode.kNoPersistParameters);
            coralIntakeRightMotor.configure(coralIntakeRightMotorConfig, null, PersistMode.kNoPersistParameters);

            // Makes the Smart Dashboard buttons work and make the selectable.
            sendableChooser.setDefaultOption("Do nothing.", doNothingAuto);
            sendableChooser.addOption("Drive forward.", driveForward);
            SmartDashboard.putData("Auto choices:", sendableChooser);

            // Assigns new differential drive object and links left and right drive train motors.
            tankDriveTrain = new DifferentialDrive(leftFrontMotor, rightFrontMotor);

        } // end robotInit method.

    // This function is called every 20 ms. This function is used as a potential diagnostic for problems.
    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Time (seconds)", Timer.getFPGATimestamp());
    } // end robotPeriodic method.

    // Auto constants declaration.
    double AUTO_DRIVE_DELAY_S;
    double AUTO_DRIVE_TIME_S;
    double AUTO_DRIVE_SPEED;

    // Competition auto start time declaration.
    double autonomousStartTime;

    // Initializes conditions needed for autonomous stage.
    @Override
    public void autonomousInit() {

        // Gets the selected autonomous mode.
        autoSelected = sendableChooser.getSelected();

        // Sets autonomous constants.
        AUTO_DRIVE_DELAY_S = 3;
        AUTO_DRIVE_TIME_S = 2.0;
        AUTO_DRIVE_SPEED = -0.5;

        // If there are other functions besides the drive train, this is where you set them to zero.
        if (autoSelected.equals(doNothingAuto)) {

            AUTO_DRIVE_SPEED = 0;    // Sets drive speed to zero to be called later in autonomous periodic.

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
        if (timeElapsed < AUTO_DRIVE_DELAY_S) {

            // With a series of if, else if, and else statements, create the commands you want deployed during certain time intervals.

        }

    } // end autonomousPeriodic method. 

    // Initializes conditions needed for teleop stage.
    @Override
    public void teleopInit() {

        // Allows to motors to adjust to robot's natural momentum.
        leftRearMotorConfig.idleMode(IdleMode.kCoast);
        leftFrontMotorConfig.idleMode(IdleMode.kCoast);
        rightRearMotorConfig.idleMode(IdleMode.kCoast);
        rightFrontMotorConfig.idleMode(IdleMode.kCoast);
        centerLeftMotorConfig.idleMode(IdleMode.kCoast);
        centerRightMotorConfig.idleMode(IdleMode.kCoast);

    } // end teleopInit method.

    // This function is called periodically during operator control.
    @Override
    public void teleopPeriodic() { 

        /*
         * X-axis is axis 0.
         * Y-axis is axis 1.
         * Triggers are button 0 for both joysticks.
        */
    
        // DRIVE TRAIN
        // Button mapping for joysticks that control the drive train.
        tankDriveTrain.tankDrive(-0.9*Math.atan(leftJoystick.getRawAxis(1)), -0.9*Math.atan(rightJoystick.getRawAxis(1)), false); 

        // Button mapping for middle wheel.
        centerLeftMotor.set(-0.9*leftJoystick.getRawAxis(0)); 

        // ALGAE INTAKE
        // Button mapping for rollers.
        if (operatorController.getRawButton(3)) {
            algaeIntakeRollersMotor.set(0.4);
        } else if (operatorController.getRawButton(4)) {
            algaeIntakeRollersMotor.set(-0.4);
        } else {
            algaeIntakeRollersMotor.set(0);
        }

       // Button mapping for hinge.
       if (operatorController.getPOV() == 180) {
           algaeIntakeHingeMotor.set(0.3);
      } else if (operatorController.getPOV() == 0) {
           algaeIntakeHingeMotor.set(-0.3);
       } else {
           algaeIntakeHingeMotor.set(0);
       }

       // CORAL INTAKE
       // Button mapping for rollers.
       if (operatorController.getRawButton(1)) {
            coralIntakeLeftMotor.set(0.5);
            coralIntakeRightMotor.set(-0.5);
       } else if (operatorController.getRawButton(2)) {
            coralIntakeLeftMotor.set(-0.5);
            coralIntakeRightMotor.set(0.5);
       } else {
            coralIntakeLeftMotor.set(0);
            coralIntakeRightMotor.set(0);
       }

        
    } // end teleopPeriodic method.
}