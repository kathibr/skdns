package de.dhbw.wwi11sca.skdns.client.home;

import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;
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
import de.dhbw.wwi11sca.skdns.client.unternehmen.UnternehmenSimulation;

public class HomeSimulation implements EntryPoint{

	AbsolutePanel absolutePanelHome = new AbsolutePanel();
	AbsolutePanel absolutePanelEigenesUN = new AbsolutePanel();
	AbsolutePanel [] absolutePanelUnternehmen = new AbsolutePanel[3];
	HorizontalPanel horizontalPanel = new HorizontalPanel();
	Button buttonLogout = new Button("Logout");
	Button buttonUnternehmenBearbeiten = new Button("Unternehmen bearbeiten");
	Button buttonSimulation = new Button("Simulation");
	Label labelHome = new Label("Home");		
	Label labelEigenesUnternehmen = new Label("Eigenes Unternehmen");
	Label labelUmsatz = new Label("Umsatz:");
	Label labelGewinn = new Label("Gewinn:");
	Label labelMarktanteil = new Label("Marktanteil:");
	Label labelNachfrageTendenz = new Label("Nachfragetendenz:");
	Label labelUmsatzEUN = new Label("0.00\u20AC");
	Label labelGewinnEUN = new Label("0.00\u20AC");
	Label labelMarktanteilEUN = new Label("0%");
	Label labelNachfrageEUN = new Label("steigend");
	Label [] labelUnternehmen = new Label[3];
	Label [] labelUmsatzUnternehmen = new Label  [3];
	Label [] labelUmsatzUnternehmenWert = new Label [3];
	Label [] labelGewinnUnternehmen = new Label[3];
	Label [] labelGewinnUnternehmenWert = new Label[3];
	Label [] labelMarktanteilUnternehmen = new Label[3];
	Label [] labelMarktanteilUnternehmenWert = new Label[3];
	Label [] labelNachfrageTendenzUnternehmen = new Label [3];
	Label [] labelNachfrageTendenzUnternehmenWert = new Label [3];
	Label labelAusgeloggt = new Label("Sie wurden erfolgreich ausgeloggt.");
	int anzahlUnternehmen;
	public EigenesUnternehmen eigenesUN = new EigenesUnternehmen();
	public Unternehmen [] unternehmen = new Unternehmen[3];
	
	private HomeServiceAsync service = GWT.create(HomeService.class);
	
	@Override
	public void onModuleLoad() {
		// allgemeine Panels
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");
		rootPanel.add(absolutePanelHome, 0, 0);
		absolutePanelHome.setSize("1024px", "768px");
		
		// Label, das anzeigt, wo der User sich befindet
		absolutePanelHome.add(labelHome, 43, 43);

		AsyncCallback<List<Unternehmen>> callback = new AsyncCallback<List<Unternehmen>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO on Failure
				
			}

