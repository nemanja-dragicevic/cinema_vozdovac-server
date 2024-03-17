package com.master.BioskopVozdovac.project.service;

import com.master.BioskopVozdovac.project.adapter.ProjectAdapter;
import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository broadcastRepository;

    private final ProjectAdapter broadcastAdapter;

    public List<ProjectDTO> getAllDTOs() {
        return null;
    }
}
