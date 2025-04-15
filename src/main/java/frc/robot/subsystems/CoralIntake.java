package frc.robot.subsystems;


import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.resources.Constants;

public class CoralIntake extends SubsystemBase {

    // Constants Declaration
    public Constants constants = new Constants();

    // Coral intake motor declaration
    private SparkMax coralIntakeLeftMotor;
    private SparkMax coralIntakeRightMotor;

    // Coral configuration declaration
    private final SparkMaxConfig coralIntakeLeftMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig coralIntakeRightMotorConfig = new SparkMaxConfig();

    Joystick operatorController = new Joystick(2); // Operator controller.

    public CoralIntake() {

        // Coral intake motor initialization
        coralIntakeLeftMotor = new SparkMax(constants.CORAL_INTAKE_LEFT_CAN_ID, SparkLowLevel.MotorType.kBrushed);
        coralIntakeRightMotor = new SparkMax(constants.CORAL_INTAKE_RIGHT_CAN_ID, SparkLowLevel.MotorType.kBrushed);

        // Coral intake configuration initialization
        coralIntakeLeftMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
                .smartCurrentLimit(30,20);
        coralIntakeRightMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
                .smartCurrentLimit(30,20);

        // Coral intake motor configuration with safe
        coralIntakeLeftMotor.configure(coralIntakeLeftMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);
        coralIntakeRightMotor.configure(coralIntakeRightMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);

    }

    public void coralControl() {

        // Button mapping for rollers.
        if (operatorController.getRawButton(1)) {
            coralIntakeLeftMotor.set(constants.CORAL_INTAKE_LEFT_SPEED);
            coralIntakeRightMotor.set(constants.CORAL_INTAKE_RIGHT_SPEED);
        } else if (operatorController.getRawButton(2)) {
            coralIntakeLeftMotor.set(-constants.CORAL_INTAKE_LEFT_SPEED);
            coralIntakeRightMotor.set(-constants.CORAL_INTAKE_RIGHT_SPEED);
        } else {
            coralIntakeLeftMotor.set(0);
            coralIntakeRightMotor.set(0);
        }

    }

}

