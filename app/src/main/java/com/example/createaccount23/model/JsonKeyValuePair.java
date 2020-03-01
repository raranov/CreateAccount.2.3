package com.example.createaccount23.model;

public class JsonKeyValuePair {
    Fields field;
    String value;

    public JsonKeyValuePair(Fields field, String value) {
        this.field = field;
        this.value = value;
    }

    public JsonKeyValuePair() {
        this.field = null;
        this.value = "";
    }

    public enum Fields{
        first_name,
        last_name,
        gender,
        email,
        password,
        name_middle,
        name_nickname,
        name_reference,
        birth_date,
        birth_country,
        birth_city,
        birth_street,
        picture_profile,
        video_intro
    }

    public Fields getField() { return field; }

    public String getValue() { return value; }

    public void setField(Fields field) {
        this.field = field;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
