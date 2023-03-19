package sg.edu.nus.iss.revision2.repository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.revision2.model.Item;

@Repository
public class QuotationRepository {
    

    @Autowired @Qualifier("quotation")
    RedisTemplate<String, String> redisTemplate;

    public void saveGame(final Item i){
        JsonObject j = i.toJSON().build();
        redisTemplate.opsForValue()
            .set(i.getId(), j.toString());
    }

    public Item[] getQuotations() throws IOException {
        Set<String> allQuotation = redisTemplate.keys("*");
        List<Item> mArr = new LinkedList<Item>();
        for (String key : allQuotation) {
            String result = (String) redisTemplate.opsForValue().get(key);

            mArr.add((Item) Item.create(result));
        }
        return mArr.toArray(new Item[mArr.size()]);
    }
}
