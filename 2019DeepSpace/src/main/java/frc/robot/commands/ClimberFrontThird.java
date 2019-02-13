/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot; 

public class ClimberFrontThird extends Command {
  public ClimberFrontThird() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climber); 
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climber.setGoalClimberPosition(Robot.climber.THIRD_GOAL_POS);

    System.out.println("init Climber to third all up");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.climber.holdClimberFrontPosition();
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    Robot.climber.checkCurrentFrontPosition(Robot.climber.THIRD_GOAL_POS);
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.climberStop(); 
    System.out.println("end Climber to third all up");

  }

  
}