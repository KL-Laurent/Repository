package com.gluonapplication;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;

public class ControlerDrawerListePanne implements Initializable{


    @FXML
    private JFXTreeTableView<Base_view_user_selection> tableResponsable;

    private TreeTableViewSelectionModel<Base_view_user_selection> listeSelectionUser;

    @FXML
    private JFXButton bResponsableNew;

    @FXML
    private JFXButton bResponsableSuppr;

    @FXML
    private JFXButton bEnvoyer;

    @FXML
    private JFXButton bCancel;
    
    private ObservableList<Base_view_user_selection> listeResponsable  =  FXCollections.observableArrayList(); 
	
    @FXML
    private AnchorPane pane;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	
    	
		initTableResponsable();
		bResponsableNew.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			newResponsable();
		});
		
		bResponsableSuppr.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			if(alerteMessage(Langage.confirmationSuppr())) {
				suprResponsable(this.listeSelectionUser.getSelectedIndex());
			}
			
		});
				
		bEnvoyer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			if(alerteMessage(Langage.confirmationEnvoye())) {
				send();
			}
		});
		
		this.bResponsableSuppr.setDisable(true);
		listeResponsable.setAll();
	}

    private void send() {
        try {
        	Mail mail = new Mail();
        	String Newligne=System.getProperty("line.separator");
        	for(int i = 0 ; i < listeResponsable.size();i++) {
        		mail.send(
        					listeResponsable.get(i).getMailUser(),
        					"Claims management",
        					"Equipment failure",
        					CONFIG.user.nom.getValue()+"," + Newligne + ControlerTCRListePanne.selectedContenu.libelle_engin.getValue()  +" : " + ControlerTCRListePanne.selectedContenu.libelle_equipement.getValue() ) 
        		;
        					
    			new messageBar(Langage.OPFicheButtonEnvoyer(), 4, pane);
            	SQLConnection connection;
				try {
					connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
	            	connection.insert_mail(listeResponsable.get(i).selectedGkey(), CONFIG.user.gkey, ControlerTCRListePanne.selectedContenu.gkey);
	            	connection.close();
				} catch (ClassNotFoundException | SQLException e) {
					new messageBar(e.getMessage(), 4, pane);
					e.printStackTrace();
				}

        	}
        	
		}catch(AddressException e) {
			new messageBar(e.getMessage(), 4, pane);
		}
		catch(SendFailedException e) {
			new messageBar(e.getMessage(), 4, pane);
		}
	    catch(MessagingException e) {
			new messageBar(e.getMessage(), 4, pane);
		}



        
    	//new messageBar(" ??? ", 4, pane);

    }
    
	private void newResponsable() {
		listeResponsable.add(new Base_view_user_selection());
	}
	
	public void suprResponsable(int index) {
	     TreeItem<Base_view_user_selection> selectedItem = listeSelectionUser.getModelItem(index);
	     TreeItem<Base_view_user_selection> parent = selectedItem.getParent();
	     
	     // TRES IMPORTANT !!!! SA SUPPRIME DE L'OBSERVABLE LISTE   rt 
	     listeResponsable.remove(selectedItem.getValue());

	     if(true) {
	    	  if (parent != null)
		        {
		            parent.getChildren().remove(selectedItem);  
		        }
		        else
		        {
		            tableResponsable.setRoot(null);
		        }
	     }
	}
	
	private void initTableResponsable() {
		
		JFXTreeTableColumn<Base_view_user_selection, JFXComboBox<String>> responsable = new JFXTreeTableColumn<>("responsable");
		// libelle.setPrefWidth(100);
		responsable.prefWidthProperty().bind(tableResponsable.widthProperty().multiply(0.85));
		responsable.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user_selection, JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
					@Override
					public ObservableValue<JFXComboBox<String>> call(
							CellDataFeatures<Base_view_user_selection, JFXComboBox<String>> param) {
						return param.getValue().getValue().comboNom;
					}
				});

		Callback<TreeTableColumn<Base_view_user_selection, JFXComboBox<String>>, TreeTableCell<Base_view_user_selection, JFXComboBox<String>>> factoryResponsable = new Callback<TreeTableColumn<Base_view_user_selection, JFXComboBox<String>>, TreeTableCell<Base_view_user_selection, JFXComboBox<String>>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user_selection, JFXComboBox<String>> param) {
				final TreeTableCell<Base_view_user_selection, JFXComboBox<String>> cell = new TreeTableCell<Base_view_user_selection, JFXComboBox<String>>() {

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
		
		JFXTreeTableColumn<Base_view_user_selection, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableResponsable.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user_selection, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_user_selection, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_user_selection, Label>, TreeTableCell<Base_view_user_selection, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_user_selection, Label>, TreeTableCell<Base_view_user_selection, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user_selection, Label> param) {
				final TreeTableCell<Base_view_user_selection, Label> cell = new TreeTableCell<Base_view_user_selection, Label>() {
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
		
		listeSelectionUser = tableResponsable.getSelectionModel();

		listeResponsable.setAll();

		tableResponsable.setEditable(true);
		TreeItem<Base_view_user_selection> root = new RecursiveTreeItem<Base_view_user_selection>(listeResponsable,RecursiveTreeObject::getChildren);
		tableResponsable.getColumns().setAll(marque, responsable);
		tableResponsable.setRoot(root);
		tableResponsable.setShowRoot(false);
		tableResponsable.setPlaceholder(new Label(Langage.vide()));
		tableResponsable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.bResponsableSuppr.setDisable(false);
			}
		});
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
