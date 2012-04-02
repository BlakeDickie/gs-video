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
--// FileState Conflict Tables
-- Migration SQL that makes the change goes here.
ALTER TABLE SharedFile ADD COLUMN autoRename boolean NOT NULL DEFAULT false;

ALTER TABLE SharedFile ALTER COLUMN fileSize TYPE bigint;



Create Table SharedFileConflict (
	conflictId SERIAL NOT NULL PRIMARY KEY,
	reportedDate timestamp with time zone,
	conflictType varchar(200) NOT NULL
);

Create Table SharedFileConflictItem (
	conflictId integer NOT NULL REFERENCES SharedFileConflict ON DELETE CASCADE,
	fileId integer NOT NULL REFERENCES SharedFile ON DELETE CASCADE,
	conflict_extra varchar(1000) NULL
);


CREATE INDEX IX_SharedFile_Path ON SharedFile (directoryId, subpath);

--//@UNDO
-- SQL to undo the change goes here.

DROP INDEX IX_SharedFile_Path;

Drop Table SharedFileConflictItem;
Drop Table SharedFileConflict;

ALTER TABLE SharedFile DROP COLUMN autoRename;
