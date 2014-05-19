--
--     Copyright (C) 2012-2014 Blake Dickie
--
--     This program is free software: you can redistribute it and/or modify
--     it under the terms of the GNU General Public License as published by
--     the Free Software Foundation, either version 3 of the License, or
--     (at your option) any later version.
--
--     This program is distributed in the hope that it will be useful,
--     but WITHOUT ANY WARRANTY; without even the implied warranty of
--     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--     GNU General Public License for more details.
--
--     You should have received a copy of the GNU General Public License
--     along with this program.  If not, see <http://www.gnu.org/licenses/>.
--
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
