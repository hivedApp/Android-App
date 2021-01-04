package com.megthinksolutions.apps.hived.networking;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class RequestFormatter {
    /* todo HomeList */
    public static JsonObject jsonObjectHomeListWithPagination(String userId,
                                                              String paginationValue1,
                                                              String paginationValue2) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", userId);
        jsonObject.addProperty("paginationValue1", paginationValue1);
        jsonObject.addProperty("paginationValue2", paginationValue2);

        return jsonObject;

    }

    /* todo ProductReview */
    public static JsonObject jsonObjectProductReviewPage1(String userId,
                                                          String productId, String userName,
                                                          String product_select, String product_brand,
                                                          String product_model, List<String> add_product_Photos,
                                                          String add_product_comment, String product_title,
                                                          String product_description, float product_rating,
                                                          String product_seller, String product_branch,
                                                          String product_sales_person, String product_saller_title,
                                                          String product_seller_description, float product_seller_rating) {

        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("userId", userId);
            jsonObject.addProperty("productId", productId);
            jsonObject.addProperty("userName", userName);
            jsonObject.addProperty("product_select", product_select);
            jsonObject.addProperty("product_brand", product_brand);
            jsonObject.addProperty("product_model", product_model);

            JsonArray jsonArray = new JsonArray();
            for (int a = 0; a < add_product_Photos.size(); a++) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("image_number", a);
                jsonObject2.addProperty("type", add_product_Photos.get(a));
                jsonArray.add(jsonObject2);
            }

            jsonObject.add("add_product_Photos", jsonArray);

            jsonObject.addProperty("add_product_comment", add_product_comment);
            jsonObject.addProperty("product_title", product_title);
            jsonObject.addProperty("product_description", product_description);
            jsonObject.addProperty("product_rating", product_rating);
            jsonObject.addProperty("product_seller", product_seller);
            jsonObject.addProperty("product_branch", product_branch);
            jsonObject.addProperty("product_sales_person", product_sales_person);
            jsonObject.addProperty("product_saller_title", product_saller_title);
            jsonObject.addProperty("product_seller_description", product_seller_description);
            jsonObject.addProperty("product_seller_rating", product_seller_rating);

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return jsonObject;

    }

    public static JsonObject jsonObjectProductReviewPage2(String userId,
                                                          String productId, String product_title,
                                                          String product_description, float product_rating) {
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("userId", userId);
            jsonObject.addProperty("productId", productId);
            jsonObject.addProperty("product_title", product_title);
            jsonObject.addProperty("product_description", product_description);
            jsonObject.addProperty("product_rating", product_rating);

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return jsonObject;
    }


    public static JsonObject jsonObjectProductReviewPage3(String userId,
                                                          String productId, String product_title,
                                                          String product_seller, String product_branch,
                                                          String product_sales_person, String product_saller_title,
                                                          String product_seller_description, float product_seller_rating) {
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("userId", userId);
            jsonObject.addProperty("productId", productId);
            jsonObject.addProperty("product_title", product_title);
            jsonObject.addProperty("product_seller", product_seller);
            jsonObject.addProperty("product_branch", product_branch);
            jsonObject.addProperty("product_sales_person", product_sales_person);
            jsonObject.addProperty("product_saller_title", product_saller_title);
            jsonObject.addProperty("product_seller_description", product_seller_description);
            jsonObject.addProperty("product_seller_rating", product_seller_rating);

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return jsonObject;
    }


    public static JsonObject jsonObjectProfileProduct(String userId) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", userId);

        return jsonObject;
    }

    public static JsonObject jsonObjectProfilePost(String userId) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", userId);

        return jsonObject;
    }

}
