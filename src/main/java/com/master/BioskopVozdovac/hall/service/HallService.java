package com.master.BioskopVozdovac.hall.service;

import com.master.BioskopVozdovac.hall.adapter.HallAdapter;
import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import com.master.BioskopVozdovac.seat.model.SeatEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {

    private final HallRepository hallRepository;

    private final HallAdapter hallAdapter;

    public HallDTO createHall(HallDTO dto, int numberOfRows, int seatsPerRow) {
        HallEntity entity = hallAdapter.dtoToEntity(dto);

        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                SeatEntity seat = new SeatEntity();
                seat.setRowNumber(i);
                seat.setSeatNumber(j);
                seat.setHall(entity);
                entity.getSeats().add(seat);
            }
        }

        return hallAdapter.entityToDTO(hallRepository.save(entity));
    }

    public String deleteWithId(Long id) {
        hallRepository.deleteById(id);
        return "Successfully deleted hall";
    }

    public List<HallDTO> getAll() {
        List<HallEntity> entities = hallRepository.findAll();
        return hallAdapter.toDTO(entities);
    }
}
