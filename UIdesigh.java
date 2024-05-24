import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UIdesigh extends JFrame implements ActionListener {
    private JPanel part1, part2;
    private JButton addToList, delBus ,route;
    private JTextField plateField, capacityField;
    private JTable table;
    private DefaultTableModel model;
    private BusDAO busDAO;

    public UIdesigh() {
    	
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setTitle("Bus Information");

       
        busDAO = new BusDAO();

        /////////////////////
        part1 = new JPanel();
        part1.add(new JLabel("Bus Plate:"));
        plateField = new JTextField(10);
        part1.add(plateField);
        part1.add(new JLabel("Capacity:"));
        capacityField = new JTextField(10);
        part1.add(capacityField);
        

        addToList = new JButton("ADD TO LIST");
        addToList.addActionListener(this);
        delBus = new JButton("DELETE");
        delBus.addActionListener(this);
        route= new JButton("ROUTE");
        route.addActionListener(this);

        part2 = new JPanel();
        part2.add(addToList);
        part2.add(delBus);
        part2.add(route);

        add(part1, BorderLayout.NORTH);
        add(part2, BorderLayout.SOUTH);

       
        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        
        loadTableData();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToList) {
            Bus bus = new Bus(plateField.getText(),
            		Integer.parseInt(capacityField.getText()));
            busDAO.addBus(bus);
            plateField.setText("");
            capacityField.setText("");
            loadTableData();
            
        } 
        if (e.getSource() == delBus) {    
            Bus busToDelete = new Bus(plateField.getText(), 0);
            busDAO.deleteBus( busToDelete);
            loadTableData(); 
            plateField.setText("");
        }
        if(e.getSource() == route) {
        	 dispose();
        	 RouteUIdesighn f2 = new RouteUIdesighn();
        }
       
    }

    private void loadTableData() {
        try {
            List<Bus> buses = busDAO.getAllbuses();
            model = new DefaultTableModel();
            model.addColumn("Plate");
            model.addColumn("Capacity");
            for (Bus bus : buses) {
                model.addRow(new Object[]{bus.getplate(), bus.getcapacity()});
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
            		"Error loading data from database.",
            		"Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UIdesigh();
    }
}