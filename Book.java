package L6_BD.data;

public class Book extends PrinteredEdition{
    private String genre;
    public Book(int id, String title, String author, int year, int numOfPages, String genre) {
        super(id, title, author, year, numOfPages);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
