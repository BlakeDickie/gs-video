--// Add URL to AnimeGroup.
ALTER TABLE AnimeGroup
	ADD COLUMN group_url varchar(200) NULL;

ALTER TABLE AnimeGroup
	ADD COLUMN group_full boolean NOT NULL DEFAULT false;


--//@UNDO

ALTER TABLE AnimeGroup
	DROP COLUMN group_url;

ALTER TABLE AnimeGroup
	DROP COLUMN group_full;
