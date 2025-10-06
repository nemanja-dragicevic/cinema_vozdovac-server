package com.master.BioskopVozdovac.hall.service;

import com.master.BioskopVozdovac.hall.adapter.HallAdapter;
import com.master.BioskopVozdovac.hall.model.CreateHall;
import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import com.master.BioskopVozdovac.seat.model.SeatEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling operations related to halls.
 *
 * This service provides methods for creating, retrieving, updating, and deleting halls.
 *
 * @author Nemanja Dragićević
 */
@Service
@RequiredArgsConstructor
public class HallService {

    /**
     * Repository for accessing hall data
     */
    private final HallRepository hallRepository;

    /**
     * Creates a new hall with seats based on the provided HallDTO.
     *
     * @param dto The HallDTO containing hall details including number of rows and seats per row.
     * @return The created HallDTO after saving to the database.
     */
    public HallDTO createHall(CreateHall dto) {
        int numberOfRows = dto.rowsCount();
        int seatsPerRow = dto.seatsPerRow();

        HallEntity entity = HallEntity.builder()
                .hallName(dto.hallName())
                .rowsCount(numberOfRows)
                .seatsPerRow(seatsPerRow)
                .build();

        List<SeatEntity> seats = new ArrayList<>();
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                SeatEntity seat = new SeatEntity();
                seat.setRowNumber(i);
                seat.setSeatNumber(j);
                seat.setHall(entity);
                seats.add(seat);
            }
        }
        entity.setSeats(seats);

        return HallAdapter.entityToDTO(hallRepository.save(entity));
    }

    /**
     * Deletes a hall by the specified ID.
     *
     * @param id The ID of the hall to delete.
     * @return A success message indicating the deletion of the hall.
     */
    public String deleteWithId(Long id) {
        //TODO: When there are movies currently projecting in this hall, or if it is used earlier, no delete allowed
        hallRepository.deleteById(id);
        return "Successfully deleted hall";
    }

    /**
     * Retrieves all halls from the database.
     *
     * @return A list of HallDTOs representing all halls found in the database.
     */
    public List<HallDTO> getAll() {
        List<HallEntity> entities = hallRepository.findAll();
        return HallAdapter.toDTO(entities);
    }

    /**
     * Updates an existing hall based on the provided HallDTO.
     *
     * @param dto The HallDTO containing updated hall details including number of rows and seats per row.
     * @return The updated HallDTO after saving the updated entity to the database.
     */
    public HallDTO updateHall(HallDTO dto) {
        HallEntity entity = HallAdapter.dtoToEntity(dto);
        entity.setSeats(new ArrayList<>());

        for (int i = 1; i <= dto.rowsCount(); i++) {
            for (int j = 1; j <= dto.seatsPerRow(); j++) {
                SeatEntity seat = new SeatEntity();
                seat.setRowNumber(i);
                seat.setSeatNumber(j);
                seat.setHall(entity);
                entity.getSeats().add(seat);
            }
        }

        //TODO: When there are movies currently projecting in this hall, no update allowed
        return HallAdapter.entityToDTO(hallRepository.saveAndFlush(entity));
    }
}
