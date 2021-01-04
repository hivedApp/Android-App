package com.megthinksolutions.apps.hived.models;

public class RewardsProfileModel {
    private String id;
    private String rewards_title;
    private String rewards_desc;
    private String rewards_price;
    private String rewards_image;

    public RewardsProfileModel() {
    }

    public RewardsProfileModel(String id, String rewards_title, String rewards_desc, String rewards_price, String rewards_image) {
        this.id = id;
        this.rewards_title = rewards_title;
        this.rewards_desc = rewards_desc;
        this.rewards_price = rewards_price;
        this.rewards_image = rewards_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRewards_title() {
        return rewards_title;
    }

    public void setRewards_title(String rewards_title) {
        this.rewards_title = rewards_title;
    }

    public String getRewards_desc() {
        return rewards_desc;
    }

    public void setRewards_desc(String rewards_desc) {
        this.rewards_desc = rewards_desc;
    }

    public String getRewards_price() {
        return rewards_price;
    }

    public void setRewards_price(String rewards_price) {
        this.rewards_price = rewards_price;
    }

    public String getRewards_image() {
        return rewards_image;
    }

    public void setRewards_image(String rewards_image) {
        this.rewards_image = rewards_image;
    }
}
