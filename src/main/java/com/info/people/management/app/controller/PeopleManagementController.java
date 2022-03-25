package com.info.people.management.app.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.people.management.app.entity.Person;
import com.info.people.management.app.repo.PeoplemanagementRepo;
import com.info.people.management.app.service.PeopleManagementService;

@RestController
@RequestMapping("/api")
@EnableAsync
public class PeopleManagementController {

	@Autowired
	private PeopleManagementService peopleManagementService;

	@Autowired
	private PeoplemanagementRepo repo;

	@PostMapping(path = "/saveListPerson")
	private void createPersons() {

		System.out.println("===============saving list of person");
		List<Person> personList = Arrays.asList(new Person("Barry", "Johnson", "barry.j2019@Gmail.com", new Date()),
				new Person("Kishan", "Kumar", "kishan.cs2019@yahoo.com", new Date()));

		Iterable<Person> createPersons = repo.saveAll(personList);
		createPersons.forEach(x -> System.out.println(x.getFirstName()));

		/*
		 * Iterable<Person> createPersons =
		 * peopleManagementService.createPersons(personList); for (Person person :
		 * createPersons) { System.out.println(person); }
		 */

	}

	@PostMapping(path = "/savePerson")
	private void createPerson() {
		System.out.println("===============saving one person 1");
		Person person1 = new Person("Sean", "Muphy", "sean.m2017@gamil.com", new Date());

		Person personDb = repo.save(person1);

		// Person personDb = peopleManagementService.createPerson1(person1);
		System.out.println(personDb);
	}

	@PostMapping(path = "/save")
	private void save(@RequestBody Person person) {
		System.out.println("===============saving one person 2");

		Person personDb = repo.save(person);
		System.out.println(personDb);
	}

