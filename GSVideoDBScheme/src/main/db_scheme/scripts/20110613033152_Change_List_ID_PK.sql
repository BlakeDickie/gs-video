--// Change List ID PK
-- Migration SQL that makes the change goes here.

Drop Index idx_AnimeMyList_by_fid;

ALTER TABLE AnimeMyList DROP CONSTRAINT IF EXISTS animemylist_pkey;

ALTER TABLE AnimeMyList DROP COLUMN list_id;

ALTER TABLE AnimeMyList ADD PRIMARY KEY(file_id);

--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE AnimeMyList DROP CONSTRAINT IF EXISTS animemylist_pkey;

ALTER TABLE AnimeMyList ADD COLUMN list_id int NOT NULL;

ALTER TABLE AnimeMyList ADD PRIMARY KEY(list_id);

CREATE INDEX idx_AnimeMyList_by_fid ON AnimeMyList (file_id);
