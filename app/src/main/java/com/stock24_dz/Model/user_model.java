package com.stock24_dz.Model;

public class user_model {
    private String id;
    private String email;
    private String first_name;
    private String primary_phone_number ;
    private String secondary_phone_number ;
    private String about ;
    private String 	company ;
    private String 	address ;
    private String 	town_id  ;
    private String profile_image   ;
    private String cover_image   ;
    private String badge   ;
    private String user_type   ;
    private String facebook_id    ;
    private String facebook_access_token     ;
    private String firebase_id    ;
    private String email_verified_at     ;
    private int approved;
    private int auto_approve_future_announcements;
    private  int subscribed;

    public user_model(){

    }

    public user_model(String id, String email, String first_name, String primary_phone_number, String secondary_phone_number, String about, String company, String address, String town_id, String profile_image, String cover_image, String badge, String user_type, String facebook_id, String facebook_access_token, String firebase_id, String email_verified_at, int approved, int auto_approve_future_announcements, int subscribed) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.primary_phone_number = primary_phone_number;
        this.secondary_phone_number = secondary_phone_number;
        this.about = about;
        this.company = company;
        this.address = address;
        this.town_id = town_id;
        this.profile_image = profile_image;
        this.cover_image = cover_image;
        this.badge = badge;
        this.user_type = user_type;
        this.facebook_id = facebook_id;
        this.facebook_access_token = facebook_access_token;
        this.firebase_id = firebase_id;
        this.email_verified_at = email_verified_at;
        this.approved = approved;
        this.auto_approve_future_announcements = auto_approve_future_announcements;
        this.subscribed = subscribed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPrimary_phone_number() {
        return primary_phone_number;
    }

    public void setPrimary_phone_number(String primary_phone_number) {
        this.primary_phone_number = primary_phone_number;
    }

    public String getSecondary_phone_number() {
        return secondary_phone_number;
    }

    public void setSecondary_phone_number(String secondary_phone_number) {
        this.secondary_phone_number = secondary_phone_number;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown_id() {
        return town_id;
    }

    public void setTown_id(String town_id) {
        this.town_id = town_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getFacebook_access_token() {
        return facebook_access_token;
    }

    public void setFacebook_access_token(String facebook_access_token) {
        this.facebook_access_token = facebook_access_token;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getAuto_approve_future_announcements() {
        return auto_approve_future_announcements;
    }

    public void setAuto_approve_future_announcements(int auto_approve_future_announcements) {
        this.auto_approve_future_announcements = auto_approve_future_announcements;
    }

    public int getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }
}
