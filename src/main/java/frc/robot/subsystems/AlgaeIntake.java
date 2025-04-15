package frc.robot.subsystems;


import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.resources.Constants;

public class AlgaeIntake extends SubsystemBase {

    // Constants Declaration
    public Constants constants = new Constants();

    // Algae intake motor declaration
    private SparkMax algaeIntakeRollersMotor;
    private SparkMax algaeIntakeHingeMotor;

    // Algae configuration declaration
    private final SparkMaxConfig algaeIntakeRollersMotorConfig = new SparkMaxConfig();
    private final SparkMaxConfig algaeIntakeHingeMotorConfig = new SparkMaxConfig();

    // Object creation of joystick controllers.
    Joystick operatorController = new Joystick(2); // Operator controller.

    public AlgaeIntake() {

        // Common config
        SparkMaxConfig commonConfig = new SparkMaxConfig();

        // Algae intake motor initialization
        algaeIntakeRollersMotor = new SparkMax(constants.ALGAE_INTAKE_ROLLERS_CAN_ID, SparkLowLevel.MotorType.kBrushed);
        algaeIntakeHingeMotor = new SparkMax(constants.ALGAE_INTAKE_HINGE_CAN_ID, SparkLowLevel.MotorType.kBrushless);

        // Algae intake configuration initialization
        algaeIntakeRollersMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
                .smartCurrentLimit(40,30);
        algaeIntakeHingeMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake).openLoopRampRate(0.0).closedLoopRampRate(0.0)
                .smartCurrentLimit(40,30);

        // Algae intake motor configuration with safe
        algaeIntakeRollersMotor.configure(algaeIntakeRollersMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);
        algaeIntakeHingeMotor.configure(algaeIntakeHingeMotorConfig, null, SparkBase.PersistMode.kNoPersistParameters);

    }

    public void rollerControl() {

        // Button mapping for rollers.
        if (operatorController.getRawButton(4)) {
            algaeIntakeRollersMotor.set(constants.ALGAE_INTAKE_ROLLERS_SPEED);
        } else if (operatorController.getRawButton(3)) {
            algaeIntakeRollersMotor.set(-constants.CORAL_INTAKE_LEFT_SPEED);
        } else {
            algaeIntakeRollersMotor.set(0);
        }

    }

    public void hingeControl() {

        // Button mapping for hinge.
        if (operatorController.getPOV() == 180) {
            algaeIntakeHingeMotor.set(constants.ALGAE_INTAKE_HINGE_SPEED);
        } else if (operatorController.getPOV() == 0) {
            algaeIntakeHingeMotor.set(-constants.ALGAE_INTAKE_HINGE_SPEED);
        } else {
            algaeIntakeHingeMotor.set(0);
        }

    }

}

