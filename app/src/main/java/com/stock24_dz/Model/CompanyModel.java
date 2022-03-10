package com.stock24_dz.Model;

import androidx.annotation.Nullable;

public class CompanyModel {
    @Nullable
    private String about_us_fr;
    @Nullable
    private String email;
    @Nullable
    private String terms_of_services_fr;
    @Nullable
    private String privacy_policy_fr;

    public CompanyModel(@Nullable String about_us_fr, @Nullable String email, @Nullable String terms_of_services_fr, @Nullable String privacy_policy_fr) {
        this.about_us_fr = about_us_fr;
        this.email = email;
        this.terms_of_services_fr = terms_of_services_fr;
        this.privacy_policy_fr = privacy_policy_fr;
    }

    @Nullable
    public String getAbout_us_fr() {
        return about_us_fr;
    }

    public void setAbout_us_fr(@Nullable String about_us_fr) {
        this.about_us_fr = about_us_fr;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getTerms_of_services_fr() {
        return terms_of_services_fr;
    }

    public void setTerms_of_services_fr(@Nullable String terms_of_services_fr) {
        this.terms_of_services_fr = terms_of_services_fr;
    }

    @Nullable
    public String getPrivacy_policy_fr() {
        return privacy_policy_fr;
    }

    public void setPrivacy_policy_fr(@Nullable String privacy_policy_fr) {
        this.privacy_policy_fr = privacy_policy_fr;
    }
}
