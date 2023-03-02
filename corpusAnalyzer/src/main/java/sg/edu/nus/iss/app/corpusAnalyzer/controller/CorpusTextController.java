package sg.edu.nus.iss.app.corpusAnalyzer.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.corpusAnalyzer.model.Corpus;
import sg.edu.nus.iss.app.corpusAnalyzer.service.CorpusTextService;

@Controller
@RequestMapping(path="/analyze")
public class CorpusTextController {
    
    @Autowired
    private CorpusTextService cSvc;

    @GetMapping()
    public String analyze(Model model, @RequestParam(defaultValue="") String para){
        List<Corpus> ca = new LinkedList<>();
        cSvc.analyze(para);
        List<String> ll  = cSvc.getListOfCurrentNextWord();
        for(String wordToCount : ll){
            int count = cSvc.getWordCount(wordToCount);
            System.out.println("The word \"" + wordToCount + "\" appears " 
                    + count + " times in the corpus.");
            String[] splitWords = wordToCount.split(" ");
            Corpus c = new Corpus();
            c.setWord(splitWords[0]);
            c.setNextWord(splitWords[1]);
            c.setCount(count);
            ca.add(c);
        }
        model.addAttribute("wordcount", ca);
        return "result";
    }

}
