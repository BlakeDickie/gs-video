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
