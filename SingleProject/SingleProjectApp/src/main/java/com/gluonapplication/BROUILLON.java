package com.gluonapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;

public class BROUILLON {
	
	
	private void contextMEnu() {
/*		marque.visibleProperty().bindBidirectional(colMarque);
		engin.visibleProperty().bindBidirectional(colEngin);
		equipement.visibleProperty().bindBidirectional(colEquipement);
		commentaire.visibleProperty().bindBidirectional(colCommentaire);
		reparer.visibleProperty().bindBidirectional(colReparer);
		isCLosed.visibleProperty().bindBidirectional(colIsClosed);
		commentaireRP.visibleProperty().bindBidirectional(colCommentaireRP);
		
		
		ContextMenu contextMenu = new ContextMenu();
		CheckMenuItem itemEquipement = new CheckMenuItem(Langage.tableEquipement());
		itemEquipement.setSelected(true);
		itemEquipement.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(!itemEquipement.isSelected()) {
					colEquipement.set(false);
				}
				else {
					colEquipement.set(true);
				}
			}
		});
		
		CheckMenuItem itemEngin = new CheckMenuItem(Langage.tCREnginLabelEngin());
		itemEngin.setSelected(true);
		itemEngin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!itemEngin.isSelected()) {
					colEngin.set(false);
				}
				else {
					colEngin.set(true);
				}
			}
		});
		
		CheckMenuItem itemCommentaire = new CheckMenuItem(Langage.tableCommentaireOperateur());
		itemCommentaire.setSelected(true);
		itemCommentaire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!itemCommentaire.isSelected()) {
					colCommentaire.set(false);
				}
				else {
					colCommentaire.set(true);
				}
			}
		});
		
		CheckMenuItem itemReparer = new CheckMenuItem(Langage.tableReparer());
		itemReparer.setSelected(true);
		itemReparer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!itemReparer.isSelected()) {
					colReparer.set(false);
				}
				else {
					colReparer.set(true);
				}
			}
		});
		
		CheckMenuItem itemIsClosed = new CheckMenuItem(Langage.tableFermer());
		itemIsClosed.setSelected(true);
		itemIsClosed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!itemIsClosed.isSelected()) {
					colIsClosed.set(false);
				}
				else {
					colIsClosed.set(true);
				}
			}
		});
		
		CheckMenuItem itemCommentaireRP = new CheckMenuItem(Langage.tableCommentaireReparation());
		itemCommentaireRP.setSelected(true);
		itemCommentaireRP.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!itemCommentaireRP.isSelected()) {
					colCommentaireRP.set(false);
				}
				else {
					colCommentaireRP.set(true);
				}
			}
		});
		
		contextMenu.getItems().addAll(itemEngin,itemEquipement ,itemCommentaire,itemReparer,itemIsClosed,itemCommentaireRP);
		
        // When user right-click table
		//tableContenu.setOnContextMenuRequested((e) -> contextMenu.show(tableContenu,e.getScreenX(),e.getScreenY()));
*/
	}
	private void initTableresponsableFonction() {
		/*compteurIdResponsable = 0 ;
		JFXTreeTableColumn<Base_view_user_selection, JFXComboBox<String>> NomListe = new JFXTreeTableColumn<>("Nom");
		// libelle.setPrefWidth(100);
		NomListe.prefWidthProperty().bind(tableResponsableFonction.widthProperty().multiply(0.94));
		NomListe.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Base_view_user_selection, JFXComboBox<String>>, ObservableValue<JFXComboBox<String>>>() {
					@Override
					public ObservableValue<JFXComboBox<String>> call(
							CellDataFeatures<Base_view_user_selection, JFXComboBox<String>> param) {
						return param.getValue().getValue().comboNom;
					}
				});

		Callback<TreeTableColumn<Base_view_user_selection, JFXComboBox<String>>, TreeTableCell<Base_view_user_selection, JFXComboBox<String>>> factoryListeNom = new Callback<TreeTableColumn<Base_view_user_selection, JFXComboBox<String>>, TreeTableCell<Base_view_user_selection, JFXComboBox<String>>>() {
			@Override
			public TreeTableCell call(final TreeTableColumn<Base_view_user_selection, JFXComboBox<String>> param) {
				final TreeTableCell<Base_view_user_selection, JFXComboBox<String>> cell = new TreeTableCell<Base_view_user_selection, JFXComboBox<String>>() {

					@Override
					public void updateItem(JFXComboBox<String> item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							item.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
							item.getParent();
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
		NomListe.setCellFactory(factoryListeNom);

		JFXTreeTableColumn<Base_view_user_selection, Label> marque = new JFXTreeTableColumn<>("");
		marque.prefWidthProperty().bind(tableResponsableFonction.widthProperty().multiply(0.01));
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
		
		listeSelectionResponsable = tableResponsableFonction.getSelectionModel();
		tableResponsableFonction.setEditable(true);
		TreeItem<Base_view_user_selection> root = new RecursiveTreeItem<Base_view_user_selection>(listeReponsableFonction,RecursiveTreeObject::getChildren);
		tableResponsableFonction.getColumns().setAll(marque , NomListe);
		tableResponsableFonction.setRoot(root);
		tableResponsableFonction.setShowRoot(false);
		tableResponsableFonction.setPlaceholder(new Label("Press button new (+) for add new liste"));

		tableResponsableFonction.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				
			}
		});*/
	}
	/*private void refreshResponsableFonction() {
	listeReponsableFonction.setAll();
	for(int i = 0 ; i < listeNewsResponsableFonction.size() ; i++) {
		if(listeNewsResponsableFonction.get(i).fkey == selectedIdTestFonction)
			listeReponsableFonction.add(listeNewsResponsableFonction.get(i));
	}
}

private void refreshResponsableEquipement() {
	listeReponsable.setAll();
	for(int i = 0 ; i < listeNewsResponsable.size() ; i++) {
		if(listeNewsResponsable.get(i).fkey == selectedIdEquipement)
			listeReponsable.add(listeNewsResponsable.get(i));
	}
}
*/
/*public void nouveauReponsable() {
	listeNewsResponsable.add(new Base_view_user_selection(compteurIdResponsable,selectedIdEquipement));
	compteurIdResponsable+=1;
	refreshResponsableEquipement();
} 

public void nouveauReponsableFonction() {
	listeNewsResponsableFonction.add(new Base_view_user_selection(compteurIdResponsable,selectedIdTestFonction));
	System.out.println(" TEst "+selectedIdTestFonction);
	compteurIdResponsable+=1;
	refreshResponsableFonction();
} */
}
