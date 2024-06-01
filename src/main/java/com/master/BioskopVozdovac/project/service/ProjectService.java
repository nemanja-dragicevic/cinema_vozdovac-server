package com.master.BioskopVozdovac.project.service;

import com.master.BioskopVozdovac.project.adapter.ProjectAdapter;
import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.model.ProjectTimes;
import com.master.BioskopVozdovac.project.model.ProjectionsSave;
import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectAdapter projectAdapter;

    public List<ProjectDTO> getAllDTOs() {
        return null;
    }

    public ProjectDTO saveMovieProjections(ProjectionsSave dto) {
        LocalDate startDate = dto.getMovie().getStartTime();
        LocalDate endDate = dto.getMovie().getEndTime();

        while (!startDate.isAfter(endDate)) {
            ProjectDTO project = new ProjectDTO();
            project.setMovie(dto.getMovie());
            project.setHall(dto.getHall());
            project.setPrice(dto.getPrice());

            for (LocalTime lt : dto.getProjectionTime()) {
//                project.setProjectTime(LocalDateTime.of(startDate, LocalTime.parse(s)));

                project.setProjectTime(LocalDateTime.of(startDate, lt));
                projectRepository.save(projectAdapter.dtoToEntity(project));
            }
            startDate = startDate.plusDays(1);
        }

        return null;
    }

//    public List<String> getAvailableTimes(ProjectionsSave dto) {
////        long days = ChronoUnit.DAYS.between(dto.getMovie().getStartTime(), dto.getMovie().getEndTime()) + 1;
//        List<LocalTime> valid = new ArrayList<>();
//
//        int duration = (int) Math.ceil((double) dto.getMovie().getDuration() / 60);
////        List<Object[]> val = projectRepository.getAvailableTimes(
////                dto.getHall().getHallID(),
////                dto.getMovie().getStartTime().atTime(12, 0, 0),
////                dto.getMovie().getEndTime().atTime(23, 0, 0),
////                0);
//        List<ProjectTimes> values = projectRepository.getAvailableTimes(
//                dto.getHall().getHallID(),
//                dto.getMovie().getStartTime().atTime(12, 0, 0),
//                dto.getMovie().getEndTime().atTime(23, 0, 0),
//                0).stream()
//                .map(result -> new ProjectTimes(
//                        LocalTime.parse(result[0].toString()),
//                        LocalTime.parse(result[1].toString())
//                )).toList();
//
//        LocalTime startTime = LocalTime.of(12, 0);
//        LocalTime endTime = LocalTime.of(23 - duration, 0);
//
//        while (startTime.isBefore(endTime)) {
//            if (isTimeValid(values, startTime, startTime.plusHours(duration))) valid.add(startTime);
//
//            startTime = startTime.plusHours(1);
//        }
//
//        return null;
//    }

    public List<ProjectTimes> getAvailableTimes(ProjectionsSave dto) {
        return projectRepository.getAvailableTimes(
                dto.getHall().getHallID(),
                dto.getMovie().getStartTime().atTime(12, 0, 0),
                dto.getMovie().getEndTime().atTime(23, 0, 0),
                0).stream()
                .map(result -> new ProjectTimes(
                        LocalTime.parse(result[0].toString()),
                        LocalTime.parse(result[1].toString())
                )).toList();
    }

    private boolean isTimeValid(List<ProjectTimes> times, LocalTime value, LocalTime endTime) {
        if (times.size() == 0)
            return true;

        for (ProjectTimes time : times) {
            if (value.isBefore(time.getStart()) && endTime.isBefore(time.getStart()))
                return true;
        }

        return false;
    }

    public List<ProjectDTO> getProjectionsForMovie(final Long id) {
        List<ProjectEntity> entities = projectRepository.findAllByMovieMovieID(id);

        return projectAdapter.toDTOs(entities);
    }
}
