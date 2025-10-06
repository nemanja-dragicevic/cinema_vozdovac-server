package com.master.BioskopVozdovac.project.service;

import com.master.BioskopVozdovac.movie.service.MovieService;
import com.master.BioskopVozdovac.project.adapter.ProjectAdapter;
import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.model.ProjectTimes;
import com.master.BioskopVozdovac.project.model.ProjectionsSave;
import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectAdapter projectAdapter;
    private final MovieService movieService;

    public List<ProjectDTO> getAllDTOs() {
        return null;
    }

    public String saveMovieProjections(ProjectionsSave dto) {
        LocalDate startDate = dto.movie().getStartTime();
        LocalDate endDate = dto.movie().getEndTime();

        dto.movie().setStartTime(startDate);
        dto.movie().setEndTime(endDate);

        while (!startDate.isAfter(endDate)) {
            ProjectDTO project = new ProjectDTO();
            project.setMovie(dto.movie());
            project.setHall(dto.hall());
            project.setPrice(dto.price());

            for (LocalTime lt : dto.projectionTime()) {
                project.setProjectTime(LocalDateTime.of(startDate, lt));
                project.setProjectEnd(LocalDateTime.of(startDate, lt.plusMinutes(dto.movie().getDuration())));
                projectRepository.save(projectAdapter.dtoToEntity(project));
            }
            startDate = startDate.plusDays(1);
        }
        movieService.saveStartAndEndDates(dto.movie());

        return "Successfully saved movie projection(s)!!!";
    }

    public List<ProjectTimes> getAvailableTimes(ProjectionsSave dto) {
        return projectRepository.getAvailableTimes(
                dto.hall().hallID(),
                dto.movie().getStartTime().atTime(12, 0, 0),
                dto.movie().getEndTime().atTime(23, 0, 0)).stream()
                .map(result -> new ProjectTimes(
                        LocalTime.parse(result[0].toString()),
                        LocalTime.parse(result[1].toString())
                )).toList();
    }

    private boolean isTimeValid(List<ProjectTimes> times, LocalTime value, LocalTime endTime) {
        if (times.isEmpty())
            return true;

        for (ProjectTimes time : times) {
            if (value.isBefore(time.start()) && endTime.isBefore(time.start()))
                return true;
        }

        return false;
    }

    public List<ProjectDTO> getProjectionsForMovie(final Date date) {
        List<ProjectEntity> entities = projectRepository.findAllByDate(date);

        return projectAdapter.toDTOs(entities);
    }

    public List<ProjectTimes> getProjectionsForDateAndHall(final Date date, final Long hallID) {
        List<Object[]> results = projectRepository.findAllByDateAndHall(date, hallID);

        return results.stream().map(result -> new ProjectTimes(
                LocalTime.parse(result[0].toString()),
                LocalTime.parse(result[1].toString()))).toList();
    }

    public String deleteProjectionWithID(Long id) {
        projectRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("There is no such projection"));

        projectRepository.deleteById(id);
        return "Successfully deleted projection";
    }

    public ProjectDTO updateProjection(ProjectDTO dto) {

        if (!projectRepository.existsById(dto.getId())) {
            throw new IllegalStateException("Projection with given id doesn't exist!");
        }

        return projectAdapter.entityToDTO(projectRepository.save(projectAdapter.dtoToEntity(dto)));
    }

    public List<ProjectDTO> getProjectionsForDateAndMovie(Date date, Long id) {
        List<ProjectEntity> projections = projectRepository.findAllByDateAndMovie(date, id);
        return projections.stream().map(projectAdapter::entityToDTO).collect(Collectors.toList());
    }

    public List<ProjectTimes> getProjectionsForHallAndDates(Long hallID, Date startDate, Date endDate) {
        List<Object[]> results = projectRepository.findAllByHallAndDates(hallID, startDate, endDate);

        return results.stream().map(result -> new ProjectTimes(
                LocalTime.parse(result[0].toString()),
                LocalTime.parse(result[1].toString()))).toList();
    }

    public List<ProjectDTO> getProjectionsForCertainMovie(Long id) {
        LocalDate currentDate = LocalDate.now();
        Date startDate = Date.valueOf(currentDate);
        Date endDate = Date.valueOf(currentDate.plusDays(2));
        List<ProjectEntity> projections = projectRepository.findByMovieMovieID(id, startDate, endDate);
        return projections.stream().map(projectAdapter::entityToDTO).collect(Collectors.toList());
    }

    public List<ProjectDTO> todaysProjections() {
        List<ProjectEntity> entities = projectRepository.findTodaysProjections();
        return entities.stream().map(projectAdapter::entityToDTO).collect(Collectors.toList());
    }
}
