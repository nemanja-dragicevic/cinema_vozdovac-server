package com.master.BioskopVozdovac.project.repository;

import com.master.BioskopVozdovac.project.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {



}
