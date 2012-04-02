--
--     Copyright (C) 2012 Blake Dickie
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
--// Create Episode Tables
-- Migration SQL that makes the change goes here.

Create Table AnimeEpisode (
	episode_id int NOT NULL PRIMARY KEY,
    anime_id int NOT NULL REFERENCES Anime ON DELETE CASCADE,
    episode_number varchar(20) NOT NULL,
	length int NULL,
	rating numeric(6,2) NULL,
	rating_votes int NULL,
	name_english varchar(200) NULL,
	name_romaji varchar(200) NULL,
	name_kanji varchar(200) NULL,
	air_date date NULL,
	normal_episode_number int NULL
);

CREATE INDEX idx_AnimeEpisode_by_anime ON AnimeEpisode (anime_id, normal_episode_number);

--//@UNDO
-- SQL to undo the change goes here.

Drop Index idx_AnimeEpisode_by_anime

Drop Table AnimeEpisode;

