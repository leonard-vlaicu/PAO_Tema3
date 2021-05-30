
package model;

public class Cont {
	private String	Nume;
	private int		ID;
	private int		BalantaCont;

	// Constructori
	public Cont() {
		this.Nume			= "NULL";
		this.ID				= 1;
		this.BalantaCont	= 0;
	}
	public Cont(String nume) {
		this.Nume 			= nume;
		this.ID 			= 1;
		this.BalantaCont 	= 0;
	}

	// Getters
	public int			getBalantaCont	() {
		return BalantaCont;
	}
	public String		getNume			() {
		return Nume;
	}
	public int 			getID			() {
		return ID;
	}

	// Setters
	public void setNume			(String nume) {
		Nume = nume;
	}
	public void setID			(int ID) {
		this.ID = ID;
	}
	public void setBalantaCont	(int balantaCont) {
		BalantaCont = balantaCont;
	}

	// Metode Publice
	public void adauga			(int suma) {
	this.BalantaCont += suma;
	}
	public void retrage			(int suma) {
	this.BalantaCont -= suma;
	}

	@Override
	public String toString() {
		return "Cont{" +
				"Nume='" + Nume + '\'' +
				", ID=" + ID +
				", BalantaCont=" + BalantaCont +
				'}';
	}
}
