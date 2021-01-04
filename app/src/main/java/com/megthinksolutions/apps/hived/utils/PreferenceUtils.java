package com.megthinksolutions.apps.hived.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;

import androidx.annotation.StringRes;

import com.megthinksolutions.apps.hived.R;


public class PreferenceUtils {
    private static PreferenceUtils sSharedPrefs;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private boolean mBulkUpdate = false;
    Context _context;

    private static final SparseArray<Object> sDefaultValues = new SparseArray<>();

    public PreferenceUtils(Context context) {
        _context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);

        sDefaultValues.put(R.string.pref_isFirstTimeLaunch_key, true);
        sDefaultValues.put(R.string.pref_is_login_key, false);
        sDefaultValues.put(R.string.pref_product_id,"");
    }

    public static PreferenceUtils getInstance(Context context, boolean isNewActivity) {
        if (sSharedPrefs == null && isNewActivity) {
            sSharedPrefs = new PreferenceUtils(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static PreferenceUtils getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    public Object getDefaultValue(@StringRes int key) {
        return sDefaultValues.get(key);
    }

    public void putInt(@StringRes int key, int value) {
        doEdit();
        editor.putInt(_context.getString(key), value);
        doCommit();
    }

    public void putDouble(@StringRes int key, double value) {
        doEdit();
        editor.putLong(_context.getString(key), Double.doubleToRawLongBits(value));
        doCommit();
    }

    public double getDouble(@StringRes int key) {
        return Double.longBitsToDouble(pref.getLong(_context.getString(key), Double.doubleToLongBits(0.00)));
    }

    public void putString(@StringRes int key, String value) {
        doEdit();
        editor.putString(_context.getString(key), value);
        doCommit();
    }

    public void putBoolean(@StringRes int key, boolean value) {
        doEdit();
        editor.putBoolean(_context.getString(key), value);
        doCommit();
    }

    public void putFloat(@StringRes int key, float value) {
        doEdit();
        editor.putFloat(_context.getString(key), value);
        doCommit();
    }

    public void putLong(@StringRes int key, long value) {
        doEdit();
        editor.putLong(_context.getString(key), value);
        doCommit();
    }

    public int getInt(@StringRes int key) {
        try {
            return pref.getInt(_context.getString(key), (int) getDefaultValue(key));
        } catch (Exception e) {
            return pref.getInt(_context.getString(key), -1);
        }
    }

    public String getString(@StringRes int key) {
        return pref.getString(_context.getString(key), (String) getDefaultValue(key));
    }

    public boolean getBoolean(@StringRes int key) {
        return pref.getBoolean(_context.getString(key), (boolean) getDefaultValue(key));
    }

    public float getFloat(@StringRes int key) {
        return pref.getFloat(_context.getString(key), (float) getDefaultValue(key));
    }

    public long getLong(@StringRes int key) {
        return pref.getLong(_context.getString(key), (long) getDefaultValue(key));
    }

    public void edit() {
        mBulkUpdate = true;
        editor = pref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        editor.commit();
        editor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && editor == null) {
            editor = pref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && editor != null) {
            editor.commit();
            editor = null;
        }
    }


}
