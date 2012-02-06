--// TheTVDB new tables.


Create Table TVDBShow (
    series_id int PRIMARY KEY,
    series_name varchar(500) NOT NULL,
	start_date date NULL,
	series_summary text NULL,
	series_image_url varchar(200) NULL,
	rating numeric(6, 2) NULL,
	rating_votes int NULL
);


Create Table TVDBEpisode (
	episode_id int PRIMARY KEY,
    series_id int NOT NULL REFERENCES TVDBShow ON DELETE CASCADE,
    episode_name varchar(500) NOT NULL,
    season_num int NOT NULL,
    episode_num int NOT NULL,
	air_date date NULL,
	episode_summary text NULL
);


--//@UNDO

Drop Table TVDBEpisode;
Drop Table TVDBShow;


