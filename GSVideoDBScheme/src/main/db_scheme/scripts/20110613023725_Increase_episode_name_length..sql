--// Increase episode name length.
-- Migration SQL that makes the change goes here.

ALTER TABLE AnimeEpisode
	ALTER COLUMN name_english TYPE varchar(1000);
	
ALTER TABLE AnimeEpisode
	ALTER COLUMN name_romaji TYPE varchar(1000);
	
ALTER TABLE AnimeEpisode
	ALTER COLUMN name_kanji TYPE varchar(1000);

--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE AnimeEpisode
	ALTER COLUMN name_english TYPE varchar(200);
	
ALTER TABLE AnimeEpisode
	ALTER COLUMN name_romaji TYPE varchar(200);
	
ALTER TABLE AnimeEpisode
	ALTER COLUMN name_kanji TYPE varchar(200);
