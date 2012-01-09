/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video;

/**
 *
 * @author bdickie
 */
public class TestObject implements java.io.Serializable {
    private String testing;

    public TestObject(String testing) {
        this.testing = testing;
    }

    public TestObject() {
    }

    public String getTesting() {
        return testing;
    }

    public void setTesting(String testing) {
        this.testing = testing;
    }
    
    
}
