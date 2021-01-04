package com.megthinksolutions.apps.hived.database;

import android.os.Build;
import android.os.Process;

import androidx.annotation.RequiresApi;

import com.megthinksolutions.apps.hived.utils.Utils;

import java.util.List;

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void insertProductList(List<ProductListModel> productModelList) {
        if (Utils.isInUIThread()) {
            Thread thread = new Thread(() -> {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                mDatabase.getProductListDao().insert(productModelList);
            });
            thread.start();
        } else {
            mDatabase.getProductListDao().insert(productModelList);
        }
    }

    public List<ProductListModel> getProductListUri() {
        return mDatabase.getProductListDao().getProductListData();
    }

    public List<String> getAllProductList() {
        return mDatabase.getProductListDao().getAllSelectList();
    }

    public List<String> getBrandListSelectWise(String select) {
        return mDatabase.getProductListDao().getBrandListSelectWise(select);
    }



}
