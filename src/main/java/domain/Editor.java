package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import persistence.AntwoordDao;
import persistence.DataBaseDao;
import persistence.VraagDao;
import resource.IDUtil;

public class Editor extends DataBaseDao {

	public void maakVraag() throws SQLException  {
		Scanner reader = new Scanner(System.in);
		System.out.println("Voer hier de vraag in, niet langer dan 255 characters: ");
		String vraagString = reader.nextLine();

		if (vraagString.length() < 256) {
			System.out.println("De vraag: " + vraagString);
			Vraag vraag = new Vraag();
			vraag.setVraagText(vraagString);
			vraag.setID(IDUtil.getNextId());

			List<Antwoord> antwoordLijst = maakAntwoord(reader, vraag);

			VraagDao vraagFactory = new VraagDao();
			AntwoordDao antwoordFactory = new AntwoordDao();

			vraagFactory.createVraag(getConnection(), vraag);
			for (Antwoord antwoord : antwoordLijst) {
				antwoordFactory.createAntwoord(getConnection(), antwoord, vraag);
			}

		} else
			System.out.println("De vraaglengte is te lang!");

	}

	private List<Antwoord> maakAntwoord(Scanner reader, Vraag vraag) {
		List<Antwoord> antwoordLijst = new ArrayList<Antwoord>();
		for (int i = 0; i < 4; i++) {
			System.out.println("Geef een antwoord: ");
			String antwoordString = reader.nextLine();
			System.out.println("Geef aan of dit antwoord correct is. J = correct, N = foutief.");
			String correct_jn = reader.nextLine().toUpperCase();

			if (correct_jn.length() == 1) {
				Antwoord antwoord = new Antwoord();
				antwoord.setAntwoordText(antwoordString);
				antwoord.setCorrect_jn(correct_jn);
				antwoord.setVraagID(vraag.getID());
				antwoord.setID(IDUtil.getNextId());

				antwoordLijst.add(antwoord);
			}
		}

		return antwoordLijst;
	}
}
