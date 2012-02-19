--// Add extra video file infomation to AnimeFile

ALTER TABLE AnimeFile
	ADD COLUMN source varchar(20) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN videoCodec varchar(40) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN videoResolution varchar(20) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN fileType varchar(20) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN save_revision integer NOT NULL DEFAULT 1;

--//@UNDO


ALTER TABLE AnimeFile
	DROP COLUMN source;

ALTER TABLE AnimeFile
	DROP COLUMN videoCodec;

ALTER TABLE AnimeFile
	DROP COLUMN videoResolution;

ALTER TABLE AnimeFile
	DROP COLUMN fileType;

ALTER TABLE AnimeFile
	DROP COLUMN save_revision;

