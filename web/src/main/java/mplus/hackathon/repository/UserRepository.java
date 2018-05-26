package mplus.hackathon.repository;

import mplus.hackathon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByName(String name);
}
