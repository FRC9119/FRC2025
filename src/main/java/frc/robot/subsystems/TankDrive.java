package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.resources.Constants;

public class TankDrive extends SubsystemBase {

  // Constants Declaration
  public Constants constants = new Constants();

  // Drivetrain motor declaration
  private SparkMax leftRearMotor;
  private SparkMax leftFrontMotor;
  private SparkMax rightRearMotor;
  private SparkMax rightFrontMotor;

  // Drivetrain configuration declaration
  private final SparkMaxConfig leftFrontMotorConfig = new SparkMaxConfig();
  private final SparkMaxConfig leftRearMotorConfig = new SparkMaxConfig();
  private final SparkMaxConfig rightFrontMotorConfig = new SparkMaxConfig();
  private final SparkMaxConfig rightRearMotorConfig = new SparkMaxConfig();

  // Differential drive train declaration.
  DifferentialDrive tankDriveTrain;

  // Object creation of joystick controllers.
  Joystick leftJoystick = new Joystick(0);       // Left vertical and horizontal driving.
  Joystick rightJoystick = new Joystick(1);      // Right vertical driving.

  public TankDrive() {

    // Common config
    SparkMaxConfig commonConfig = new SparkMaxConfig();

    // Drivetrain motor initialization
    leftRearMotor = new SparkMax(constants.LEFT_REAR_CAN_ID, SparkLowLevel.MotorType.kBrushed);
    leftFrontMotor = new SparkMax(constants.LEFT_FRONT_CAN_ID, SparkLowLevel.MotorType.kBrushed);
    rightRearMotor = new SparkMax(constants.RIGHT_REAR_CAN_ID, SparkLowLevel.MotorType.kBrushed);
    rightFrontMotor = new SparkMax(constants.RIGHT_FRONT_CAN_ID, SparkLowLevel.MotorType.kBrushed);

    // Drivetrain configuration initialization
    leftFrontMotorConfig.apply(commonConfig).idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).inverted(true);
    leftRearMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).follow(2);
    rightFrontMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).inverted(false);
    rightRearMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
            .smartCurrentLimit(40,30).follow(5);

    // Drivetrain motor configuration with safe
    leftRearMotor.configure(leftRearMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);
    leftFrontMotor.configure(leftFrontMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);
    rightRearMotor.configure(rightRearMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);
    rightFrontMotor.configure(rightFrontMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);

    // Assigns new differential drive object and links left and right drive train motors.
    tankDriveTrain = new DifferentialDrive(leftFrontMotor, rightFrontMotor);

  }

  public void driveInit() {

    // Allows to motors to adjust to robot's natural momentum.
    leftRearMotorConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
    leftFrontMotorConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
    rightRearMotorConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
    rightFrontMotorConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);

  }

  public void joystickDrive() {

    // Tank drive train joystick implementation
    tankDriveTrain.tankDrive(-0.9*Math.atan(leftJoystick.getRawAxis(1)), -0.9*Math.atan(rightJoystick.getRawAxis(1)), false);

  }


}


