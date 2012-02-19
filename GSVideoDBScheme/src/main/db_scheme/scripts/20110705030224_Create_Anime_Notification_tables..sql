--// Create Anime Notification tables.

Create Table AnimeNotification (
    file_id int NOT NULL PRIMARY KEY REFERENCES AnimeFile ON DELETE CASCADE,
	added_date timestamp with time zone NOT NULL,
	view_date timestamp with time zone NULL,
	removed_date timestamp with time zone NULL,
	type_id int NOT NULL
);

CREATE INDEX idx_AnimeNotification_unremoved ON AnimeNotification (removed_date);

Create Table AnimeMessage (
    messageId int NOT NULL PRIMARY KEY,
	msg_date timestamp with time zone NOT NULL,
	type_id int NOT NULL,
	from_user varchar(200) NOT NULL,
	title varchar(1000) NOT NULL,
	body text NOT NULL,
	removed_date timestamp with time zone NULL
);

--//@UNDO

Drop Table AnimeMessage;
Drop Index idx_AnimeNotification_unremoved;
Drop Table AnimeNotification;