	@GetMapping(path = "/getByName/{name}")
	private Person getByName(@PathVariable String name) {
		System.out.println("===============getByName");

		Person personDb = repo.findByFirstName(name);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/getByEmail-namedQuery/{email}")
	private Person getByemail(@PathVariable String email) {
		System.out.println("===============getByEmail-namedQuery");

		Person personDb = repo.findByemail(email);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/getByIdFirstname-namedQuery/{id}/{name}")
	private Person arrestByIdAndFirstName(@PathVariable int id, @PathVariable String name) {
		System.out.println("===============getByIdFirstname-namedQuery");

		Person personDb = repo.findByIdAndFirstName(id, name);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/getByFirstNameEmail/{name}/{email}")
	private List<Person> FirstNameEmail(@PathVariable String name, @PathVariable String email) {
		System.out.println("===============getByFirstNameEmail");

		List<Person> personDb = repo.findByFirstNameAndEmail(name, email);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/getById/{id}")
	private Person getById(@PathVariable int id) {
		System.out.println("===============getById");

		Optional<Person> personDb = repo.findById(id);
		System.out.println(personDb.get());
		return personDb.get();
		/*
		 * Person personDb = repo.getById(id); System.out.println(personDb); return
		 * personDb;
		 */
	}

	@GetMapping(path = "/getByIds-Array")
	private List<Person> getByIds() {
		System.out.println("===============getByIds-Array");

		List<Integer> ids = repo.findAll().stream().map(X -> X.getId()).toList();
		ids.forEach(i -> System.out.println(i));

		List<Person> personDb = repo.findAllById(ids);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/orderByLastName")
	private List<Person> orderBy() {
		System.out.println("===============orderByLastName");
		List<Person> personDb = repo.findByOrderByLastName();
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/findByFirstNameorderByLastName/{name}")
	private List<Person> findByFirstNameorderByLastName(@PathVariable String name) {
		System.out.println("===============findByFirstNameorderByLastName");
		List<Person> personDb = repo.findByFirstNameOrderByLastName(name);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/ascSortByLastName")
	private List<Person> ascSortByLastName() {
		System.out.println("===============orderByLastNameAsc");
		List<Person> personDb = repo.findAll(Sort.by(Sort.Direction.ASC, "lastName"));
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/dscSortByLastName")
	private List<Person> dscSortByLastName() {
		System.out.println("===============orderByLastNameDsc");
		List<Person> personDb = repo.findAll(Sort.by(Sort.Direction.DESC, "lastName"));
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/findByFirstNameSortByLastName/{name}")
	private List<Person> findByFirstNameSortByLastName(@PathVariable String name) {
		System.out.println("===============findByFirstNameSortByLastName");
		List<Person> personDb = repo.findByFirstName(name, Sort.by(Sort.Direction.DESC, "lastName"));
		System.out.println(personDb);
		return personDb;
	}

	@DeleteMapping(path = "/deleteByEntityId/{id}")
	private void deleteByEntity(@PathVariable int id) {
		System.out.println("===============deleteByEntity");
		Optional<Person> person = repo.findById(id);
		repo.delete(person.get());
	}

	@DeleteMapping(path = "/deleteById/{id}")
	private void deleteById(@PathVariable int id) {
		System.out.println("===============deleteById");
		repo.deleteById(id);
	}

	@DeleteMapping(path = "/deleteByIds-Array")
	private void deleteByIdsArray() {
		System.out.println("===============deleteByEntityIds-Array");
		List<Integer> ids = repo.findAll().stream().map(X -> X.getId()).toList();
		repo.deleteAllById(ids);
	}

	@PutMapping(path = "/update1/{id}")
	private void update1(@RequestBody Person person, @PathVariable int id) {
		System.out.println("===============update 1 using body and pathvariable as id");

		Optional<Person> personDb = repo.findById(id);
		if (personDb.isPresent()) {
			personDb.get().setId(id);
			personDb.get().setEmail(person.getEmail());
			personDb.get().setFirstName(person.getFirstName());
			personDb.get().setLastName(person.getLastName());
			personDb.get().setCreationDate(new Date());
			repo.save(personDb.get());
			System.out.println(personDb);
		} else {
			System.out.println("========person not found==============");
		}

	}

	@PutMapping(path = "/update2")
	private void update2(@RequestBody Person person) {
		System.out.println("===============update 1 using body");

		Optional<Person> personDb = repo.findById(person.getId());
		if (personDb.isPresent()) {
			personDb.get().setId(person.getId());
			personDb.get().setEmail(person.getEmail());
			personDb.get().setFirstName(person.getFirstName());
			personDb.get().setLastName(person.getLastName());
			personDb.get().setCreationDate(new Date());
			repo.save(personDb.get());
			System.out.println(personDb);
		} else {
			System.out.println("========person not found==============");
		}

	}

	@GetMapping(path = "/getByName-UsingQuery/{name}")
	private List<Person> getByNameUsingQuery(@PathVariable String name) {
		System.out.println("===============getByName-UsingQuery");

		List<Person> personDb = repo.findByLastName_Query(name);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/getByName-NativeQuery/{name}")
	private List<Person> getByNameNativeQuery(@PathVariable String name) {
		System.out.println("===============getByName-NativeQuery");

		List<Person> personDb = repo.findByLastName_NativeQuery(name);
		System.out.println(personDb);
		return personDb;
	}

	@GetMapping(path = "/getByEmailAsync/{email}")
	private Person getByEmail(@PathVariable String email)
			throws InterruptedException, ExecutionException, TimeoutException {
		System.out.println("===============getByEmailAsync");

		CompletableFuture<Person> personDb = repo.findByEmail(email);
		Person person = personDb.get(20, TimeUnit.SECONDS);
		System.out.println(person);
		return person;
	}

	@PutMapping(path = "/findByFirstNameOrLastName/{fname}/{lname}")
	private List<Person> findByFirstNameOrLastName(@PathVariable String fname, @PathVariable String lname) {
		System.out.println("===============findByFirstNameOrLastName");
		List<Person> personDb = repo.findByFirstNameOrLastName(fname, lname);
		System.out.println(personDb);
		return personDb;
	}
	@PutMapping(path = "/updateEmailById/{id}/{email}")
	private void updateEmailById(@PathVariable int id, @PathVariable String email) {
		System.out.println("===============updateEmailById");
		
		repo.updateEmailById(id,email);

	}
}
