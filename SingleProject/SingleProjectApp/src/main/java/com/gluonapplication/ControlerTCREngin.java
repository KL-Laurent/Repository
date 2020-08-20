package com.gluonapplication;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ControlerTCREngin implements Initializable {

    @FXML
    private JFXTreeTableView<Base_view_engin> tableEngin;

    @FXML
    private JFXButton bEnginNew;

    @FXML
    private JFXButton bEnginSuppr;

    @FXML
    private JFXButton bEnginEdit;

    @FXML
    private JFXButton bSave;

    @FXML
    private JFXButton bCancel;
   
    @FXML
    private TextField fieldSearch;
    
    @FXML
    private Label labelEngin;
    @FXML
    private AnchorPane paneMain;
    
    private TreeTableViewSelectionModel<Base_view_engin> listeSelectionEngin;
    
	private ObservableList<Base_view_engin> listeEngin = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		bEnginEdit.setDisable(true);
		bSave.setDisable(true);
		bCancel.setDisable(true);
		
		bEnginNew.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			new messageBar(Langage.messageAjouter(), 4, paneMain);
			newEngin();
		});
		bEnginEdit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			if(alerteMessage(Langage.confirmationEdit())) {
				new messageBar(Langage.messageDeverouiller(), 4, paneMain);
				listeSelectionEngin.getSelectedItem().getValue().unlockField();
				listeSelectionEngin.getSelectedItem().getValue().unlockField();
			}

		});
		
		bCancel.setVisible(false);
		bSave.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			if(alerteMessage(Langage.confirmationUpdate())) {
								if(listeSelectionEngin.getSelectedItem().getValue().isEdited) {
					updateEngin();
					
					listeSelectionEngin.getSelectedItem().getValue().lockField();
				}
				else if(listeSelectionEngin.getSelectedItem().getValue().isNews) {
					save();
				}
			}
			
			load();
		});
		
		labelEngin.setText(Langage.tCREnginLabelEngin());
		
		bSave.setText(Langage.bEnregistrer());
		
		/// !!!! 
		bEnginSuppr.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			suppr();
		});
		
		initTableContenu();
		initSearch();
		load();
	}
	
	private void newEngin() {
		listeEngin.add(new Base_view_engin());
	}
	
	private void initTableContenu() {
		JFXTreeTableColumn<Base_view_engin, JFXTextField> libelle = new JFXTreeTableColumn<>("libellé");
		// libelle.setPrefWidth(100);
		libelle.prefWidthProperty().bind(tableEngin.widthProperty().multiply(0.32));
		libelle.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_engin, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_engin, JFXTextField> param) {
						return param.getValue().getValue().libelle;
					}
				});

		Callback<TreeTableColumn<Base_view_engin, JFXTextField>, TreeTableCell<Base_view_engin, JFXTextField>> factorycommentaire = new Callback<TreeTableColumn<Base_view_engin, JFXTextField>, TreeTableCell<Base_view_engin, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_engin, JFXTextField> param) {
				final TreeTableCell<Base_view_engin, JFXTextField> cell = new TreeTableCell<Base_view_engin, JFXTextField>() {

					@Override
					public void updateItem(JFXTextField item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							setGraphic(item);
						} else {
							setGraphic(null);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		libelle.setCellFactory(factorycommentaire);
		
		
		JFXTreeTableColumn<Base_view_engin, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableEngin.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_engin, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_engin, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_engin, Label>, TreeTableCell<Base_view_engin, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_engin, Label>, TreeTableCell<Base_view_engin, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_engin, Label> param) {
				final TreeTableCell<Base_view_engin, Label> cell = new TreeTableCell<Base_view_engin, Label>() {
					@Override
					public void updateItem(Label item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.setStyle("-fx-background-color : " + GlobalColor.colore1);
							setGraphic(item);
						} else {
							setGraphic(null);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		marque.setCellFactory(factoryMarque);
		
		JFXTreeTableColumn<Base_view_engin, JFXComboBox<String>> typeEngin = new JFXTreeTableColumn<>(Langage.tCRTypeEnginLabelTypeEngin());
		// libelle.setPrefWidth(100);
		typeEngin.prefWidthProperty().bind(tableEngin.widthProperty().multiply(0.32));
		typeEngin.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_engin, JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
					@Override
					public ObservableValue<JFXComboBox<String>> call(
							CellDataFeatures<Base_view_engin, JFXComboBox<String>> param) {
						return param.getValue().getValue().type_Engin;
					}
				});

		Callback<TreeTableColumn<Base_view_engin, JFXComboBox<String>>, TreeTableCell<Base_view_engin, JFXComboBox<String>>> factoryTypeEngin = new Callback<TreeTableColumn<Base_view_engin, JFXComboBox<String>>, TreeTableCell<Base_view_engin, JFXComboBox<String>>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_engin, JFXComboBox<String>> param) {
				final TreeTableCell<Base_view_engin, JFXComboBox<String>> cell = new TreeTableCell<Base_view_engin, JFXComboBox<String>>() {

					@Override
					public void updateItem(JFXComboBox<String> item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.getParent();
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							setGraphic(item);
						} else {
							setGraphic(null);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		
		JFXTreeTableColumn<Base_view_engin, JFXComboBox<String>> responsable = new JFXTreeTableColumn<>(Langage.tableResponsable());
		// libelle.setPrefWidth(100);
		responsable.prefWidthProperty().bind(tableEngin.widthProperty().multiply(0.32));
		responsable.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_engin, JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
					@Override
					public ObservableValue<JFXComboBox<String>> call(
							CellDataFeatures<Base_view_engin, JFXComboBox<String>> param) {
						return param.getValue().getValue().responsable;
					}
				});

		Callback<TreeTableColumn<Base_view_engin, JFXComboBox<String>>, TreeTableCell<Base_view_engin, JFXComboBox<String>>> factoryResponsable = new Callback<TreeTableColumn<Base_view_engin, JFXComboBox<String>>, TreeTableCell<Base_view_engin, JFXComboBox<String>>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_engin, JFXComboBox<String>> param) {
				final TreeTableCell<Base_view_engin, JFXComboBox<String>> cell = new TreeTableCell<Base_view_engin, JFXComboBox<String>>() {

					@Override
					public void updateItem(JFXComboBox<String> item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.getParent();
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							setGraphic(item);
						} else {
							setGraphic(null);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		
		responsable.setCellFactory(factoryResponsable);
		
		listeSelectionEngin = tableEngin.getSelectionModel();
		tableEngin.setEditable(true);
		TreeItem<Base_view_engin> root = new RecursiveTreeItem<Base_view_engin>(listeEngin,RecursiveTreeObject::getChildren);
		tableEngin.getColumns().setAll( marque ,libelle,typeEngin,responsable);
		tableEngin.setRoot(root);
		tableEngin.setShowRoot(false);
		tableEngin.setPlaceholder(new Label(Langage.vide()));
		tableEngin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				bEnginEdit.setDisable(false);
				bSave.setDisable(false);
				bCancel.setDisable(false);


			}
			else {
				bEnginEdit.setDisable(true);
				bSave.setDisable(true);
				bCancel.setDisable(true);
			}
		});
	}
	
	private void save() {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			
			listeSelectionEngin.getSelectedItem().getValue().createur= CONFIG.user.gkey;
			listeSelectionEngin.getSelectedItem().getValue().dateCreation = new SimpleStringProperty(dtf.format(now).toString());
			
			SQLConnection connection;
			try {
				connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
				connection.insert_engin(listeSelectionEngin.getSelectedItem().getValue());
				connection.close();
				new messageBar(Langage.messageSave(), 4, paneMain);

				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				new messageBar(e.getMessage(), 4, paneMain);

				e.printStackTrace();
			}

	}
	
	private void load() {
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeEngin.setAll(connection.select_engin());
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			new messageBar(e.getMessage(), 4, paneMain);

			e.printStackTrace();
		}
	}
	
	private void initSearch() {
		fieldSearch.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String  newValue) {
				tableEngin.setPredicate(new Predicate<TreeItem<Base_view_engin>>() {	
					@Override
					public boolean test(TreeItem<Base_view_engin> mat) {
						// TODO Auto-generated method stub
						boolean flag = notNULL(mat.getValue().libelle.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().type_Engin.getValue().getSelectionModel().getSelectedItem()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().responsable.getValue().getSelectionModel().getSelectedItem()).toUpperCase().contains(newValue.toUpperCase())
								
								;
						return flag ; 
					}
				});				
			}
		});
	}
	
	public void suppr() {
		 Alert a = new Alert(AlertType.CONFIRMATION,Langage.confirmationSuppr());
		 a.showAndWait().ifPresent(response -> {
		    if (response == ButtonType.OK) {
			     TreeItem<Base_view_engin> selectedItem = listeSelectionEngin.getModelItem(listeSelectionEngin.getSelectedIndex());
			     TreeItem<Base_view_engin> parent = selectedItem.getParent();
			     // TRES IMPORTANT !!!! SA SUPPRIME DE L'OBSERVABLE LISTE 
			     
				 
				SQLConnection connection;
				try {
						connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
						connection.delete_engin(listeSelectionEngin.getSelectedItem().getValue());						
						connection.close();

					     listeEngin.remove(selectedItem.getValue());
					     if(true) {
					    	  if (parent != null)
						        {
						            parent.getChildren().remove(selectedItem);  
						        }
						        else
						        {
						            tableEngin.setRoot(null);
						        }
					     }
					     
						new messageBar(Langage.messageAjouter(), 4, paneMain);

				} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						new messageBar(e.getMessage(), 4, paneMain);
						e.printStackTrace();
				}
		     }

		 });    
	}
	
	private void updateEngin() {
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			connection.update_engin(listeSelectionEngin.getSelectedItem().getValue());
			connection.close();
			new messageBar(Langage.messageUpdate(), 4, paneMain);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			new messageBar(e.getMessage(), 4, paneMain);

			e.printStackTrace();
		}

	}
	private String notNULL(String txt) {
		if(txt == null )
			return "null";
		else 
			return txt;
	}
	
	public boolean alerteMessage(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning message");
		alert.setHeaderText("Warning ! ");
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true ;
		} else {
		    return false;
		}
	}
	
	
}
