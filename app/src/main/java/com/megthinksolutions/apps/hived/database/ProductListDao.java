package com.megthinksolutions.apps.hived.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProductListModel> productListModel);

    @Query("SELECT * from product_list_table")
    List<ProductListModel> getProductListData();

    @Query("SELECT DISTINCT PSelect from product_list_table ORDER BY PSelect")
    List<String> getAllSelectList();

    @Query("SELECT DISTINCT PBrand from product_list_table where PSelect = :mPSelect ORDER BY PBrand")
    List<String> getBrandListSelectWise(String mPSelect);

}
