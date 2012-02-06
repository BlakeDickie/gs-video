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
