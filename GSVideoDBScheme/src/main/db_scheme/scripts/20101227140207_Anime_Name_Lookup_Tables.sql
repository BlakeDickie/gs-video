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
--// Anime Name Lookup Tables
-- Migration SQL that makes the change goes here.


Create Table AnimeNameLookupSummary (
    anime_id int PRIMARY KEY,
    name_main varchar(500) NULL,
    name_english varchar(500) NULL
);

Create Table AnimeNameLookup (
    anime_id int NOT NULL REFERENCES AnimeNameLookupSummary ON DELETE CASCADE,
    anime_name varchar(1000) NOT NULL,
    name_type varchar(20) NOT NULL,
    name_lang varchar(10) NOT NULL,
	PRIMARY KEY (anime_id, anime_name, name_lang)
);

--//@UNDO
-- SQL to undo the change goes here.

Drop Table AnimeNameLookup;
Drop Table AnimeNameLookupSummary;
