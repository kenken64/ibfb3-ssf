package sg.edu.nus.iss.app.corpusAnalyzer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.corpusAnalyzer.service.CorpusTextService;

@Controller
@RequestMapping(path="/analyze")
public class CorpusTextController {
    
    @Autowired
    private CorpusTextService cSvc;

    @GetMapping()
    public String analyze(Model model, @RequestParam String para){
        System.out.println(para);
        String wordForDistribution = "If you";
        cSvc.analyze(para);
        Map<String, Double> distribution = cSvc.getNextWordDistribution(wordForDistribution);
        System.out.println("The word \"" + wordForDistribution + "\" is followed by the following words with the following probabilities:");
        for (String nextWord : distribution.keySet()) {
           double probability = distribution.get(nextWord);
           System.out.println("- \"" + nextWord + "\": " + probability);
        }
        return "result";
    }

}
