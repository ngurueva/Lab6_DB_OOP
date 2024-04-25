package L6_BD.DBEditions;

import L6_BD.data.Book;
import L6_BD.data.Magazine;
import L6_BD.data.PrinteredEdition;
import L6_BD.data.Textbook;
import L6_BD.view.MyTableModel;
import org.w3c.dom.Text;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWorker implements EditionsRepository{

    public static final String URL = "jdbc:sqlite:C:\\SQLite\\sqlite-tools-win-x64-3450200\\ed.db";
    public static Connection conn;
    static int count;
    @Override
    public ArrayList<PrinteredEdition> getAllEditions() throws SQLException {
        ArrayList<PrinteredEdition> list = new ArrayList<PrinteredEdition>();
        Connection conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Book;");
        while(resultSet.next()) {
            list.add(new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getInt("numOfPages"), resultSet.getString("genre")));
        }
        ResultSet resultSet1 = statement.executeQuery("SELECT * FROM Textbook;");
        while(resultSet1.next()) {
            list.add(new Textbook(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getInt("numOfPages"), resultSet.getString("genre"), resultSet.getString("StudyArea")));
        }

        ResultSet resultSet2 = statement.executeQuery("SELECT * FROM Magazine;");
        while(resultSet2.next()) {
            list.add(new Magazine(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getInt("numOfPages"), 1));
        }

        count = list.size();
        return list;
    }


    public static void initDB() {
        try {
            conn = DriverManager.getConnection(URL);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Драйвер: " + meta.getDriverName());
                createDB();
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка подключения к БД: " + ex);
        }
    }

    public static void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void createDB() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("CREATE TABLE if not exists 'Edition' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'typeEdition' text);");
        System.out.println("Таблица создана или уже существует.");

        statement.execute("CREATE TABLE if not exists 'Book' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'title' text, 'author' text, 'year' integer, 'numOfPages' integer, 'genre' text);");
        System.out.println("Таблица создана или уже существует.");

        statement.execute("CREATE TABLE if not exists 'Magazine' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'title' text, 'author' text, 'year' integer, 'numOfPages' integer, 'IssueNum' integer);");
        System.out.println("Таблица создана или уже существует.");

        statement.execute("CREATE TABLE if not exists 'Textbook' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'title' text, 'author' text, 'year' integer, 'numOfPages' integer, 'genre' text, 'StudyArea' text);");
        System.out.println("Таблица создана или уже существует.");

        conn = DriverManager.getConnection(URL);
        System.out.println("БД подключена!");
    }
    @Override
    public void deleteEdition(PrinteredEdition edition) throws SQLException{
        conn = DriverManager.getConnection(URL);
        System.out.println("БД подключена!");

        Statement statement = conn.createStatement();
        if (edition instanceof Book) {
            statement.executeUpdate("delete from Book WHERE id = " + edition.getId() + ";");
            statement.executeUpdate("delete from Edition WHERE id = " + edition.getId() + ";");
        } else if (edition instanceof Textbook) {
            statement.executeUpdate("delete from Textbook WHERE id = " + edition.getId() + ";");
            statement.executeUpdate("delete from Edition WHERE id = " + edition.getId() + ";");
        } else if (edition instanceof Magazine) {
            statement.executeUpdate("delete from Magazine WHERE id = " + edition.getId() + ";");
            statement.executeUpdate("delete from Edition WHERE id = " + edition.getId() + ";");
        }
        statement.close();
        System.out.println("Данные из таблиц удалены");


        conn.close();
        System.out.println("Соединения закрыты");
    }
    @Override
    public void deleteAll() throws SQLException
    {
        conn = DriverManager.getConnection(URL);
        System.out.println("БД подключена!");

        Statement statement = conn.createStatement();
        statement.executeUpdate("delete from Book;\n" +
                "delete from Textbook;\n" +
                "delete from Magazine;\n" +
                "delete from Edition;");
        statement.close();
        System.out.println("Данные из таблиц удалены");


        conn.close();
        System.out.println("Соединения закрыты");
    }


    @Override
    public void addEdition(PrinteredEdition edition) throws SQLException
    {
        conn = DriverManager.getConnection(URL);
        Statement statement = conn.createStatement();
        if (edition instanceof Textbook) {
            statement.executeUpdate("insert into Textbook values (" + edition.getId() + ", '" + edition.getTitle() + "', '" + edition.getAuthor() + "', " + edition.getYear() + ", " + edition.getNumOfPages() + ", 'Учебная Лит-ра', '" + ((Textbook) edition).getStudyArea() + "');");
            System.out.println("Учебник добавлен в БД");
        } else if (edition instanceof Magazine) {
            statement.executeUpdate("insert into Magazine values (" + edition.getId() + ", '" + edition.getTitle() + "', '" + edition.getAuthor() + "', " + edition.getYear() + ", " + edition.getNumOfPages() + ", '" + ((Magazine) edition).getIssueNumber() + "');");
            System.out.println("Журнал добавлен в БД");
        } else if (edition instanceof Book) {
            statement.executeUpdate("insert into Book values (" + edition.getId() + ", '" + edition.getTitle() + "', '" + edition.getAuthor() + "', " + edition.getYear() + ", " + edition.getNumOfPages() + ", '" + ((Book) edition).getGenre() + "');");
            System.out.println("Книга добавлен в БД");
        }
        statement.executeUpdate("insert into Edition values (" + edition.getId() + ", '" + edition.getTypeEdition(edition) + "');");

        System.out.println("Соединения закрыты");
        count++;
        conn.close();
    }
    public int countBookDB() throws SQLException {
        return count;
    }

    public void editEditionTitle(PrinteredEdition edition, String title) throws SQLException {
        conn = DriverManager.getConnection(URL);
        System.out.println("БД подключена!");

        Statement statement = conn.createStatement();
        if (edition instanceof Textbook) {
            statement.executeUpdate("update Textbook set title='"+title+"' where id="+edition.getId()+" ;");
            System.out.println("название учебника изменено");
        } else if (edition instanceof Magazine) {
            statement.executeUpdate("update Magazine set title='"+title+"' where id="+edition.getId()+" ;");
            System.out.println("название журнала изменено");
        } else if (edition instanceof Book) {
            statement.executeUpdate("update Book set title='"+title+"' where id="+edition.getId()+" ;");
            System.out.println("название книги изменено");
        }
        statement.executeUpdate("update Edition set title='"+title+"' where id="+edition.getId()+" ;");
        statement.close();

        conn.close();
        System.out.println("Соединения закрыты");
    }
    public void editEditionYear(PrinteredEdition edition, int year) throws SQLException {
        conn = DriverManager.getConnection(URL);
        System.out.println("БД подключена!");

        Statement statement = conn.createStatement();
        if (edition instanceof Textbook) {
            statement.executeUpdate("update Textbook set year='"+year+"' where id="+edition.getId()+" ;");
            System.out.println("год учебника изменено");
        } else if (edition instanceof Magazine) {
            statement.executeUpdate("update Magazine set year='"+year+"' where id="+edition.getId()+" ;");
            System.out.println("год журнала изменено");
        } else if (edition instanceof Book) {
            statement.executeUpdate("update Book set year='"+year+"' where id="+edition.getId()+" ;");
            System.out.println("год книги изменено");
        }
        System.out.println("год издания изменен");
        statement.close();

        conn.close();
        System.out.println("Соединения закрыты");
    }
    public void editEditionNumOfPages(PrinteredEdition edition, int num) throws SQLException {
        conn = DriverManager.getConnection(URL);
        System.out.println("БД подключена!");

        Statement statement = conn.createStatement();
        if (edition instanceof Textbook) {
            statement.executeUpdate("update Textbook set numOfPages='"+num+"' where id="+edition.getId()+" ;");
            System.out.println("год учебника изменено");
        } else if (edition instanceof Magazine) {
            statement.executeUpdate("update Magazine set numOfPages='"+num+"' where id="+edition.getId()+" ;");
            System.out.println("год журнала изменено");
        } else if (edition instanceof Book) {
            statement.executeUpdate("update Book set numOfPages='"+num+"' where id="+edition.getId()+" ;");
            System.out.println("год книги изменено");
        }
        System.out.println("год издания изменен");
        statement.close();

        conn.close();
        System.out.println("Соединения закрыты");
    }
}