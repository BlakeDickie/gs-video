--// Add autoCheckProperty to SharedDirectory.
-- Migration SQL that makes the change goes here.
ALTER TABLE SharedDirectory ADD COLUMN renameNewFiles boolean NOT NULL DEFAULT false;


--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE SharedDirectory DROP COLUMN renameNewFiles;

