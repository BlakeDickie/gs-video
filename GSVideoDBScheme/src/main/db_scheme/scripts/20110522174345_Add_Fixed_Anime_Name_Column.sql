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
--// Add Fixed Anime Name Column
-- Migration SQL that makes the change goes here.
ALTER TABLE animenamelookup ADD COLUMN anime_name_escaped varchar(1000) NULL;
CREATE INDEX IX_animenamelookup_full ON animenamelookup (anime_name);
CREATE INDEX IX_animenamelookup_escaped ON animenamelookup (anime_name_escaped);

--//@UNDO
-- SQL to undo the change goes here.
DROP INDEX IX_animenamelookup_escaped;
DROP INDEX IX_animenamelookup_full;
ALTER TABLE animenamelookup DROP COLUMN anime_name_escaped;

