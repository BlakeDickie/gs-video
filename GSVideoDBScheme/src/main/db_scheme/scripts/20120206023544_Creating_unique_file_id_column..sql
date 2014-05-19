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
--// Creating unique file id column.
-- Migration SQL that makes the change goes here.


ALTER TABLE SharedFile
	ADD COLUMN videoId varchar(255) NULL;

CREATE INDEX IX_SharedFile_videoId on SharedFile (videoId);

--//@UNDO
-- SQL to undo the change goes here.

DROP INDEX IX_SharedFile_videoId;

ALTER TABLE SharedFile
	DROP COLUMN videoId;
