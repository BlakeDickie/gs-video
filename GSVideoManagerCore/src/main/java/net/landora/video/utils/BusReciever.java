/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author bdickie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusReciever {
    boolean awt() default true;
}
