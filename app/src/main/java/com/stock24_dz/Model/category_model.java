package com.stock24_dz.Model;

public class category_model {
    private String id;
    private String category;
    private String category_image ;
    private String category_fr ;
    private String parent_id ;


    public category_model(){

    }

    public category_model(String id, String category, String category_image, String category_fr, String parent_id) {
        this.id = id;
        this.category = category;
        this.category_image = category_image;
        this.category_fr = category_fr;
        this.parent_id = parent_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_fr() {
        return category_fr;
    }

    public void setCategory_fr(String category_fr) {
        this.category_fr = category_fr;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
