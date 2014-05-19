--
--     Copyright (C) 2012-2014 Blake Dickie
--
--     This program is free software: you can redistribute it and/or modify
--     it under the terms of the GNU General Public License as published by
--     the Free Software Foundation, either version 3 of the License, or
--     (at your option) any later version.
--
--     This program is distributed in the hope that it will be useful,
--     but WITHOUT ANY WARRANTY; without even the implied warranty of
--     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--     GNU General Public License for more details.
--
--     You should have received a copy of the GNU General Public License
--     along with this program.  If not, see <http://www.gnu.org/licenses/>.
--
--// TMDb Initial Tables

CREATE TABLE TMDbGenre (id INTEGER NOT NULL, name VARCHAR(255) NOT NULL, PRIMARY KEY (id));

CREATE TABLE TMDbMovie
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    adult         BOOLEAN,
    homepage      VARCHAR(255),
    imdbid        VARCHAR(255),
    originaltitle VARCHAR(255) NOT NULL,
    overview      TEXT,
    popularity    FLOAT4,
    runtime       INTEGER,
    tagline       VARCHAR(255),
    title         VARCHAR(255) NOT NULL,
    tmdbid        INTEGER UNIQUE,
    voteaverage   FLOAT4,
    votecount     INTEGER
);

CREATE TABLE TMDbMovieGenre (
    movie_id BIGINT REFERENCES TMDbMovie ON DELETE CASCADE, 
    genre_id INTEGER REFERENCES TMDbGenre
);

CREATE INDEX I_TMDBGNR_ELEMENT ON TMDbMovieGenre (genre_id);

CREATE INDEX I_TMDBGNR_MOVIE_ID ON TMDbMovieGenre (movie_id);

--//@UNDO

Drop Table TMDbMovieGenre;

Drop Table TMDbMovie;

Drop Table TMDbGenre;