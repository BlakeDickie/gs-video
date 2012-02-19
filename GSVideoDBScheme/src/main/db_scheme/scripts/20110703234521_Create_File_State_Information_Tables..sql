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


