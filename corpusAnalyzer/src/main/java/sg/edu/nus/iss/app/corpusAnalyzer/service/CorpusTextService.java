package sg.edu.nus.iss.app.corpusAnalyzer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CorpusTextService {
    private Map<String, Integer> wordCounts;
    private List<String> combiWords;
    
    public CorpusTextService() {
        wordCounts = new HashMap<>();
        combiWords = new LinkedList<>();
    }

    private String[] getCleanText(String text){
        String cleanedText = Arrays.stream(text.split("\\s+"))
                            .map(word -> word.replaceAll("[!.,':;\\-\"?]+", ""))
                           .filter(word -> !word.isEmpty())
                           .collect(Collectors.joining(" "));
        System.out.println("cleanedText > " + cleanedText);
        String[] words = cleanedText.split("\\s+");
        return words;
    }

    public List<String> getListOfCurrentNextWord(){
        return combiWords;
    }

    public void analyze(String text){
        String[] words = this.getCleanText(text);
        for (int i = 0; i < words.length; i++) {
            try{
                String currentWord = words[i] + " " + words[i+1];
                combiWords.add(currentWord);
                wordCounts.put(currentWord, wordCounts.getOrDefault(currentWord, 0) + 1);
            }catch(ArrayIndexOutOfBoundsException e){
                System.err.println(e.getMessage());
            }
            
        }
    }

    public int getWordCount(String word) {
        return wordCounts.getOrDefault(word, 0);
    }
}
