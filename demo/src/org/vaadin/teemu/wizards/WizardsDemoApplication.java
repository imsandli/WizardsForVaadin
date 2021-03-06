package org.vaadin.teemu.wizards;

import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardProgressListener;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Demo application for the <a
 * href="http://vaadin.com/addon/wizards-for-vaadin">Wizards for Vaadin</a>
 * add-on.
 * 
 * @author Teemu Pöntelin / Vaadin Ltd
 */
@SuppressWarnings("serial")
public class WizardsDemoApplication extends Application implements
        WizardProgressListener {

    private Wizard wizard;
    private VerticalLayout mainLayout;

    @Override
    public void init() {
        // setup the main window
        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        Window mainWindow = new Window("WizardsDemoApplication");
        mainWindow.setContent(mainLayout);
        setMainWindow(mainWindow);

        // create the Wizard component and add the steps
        wizard = new Wizard();
        wizard.setUriFragmentEnabled(true);
        wizard.addListener(this);
        wizard.addStep(new IntroStep(), "intro");
        wizard.addStep(new SetupStep(), "setup");
        wizard.addStep(new ListenStep(), "listen");
        wizard.addStep(new LastStep(wizard), "last");
        wizard.setHeight("600px");
        wizard.setWidth("800px");

        mainLayout.addComponent(wizard);
        mainLayout.setComponentAlignment(wizard, Alignment.TOP_CENTER);
        setTheme("demo");
    }

    public void wizardCompleted(WizardCompletedEvent event) {
        endWizard("Wizard Completed!");
    }

    public void activeStepChanged(WizardStepActivationEvent event) {
        // display the step caption as the window title
        getMainWindow().setCaption(event.getActivatedStep().getCaption());
    }

    public void stepSetChanged(WizardStepSetChangedEvent event) {
        // NOP, not interested on this event
    }

    public void wizardCancelled(WizardCancelledEvent event) {
        endWizard("Wizard Cancelled!");
    }

    private void endWizard(String message) {
        wizard.setVisible(false);
        getMainWindow().showNotification(message);
        getMainWindow().setCaption(message);
        Button startOverButton = new Button("Run the demo again",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        WizardsDemoApplication.this.close();
                    }
                });
        mainLayout.addComponent(startOverButton);
        mainLayout.setComponentAlignment(startOverButton,
                Alignment.MIDDLE_CENTER);
    }

}
