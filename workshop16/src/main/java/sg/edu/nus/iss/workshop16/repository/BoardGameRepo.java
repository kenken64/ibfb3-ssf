package sg.edu.nus.iss.workshop16.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop16.model.Mastermind;

@Repository
public class BoardGameRepo {
    @Autowired RedisTemplate<String, String> template;

    public int saveGame(final Mastermind md){
        template.opsForValue()
            .set(md.getId(), md.toJSON().toString());
        String result= (String) template
            .opsForValue().get(md.getId());
        if(result != null){
            return 1;
        }
        return 0;
    }

    public Mastermind findById(final String msId) 
        throws IOException{
        String jsonStrVal = (String)template
                                .opsForValue()
                                .get(msId);
        Mastermind m = Mastermind.create(jsonStrVal);
        return m;
    }

    public int updateBoardGame(final Mastermind ms){
        String result = (String)template
                    .opsForValue().get(ms.getId());
        if(ms.isUpSert()){
            if(result != null){
                template.opsForValue().set(ms.getId()
                                , ms.toJSON().toString());
            }else{
                ms.setId(ms.generateId(8));
                template.opsForValue()
                            .setIfAbsent(ms.getId()
                            ,ms.toJSON().toString());
            }
                
        }else{
            if(result != null){
                template.opsForValue().set(ms.getId()
                                , ms.toJSON().toString());
            }
        }

        result = (String)template.opsForValue().get(ms.getId());
        if(result != null)
            return 1;
        return 0;
    }
}
