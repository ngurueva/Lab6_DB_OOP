package L6_BD.view;

import L6_BD.data.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyTableModel extends AbstractTableModel {
    private PublishingHouse data;
    public MyTableModel(PublishingHouse publishingHouse){
        this.data = publishingHouse;
    }


    @Override
    public int getRowCount() {
        return data.getSizeList();
    }

    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "id";
            case 1: return "Название";
            case 2: return "Автор";
            case 3: return "Год выпуска";
            case 4: return "Количестов страниц";
            case 5: return "Жанр";
            case 6: return "Учебная область";
            case 7: return "Номер выпуска";
        }
        return "";
    }

    @Override
    public int getColumnCount() {
        return 8;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0: return data.getEdition(rowIndex).getId();
            case 1: return data.getEdition(rowIndex).getTitle();
            case 2: return data.getEdition(rowIndex).getAuthor();
            case 3: return data.getEdition(rowIndex).getYear();
            case 4: return data.getEdition(rowIndex).getNumOfPages();
            case 5:{
                PrinteredEdition edition = data.getEdition(rowIndex);
                if (edition instanceof Book) {
                    return ((Book) edition).getGenre();
                }
                else {
                    return "-";
                }
            }
            case 6:{
                PrinteredEdition edition = data.getEdition(rowIndex);
                if (edition instanceof Textbook){
                    return ((Textbook)edition).getStudyArea();
                }
                else {
                    return "-";
                }
            }
            case 7: PrinteredEdition edition = data.getEdition(rowIndex);
                if (edition instanceof Magazine){
                    return ((Magazine)edition).getIssueNumber();
                }
                else {
                    return "-";
                }
        }
        return "-";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            switch (columnIndex) {
                case 1:
                    data.getEdition(rowIndex).setTitle((String) aValue);
                    data.editEditionTitle(data.getEdition(rowIndex), ((String) aValue));
                    break;
                case 2:
                    data.getEdition(rowIndex).setAuthor((String) aValue);
                    break;
                case 3:
                    data.getEdition(rowIndex).setYear(Integer.parseInt((String) aValue));
                    data.editEditionYear(data.getEdition(rowIndex), (Integer.parseInt((String) aValue)));
                    break;
                case 4:
                    data.getEdition(rowIndex).setNumOfPages(Integer.parseInt((String) aValue));
                    data.editEditionNumOfPages(data.getEdition(rowIndex), (Integer.parseInt((String) aValue)));
                    break;
                case 5:
                    data.getEdition(rowIndex).setGenre((String) aValue);
                    break;
                case 6:
                    data.getEdition(rowIndex).setStudyArea((String) aValue);
                    break;
                case 7:
                    data.getEdition(rowIndex).setIssueNum(Integer.parseInt((String) aValue));
                    break;
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (NumberFormatException ex){
            JDialog dialog = new JDialog(new Frame(), "Редактирование", true);
            dialog.add(new JLabel("   Введите корректное значение"));
            dialog.setSize(250, 100);
            dialog.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0: return false;
            case 1: return true;
            case 2: return false;
            case 3: return true;
            case 4: return true;
            case 5: return false;
            case 6: return false;
            case 7: return false;
        }
        return false;
    }

    public void delete(int index){
        this.data.remove(index);
        fireTableDataChanged();
    }

    public ArrayList<Integer> search(String searchText) {
        ArrayList<Integer> searchRow = new ArrayList<>();
        for (int i = 0; i < data.getCount(); i++) {
            if (this.data.getEdition(i).getTitle().equals(searchText)){
                searchRow.add(i);
            }
            if (this.data.getEdition(i).getAuthor().equals(searchText)){
                searchRow.add(i);
            }
        }
        return searchRow;
    }

    public PublishingHouse getData() {
        return data;
    }

    public void sort(String option){
        data.sort(option);
        fireTableDataChanged();
    }

    public void deleteEdition(int selectedRow) throws SQLException {
        data.deleteEdition(data.getEdition(selectedRow));
        delete(selectedRow);
        fireTableDataChanged();
    }

    public void getAllEditions() throws SQLException {
        data.getAllEditions();
    }

}

