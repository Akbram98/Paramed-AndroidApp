package com.example.paramed;

public class AddressConfig {
    private String street_address, city, province, postal;

    public AddressConfig(){
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    @Override
    public String toString(){
        return null;
    }
}
