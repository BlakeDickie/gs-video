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
--// Add extra video file infomation to AnimeFile

ALTER TABLE AnimeFile
	ADD COLUMN source varchar(20) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN videoCodec varchar(40) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN videoResolution varchar(20) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN fileType varchar(20) NULL;

ALTER TABLE AnimeFile
	ADD COLUMN save_revision integer NOT NULL DEFAULT 1;

--//@UNDO


ALTER TABLE AnimeFile
	DROP COLUMN source;

ALTER TABLE AnimeFile
	DROP COLUMN videoCodec;

ALTER TABLE AnimeFile
	DROP COLUMN videoResolution;

ALTER TABLE AnimeFile
	DROP COLUMN fileType;

ALTER TABLE AnimeFile
	DROP COLUMN save_revision;

