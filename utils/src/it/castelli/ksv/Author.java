package it.castelli.ksv;

import java.sql.Date;
import java.util.ArrayList;

public class Author implements Entity {
	private final String firstName, lastName;
	private final Date birthDate, deathDate;
	private final String life;
	private final ArrayList<Opus> texts;
	private final ArrayList<String> connections;

	public Author(String firstName, String lastName, Date birthDate, Date deathDate, String life,
                  ArrayList<Opus> texts, ArrayList<String> connections) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
		this.life = life;
		this.texts = texts;
		this.connections = connections;
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

	public ArrayList<Opus> getOpuses() {
		return texts;
	}

	public ArrayList<String> getConnections() {
		return connections;
	}
}
