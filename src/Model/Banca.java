
package Model;

import java.util.HashMap;

public class Banca {
	private String Nume;
	private HashMap<Integer, Client> ListaClienti;
	private int NumarClienti;

	public Banca(String nume) {
		this.Nume           = nume;
		this.ListaClienti   = new HashMap<Integer, Client>();
		this.NumarClienti   = 0;
	}

	public int 						getNumarClienti(){
		return NumarClienti;
	}
	public String 					getNume(){
		return this.Nume;
	}
	public HashMap<Integer, Client> getListaClienti(){
		return this.ListaClienti;
	}

	public void setNume			(String nume) {
		this.Nume = nume;
	}
	public void setListaClienti	(HashMap<Integer,Client> ListaClienti) {
		this.ListaClienti = ListaClienti;
	}
	public void setNumarClienti (int numarClienti) {
		NumarClienti = numarClienti;
	}

	public void adaugaClient(Client client) {
		this.ListaClienti.put(client.getID(), client);
	}
	public void modificaClient(int idClient,Client client) {
		this.ListaClienti.replace(idClient, client);
	}
	public void stergeClient(int idClient) {
		this.ListaClienti.remove(idClient);
	}
	public void afiseazaClienti() {
		for (Client client : this.ListaClienti.values()) {
			System.out.println(client.toString());
		}
	}
}