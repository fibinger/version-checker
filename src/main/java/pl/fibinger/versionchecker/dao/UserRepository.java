package pl.fibinger.versionchecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fibinger.versionchecker.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

}
