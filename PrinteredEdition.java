package L6_BD.data;

import java.util.ArrayList;

public abstract class PrinteredEdition {
    private ArrayList<PrinteredEdition> list = new ArrayList<>();
    protected String title;
    protected String author;
    protected int year;
    protected int numOfPages;
    protected int id;

    public PrinteredEdition(int id, String title, String author, int year, int numOfPages) {
        this.id = id;
        this.setTitle(title);
        this.setAuthor(author);
        this.setYear(year);
        this.setNumOfPages(numOfPages);
    }


    public int getId() {
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setGenre(String aValue) {
    }

    public void setStudyArea(String aValue) {
    }

    public void setIssueNum(int i) {
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getTypeEdition(PrinteredEdition edition){
        if (edition instanceof Textbook) {
            return "Textbook";
        } else if (edition instanceof Magazine) {
            return "Magazine";
        } else if (edition instanceof Book) {
            return "Book";
        }
        return null;
    }
    public String getTypeEdition(){
        Class<?> cls = getClass();
        return cls.getName();
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
}