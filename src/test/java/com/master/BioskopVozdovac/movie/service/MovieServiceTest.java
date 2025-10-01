package com.master.BioskopVozdovac.movie.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.master.BioskopVozdovac.aws.S3Service;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import com.master.BioskopVozdovac.role.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private S3Service s3Service;

    @Mock
    private RoleService roleService;

    @Mock
    private RoleAdapter roleAdapter;

    @InjectMocks
    private MovieService movieService;

    private MovieDTO movieDTO;
    private MovieEntity movieEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        movieDTO = new MovieDTO();
        movieDTO.setMovieID(1L);
        movieDTO.setName("Test movie");
        movieDTO.setDescription("My description");
        movieDTO.setDuration(120);

        movieEntity = new MovieEntity();
        movieEntity.setMovieID(1L);
        movieEntity.setName("Test movie");
        movieEntity.setDescription("My description");
        movieEntity.setDuration(120);
    }

    @Test
    void testSaveMovie() throws IOException {
        MultipartFile small = mock(MultipartFile.class);
        MultipartFile big = mock(MultipartFile.class);
        when(roleAdapter.toEntities(anySet())).thenReturn(new HashSet<>());
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(movieEntity);

        MovieDTO result = movieService.saveMovie(movieDTO, small, big);

        assertNotNull(result);
        verify(movieRepository, times(1)).save(any(MovieEntity.class));
        verify(s3Service, times(2)).uploadFile(anyString(), any(MultipartFile.class));
    }

    @Test
    void testSaveMovieException() throws IOException {
        MultipartFile small = mock(MultipartFile.class);
        MultipartFile big = mock(MultipartFile.class);
        when(roleAdapter.toEntities(anySet())).thenReturn(new HashSet<>());
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(movieEntity);
        doThrow(IOException.class).when(s3Service).uploadFile(anyString(), any(MultipartFile.class));

        assertThrows(RuntimeException.class, () -> movieService.saveMovie(movieDTO, small, big));
    }

    @Test
    void testGetMovieById() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movieEntity));

        MovieDTO result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals(movieDTO.getName(), result.getName());
        verify(movieRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetMovieByIdNotFound() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> movieService.getMovieById(1L));
    }

    @Test
    void testUpdateMovie() throws IOException {
        MultipartFile smallFile = mock(MultipartFile.class);
        MultipartFile bigFile = mock(MultipartFile.class);
        when(roleAdapter.toEntities(anySet())).thenReturn(new HashSet<>());
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(movieEntity);

        MovieDTO result = movieService.updateMovie(movieDTO, smallFile, bigFile);

        assertNotNull(result);
        verify(movieRepository, times(1)).save(any(MovieEntity.class));
        verify(s3Service, times(1)).uploadFile(eq(movieDTO.getName() + ".webp"), any(MultipartFile.class));
        verify(s3Service, times(1)).uploadFile(eq(movieDTO.getName() + " big.webp"), any(MultipartFile.class));
    }

    @Test
    void testUpdateMovieException() throws IOException {
        MultipartFile smallFile = mock(MultipartFile.class);
        MultipartFile bigFile = mock(MultipartFile.class);
        when(roleAdapter.toEntities(anySet())).thenReturn(new HashSet<>());
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(movieEntity);
        doThrow(IOException.class).when(s3Service).uploadFile(anyString(), any(MultipartFile.class));

        assertThrows(RuntimeException.class, () -> movieService.updateMovie(movieDTO, smallFile, bigFile));
    }

    @Test
    void testDeleteMovieById() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movieEntity));

        String result = movieService.deleteMovieById(1L);

        assertEquals("Successfully deleted movie with id: 1", result);
        verify(movieRepository, times(1)).findById(anyLong());
        verify(movieRepository, times(1)).delete(any(MovieEntity.class));
        verify(s3Service, times(1)).deleteFileFromS3Bucket(eq(movieEntity.getName() + ".webp"));
        verify(s3Service, times(1)).deleteFileFromS3Bucket(eq(movieEntity.getName() + " big.webp"));
    }

    @Test
    void testDeleteMovieByIdNotFound() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> movieService.deleteMovieById(1L));
    }

    @Test
    void testGetAllMovies() {
        List<MovieEntity> movieEntities = Arrays.asList(movieEntity);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);
        when(movieRepository.findCurrentAndUpcomingMovies()).thenReturn(movieEntities);

        List<MovieDTO> result = movieService.getAllMovies();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(movieDTOs.size(), result.size());
        verify(movieRepository, times(1)).findCurrentAndUpcomingMovies();
    }

    @Test
    void testGetAllMoviesWithAWS() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieID(1L);
        movieEntity.setName("Test Movie");

        List<MovieEntity> entities = List.of(movieEntity);
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieID(1L);
        movieDTO.setName("Test Movie");

        when(movieRepository.findCurrentAndUpcomingMovies()).thenReturn(entities);
        when(roleService.getRolesForMovie(anyLong())).thenReturn(new HashSet<>());

        String pictureContent = Base64.getEncoder().encodeToString("fake-image-content".getBytes());

        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new ByteArrayInputStream("fake-image-content".getBytes()));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        s3Object.setObjectMetadata(objectMetadata);
        when(s3Service.getFile(anyString())).thenReturn(s3Object);

        List<MovieDTO> result = movieService.getAllMoviesWithAWS();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getName());
        assertNotNull(result.get(0).getSmallPicture());
        assertEquals(pictureContent, result.get(0).getSmallPicture());
    }

    @Test
    void testSaveStartAndEndDates() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieID(1L);
        movieDTO.setName("Test Movie");

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieID(1L);
        movieEntity.setName("Test Movie");

        when(movieRepository.save(any(MovieEntity.class))).thenReturn(movieEntity);

        movieService.saveStartAndEndDates(movieDTO);

        verify(movieRepository, times(1)).save(any(MovieEntity.class));
    }

    @Test
    void testGetMoviesWithoutProjections() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieID(1L);
        movieEntity.setName("Test Movie");

        List<MovieEntity> entities = List.of(movieEntity);
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieID(1L);
        movieDTO.setName("Test Movie");

        when(movieRepository.findAllWOProjections()).thenReturn(entities);

        List<MovieDTO> result = movieService.getMoviesWithoutProjections();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getName());
    }

}
