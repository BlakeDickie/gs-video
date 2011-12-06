--// Anime Categories
-- Migration SQL that makes the change goes here.


Create Table AnimeCategory (
    category_id int PRIMARY KEY,
    category_name varchar(500) NOT NULL,
    category_desc varchar NULL,
	hentai boolean NOT NULL,
	parent_category_id int NULL REFERENCES AnimeCategory ON DELETE CASCADE
);

--//@UNDO
-- SQL to undo the change goes here.
Drop Table AnimeCategory;

