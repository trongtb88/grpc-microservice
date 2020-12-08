package com.dragon88.seatservice.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Builder
@Getter
@Setter
public class SeatEntity  {

	@EmbeddedId
	private SeatIdentity seatIdentity;
	private String status;

	public SeatEntity() {}

	public SeatEntity(int row, int column, String status) {
		super();
		this.seatIdentity.setIndexColumn(row);
		this.seatIdentity.setIndexRow(column);
		this.setStatus(status);
	}

	public SeatEntity(SeatIdentity seatIdentity, String status) {
		super();
		this.setSeatIdentity(seatIdentity);
		this.setStatus(status);
	}

	@Override
	public String toString() {
		return "SeatEntity [row=" + seatIdentity.getIndexRow() + ", column=" + seatIdentity.getIndexColumn() + ", status=" + status + "]";
	}

}