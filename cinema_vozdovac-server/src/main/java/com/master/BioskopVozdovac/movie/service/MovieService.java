package com.master.BioskopVozdovac.movie.service;

import com.amazonaws.util.IOUtils;
import com.master.BioskopVozdovac.aws.S3Service;
import com.master.BioskopVozdovac.movie.adapter.MovieAdapter;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import com.master.BioskopVozdovac.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Service class that handles movie-related business logic and interactions with repositories and external services.
 *
 * @author Nemanja Dragićević
 */
@Service
@RequiredArgsConstructor
public class MovieService {

    private static final String SMALL_IMAGE_FILE = ".webp";
    private static final String BIG_IMAGE_FILE = " big.webp";

    /**
     * Repository for movie entities
     */
    private final MovieRepository movieRepository;

    private final MovieAdapter movieAdapter;

    /**
     * Service for interacting with AWS S3 bucket
     */
    private final S3Service s3Service;

    /**
     * Service for handling movie roles
     */
    private final RoleService roleService;

    /**
     * Adapter for converting between RoleDTO and RoleEntity
     */
    private final RoleAdapter roleAdapter;

    /**
     * Saves a new movie along with its poster image to the database and S3 storage.
     *
     * @param dto  The MovieDTO containing movie details.
     * @param small The MultipartFile representing the movie thumbnail image.
     * @param big The MultipartFile representing the movie poster image.
     * @return The saved MovieDTO.
     * @throws RuntimeException if there is an error during file upload to S3.
     */
    public MovieDTO saveMovie(MovieDTO dto, MultipartFile small, MultipartFile big) {
        Set<RoleEntity> roleEntities = roleAdapter.toEntities(dto.getRoleDTO());
        MovieEntity entity = movieAdapter.dtoToEntity(dto);

        for (RoleEntity r : roleEntities)
            r.setMovie(entity);
        entity.setRoles(roleEntities);

        entity = movieRepository.save(entity);

        try {
            s3Service.uploadFile(dto.getName() + SMALL_IMAGE_FILE, small);
            s3Service.uploadFile(dto.getName() + BIG_IMAGE_FILE, big);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movieAdapter.entityToDTO(entity);
    }

    /**
     * Retrieves a movie by its ID from the database.
     *
     * @param id The ID of the movie to retrieve.
     * @return The MovieDTO representing the retrieved movie.
     * @throws NoSuchElementException if no movie exists with the given ID.
     */
    public MovieDTO getMovieById(Long id) {
        MovieEntity entity = movieRepository.findById(id).orElseThrow(()
                    -> new NoSuchElementException("No element with id: " + id));
        return movieAdapter.entityToDTO(entity);
    }

    /**
     * Updates an existing movie with new details and optionally updates poster images on S3.
     *
     * @param dto   The MovieDTO containing updated movie details.
     * @param small The MultipartFile representing the updated small picture (thumbnail).
     * @param big   The MultipartFile representing the updated big picture (poster).
     * @return The updated MovieDTO.
     * @throws RuntimeException if there is an error during file upload to S3.
     */
    public MovieDTO updateMovie(MovieDTO dto, MultipartFile small, MultipartFile big) {
        try {
            Set<RoleEntity> roleEntities = roleAdapter.toEntities(dto.getRoleDTO());
            MovieEntity entity = movieAdapter.dtoToEntity(dto);

            for (RoleEntity r : roleEntities)
                r.setMovie(entity);
            entity.setRoles(roleEntities);

            entity = movieRepository.save(entity);

            if (small != null)
                s3Service.uploadFile(dto.getName() + SMALL_IMAGE_FILE, small);
            if (big != null)
                s3Service.uploadFile(dto.getName() + BIG_IMAGE_FILE, big);

            return movieAdapter.entityToDTO(entity);
        } catch (IOException e) {
            throw new RuntimeException("There was an error while trying to update the movie");
        }
    }

    /**
     * Deletes a movie by its ID from the database and removes associated poster images from S3.
     *
     * @param id The ID of the movie to delete.
     * @return A success message indicating the deletion.
     * @throws NoSuchElementException if no movie exists with the given ID.
     */
    public String deleteMovieById(Long id) {
        MovieEntity entity = movieRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("There is no movie with id: " + id)
        );
        movieRepository.delete(entity);
        s3Service.deleteFileFromS3Bucket(entity.getName() + SMALL_IMAGE_FILE);
        s3Service.deleteFileFromS3Bucket(entity.getName() + BIG_IMAGE_FILE);
        return "Successfully deleted movie with id: " + id;
    }

    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> entities = movieRepository.findCurrentAndUpcomingMovies();
        return movieAdapter.toDto(entities);
    }

    /**
     * Retrieves all movies with AWS details, including roles and poster images.
     *
     * @return A list of MovieDTOs with AWS details.
     */
    public List<MovieDTO> getAllMoviesWithAWS() {
        List<MovieEntity> entities = movieRepository.findCurrentAndUpcomingMovies();
        List<MovieDTO> dtos = new ArrayList<>();

        for (MovieEntity entity : entities) {
            MovieDTO dto = movieAdapter.entityToDTO(entity);
            try {
                dto.setRoleDTO(roleService.getRolesForMovie(dto.getMovieID()));
                dto.setSmallPicture(getPicture(dto.getName()));
                dto.setBigPicture(getPicture(dto.getName() + " big"));
                dtos.add(dto);
            } catch (Exception e) {
                dto.setSmallPicture(null);
            }
        }
        return dtos;
    }

    private String getPicture(String filename) {
        try {
            var s3Object = s3Service.getFile(filename + SMALL_IMAGE_FILE);
            var content = s3Object.getObjectContent();
            // Read the image content into a byte array
            byte[] bytes = IOUtils.toByteArray(content);

            // Encode the byte array to Base64
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveStartAndEndDates(final MovieDTO dto) {
        movieRepository.save(movieAdapter.dtoToEntity(dto));
    }

    public List<MovieDTO> getMoviesWithoutProjections() {
        List<MovieEntity> movies = movieRepository.findAllWOProjections();
        return movies.stream().map(movieAdapter::entityToDTO).toList();
    }

}
