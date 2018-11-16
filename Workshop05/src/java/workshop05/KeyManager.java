
package workshop05;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KeyManager {
    
   
    private Key key;
    @PostConstruct
    
    private void init() {
        //initilaise or load the key from file
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println("KeyManager");
    }
    public Key getkey() {
        return (key);
    }
    
    
}
