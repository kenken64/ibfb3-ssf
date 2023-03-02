package sg.edu.nus.iss.app.corpusAnalyzer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CorpusTextService {
    private Map<String, Integer> wordCounts;
    private Map<String, List<String>> nextWords;
    
    public CorpusTextService() {
        wordCounts = new HashMap<>();
        nextWords = new HashMap<>();
    }

    public void analyze(String text){
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i];
            if (i < words.length - 1) {
                String nextWord = words[i+1];
                if (!nextWords.containsKey(currentWord)) {
                    nextWords.put(currentWord, new ArrayList<>());
                }
                nextWords.get(currentWord).add(nextWord);
            }
            wordCounts.put(currentWord, wordCounts.getOrDefault(currentWord, 0) + 1);
        }
    }

    public int getWordCount(String word) {
        return wordCounts.getOrDefault(word, 0);
    }

    public List<String> getNextWords(String word) {
        return nextWords.getOrDefault(word, new ArrayList<>());
    }

    public Map<String, Double> getNextWordDistribution(String word) {
        Map<String, Double> distribution = new HashMap<>();
        List<String> possibleNextWords = getNextWords(word);
        int totalPossibleNextWords = possibleNextWords.size();
        if (totalPossibleNextWords == 0) {
            return distribution;
        }
        for (String nextWord : possibleNextWords) {
            int nextWordCount = getWordCount(nextWord);
            double probability = (double) nextWordCount / totalPossibleNextWords;
            distribution.put(nextWord, probability);
        }
        return distribution;
    }
}
