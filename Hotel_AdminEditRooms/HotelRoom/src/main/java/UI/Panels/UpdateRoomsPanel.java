package UI.Panels;

import AccountService.Guest;
import Central.CentralDatabase;
import Central.CentralReservations;
import Central.CentralRoom;
import Utilities.DateProcessor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;


public class UpdateRoomsPanel extends JPanel {

    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel model;
    private JTable table;
    private Integer id;
    private JScrollPane scrollPane;
    private JButton addRoom, editRoom;
    
    
    public UpdateRoomsPanel() {

        super();
        this.setLayout(new BorderLayout());
        model = new DefaultTableModel();


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
        
        addRoom = new JButton("Add Room");
        addRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				id = -1;
				EditDialog addRoom = new EditDialog(table);
			}
        	
        });
        editRoom = new JButton("Edit Selected Room");
        addRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (id < 0) {
					JOptionPane.showMessageDialog(addRoom, "No room selected");
				} else {
					EditDialog editRoom = new EditDialog(table);
				}
			}
        	
        });

        model.addColumn("Room ID");
        model.addColumn("Room Status");
        model.addColumn("Room Type");
        model.addColumn("Bed Type");
        model.addColumn("Quality Level");
        model.addColumn("Smoking Allowed");
        table = new JTable(model);
        table.setRowHeight(15);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(65);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(30);

        table.setAutoCreateRowSorter(true);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::valueChanged);

        scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        updateTable("SELECT * FROM Rooms");

        add(scrollPane,BorderLayout.CENTER);
        add(textPanel,BorderLayout.SOUTH);
        add(addRoom, BorderLayout.SOUTH);
        add(editRoom, BorderLayout.SOUTH);




    }

    public boolean roomIsSelected() {
        return table.getSelectedRow() > -1;
    }

    public void updateTable(String SQL) {
        scrollPane.setVisible(true);

        id = null;

        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }

        try {
            CentralReservations.putValues(SQL, model);

            if(model.getRowCount() == 0){
                throw new SQLException();
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void valueChanged(ListSelectionEvent event) {
        int viewRow = table.getSelectedRow();

        if(viewRow >= 0){
            int modelRow = table.convertRowIndexToModel(viewRow);
            String idStr = (String) model.getValueAt(modelRow, 0);
            id = Integer.parseInt(idStr);
        }
    }

    

    public Integer getRoomID() {
        return id;
    }
    
    private class EditDialog extends JDialog {
    	private JTable dialogTable;
		private JComboBox<String> roomTypeComboBox, bedTypeComboBox, qualityLevelComboBox;
		private JSpinner roomNumber;
		private JCheckBox smokingCheckBox;
		
		public EditDialog(JTable owner) {
			super(javax.swing.SwingUtilities.windowForComponent(owner));
			dialogTable = owner;
			createGUI();
		}

		private void createGUI() {
			setPreferredSize(new Dimension(600, 400));
			setTitle(getClass().getSimpleName());
			JPanel listPane = new JPanel();
			
			listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
			JLabel label = new JLabel();
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			listPane.add(label);
			
			int row = dialogTable.getSelectedRow();
			
			JPanel formPanel = new JPanel(new GridLayout(2, 0));

	        formPanel.add(new JLabel("RoomNumber"));
	        formPanel.add(new JLabel("roomtype"));
	        formPanel.add(new JLabel("bedtype"));
	        formPanel.add(new JLabel("Qualitylevel"));
	        formPanel.add(new JLabel("smokingallowed"));
	        
	        JSpinner roomNumber = new JSpinner(new SpinnerNumberModel(100, 100, 399, 1));
	        //Remove increment buttons
	        for (Component component : roomNumber.getComponents()) {
	            if (component.getName() != null && component.getName().endsWith("Button")) {
	                roomNumber.remove(component);
	            }
	        }
	        formPanel.add(roomNumber);
	        
	        roomTypeComboBox = new JComboBox<String>();
	        updateRoomTypes();
	        roomNumber.addChangeListener(e -> updateRoomTypes());
	        formPanel.add(roomTypeComboBox);
	        
	        bedTypeComboBox = new JComboBox<>(new String[] {"Twin", "Full", "Queen", "King"});
	        formPanel.add(bedTypeComboBox);
	        
	        qualityLevelComboBox = new JComboBox<>(new String[]{"Executive", "Business", "Comfort", "Economy"});
	        formPanel.add(qualityLevelComboBox);
	        
	        smokingCheckBox = new JCheckBox();
	        formPanel.add(smokingCheckBox);

	        add(formPanel);
			
			formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			listPane.add(formPanel);
			JButton addButton;
			if (id < 0) {
				addButton = new JButton("Add Room");
				addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO: Add check to prevent adding an existing room number
						String roomData = roomNumber.getValue() + ",VaCl," + roomTypeComboBox.getSelectedItem() + "," + bedTypeComboBox.getSelectedItem() + ","  + qualityLevelComboBox.getSelectedItem() + ","  + smokingCheckBox.isSelected();
						if (JOptionPane.showConfirmDialog(formPanel, "Are you sure you want to add this room?", "Confirm Room Details", JOptionPane.YES_NO_OPTION) == 0) {
							try {
								CentralDatabase.insertIntoHotelRoomsData(roomData);
								JOptionPane.showMessageDialog(formPanel, "Room has been added");
							} catch(Exception e1) {
								JOptionPane.showMessageDialog(formPanel, "Room could not be added - " + e1.getCause());
							}
						} else {
							JOptionPane.showMessageDialog(formPanel, "Room was not added");
						}
					}
				});
				
			} else {
				addButton = new JButton("Edit Room");
				roomNumber.setValue(dialogTable.getValueAt(id, 0));
				roomNumber.setEnabled(false);
				updateRoomTypes();
				setRoomTypeComboBox();
				setBedTypeComboBox();
				setQualityLevelComboBox();
				setSmokingCheckbox();
				
				addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO: Add check to prevent adding an existing room number
						String roomData = roomNumber.getValue() + ",VaCl," + roomTypeComboBox.getSelectedItem() + "," + bedTypeComboBox.getSelectedItem() + ","  + qualityLevelComboBox.getSelectedItem() + ","  + smokingCheckBox.isSelected();
						if (JOptionPane.showConfirmDialog(formPanel, "Are you sure you want to edit room #" + roomNumber.getValue() + "?", "Confirm Room Details", JOptionPane.YES_NO_OPTION) == 0) {
							try {
								CentralDatabase.updateHotelRoomsData(roomData);
								JOptionPane.showMessageDialog(formPanel, "Room # " + roomNumber.getValue() + " has been edited");
							} catch(Exception e1) {
								JOptionPane.showMessageDialog(formPanel, "Room could not be edited - " + e1.getCause());
							}
						} else {
							JOptionPane.showMessageDialog(formPanel, "Room was not edited");
						}
					}
				});
			}
			listPane.add(addButton);
			
			add(listPane);
			
			setSize(500, 200);
			setLocationRelativeTo(getParent());
			setVisible(true);
		}
		
		private void updateRoomTypes() {
	        roomTypeComboBox.removeAllItems();
	        if ((int) roomNumber.getValue() >= 100 && (int) roomNumber.getValue() < 200) {
	            roomTypeComboBox.addItem("Single");
	            roomTypeComboBox.addItem("Double");
	            roomTypeComboBox.addItem("Family");
	        } else if ((int) roomNumber.getValue() >= 200 && (int) roomNumber.getValue() < 300) {
	            roomTypeComboBox.addItem("Suite");
	            roomTypeComboBox.addItem("Deluxe");
	        } else if ((int) roomNumber.getValue() >= 300 && (int) roomNumber.getValue() < 400) {
	            roomTypeComboBox.addItem("Standard");
	            roomTypeComboBox.addItem("Deluxe");
	        }
	    }
		
		private void setRoomTypeComboBox() {
			String input = (String) dialogTable.getModel().getValueAt(id, 1);
			if ((int) roomNumber.getValue() >= 100 && (int) roomNumber.getValue() < 200) {
	            if (input.equals("Single")) {
	            	roomTypeComboBox.setSelectedIndex(0);
	            } else if (input.equals("Double")) {
	            	roomTypeComboBox.setSelectedIndex(1);
	            } else if (input.equals("Family")) {
	            	roomTypeComboBox.setSelectedIndex(2);
	            }
	        } else if ((int) roomNumber.getValue() >= 200 && (int) roomNumber.getValue() < 300) {
	        	if (input.equals("Suite")) {
	            	roomTypeComboBox.setSelectedIndex(0);
	            } else if (input.equals("Deluxe")) {
	            	roomTypeComboBox.setSelectedIndex(1);
	            }
	        } else if ((int) roomNumber.getValue() >= 300 && (int) roomNumber.getValue() < 400) {
	        	if (input.equals("Standard")) {
	            	roomTypeComboBox.setSelectedIndex(0);
	            } else if (input.equals("Deluxe")) {
	            	roomTypeComboBox.setSelectedIndex(1);
	            }
	        }
		}
		
		private void setBedTypeComboBox() {
			String input = (String) dialogTable.getModel().getValueAt(id, 2);
			if (input.equals("Twin")) {
				bedTypeComboBox.setSelectedIndex(0);
			} else if (input.equals("Full")) {
				bedTypeComboBox.setSelectedIndex(1);
			} else if (input.equals("Queen")) {
				bedTypeComboBox.setSelectedIndex(2);
			} else if (input.equals("King")) {
				bedTypeComboBox.setSelectedIndex(3);
			}
		}
		
		private void setQualityLevelComboBox() {
			String input = (String) dialogTable.getModel().getValueAt(id, 3);
			if (input.equals("Executive")) {
				qualityLevelComboBox.setSelectedIndex(0);
			} else if (input.equals("Business")) {
				qualityLevelComboBox.setSelectedIndex(1);
			} else if (input.equals("Comfort")) {
				qualityLevelComboBox.setSelectedIndex(2);
			} else if (input.equals("Economy")) {
				qualityLevelComboBox.setSelectedIndex(3);
			}
		}
		
		private void setSmokingCheckbox() {
			String input = (String) dialogTable.getModel().getValueAt(id, 6);
			Boolean check = Boolean.valueOf(input);
			smokingCheckBox.setSelected(check);
		}
			
		@Override
		public void dispose() {
			super.dispose();
		}
    };
}
