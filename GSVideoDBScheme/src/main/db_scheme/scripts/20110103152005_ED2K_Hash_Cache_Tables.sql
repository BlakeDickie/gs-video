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
--// ED2K Hash Cache Tables
-- Migration SQL that makes the change goes here.

Create Table AnimeED2KCaching (
	ed2k varchar(32) NOT NULL,
	file_length bigint NOT NULL,
	episode_id int NOT NULL REFERENCES AnimeEpisode ON DELETE CASCADE,
	PRIMARY KEY (ed2k, file_length)
);

Create Table AnimeED2KLookupFailureCaching (
	ed2k varchar(32) NOT NULL,
	file_length bigint NOT NULL,
	last_attempt timestamp with time zone,
	PRIMARY KEY (ed2k, file_length)
);

--//@UNDO
-- SQL to undo the change goes here.

Drop Table AnimeED2KCaching;
Drop Table AnimeED2KLookupFailureCaching;
