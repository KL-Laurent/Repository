package com.gluonapplication;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.security.auth.RefreshFailedException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
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

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ControlerTCRTypeEngin implements Initializable {

	@FXML
	private JFXButton bEnginNew;

	@FXML
	private JFXButton bEnginEdit;

	@FXML
	private JFXButton bEquipeNew;

	@FXML
	private JFXButton bEquipEdit;

	@FXML
	private JFXButton bFonctNew;

	@FXML
	private JFXButton bFonctEdit;

    @FXML
    private JFXButton bAjout;
    

    @FXML
    private JFXButton bEnginDelete;
    
    @FXML
    private JFXButton bEquipDelete;

    @FXML
    private JFXButton bFonctionDelet;
    
	@FXML
	private JFXTreeTableView<Base_view_type_engin> tableEngin;

	@FXML
	private JFXTreeTableView<Base_view_equipment> tableEquipement;

	@FXML
	private JFXTreeTableView<Base_view_equipment> tableFonction;

    /*@FXML
    private JFXTreeTableView<Base_view_user_selection> tableResponsable;
	
    @FXML
    private JFXTreeTableView<Base_view_user_selection> tableResponsableFonction;
     */
	
    @FXML
	private TreeTableViewSelectionModel<Base_view_type_engin> listeSelectionEngin;
	
	@FXML
	private TreeTableViewSelectionModel<Base_view_equipment> listeSelectionEquipement;
	
	@FXML
	private TreeTableViewSelectionModel<Base_view_equipment> listeSelectionTestFonction;
	
	/*@FXML
	private TreeTableViewSelectionModel<Base_view_user_selection> listeSelectionResponsable;
	 */
	
	private ObservableList<Base_view_type_engin> listeEngin = FXCollections.observableArrayList();

	
	private ObservableList<Base_view_equipment> listeEquipement = FXCollections.observableArrayList();

	private ObservableList<Base_view_equipment> listeNewsEquipement = FXCollections.observableArrayList();
	

	
	private ObservableList<Base_view_equipment> listeTestFonction = FXCollections.observableArrayList();

	private ObservableList<Base_view_equipment> listeNewsTestFonction = FXCollections.observableArrayList();
	


	/*private ObservableList<Base_view_user_selection> listeReponsable = FXCollections.observableArrayList();

	private ObservableList<Base_view_user_selection> listeNewsResponsable = FXCollections.observableArrayList();
	
	private ObservableList<Base_view_user_selection> listeOldResponasable = FXCollections.observableArrayList();
	*/
	
	/*private ObservableList<Base_view_user_selection> listeReponsableFonction = FXCollections.observableArrayList();

	private ObservableList<Base_view_user_selection> listeNewsResponsableFonction = FXCollections.observableArrayList();
	
	private ObservableList<Base_view_user_selection> listeOldResponasableFonction = FXCollections.observableArrayList();
	*/
	
	private int compteurIdEngin;
	private int selectedEnginId;
	
	private int compteurIdEquipement;
	private int selectedIdEquipement;
	
	private int compteurIdTestFonction;
	private int selectedIdTestFonction;
	
    @FXML
    private JFXButton bEnginRefresh;

    @FXML
    private JFXButton bEquipementRefresh;
    
    @FXML
    private JFXButton bFonctionRefresh;
    
    @FXML
    private Label labelTypeEngin;
	
    @FXML
    private Label labelEquipement;
    
    @FXML
    private Label labelTestFonction;
    
    
	private SQLConnection connection ; 
	
    @FXML
    private AnchorPane paneMain;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		bEnginNew.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			new messageBar(Langage.messageDeverouiller(), 4, paneMain);
			nouveauEngin();
		});
		
		bEnginEdit.setDisable(true);

		bEnginEdit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			if(alerteMessage(Langage.confirmationEdit())) {
				editEngin();
				new messageBar(Langage.messageDeverouiller(), 4, paneMain);
			}

		});
		
		bEquipeNew.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			new messageBar(Langage.messageAjouter(), 4, paneMain);
			nouveauEquipement();
		});
		
		bEquipEdit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			if(alerteMessage(Langage.confirmationEdit())) {
				new messageBar(Langage.messageDeverouiller(), 4, paneMain);
				editEquipement();
			}

		});

		bFonctNew.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			new messageBar(Langage.messageAjouter(), 4, paneMain);
			nouveauTestFonction();
		});
		
		bFonctEdit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			if(alerteMessage(Langage.confirmationEdit())) {
				new messageBar(Langage.messageDeverouiller(), 4, paneMain);
				editFonction();
			}

		});
		
		bAjout.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			if(alerteMessage(Langage.confirmationUpdate())) {
				saveData();
			}
		
		});
		
		bEquipementRefresh.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
				loadEquipement();
				refreshEquipement();
		});
		
		bFonctionRefresh.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
				loadFonction();
				refreshTestFonction();
		});
		
		bEnginRefresh.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			load();
		});
		
		bEnginDelete.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			supprEngin();
			//load();
		});

		bEquipDelete.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			supprEquipement();
		});
		
		bFonctionDelet.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
			supprTestFoncion();
		});
		initTableEngin();
		initTableEquipement();
		initTableTestFonction();
		initLangage();
		disableButton();
		load();
	}
	
	public void initLangage() {
		labelEquipement.setText(Langage.tCRTypeEnginLabelEquipement());
		labelTestFonction.setText(Langage.tCRTypeEnginTestFonction());
		labelTypeEngin.setText(Langage.tCRTypeEnginLabelTypeEngin());
		bAjout.setText(Langage.bAjouter());
		
	}
	
	public void disableButton() {
		bEquipementRefresh.setDisable(true);
		bFonctionRefresh.setDisable(true);
		bEnginDelete.setDisable(true);
		bEquipDelete.setDisable(true);
		bFonctionDelet.setDisable(true);
		bEquipeNew.setDisable(true);
		bEquipEdit.setDisable(true);
		bFonctNew.setDisable(true);
		bFonctEdit.setDisable(true);

	}
	
	public void enableButton() {
		bEquipeNew.setDisable(false);
		//bEquipEdit.setDisable(false);
		bFonctNew.setDisable(false);
		//bFonctEdit.setDisable(false);
		bEquipementRefresh.setDisable(false);
		bFonctionRefresh.setDisable(false);
	}
	

	public void nouveauEngin() {
		listeEngin.add(new Base_view_type_engin(compteurIdEngin));
		compteurIdEngin+=1 ; 
	}
	
	public void nouveauEquipement() {
		listeNewsEquipement.add(new Base_view_equipment(compteurIdEquipement,selectedEnginId,"EQUiPEMENT"));
		compteurIdEquipement+=1;
		refreshEquipement();
	}
	
	public void nouveauTestFonction() {
		listeNewsTestFonction.add(new Base_view_equipment(compteurIdTestFonction,selectedEnginId,"FONCTION_TEST"));
		compteurIdTestFonction +=1; 
		refreshTestFonction();
	}

	private void initTableEngin() {

		compteurIdEngin = 0 ;
		JFXTreeTableColumn<Base_view_type_engin, JFXTextField> libelle = new JFXTreeTableColumn<>(Langage.tableLibelle());
		// libelle.setPrefWidth(100);
		libelle.prefWidthProperty().bind(tableEngin.widthProperty().multiply(0.96));
		libelle.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_type_engin, JFXTextField>, ObservableValue<JFXTextField>>() {
					@Override
					public ObservableValue<JFXTextField> call(
							CellDataFeatures<Base_view_type_engin, JFXTextField> param) {
						return param.getValue().getValue().libelle;
					}
				});

		Callback<TreeTableColumn<Base_view_type_engin, JFXTextField>, TreeTableCell<Base_view_type_engin, JFXTextField>> factoryLibelle = new Callback<TreeTableColumn<Base_view_type_engin, JFXTextField>, TreeTableCell<Base_view_type_engin, JFXTextField>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_type_engin, JFXTextField> param) {
				final TreeTableCell<Base_view_type_engin, JFXTextField> cell = new TreeTableCell<Base_view_type_engin, JFXTextField>() {

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

		JFXTreeTableColumn<Base_view_type_engin, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableEngin.widthProperty().multiply(0.01));
		marque.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_type_engin, Label>, ObservableValue<Label>>() {
					@Override
					public ObservableValue<Label> call(CellDataFeatures<Base_view_type_engin, Label> param) {
						return new SimpleObjectProperty<Label>(new Label());
					}
				});

		Callback<TreeTableColumn<Base_view_type_engin, Label>, TreeTableCell<Base_view_type_engin, Label>> factoryMarque = new Callback<TreeTableColumn<Base_view_type_engin, Label>, TreeTableCell<Base_view_type_engin, Label>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_type_engin, Label> param) {
				final TreeTableCell<Base_view_type_engin, Label> cell = new TreeTableCell<Base_view_type_engin, Label>() {
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
		listeSelectionEngin = tableEngin.getSelectionModel();
		tableEngin.setEditable(true);
		TreeItem<Base_view_type_engin> root = new RecursiveTreeItem<Base_view_type_engin>(listeEngin,
				RecursiveTreeObject::getChildren);
		tableEngin.getColumns().setAll( marque, libelle);
		tableEngin.setRoot(root);
		tableEngin.setShowRoot(false);
		
		tableEngin.setPlaceholder(new Label(Langage.vide()));

		tableEngin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.bEnginDelete.setDisable(false);
				if(!newSelection.getValue().isNew) {
					this.bEnginEdit.setDisable(false);
				}
				else {
					this.bEnginEdit.setDisable(true);

				}
				System.out.println(tableEngin.getSelectionModel().getSelectedItem().getValue().libelle.getValue().getText());
				selectedEnginId = tableEngin.getSelectionModel().getSelectedItem().getValue().gkey;
				enableButton();
				refreshEquipement();
				refreshTestFonction();
				selectedIdEquipement = -1 ; 
				selectedIdTestFonction = -1 ;

			}
			else {
				this.bEnginDelete.setDisable(true);
				this.bEnginEdit.setDisable(true);

			}
		});
	}
	
	private void initTableEquipement() {
		compteurIdEquipement = 0 ;
		JFXTreeTableColumn<Base_view_equipment, JFXTextField> libelle = new JFXTreeTableColumn<>(Langage.tableLibelle());
		// libelle.setPrefWidth(100);
		libelle.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.50));
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

		JFXTreeTableColumn<Base_view_equipment, JFXCheckBox> critique = new JFXTreeTableColumn<>(Langage.tableCritique());
		critique.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.15));;
		critique.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_equipment, JFXCheckBox> param) {
						return param.getValue().getValue().critique;
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

		critique.setCellFactory(factoryCritique);
		
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
		

		JFXTreeTableColumn<Base_view_equipment, JFXComboBox<String>> responsable = new JFXTreeTableColumn<>(Langage.tableResponsable());
		responsable.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.30));
		responsable.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
					@Override
					public ObservableValue<JFXComboBox<String>> call(CellDataFeatures<Base_view_equipment, JFXComboBox<String>> param) {
						return param.getValue().getValue().responsable;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXComboBox<String>>, TreeTableCell<Base_view_equipment, JFXComboBox<String>>> factoryResponsable = new Callback<TreeTableColumn<Base_view_equipment, JFXComboBox<String>>, TreeTableCell<Base_view_equipment, JFXComboBox<String>>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXComboBox<String>> param) {
				final TreeTableCell<Base_view_equipment, JFXComboBox<String>> cell = new TreeTableCell<Base_view_equipment, JFXComboBox<String>>() {
					@Override
					public void updateItem(JFXComboBox<String> item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
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

		listeSelectionEquipement = tableEquipement.getSelectionModel();
		tableEquipement.setEditable(true);
		TreeItem<Base_view_equipment> root = new RecursiveTreeItem<Base_view_equipment>(listeEquipement,RecursiveTreeObject::getChildren);
		tableEquipement.getColumns().setAll(marque ,critique, libelle,responsable);
		tableEquipement.setRoot(root);
		tableEquipement.setShowRoot(false);
		tableEquipement.setPlaceholder(new Label(Langage.vide()));

		tableEquipement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.bEquipDelete.setDisable(false);
				if(!newSelection.getValue().isNew) {
					this.bEquipEdit.setDisable(false);
				}
				else {
					this.bEquipEdit.setDisable(true);

				}

				System.out.println(tableEquipement.getSelectionModel().getSelectedItem().getValue().libelle.getValue().getText());
				selectedIdEquipement = tableEquipement.getSelectionModel().getSelectedItem().getValue().gkey;

			}
			else {
				this.bEquipDelete.setDisable(true);
				this.bEquipEdit.setDisable(true);
			}
		});
	}
	
	private void initTableTestFonction() {
		compteurIdTestFonction = 0 ; 
		JFXTreeTableColumn<Base_view_equipment, JFXTextField> libelle = new JFXTreeTableColumn<>(Langage.tableLibelle());
		// libelle.setPrefWidth(100);
		libelle.prefWidthProperty().bind(tableFonction.widthProperty().multiply(0.50));
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

		JFXTreeTableColumn<Base_view_equipment, JFXCheckBox> critique = new JFXTreeTableColumn<>(Langage.tableCritique());
		critique.prefWidthProperty().bind(tableFonction.widthProperty().multiply(0.15));;
		critique.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
					@Override
					public ObservableValue<JFXCheckBox> call(CellDataFeatures<Base_view_equipment, JFXCheckBox> param) {
						return param.getValue().getValue().critique;
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

		critique.setCellFactory(factoryCritique);
		
		
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
		

		JFXTreeTableColumn<Base_view_equipment, JFXComboBox<String>> responsable = new JFXTreeTableColumn<>(Langage.tableResponsable());
		responsable.prefWidthProperty().bind(tableEquipement.widthProperty().multiply(0.30));
		responsable.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_equipment, JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
					@Override
					public ObservableValue<JFXComboBox<String>> call(CellDataFeatures<Base_view_equipment, JFXComboBox<String>> param) {
						return param.getValue().getValue().responsable;
					}
				});

		Callback<TreeTableColumn<Base_view_equipment, JFXComboBox<String>>, TreeTableCell<Base_view_equipment, JFXComboBox<String>>> factoryResponsable = new Callback<TreeTableColumn<Base_view_equipment, JFXComboBox<String>>, TreeTableCell<Base_view_equipment, JFXComboBox<String>>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_equipment, JFXComboBox<String>> param) {
				final TreeTableCell<Base_view_equipment, JFXComboBox<String>> cell = new TreeTableCell<Base_view_equipment, JFXComboBox<String>>() {
					@Override
					public void updateItem(JFXComboBox<String> item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
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
		
		listeSelectionTestFonction = tableFonction.getSelectionModel();
		tableFonction.setEditable(true);
		TreeItem<Base_view_equipment> root = new RecursiveTreeItem<Base_view_equipment>(listeTestFonction,RecursiveTreeObject::getChildren);
		tableFonction.getColumns().setAll( marque ,critique, libelle,responsable);
		tableFonction.setRoot(root);
		tableFonction.setShowRoot(false);
		tableFonction.setPlaceholder(new Label(Langage.vide()));

		tableFonction.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.bFonctionDelet.setDisable(false);
				if(!newSelection.getValue().isNew) {
					this.bFonctEdit.setDisable(false);
				
				}
				else {
					this.bFonctEdit.setDisable(true);
				}
				
				System.out.println(tableFonction.getSelectionModel().getSelectedItem().getValue().gkey);
				selectedIdTestFonction = tableFonction.getSelectionModel().getSelectedItem().getValue().gkey;
			}
			else {
				this.bFonctEdit.setDisable(true);
				this.bFonctionDelet.setDisable(true);
			}
		});
	}

	
	private void refreshEquipement () {
		listeEquipement.setAll();
		for(int i = 0 ; i < listeNewsEquipement.size() ; i++) {
			if(listeNewsEquipement.get(i).gkey_engin == selectedEnginId)
			listeEquipement.add(listeNewsEquipement.get(i));
		}
	}
	
	private void refreshTestFonction () {
		listeTestFonction.setAll();
		for(int i = 0 ; i < listeNewsTestFonction.size() ; i++) {
			if(listeNewsTestFonction.get(i).gkey_engin == selectedEnginId)
			listeTestFonction.add(listeNewsTestFonction.get(i));
		}
	}

	private void saveData() {
		
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);

			for(int i = 0 ; i < listeEngin.size() ; i++) {
				if(listeEngin.get(i).isNew) {
					connection.insert_type_engin(listeEngin.get(i));
					
					for(int j = 0 ; j < listeNewsEquipement.size() ; j++) {
						if(listeNewsEquipement.get(j).gkey_engin == listeEngin.get(i).gkey) {
							connection.insert_equipement(listeNewsEquipement.get(j));
							/*for(int k = 0 ; k < listeNewsResponsable.size() ; k++) {
								if(listeNewsResponsable.get(k).fkey == listeNewsEquipement.get(j).gkey)
									connection.insert_responsable(1);
							}*/
						}
						
					}
					
					for(int j = 0 ; j < listeNewsTestFonction.size() ; j++) {
						if(listeNewsTestFonction.get(j).gkey_engin == listeEngin.get(i).gkey) {
							connection.insert_equipement(listeNewsTestFonction.get(j) );
						}
					}
				}
				else if(listeEngin.get(i).isEdit) {
					connection.update_type_engin(listeEngin.get(i));
				}
				else {
					for(int j = 0 ; j < listeNewsEquipement.size() ; j++) {
						
						if((listeNewsEquipement.get(j).isNew)&&(listeNewsEquipement.get(j).gkey_engin == listeEngin.get(i).gkey)) {
							connection.insert_equipement(listeNewsEquipement.get(j), listeEngin.get(i).gkey);

						}
						
						if((listeNewsEquipement.get(j).isEdited)&&(listeNewsEquipement.get(j).gkey_engin == listeEngin.get(i).gkey)) {
							connection.update_equipement(listeNewsEquipement.get(j));
						}
					}
					
					for(int j = 0 ; j < listeNewsTestFonction.size() ; j++) {
						if((listeNewsTestFonction.get(j).isNew)&&(listeNewsTestFonction.get(j).gkey_engin == listeEngin.get(i).gkey)) {
							connection.insert_equipement(listeNewsTestFonction.get(j), listeEngin.get(i).gkey);

						}
						
						if((listeNewsTestFonction.get(j).isEdited)&&(listeNewsTestFonction.get(j).gkey_engin == listeEngin.get(i).gkey)) {
							connection.update_equipement(listeNewsTestFonction.get(j));

						}
					}
				}
				
				new messageBar(Langage.messageSave(), 4, paneMain);

			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			new messageBar(e.getMessage(), 4, paneMain);

			e.printStackTrace();
		}

		connection.close();
	}

	public void load() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listeEngin.setAll(connection.select_type_engin());
		listeNewsEquipement.setAll(connection.select_equipement_fonction("EQUiPEMENT"));
		listeNewsTestFonction.setAll(connection.select_equipement_fonction("FONCTION_TEST"));
		connection.close();
	}
	
	public void loadEquipement() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listeNewsEquipement.setAll(connection.select_equipement_fonction("EQUiPEMENT"));
		connection.close();
	}
	
	public void loadFonction() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listeNewsTestFonction.setAll(connection.select_equipement_fonction("FONCTION_TEST"));
		connection.close();
	}
	
	private void editEngin() {
		listeSelectionEngin.getSelectedItem().getValue().unlock();
	}
	
	private void editEquipement() {
		listeSelectionEquipement.getSelectedItem().getValue().unlock();
	}
	
	private void editFonction() {
		listeSelectionTestFonction.getSelectedItem().getValue().unlock();
	}
	
	public void supprEquipement() {
		 Alert a = new Alert(AlertType.CONFIRMATION,Langage.confirmationSuppr());
		 a.showAndWait().ifPresent(response -> {
		    if (response == ButtonType.OK) {
			     TreeItem<Base_view_equipment> selectedItem = listeSelectionEquipement.getModelItem(listeSelectionEquipement.getSelectedIndex());
			     TreeItem<Base_view_equipment> parent = selectedItem.getParent();
			     Base_view_equipment equip = listeSelectionEquipement.getSelectedItem().getValue();
			     boolean isOk  = true ; 
			     if(!equip.isNew) {
			    	 try {
						connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
						connection.delete_equipement(equip);
						connection.close();
			    	 } catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						new messageBar(e.getMessage(), 5, paneMain);
						isOk = false; 
						
					}
			     }
			     
			     if(isOk) {
			    	// TRES IMPORTANT !!!! SA SUPPRIME DE L'OBSERVABLE LISTE   rt 
				     listeEquipement.remove(selectedItem.getValue());
				     listeNewsEquipement.remove(selectedItem.getValue());
				     if(true) {
				    	  if (parent != null)
					        {
					            parent.getChildren().remove(selectedItem);  
					        }
					        else
					        {
					            tableEquipement.setRoot(null);
					        }
				     } 
						new messageBar(Langage.supprimer(), 5, paneMain);
			     }
		     }

		 });    
	}
	
	public void supprAllEquipement(Base_view_type_engin base_view_type_engin ) throws ClassNotFoundException, SQLException {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
				if(base_view_type_engin.isNew) {
					for(int j = 0 ; j < listeNewsEquipement.size() ; j++) {
						if(listeNewsEquipement.get(j).gkey_engin == base_view_type_engin.gkey) {
						     listeEquipement.remove(listeNewsEquipement.get(j));
						     listeNewsEquipement.remove(listeNewsEquipement.get(j));
						}	
					}
					
					for(int j = 0 ; j < listeNewsTestFonction.size() ; j++) {
						if(listeNewsTestFonction.get(j).gkey_engin == base_view_type_engin.gkey) {
							listeTestFonction.remove(listeNewsTestFonction.get(j));
							listeNewsTestFonction.remove(listeNewsTestFonction.get(j));
						}
					}	
				}	
				else {
					for(int j = 0 ; j < listeNewsEquipement.size() ; j++) {
						if(listeNewsEquipement.get(j).gkey_engin == base_view_type_engin.gkey) {
							connection.delete_equipement(listeNewsEquipement.get(j));
						     listeEquipement.remove(listeNewsEquipement.get(j));
						     listeNewsEquipement.remove(listeNewsEquipement.get(j));
						}	
					}
					
					for(int j = 0 ; j < listeNewsTestFonction.size() ; j++) {
						if(listeNewsTestFonction.get(j).gkey_engin == base_view_type_engin.gkey) {
							connection.delete_equipement(listeNewsTestFonction.get(j));
							listeTestFonction.remove(listeNewsTestFonction.get(j));
							listeNewsTestFonction.remove(listeNewsTestFonction.get(j));
						}
					}	
				}
			connection.close();
			supprEngin();
	}

	public void supprTestFoncion() {
		 Alert a = new Alert(AlertType.CONFIRMATION,Langage.confirmationSuppr());
		 a.showAndWait().ifPresent(response -> {
		    if (response == ButtonType.OK) {
			     TreeItem<Base_view_equipment> selectedItem = listeSelectionTestFonction.getModelItem(listeSelectionTestFonction.getSelectedIndex());
			     TreeItem<Base_view_equipment> parent = selectedItem.getParent();
			     Base_view_equipment equip = listeSelectionTestFonction.getSelectedItem().getValue();
			     boolean isOk = true ; 
			     if(!equip.isNew) {
			    	 try {
						connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
						connection.delete_equipement(equip);
						connection.close();
			    	 } catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						new messageBar(e.getMessage(), 8, paneMain);
						isOk  = false ; 
					}
			    	 if(isOk) {
			    		// TRES IMPORTANT !!!! SA SUPPRIME DE L'OBSERVABLE LISTE 
					     listeTestFonction.remove(selectedItem.getValue());
					     listeNewsTestFonction.remove(selectedItem.getValue());
					     if(true) {
					    	  if (parent != null)
						        {
						            parent.getChildren().remove(selectedItem);  
						        }
						        else
						        {
						            tableFonction.setRoot(null);
						        }
					     }
							new messageBar(Langage.supprimer(), 8, paneMain);
			    	 }
			     }
			    
		     }

		 });    
	}
	
	
	public void supprEngin() {
		 Alert a = new Alert(AlertType.CONFIRMATION,Langage.confirmationSuppr());
		 a.showAndWait().ifPresent(response -> {
		    if (response == ButtonType.OK) {
			     TreeItem<Base_view_type_engin> selectedItem = listeSelectionEngin.getModelItem(listeSelectionEngin.getSelectedIndex());
			     TreeItem<Base_view_type_engin> parent = selectedItem.getParent();
			     Base_view_type_engin engin  = listeSelectionEngin.getSelectedItem().getValue(); 
			     boolean isOk = true ; 
			     if(!engin.isNew) {
			    	 try {
						connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
						connection.delete_type_engin(engin);
					
						connection.close();
			    	 } catch (ClassNotFoundException | SQLException e ) {
						// TODO Auto-generated catch block
						new messageBar(e.getMessage(), 5, paneMain);
						e.printStackTrace();
						isOk = false ;
					}
			     }
			     if(isOk) {
			    	 listeEngin.remove(selectedItem.getValue());
						new messageBar(Langage.supprimer(), 5, paneMain);

			     }
			     // TRES IMPORTANT !!!! SA SUPPRIME DE L'OBSERVABLE LISTE 			     
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
