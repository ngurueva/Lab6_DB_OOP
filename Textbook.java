package L6_BD.data;

public class Textbook extends Book {
    private String studyArea;

    public Textbook(int id, String title, String author, int year, int numOfPages, String genre, String studyArea) {
        super(id, title, author, year, numOfPages, genre);
        this.studyArea = studyArea;
        this.setGenre("Учебная литература");

    }
    public String getStudyArea() {
        return studyArea;
    }
    public void setStudyArea(String studyArea) {
        this.studyArea = studyArea;
    }
}