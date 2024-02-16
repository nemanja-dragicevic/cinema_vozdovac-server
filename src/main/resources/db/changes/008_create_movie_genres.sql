create table movie_genres (
    movie_id         BIGINT,
    genre_id         BIGINT,

    primary key (movie_id, genre_id),
    foreign key(movie_id) references movie(id) ON DELETE CASCADE,
    foreign key (genre_id) references genre(genre_id) ON DELETE CASCADE
)