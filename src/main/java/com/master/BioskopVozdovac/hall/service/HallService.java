package com.master.BioskopVozdovac.hall.service;

import com.master.BioskopVozdovac.hall.adapter.HallAdapter;
import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HallService {

    private final HallRepository hallRepository;

    private final HallAdapter hallAdapter;

    public HallDTO createHall(HallDTO dto) {
        return hallAdapter.entityToDTO(hallRepository.save(hallAdapter.dtoToEntity(dto)));
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
