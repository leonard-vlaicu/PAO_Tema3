
package service;

import model.*;
import utilities.DBConnection;


import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;



public class Controller {
	private final Connection connection;
	private final DBConnection dbConnection;

	// Constructor
	public Controller() {
		this.dbConnection = DBConnection.getInstance();
		this.connection = dbConnection.getDBConnection();
	}

	// Implementare Comenzi Utilizator
	public void UserCommand(int option, Client client, Banca bank) throws SQLException {
		Scanner scanner	= new Scanner(System.in);

		// Depoziteaza bani
		if (option == 1) {
			int idCont = -1;
			int suma;
			boolean found = false;
			String numeCont;
			Cont conttmp = new Cont();

			System.out.println("Introduceti Numele Contului: ");
			numeCont = scanner.next();

			for (Cont cont : client.getListaConturi().values()) {
				if(cont.getNume().equals(numeCont)) {
					idCont	= cont.getID();
					found	= true;
					conttmp = cont;
				}
			}

			if (found) {
				System.out.println("Introduceti Suma Dorita: ");
				suma = scanner.nextInt();

				client.depoziteazaBani(idCont, suma);

				DBConnection dbConnection = this.dbConnection;
				Connection connection     = this.connection;
				String SQL = "UPDATE cont SET BalantaCont = ? WHERE IDCont = ? AND IDClient = ?";
				PreparedStatement DepoziteazaBani = connection.prepareStatement(SQL);
				DepoziteazaBani.setInt(1, conttmp.getBalantaCont());
				DepoziteazaBani.setInt(2, idCont);
				DepoziteazaBani.setInt(3, client.getID());
				DepoziteazaBani.executeUpdate();
				DepoziteazaBani.close();
			}
		}

		// Retrage bani
		else if (option == 2) {
			int idCont = -1;
			int suma;
			boolean found = false;
			String numeCont;
			Cont conttmp = new Cont();

			System.out.println("Introduceti Numele Contului: ");
			numeCont = scanner.next();

			for (Cont cont : client.getListaConturi().values()) {
				if(cont.getNume().equals(numeCont)) {
					idCont	= cont.getID();
					found	= true;
					conttmp = cont;
				}
			}

			if (found) {
				System.out.println("Introduceti Suma Dorita: ");
				suma = scanner.nextInt();

				client.retrageBani(idCont, suma);

				DBConnection dbConnection = this.dbConnection;
				Connection connection     = this.connection;
				String SQL = "UPDATE cont SET BalantaCont = ? WHERE IDCont = ? AND IDClient = ?";
				PreparedStatement RetrageBani = connection.prepareStatement(SQL);
				RetrageBani.setInt(1, conttmp.getBalantaCont());
				RetrageBani.setInt(2, idCont);
				RetrageBani.setInt(3, client.getID());
				RetrageBani.executeUpdate();
				RetrageBani.close();
			}
		}

		// Transfera bani
		else if (option == 3) {
			int idContSursa = -1, idContDest = -1, suma;
			boolean foundSursa = false, foundDest = false;
			String numeContSursa, numeContDest;
			Cont contTmp1 = new Cont(), contTmp2 = new Cont();

			System.out.println("Introduceti Numele Contului Sursa: ");
			numeContSursa = scanner.next();

			System.out.println("Introduceti Numele Contului Destinatie: ");
			numeContDest  = scanner.next();

			for (Cont cont : client.getListaConturi().values()) {
				if (cont.getNume().equals(numeContSursa)){
					idContSursa = cont.getID();
					foundSursa	= true;
					contTmp1	= cont;
				}

				else if (cont.getNume().equals(numeContDest)) {
					idContDest	= cont.getID();
					foundDest	= true;
					contTmp2	= cont;
				}
			}

			if (foundDest && foundSursa){
				System.out.println("Introduceti Suma Dorita: ");
				suma = scanner.nextInt();

				client.transferaBani(idContSursa, idContDest, suma);

				DBConnection dbConnection = this.dbConnection;
				Connection connection     = this.connection;

				String SQLSrc = "UPDATE cont SET BalantaCont = ? WHERE IDCont = ? AND IDClient = ?";
				String SQLDst = "UPDATE cont SET BalantaCont = ? WHERE IDCont = ? AND IDClient = ?";

				PreparedStatement TransferaBaniSrc = connection.prepareStatement(SQLSrc);
				PreparedStatement TransferaBaniDst = connection.prepareStatement(SQLDst);

				TransferaBaniSrc.setInt(1, contTmp1.getBalantaCont());
				TransferaBaniSrc.setInt(2, idContSursa);
				TransferaBaniSrc.setInt(3, client.getID());
				TransferaBaniSrc.executeUpdate();
				TransferaBaniSrc.close();

				TransferaBaniDst.setInt(1, contTmp2.getBalantaCont());
				TransferaBaniDst.setInt(2, idContDest);
				TransferaBaniDst.setInt(3, client.getID());
				TransferaBaniDst.executeUpdate();
				TransferaBaniDst.close();
			}
		}

		// Afiseaza conturile curente
		else if (option == 4) {
			client.afiseazaConturi();
		}

		// Afiseaza balanta totala
		else if (option == 5) {
			System.out.println("Balanta Totala: " + client.balantaTotala());
		}

		// Deschide cont nou
		else if (option == 6) {
			String	numeCont;
			Cont	newCont = new Cont();

			System.out.println("Nume Cont: ");
			numeCont = scanner.next();

			newCont.setNume	(numeCont);
			newCont.setID	(client.getNumarConturi());

//			client.setNumarConturi(client.getNumarConturi()+1);
			client.deschideCont(newCont);

			DBConnection dbConnection = DBConnection.getInstance();
			Connection connection 	  = dbConnection.getDBConnection();

			String SQL = "UPDATE client SET NumarConturi = ? WHERE IDClient = ?";
			PreparedStatement DeschideCont = connection.prepareStatement(SQL);

			DeschideCont.setInt(1, client.getNumarConturi()-1);
			DeschideCont.setInt(2, client.getID());
			DeschideCont.executeUpdate();
			DeschideCont.close();
		}

		// Deconectare
		else if (option == 9) {
		}
	}

