/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author bdickie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContextActionObject {
    String value();
}
