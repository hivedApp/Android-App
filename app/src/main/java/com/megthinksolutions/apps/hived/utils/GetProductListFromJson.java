package com.megthinksolutions.apps.hived.utils;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.megthinksolutions.apps.hived.database.AppDatabase;
import com.megthinksolutions.apps.hived.database.DataRepository;
import com.megthinksolutions.apps.hived.database.ProductListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetProductListFromJson {
    //product.json
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void getProductListData(Context context) {
        List<ProductListModel> productModelList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(getJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("array");
            for (int a = 0; a < jsonArray.length(); a++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(a);

                ProductListModel productListModel = new ProductListModel();
                productListModel.setId(jsonObject1.getString("id"));
                productListModel.setPSelect(jsonObject1.getString("select"));
                productListModel.setPBrand(jsonObject1.getString("brand"));
                productListModel.setPModel(jsonObject1.getString("model"));
                productModelList.add(productListModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataRepository repository = DataRepository
                .getInstance(AppDatabase.getDatabase(context));

        repository.insertProductList(productModelList);
    }

    private static String getJson(Context context) {
        String json = null;
        try {
            // Opening cities.json file
            InputStream is = context.getAssets().open("product.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }
}


