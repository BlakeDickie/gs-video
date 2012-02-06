--// Create Anime MyList Tables
-- Migration SQL that makes the change goes here.

Create Table AnimeMyList (
	list_id int NOT NULL PRIMARY KEY,
    file_id int NOT NULL REFERENCES AnimeFile ON DELETE CASCADE,
	state int NOT NULL,
	file_state int NOT NULL,
	added_date timestamp with time zone NOT NULL,
	view_date timestamp with time zone NULL,
	storage varchar(200) NOT NULL,
	source varchar(200) NOT NULL,
	other text NOT NULL
);

CREATE INDEX idx_AnimeMyList_by_fid ON AnimeMyList (file_id);

--//@UNDO
-- SQL to undo the change goes here.

Drop Index idx_AnimeMyList_by_fid;

Drop Table AnimeMyList;

