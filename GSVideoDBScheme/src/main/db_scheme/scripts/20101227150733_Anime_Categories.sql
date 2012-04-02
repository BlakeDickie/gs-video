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
--// Anime Categories
-- Migration SQL that makes the change goes here.


Create Table AnimeCategory (
    category_id int PRIMARY KEY,
    category_name varchar(500) NOT NULL,
    category_desc varchar NULL,
	hentai boolean NOT NULL,
	parent_category_id int NULL REFERENCES AnimeCategory ON DELETE CASCADE
);

--//@UNDO
-- SQL to undo the change goes here.
Drop Table AnimeCategory;

