package com.gluonapplication;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.prism.impl.FactoryResetException;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

public class ControlerOPFicheVerification  implements Initializable  {
	
    @FXML
    private Label labelTestFonction;
    
    @FXML
    private Label labelCodeEngin;
    
    @FXML
    private Label labelDate;

    @FXML
    private Label labelShift;
    
    @FXML
    private Label labelHeureDebut;
   
    @FXML
    private Label labelEquipementCritique;
  
    @FXML
    private Label labelEquipementsNonCritique;
   
    @FXML
    private JFXTreeTableView<Base_view_equipment> tableEquipement;

    @FXML
    private JFXTreeTableView<Base_view_equipment> tableEquipementNC;

    @FXML
    private JFXTreeTableView<Base_view_equipment> tableFonction;

    @FXML
	private TreeTableViewSelectionModel<Base_view_equipment> listeSelectionEquipement;
	
	@FXML
	private TreeTableViewSelectionModel<Base_view_equipment> listeSelectionEquipementNC;
	
	@FXML
	private TreeTableViewSelectionModel<Base_view_equipment> listeSelectionFonction;
	
	
	private ObservableList<Base_view_equipment> listeEquipement = FXCollections.observableArrayList();
	
	private ObservableList<Base_view_equipment> listeEquipementNC = FXCollections.observableArrayList();

	private ObservableList<Base_view_equipment> listeFonction = FXCollections.observableArrayList();

    @FXML
    private JFXButton bEnvoyer;

    @FXML
    private JFXTextField txtCodeEngin;

    @FXML
    private Label labelJour;

    @FXML
    private Label labelNuit;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXTimePicker heureDebut;

    @FXML
    private JFXTimePicker heureFin;

    @FXML
    private JFXTextArea txtObservation;

    private SQLConnection connection ; 
  
    @FXML
    private Label labelHoraireFin;
    
    private boolean shift_jour ; 
    

    @FXML
    private AnchorPane pane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		heureDebut.set24HourView(true);
		heureFin.set24HourView(true);
		txtObservation.setStyle("-fx-prompt-text-fill: #868895; "
					+ "-fx-background-color : white;");
		txtCodeEngin.setText(CONFIG.engin.libelle.getValue().getText());
		txtCodeEngin.setEditable(false);

		
		heureDebut.setDisable(true);
		
		date.setDisable(true);
		
		
		// REND DATE FIN INVISIBLE
		labelHoraireFin.setVisible(false);
		heureFin.setVisible(false);
		
		
		DateTimeFormatter fh = DateTimeFormatter.ofPattern("HH:mm");  
		DateTimeFormatter fd = DateTimeFormatter.ofPattern("yyyy-mm-dd");  

		date.setValue(LocalDate.now());

