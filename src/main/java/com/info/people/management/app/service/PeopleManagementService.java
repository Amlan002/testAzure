package com.info.people.management.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.people.management.app.entity.Person;
import com.info.people.management.app.repo.PeoplemanagementRepo;

@Service
public class PeopleManagementService {
	@Autowired
	private PeoplemanagementRepo peopleManangementDao;
	
	public Person createPerson1(Person person1) {
		return peopleManangementDao.save(person1);
	}

	public Iterable<Person> createPersons(List<Person> personList) {
		Iterable<Person> list = peopleManangementDao.saveAll(personList);
		return list;
	}
}
