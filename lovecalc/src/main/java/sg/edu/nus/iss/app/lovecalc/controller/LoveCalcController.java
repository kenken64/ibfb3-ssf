package sg.edu.nus.iss.app.lovecalc.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.lovecalc.model.LoverResult;
import sg.edu.nus.iss.app.lovecalc.service.LoveCalcService;

@Controller
@RequestMapping(path = "/lovecalc")
public class LoveCalcController {

    @Autowired
    private LoveCalcService lvSvc;

    @GetMapping
    public String calcCompatibility(@RequestParam(required = true) String fname,
            @RequestParam(required = true) String sname,
            Model model)
            throws IOException {
        LoverResult rr = new LoverResult(fname, sname);
        Optional<LoverResult> r = this.lvSvc.calcCompatibility(rr);
        model.addAttribute("result", r.get());
        return "result";
    }

    @GetMapping(path = "/list")
    public String getAllLoveCompat(Model model) throws IOException {
        LoverResult[] mArr = lvSvc.getAllMatchMaking();
        Arrays.sort(mArr);
        model.addAttribute("arr", mArr);
        return "list";
    }
}
