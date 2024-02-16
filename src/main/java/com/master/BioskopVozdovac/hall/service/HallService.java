package com.master.BioskopVozdovac.hall.service;

import com.master.BioskopVozdovac.hall.adapter.HallAdapter;
import com.master.BioskopVozdovac.hall.repository.HallRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HallService {

    private final HallRepository hallRepository;

    private final HallAdapter hallAdapter;

}
