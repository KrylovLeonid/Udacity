package com.silvershadow.sandwichclub.utils;

import com.silvershadow.sandwichclub.Model.Sandwich;

import java.util.Arrays;
import java.util.List;

public class JsonUtils {
    public static final int INDENT = 1;

    public static Sandwich parseSandwichJson(String json) {


        String mainName = parseString(json,"\"mainName\":");
        List<String> alsoKnownAs = parseArrayOfStrings(json, "\"alsoKnownAs\":");
        String origin = parseString(json,"\"placeOfOrigin\":");
        String description = parseString(json,"\"description\":");;
        String image = parseString(json,"\"image\":");
        List<String> ingredients = parseArrayOfStrings(json, "\"ingredients\":");;




        return new Sandwich(mainName,alsoKnownAs, origin, description, image,ingredients);
    }

    private static String parseString(String json, String request){
        int startIndex = json.indexOf(request) + request.length() + INDENT;
        int lastIndex = json.indexOf("\",\"",startIndex );
        return json.substring(startIndex,lastIndex);
    }

    private static List<String> parseArrayOfStrings(String json, String request) {
        int startIndex = json.indexOf(request) + request.length() + INDENT;
        int lastIndex = json.indexOf("]", startIndex);
        List<String> result = Arrays.asList((json.substring(startIndex, lastIndex).replaceAll("\"", "").split(",")));
        return result;
    }

}
