package com.example.createaccount23.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;


public class User {
    private String id; // this will comes from the random username assigned by cognito

    //Helper fields
    private String firstInput;
    private String secondInput;

    //Create Account
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private Gender gender;
    private String email;
    private String password;

    //Your birth
    private String birthDay;
    private String birthMonth;
    private String birthYear;
    @SerializedName("birth_date")
    private Date birthDate;
    @SerializedName("birth_country")
    private String birthCountry;
    @SerializedName("birth_city")
    private String birthCity;
    @SerializedName("birth_street")
    private String birthStreet;

    //Your Name
    @SerializedName("name_middle")
    private String middleName;
    @SerializedName("name_nickname")
    private String nickname;
    @SerializedName("name_reference")
    private String referenceName;

    // Pictures
    @SerializedName("picture_profile")
    private String profilePicture;

    // Videos
    @SerializedName("video_intro")
    private String introVideo;

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public enum Gender{
        MALE,
        FEMALE
    }

    public User() {
        this.firstInput = "";
        this.secondInput = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
    }

    public User(String userID, String firstName, String lastName, String email, String password) {
        this.id = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void setFirstInput(String firstInput) {
        this.firstInput = firstInput;
    }

    public void setSecondInput(String secondInput) {
        this.secondInput = secondInput;
    }

    public void setId(String id) {this.id = id;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstInput() {
        return firstInput;
    }

    public String getSecondInput() {
        return secondInput;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {return lastName;   }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getBirthStreet() {
        return birthStreet;
    }

    public void setBirthStreet(String birthStreet) {
        this.birthStreet = birthStreet;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getIntroVideo() {
        return introVideo;
    }

    public void setIntroVideo(String introVideo) {
        this.introVideo = introVideo;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @NotNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstInput='" + firstInput + '\'' +
                ", secondInput='" + secondInput + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", birthMonth='" + birthMonth + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", birthCountry='" + birthCountry + '\'' +
                ", birthCity='" + birthCity + '\'' +
                ", birthStreet='" + birthStreet + '\'' +
                ", referenceName='" + referenceName + '\'' +
                '}';
    }
}
