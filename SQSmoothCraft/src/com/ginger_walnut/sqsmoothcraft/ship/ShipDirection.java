package com.ginger_walnut.sqsmoothcraft.ship;

public class ShipDirection {

	double yaw = 0;
	
	double yawCos = 0;
	double yawSin = 0;
	
	double adjustedYawCos = 0;
	double adjustedYawSin = 0;
	
	double pitch = 0;
	
	double pitchCos = 0;
	double pitchSin = 0;
	
	double adjustedPitchCos = 0;
	double adjustedPitchSin = 0;
	
	public ShipDirection(double firstYaw, double firstPitch) {
		
		yaw = firstYaw;
		
		double radYaw = Math.toRadians(yaw);
		
		yawCos = Math.cos(radYaw);
		yawSin = Math.sin(radYaw);
		
		adjustedYawSin = Math.sin(radYaw + 1.570796);
		adjustedYawCos = Math.cos(radYaw + 1.570796);
		
		pitch = firstPitch;
		
		double radPitch = Math.toRadians(pitch);
		
		pitchCos = Math.cos(radPitch);
		pitchSin = Math.sin(radPitch);
		
		adjustedPitchSin = Math.sin(radPitch + 1.570796);
		adjustedPitchCos = Math.cos(radPitch + 1.570796);
		
	}
	
	public void setYaw(double newYaw) {
		
		if (((float) yaw) != ((float) newYaw)) {
			
			yaw = newYaw;
			
			double radYaw = Math.toRadians(yaw);
			
			yawCos = Math.cos(radYaw);
			yawSin = Math.sin(radYaw);
			
			adjustedYawSin = Math.sin(radYaw + 1.570796);
			adjustedYawCos = Math.cos(radYaw + 1.570796);
			
		}

	}
	
	public void setPitch(double newPitch) {
		
		if (((float) pitch) != ((float) newPitch)) {
		
			pitch = newPitch;
			
			double radPitch = Math.toRadians(pitch);
			
			pitchCos = Math.cos(radPitch);
			pitchSin = Math.sin(radPitch);
			
			adjustedPitchSin = Math.sin(radPitch + 1.570796);
			adjustedPitchCos = Math.cos(radPitch + 1.570796);
			
		}
			
	}
	
}
