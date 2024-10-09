package com.appointment_system.entity.enums;

public enum Time {

	Morning_8_to_12("8:00 AM - 12:00 PM"),
	Afternoon_10_to_2("10:00 AM - 2:00 PM"),
	Afternoon_12_to_4("12:00 PM - 4:00 PM");
	
	private final String description; 
	
	Time(String description)
	{
		this.description=description;
	}
	
	public String getDescription()
	{
		return description;
	}
}
