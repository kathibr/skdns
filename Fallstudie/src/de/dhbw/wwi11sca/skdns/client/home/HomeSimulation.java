package de.dhbw.wwi11sca.skdns.client.home;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.*;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import de.dhbw.wwi11sca.skdns.client.simulation.*;
import de.dhbw.wwi11sca.skdns.client.unternehmen.*;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class HomeSimulation implements EntryPoint {

//	AbsolutePanel absolutePanelHome = new AbsolutePanel();
//	AbsolutePanel absolutePanelEigenesUN = new AbsolutePanel();
//	AbsolutePanel[] absolutePanelUnternehmen = new AbsolutePanel[3];
//	HorizontalPanel horizontalPanel = new HorizontalPanel();
//	Button buttonLogout = new Button("Logout");
//	Button buttonUnternehmenBearbeiten = new Button("Unternehmen bearbeiten");
//	Button buttonSimulation = new Button("Simulation");
//	Label labelHome = new Label("Unternehmen - \u00DCbersicht");
//	Label labelEigenesUnternehmen = new Label("Eigenes Unternehmen");
//	Label labelUmsatz = new Label("Umsatz:");
//	Label labelGewinn = new Label("Gewinn:");
//	Label labelMarktanteil = new Label("Marktanteil:");
//	Label labelNachfrageTendenz = new Label("Nachfragetendenz:");
//	Label labelUmsatzEUN = new Label("0.00\u20AC");
//	Label labelGewinnEUN = new Label("0.00\u20AC");
//	Label labelMarktanteilEUN = new Label("0%");
//	Label labelNachfrageEUN = new Label("steigend");
//	Label[] labelUnternehmen = new Label[3];
//	Label[] labelUmsatzUnternehmen = new Label[3];
//	Label[] labelUmsatzUnternehmenWert = new Label[3];
//	Label[] labelGewinnUnternehmen = new Label[3];
//	Label[] labelGewinnUnternehmenWert = new Label[3];
//	Label[] labelMarktanteilUnternehmen = new Label[3];
//	Label[] labelMarktanteilUnternehmenWert = new Label[3];
//	Label[] labelNachfrageTendenzUnternehmen = new Label[3];
//	Label[] labelNachfrageTendenzUnternehmenWert = new Label[3];
//	Label labelAusgeloggt = new Label("Sie wurden erfolgreich ausgeloggt.");
//	int anzahlUnternehmen;
//	public EigenesUnternehmen eigenesUN = new EigenesUnternehmen();
//	public Unternehmen[] unternehmen = new Unternehmen[3];

	AbsolutePanel absolutePanelHome;
	CellTable<Unternehmen> tableUnternehmen;
	CellTable<EigenesUnternehmen> tableEigenesUnternehmen;
	
	Button btUNBearbeiten;
	Button btSimulation;
	Button btLogout;
	
	Label labelEigenesUN;
	Label labelAndereUN;
	Label labelAusgeloggt;
	
	List<Unternehmen> dbUN;
	List<Unternehmen> unList;
	List<EigenesUnternehmen> unEList;
	private HomeServiceAsync service = GWT.create(HomeService.class);

	@Override
	public void onModuleLoad() {
	

		// allgemeine Panels
		absolutePanelHome = new AbsolutePanel();
		RootPanel.get().add(absolutePanelHome,0,0);
		absolutePanelHome.setSize("1024px", "768px");
		
		
		Image image = new Image("fallstudie/gwt/clean/images/logo.png");
		absolutePanelHome.add(image, 0, 0);
		image.setSize("624px", "110px");
		
		labelEigenesUN = new Label("Eigenes Unternehmen");
		labelEigenesUN.setStyleName("gwt-Home-Label");
		absolutePanelHome.add(labelEigenesUN, 10, 115);
		
		
		labelAndereUN = new Label("Andere Unternehmen");
		labelAndereUN.setStyleName("gwt-Home-Label");
		absolutePanelHome.add(labelAndereUN, 10, 255);
		
		// Buttons
	
		btUNBearbeiten = new Button("Unternehmen bearbeiten");
		absolutePanelHome.add(btUNBearbeiten, 0, 459);
		btUNBearbeiten.setSize("200px", "35px");
		
		btSimulation = new Button("Simulation starten");
		absolutePanelHome.add(btSimulation, 206, 459);
		btSimulation.setSize("200px", "35px");
		
		btLogout = new Button("Logout");
		absolutePanelHome.add(btLogout, 633, 0);
		btLogout.setSize("100px", "35px");
				
		btUNBearbeiten.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				UnternehmenSimulation unternehmen = new UnternehmenSimulation();
				unternehmen.onModuleLoad();
			}
		});
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				Simulation simulation = new Simulation();
				simulation.onModuleLoad();
			}
		});
	
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Daten an DB übergeben?
				labelAusgeloggt = new Label("Sie wurden erfolgreich ausgeloggt.");
				RootPanel.get().clear();
				RootPanel.get().add(labelAusgeloggt);
			}
		});
		
		// CellTable für eigenes Unternehmen
		
		tableEigenesUnternehmen = new CellTable<EigenesUnternehmen>();
		absolutePanelHome.add(tableEigenesUnternehmen, 0, 144);
		tableEigenesUnternehmen.setSize("622px", "100px");
		
		TextColumn<EigenesUnternehmen> nameEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return unternehmen.getName();
			}			
		};
		TextColumn<EigenesUnternehmen> umsatzEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getUmsatz()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> gewinnEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getGewinn()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> marktAnteilEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getMarktAnteil()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> produktMengeEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getProdukt().getMenge()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> produktPreisEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getProdukt().getPreis()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> FixkostenEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getFixkosten()).toString();
			}			
		};
		tableEigenesUnternehmen.addColumn(nameEColumn, "Name");
		tableEigenesUnternehmen.addColumn(umsatzEColumn, "Umsatz");
		tableEigenesUnternehmen.addColumn(gewinnEColumn, "Gewinn");
		tableEigenesUnternehmen.addColumn(marktAnteilEColumn, "Marktanteil");
		tableEigenesUnternehmen.addColumn(produktMengeEColumn, "Produkt: Menge");
		tableEigenesUnternehmen.addColumn(produktPreisEColumn, "Produkt: Preis");
		tableEigenesUnternehmen.addColumn(FixkostenEColumn, "Fixkosten");
		

			
		
		// CellTable für andere Unternehmen
		
		tableUnternehmen = new CellTable<Unternehmen>();
		absolutePanelHome.add(tableUnternehmen, 0, 270);
		tableUnternehmen.setSize("622px", "200px");
				
		TextColumn<Unternehmen> nameColumn = new TextColumn<Unternehmen>(){
			@Override
			public String getValue(Unternehmen unternehmen) {
				return unternehmen.getName();
			}			
		};
		TextColumn<Unternehmen> umsatzColumn = new TextColumn<Unternehmen>(){
			@Override
			public String getValue(Unternehmen unternehmen) {
				return new Integer(unternehmen.getUmsatz()).toString();
			}			
		};
		TextColumn<Unternehmen> gewinnColumn = new TextColumn<Unternehmen>(){
			@Override
			public String getValue(Unternehmen unternehmen) {
				return new Integer(unternehmen.getGewinn()).toString();
			}			
		};
		TextColumn<Unternehmen> marktAnteilColumn = new TextColumn<Unternehmen>(){
			@Override
			public String getValue(Unternehmen unternehmen) {
				return new Double(unternehmen.getMarktAnteil()).toString();
			}			
		};
		TextColumn<Unternehmen> produktMengeColumn = new TextColumn<Unternehmen>(){
			@Override
			public String getValue(Unternehmen unternehmen) {
				return new Integer(unternehmen.getProdukt().getMenge()).toString();
			}			
		};
		TextColumn<Unternehmen> produktPreisColumn = new TextColumn<Unternehmen>(){
			@Override
			public String getValue(Unternehmen unternehmen) {
				return new Double(unternehmen.getProdukt().getPreis()).toString();
			}			
		};
		tableUnternehmen.addColumn(nameColumn, "Name");
		tableUnternehmen.addColumn(umsatzColumn, "Umsatz");
		tableUnternehmen.addColumn(gewinnColumn, "Gewinn");
		tableUnternehmen.addColumn(marktAnteilColumn, "Marktanteil");
		tableUnternehmen.addColumn(produktMengeColumn, "Produkt: Menge");
		tableUnternehmen.addColumn(produktPreisColumn, "Produkt: Preis");

		//Call
		
		service.getEigenesUnternehmen(new GetEigenesUnternehmenCallback());
		service.getUnternehmen(new GetUnternehmenCallback());
		
		
	}


		public class GetUnternehmenCallback implements AsyncCallback<List<Unternehmen>> {
	
	        @Override
	        public void onFailure(Throwable caught) { }
	
	        @Override
	        public void onSuccess(List<Unternehmen> result) {
	        	
	    		ListDataProvider<Unternehmen> dataProvider = new ListDataProvider<Unternehmen>();
	    	    // Connect the table to the data provider.
	    	    dataProvider.addDataDisplay(tableUnternehmen);
	    			
	    	   // Add the data to the data provider, which automatically pushes it to the widget.
	    	    unList = dataProvider.getList();
	        	
	        	for (Unternehmen unternehmen : result) {
	                unList.add(unternehmen);
	        	}
	     
	        }
	    }
		public class GetEigenesUnternehmenCallback implements AsyncCallback<List<EigenesUnternehmen>> {
			
	        @Override
	        public void onFailure(Throwable caught) { }
	
	        @Override
	        public void onSuccess(List<EigenesUnternehmen> result) {
	        	
	    		ListDataProvider<EigenesUnternehmen> dataEProvider = new ListDataProvider<EigenesUnternehmen>();
	    	    // Connect the table to the data provider.
	    	    dataEProvider.addDataDisplay(tableEigenesUnternehmen);
	    			
	    	   // Add the data to the data provider, which automatically pushes it to the widget.
	    	    unEList = dataEProvider.getList();
	        	
	        	for (EigenesUnternehmen unternehmen : result) {
	                unEList.add(unternehmen);
	        	}
	     
	        }
	    }
		
	}
