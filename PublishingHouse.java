package L6_BD.data;
import L6_BD.DBEditions.EditionsRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class PublishingHouse {
    private ArrayList<PrinteredEdition> list = new ArrayList<>();
    protected EditionsRepository repository;

    public PublishingHouse(EditionsRepository repository) {
        this.repository = repository;
    }
    public int getSizeList(){
        return this.list.size();
    }

    public PrinteredEdition getEdition(int index){
        return list.get(index);
    }


    public void addEdition(PrinteredEdition edition) throws SQLException {
        this.list.add(edition);
        this.repository.addEdition(edition);
    }

    public int countBookDB() throws SQLException {
        return this.repository.countBookDB();
    }

    public int getCount() {
        return this.list.size();
    }

    public ArrayList<PrinteredEdition> sort(String option) {
        if (Objects.equals(option, "по названию")){
            list.sort(Comparator.comparing(PrinteredEdition::getTitle));
            return list;
        }
        if (Objects.equals(option, "по году издания")){
            list.sort(Comparator.comparing(PrinteredEdition::getYear).reversed());
            return list;
        }
        if (Objects.equals(option, "по типу издания")){
            list.sort(Comparator.comparing(PrinteredEdition::getTypeEdition));
            return list;
        }
        return list;
    }


    public void remove(int index) {
        this.list.remove(index);
    }
    public void deleteAll() throws SQLException {
        for (int i = 0; i < getSizeList(); i++){
            this.list.remove(i);
        }
        System.out.println("Я прошел лист");
        this.repository.deleteAll();
    }

    public void deleteEdition(PrinteredEdition edition) throws SQLException {
        this.repository.deleteEdition(edition);
    }

    public void editEditionTitle(PrinteredEdition edition, String name) throws SQLException {
        this.repository.editEditionTitle(edition, name);
    }
    public void editEditionYear(PrinteredEdition edition, int num) throws SQLException {
        this.repository.editEditionYear(edition, num);
    }
    public void editEditionNumOfPages(PrinteredEdition edition, int numPages) throws SQLException {
        this.repository.editEditionNumOfPages(edition, numPages);
    }

    public void getAllEditions() throws SQLException {
        try {
            ArrayList<PrinteredEdition> templist = this.repository.getAllEditions();
            for (int i = 0; i < templist.size(); i++){
                list.add(templist.get(i));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}