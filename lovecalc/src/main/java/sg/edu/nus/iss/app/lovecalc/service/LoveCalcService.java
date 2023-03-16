package sg.edu.nus.iss.app.lovecalc.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${lovecalc.api.url}")
    private String loveCalcApiUrl;
    
    @Value("${lovecalc.api.key}")
    private String loveCalcApiKey;
    
    @Value("${lovecalc.api.host}")
    private String loveCalcApiHost;
    

    @Autowired @Qualifier("lovecalc")
    RedisTemplate<String, Object> redisTemplate;

    public Optional<LoverResult> calcCompatibility(LoverResult rr)
            throws IOException {
        String finalLoveCalculatorUrl = UriComponentsBuilder
                .fromUriString(loveCalcApiUrl)
                .queryParam("fname", rr.getFname())
                .queryParam("sname", rr.getSname())
                .toUriString();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        HttpHeaders headers = new HttpHeaders();
        
        headers.set("X-RapidAPI-Key", loveCalcApiKey);
        headers.set("X-RapidAPI-Host", loveCalcApiHost);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        resp = template.exchange(finalLoveCalculatorUrl, HttpMethod.GET,
                requestEntity, String.class);
        LoverResult w = LoverResult.create(resp.getBody());
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
