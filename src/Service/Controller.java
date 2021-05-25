
package Service;

import Model.*;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
	public Controller() {
	}

	// Implementare Comenzi Utilizator
	public void UserCommand(int option, Client client){
		Scanner scanner	= new Scanner(System.in);

		// Depoziteaza bani
		if (option == 1) {
			int idCont = -1;
			int suma;
			boolean found = false;
			String numeCont;

			System.out.println("Introduceti Numele Contului: ");
			numeCont = scanner.next();

			for (Cont cont : client.getListaConturi().values()) {
				if(cont.getNume().equals(numeCont)) {
					idCont	= cont.getID();
					found	= true;
				}
			}

			if (found) {
				System.out.println("Introduceti Suma Dorita: ");
				suma = scanner.nextInt();

				client.depoziteazaBani(idCont, suma);
			}
		}

		// Retrage bani
		else if (option == 2) {
			int idCont = -1;
			int suma;
			boolean found = false;
			String numeCont;

			System.out.println("Introduceti Numele Contului: ");
			numeCont = scanner.next();

			for (Cont cont : client.getListaConturi().values()) {
				if(cont.getNume().equals(numeCont)) {
					idCont	= cont.getID();
					found	= true;
				}
			}

			if (found) {
				System.out.println("Introduceti Suma Dorita: ");
				suma = scanner.nextInt();

				client.retrageBani(idCont, suma);
			}
		}

		// Transfera bani
		else if (option == 3) {
			int idContSursa = -1, idContDest = -1, suma;
			boolean foundSursa = false, foundDest = false;
			String numeContSursa, numeContDest;

			System.out.println("Introduceti Numele Contului Sursa: ");
			numeContSursa = scanner.next();

			System.out.println("Introduceti Numele Contului Destinatie: ");
			numeContDest  = scanner.next();

			for (Cont cont : client.getListaConturi().values()) {
				if (cont.getNume().equals(numeContSursa)){
					idContSursa = cont.getID();
					foundSursa	= true;
				}

				else if (cont.getNume().equals(numeContDest)) {
					idContDest	= cont.getID();
					foundDest	= true;
				}
			}

			if (foundDest && foundSursa){
				System.out.println("Introduceti Suma Dorita: ");
				suma = scanner.nextInt();

				client.transferaBani(idContSursa, idContDest, suma);
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

			client.setNumarConturi(client.getNumarConturi()+1);
			client.deschideCont(newCont);
		}

		// Deconectare
		else if (option == 9) {

		}
	}

	// Implementare Comenzi Admin
	public void AdminCommand(int option, Banca bank) throws IOException {
		Scanner scanner = new Scanner(System.in);
		int adminOpt = -1;

		// Insereaza client
		if (option == 1) {
			System.out.println("1. Persoana Fizica");
			System.out.println("2. Persoana Juridica");

			adminOpt = scanner.nextInt();

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
			}

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

			System.out.println("Datele pentru ID-ul " + idClient + " au fost actualizate");
		}

		// Sterge client
		else if (option == 3) {
			int idClient;

			System.out.println("Introduceti ID-ul Clientului: ");
			idClient = scanner.nextInt();

			bank.stergeClient(idClient);

			System.out.println("Clientul cu ID-ul " + idClient + " a fost sters");
		}

		// Afiseaza toti clientii
		else if (option == 4) {
			bank.afiseazaClienti();
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
