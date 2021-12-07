package it.castelli.ksv;

import java.sql.Date;

public class Author implements Entity {
	private final String firstName, lastName;
	private final Date birthDate, deathDate;
	private final String life;

	public Author() {
		firstName = "";
		lastName = "";
		birthDate = null;
		deathDate = null;
		life = "";
	}

	public Author(String firstName, String lastName, Date birthDate, Date deathDate, String life) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
		this.life = life;
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
