package com.bjh.pojo;

public class CropInfo {
    String name;
    String databaseData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabaseData() {
        return databaseData;
    }

    public void setDatabaseData(String databaseData) {
        this.databaseData = databaseData;
    }

    @Override
    public String toString() {
        return "CropInfo{" +
                "name='" + name + '\'' +
                ", englishName='" + databaseData + '\'' +
                '}';
    }
}
