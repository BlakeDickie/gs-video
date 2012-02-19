--// Create File Tables
-- Migration SQL that makes the change goes here.

Create Table AnimeGroup (
	group_id int NOT NULL PRIMARY KEY,
	name_short varchar(200) NULL,
	name_long varchar(200) NULL
);

Create Table AnimeFile (
	file_id int NOT NULL PRIMARY KEY,
	episode_id int NOT NULL REFERENCES AnimeEpisode ON DELETE CASCADE,
	group_id int NULL REFERENCES AnimeGroup ON DELETE CASCADE,
	ed2k varchar(32) NULL,
	file_length bigint NULL,
	crc_valid boolean NULL,
	version smallint NULL,
	censored boolean NULL,
	generic_file boolean NOT NULL
	
);

CREATE INDEX idx_AnimeFile_by_e2dk ON AnimeFile (ed2k, file_length);
CREATE INDEX idx_AnimeFile_by_generic ON AnimeFile (episode_id, generic_file);

--//@UNDO
-- SQL to undo the change goes here.

Drop Index idx_AnimeFile_by_generic;
Drop Index idx_AnimeFile_by_e2dk;

Drop Table AnimeFile;
Drop Table AnimeGroup;