		heureDebut.setValue(LocalTime.parse(LocalTime.now().format(fh)));
		labelJour.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			this.labelJour.setVisible(false);
			this.labelNuit.setVisible(true); 
			this.shift_jour = false ;
		});
		
		labelNuit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			this.labelJour.setVisible(true);
			this.labelNuit.setVisible(false); 
			this.shift_jour = false ;
		});
		
		if(heureDebut.getValue().getHour()<19) {
			this.shift_jour = true ; 
			this.labelJour.setVisible(true);
			this.labelNuit.setVisible(false);
		}
		else {
			this.shift_jour = false ;
			this.labelJour.setVisible(false);
			this.labelNuit.setVisible(true);
		}
		
		bEnvoyer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			if(alerteMessage(Langage.confirmationEnvoye())) {
				saveFiche();
				Platform.exit();
				System.exit(0);
			}
		});
		
		updateListe();
		
		initTableEquipement();
		initTableEquipementNC();
		initTableFonction();
		initText();
		
	}
	
	private void initText() {
		labelCodeEngin.setText(Langage.OPFicheLabelCodeEngin());
		labelDate.setText(Langage.OPFicheLabeldate());
		labelEquipementCritique.setText(Langage.OPFicheLabelEquipementsCritiques());
		labelEquipementsNonCritique.setText(Langage.OPFicheLabelEquipementsNonCritiques());
		labelHeureDebut.setText(Langage.OPFicheLabelHeureDebut());
		labelHoraireFin.setText(Langage.OPFicheLabelHeureFin());
		labelShift.setText(Langage.OPFicheLabelShift());
		labelTestFonction.setText(Langage.OPFicheLabelTestFonction());
		bEnvoyer.setText(Langage.OPFicheButtonEnvoyer());
		txtObservation.setPromptText(Langage.OPFicheObservation());
		
	}
	
	private void updateListe() {
		
		try {
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeEquipement = connection.select_equipement_fonction(CONFIG.engin.gkey_type_engin, "EQUIPEMENT", true);
			connection.close();
			
			
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeEquipementNC = connection.select_equipement_fonction(CONFIG.engin.gkey_type_engin, "EQUIPEMENT", false);
			connection.close();
			
			
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeFonction = connection.select_equipement_fonction(CONFIG.engin.gkey_type_engin, "FONCTION_TEST");
			connection.close();

			new messageBar(Langage.messageUpdate(), 4, pane);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			new messageBar(e.getMessage(), 4, pane);

			e.printStackTrace();
		}

		


	}
	
	private void initTableEquipement() {
		JFXTreeTableColumn<Base_view_equipment, JFXTextField> libelle = new JFXTreeTableColumn<>(Langage.tableLibelle());
		// libelle.setPrefWidth(100);
		libelle.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.40));
		libelle.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_equipment, JFXTextField> param) {
						return param.getValue().getValue().libelle;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>> factoryLibelle = new Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXTextField> param) {
				final TreeTableCell<Base_view_equipment, JFXTextField> cell = new TreeTableCell<Base_view_equipment, JFXTextField>() {

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
		libelle.setCellFactory(factoryLibelle);
		
		
		JFXTreeTableColumn<Base_view_equipment, JFXTextField> commentaire = new JFXTreeTableColumn<>(Langage.tableCommentaire());
		// libelle.setPrefWidth(100);
		commentaire.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.40));
		commentaire.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_equipment, JFXTextField> param) {
						return param.getValue().getValue().tempContenu.commentaire;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>> factoricommentaire = new Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXTextField> param) {
				final TreeTableCell<Base_view_equipment, JFXTextField> cell = new TreeTableCell<Base_view_equipment, JFXTextField>() {

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
		commentaire.setCellFactory(factoricommentaire);

		
		JFXTreeTableColumn<Base_view_equipment, JFXCheckBox> etat = new JFXTreeTableColumn<>(Langage.tableEtat());
		etat.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.12));;
		etat.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_equipment, JFXCheckBox> param) {
						return param.getValue().getValue().tempContenu.status;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXCheckBox>, TreeTableCell<Base_view_equipment, JFXCheckBox>> factoryCritique = new Callback<TreeTableColumn<Base_view_equipment, JFXCheckBox>, TreeTableCell<Base_view_equipment, JFXCheckBox>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXCheckBox> param) {
				final TreeTableCell<Base_view_equipment, JFXCheckBox> cell = new TreeTableCell<Base_view_equipment, JFXCheckBox>() {
					@Override
					public void updateItem(JFXCheckBox item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.setStyle("-fx-alignment: CENTER;");
							item.setUnCheckedColor(Color.valueOf("#ff0000"));
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
		
		JFXTreeTableColumn<Base_view_equipment, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_equipment, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, Label>, TreeTableCell<Base_view_equipment, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_equipment, Label>, TreeTableCell<Base_view_equipment, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, Label> param) {
				final TreeTableCell<Base_view_equipment, Label> cell = new TreeTableCell<Base_view_equipment, Label>() {
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

		
		listeSelectionEquipement = tableEquipement.getSelectionModel();
		tableEquipement.setEditable(true);
		TreeItem<Base_view_equipment> root = new RecursiveTreeItem<Base_view_equipment>(listeEquipement,RecursiveTreeObject::getChildren);
		tableEquipement.getColumns().setAll(marque , libelle, etat ,commentaire);
		tableEquipement.setRoot(root);
		tableEquipement.setShowRoot(false);
		tableEquipement.setPlaceholder(new Label(Langage.vide()));

		tableEquipement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				System.out.println(tableEquipement.getSelectionModel().getSelectedItem().getValue().libelle.getValue().getText());
			}
		});
	}

	private void initTableEquipementNC() {
		JFXTreeTableColumn<Base_view_equipment, JFXTextField> libelle = new JFXTreeTableColumn<>(Langage.tableLibelle());
		// libelle.setPrefWidth(100);
		libelle.prefWidthProperty().bind(tableEquipementNC.widthProperty().multiply(0.425));
		libelle.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_equipment, JFXTextField> param) {
						return param.getValue().getValue().libelle;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>> factoryLibelle = new Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXTextField> param) {
				final TreeTableCell<Base_view_equipment, JFXTextField> cell = new TreeTableCell<Base_view_equipment, JFXTextField>() {

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
		libelle.setCellFactory(factoryLibelle);
		
		JFXTreeTableColumn<Base_view_equipment, JFXTextField> commentaire = new JFXTreeTableColumn<>(Langage.tableCommentaire());
		// libelle.setPrefWidth(100);
		commentaire.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.425));
		commentaire.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_equipment, JFXTextField> param) {
						return param.getValue().getValue().tempContenu.commentaire;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>> factoricommentaire = new Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXTextField> param) {
				final TreeTableCell<Base_view_equipment, JFXTextField> cell = new TreeTableCell<Base_view_equipment, JFXTextField>() {

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
		commentaire.setCellFactory(factoricommentaire);

		JFXTreeTableColumn<Base_view_equipment, JFXCheckBox> etat = new JFXTreeTableColumn<>(Langage.tableEtat());
		etat.prefWidthProperty().bind(tableEquipementNC.widthProperty().multiply(0.12));;
		etat.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_equipment, JFXCheckBox> param) {
						return param.getValue().getValue().tempContenu.status;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXCheckBox>, TreeTableCell<Base_view_equipment, JFXCheckBox>> factoryCritique = new Callback<TreeTableColumn<Base_view_equipment, JFXCheckBox>, TreeTableCell<Base_view_equipment, JFXCheckBox>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXCheckBox> param) {
				final TreeTableCell<Base_view_equipment, JFXCheckBox> cell = new TreeTableCell<Base_view_equipment, JFXCheckBox>() {
					@Override
					public void updateItem(JFXCheckBox item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.setStyle("-fx-alignment: CENTER;");
							item.setUnCheckedColor(Color.valueOf("#ff0000"));

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
		
		JFXTreeTableColumn<Base_view_equipment, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableEquipementNC.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_equipment, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, Label>, TreeTableCell<Base_view_equipment, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_equipment, Label>, TreeTableCell<Base_view_equipment, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, Label> param) {
				final TreeTableCell<Base_view_equipment, Label> cell = new TreeTableCell<Base_view_equipment, Label>() {
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

		listeSelectionEquipement = tableEquipementNC.getSelectionModel();
		tableEquipementNC.setEditable(true);
		TreeItem<Base_view_equipment> root = new RecursiveTreeItem<Base_view_equipment>(listeEquipementNC,RecursiveTreeObject::getChildren);
		tableEquipementNC.getColumns().setAll(marque , libelle, etat, commentaire);
		tableEquipementNC.setRoot(root);
		tableEquipementNC.setShowRoot(false);
		tableEquipementNC.setPlaceholder(new Label(Langage.vide()));

		tableEquipementNC.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				System.out.println(tableEquipementNC.getSelectionModel().getSelectedItem().getValue().libelle.getValue().getText());
			}
		});
	}
	
	private void initTableFonction() {
		JFXTreeTableColumn<Base_view_equipment, JFXTextField> libelle = new JFXTreeTableColumn<>(Langage.tableLibelle());
		// libelle.setPrefWidth(100);
		libelle.prefWidthProperty().bind(tableFonction.widthProperty().multiply(0.425));
		libelle.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_equipment, JFXTextField> param) {
						return param.getValue().getValue().libelle;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>> factoryLibelle = new Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXTextField> param) {
				final TreeTableCell<Base_view_equipment, JFXTextField> cell = new TreeTableCell<Base_view_equipment, JFXTextField>() {

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
		libelle.setCellFactory(factoryLibelle);

		JFXTreeTableColumn<Base_view_equipment, JFXTextField> commentaire = new JFXTreeTableColumn<>(Langage.tableCommentaire());
		// libelle.setPrefWidth(100);
		commentaire.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.425));
		commentaire.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_equipment, JFXTextField> param) {
						return param.getValue().getValue().tempContenu.commentaire;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>> factoricommentaire = new Callback<TreeTableColumn<Base_view_equipment, JFXTextField>, TreeTableCell<Base_view_equipment, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXTextField> param) {
				final TreeTableCell<Base_view_equipment, JFXTextField> cell = new TreeTableCell<Base_view_equipment, JFXTextField>() {

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
		commentaire.setCellFactory(factoricommentaire);
		JFXTreeTableColumn<Base_view_equipment, JFXCheckBox> etat = new JFXTreeTableColumn<>(Langage.tableEtat());
		etat.prefWidthProperty().bind(tableFonction.widthProperty().multiply(0.12));;
		etat.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_equipment, JFXCheckBox> param) {
						return param.getValue().getValue().tempContenu.status;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXCheckBox>, TreeTableCell<Base_view_equipment, JFXCheckBox>> factoryCritique = new Callback<TreeTableColumn<Base_view_equipment, JFXCheckBox>, TreeTableCell<Base_view_equipment, JFXCheckBox>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXCheckBox> param) {
				final TreeTableCell<Base_view_equipment, JFXCheckBox> cell = new TreeTableCell<Base_view_equipment, JFXCheckBox>() {
					@Override
					public void updateItem(JFXCheckBox item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.setStyle("-fx-alignment: CENTER;");
							item.setUnCheckedColor(Color.valueOf("#ff0000"));
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
		
		JFXTreeTableColumn<Base_view_equipment, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableFonction.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_equipment, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, Label>, TreeTableCell<Base_view_equipment, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_equipment, Label>, TreeTableCell<Base_view_equipment, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, Label> param) {
				final TreeTableCell<Base_view_equipment, Label> cell = new TreeTableCell<Base_view_equipment, Label>() {
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

		listeSelectionEquipement = tableFonction.getSelectionModel();
		tableFonction.setEditable(true);
		TreeItem<Base_view_equipment> root = new RecursiveTreeItem<Base_view_equipment>(listeFonction,RecursiveTreeObject::getChildren);
		tableFonction.getColumns().setAll(marque , libelle, etat ,commentaire);
		tableFonction.setRoot(root);
		tableFonction.setShowRoot(false);
		tableFonction.setPlaceholder(new Label(Langage.vide()));

		tableFonction.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				System.out.println(tableFonction.getSelectionModel().getSelectedItem().getValue().libelle.getValue().getText());
			}
		});
	}
	
	public void saveFiche() {

		Base_view_fiche fiche = new Base_view_fiche(0, this.txtObservation.getText(), this.heureDebut.getValue().toString(), null, this.date.getValue().toString(),CONFIG.user.gkey,CONFIG.engin.gkey,shift_jour);
		Base_view_contenu tempContenu  = new Base_view_contenu();
		try {
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			connection.insert_fiche_verification(fiche);
			int gkey_fiche = connection.getLastFiche();
			for(int i = 0 ; i < listeEquipement.size() ; i++) {
				tempContenu = listeEquipement.get(i).tempContenu;
				tempContenu.gkey_fiche_verification = new SimpleIntegerProperty(gkey_fiche) ;
				connection.insert_contenu(tempContenu);
			}
		
			for(int i = 0 ; i < listeEquipementNC.size() ; i++) {
				tempContenu = listeEquipementNC.get(i).tempContenu;
				tempContenu.gkey_fiche_verification = new SimpleIntegerProperty(gkey_fiche) ;
				connection.insert_contenu(tempContenu);
			}
			
			for(int i = 0 ; i < listeFonction.size() ; i++) {
				tempContenu = listeFonction.get(i).tempContenu;
				tempContenu.gkey_fiche_verification = new SimpleIntegerProperty(gkey_fiche) ;
				connection.insert_contenu(tempContenu);
			}

			new messageBar(Langage.messageSave(), 4, pane);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		connection.close();
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
