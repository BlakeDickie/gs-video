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
--// Create Anime MyList Tables
-- Migration SQL that makes the change goes here.

Create Table AnimeMyList (
	list_id int NOT NULL PRIMARY KEY,
    file_id int NOT NULL REFERENCES AnimeFile ON DELETE CASCADE,
	state int NOT NULL,
	file_state int NOT NULL,
	added_date timestamp with time zone NOT NULL,
	view_date timestamp with time zone NULL,
	storage varchar(200) NOT NULL,
	source varchar(200) NOT NULL,
	other text NOT NULL
);

CREATE INDEX idx_AnimeMyList_by_fid ON AnimeMyList (file_id);

--//@UNDO
-- SQL to undo the change goes here.

Drop Index idx_AnimeMyList_by_fid;

Drop Table AnimeMyList;

