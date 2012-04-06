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
public class OrderedRepresentation<T> extends Representation<T> implements Comparable<OrderedRepresentation<T>> {
    private long order;

    public OrderedRepresentation(long order, T value) {
        super(value);
        this.order = order;
    }

    public OrderedRepresentation(long order, String toString, T value) {
        super(toString, value);
        this.order = order;
    }

    public OrderedRepresentation(T value) {
        super(value);
    }

    public OrderedRepresentation(String toString, T value) {
        super(toString, value);
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    @Override
    public int compareTo(OrderedRepresentation<T> o) {
        if (o == this)
            return 0;
        
        long otherOrder = o.getOrder();
        long thisOrder = getOrder();
        
        if (thisOrder < otherOrder)
            return -1;
        else if (thisOrder > otherOrder)
            return 1;
        else
            return toString().compareToIgnoreCase(o.toString());
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderedRepresentation<T> other = (OrderedRepresentation<T>) obj;
        if (this.order != other.order) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.order ^ (this.order >>> 32));
        hash = 23 * hash + super.hashCode();
        return hash;
    }
    
}
