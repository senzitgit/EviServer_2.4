package com.senzit.evidencer.server.model;

public class Court {
	
	private int courtId;
	private String courtName;
	private String courtDetails;
	private Location location;
	
	public Court(){}

	public int getCourtId() {
		return courtId;
	}

	public void setCourtId(int courtId) {
		this.courtId = courtId;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getCourtDetails() {
		return courtDetails;
	}

	public void setCourtDetails(String courtDetails) {
		this.courtDetails = courtDetails;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}	

}
