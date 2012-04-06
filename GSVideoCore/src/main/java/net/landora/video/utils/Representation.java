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

package net.landora.video.utils;

/**
 *
 * @author bdickie
 */
public class Representation<T> {
    
    protected T value;
    
    protected String toString;

    public Representation(String toString, T value) {
        this.value = value;
        this.toString = toString;
    }

    public Representation(T value) {
        this(String.valueOf(value), value);
    }

    public String getToString() {
        return toString;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return toString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Representation<T> other = (Representation<T>) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
    
}
