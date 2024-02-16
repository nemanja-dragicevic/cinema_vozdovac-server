package com.master.BioskopVozdovac.broadcast.service;

import com.master.BioskopVozdovac.broadcast.adapter.BroadcastAdapter;
import com.master.BioskopVozdovac.broadcast.model.BroadcastDTO;
import com.master.BioskopVozdovac.broadcast.repository.BroadcastRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BroadcastService {

    private final BroadcastRepository broadcastRepository;

    private final BroadcastAdapter broadcastAdapter;

    public List<BroadcastDTO> getAllDTOs() {
        return null;
    }
}
