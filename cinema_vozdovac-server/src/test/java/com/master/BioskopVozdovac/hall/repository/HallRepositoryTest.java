package com.master.BioskopVozdovac.hall.repository;

import com.master.BioskopVozdovac.hall.model.HallEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.master.BioskopVozdovac.input.HallData.HALL_ENTITY;
import static com.master.BioskopVozdovac.input.HallData.SEAT_ENTITY;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HallRepositoryTest {

    @Autowired
    private HallRepository repository;

    @BeforeEach
    void setUp() {
        SEAT_ENTITY.setHall(HALL_ENTITY);
    }

    @Test
    void testSave() {
        HallEntity hall = repository.save(HALL_ENTITY);

        HallEntity fetchedHall = repository.findById(hall.getHallID()).orElse(null);
        assertNotNull(fetchedHall);
        assertEquals(hall.getHallID(), fetchedHall.getHallID());
        assertEquals(hall.getHallName(), fetchedHall.getHallName());
        assertEquals(hall.getRowsCount(), fetchedHall.getRowsCount());
        assertEquals(hall.getSeatsPerRow(), fetchedHall.getSeatsPerRow());
    }

    @Test
    void testFindById() {
        HallEntity hall = repository.save(HALL_ENTITY);

        HallEntity fetchedHall = repository.findById(hall.getHallID()).orElse(null);
        assertNotNull(fetchedHall);
        assertEquals(hall.getHallID(), fetchedHall.getHallID());
        assertEquals(hall.getHallName(), fetchedHall.getHallName());
        assertEquals(hall.getRowsCount(), fetchedHall.getRowsCount());
        assertEquals(hall.getSeatsPerRow(), fetchedHall.getSeatsPerRow());
    }

    @Test
    void testDeleteById() {
        HallEntity hall = repository.save(HALL_ENTITY);

        repository.deleteById(hall.getHallID());
        HallEntity foundHall = repository.findById(hall.getHallID()).orElse(null);
        assertNull(foundHall);
    }

}
