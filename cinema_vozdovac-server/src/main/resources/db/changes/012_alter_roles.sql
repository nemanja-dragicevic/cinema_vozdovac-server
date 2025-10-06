ALTER TABLE roles
ADD CONSTRAINT fk_roles_movie FOREIGN KEY (movie_id)
REFERENCES movie(id);

ALTER TABLE roles
ADD CONSTRAINT fk_roles_actor FOREIGN KEY (actor_id)
REFERENCES actor(id);