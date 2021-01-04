package com.megthinksolutions.apps.hived.database;


import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class Converters {

//    @TypeConverter
//    public static String listToString(List<String> list) {
//        return Utilities.joinStringsWithSeparator(list, ";");
//    }

    @TypeConverter
    public static List<String> stringToList(String str) {
        String[] arr = str.split(";");
        return Arrays.asList(arr);
    }
}
