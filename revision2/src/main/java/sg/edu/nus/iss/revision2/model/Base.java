package sg.edu.nus.iss.revision2.model;

import java.io.Serializable;
import java.security.SecureRandom;

public class Base implements Serializable{
    public synchronized String generateId(int maxChars){
        SecureRandom sr= new SecureRandom();
        StringBuilder strbuilder = new StringBuilder();
        while(strbuilder.length() < maxChars){
            strbuilder.append(Integer.toHexString(sr.nextInt()));
        }

        return strbuilder.toString().substring(0, maxChars);
    }
}
