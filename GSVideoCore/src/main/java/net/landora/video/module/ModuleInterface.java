/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package net.landora.video.module;

/**
 *
 * @author bdickie
 */
public interface ModuleInterface {
    public static final int MODULE_PRIORITY_PREFERRED = 10;
    public static final int MODULE_PRIORITY_FALLBACK = 0;
    public static final int MODULE_PRIORITY_GENERAL = 5;
    
    public String getModuleName();
    public String getModuleDescription();
    public boolean isModuleUsable();
    public boolean isModuleConfigured();
    public String getModuleError();
    public int getModulePriority();
}
