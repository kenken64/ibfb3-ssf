package sg.edu.nus.iss.app.workshop14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.nus.iss.app.workshop14.model.Contact;
import sg.edu.nus.iss.app.workshop14.repository.AddressbookRepository;

@SpringBootTest
class Workshop14ApplicationTests {

	@Autowired
	private AddressbookRepository adrepo;
	
	private String contactId="edc75f42";

	@Test
	void contextLoads() {
	}

	@Test
	void testAddressbookserviceSave(){
		Contact c = new Contact();
		c.setName("Kenneth");
		c.setDateOfBirth(LocalDate.now());
		c.setEmail("k@k.com");
		c.setPhoneNumber("12345678");	
		Contact insertedContact = adrepo.save(c);
		assertNotNull(insertedContact);
		assertNotEquals("", insertedContact.getId());
		System.out.println(insertedContact.getId());
	}

	@Test
	void testAddressbookserviceGetContactById(){
		Contact insertedContact = adrepo.findById(contactId);
		assertNotNull(insertedContact);
		assertEquals(this.contactId, insertedContact.getId());
	}

	@Test
	void testAddressbookServiceGetAllContacts(){
		List <Contact> allCtcs = adrepo.findAll(0);
		assertTrue(!allCtcs.isEmpty());
	}

}
