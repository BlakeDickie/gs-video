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
--// Change List ID PK
-- Migration SQL that makes the change goes here.

Drop Index idx_AnimeMyList_by_fid;

ALTER TABLE AnimeMyList DROP CONSTRAINT IF EXISTS animemylist_pkey;

ALTER TABLE AnimeMyList DROP COLUMN list_id;

ALTER TABLE AnimeMyList ADD PRIMARY KEY(file_id);

--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE AnimeMyList DROP CONSTRAINT IF EXISTS animemylist_pkey;

ALTER TABLE AnimeMyList ADD COLUMN list_id int NOT NULL;

ALTER TABLE AnimeMyList ADD PRIMARY KEY(list_id);

CREATE INDEX idx_AnimeMyList_by_fid ON AnimeMyList (file_id);
