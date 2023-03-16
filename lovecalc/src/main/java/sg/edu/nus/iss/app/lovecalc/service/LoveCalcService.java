package sg.edu.nus.iss.app.lovecalc.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.redis.core.RedisTemplate;

import sg.edu.nus.iss.app.lovecalc.model.LoverResult;

@Service
public class LoveCalcService {
    private static final String LOVE_CALC_API_URL = "https://love-calculator.p.rapidapi.com/getPercentage";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public Optional<LoverResult> calcCompatibility(LoverResult rr)
            throws IOException {
        String finalLoveCalculatorUrl = UriComponentsBuilder
                .fromUriString(LOVE_CALC_API_URL)
                .queryParam("fname", rr.getFname())
                .queryParam("sname", rr.getSname())
                .toUriString();
        System.out.println(finalLoveCalculatorUrl);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        HttpHeaders headers = new HttpHeaders();
        String loverApiKey = System.getenv("LOVER_API_KEY");
        String loverApiHost = System.getenv("LOVER_API_HOST");

        headers.set("X-RapidAPI-Key", loverApiKey);
        headers.set("X-RapidAPI-Host", loverApiHost);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        resp = template.exchange(finalLoveCalculatorUrl, HttpMethod.GET,
                requestEntity, String.class);
        System.out.println(resp);
        LoverResult w = LoverResult.create(resp.getBody());
        System.out.println(w);
        if (w != null) {
            redisTemplate.opsForValue().set(w.getId(), resp.getBody().toString());
            return Optional.of(w);
        }

        return Optional.empty();
    }

    public LoverResult[] getAllMatchMaking() throws IOException {
        Set<String> allMatchMakingdKeys = redisTemplate.keys("*");
        List<LoverResult> mArr = new LinkedList<LoverResult>();
        for (String matchMakeKey : allMatchMakingdKeys) {
            String result = (String) redisTemplate.opsForValue().get(matchMakeKey);

            mArr.add((LoverResult) LoverResult.create(result));
        }

        return mArr.toArray(new LoverResult[mArr.size()]);
    }

}
