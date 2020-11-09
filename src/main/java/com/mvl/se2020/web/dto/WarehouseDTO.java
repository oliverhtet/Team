package com.mvl.se2020.web.dto;

import com.mvl.se2020.web.enumerations.Location;

public class WarehouseDTO {

	private Long id;
	private String wareName;
	private Location location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "WarehouseDTO [id=" + id + ", wareName=" + wareName + ", location=" + location + "]";
	}

}
