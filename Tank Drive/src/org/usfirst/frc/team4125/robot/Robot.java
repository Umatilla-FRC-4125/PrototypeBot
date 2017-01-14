package org.usfirst.frc.team4125.robot;


import edu.wpi.first.wpilibj.SampleRobot;
//import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;

public class Robot extends SampleRobot {
	//public static USBCamera cam;
//	Compressor c;
	public static Solenoid sol;
	public SmartDashboard dashboard = new SmartDashboard();
	public static Ultrasonic ultraSonic;
	Encoder encoder;
	//public static GyroBase gyro;
	public static boolean selected = true;
    Joystick controller;
    //Joystick rightStick;// set to ID 1 in DriverStation
    Talon talon1, talon2, talon3, talon4, talon5, talon6, shooter;
    AnalogInput ai;
//    AnalogGyro gyro;
    SPI gyro;
    double value;
    boolean shooterToggle;
    boolean shooterButton;
    
    public Robot() {
//    	c = new Compressor();
        controller = new Joystick(0);
        talon1 = new Talon(1);
        talon2 = new Talon(2);
        talon3 = new Talon(3);
        talon4 = new Talon(4);
        talon5 = new Talon(5);
        talon6 = new Talon(6);
        shooter = new Talon(7);
        ai = new AnalogInput(1);
        gyro = new SPI(Port.kOnboardCS0);
//        ultraSonic = new Ultrasonic(7, 8);
        encoder = new Encoder(1, 2, false, Encoder.EncodingType.k4X);
    }

    public void teleopInit(){
    	talon1.set(0);
    	talon2.set(0);
    	talon3.set(0);
    	talon4.set(0);
		talon5.set(0);
		talon6.set(0);
		encoder.setMaxPeriod(.1);
		encoder.setMinRate(10);
		encoder.setDistancePerPulse(5);
		encoder.setReverseDirection(true);
		encoder.setSamplesToAverage(7);
		CameraServer.getInstance().startAutomaticCapture("cam0");
//		server.setQuality(50);
//		server.startAutomaticCapture("cam");
    }
    /**
     * Runs the motors with tank steering.
     */
    public void operatorControl() {
//    	camera.setQuality(50);
//    	camera.startAutomaticCapture("cam0");
    	talon1.setInverted(true);
    	talon2.setInverted(true);
    	talon3.setInverted(true);
    	encoder.reset();
        while (isOperatorControl() && isEnabled()) {
        	if (shooterToggle && controller.getRawButton(4)) {  // Only execute once per Button push
        		  shooterToggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
        		  if (shooterButton) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
        		    shooterButton= false;
        		    shooter.set(.5);
        		  } else {
        		    shooterButton= true;
        		    shooter.set(0);
        		  }
        		} else if(controller.getRawButton(4) == false) { 
        		    shooterToggle = true; // Button has been released, so this allows a re-press to activate the code above.
        		}
//        	c.start();
//        	camera.startAutomaticCapture("cam0");
        	encoder.startLiveWindowMode();
        	value = ai.getValue();
        	SmartDashboard.putNumber("UltraSonic", value);
        	SmartDashboard.putNumber("Encoder", encoder.getDistance());
//        	SmartDashboard.putData("camera", );
        	if(controller.getRawAxis(0) > 0.1){
            	talon1.set(controller.getRawAxis(1) - controller.getRawAxis(0));
            	talon2.set(controller.getRawAxis(1) - controller.getRawAxis(0));
            	talon3.set(controller.getRawAxis(1) - controller.getRawAxis(0));
            	talon4.set(controller.getRawAxis(1) + controller.getRawAxis(0));
            	talon5.set(controller.getRawAxis(1) + controller.getRawAxis(0));
            	talon6.set(controller.getRawAxis(1) + controller.getRawAxis(0));
        	}else if(controller.getRawAxis(0) < -0.1){
            	talon1.set(controller.getRawAxis(1) - controller.getRawAxis(0));
            	talon2.set(controller.getRawAxis(1) - controller.getRawAxis(0));
            	talon3.set(controller.getRawAxis(1) - controller.getRawAxis(0));
            	talon4.set(controller.getRawAxis(1) + controller.getRawAxis(0));
            	talon5.set(controller.getRawAxis(1) + controller.getRawAxis(0));
            	talon6.set(controller.getRawAxis(1) + controller.getRawAxis(0));
        	}else if(controller.getRawAxis(1) > 0.1 || controller.getRawAxis(1) < -0.1){
        		talon1.set(controller.getRawAxis(1));
        		talon2.set(controller.getRawAxis(1));
        		talon3.set(controller.getRawAxis(1));
        		talon4.set(controller.getRawAxis(1));
        		talon5.set(controller.getRawAxis(1));
        		talon6.set(controller.getRawAxis(1));
        	}else {
        		talon1.set(0);
        		talon2.set(0);
        		talon3.set(0);
        		talon4.set(0);
        		talon5.set(0);
        		talon6.set(0);
        	}
        }
    }
    
    public void autonomousInit(){
//    	Solenoid.setDefaultSolenoidModule(0);
//    	encoder.reset();
//    	gyro.reset();
//    	encoder.startLiveWindowMode();
//    	gyro.startLiveWindowMode();
    	//ai.startLiveWindowMode();
    }

	public void autonomous(){
//        	System.out.println("UltraSonic Value in Inches: " + ultraSonic.getRangeInches());
//        	System.out.println("Encoder Directional Value: " + encoder.getDirection());
//        	System.out.println("Gyro Angle Value: " + gyro.getAngle());
		SmartDashboard.putNumber("UltraSonic", ai.getValue());
		ai.startLiveWindowMode();
			
		
		//SmartDashboard.putBoolean("Encoder", encoder.getDirection());
		//SmartDashboard.putNumber("Gyro", gyro.getAngle());
		
    }
	
}
