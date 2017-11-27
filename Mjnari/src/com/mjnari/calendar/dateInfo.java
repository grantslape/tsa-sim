package com.mjnari.calendar;


public class dateInfo {
	private static String id;
	public static String info;
	
	//create a dateInfo object with given values
	public dateInfo(String identification) {
		id = identification;
		System.out.println("made date with id " + id);
	}
	
	public dateInfo(String identification, String information) {
		info = information;
		System.out.println("id = " + id);
		System.out.println("saved " + info);
	}
	
	public String getInfo() {
		System.out.println("returned info");
		return info;
	}
}
