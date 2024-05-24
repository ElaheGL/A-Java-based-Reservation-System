import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner ;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Reservation extends JFrame implements ActionListener,ListSelectionListener {
    private JTable table;
    private DefaultTableModel model;
    private ReservationDAO reservationDAO;
    private JTextField selectedTicket;
    private JButton confirm;
    private JPanel part1;
    private JSpinner numberOfTicketsSpinner;
    

    public Reservation() {
        setTitle("CUSTOMER RESERVATION");
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
   

        model = new DefaultTableModel();
        
        model.addColumn("Origin");
        model.addColumn("Destenation");
        model.addColumn("Departure Time");
        model.addColumn("Capacity");

        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
       
    
    
        table.getSelectionModel().addListSelectionListener((ListSelectionListener) this); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
      
        


        loadTable();
        
        
        part1 = new JPanel();
        part1.add(new JLabel("Selected Ticket :"));
        selectedTicket = new JTextField(30);
        part1.add(selectedTicket);
        numberOfTicketsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        part1.add(numberOfTicketsSpinner);
        confirm= new JButton("Confirm");
        part1.add(confirm);
        confirm.addActionListener(this);
        add(part1,BorderLayout.SOUTH);
        
       	

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirm) {
        	confirmation();
        	
        }
    }

    private void loadTable() {
        try {
            reservationDAO = new ReservationDAO();
            try {
                reservationDAO.insertIntoReservationData();
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Reservations> reservations = reservationDAO.getAllReservation();

            model.setRowCount(0);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            for (Reservations reservation : reservations) {
                LocalDateTime departureDateTime = LocalDateTime.parse
                		(reservation.getDepartureTime(), formatter);
                if (departureDateTime.isAfter(currentDateTime)) {
                    model.addRow(new Object[]{ reservation.getorigin(),
                    		reservation.getdestenation(),reservation.getDepartureTime(),
                        reservation.getcapacity() });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Reservation();
    }
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (table.isRowSelected(selectedRow)) {
                String origin = (String) table.getValueAt(selectedRow, 0);
                String destination = (String) table.getValueAt(selectedRow, 1);
                String departureTime = (String) table.getValueAt(selectedRow, 2);
                selectedTicket.setText( origin + " -TO- " + destination + " -AT  : " + departureTime);
                
            }
        }
    }
    public void confirmation() {
    	int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String origin = (String) table.getValueAt(selectedRow, 0);
            String destination = (String) table.getValueAt(selectedRow, 1);
            String departureTime = (String) table.getValueAt(selectedRow, 2);
            int capacity = (int) table.getValueAt(selectedRow, 3);
            int numberOfTickets = (int) numberOfTicketsSpinner.getValue();

            if (numberOfTickets <= capacity) {
                // Update capacity in the table
                int updatedCapacity = capacity - numberOfTickets;
                table.setValueAt(updatedCapacity, selectedRow, 3);
                JOptionPane.showMessageDialog(null, "Reservation confirmed for " + 
                numberOfTickets + " ticket(s) from " + origin + " to " + destination + ".",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Not enough available seats for the selected route.",
                		"Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a route from the table.",
            		"Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
 
   
}
