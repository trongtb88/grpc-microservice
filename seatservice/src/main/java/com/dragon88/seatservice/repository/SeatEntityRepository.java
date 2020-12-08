package com.dragon88.seatservice.repository;

import com.dragon88.seatservice.dao.SeatEntity;
import com.dragon88.seatservice.dao.SeatIdentity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatEntityRepository extends CrudRepository<SeatEntity, SeatIdentity> {

    @Query("SELECT s FROM SeatEntity s WHERE s.status = 'empty'")
    public List<SeatEntity> findAllByStatusEqualsEmpty();

    public List<SeatEntity> findAllByStatus(String status);

    //public List<SeatEntity> findAllStatusAndName(String status, String name);

}
