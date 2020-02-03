package com.example.github.db;

import androidx.room.TypeConverter;
import androidx.room.util.StringUtil;

import java.util.Collections;
import java.util.List;

public class GitHubTypeConverter {

    @TypeConverter
    public static List<Integer> stringToIntList(String data){
        if (data == null){
            return Collections.emptyList();
        }
        return StringUtil.splitToIntList(data);
    }

    @TypeConverter
    public static String intListToString(List<Integer> ints){
        return StringUtil.joinIntoString(ints);
    }
}
