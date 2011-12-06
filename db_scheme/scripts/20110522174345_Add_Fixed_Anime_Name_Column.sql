--// Add Fixed Anime Name Column
-- Migration SQL that makes the change goes here.
ALTER TABLE animenamelookup ADD COLUMN anime_name_escaped varchar(1000) NULL;
CREATE INDEX IX_animenamelookup_full ON animenamelookup (anime_name);
CREATE INDEX IX_animenamelookup_escaped ON animenamelookup (anime_name_escaped);

--//@UNDO
-- SQL to undo the change goes here.
DROP INDEX IX_animenamelookup_escaped;
DROP INDEX IX_animenamelookup_full;
ALTER TABLE animenamelookup DROP COLUMN anime_name_escaped;

