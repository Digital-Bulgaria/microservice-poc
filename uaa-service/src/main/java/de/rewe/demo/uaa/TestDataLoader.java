package de.rewe.demo.uaa;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import de.rewe.demo.uaa.domain.User;
import de.rewe.demo.uaa.repository.UserRepository;

/**
 * Loads some test data in the user repository.
 */
@Component
public class TestDataLoader  implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public TestDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        userRepository.save(Arrays.asList(user1()));
    }
    
    private User user1()
    {
    	User user1 = new User();
    	
    	user1.setName("buyer");
    	user1.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
    	user1.setAccountLocked(false);
    	//user1.setPermissions(Arrays.asList("ROLE_READ_BOOK"));
    	
    	return user1;
    }
}
