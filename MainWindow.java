package L6_BD.view;

import L6_BD.DBEditions.DBWorker;
import L6_BD.data.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.awt.event.*;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    protected JTable jTable;
    private JPanel rightPanel;
    private JPanel upPanel;
    private MyTableModel myTableModel;
    private JScrollPane jScrollPane;
    private JButton deleteButton;
    private JButton addButton;
    private JButton deleteALLButton;
    private JLabel searchLabel;
    private JTextField searchTextField;
    private JButton OKsearchButton;
    private JComboBox<String> dropdownListForSort;
    private Dimension dimension = new Dimension(140, 25);
    private AddWindow addWindow;
    public MainWindow() {
        setTitle("Главное окно");

        initComponents();
        addComponents();
        setSizes();
        try {
            myTableModel.getAllEditions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myTableModel.deleteEdition(jTable.getSelectedRow());


                }  catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            private boolean windowOpen = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!windowOpen) {
                    addWindow = new AddWindow(myTableModel.getData());
                    windowOpen = true;
                    addWindow.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {
                            windowOpen = true;
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            windowOpen = false;
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            windowOpen = false;
                            myTableModel.fireTableDataChanged();
                        }
                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });
                }
                addWindow.setVisible(true);
            }
        });


        deleteALLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jTable.setRowSelectionInterval(0, jTable.getRowCount() - 1);
                    myTableModel.delete(jTable.getSelectedRow());
                    myTableModel.getData().deleteAll();
                }
                catch (IllegalArgumentException ex){
                    JDialog dialog = new JDialog(MainWindow.this, "Удаление", true);
                    setLocationDialog(dialog);
                    dialog.add(new JLabel("   Таблица пустая!"));
                    dialog.setSize(200, 100);
                    dialog.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        dropdownListForSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropdownListForSort.removeItem("Отсортировать по");
                Object option = dropdownListForSort.getSelectedItem();
                jTable.setModel(myTableModel);
                myTableModel.sort((String)option);
            }
        });

        OKsearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchTextField.getText();
                if (searchText.isEmpty()){
                    JDialog dialog = new JDialog(MainWindow.this, "Поиск", true);
                    setLocationDialog(dialog);
                    dialog.add(new JLabel("   Введите название или автора издания, для его поиска"));
                    dialog.setSize(400, 100);
                    dialog.setVisible(true);
                }
                else {
                    ArrayList<Integer> foundRow = myTableModel.search(searchText);
                    jTable.clearSelection();
                    for (int i = 0; i < foundRow.size(); i++){
                        jTable.addRowSelectionInterval(Integer.parseInt(foundRow.get(i).toString()), Integer.parseInt(foundRow.get(i).toString()));
                    }
                    int countFoundRow = jTable.getSelectedRowCount();
                    JDialog dialog = new JDialog(MainWindow.this, "Поиск", true);
                    setLocationDialog(dialog);
                    if ((countFoundRow % 10 == 1)) {
                        dialog.add(new JLabel("   Найдено " + countFoundRow + " издание"));
                    }
                    else if (((countFoundRow % 10 > 1) && (countFoundRow % 10 < 5))) {
                        dialog.add(new JLabel("   Найдено " + countFoundRow + " издания"));
                    }
                    else{
                        dialog.add(new JLabel("   Найдено " + countFoundRow + " изданий"));
                    }

                    dialog.setSize(200, 100);
                    dialog.setVisible(true);
                }
            }
        });

        JFrame jFrame = this;
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(jFrame,
                        "Вы уверены, что хотите выйти?", "Выход", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(1);
                    jFrame.dispose();
                }
                else {
                    jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });


        this.pack();
        this.setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        DBWorker.initDB();
        jTable = new JTable();
        rightPanel = new JPanel();
        upPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        myTableModel = new MyTableModel(new PublishingHouse(new DBWorker()));
        jTable.setModel(myTableModel);
        jScrollPane = new JScrollPane(jTable);
        deleteButton = new JButton("Удалить");
        addButton = new JButton("Добавить");
        deleteALLButton = new JButton("Очистить");
        searchLabel = new JLabel("Найти: ");
        searchTextField = new JTextField();
        OKsearchButton = new JButton("ОК");
        String[] optionsForSort = {"Отсортировать по", "по названию", "по году издания", "по типу издания"};
        dropdownListForSort = new JComboBox<>(optionsForSort);
    }

    private void setLocationDialog(JDialog dialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - dialog.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - dialog.getHeight()) / 2);
        dialog.setLocation(x, y);
    }

    private void addComponents() {
        add(jScrollPane, BorderLayout.CENTER);
        add(upPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        rightPanel.add(deleteButton);
        rightPanel.add(addButton);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(deleteALLButton);

        upPanel.add(dropdownListForSort);
        upPanel.add(searchLabel);
        upPanel.add(searchTextField);
        upPanel.add(OKsearchButton);
    }

    private void setSizes() {
        setMinimumSize(new Dimension(900, 300));
        deleteButton.setMaximumSize(dimension);
        deleteButton.setMaximumSize(dimension);
        addButton.setMaximumSize(dimension);
        addButton.setMinimumSize(dimension);
        deleteALLButton.setMaximumSize(dimension);
        deleteALLButton.setMinimumSize(dimension);
        searchTextField.setMaximumSize(new Dimension(435, 300));
        searchTextField.setMinimumSize(new Dimension(140, 300));
        OKsearchButton.setMinimumSize(dimension);
        OKsearchButton.setMaximumSize(dimension);
        dropdownListForSort.setMaximumSize(dimension);
    }

}