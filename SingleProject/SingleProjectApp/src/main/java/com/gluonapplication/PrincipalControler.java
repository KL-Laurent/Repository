package com.gluonapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class PrincipalControler implements Initializable{

    @FXML
    private AnchorPane panePrincipal;
    
    @FXML
    private JFXButton bcancel;

    @FXML
    private JFXButton bsubmit;
    @FXML
    private JFXButton bnew;

    @FXML
    private JFXButton bparametre;

    @FXML
    private JFXTreeTableView<Conteneur> listView;

    @FXML
    private JFXComboBox<String> comboClaimType;
    
    SQLConnection connection ;
 	
    private ObservableList<Conteneur> liste = FXCollections.observableArrayList();

    
    @FXML private TreeTableViewSelectionModel<Conteneur> listeSelection ;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initializeComboDescription();
		initializeListView();
		bnew.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			newList();
		});
		bsubmit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			submit();
		});
		bcancel.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			cancel();
		});
		
		bparametre.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			parametre();
		});
		
		//new Mail();
	}
	
	
	private void parametre() {
		AnchorPane p = (AnchorPane) panePrincipal.getParent();
		Region reg;
		try {
			reg = FXMLLoader.load(getClass().getResource("Parametre.fxml"));
			p.getChildren().clear();
			p.getChildren().add(reg);
			p.setVisible(true);
			reg.prefWidthProperty().bind(p.widthProperty());
			reg.prefHeightProperty().bind(p.heightProperty());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void submit() {
		System.out.print(liste.get(0).claimsType.getValue().getValue());
	}
	
	public void cancel() {
		//if(!liste.isEmpty())
			//liste.clear();
		AnchorPane p = (AnchorPane) panePrincipal.getParent();
		Region reg;
		try {
			reg = FXMLLoader.load(getClass().getResource("Login.fxml"));
			p.getChildren().clear();
			p.getChildren().add(reg);
			p.setVisible(true);
			reg.prefWidthProperty().bind(p.widthProperty());
			reg.prefHeightProperty().bind(p.heightProperty());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializeComboDescription() {
		try {
			connection = new SQLConnection("127.0.0.1","49708","Claims","KL","kl141294");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ObservableList<String> list = connection.select_claims_type();
		connection.close();
		//comboClaimType.setItems(list);
	}
	
	public void newList() {
		liste.add(new Conteneur());
	}
	
	public void deleteList(int index) {
		 Alert a = new Alert(AlertType.CONFIRMATION,"Delete this item ?");
		 a.showAndWait().ifPresent(response -> {
		     if (response == ButtonType.OK) {
		    	 System.out.print(index);
			     TreeItem<Conteneur> selectedItem = listeSelection.getModelItem(index);
			     TreeItem<Conteneur> parent = selectedItem.getParent();
			     
			     if(true) {
			    	  if (parent != null)
				        {
				            parent.getChildren().remove(selectedItem);  
				        }
				        else
				        {
				            listView.setRoot(null);
				        }
			     }
		     }
		 });    
	}

	public void initializeListView() {
		JFXTreeTableColumn<Conteneur, JFXComboBox<String>> claimsType  = new JFXTreeTableColumn<>("Claims Type");
		claimsType.setPrefWidth(500);
		claimsType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Conteneur,JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
			@Override
			public ObservableValue<JFXComboBox<String>> call(CellDataFeatures<Conteneur, JFXComboBox<String>> param) {
				return param.getValue().getValue().claimsType;
			}
		});
		
		Callback<TreeTableColumn<Conteneur, JFXComboBox<String>>, TreeTableCell<Conteneur, JFXComboBox<String>>> ClaimsTFactory
        = 
        new Callback<TreeTableColumn<Conteneur, JFXComboBox<String>>, TreeTableCell<Conteneur, JFXComboBox<String>>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<Conteneur, JFXComboBox<String>> param) {
                final TreeTableCell<Conteneur, JFXComboBox<String>> cell = new TreeTableCell<Conteneur, JFXComboBox<String>>() {
                    JFXComboBox<String>  cbox = new JFXComboBox<String>();

                	@Override
                    public void updateItem(JFXComboBox<String> item, boolean empty) {
                   	 super.updateItem(item, empty);
                        if (!isEmpty()) {
                            setGraphic(item);
                        }
                        else {
                       	 setGraphic(null);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        claimsType.setCellFactory(ClaimsTFactory);
        
        
        JFXTreeTableColumn<Conteneur, JFXTextArea> ClaimsDescriptio  = new JFXTreeTableColumn<>("Claims Decription");
		ClaimsDescriptio.setPrefWidth(500);
		ClaimsDescriptio.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Conteneur,JFXTextArea>, ObservableValue<JFXTextArea>>() {
			@Override
			public ObservableValue<JFXTextArea> call(CellDataFeatures<Conteneur, JFXTextArea> param) {
				return param.getValue().getValue().description;
			}
		});
		
		Callback<TreeTableColumn<Conteneur, JFXTextArea>, TreeTableCell<Conteneur, JFXTextArea>> claimsD
        = 
        new Callback<TreeTableColumn<Conteneur, JFXTextArea>, TreeTableCell<Conteneur, JFXTextArea>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<Conteneur, JFXTextArea> param) {
                final TreeTableCell<Conteneur, JFXTextArea> cell = new TreeTableCell<Conteneur, JFXTextArea>() {
                   
                	@Override
                    public void updateItem(JFXTextArea item, boolean empty) {
                   	 super.updateItem(item, empty);
                        if (!isEmpty()) {
                       	 		setGraphic(item);
                        }
                        else {
                       	 setGraphic(null);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        ClaimsDescriptio.setCellFactory(claimsD);
        
        
        
    	JFXTreeTableColumn<Conteneur, String> cancelColumn = new JFXTreeTableColumn<>("Cancel");
		cancelColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Conteneur,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Conteneur, String> param) {
				return param.getValue().getValue().cancel;
			}
			
		});
        cancelColumn.setPrefWidth(150);
        Callback<TreeTableColumn<Conteneur, String>, TreeTableCell<Conteneur, String>> cancelFactory
        = 
        new Callback<TreeTableColumn<Conteneur, String>, TreeTableCell<Conteneur, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<Conteneur, String> param) {
                final TreeTableCell<Conteneur, String> cell = new TreeTableCell<Conteneur, String>() {

                    JFXButton btn = new JFXButton("Delete");
           
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);  
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.FLAT);
                            btn.setStyle("-fx-fill : #ffffff;"
                            		+ "-fx-background-color : #a2ee94");
                            btn.setMinSize(150, 50);
                            btn.setTextFill(Color.WHITE);
                            btn.setOnAction(event -> {
                            	deleteList(this.getIndex());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        cancelColumn.setCellFactory(cancelFactory);
      		listeSelection = listView.getSelectionModel();
      		//listeSelection.setSelectionMode(SelectionMode.SINGLE);
      		//listeSelection.setCellSelectionEnabled(true);
      		listView.setEditable(true);
      		TreeItem<Conteneur> root = new RecursiveTreeItem<Conteneur>(liste, RecursiveTreeObject::getChildren);	
      		listView.getColumns().setAll(claimsType,ClaimsDescriptio,cancelColumn); 
      		//nomName.prefWidthProperty().bind(tableListe.maxWidthProperty());
      		listView.setRoot(root);
      		listView.setShowRoot(false);
      		//s affiche quand la table est vide
      		listView.setPlaceholder(new Label("Press button new (+) for add new liste"));
	
	}
}