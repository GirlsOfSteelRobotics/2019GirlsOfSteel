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
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX pivot; 
  private double goalPivotPosition; 

  // TODO: tune all
  public static final double WRIST_IN_BOUND = -60; 
	public static final double WRIST_OUT_BOUND = -1000; 
	public static final double WRIST_COLLECT = -930; 
	public static final double WRIST_SWITCH = -800; 
	public static final double WRIST_INCREMENT = 20; 
	public static final double WRIST_SHOOT = -350;
  
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

  public void setupPivotFPID() {
		pivot.config_kF(0, 0, 10);
		pivot.config_kP(0, 1.5, 10);
		pivot.config_kI(0, 0, 10);
		pivot.config_kD(0, 15, 10);	
  }

  public void setPivotSpeed(double speed){
    pivot.set(speed); 
  }

  public void setPivotPosition(double pos){
    pivot.set(ControlMode.Position, pos); 

  }

  public void pivotStop(){
    pivot.stopMotor(); 
  }

  public void holdPivotPosition(){
    pivot.set(ControlMode.Position, goalPivotPosition); 
  }

  public void wristIn(){
    double goalPosition = goalPivotPosition + WRIST_INCREMENT;
		if (goalPosition >= WRIST_IN_BOUND)
		{
			goalPivotPosition = WRIST_IN_BOUND;
		}
		else
		{
			goalPivotPosition = goalPosition;
		}

  }

  public void pivotOut()
	{
		double goalPosition = goalPivotPosition - WRIST_INCREMENT;
		if (goalPosition <= WRIST_OUT_BOUND) {
			goalPivotPosition = WRIST_OUT_BOUND;
		}	else {
			goalPivotPosition = goalPosition;
    }
    
  }
	
	public void setGoalPivotPosition(double goal) {
		goalPivotPosition = goal;
	}
	
	public double getPivotPosition() {
		return pivot.getSelectedSensorPosition(0);
	}

	public void incrementPivot () {
		goalPivotPosition += WRIST_INCREMENT; // TODO: Adjust
	}

	public void decrementPivot () {
		goalPivotPosition -= WRIST_INCREMENT; //TODO: Adjust
	}
}