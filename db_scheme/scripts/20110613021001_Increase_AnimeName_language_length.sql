--// Increase AnimeName language length
-- Migration SQL that makes the change goes here.
ALTER TABLE AnimeName
	ALTER COLUMN name_lang TYPE varchar(200);


--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE AnimeName
	ALTER COLUMN name_lang TYPE varchar(10);

