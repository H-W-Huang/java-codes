/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise.EX17_9;

import java.io.Serializable;

/**
 *
 * @author H.W
 */
public class MyAddress implements Serializable{

    private String name;
    private String street;
    private String city;
    private String state;
    private int zip;

    public MyAddress(String name, String street, String city, String state, int zip) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

}
