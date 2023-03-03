package sg.edu.nus.iss.app.corpusAnalyzer.controller;

import java.util.List;

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
        cSvc.analyze(para);
        List<Corpus> ll  = cSvc.getListOfCurrentNextWord();
        model.addAttribute("wordcountList", ll);
        return "result";
    }

}
