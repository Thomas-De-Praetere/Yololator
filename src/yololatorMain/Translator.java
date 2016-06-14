/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yololatorMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas De Praetere
 */
public class Translator {

    private Map<String, String[]> wordMap;
    private Map<Character, Character[]> charMap;
    private Map<String, Integer> amountMap;
    private List<Character> charList = new ArrayList<>();
    private Random rd = new Random();

    public Translator() {
        try {
            charList.addAll(Arrays.asList(new Character[]{'.', ',', ':', '!', '?', ' '}));
            this.initWordMap();
            this.initCharMap();
            this.initAmountMap();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String translate(String text) {
        String[] textArray = split(text);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < textArray.length; i++) {
            textArray[i] = this.changeWord(textArray[i]);
            textArray[i] = this.changeLetter(textArray[i]);
            textArray[i] = this.changeExtend(textArray[i]);
        }
        for (String s : textArray) {
            builder.append(s);
        }
        return builder.toString();
    }

    private String[] split(String text) {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (Character c : text.toCharArray()) {
            if (!charList.contains(c)) {
                builder.append(c);
            } else {
                list.add(builder.toString());
                builder = new StringBuilder();
                list.add("" + c);
            }
        }
        String[] arr = new String[list.size()];
        int i = 0;
        for(String s : list){
            arr[i] = s;
            i++;
        }
        
        return arr;
    }

    private String changeWord(String string) {
        if (this.wordMap.containsKey(string)) {
            String[] get = this.wordMap.get(string);
            int i = rd.nextInt(get.length);
            return get[i];
        } else {
            return string;
        }
    }

    private String changeLetter(String string) {
        StringBuilder b = new StringBuilder();
        char[] charr = string.toLowerCase().toCharArray();
        for (char c : charr) {
            if (this.charMap.containsKey(c)) {
                Character[] get = this.charMap.get(c);
                int i = rd.nextInt(get.length);
                b.append(get[i]);
            } else {
                b.append(c);
            }
        }
        return b.toString();
    }

    private String changeExtend(String string) {
        StringBuilder b = new StringBuilder();
        b.append(string);
        if (this.amountMap.containsKey(string)) {
            Integer get = this.amountMap.get(string);
            int i = rd.nextInt(get);
            for (int j = 0; j < i; j++) {
                b.append(string);
            }
        } else {
            return string;
        }
        return b.toString();
    }

    private void initWordMap() throws FileNotFoundException, IOException, URISyntaxException {
        this.wordMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(new File("swagChange.txt")));
        String readLine = br.readLine();
        while (readLine != null) {
            String[] split = readLine.split(" ");
            String[] arr = new String[split.length - 1];
            for (int i = 1; i < split.length; i++) {
                arr[i - 1] = split[i];
            }
            wordMap.put(split[0], arr);
            readLine = br.readLine();
        }
        br.close();
    }

    private void initCharMap() throws FileNotFoundException, IOException, URISyntaxException {
        this.charMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(new File("swagtionary.txt")));
        String readLine = br.readLine();
        while (readLine != null) {
            String[] split = readLine.split(" ");
            Character[] arr = new Character[split.length - 1];
            for (int i = 1; i < split.length; i++) {
                arr[i - 1] = split[i].toCharArray()[0];
            }
            charMap.put((Character)split[0].toCharArray()[0],arr);
            readLine = br.readLine();
        }
        br.close();
    }

    private void initAmountMap() throws FileNotFoundException, IOException, URISyntaxException {
        this.amountMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(new File("swagAmount.txt")));
        String readLine = br.readLine();
        while (readLine != null) {
            String[] split = readLine.split(" ");
            this.amountMap.put(split[0], Integer.parseInt(split[1]));
            readLine = br.readLine();
        }
        br.close();
    }
}
