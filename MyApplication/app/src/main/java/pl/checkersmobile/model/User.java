package pl.checkersmobile.model;

import java.io.Serializable;

/**
 * Created by syron on 18.01.16.
 */
public class User implements Serializable {


    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