//		RootPanel rootPanel = RootPanel.get();
//		rootPanel.setSize("1024", "768");
//		rootPanel.add(absolutePanelHome, 0, 0);
//		absolutePanelHome.setSize("1024px", "768px");
//		Label, das anzeigt, wo der User sich befindet
//		absolutePanelHome.add(labelHome, 10, 116);
//
//		AsyncCallback<List<Unternehmen>> callback = new AsyncCallback<List<Unternehmen>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO on Failure
//
//			}
//
//			@Override
//			public void onSuccess(List<Unternehmen> result) {
//				// Anzahl der Unternehmen die in der Datenbank vorhanden sind
//				// -1, da eigenes Unternehmen getrennt behandelt wird in dieser
//				// Klasse
//				anzahlUnternehmen = result.size() - 1;
//
//				// Labels mit den aus der Datenbank zurück gegebenen Daten
//				// befüllen
//				eigenesUN.setGewinn(result.get(0).getGewinn());
//				eigenesUN.setMarktAnteil(result.get(0).getMarktAnteil());
//				eigenesUN.setNachfrageTendenz(result.get(0)
//						.getNachfrageTendenz());
//				eigenesUN.setUmsatz(result.get(0).getUmsatz());
//				eigenesUN.setProdukt(result.get(0).getProdukt());
//
//				for (int i = 1; i < result.size(); i++) {
//					unternehmen[i - 1] = new Unternehmen();
//					unternehmen[i - 1].setGewinn(result.get(i).getGewinn());
//					unternehmen[i - 1].setMarktAnteil(result.get(i)
//							.getMarktAnteil());
//					unternehmen[i - 1].setNachfrageTendenz(result.get(i)
//							.getNachfrageTendenz());
//					unternehmen[i - 1].setUmsatz(result.get(i).getUmsatz());
//					unternehmen[i - 1].setProdukt(result.get(i).getProdukt());
//				}
//				befuellen();
//			}
//
//		};
//
//		service.getUnternehmen(callback);
//
//		// Button, um zur Unternehmen bearbeiten Oberfläche zu bearbeiten
//		absolutePanelHome.add(buttonUnternehmenBearbeiten, 0, 459);
//		buttonUnternehmenBearbeiten.setSize("200px", "35px");
//		buttonUnternehmenBearbeiten.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				RootPanel.get().clear();
//				UnternehmenSimulation unternehmen = new UnternehmenSimulation();
//				unternehmen.onModuleLoad();
//			}
//		});
//		buttonSimulation.setText("Simulation starten");
//
//		// Button, um zur Simulationsoberfläche zu gelangen
//		absolutePanelHome.add(buttonSimulation, 206, 459);
//		buttonSimulation.setSize("200px", "35px");
//		buttonSimulation.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				RootPanel.get().clear();
//				Simulation simulation = new Simulation();
//				simulation.onModuleLoad();
//			}
//		});
//
//		// Button zum ausloggen
//		absolutePanelHome.add(buttonLogout, 633, 0);
//		buttonLogout.setSize("100px", "35px");
//		
//		Image image = new Image("fallstudie/gwt/clean/images/logo.png");
//		absolutePanelHome.add(image, 0, 0);
//		image.setSize("624px", "110px");
//		
//		CellTable<Object> cellTable_1 = new CellTable<Object>();
//		absolutePanelHome.add(cellTable_1, 2, 144);
//		cellTable_1.setSize("622px", "309px");
//		buttonLogout.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				// TODO: Daten an DB übergeben?
//
//				RootPanel.get().clear();
//				RootPanel.get().add(labelAusgeloggt);
//			}
//		});

