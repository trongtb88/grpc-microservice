package com.dragon88.seatservice;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppProperties {
 
	private int cinemaRow;
	private int cinemaColumn;
	private int distanceManhattan;
	
  public int getCinemaRow() {
		return cinemaRow;
	}
	public void setCinemaRow(int cinemaRow) {
		this.cinemaRow = cinemaRow;
	}
	public int getCinemaColumn() {
		return cinemaColumn;
	}
	
	public void setCinemaColumn(int cinemaColumn) {
		this.cinemaColumn = cinemaColumn;
	}
	public int getDistanceManhattan() {
		return distanceManhattan;
	}
	public void setDistanceManhattan(int distanceManhattan) {
		this.distanceManhattan = distanceManhattan;
	}
}