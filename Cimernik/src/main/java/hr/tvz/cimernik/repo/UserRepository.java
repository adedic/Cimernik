package hr.tvz.cimernik.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.tvz.cimernik.model.RoomateGroup;
import hr.tvz.cimernik.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findOneByUsername(String username);
	List<User> findAllByRoomateGroup(RoomateGroup rg);
	Integer countByUsername(String username);

}
