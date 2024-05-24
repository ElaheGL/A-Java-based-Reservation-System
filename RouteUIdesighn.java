
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class RouteUIdesighn extends JFrame implements ActionListener{
	
	private JTextField origiField,destenationField,departureTimeField;
	private JButton addToList,delete,next;
	private JPanel part1,part2;
	private JTable table;
	private DefaultTableModel model;
	private RouteDAO routeDAO;
	private JComboBox<String> selectBusPlate;
	private BusDAO busDAO;
	private JButton Back;
	
	public RouteUIdesighn() {
		setSize(820,500);
		setTitle("ROUTE INFORMATION");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		
		part1 = new JPanel();
		part1.add(new JLabel("Origin : "));
		origiField = new JTextField(10);
		part1.add(origiField);
		
		///////////////////////////////////////////////////
		 
		part1.add(new JLabel("Destenation"));
		destenationField = new JTextField(10);
		part1.add(destenationField);
		
		part1.add(new JLabel("Departure Time : "));
		departureTimeField = new JTextField(10);
		part1.add(departureTimeField);
		
////////////////////////////////////////////////////combobox:
		busDAO = new BusDAO();
		selectBusPlate = new JComboBox<String>();
	    List<Bus> buses = busDAO.getAllbuses();
	    for(Bus bus: buses) {
	    	selectBusPlate.addItem(bus.getplate());
	    }
		part1.add(new JLabel("Bus Plate:"));
		part1.add(selectBusPlate);
		part1.add(Timemanagin());
		//////////////////////////////////////////
		
		part2 = new JPanel();
		addToList = new JButton("ADD");
		addToList.addActionListener(this);
		part2.add(addToList);
		
		delete = new JButton("DELETE");
		delete.addActionListener(this);
		part2.add(delete);
		
		Back = new JButton("BACK");
		part2.add(Back);
		Back.addActionListener(this);
	
		next = new JButton("NEXT");
		next.addActionListener(this);
		part2.add(next);
		
		
		table = new JTable();
		routeDAO = new RouteDAO();
		

		
		
		
		add(part1,BorderLayout.NORTH);
		add(part2,BorderLayout.SOUTH);
		add(new JScrollPane(table),BorderLayout.CENTER);
		RouteTableData();
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addToList) {
			String selectedPlate = (String)selectBusPlate.getSelectedItem();
			String departureTimeStr = departureTimeField.getText();
			LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, 
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				Route route1 = new Route(origiField.getText(),
						destenationField.getText(),departureTime) ;
				routeDAO.addRoute(route1, selectedPlate);
				origiField.setText("");
				destenationField.setText("");
				departureTimeField.setText("");
				RouteTableData();}
			
		if(e.getSource() == delete) {
			String departureTimeStr = departureTimeField.getText();
			LocalDateTime departureTime = LocalDateTime.parse
					(departureTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				
			Route routeToDelete = new Route(origiField.getText(),
					destenationField.getText(),departureTime);
			routeDAO.deleteRwoute(routeToDelete);
			origiField.setText("");
			destenationField.setText("");
			departureTimeField.setText("");
			RouteTableData();
			
			
		}
		if(e.getSource()== Back) {
			dispose();
			UIdesigh f2 = new UIdesigh();
			
			
		}
		if(e.getSource() == next) {
			dispose();
			Reservation f3 = new Reservation();
		}
		
		
	}
	private void RouteTableData() {
		try {
			routeDAO = new RouteDAO();
			List<Route> routes = routeDAO.getAllRoute();
			model = new DefaultTableModel();
			model.addColumn("origin");
			model.addColumn("destenation");
			model.addColumn("Departure Time");
			for(Route route : routes) {
				model.addRow(new Object[] {route.getorigin(),route.getdestenation(),route.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))});
		}
			table.setModel(model);
					
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
				
	}
	private JLabel Timemanagin() {
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
		String finalNow = now.format(formatter);
		
			return new JLabel(" " +finalNow);
		
			
	}



	
}

