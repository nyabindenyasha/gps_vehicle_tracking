package com.minimum.local;

public class VehicleResponse {
	
    private Integer id;
    
    private int vehicleId;
    
    private double currentLocationLatitude;
    
    private double currentLocationLongitude;
    
    private String time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public double getCurrentLocationLatitude() {
		return currentLocationLatitude;
	}

	public void setCurrentLocationLatitude(double currentLocationLatitude) {
		this.currentLocationLatitude = currentLocationLatitude;
	}

	public double getCurrentLocationLongitude() {
		return currentLocationLongitude;
	}

	public void setCurrentLocationLongitude(double currentLocationLongitude) {
		this.currentLocationLongitude = currentLocationLongitude;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
