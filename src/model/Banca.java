
package model;

import service.CSVWriter;


import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;



public class Banca {
	private String Nume;
	private HashMap<Integer, Client> ListaClienti;


	// Constructor
	public Banca(String nume) {
		this.Nume           = nume;
		this.ListaClienti   = new HashMap<Integer, Client>();
	}


	// Getters
	public String 						getNume(){
		return this.Nume;
	}
	public HashMap<Integer, Client> 	getListaClienti(){
		return this.ListaClienti;
	}


	// Setters
	public void setNume			(String nume) {
		this.Nume = nume;
	}
	public void setListaClienti	(HashMap<Integer,Client> ListaClienti) {
		this.ListaClienti = ListaClienti;
	}


	// Metode Publice
	public void adaugaClient	(Client client) {
		this.ListaClienti.put(client.getID(), client);
	}
	public void modificaClient	(int idClient,Client client) {
		this.ListaClienti.replace(idClient, client);
	}
	public void stergeClient	(int idClient) {
		this.ListaClienti.remove(idClient);
	}
	public void afiseazaClienti	() {
		for (Client client : this.ListaClienti.values()) {
			System.out.println(client.toString());
		}
	}
	public void importClienti	() throws IOException {
		CSVWriter helper = CSVWriter.getInstance();
		ArrayList<Client> dateClienti = helper.importClienti();

		for (Client client : dateClienti) {
			this.adaugaClient(client);
		}
	}
	public void exportClienti	() throws IOException {
		CSVWriter helper = CSVWriter.getInstance();
		helper.exportClienti(this.ListaClienti);
	}
	public void importConturi	() throws IOException, SQLException {
		CSVWriter helper = CSVWriter.getInstance();
		ArrayList<ArrayList<String>> dateConturi = helper.importConturi();

		for(int i=0; i< dateConturi.get(0).size(); i++) {
			Cont cont = new Cont();
			int idClient = Integer.parseInt(dateConturi.get(0).get(i));
			String numeCont = dateConturi.get(1).get((i));
			Client client = this.ListaClienti.get(idClient);

			cont.setID(client.NumarConturi);
			cont.setNume(numeCont);
			cont.setBalantaCont(0);

			this.ListaClienti.get(idClient).deschideCont(cont);
		}
	};
	public void exportConturi	() throws IOException{
		CSVWriter helper = CSVWriter.getInstance();
		helper.exportConturi(this.ListaClienti);
	}
}