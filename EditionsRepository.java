package L6_BD.DBEditions;
import L6_BD.data.PrinteredEdition;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EditionsRepository {
    ArrayList<PrinteredEdition> getAllEditions() throws SQLException;
    void addEdition(PrinteredEdition edition) throws SQLException;
    int countBookDB() throws SQLException;
    void deleteAll() throws SQLException;
    void deleteEdition(PrinteredEdition edition) throws SQLException;
    void editEditionYear(PrinteredEdition edition, int num) throws SQLException;
    void editEditionTitle(PrinteredEdition edition, String name) throws SQLException;
    void editEditionNumOfPages(PrinteredEdition edition, int numPages) throws SQLException;
}
