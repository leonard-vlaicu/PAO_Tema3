package Service;

import Model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVWriter {
	// Constructor
	private CSVWriter() {
	}

	// Functii Helper
	private static class CitireCSVHelper {
		private static final CSVWriter INSTANCE = new CSVWriter();

	}

	public static CSVWriter getInstance() {
		return CitireCSVHelper.INSTANCE;
	}

	// Metode Publice
	public ArrayList<Client> importClienti() throws IOException {
		ArrayList<Client> dateImport = new ArrayList<>();
		String CSVline = "", splitBy = ",", datetimeInput, headerLine;
		String[] dateCSV;
		BufferedReader br = new BufferedReader(new FileReader("src/utilities/clientiSrc.csv"));
		DateTimeFormatter datetimeFormat;
		LocalDate dataNasterii;

		headerLine = br.readLine();

		while ((CSVline = br.readLine()) != null) {
			Client client = new Client();

			dateCSV = CSVline.split(splitBy);
			datetimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			datetimeInput = dateCSV[4];
			dataNasterii = LocalDate.parse(datetimeInput, datetimeFormat);

			client.setNume(dateCSV[1]);
			client.setPrenume(dateCSV[2]);
			client.setCNP(dateCSV[3]);
			client.setDataNasterii(dataNasterii);

			dateImport.add(client);
		}

		audit("Import Conturi");

		return dateImport;
	}
	public void exportClienti(HashMap<Integer, Client> listaClienti) throws IOException {
		ArrayList<Client> dateExport = new ArrayList<Client>(listaClienti.values());
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/utilities/clientiDst.csv", true));
		String header = "ID,Nume,Prenume,CNP,Data Nasterii \n";
		bw.write(header);

		for (Client client : dateExport) {
			String info = client.getID() + "," + client.getNume() + "," + client.getPrenume() + "," +
					client.getCNP() + "," + client.getDataNasterii() + "\n";
			bw.write(info);
		}
		bw.close();
		audit("Export Clienti");

	}
	public ArrayList<ArrayList<String>> importConturi() throws IOException {
		ArrayList<ArrayList<String>> dateImport = new ArrayList<>();
		ArrayList<String> idClienti  = new ArrayList<>();
		ArrayList<String> numeConturi= new ArrayList<>();

		String CSVline = "", splitBy =",", headerLine;
		String[] dateCSV;
		BufferedReader br = new BufferedReader(new FileReader("src/utilities/conturiSrc.csv"));

		headerLine = br.readLine();

		while ((CSVline = br.readLine()) != null) {
			Cont cont = new Cont();

			dateCSV = CSVline.split(splitBy);
			idClienti.add(dateCSV[0]);
			numeConturi.add(dateCSV[1]);
		}

		dateImport.add(idClienti);
		dateImport.add(numeConturi);

		audit("Import Conturi");


		return dateImport;
	}
	public void exportConturi(HashMap<Integer, Client> listaClienti) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/utilities/conturiDst.csv", true));
		String header = "CNP, Nume Cont \n";
		bw.write(header);

		for (Client client : listaClienti.values()) {
			for (Cont cont : client.getListaConturi().values()) {
				String info = client.getCNP() + "," + cont.getNume() +"\n";
				bw.write(info);
			}
		}
		bw.close();
		audit("Export Conturi");
	}

	public void audit(String operatie) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/utilities/audit.csv", true));
		String info = LocalDateTime.now() + "," + operatie + "\n";
		bw.write(info);

		bw.close();
	}
}