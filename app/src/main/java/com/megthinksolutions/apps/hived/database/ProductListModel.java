package com.megthinksolutions.apps.hived.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_list_table")
public class ProductListModel {
    @PrimaryKey(autoGenerate = true)
    private int autoId;
    private String id;
    private String PSelect;
    private String PBrand;
    private String PModel;

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPSelect() {
        return PSelect;
    }

    public void setPSelect(String PSelect) {
        this.PSelect = PSelect;
    }

    public String getPBrand() {
        return PBrand;
    }

    public void setPBrand(String PBrand) {
        this.PBrand = PBrand;
    }

    public String getPModel() {
        return PModel;
    }

    public void setPModel(String PModel) {
        this.PModel = PModel;
    }
}
