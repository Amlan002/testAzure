package com.info.people.management.app.entity;

 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "person_table")
@Getter
@Setter
@ToString
@DynamicUpdate
@NamedQueries(value = {
		@NamedQuery(name = "person.findByemail",query = "Select p from Person p where p.email=?1"),
		@NamedQuery(name = "person.findByIdAndFirstname",query = "Select p from Person p where p.id=?1 And p.firstName=?2")
})
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "person_id")
	private int id;
	@Column(name = "first_name", length = 60, nullable = false)
	private String firstName;
	@Column(name = "last_name", length = 60, nullable = false)
	private String lastName;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "creation_date")
	private Date creationDate;

	public Person(String firstName, String lastName, String email, Date creationDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.creationDate = creationDate;
	}

	public Person() {

	}

}