			@Override
			public void onSuccess(List<Unternehmen> result) {
				// Anzahl der Unternehmen die in der Datenbank vorhanden sind
				// -1, da eigenes Unternehmen getrennt behandelt wird in dieser Klasse
				anzahlUnternehmen = result.size() -1;
				
				// Labels mit den aus der Datenbank zurück gegebenen Daten befüllen
				eigenesUN.setGewinn(result.get(0).getGewinn());
				eigenesUN.setMarktAnteil(result.get(0).getMarktAnteil());
				eigenesUN.setNachfrageTendenz(result.get(0).getNachfrageTendenz());
				eigenesUN.setUmsatz(result.get(0).getUmsatz());
				eigenesUN.setProdukt(result.get(0).getProdukt());
				
				for (int i=1; i< result.size(); i++){
					unternehmen[i] = new Unternehmen();
					unternehmen[i].setGewinn(result.get(i).getGewinn());
					unternehmen[i].setMarktAnteil(result.get(i).getMarktAnteil());
					unternehmen[i].setNachfrageTendenz(result.get(i).getNachfrageTendenz());
					unternehmen[i].setUmsatz(result.get(i).getUmsatz());
					unternehmen[i].setProdukt(result.get(i).getProdukt());
				}
				
			}
			
		};
		
		service.getUnternehmen(callback);
		
		// Panel, auf dem die Unternehmeninformationen angezeigt werden
		absolutePanelHome.add(horizontalPanel, 60, 100);
		horizontalPanel.setSize("894px", "160px");
			// Eigenes Unternehmen		
			horizontalPanel.add(absolutePanelEigenesUN);
			absolutePanelEigenesUN.setHeight("159px");
				// Label
				labelUmsatzEUN.setText(eigenesUN.getUmsatz() + " €");
				labelGewinnEUN.setText(eigenesUN.getGewinn() + " €");
				labelMarktanteilEUN.setText(eigenesUN.getMarktAnteil() + " %");
				labelNachfrageEUN.setText(eigenesUN.getNachfrageTendenz());
				
				labelEigenesUnternehmen.setStyleName("gwt-UnternehmenLabel");
				labelEigenesUnternehmen.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				absolutePanelEigenesUN.add(labelEigenesUnternehmen, 0, 0);
				labelEigenesUnternehmen.setSize("224px", "18px");		
				absolutePanelEigenesUN.add(labelUmsatz, 10, 24);		
				absolutePanelEigenesUN.add(labelGewinn, 10, 48);		
				absolutePanelEigenesUN.add(labelMarktanteil, 10, 72);
				absolutePanelEigenesUN.add(labelNachfrageTendenz, 10, 96);
				absolutePanelEigenesUN.add(labelUmsatzEUN, 129, 24);	
				absolutePanelEigenesUN.add(labelGewinnEUN, 129, 48);
				labelGewinnEUN.setSize("32px", "18px");		
				absolutePanelEigenesUN.add(labelMarktanteilEUN, 129, 72);
				labelMarktanteilEUN.setSize("32px", "18px");		
				absolutePanelEigenesUN.add(labelNachfrageEUN, 129, 96);
				labelNachfrageEUN.setSize("49px", "18px");
			// weitere Unternehmen
				// TODO: absolutePanelUnternehmen.length durch anzahlUnternehmen ersetzen, wenn DB Daten zurückgibt
			for (int i=0; i< absolutePanelUnternehmen.length; i++){
				int j = i+1;
				// neues Panel erzeugen 
				absolutePanelUnternehmen[i] = new AbsolutePanel();
				horizontalPanel.add(absolutePanelUnternehmen[i]);
				absolutePanelUnternehmen[i].setHeight("159px");
				// "Überschrift" anbringen
				labelUnternehmen[i] = new Label("Unternehmen " + j);
				labelUnternehmen[i].setStyleName("gwt-UnternehmenLabel");
				labelUnternehmen[i].setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				absolutePanelUnternehmen[i].add(labelUnternehmen[i],0,0);				
				labelUnternehmen[i].setSize("224px", "18px");
				// neue Labels erzeugen
				labelUmsatzUnternehmen[i] = new Label("Umsatz:");
				labelGewinnUnternehmen[i] = new Label("Gewinn: ");
				labelMarktanteilUnternehmen[i] = new Label("Marktanteil:");
				labelNachfrageTendenzUnternehmen[i] = new Label("Nachfragetendenz:");
				//Werte aus der DB übernehmen
				labelUmsatzUnternehmenWert[i] = new Label(unternehmen[i].getUmsatz() + " €");
				labelGewinnUnternehmenWert[i] = new Label(unternehmen[i].getGewinn() + " €");
				labelMarktanteilUnternehmenWert[i] = new Label(unternehmen[i].getMarktAnteil() + " %");
				labelNachfrageTendenzUnternehmenWert[i] = new Label(unternehmen[i].getNachfrageTendenz());				
				// Labels auf dem Panel anbringen
				absolutePanelUnternehmen[i].add(labelUmsatzUnternehmen[i], 10, 24);		
				absolutePanelUnternehmen[i].add(labelGewinnUnternehmen[i], 10, 48);		
				absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmen[i], 10, 72);
				absolutePanelUnternehmen[i].add(labelNachfrageTendenzUnternehmen[i], 10, 96);				
				absolutePanelUnternehmen[i].add(labelUmsatzUnternehmenWert[i], 129, 24);		
				absolutePanelUnternehmen[i].add(labelGewinnUnternehmenWert[i], 129, 48);
				labelGewinnUnternehmenWert[i].setSize("32px", "18px");		
				absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmenWert[i], 129, 72);
				labelMarktanteilUnternehmenWert[i].setSize("32px", "18px");		
				absolutePanelUnternehmen[i].add(labelNachfrageTendenzUnternehmenWert[i], 129, 96);
				labelNachfrageTendenzUnternehmenWert[i].setSize("49px", "18px");				
				
			}
		

		
		// Button, um zur Unternehmen bearbeiten Oberfläche zu bearbeiten
		absolutePanelHome.add(buttonUnternehmenBearbeiten, 275, 337);
		buttonUnternehmenBearbeiten.setSize("154px", "35px");
		buttonUnternehmenBearbeiten.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				UnternehmenSimulation unternehmen = new UnternehmenSimulation();
				unternehmen.onModuleLoad();
			}
		});
		
		
		// Button, um zur Simulationsoberfläche zu gelangen
		absolutePanelHome.add(buttonSimulation, 608, 337);
		buttonSimulation.setSize("100px", "35px");
		buttonSimulation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				Simulation simulation = new Simulation();
				simulation.onModuleLoad();
			}
		});
		
		
		// Button zum ausloggen
		absolutePanelHome.add(buttonLogout, 914, 10);
		buttonLogout.setSize("100px", "35px");
		buttonLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Daten an DB übergeben
				
				RootPanel.get().clear();
				RootPanel.get().add(labelAusgeloggt);
			}
		});
		
		
	}
}