//	}

	
//		// Panel, auf dem die Unternehmeninformationen angezeigt werden
//		absolutePanelHome.add(horizontalPanel, 60, 100);
//		horizontalPanel.setSize("894px", "160px");
//		// Eigenes Unternehmen
//		horizontalPanel.add(absolutePanelEigenesUN);
//		absolutePanelEigenesUN.setHeight("159px");
//		// Label
//		labelUmsatzEUN.setText(eigenesUN.getUmsatz() + " €");
//		labelGewinnEUN.setText(eigenesUN.getGewinn() + " €");
//		labelMarktanteilEUN.setText(eigenesUN.getMarktAnteil() + " %");
//		labelNachfrageEUN.setText(eigenesUN.getNachfrageTendenz());
//
//		labelEigenesUnternehmen.setStyleName("gwt-UnternehmenLabel");
//		labelEigenesUnternehmen
//				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		absolutePanelEigenesUN.add(labelEigenesUnternehmen, 0, 0);
//		labelEigenesUnternehmen.setSize("224px", "18px");
//		absolutePanelEigenesUN.add(labelUmsatz, 10, 24);
//		absolutePanelEigenesUN.add(labelGewinn, 10, 48);
//		absolutePanelEigenesUN.add(labelMarktanteil, 10, 72);
//		absolutePanelEigenesUN.add(labelNachfrageTendenz, 10, 96);
//		absolutePanelEigenesUN.add(labelUmsatzEUN, 129, 24);
//		absolutePanelEigenesUN.add(labelGewinnEUN, 129, 48);
//		labelGewinnEUN.setSize("32px", "18px");
//		absolutePanelEigenesUN.add(labelMarktanteilEUN, 129, 72);
//		labelMarktanteilEUN.setSize("49px", "18px");
//		absolutePanelEigenesUN.add(labelNachfrageEUN, 129, 96);
//		labelNachfrageEUN.setSize("49px", "18px");
//		// weitere Unternehmen
//		for (int i = 0; i < anzahlUnternehmen; i++) {
//			int j = i + 1;
//			// neues Panel erzeugen
//			absolutePanelUnternehmen[i] = new AbsolutePanel();
//			horizontalPanel.add(absolutePanelUnternehmen[i]);
//			absolutePanelUnternehmen[i].setHeight("159px");
//			// "Überschrift" anbringen
//			labelUnternehmen[i] = new Label("Unternehmen " + j);
//			labelUnternehmen[i].setStyleName("gwt-UnternehmenLabel");
//			labelUnternehmen[i]
//					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//			absolutePanelUnternehmen[i].add(labelUnternehmen[i], 0, 0);
//			labelUnternehmen[i].setSize("224px", "18px");
//			// neue Labels erzeugen
//			labelUmsatzUnternehmen[i] = new Label("Umsatz:");
//			labelGewinnUnternehmen[i] = new Label("Gewinn: ");
//			labelMarktanteilUnternehmen[i] = new Label("Marktanteil:");
//			labelNachfrageTendenzUnternehmen[i] = new Label("Nachfragetendenz:");
//			// Werte aus der DB übernehmen
//			labelUmsatzUnternehmenWert[i] = new Label(
//					unternehmen[i].getUmsatz() + " €");
//			labelGewinnUnternehmenWert[i] = new Label(
//					unternehmen[i].getGewinn() + " €");
//			labelMarktanteilUnternehmenWert[i] = new Label(
//					unternehmen[i].getMarktAnteil() + " %");
//			labelNachfrageTendenzUnternehmenWert[i] = new Label(
//					unternehmen[i].getNachfrageTendenz());
//			// Labels auf dem Panel anbringen
//			absolutePanelUnternehmen[i].add(labelUmsatzUnternehmen[i], 10, 24);
//			absolutePanelUnternehmen[i].add(labelGewinnUnternehmen[i], 10, 48);
//			absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmen[i], 10,
//					72);
//			absolutePanelUnternehmen[i].add(
//					labelNachfrageTendenzUnternehmen[i], 10, 96);
//			absolutePanelUnternehmen[i].add(labelUmsatzUnternehmenWert[i], 129,
//					24);
//			absolutePanelUnternehmen[i].add(labelGewinnUnternehmenWert[i], 129,
//					48);
//			labelGewinnUnternehmenWert[i].setSize("32px", "18px");
//			absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmenWert[i],
//					129, 72);
//			labelMarktanteilUnternehmenWert[i].setSize("49px", "18px");
//			absolutePanelUnternehmen[i].add(
//					labelNachfrageTendenzUnternehmenWert[i], 129, 96);
//			labelNachfrageTendenzUnternehmenWert[i].setSize("49px", "18px");
		

	