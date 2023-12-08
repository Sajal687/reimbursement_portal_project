package com.project.reimburse.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.reimburse.entities.Designation;
import com.project.reimburse.entities.User;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepoTests {
	@Autowired
	private UserRepo userRepository;

	@Test
	public void testFindByEmailAndPassword() {
		User user = new User(123, "Sajal Nema", Designation.Admin, "sajal@nucleusTeq.com", new Date(), 0, "Sajal@123",
				"This is my Secret Answer", null, null);

		User createdUser = userRepository.save(user);
		User fetchLogInUser = userRepository.findByEmailAndPassword(createdUser.getEmail(), createdUser.getPassword());

		assertThat(fetchLogInUser).isNotNull();
		assertThat(fetchLogInUser.getName()).isEqualTo(user.getName());
	}
}
