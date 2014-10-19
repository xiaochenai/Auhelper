package com.example.gmap;
public class objectinfo {
	public String name;
	public String gender;
	public String email;
	public String phone;
	public String Lat;
	public String Lng;
	public objectinfo(String name, String gender, String email,String phone,  String Lat, String Lng){
		this.name=name;
		this.gender = gender;
		this.email=email;
		this.phone = phone;
		this.Lat=Lat;
		this.Lng=Lng;
	}
	public String getName(){
		return this.name;
	}
	public String getGenger(){
		return this.gender;
	}
	public String getPhone(){
		return this.phone;
	}
	public String getEmail(){
		return this.email;
	}
	public String getLat(){
		return this.Lat;
	}
	public String getLng(){
		return this.Lng;
	}
}
