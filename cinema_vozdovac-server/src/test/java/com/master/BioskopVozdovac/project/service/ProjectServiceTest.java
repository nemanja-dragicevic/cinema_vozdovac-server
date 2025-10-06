package com.master.BioskopVozdovac.project.service;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.service.MovieService;
import com.master.BioskopVozdovac.project.adapter.ProjectAdapter;
import com.master.BioskopVozdovac.project.model.ProjectDTO;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.model.ProjectTimes;
import com.master.BioskopVozdovac.project.model.ProjectionsSave;
import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.master.BioskopVozdovac.input.HallData.HALL_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectAdapter projectAdapter;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMovieProjections() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setStartTime(LocalDate.now());
        movieDTO.setEndTime(LocalDate.now().plusDays(2));
        movieDTO.setDuration(120);
        ProjectionsSave dto = new ProjectionsSave(0L,
                movieDTO,
                HALL_DTO,
                List.of(LocalTime.of(10, 0), LocalTime.of(14, 0)),
                10L);

        when(projectAdapter.dtoToEntity(any(ProjectDTO.class))).thenReturn(new ProjectEntity());

        String result = projectService.saveMovieProjections(dto);

        assertEquals("Successfully saved movie projection(s)!!!", result);
        verify(projectRepository, times(6)).save(any(ProjectEntity.class));
        verify(movieService, times(1)).saveStartAndEndDates(any(MovieDTO.class));
    }

    @Test
    void testGetAvailableTimes() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieID(1L);
        movieDTO.setStartTime(LocalDate.now());
        movieDTO.setEndTime(LocalDate.now().plusDays(2));
        movieDTO.setDescription("asd");
        movieDTO.setName("asd");
        movieDTO.setRoleDTO(new HashSet<>());
        movieDTO.setDuration(100);

        List<Object[]> availableTimes = new ArrayList<>();

        when(projectRepository.getAvailableTimes(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(availableTimes);

        HallDTO hallDTO = HallDTO.builder()
                .hallID(1L)
                .hallName("Sala Uno")
                .build();

        ProjectionsSave dto = new ProjectionsSave(1L, movieDTO, hallDTO, new ArrayList<>(), 1000L);

        availableTimes.add(new Object[]{LocalTime.of(10, 0), LocalTime.of(12, 0)});
        availableTimes.add(new Object[]{LocalTime.of(14, 0), LocalTime.of(16, 0)});

        List<ProjectTimes> result = projectService.getAvailableTimes(dto);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(LocalTime.of(10, 0), result.get(0).start());
        assertEquals(LocalTime.of(12, 0), result.get(0).end());
        assertEquals(LocalTime.of(14, 0), result.get(1).start());
        assertEquals(LocalTime.of(16, 0), result.get(1).end());
    }

    @Test
    void testDeleteProjectionWithID() {
        Long projectionId = 1L;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectID(projectionId);

        when(projectRepository.findById(projectionId)).thenReturn(Optional.of(projectEntity));

        String result = projectService.deleteProjectionWithID(projectionId);

        assertEquals("Successfully deleted projection", result);
        verify(projectRepository, times(1)).deleteById(projectionId);
    }

    @Test
    void testUpdateProjection() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectID(1L);

        when(projectRepository.existsById(1L)).thenReturn(true);
        when(projectAdapter.dtoToEntity(any(ProjectDTO.class))).thenReturn(projectEntity);
        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(projectEntity);
        when(projectAdapter.entityToDTO(any(ProjectEntity.class))).thenReturn(projectDTO);

        ProjectDTO result = projectService.updateProjection(projectDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetProjectionsForDateAndMovie() {
        Long movieId = 1L;

        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        List<ProjectEntity> projectEntities = List.of(new ProjectEntity());

        when(projectRepository.findAllByDateAndMovie(any(Date.class), anyLong())).thenReturn(projectEntities);
        when(projectAdapter.entityToDTO(any(ProjectEntity.class))).thenReturn(new ProjectDTO());

        List<ProjectDTO> result = projectService.getProjectionsForDateAndMovie(date, movieId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetProjectionsForHallAndDates() {
        Long hallID = 1L;

        long currentTimeMillis = System.currentTimeMillis();
        Date startDate = new Date(currentTimeMillis);
        Date endDate = new Date(currentTimeMillis);
        List<Object[]> projections = new ArrayList<>();
        projections.add(new Object[]{"10:00", "12:00"});

        when(projectRepository.findAllByHallAndDates(anyLong(), any(Date.class), any(Date.class))).thenReturn(projections);

        List<ProjectTimes> result = projectService.getProjectionsForHallAndDates(hallID, startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("10:00", result.get(0).start().toString());
        assertEquals("12:00", result.get(0).end().toString());
    }

    @Test
    void testGetProjectionsForCertainMovie() {
        Long movieId = 1L;
        List<ProjectEntity> projections = List.of(new ProjectEntity());

        when(projectRepository.findByMovieMovieID(anyLong(), any(Date.class), any(Date.class))).thenReturn(projections);
        when(projectAdapter.entityToDTO(any(ProjectEntity.class))).thenReturn(new ProjectDTO());

        List<ProjectDTO> result = projectService.getProjectionsForCertainMovie(movieId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testTodaysProjections() {
        List<ProjectEntity> entities = List.of(new ProjectEntity());

        when(projectRepository.findTodaysProjections()).thenReturn(entities);
        when(projectAdapter.entityToDTO(any(ProjectEntity.class))).thenReturn(new ProjectDTO());

        List<ProjectDTO> result = projectService.todaysProjections();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

}
