package pl.fibinger.versionchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fibinger.versionchecker.dao.UserRepository;
import pl.fibinger.versionchecker.domain.User;
import pl.fibinger.versionchecker.representation.UserRepresentation;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String userName) {
        return userRepository.findByName(userName);
    }

    public UserRepresentation addUser(String userName) {
        User user = userRepository.save(new User(userName));
        return new UserRepresentation().setId(user.getId()).setName(user.getName());
    }
}
