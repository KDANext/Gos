package com.example.gossssss;

public class Country {
    private int Id;
    private String Name;
    private String Capital;
    private Integer Number;
    private boolean flag;

    public Country(int id, String name, String capital, Integer number, boolean flag) {
        Id = id;
        Name = name;
        Capital = capital;
        Number = number;
        this.flag = flag;
    }

    public Country(String Name, String Capital, Integer Number, boolean flag){
        this.Name = Name;
        this.Capital = Capital;
        this.Number = Number;
        this.flag = flag;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    @Override
    public String toString() {
        return "Country{" +
                "Name='" + Name + '\'' +
                ", Capital='" + Capital + '\'' +
                '}';
    }
}
