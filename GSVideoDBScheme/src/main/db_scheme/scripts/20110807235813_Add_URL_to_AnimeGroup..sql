--
--     Copyright (C) 2012 Blake Dickie
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
--// Add URL to AnimeGroup.
ALTER TABLE AnimeGroup
	ADD COLUMN group_url varchar(200) NULL;

ALTER TABLE AnimeGroup
	ADD COLUMN group_full boolean NOT NULL DEFAULT false;


--//@UNDO

ALTER TABLE AnimeGroup
	DROP COLUMN group_url;

ALTER TABLE AnimeGroup
	DROP COLUMN group_full;
