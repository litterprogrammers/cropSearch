package com.bjh.pojo;

public class CropsSpeciesInfo {
    String name;
    String database;
    String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "SpeciesInfo{" +
                "name='" + name + '\'' +
                ", database='" + database + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
