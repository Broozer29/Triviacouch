package persistence;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import resource.IDUtil;
import domain.Speler;

//create table deelnemer (id decimal(10),sessieID decimal(10),spelerID decimal(10),spelerScore decimal(10), PRIMARY KEY(id))
public class DeelnemerDao {

	public void addSpelerAanSessie(Connection connection, Speler speler, long sessieID) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("insert into deelnemer (id, sessieID, spelerID) values (?,?,?)");
		IDUtil id = new IDUtil();
//		Long randomLong = id.getNextId();
		stmt.setLong(1, 1);
		stmt.setLong(2, sessieID);
		stmt.setLong(3, speler.getId());
		stmt.execute();
		stmt.close();
	}

	public List<Long> getSpelersIDVanSessie(Connection connection) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("select spelerID from deelnemer fetch first row only");
		ResultSet rs = stmt.executeQuery();
		List<Long> spelerIDs = new ArrayList<Long>();

		while (rs.next()) {;
			spelerIDs.add(rs.getLong(1));
		}
		rs.close();
		return spelerIDs;
	}
	
	
	public void deleteSessie(Connection connection, long sessieID) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("delete * from deelnemer where sessieID = ?");
		stmt.setLong(1, sessieID);
		stmt.execute();
		stmt.close();
		
	}
	public void deleteSpelerVanSessie(Connection connection, Speler speler) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("delete from deelnemer where spelerID = ?");
		stmt.setLong(1, speler.getId());
		stmt.execute();
		stmt.close();
		
	}

}
