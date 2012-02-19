--// Anime Name Lookup Tables
-- Migration SQL that makes the change goes here.


Create Table AnimeNameLookupSummary (
    anime_id int PRIMARY KEY,
    name_main varchar(500) NULL,
    name_english varchar(500) NULL
);

Create Table AnimeNameLookup (
    anime_id int NOT NULL REFERENCES AnimeNameLookupSummary ON DELETE CASCADE,
    anime_name varchar(1000) NOT NULL,
    name_type varchar(20) NOT NULL,
    name_lang varchar(10) NOT NULL,
	PRIMARY KEY (anime_id, anime_name, name_lang)
);

--//@UNDO
-- SQL to undo the change goes here.

Drop Table AnimeNameLookup;
Drop Table AnimeNameLookupSummary;
