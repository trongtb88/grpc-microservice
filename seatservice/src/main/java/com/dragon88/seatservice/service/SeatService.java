package com.dragon88.seatservice.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.dragon88.gen.proto.Seat;
import com.dragon88.seatservice.AppProperties;
import com.dragon88.seatservice.dao.SeatEntity;
import com.dragon88.seatservice.dao.SeatIdentity;
import com.dragon88.seatservice.repository.SeatEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SeatService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppProperties myAppProperties;

	@Autowired
	private SeatEntityRepository seatRepository;

	
	
	public boolean validateSeatListBoundaries(List<Seat> seats) {
		for (final Seat seat: seats) {
			SeatIdentity seatIdentity = new SeatIdentity();
			seatIdentity.setIndexRow(seat.getRow());
			seatIdentity.setIndexColumn(seat.getColumn());
			SeatEntity seatEntity = new SeatEntity(seatIdentity, seat.getStatus());
			if (!validateSeatBoundary(seatEntity)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validateSeatBoundary(SeatEntity seat) {
		final boolean result = seat.getSeatIdentity().getIndexRow() < myAppProperties.getCinemaRow() && seat.getSeatIdentity().getIndexRow() >= 0
				&& seat.getSeatIdentity().getIndexColumn() < myAppProperties.getCinemaColumn() && seat.getSeatIdentity().getIndexColumn() >= 0;
				
		if (result)
			return true;
		
		logger.warn("seat " + seat.toString() + " boundaries are violated");
		return false;
	}
	

	public boolean reserve(List<Seat> seats) {
		if (seats.isEmpty()) {
			return true;
		}
		List<SeatEntity> availableSeats = seatRepository.findAllByStatusEqualsEmpty();
		if (availableSeats.isEmpty()) {
			return false;
		}
		
		
		List<SeatEntity> assignedSeats = new LinkedList<SeatEntity>();

		logger.info("reserve seat size: {}", seats.size());
		for (Seat seat: seats) {
			SeatIdentity seatIdentity = new SeatIdentity();
			seatIdentity.setIndexRow(seat.getRow());
			seatIdentity.setIndexColumn(seat.getColumn());
			SeatEntity seatEntity = new SeatEntity(seatIdentity, seat.getStatus());
			if (!checkSeatIsEmpty(seatEntity)) {
				return false;
			}

			if (!updateUnsafeSeatsAfterBook(seatEntity, availableSeats, assignedSeats)) {
				return false;
			}
		} 
		
		seatRepository.saveAll(assignedSeats);
		return true;
	}
	
	private boolean checkSeatIsEmpty(SeatEntity seatEntity) {
		SeatIdentity seatIdentity = new SeatIdentity();
		seatIdentity.setIndexRow(seatEntity.getSeatIdentity().getIndexRow());
		seatIdentity.setIndexColumn(seatEntity.getSeatIdentity().getIndexColumn());
		final Optional<SeatEntity> optionSeat = seatRepository.findById(seatIdentity);
		if (optionSeat.isPresent()) {
			final SeatEntity seat = optionSeat.get();
			logger.info("check seat empty" , seat.toString());
			if (seat !=null && seat.getStatus().equals("empty")) {
				return true;
			}
		}
		return false;
	}

	private int getManhattanDistance(SeatEntity seatEntity1, SeatEntity seatEntity2) {
		return Math.abs(seatEntity1.getSeatIdentity().getIndexRow() - seatEntity2.getSeatIdentity().getIndexRow())
				+ Math.abs(seatEntity1.getSeatIdentity().getIndexColumn() - seatEntity2.getSeatIdentity().getIndexColumn());
	}

	private boolean updateUnsafeSeatsAfterBook(SeatEntity seatEntity, List<SeatEntity> availableSeats, List<SeatEntity> unsafeSeats) {
		
		final int totalAvailableSeat = availableSeats.size();
		
		for (int i = totalAvailableSeat-1; i>=0; i--) {
			int manhattanDistance = getManhattanDistance(seatEntity, availableSeats.get(i));
			logger.info("manhattanDistance distance from {} {} to {} {} is {}", seatEntity.getSeatIdentity().getIndexRow(), seatEntity.getSeatIdentity().getIndexColumn(), availableSeats.get(i).getSeatIdentity().getIndexColumn(),
					availableSeats.get(i).getSeatIdentity().getIndexColumn(), myAppProperties.getDistanceManhattan());
			if (manhattanDistance <= myAppProperties.getDistanceManhattan()) {
				SeatEntity seat = availableSeats.get(i);
				if (seat.getSeatIdentity().getIndexRow() == seatEntity.getSeatIdentity().getIndexRow() && seat.getSeatIdentity().getIndexColumn() == seatEntity.getSeatIdentity().getIndexColumn()) {
					if (!"empty".equals(seat.getStatus())) {
						return false;
					} else {
						seat.setStatus("booked");
						unsafeSeats.add(seat);
					}

				} else {
					if ("empty".equals(seat.getStatus())) {
						seat.setStatus("unsafe");
						unsafeSeats.add(seat);
					}
				}
				availableSeats.remove(i);
			}
		}
		return true;
	}

	public boolean validateSeatNumber(int total) {
		List<SeatEntity> availableSeats = seatRepository.findAllByStatusEqualsEmpty();
		return total < availableSeats.size() && total >= 0;
	}

	public List<SeatEntity> getSeats(int total) {
		
		List<SeatEntity> availableSeats = seatRepository.findAllByStatusEqualsEmpty();
		List<SeatEntity> assignedSeats = new LinkedList<SeatEntity>();
		List<SeatEntity> unsafeSeats = new LinkedList<SeatEntity>();

		while (total-- > 0) {
			if (availableSeats.isEmpty()) {
				return Collections.emptyList();
			}
			final SeatEntity seat = availableSeats.remove(0);
			assignedSeats.add(seat);
			if (!updateUnsafeSeatsAfterBook(seat, availableSeats, unsafeSeats)) {
				return Collections.emptyList();
			}
		}
		return assignedSeats;
	}
}