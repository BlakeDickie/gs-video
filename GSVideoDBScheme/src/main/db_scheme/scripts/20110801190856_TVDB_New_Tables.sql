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
--// TheTVDB new tables.


Create Table TVDBShow (
    series_id int PRIMARY KEY,
    series_name varchar(500) NOT NULL,
	start_date date NULL,
	series_summary text NULL,
	series_image_url varchar(200) NULL,
	rating numeric(6, 2) NULL,
	rating_votes int NULL
);


Create Table TVDBEpisode (
	episode_id int PRIMARY KEY,
    series_id int NOT NULL REFERENCES TVDBShow ON DELETE CASCADE,
    episode_name varchar(500) NOT NULL,
    season_num int NOT NULL,
    episode_num int NOT NULL,
	air_date date NULL,
	episode_summary text NULL
);


--//@UNDO

Drop Table TVDBEpisode;
Drop Table TVDBShow;


