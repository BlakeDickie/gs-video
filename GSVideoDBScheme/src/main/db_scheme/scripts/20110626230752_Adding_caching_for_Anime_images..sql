--// Adding caching for Anime images.
-- Migration SQL that makes the change goes here.

Create Table AnimePictures (
	filename varchar(200) NOT NULL PRIMARY KEY,
    image_data bytea NOT NULL
);


--//@UNDO
-- SQL to undo the change goes here.

Drop Table AnimePictures;

