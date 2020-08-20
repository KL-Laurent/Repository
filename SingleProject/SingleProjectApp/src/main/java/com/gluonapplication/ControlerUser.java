package com.gluonapplication;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.mail.util.BEncoderStream;

import javafx.beans.property.SimpleObjectProperty;
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

public class ControlerUser implements Initializable{

    @FXML
    private JFXTreeTableView<Base_view_user> tableUser;

    @FXML
    private JFXButton bUserNew;

    @FXML
    private JFXButton bUserEdit;

    @FXML
    private JFXButton bUserSuppr;

    @FXML
    private JFXButton bSave;

    @FXML
    private JFXButton bCancel;
    
    @FXML
    private TextField fieldSearch;
    
    @FXML
    private Label labelUtilisateur;
    
 
    private TreeTableViewSelectionModel<Base_view_user> listeSelectionUser;
    
   	private ObservableList<Base_view_user> listeUser = FXCollections.observableArrayList();
  
    
    @FXML
    private AnchorPane pane; 
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initTableContenu();
		initSearch();
		loadUser();
		bUserNew.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			new messageBar(Langage.messageAjouter(), 4, pane);
			System.out.println(Langage.messageAjouter());
			nouveauUser();
		});
		
		bUserEdit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			if(alerteMessage(Langage.confirmationEdit())) {
				new messageBar(Langage.messageDeverouiller(), 4, pane);
				unlockSelection();
			}

		});
		
		bSave.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			if(alerteMessage(Langage.confirmationUpdate())) {
				if(listeSelectionUser.getSelectedItem().getValue().isEdited)
					updateSelection();
				else if(listeSelectionUser.getSelectedItem().getValue().isNews)
					saveSelection();
			}

		});
		
		
		labelUtilisateur.setText(Langage.user());
		
		bUserEdit.setDisable(true);
		bUserSuppr.setDisable(true);
		
		// !!!
		bUserSuppr.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			//loadUser();
			supprUser();
		});
		
		bSave.setDisable(true);
		
		bCancel.setVisible(false);
		
		fieldSearch.setPromptText(Langage.buttonSearch());
		
		bSave.setText(Langage.bEnregistrer());
		
		//bCancel.setDisable(true);
		//bSave.setDisable(true);
	}
	
	private void nouveauUser() {
		listeUser.add(new Base_view_user());
	}
	
	private void loadUser() {
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeUser.setAll(connection.select_user());
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private  void supprUser() {
		if(alerteMessage(Langage.confirmationSuppr())) {
			SQLConnection connection;
			boolean isOk  = true ; 
			try {
				connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
				connection.deleteUser(listeSelectionUser.getSelectedItem().getValue());
				connection.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				new messageBar(e.getMessage(), 5, pane);
				isOk = false; 
				e.printStackTrace();
			}
			
			if(isOk) {
				new messageBar(Langage.supprimer(), 5, pane);
				loadUser();
			}
		}
	}
	
	private void initTableContenu() {
		JFXTreeTableColumn<Base_view_user, JFXTextField> nom = new JFXTreeTableColumn<>(Langage.tableNom());
		// libelle.setPrefWidth(100);
		nom.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.20));
		nom.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_user, JFXTextField> param) {
						return param.getValue().getValue().fieldNom;
					}
				});

		Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>> factoryNom = new Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, JFXTextField> param) {
				final TreeTableCell<Base_view_user, JFXTextField> cell = new TreeTableCell<Base_view_user, JFXTextField>() {

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
		nom.setCellFactory(factoryNom);
		
		JFXTreeTableColumn<Base_view_user, JFXTextField> address = new JFXTreeTableColumn<>(Langage.tableAdresse());
		// libelle.setPrefWidth(100);
		address.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.20));
		address.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_user, JFXTextField> param) {
						return param.getValue().getValue().fieldAddress;
					}
				});

		Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>> factoryAddress = new Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, JFXTextField> param) {
				final TreeTableCell<Base_view_user, JFXTextField> cell = new TreeTableCell<Base_view_user, JFXTextField>() {

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
		address.setCellFactory(factoryAddress);
		
		JFXTreeTableColumn<Base_view_user, JFXTextField> mail = new JFXTreeTableColumn<>(Langage.tableMail());
		// libelle.setPrefWidth(100);
		mail.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.20));
		mail.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_user, JFXTextField> param) {
						return param.getValue().getValue().fieldMail;
					}
				});

		Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>> factoryMail = new Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, JFXTextField> param) {
				final TreeTableCell<Base_view_user, JFXTextField> cell = new TreeTableCell<Base_view_user, JFXTextField>() {

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
		mail.setCellFactory(factoryMail);
		
		JFXTreeTableColumn<Base_view_user, JFXTextField> numTel = new JFXTreeTableColumn<>(Langage.tableTelephone());
		// libelle.setPrefWidth(100);
		numTel.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.20));
		numTel.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_user, JFXTextField> param) {
						return param.getValue().getValue().fieldNumtel;
					}
				});

		Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>> factoryNum = new Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, JFXTextField> param) {
				final TreeTableCell<Base_view_user, JFXTextField> cell = new TreeTableCell<Base_view_user, JFXTextField>() {

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
		numTel.setCellFactory(factoryNum);
		
		JFXTreeTableColumn<Base_view_user, JFXComboBox<String>> type = new JFXTreeTableColumn<>(Langage.tableType());
		// libelle.setPrefWidth(100);
		type.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.17));
		type.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
					@Override
					public ObservableValue<JFXComboBox<String>> call(
							CellDataFeatures<Base_view_user, JFXComboBox<String>> param) {
						return param.getValue().getValue().fieldType;
					}
				});

		Callback<TreeTableColumn<Base_view_user, JFXComboBox<String>>, TreeTableCell<Base_view_user, JFXComboBox<String>>> factoryType = new Callback<TreeTableColumn<Base_view_user, JFXComboBox<String>>, TreeTableCell<Base_view_user, JFXComboBox<String>>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, JFXComboBox<String>> param) {
				final TreeTableCell<Base_view_user, JFXComboBox<String>> cell = new TreeTableCell<Base_view_user, JFXComboBox<String>>() {

					@Override
					public void updateItem(JFXComboBox<String> item, boolean empty) {
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
		type.setCellFactory(factoryType);
		
		
		JFXTreeTableColumn<Base_view_user, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_user, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_user, Label>, TreeTableCell<Base_view_user, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_user, Label>, TreeTableCell<Base_view_user, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, Label> param) {
				final TreeTableCell<Base_view_user, Label> cell = new TreeTableCell<Base_view_user, Label>() {
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
		
		JFXTreeTableColumn<Base_view_user, JFXTextField> id = new JFXTreeTableColumn<>(Langage.tableID());
		// libelle.setPrefWidth(100);
		id.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.20));
		id.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_user, JFXTextField> param) {
						return param.getValue().getValue().fieldId;
					}
				});

		Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>> factoryID = new Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, JFXTextField> param) {
				final TreeTableCell<Base_view_user, JFXTextField> cell = new TreeTableCell<Base_view_user, JFXTextField>() {

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
		id.setCellFactory(factoryID);
		
		JFXTreeTableColumn<Base_view_user, JFXTextField> password = new JFXTreeTableColumn<>(Langage.tablePassword());
		// libelle.setPrefWidth(100);
		password.prefWidthProperty().bind(tableUser.widthProperty().multiply(0.20));
		password.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_user, JFXTextField> param) {
						return param.getValue().getValue().fieldPass;
					}
				});

		Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>> factoryPassword = new Callback<TreeTableColumn<Base_view_user, JFXTextField>, TreeTableCell<Base_view_user, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user, JFXTextField> param) {
				final TreeTableCell<Base_view_user, JFXTextField> cell = new TreeTableCell<Base_view_user, JFXTextField>() {

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
		password.setCellFactory(factoryPassword);
		
		
		listeSelectionUser = tableUser.getSelectionModel();
		tableUser.setEditable(true);
		TreeItem<Base_view_user> root = new RecursiveTreeItem<Base_view_user>(listeUser,RecursiveTreeObject::getChildren);
		tableUser.getColumns().setAll( marque ,nom , mail , address , numTel , type ,id,password);
		tableUser.setRoot(root);
		tableUser.setShowRoot(false);
		tableUser.setPlaceholder(new Label("Press button new (+) for add new liste"));
		tableUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				bUserEdit.setDisable(false);
				bUserSuppr.setDisable(false);
				if((newSelection.getValue().isEdited)||(newSelection.getValue().isNews)) {
					bSave.setDisable(false);
				}
				else {
					bSave.setDisable(true);

				}
			}
			else {
				bUserEdit.setDisable(true);
				bSave.setDisable(true);
				bUserSuppr.setDisable(true);
			}
		});
	}
	
	private void unlockSelection() {
		// C DU A UN BUG DE JAVAFX DONC JE DOIT LE LANCER 2 FOIS , OU BIEN JE SUIS TOTALMENT NOOB
		tableUser.getSelectionModel().getSelectedItem().getValue().unlockField();
		tableUser.getSelectionModel().getSelectedItem().getValue().unlockField();

		bSave.setDisable(false);
	}
	
	private void updateSelection() {
	
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			connection.update_user(tableUser.getSelectionModel().getSelectedItem().getValue());
			connection.close();
			new messageBar(Langage.messageUpdate(), 4, pane);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			new messageBar(e.getMessage(), 4, pane);
			e.printStackTrace();
		}
	
		tableUser.getSelectionModel().getSelectedItem().getValue().lockField();
		bSave.setDisable(true);		
	
	}
	
	
	private void saveSelection() {
		SQLConnection connection;
		boolean test = true ; 
		if(listeSelectionUser.getSelectedItem().getValue().fieldNom.getValue().getText().trim().equals("")) {
			new messageBar(Langage.fieldEmpty() , 5, pane);
			test = false;
		}
		if(listeSelectionUser.getSelectedItem().getValue().fieldId.getValue().getText().trim().equals("")) {
			new messageBar(Langage.fieldEmpty() , 5, pane);
			test = false;

		}
		if(listeSelectionUser.getSelectedItem().getValue().fieldMail.getValue().getText().trim().equals("")) {
			new messageBar(Langage.fieldEmpty() , 5, pane);
			test = false;

		}
		if(listeSelectionUser.getSelectedItem().getValue().fieldAddress.getValue().getText().trim().equals("")) {
			new messageBar(Langage.fieldEmpty() , 5, pane);
			test = false;

		}
		if(listeSelectionUser.getSelectedItem().getValue().fieldPass.getValue().getText().trim().equals("")) {
			new messageBar(Langage.fieldEmpty() , 5, pane);		
			test = false;

		}
		
		if(listeSelectionUser.getSelectedItem().getValue().fieldType.getValue().getSelectionModel().isEmpty()) {
			new messageBar(Langage.fieldEmpty() , 5, pane);		
			test = false;
		}
		
		if(test == true ) {
			try {
				connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
				connection.insert_user(listeSelectionUser.getSelectedItem().getValue());
				connection.close();
				new messageBar(Langage.messageSave(), 4, pane);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				new messageBar(e.getMessage(), 4, pane);
				e.printStackTrace();
			}
		}
	}
	
	private void initSearch() {
		fieldSearch.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String  newValue) {
				tableUser.setPredicate(new Predicate<TreeItem<Base_view_user>>() {	
					@Override
					public boolean test(TreeItem<Base_view_user> mat) {
						// TODO Auto-generated method stub
						boolean flag = notNULL(mat.getValue().fieldNom.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().fieldAddress.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().fieldId.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().fieldMail.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().fieldPass.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().fieldType.getValue().getSelectionModel().getSelectedItem()).toUpperCase().contains(newValue.toUpperCase())

								;
						return flag ; 
					}
				});				
			}
		});
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
