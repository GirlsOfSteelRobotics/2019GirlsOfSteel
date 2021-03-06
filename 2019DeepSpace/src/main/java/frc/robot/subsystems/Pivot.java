/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap; 
import frc.robot.commands.PivotHold;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; 
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Pivot extends Subsystem {

  private WPI_TalonSRX pivot; 
	private double goalPivotPosition;

	public static final double PIVOT_DOWN_INCREMENT = 125; 
	public static final double PIVOT_UP_INCREMENT = 200; 
	public static final double PIVOT_GROUND = -3875; // -4121 was the pre pool noodle value
	public static final double PIVOT_ROCKET = -1371; //-1775  
	public static final double PIVOT_CARGO = -559; // -838
	public static final double PIVOT_TOLERANCE = 100;

	public static enum PivotDirection {
		Up, Down
	};
  
  public Pivot() {
		pivot = new WPI_TalonSRX(RobotMap.PIVOT_TALON);
		pivot.setSensorPhase(true);
		setupPivotFPID();
		LiveWindow.add(pivot);
	}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new PivotHold()); 
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

	public void setupPivotFPID() {
		pivot.config_kF(0, 0, 10);
		pivot.config_kP(0, 1.5, 10);
		pivot.config_kI(0, 0, 10);
		pivot.config_kD(0, 15, 10);	
  }

  public void setPivotPosition(double pos){
    pivot.set(ControlMode.Position, pos); 
  }

  public void holdPivotPosition(){
		pivot.set(ControlMode.Position, goalPivotPosition); 
		//System.out.println("Pivot goal position" + goalPivotPosition +  "actual position " + pivot.getSelectedSensorPosition(0));
  }

  public void pivotToGround(){
		pivot.set(ControlMode.Position, PIVOT_GROUND); 
  }

  public void pivotToRocket(){
		pivot.set(ControlMode.Position, PIVOT_ROCKET); 
	}
	
	public void pivotToCargo(){
		pivot.set(ControlMode.Position, PIVOT_CARGO); 
  }
	
	public void setGoalPivotPosition(double goal) {
		goalPivotPosition = goal;
	}
	
	public double getPivotPosition() {
		return pivot.getSelectedSensorPosition(0);
	}

	public void incrementPivot () {
		goalPivotPosition = getPivotPosition(); 
		goalPivotPosition += PIVOT_UP_INCREMENT;
	}

	public void decrementPivot () {
		goalPivotPosition = getPivotPosition(); 
		goalPivotPosition -= PIVOT_DOWN_INCREMENT;
	}

	public boolean checkCurrentPivotPosition(double goalPos) {
		boolean isFinished = (goalPos <= getPivotPosition() + PIVOT_TOLERANCE 
    && goalPos  >= getPivotPosition() - PIVOT_TOLERANCE);
    //System.out.println("isFinished: " + isFinished);
    return isFinished;
	}

	public void pivotStop(){
		pivot.stopMotor(); 
	}	
}

