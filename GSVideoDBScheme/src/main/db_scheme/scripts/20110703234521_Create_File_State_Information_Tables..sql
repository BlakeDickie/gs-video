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
--// Create File State Information Tables.
-- Migration SQL that makes the change goes here.

Create Table SharedDirectory (
	directoryId SERIAL NOT NULL PRIMARY KEY,
    directoryUuid varchar(64) NOT NULL,
    directoryName varchar(1000) NOT NULL,
    directoryDefaultPath varchar(2000) NOT NULL
);

Create Table SharedFile (
	fileId SERIAL NOT NULL PRIMARY KEY,
	directoryId integer NOT NULL REFERENCES SharedDirectory ON DELETE CASCADE,
	subpath varchar(2000) NOT NULL,
	filename varchar(1000) NOT NULL,
	fileSize integer NOT NULL,
	ed2k varchar(32) NOT NULL,
	metadata_source varchar(1000) NULL,
	metadata_id varchar(1000) NULL,
	last_modified timestamp with time zone
);

--//@UNDO
-- SQL to undo the change goes here.

Drop Table SharedFile;
Drop Table SharedDirectory;


