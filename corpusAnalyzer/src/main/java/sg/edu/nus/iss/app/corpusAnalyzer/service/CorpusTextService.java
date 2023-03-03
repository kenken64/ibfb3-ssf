package sg.edu.nus.iss.app.corpusAnalyzer.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.corpusAnalyzer.model.Corpus;
import sg.edu.nus.iss.app.corpusAnalyzer.repository.CorpusRepository;

@Service
public class CorpusTextService {
    @Autowired
    private CorpusRepository cRepo;

    private Map<String, Integer> wordCounts;
    private Set<String> combiWords;
    
    private String[] getCleanText(String text){
        String cleanedText = Arrays.stream(text.split("\\s+"))
                            .map(word -> word.replaceAll("[!.,':;\\-\"?]+", ""))
                           .filter(word -> !word.isEmpty())
                           .collect(Collectors.joining(" "));
        System.out.println("cleanedText > " + cleanedText);
        String[] words = cleanedText.split("\\s+");
        return words;
    }

    public Map<String, List<Corpus>> getListOfCurrentNextWord(){
        List<Corpus> ca = new LinkedList<>();
        Map<String, List<Corpus>> displayResult = new HashMap<>();
        
        for(String wordToCount : combiWords){
            int count = getWordCount(wordToCount);
            System.out.println("The word \"" + wordToCount + "\" appears " 
                    + count + " times in the corpus.");
            String[] splitWords = wordToCount.split(" ");
            Corpus c = new Corpus();
            c.setWord(splitWords[0]);
            c.setNextWord(splitWords[1]);
            c.setCount(count);
            ca.add(c);
        }
        for(Corpus c : ca){
            if(!displayResult.containsKey(c.getWord())){
                Corpus cc = new Corpus();
                cc.setNextWord(c.getNextWord());
                cc.setCount(c.getCount());
                List<Corpus> arrMap = new LinkedList<>();
                arrMap.add(cc);
                displayResult.put(c.getWord(), arrMap);
            }else{
                Corpus cc = new Corpus();
                cc.setNextWord(c.getNextWord());
                cc.setCount(c.getCount());
                List<Corpus> mr = displayResult.get(c.getWord());
                mr.add(cc);
                displayResult.put(c.getWord(),mr);
            }
            
        }
        System.out.println(displayResult);
        cRepo.saveCorpusResult(ca);
        return displayResult;
    }

    public void analyze(String text){
        wordCounts = new HashMap<>();
        combiWords = new LinkedHashSet<>();
        String[] words = this.getCleanText(text);
        for (int i = 0; i < words.length; i++) {
            try{
                String currentWord = words[i].toLowerCase() + " " + words[i+1].toLowerCase();
                String trimmedCurrentWord = currentWord.trim();
                combiWords.add(trimmedCurrentWord);
                wordCounts.put(trimmedCurrentWord, wordCounts.getOrDefault(trimmedCurrentWord, 0) + 1);
            }catch(ArrayIndexOutOfBoundsException e){
                System.err.println(e.getMessage());
            }
            
        }
    }

    public int getWordCount(String word) {
        return wordCounts.getOrDefault(word, 0);
    }
}
