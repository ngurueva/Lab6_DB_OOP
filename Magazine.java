package L6_BD.data;
public class Magazine extends PrinteredEdition {

    private int issueNumber;
    public Magazine(int id, String title, String author, int year, int numOfPages, int issueNumber) {
        super(id, title, author, year, numOfPages);
        this.issueNumber = issueNumber;
    }
    public int getIssueNumber() {
        return issueNumber;
    }
    public void setIssueNum(int issueNum) {
        this.issueNumber = issueNum;
    }
}