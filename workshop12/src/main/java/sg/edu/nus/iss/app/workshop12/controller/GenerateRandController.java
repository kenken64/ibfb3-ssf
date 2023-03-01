package sg.edu.nus.iss.app.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.workshop12.exception.RandNoException;
import sg.edu.nus.iss.app.workshop12.model.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenerateRandController {
    
    @GetMapping(path="/show")
    public String showRandForm(Model m){
        Generate g = new Generate();
        m.addAttribute("generateObj", g);
        return "generate";
    }

    @GetMapping(path="/generate")
    public String generate(@RequestParam Integer numberVal, Model m){
        this.randomizerNum(m, numberVal.intValue());
        return "result";
    }

    @GetMapping(path="/generate/{numberVal}")
    public String generateRandByPathVar(@PathVariable Integer numberVal, Model m){
        this.randomizerNum(m, numberVal.intValue());
        return "result";
    }

    private void randomizerNum(Model m, int noOfGenerateNo){
        int maxGenNo = 31;
        String[] imgNumbers = new String[maxGenNo];
        if(noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo-1){
            throw new RandNoException();
        }

        for(int x = 0; x < maxGenNo; x++){
            imgNumbers[x] = "number" + x + ".jpg";
        }
        
        List<String> selectedImg = new ArrayList<String>();
        Random rand = new Random();
        Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
        while(uniqueResult.size() < noOfGenerateNo){
            Integer randNumberVal = rand.nextInt(maxGenNo);
            if(randNumberVal != null)
                uniqueResult.add(randNumberVal);
        }

        Integer currElem = null;
        for (Iterator iter = uniqueResult.iterator(); iter.hasNext();){
            currElem = (Integer)iter.next();
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }
        
        m.addAttribute("numberRandomNum", noOfGenerateNo);
        m.addAttribute("randNumResult", selectedImg.toArray());
        

    }
}
