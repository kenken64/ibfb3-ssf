package sg.edu.nus.iss.app.workshop14.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.workshop14.model.Contact;

@Repository
public class AddressbookRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String CONTACT_LIST = "contactlist";

    public Contact save(final Contact ctc){
        System.out.println(ctc.getDateOfBirth());
        redisTemplate.opsForList()
                .leftPush(CONTACT_LIST, ctc.getId());
        redisTemplate.opsForHash()
            .put(CONTACT_LIST + "_Map", ctc.getId(), ctc);
        return findById(ctc.getId());
    }

    public Contact findById(final String contactId){
        Contact result = (Contact) redisTemplate.opsForHash()
            .get(CONTACT_LIST + "_Map",
            contactId);
        return result;
    }

    public List<Contact> findAll(int startIndex){
        List<Object> fromContactList = redisTemplate.opsForList()
            .range(CONTACT_LIST, startIndex, 10);

        List<Contact> ctcs = redisTemplate.opsForHash()
            .multiGet(CONTACT_LIST + "_Map", fromContactList)
            .stream()
            .filter(Contact.class::isInstance)
            .map(Contact.class::cast)
            .collect(Collectors.toList());

        return ctcs;
    }
}
