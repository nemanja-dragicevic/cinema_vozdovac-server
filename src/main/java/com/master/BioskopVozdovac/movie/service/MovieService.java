package com.master.BioskopVozdovac.movie.service;

import com.amazonaws.util.IOUtils;
import com.master.BioskopVozdovac.aws.S3Service;
import com.master.BioskopVozdovac.movie.adapter.MovieAdapter;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieAdapter movieAdapter;

    private final S3Service s3Service;

    public MovieDTO saveMovie(MovieDTO dto, MultipartFile file) {
        MovieEntity entity = movieRepository.save(movieAdapter.dtoToEntity(dto));
        try {
            s3Service.uploadFile(dto.getName() + ".png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movieAdapter.entityToDTO(entity);
    }

    public MovieDTO getMovieById(Long id) {
        MovieEntity entity = movieRepository.findById(id).orElseThrow(()
                    -> new NoSuchElementException("No element with id: " + id));
        return movieAdapter.entityToDTO(entity);
    }

    public MovieDTO updateMovie(MovieDTO dto) {
        MovieEntity entity = movieAdapter.dtoToEntity(dto);
        return movieAdapter.entityToDTO(movieRepository.saveAndFlush(entity));
    }

    public String deleteMovieById(Long id) {
        movieRepository.deleteById(id);
        return "Successfully deleted movie with id: " + id;
    }

    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> entities = movieRepository.findAllShowing();
        return movieAdapter.toDto(entities);
    }

    public List<MovieDTO> getAllMoviesWithAWS() {
        List<MovieDTO> dtos = movieAdapter.toDto(movieRepository.findAllShowing());

        for (MovieDTO dto : dtos) {
            try {
                dto.setSmallPicture(getPicture(dto.getName()));
                dto.setBigPicture(getPicture(dto.getName() + " bigg"));
            } catch (Exception e) {
                dto.setSmallPicture(null);
            }
        }
        return dtos;
    }

    private String getPicture(String filename) {
        try {
            var s3Object = s3Service.getFile(filename + ".webp");
            var content = s3Object.getObjectContent();
            // Read the image content into a byte array
            byte[] bytes = IOUtils.toByteArray(content);

            // Encode the byte array to Base64
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return null;
        }
    }

}
