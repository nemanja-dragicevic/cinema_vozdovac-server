package com.master.BioskopVozdovac.project.repository;

import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.project.model.ProjectTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query(value =
            "select distinct to_char(p.project, 'HH24:MI') as starting, " +
            "to_char(p.project_end, 'HH24:MI') as ending " +
            "from \"public\".projects p " +
            "where p.hall_id = ?1 " +
            "and p.project >= ?2 " +
            "and p.project <= ?3 " +
            "order by 1 ", nativeQuery = true)
    List<Object[]> getAvailableTimes(final Long hallID, final LocalDateTime startTime, final LocalDateTime endTime, final long duration);

    List<ProjectEntity> findAllByMovieMovieID(Long id);

    @Query(value = "select * " +
            "from \"public\".projects p " +
            "where cast(p.project as date) = ?1", nativeQuery = true)
    List<ProjectEntity> findAllByDate(final Date date);

    @Query(value = "select distinct to_char(p.project, 'HH24:MI') as start_time, " +
            "to_char(p.project_end, 'HH24:MI') as end_time " +
            "from \"public\".projects p " +
            "where p.hall_id = ?2 " +
            "and cast(p.project as date) = ?1 " +
            "and cast(p.project as date) = ?1 " +
            "order by 1", nativeQuery = true)
    List<Object[]> findAllByDateAndHall(final Date date, final Long hallID);

//    @Query(value = "with filtered as (select distinct to_char(p.project, 'HH24:MI') as hour " +
//            "from \"public\".projects p " +
//            "where p.hall_id = ?1 " +
//            "and p.project >= ?2 " +
//            "and p.project <= ?3 " +
//            "and extract(HOUR from p.project) >= 12 " +
//            "and extract(HOUR from p.project) <= 23) " +
//            "select hour from filtered ", nativeQuery = true)
//    List<Long> getAvailableTimesLong(final Long hallID, final LocalDateTime startTime, final LocalDateTime endTime, final long duration);

}
