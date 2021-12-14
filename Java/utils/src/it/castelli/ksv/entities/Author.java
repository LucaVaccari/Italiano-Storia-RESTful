package it.castelli.ksv.entities;

import java.sql.Date;

public class Author {
	private final int id;
	private final String firstName, lastName;
	private final Date birthDate, deathDate;
	private final String life;
	// TODO: private final int[] opusesIds;
	// TODO: private final int[] topicsIds;

	// empty constructor is necessary for deserializing from JSON
	public Author() {
		id = -1;
		firstName = "";
		lastName = "";
		birthDate = null;
		deathDate = null;
		life = "";
	}

	public Author(int id, String firstName, String lastName, Date birthDate, Date deathDate, String life) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
		this.life = life;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public String getLife() {
		return life;
	}

	@Override
	public String toString() {
		return "Author{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", birthDate=" + birthDate +
				", deathDate=" + deathDate +
				", life='" + life + '\'' +
				'}';
	}
}
