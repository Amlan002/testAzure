package com.info.people.management.app.repo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.info.people.management.app.entity.Person;

@Transactional
public interface PeoplemanagementRepo extends JpaRepository<Person, Integer> {

	Person findByFirstName(String name);

	List<Person> findByOrderByLastName();

	List<Person> findByFirstNameOrderByLastName(String name);

	List<Person> findByLastName(String name);

	List<Person> findByFirstName(String name, Sort by);

	List<Person> findByFirstNameAndEmail(String name, String email);

	// named query--> check entity class(but we can create only using findByEmail)
	Person findByemail(String email);

	// named query--> check entity class(but we can create only using
	// findByIdAndEmail)
	Person findByIdAndFirstName(int id, String name);

	@Query(value = "select u from Person u where u.firstName=?1")
	List<Person> findByLastName_Query(String name);

	@Query(value = "SELECT * FROM person_table WHERE first_name=?1", nativeQuery = true)
	List<Person> findByLastName_NativeQuery(String name);

	
	
	//Async
	@Async
	CompletableFuture<Person> findByEmail(String email);

	//use this or bellow
	//List<Person> findByFirstNameOrLastName(String fname, String lname);

	@Query("SELECT p FROM Person p WHERE p.firstName=:fName or p.lastName=:lName")
	List<Person> findByFirstNameOrLastName(@Param("fName") String fname,@Param("lName") String lname);

	
	@Modifying
	@Query("UPDATE Person set email=:email WHERE id=:id")
	void updateEmailById(@Param("id") int id,@Param("email") String email);
}
