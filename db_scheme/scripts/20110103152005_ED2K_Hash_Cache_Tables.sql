--// ED2K Hash Cache Tables
-- Migration SQL that makes the change goes here.

Create Table AnimeED2KCaching (
	ed2k varchar(32) NOT NULL,
	file_length bigint NOT NULL,
	episode_id int NOT NULL REFERENCES AnimeEpisode ON DELETE CASCADE,
	PRIMARY KEY (ed2k, file_length)
);

Create Table AnimeED2KLookupFailureCaching (
	ed2k varchar(32) NOT NULL,
	file_length bigint NOT NULL,
	last_attempt timestamp with time zone,
	PRIMARY KEY (ed2k, file_length)
);

--//@UNDO
-- SQL to undo the change goes here.

Drop Table AnimeED2KCaching;
Drop Table AnimeED2KLookupFailureCaching;
