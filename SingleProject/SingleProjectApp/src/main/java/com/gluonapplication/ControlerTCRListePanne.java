package com.gluonapplication;

import java.awt.event.MouseListener;
import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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

public class ControlerTCRListePanne implements Initializable  {

	

    @FXML
    private JFXButton bDrawer;

    @FXML
    private JFXDrawer Drawer;


    @FXML
    private AnchorPane pane;
    
    @FXML
    private Label labelListeEquipementPanne;
  
    @FXML
    private JFXTreeTableView<Base_view_contenu> tableContenu;
    @FXML
	private TreeTableViewSelectionModel<Base_view_contenu> listeSelectionContenu;
 
	private ObservableList<Base_view_contenu> listeContenu = FXCollections.observableArrayList();


    @FXML
    private JFXButton bMiseAJour;
    
    @FXML
    private TextField bSearch;

    public static Base_view_contenu selectedContenu;
    
    private SQLConnection connection ; 
    
    public static boolean isClosed ; 
    
    @FXML
    private JFXButton bFilter;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Drawer.toBack();
		bDrawer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			initDrawerMessage();
	    	Drawer.toFront();
			Drawer.open();
			
		});
		
		bFilter.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			initDrawerFilter();
	    	Drawer.toFront();
			Drawer.open();
			
		});
		
		bDrawer.setDisable(true);
		bDrawer.setText(Langage.OPFicheButtonEnvoyer());
		
		bMiseAJour.setDisable(true);
		
		initTableContenu();

		initDrawerMessage();
		initSearch();
		
		bMiseAJour.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			if(alerteMessage(Langage.confirmationUpdate())) {
				updateContenu();
				switch(CONFIG.user.type.getValue()){
				case "ADMIN":
					initListe();
					break ; 
				case "TECHNICIEN_ADMIN":
					initListe();
					break ; 
				
				case "MANAGEUR":

					initListe();
					break; 
				
				case "TECHNICIEN":
					initListeTechnicien();
					break ; 
				
				case "TECHNICIEN_CR":
					initListe();
					break ; 
					
			}
				bMiseAJour.setDisable(true);
			}
		});
		
		bSearch.setPromptText(Langage.buttonSearch());
		labelListeEquipementPanne.setText(Langage.tcrListePanneLableListeEquipement());	

	}
	
	private void initTableContenu() {
		JFXTreeTableColumn<Base_view_contenu, JFXTextField> commentaire = new JFXTreeTableColumn<>(Langage.tableCommentaireOperateur());
		// libelle.setPrefWidth(100);
		commentaire.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.37));
		commentaire.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_contenu, JFXTextField> param) {
						return param.getValue().getValue().commentaire;
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, JFXTextField>, TreeTableCell<Base_view_contenu, JFXTextField>> factorycommentaire = new Callback<TreeTableColumn<Base_view_contenu, JFXTextField>, TreeTableCell<Base_view_contenu, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, JFXTextField> param) {
				final TreeTableCell<Base_view_contenu, JFXTextField> cell = new TreeTableCell<Base_view_contenu, JFXTextField>() {

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
		commentaire.setCellFactory(factorycommentaire);
		
	
		
		JFXTreeTableColumn<Base_view_contenu, JFXCheckBox> etat = new JFXTreeTableColumn<>(Langage.tableEtat());
		etat.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.10));;
		etat.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_contenu, JFXCheckBox> param) {
						return param.getValue().getValue().status;
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, JFXCheckBox>, TreeTableCell<Base_view_contenu, JFXCheckBox>> factoryCritique = new Callback<TreeTableColumn<Base_view_contenu, JFXCheckBox>, TreeTableCell<Base_view_contenu, JFXCheckBox>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, JFXCheckBox> param) {
				final TreeTableCell<Base_view_contenu, JFXCheckBox> cell = new TreeTableCell<Base_view_contenu, JFXCheckBox>() {
					@Override
					public void updateItem(JFXCheckBox item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.setStyle("-fx-alignment: CENTER-RIGHT;");
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

		etat.setCellFactory(factoryCritique);
		
		JFXTreeTableColumn<Base_view_contenu, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_contenu, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, Label>, TreeTableCell<Base_view_contenu, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_contenu, Label>, TreeTableCell<Base_view_contenu, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, Label> param) {
				final TreeTableCell<Base_view_contenu, Label> cell = new TreeTableCell<Base_view_contenu, Label>() {
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
		
		JFXTreeTableColumn<Base_view_contenu, String> engin = new JFXTreeTableColumn<>(Langage.tCREnginLabelEngin());
		// libelle.setPrefWidth(100);
		engin.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.13));
		engin.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Base_view_contenu, String> param) {
						return param.getValue().getValue().libelle_engin;
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, String>, TreeTableCell<Base_view_contenu, String>> factoryEngin = new Callback<TreeTableColumn<Base_view_contenu, String>, TreeTableCell<Base_view_contenu, String>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, String> param) {
				final TreeTableCell<Base_view_contenu, String> cell = new TreeTableCell<Base_view_contenu, String>() {

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							setText(item);
						} else {
							setGraphic(null);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		engin.setCellFactory(factoryEngin);
		
		JFXTreeTableColumn<Base_view_contenu, String> dateCreation = new JFXTreeTableColumn<>(Langage.tableDateCreation());
		// libelle.setPrefWidth(100);
		dateCreation.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.20));
		dateCreation.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Base_view_contenu, String> param) {
						return param.getValue().getValue().date_fiche;
					}
				});
		
		dateCreation.setCellFactory(factoryEngin);
		
		JFXTreeTableColumn<Base_view_contenu, String> dateModification = new JFXTreeTableColumn<>(Langage.tableDateModification());
		// libelle.setPrefWidth(100);
		dateModification.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.20));
		dateModification.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Base_view_contenu, String> param) {
						return param.getValue().getValue().dateModification;
					}
				});
		
		dateModification.setCellFactory(factoryEngin);
		
		JFXTreeTableColumn<Base_view_contenu, String> nomCreateur = new JFXTreeTableColumn<>("Operateur");
		// libelle.setPrefWidth(100);
		nomCreateur.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.20));
		nomCreateur.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Base_view_contenu, String> param) {
						return param.getValue().getValue().nom_operateur;
					}
				});
		
		nomCreateur.setCellFactory(factoryEngin);
		
		JFXTreeTableColumn<Base_view_contenu, String> nomModificateur = new JFXTreeTableColumn<>("Modificateur");
		// libelle.setPrefWidth(100);
		nomModificateur.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.20));
		nomModificateur.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Base_view_contenu, String> param) {
						return param.getValue().getValue().nom_modificateur;
					}
				});
		
		nomModificateur.setCellFactory(factoryEngin);
		
		

		JFXTreeTableColumn<Base_view_contenu, String> equipement = new JFXTreeTableColumn<>(Langage.tableEquipement());
		// libelle.setPrefWidth(100);
		equipement.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.20));
		equipement.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Base_view_contenu, String> param) {
						return param.getValue().getValue().libelle_equipement;
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, String>, TreeTableCell<Base_view_contenu, String>> factoryEquipement = new Callback<TreeTableColumn<Base_view_contenu, String>, TreeTableCell<Base_view_contenu, String>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, String> param) {
				final TreeTableCell<Base_view_contenu, String> cell = new TreeTableCell<Base_view_contenu, String>() {

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							
							setText(item);
			
						} else {
							setGraphic(null);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		equipement.setCellFactory(factoryEquipement);
		
		JFXTreeTableColumn<Base_view_contenu, JFXCheckBox> isCLosed = new JFXTreeTableColumn<>(Langage.tableFermer());
		isCLosed.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.13));;
		isCLosed.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_contenu, JFXCheckBox> param) {
						return param.getValue().getValue().isClosed;
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, JFXCheckBox>, TreeTableCell<Base_view_contenu, JFXCheckBox>> factoryIsClosed = new Callback<TreeTableColumn<Base_view_contenu, JFXCheckBox>, TreeTableCell<Base_view_contenu, JFXCheckBox>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, JFXCheckBox> param) {
				final TreeTableCell<Base_view_contenu, JFXCheckBox> cell = new TreeTableCell<Base_view_contenu, JFXCheckBox>() {
					@Override
					public void updateItem(JFXCheckBox item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.setStyle("-fx-alignment: CENTER;");
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

		isCLosed.setCellFactory(factoryIsClosed);
		
		JFXTreeTableColumn<Base_view_contenu, JFXCheckBox> reparer = new JFXTreeTableColumn<>(Langage.tableReparer());
		reparer.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.13));;
		reparer.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_contenu, JFXCheckBox> param) {
						return param.getValue().getValue().reparer;
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, JFXCheckBox>, TreeTableCell<Base_view_contenu, JFXCheckBox>> factoryReparer= new Callback<TreeTableColumn<Base_view_contenu, JFXCheckBox>, TreeTableCell<Base_view_contenu, JFXCheckBox>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, JFXCheckBox> param) {
				final TreeTableCell<Base_view_contenu, JFXCheckBox> cell = new TreeTableCell<Base_view_contenu, JFXCheckBox>() {
					@Override
					public void updateItem(JFXCheckBox item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.setStyle("-fx-alignment: CENTER;");
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
		
		
		
		reparer.setCellFactory(factoryReparer);
		
		JFXTreeTableColumn<Base_view_contenu, JFXTextField> commentaireRP = new JFXTreeTableColumn<>(Langage.tableCommentaireReparation());
		// libelle.setPrefWidth(100);
		commentaireRP.prefWidthProperty().bind(tableContenu.widthProperty().multiply(0.37));
		commentaireRP.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_contenu, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_contenu, JFXTextField> param) {
						return param.getValue().getValue().commentaireRP;
					}
				});

		Callback<TreeTableColumn<Base_view_contenu, JFXTextField>, TreeTableCell<Base_view_contenu, JFXTextField>> factorycommentaireRP = new Callback<TreeTableColumn<Base_view_contenu, JFXTextField>, TreeTableCell<Base_view_contenu, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_contenu, JFXTextField> param) {
				final TreeTableCell<Base_view_contenu, JFXTextField> cell = new TreeTableCell<Base_view_contenu, JFXTextField>() {

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
		commentaireRP.setCellFactory(factorycommentaireRP);
		
		listeSelectionContenu = tableContenu.getSelectionModel();
		tableContenu.setEditable(true);
		TreeItem<Base_view_contenu> root = new RecursiveTreeItem<Base_view_contenu>(listeContenu,RecursiveTreeObject::getChildren);

		tableContenu.setTableMenuButtonVisible(true);
		switch(CONFIG.user.type.getValue()){
			case "ADMIN":
				tableContenu.getColumns().setAll(marque,engin,equipement ,commentaire ,reparer,isCLosed,commentaireRP,dateCreation,dateModification,nomCreateur,nomModificateur);
				initListe();
				break ; 
				
			case "TECHNICIEN_ADMIN":
				tableContenu.getColumns().setAll(marque,engin,equipement ,commentaire ,reparer,isCLosed,commentaireRP,dateCreation,dateModification,nomCreateur,nomModificateur);
				initListe();
				break ; 
			
			case "MANAGEUR":
				tableContenu.getColumns().setAll(marque,engin,equipement ,commentaire ,reparer,isCLosed,commentaireRP,dateCreation,dateModification,nomCreateur,nomModificateur);
				bMiseAJour.setVisible(false);
				bDrawer.setVisible(false);
				initListe();
				break; 
			
			case "TECHNICIEN":
				tableContenu.getColumns().setAll(marque,engin,equipement ,commentaire ,reparer,commentaireRP,dateCreation,dateModification,nomCreateur,nomModificateur);
				bDrawer.setVisible(false);
				initListeTechnicien();
				break ; 
			
			case "TECHNICIEN_CR":
				tableContenu.getColumns().setAll(marque,engin,equipement ,commentaire ,reparer,isCLosed,commentaireRP,dateCreation,dateModification,nomCreateur,nomModificateur);
				initListe();
				break ; 
				
			default : 
				tableContenu.getColumns().setAll(marque,engin,equipement ,commentaire ,commentaireRP,dateCreation,dateModification,nomCreateur,nomModificateur);
				bMiseAJour.setVisible(false);
				bDrawer.setVisible(false);
				break ;
		}		
		tableContenu.setRoot(root);
		tableContenu.setShowRoot(false);
		tableContenu.setPlaceholder(new Label(Langage.vide()));

		
		tableContenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				bDrawer.setDisable(false);
				bMiseAJour.setDisable(false);
			    selectedContenu = listeSelectionContenu.getSelectedItem().getValue();
			}
		});
	}

	public void initDrawerMessage() {
		AnchorPane paneTemp = null ;
		try {
			paneTemp = FXMLLoader.load(getClass().getResource("/DrawerListePanne.fxml"));
			paneTemp.prefHeightProperty().bind(pane.heightProperty());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Drawer.setSidePane(paneTemp);
		for(Node node : paneTemp.getChildren()) {
			System.out.println("ici");
			if(node.getAccessibleText()!= null) {
				node.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
					switch (node.getAccessibleText()){
					case "B_CANCEL" : 
						Drawer.close();
					break;
					}
				});
			}
		}
	}
	
	public void initDrawerFilter() {
		AnchorPane paneTemp = null ;
		try {
			paneTemp = FXMLLoader.load(getClass().getResource("/Filtre.fxml"));
			paneTemp.prefHeightProperty().bind(pane.heightProperty());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Drawer.setSidePane(paneTemp);
		for(Node node : paneTemp.getChildren()) {
			System.out.println("ici");
			if(node.getAccessibleText()!= null) {
				node.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
					switch (node.getAccessibleText()){
					case "B_CANCEL" : 
						Drawer.close();
						
					break;
					
					case "B_OK" :
						switch(CONFIG.user.type.getValue()){
						
						case "TECHNICIEN":
								if(ControlerFiltre.showClosed.get()) {
									System.out.println("not closed");
									initListeTechnicienClosed();
								}
									
								else {
									initListeTechnicien();
								}
								
							break ; 							
						default :
								System.out.println(CONFIG.user.type.getValue());

								if(ControlerFiltre.showClosed.get())
									initListeClosed();
								else
								{
									initListe();
								}
								
							break ;
						}
						
						initFiltre();
						//ControlerFiltre.reset();
						Drawer.close();
						
					break;
					}
				});
			}
		}
	}
	public void initListe() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeContenu.setAll(connection.select_contenu());
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void initListeClosed() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeContenu.setAll(connection.select_contenu_closed());
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initListeTechnicien() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeContenu.setAll(connection.select_contenu_technicien());
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void initListeTechnicienClosed() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeContenu.setAll();
			listeContenu.setAll(connection.select_contenu_technicien_closed());
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initSearch() {
		bSearch.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String  newValue) {
				tableContenu.setPredicate(new Predicate<TreeItem<Base_view_contenu>>() {	
					@Override
					public boolean test(TreeItem<Base_view_contenu> mat) {
						// TODO Auto-generated method stub
						boolean flag = notNULL(mat.getValue().libelle_engin.getValue()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().libelle_equipement.getValue()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().commentaire.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								||notNULL(mat.getValue().commentaireRP.getValue().getText()).toUpperCase().contains(newValue.toUpperCase())
								;
						return flag ; 
					}
				});				
			}
		});
	}
	
	private void initFiltre() {
		tableContenu.setPredicate(new Predicate<TreeItem<Base_view_contenu>>() {	
			@Override
			public boolean test(TreeItem<Base_view_contenu> mat) {
				// TODO Auto-generated method stub

							
				boolean flag = notNULL(mat.getValue().libelle_engin.getValue()).toUpperCase().contains(ControlerFiltre.tengin.getValue().toUpperCase())
						&& notNULL(mat.getValue().dateModification.getValue()).toUpperCase().contains(ControlerFiltre.tDateModification.getValue().toUpperCase())
						&&notNULL(mat.getValue().libelle_equipement.getValue()).toUpperCase().contains(ControlerFiltre.tequipement.getValue().toUpperCase())
						&&notNULL(mat.getValue().date_fiche.getValue()).toUpperCase().contains(ControlerFiltre.tDateCreation.getValue().toUpperCase())
						&&notNULL(mat.getValue().nom_operateur.getValue()).toUpperCase().contains(ControlerFiltre.tcreateur.getValue().toUpperCase())
						&&notNULL(mat.getValue().nom_modificateur.getValue()).toUpperCase().contains(ControlerFiltre.tmodificateur.getValue().toUpperCase())
						&&(String.valueOf(mat.getValue().reparer.getValue().isSelected()).contains(ControlerFiltre.treparer.getValue()))
						&&(String.valueOf(mat.getValue().isClosed.getValue().isSelected()).contains(ControlerFiltre.tfermer.getValue()))
						;
				
				return flag ; 
			}
		});
	}

	
	private void updateContenu() {
		
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			connection.update_contenu(listeSelectionContenu.getSelectedItem().getValue());
			connection.close();
			new messageBar(Langage.messageUpdate(), 4, pane);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			new messageBar(e.getMessage(), 4, pane);

			e.printStackTrace();
		}

	}

	private String notNULL(String txt) {
		if(txt==null)
			return "null";
		
		return txt;
	}
	
    @FXML
    void onClosed() {
    	Drawer.toBack();
    }

    @FXML
    void onOpened() {
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