	// Implementare Comenzi Admin
	public void AdminCommand(int option, Banca bank) throws IOException, SQLException {
		Scanner scanner = new Scanner(System.in);
		int adminOpt = -1;

		// Insereaza client
		if (option == 1) {
			System.out.println("1. Persoana Fizica");
			System.out.println("2. Persoana Juridica");

			adminOpt = scanner.nextInt();

			// Persoana fizica
			if (adminOpt == 1) {
				Client client = new PersoanaFizica();
				String numeCont, prenumeCont, CNP, dateInput;
				DateTimeFormatter dateFormat;
				LocalDate dataNasterii;

				System.out.println("Nume: ");
				numeCont = scanner.next();

				System.out.println("Prenume: ");
				prenumeCont = scanner.next();

				System.out.println("CNP: ");
				CNP = scanner.next();

				System.out.println("Data Nasterii: ");
				dateInput 	 = scanner.next();
				dateFormat 	 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				dataNasterii = LocalDate.parse(dateInput, dateFormat);

				client.setNume(numeCont);
				client.setPrenume(prenumeCont);
				client.setCNP(CNP);
				client.setDataNasterii(dataNasterii);

				bank.adaugaClient(client);

				String SQL = "INSERT INTO client VALUES (default , ?, ?, ?, ?, ?)";
				PreparedStatement InsertClient = this.connection.prepareStatement(SQL);
				InsertClient.setString(1, numeCont);
				InsertClient.setString(2, prenumeCont);
				InsertClient.setString(3, CNP);
				InsertClient.setDate  (4, java.sql.Date.valueOf(dataNasterii));
				InsertClient.setInt   (5, 1);
				InsertClient.executeUpdate();
				InsertClient.close();
			}

			// Persoana juridica
			if (adminOpt == 2) {
				Client client = new PersoanaJuridica();
				String numeCont, prenumeCont, CNP, dateInput;
				DateTimeFormatter dateFormat;
				LocalDate dataNasterii;

				System.out.println("Nume: ");
				numeCont = scanner.next();

				System.out.println("Prenume: ");
				prenumeCont = scanner.next();

				System.out.println("CNP: ");
				CNP = scanner.next();

				System.out.println("Data Nasterii: ");
				dateInput 	 = scanner.next();
				dateFormat 	 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				dataNasterii = LocalDate.parse(dateInput, dateFormat);

				client.setNume(numeCont);
				client.setPrenume(prenumeCont);
				client.setCNP(CNP);
				client.setDataNasterii(dataNasterii);

				bank.adaugaClient(client);

				String SQL = "INSERT INTO client VALUES (default , ?, ?, ?, ?, ?)";
				PreparedStatement InsertClient = this.connection.prepareStatement(SQL);
				InsertClient.setString(1, numeCont);
				InsertClient.setString(2, prenumeCont);
				InsertClient.setString(3, CNP);
				InsertClient.setDate  (4, java.sql.Date.valueOf(dataNasterii));
				InsertClient.setInt   (5, 1);
				InsertClient.executeUpdate();
				InsertClient.close();
			}

			System.out.println("Datele au fost inserate");
		}
		
		// Modifica date client
		else if (option == 2) {
			Client temp  = new Client();
			String newName, newPrenume, newCNP, dateInput;
			DateTimeFormatter dateFormat;
			LocalDate dataNasterii;

			System.out.println("Introduceti ID-ul Clientului");
			int idClient = scanner.nextInt();

			System.out.println("Nume nou: ");
			newName		= scanner.next();

			System.out.println("Prenume: ");
			newPrenume	= scanner.next();

			System.out.println("CNP: ");
			newCNP 		= scanner.next();

			System.out.println("Data Nasterii: ");
			dateInput 	 = scanner.next();
			dateFormat 	 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			dataNasterii = LocalDate.parse(dateInput, dateFormat);

			temp.setNume		(newName);
			temp.setPrenume		(newPrenume);
			temp.setCNP			(newCNP);
			temp.setDataNasterii(dataNasterii);
			temp.setID			(bank.getListaClienti().get(idClient).getID());
			temp.setListaConturi(bank.getListaClienti().get(idClient).getListaConturi());
			temp.setNumarConturi(bank.getListaClienti().get(idClient).getNumarConturi());

			bank.modificaClient(idClient, temp);

			String SQL = "UPDATE client SET NumeClient = ?, PrenumeClient = ?, CNP = ?, DataNasterii = ?, NumarConturi = ? WHERE IDClient = ?";

			PreparedStatement UpdateClient = this.connection.prepareStatement(SQL);
			UpdateClient.setString(1, newName);
			UpdateClient.setString(2, newPrenume);
			UpdateClient.setString(3, newCNP);
			UpdateClient.setDate  (4, java.sql.Date.valueOf(dataNasterii));
			UpdateClient.setInt   (5, 0);
			UpdateClient.setInt	  (6, idClient);
			UpdateClient.executeUpdate();
			UpdateClient.close();

			System.out.println("Datele pentru ID-ul " + idClient + " au fost actualizate");
		}

		// Sterge client
		else if (option == 3) {
			int idClient;

			System.out.println("Introduceti ID-ul Clientului: ");
			idClient = scanner.nextInt();
			bank.stergeClient(idClient);

			String SQL = "DELETE FROM client WHERE IDClient = ?";
			PreparedStatement DeleteClient = this.connection.prepareStatement(SQL);

			DeleteClient.setInt(1, idClient);
			DeleteClient.executeUpdate();
			DeleteClient.close();

			System.out.println("Clientul cu ID-ul " + idClient + " a fost sters");
		}

		// Afiseaza toti clientii
		else if (option == 4) {
			bank.afiseazaClienti();

			String SQL = "SELECT* FROM client";
			PreparedStatement SelectClient = this.connection.prepareStatement(SQL);

			SelectClient.execute();
			SelectClient.close();
		}

		// Importa date clienti
		else if (option == 5) {
			bank.importClienti();
			System.out.println("Datele au fost importate cu succes");
		}

		// Exporta date clienti
		else if (option == 6) {
			bank.exportClienti();
			System.out.println("Datele au fost exportate cu succes");
		}

		// Importa date conturi
		else if (option == 7) {
			bank.importConturi();
			System.out.println("Datele au fost importate cu succes");
		}

		else if (option ==8) {
			bank.exportConturi();
			System.out.println("Datele au fost exportate cu succes");
		}

		// Deconectare
		else if (option == 9) {
		}
	}
}