package com.example.common.utils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

    public static List<Integer> generateRandomInt(){
        return new Random().ints( 3 ).boxed().collect( Collectors.toList() );
    }
}