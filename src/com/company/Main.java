package com.company;
/* Classame : FinalTest
 *
 * Date: 23.06.2020
 * @author: Vitaliy
 * @version: 1.1
 * */
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {


        // create list with harry text. use regEx
        List<String> text = new ArrayList<>();
        Files.lines(Paths.get("C:\\Users\\Vitaliy\\Desktop\\harry.txt"))
                .forEach(string ->{
                    string = string.replaceAll("[^a-zA-Z0-9' ]" ,"");
                    String[] words = string.split(" ");
                    for (String word: words){
                        text.add(word);
                    }
                });

        // search distinct and number of occurrence.
        Map<String, Integer> distincts = new LinkedHashMap<>();
        text.stream().forEach(item ->{
            if(!distincts.containsKey(item)){
                distincts.put(item,1);
            }else{
                int value = distincts.get(item);
                distincts.put(item, value+1);
            }
        });

        // sorting
        Map<String , Integer> sorted = distincts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));



        // find the first 20 pairs.
        List<String> keys = new ArrayList<>();
        Map<String, Integer> top20Words = new LinkedHashMap<>();
        sorted.keySet().stream().limit(20).forEach(key-> keys.add(key));

        // list of proper names
        Map<String, Integer> properName  = new LinkedHashMap<>();
        distincts.entrySet().stream().forEach(word->{
            String example = word.getKey();
            String exampleLowCase = example.toLowerCase();
            if(example.length()>0 && example.charAt(0) != exampleLowCase.charAt(0)){
                if(!distincts.containsKey(exampleLowCase)){
                    properName.put(word.getKey(),word.getValue());
                }
            }
        });

        FileWriter nFile = new FileWriter("F:\\HarryPotterFinall\\src\\com\\company\\harryoutput.txt");

        String fileHeader = " -----HARRY POTTER TEST-----";


        nFile.write(fileHeader);

        for (Map.Entry<String, Integer> entry : top20Words.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            nFile.write(key + " - " + value +"\n");
        }

        for (Map.Entry<String, Integer> entry : properName.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            nFile.write(key + " - " + value + "\n");
        }

        nFile.close();
    }
}