--// Preferences
-- Migration SQL that makes the change goes here.


Create Table Preferences (
	context varchar(200) NOT NULL,
	prefname varchar(200) NOT NULL,
	prefvalue text NOT NULL
);

--//@UNDO
-- SQL to undo the change goes here.

Drop Table Preferences;
