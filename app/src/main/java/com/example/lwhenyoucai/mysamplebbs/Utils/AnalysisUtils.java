package com.example.lwhenyoucai.mysamplebbs.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */

public class AnalysisUtils {

    //字符串集合类型的 json集合解析
    public static List<String> analysisStringList(String stringJson) {
        List<String> stringList = new ArrayList<>();
        try {
            if (!"".equals(stringJson) && stringJson != null) {
                JsonArray jsonArray = new JsonParser().parse(stringJson).getAsJsonArray();
                Gson gson = new Gson();
                for (JsonElement element : jsonArray) {
                    String str = gson.fromJson(element, String.class);
                    stringList.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
