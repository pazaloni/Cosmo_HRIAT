package gui;





import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import controllers.ParticipantUpdateFormController;
import core.MedicalAdministrator;
import core.PopUpMessage;
import core.StaffAccount;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Purpose: This class will display the editable participant update form with
 * data from the database
 * 
 * @author Andrew Hundeby cst205
 */
public class PrintPreviewUpdateFormGUI extends ScrollPane
{
    // scrollable form
    private ScrollPane mainContainer = new ScrollPane();
    // int to hold the cosmoId
    private int cosmoID;
    // the main vbox
    private VBox mainBox;
    // the main vbox
    private VBox mainBoxPlusSave;
    // the controller instance
    private ParticipantUpdateFormController controller;
    // button to print
    private Button printBtn = new Button("Print");
    // print preview stage
    public static Stage mainStage;
    // completion text
    public Label compTextLbl = new Label(
            "Please complete the following information and return "
                    + "the completed form to Cosmopolitan Industries Ltd. "
                    + "by __________________   so "
                    + "we can update our files.");

    // completion string
    public Label heading = new Label("Participant Information Update Form");

    // The TextFields for the participant Information
    private TextField nameTxt = new TextField();
    private TextField addressTxt = new TextField();
    private TextField cityTxt = new TextField();
    private TextField postalCodeTxt = new TextField();
    private TextField phoneTxt = new TextField();
    private TextField PHNTxt = new TextField();
    private TextField SINTxt = new TextField();

    // The TextFields for the Kin Information
    private TextField kinNameTxt = new TextField();
    private TextField kinAddressTxt = new TextField();
    private TextField kinCityTxt = new TextField();
    private TextField kinPostalCodeTxt = new TextField();
    private TextField kinHomePhoneTxt = new TextField();
    private TextField kinWorkPhoneTxt = new TextField();

    // The TextFields for the Caregiver Information
    private TextField caregiverNameTxt = new TextField();
    private TextField caregiverAddressTxt = new TextField();
    private TextField caregiverCityTxt = new TextField();
    private TextField caregiverPostalCodeTxt = new TextField();
    private TextField caregiverHomePhoneTxt = new TextField();
    private TextField caregiverWorkPhoneTxt = new TextField();

    // The TextFields for the Emergency Contact Information
    private TextField emergencyContactName = new TextField();
    private TextField emergencyContactPhone = new TextField();

    private StaffAccount loggedInUser;

    /**
     * Purpose: displays the GUI
     * 
     * @param: Stage: the stage the GUI will be displayed on
     */
    public void start( Stage stage ) throws Exception
    {
        PrintPreviewUpdateFormGUIConstruct(stage, this.cosmoID, loggedInUser);
    }

    /**
     * Purpose: Creates the form, populated with information related to the
     * participant.
     * 
     * @param cosmoID
     *            The primary key for the participant to get information for.
     * @author Andrew Hundeby cst205
     */
    public void PrintPreviewUpdateFormGUIConstruct( Stage stage, int cosmoID,
            StaffAccount loggedInUser )
    {
        this.cosmoID = cosmoID;
        this.loggedInUser = loggedInUser;

        // the VBox containing all other viewable nodes
        mainBox = new VBox();
        mainBox.setMaxHeight(900);
        mainBox.setMinHeight(900);
        mainBox.setMaxWidth(850);
        mainBox.setMinWidth(850);
        // the controller used to get information from the database
        controller = new ParticipantUpdateFormController(cosmoID);

        heading.setFont(new Font("Arial", 22));
        heading.setWrapText(true);
        heading.setAlignment(Pos.CENTER);
        heading.setTextAlignment(TextAlignment.CENTER);

        compTextLbl.setFont(new Font("Arial", 14));
        compTextLbl.setWrapText(true);
        compTextLbl.setAlignment(Pos.CENTER);
        compTextLbl.setTextAlignment(TextAlignment.CENTER);

        // label for the Kin Information Area of the form
        Label basicInfoAreaHeader = new Label("Participant Information:");
        basicInfoAreaHeader.setFont(new Font("Arial", 16));

        // label for the Kin Information Area of the form
        Label kinInfoAreaHeader = new Label("Kin Information:");
        kinInfoAreaHeader.setFont(new Font("Arial", 16));

        // label for the Caregiver Information Area of the form
        Label caregiverInfoAreaHeader = new Label("Caregiver Information:");
        caregiverInfoAreaHeader.setFont(new Font("Arial", 16));

        // label for the Emergency Contact Information Area of the form
        Label emergencyContactInfoHeader = new Label(
                "Emergency Contact Information:");
        emergencyContactInfoHeader.setFont(new Font("Arial", 16));

        // add all viewable nodes to the main VBox
        mainBox.getChildren().addAll(heading, compTextLbl, basicInfoAreaHeader,
                createBasicInfoArea(), kinInfoAreaHeader, createKinInfoArea(),
                caregiverInfoAreaHeader, createCaregiverInfoArea(),
                emergencyContactInfoHeader, createEmergencyContactArea());

        if ( loggedInUser instanceof MedicalAdministrator )
        {
            printBtn.setOnAction(event -> {

                WritableImage formImage = mainBox.snapshot(
                        new SnapshotParameters(), null);
                BufferedImage buffImage = SwingFXUtils.fromFXImage(formImage,
                        null);

                PrinterJob printJob = PrinterJob.getPrinterJob();
                printJob.setPrintable(new ImagePrintable(printJob, buffImage));

                if (printJob.printDialog()) {
                    try {
                        printJob.print();
                    } catch (PrinterException prt) {
                        prt.printStackTrace();
                    }
                }

                
            });
        }

        // making form pretty
        mainBox.setMargin(heading, new Insets(20, 230, 10, 230));
        mainBox.setMargin(compTextLbl, new Insets(10, 100, 10, 100));
        mainBox.setMargin(basicInfoAreaHeader, new Insets(10, 10, 10, 100));
        mainBox.setMargin(kinInfoAreaHeader, new Insets(10, 10, 10, 100));
        mainBox.setMargin(caregiverInfoAreaHeader, new Insets(10, 10, 10, 100));
        mainBox.setMargin(emergencyContactInfoHeader, new Insets(10, 10, 10,
                100));
        mainBox.setMargin(printBtn, new Insets(10, 300, 10, 300));
        printBtn.setMinWidth(200);

        // populate the textboxes with all relevant information from the
        // database
        this.fillBasicText();
        this.fillKinText();
        this.fillCaregiverText();
        this.fillEmergencyText();

        mainStage = stage;
        mainStage.setTitle("Cosmo Industries - " + loggedInUser.GetUsername());

        // separate form from button for printing
        mainBoxPlusSave = new VBox();
        mainBoxPlusSave.getChildren().addAll(mainBox, printBtn);

        // make the form scrollable
        mainContainer.setContent(mainBoxPlusSave);
        mainContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
        mainContainer.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        mainContainer.setHmax(mainBox.getWidth());

        mainStage.setScene(new Scene(mainContainer, 850, 900));
        mainStage.setMaxHeight(900);
        mainStage.setMinHeight(900);
        mainStage.setMaxWidth(850);
        mainStage.setMinWidth(850);
        mainStage.resizableProperty().set(true);
        mainStage.show();
        // Event for when stage is closed

    }

    /**
     * Purpose: Create the area on the form containing the basic information.
     * 
     * @return the GridPane containing the labels and textfields to be populated
     *         with the participants's information.
     * @author Andrew Hundeby cst205
     */
    private GridPane createBasicInfoArea()
    {
        // GridPane containing the kin labels and textfields
        GridPane addLongAddress = new GridPane();
        GridPane main = new GridPane();

        // all labels for kin information
        Label nameLbl = new Label("Name:");
        Label addressLbl = new Label("Address:");
        Label cityLbl = new Label("City:");
        Label postalCodeLbl = new Label("Postal Code:");
        Label phoneLbl = new Label("Phone #:");
        Label PHNLbl = new Label("PHN:");
        Label SINLbl = new Label("SIN:");

        // insets for the padding and margins
        Insets insets = new Insets(5, 5, 5, 5);

        // set padding for all labels
        nameLbl.setPadding(insets);
        addressLbl.setPadding(insets);
        cityLbl.setPadding(insets);
        postalCodeLbl.setPadding(insets);
        phoneLbl.setPadding(insets);
        PHNLbl.setPadding(insets);
        SINLbl.setPadding(insets);

        // the lines for client input
        Separator line = new Separator();
        Separator line2 = new Separator();
        Separator line3 = new Separator();
        Separator line4 = new Separator();
        Separator line5 = new Separator();
        Separator line6 = new Separator();
        Separator line7 = new Separator();
        line.minWidth(120);
        line2.minWidth(120);
        line3.minWidth(120);
        line4.minWidth(120);
        line5.minWidth(120);
        line6.minWidth(120);
        line7.minWidth(120);

        // first row
        main.add(nameLbl, 0, 0);
        main.add(nameTxt, 1, 0);
        main.add(new Label(), 1, 1);
        main.add(line, 1, 2);

        main.add(phoneLbl, 2, 0);
        main.add(phoneTxt, 3, 0);
        main.add(new Label(), 3, 1);
        main.add(line5, 3, 2);

        main.add(SINLbl, 4, 0);
        main.add(SINTxt, 5, 0);
        main.add(new Label(), 5, 1);
        main.add(line7, 5, 2);

        // second row
        main.add(PHNLbl, 0, 3);
        main.add(PHNTxt, 1, 3);
        main.add(new Label(), 1, 4);
        main.add(line6, 1, 5);

        main.add(postalCodeLbl, 2, 3);
        main.add(postalCodeTxt, 3, 3);
        main.add(new Label(), 3, 4);
        main.add(line4, 3, 5);

        main.add(cityLbl, 4, 3);
        main.add(cityTxt, 5, 3);
        main.add(new Label(), 5, 4);
        main.add(line3, 5, 5);

        // third row
        GridPane addressBox = new GridPane();
        // addressBox.getChildren().addAll(addressLbl,
        // addressTxt, new Label(), line2);
        addressBox.add(addressLbl, 0, 0);
        addressBox.add(addressTxt, 1, 0);
        addressBox.add(new Label(), 1, 1);
        addressBox.add(line2, 1, 2);

        // set margins for all the textfields
        main.setMargin(nameTxt, insets);
        main.setMargin(addressTxt, insets);
        main.setMargin(cityTxt, insets);
        main.setMargin(postalCodeTxt, insets);
        main.setMargin(phoneTxt, insets);
        main.setMargin(PHNTxt, insets);
        main.setMargin(SINTxt, insets);

        addressTxt.setMinWidth(300);

        // center the gridpane
        main.alignmentProperty().set(Pos.CENTER);

        addLongAddress.add(main, 0, 0);
        addLongAddress.add(addressBox, 0, 1);
        addLongAddress.alignmentProperty().set(Pos.CENTER);

        return addLongAddress;

    }

    /**
     * Purpose: Create the area on the form containing the kin information.
     * 
     * @return the GridPane containing the labels and textfields to be populated
     *         with the kin's information.
     * @author Andrew Hundeby cst205
     */
    private GridPane createKinInfoArea()
    {
        // GridPane containing the kin labels and textfields
        GridPane main = new GridPane();

        // all labels for kin information
        Label nameLbl = new Label("Name:");
        Label addressLbl = new Label("Address:");
        Label cityLbl = new Label("City:");
        Label postalCodeLbl = new Label("Postal Code:");
        Label phoneLbl = new Label("Phone Number:");
        Label homePhoneLbl = new Label("Phone (Home):");
        Label workPhoneLbl = new Label("Phone (Work):");

        // insets for the padding and margins
        Insets insets = new Insets(5, 5, 5, 5);

        // set padding for all labels
        nameLbl.setPadding(insets);
        addressLbl.setPadding(insets);
        cityLbl.setPadding(insets);
        postalCodeLbl.setPadding(insets);
        phoneLbl.setPadding(insets);
        homePhoneLbl.setPadding(insets);
        workPhoneLbl.setPadding(insets);

        // the lines for client input
        Separator line = new Separator();
        Separator line2 = new Separator();
        Separator line3 = new Separator();
        Separator line4 = new Separator();
        Separator line5 = new Separator();
        Separator line6 = new Separator();
        line.minWidth(120);
        line2.minWidth(120);
        line3.minWidth(120);
        line4.minWidth(120);
        line5.minWidth(120);
        line6.minWidth(120);

        // first column
        main.add(nameLbl, 0, 0);
        main.add(kinNameTxt, 1, 0);
        main.add(new Label(), 1, 1);
        main.add(line, 1, 2);

        main.add(homePhoneLbl, 0, 3);
        main.add(kinHomePhoneTxt, 1, 3);
        main.add(new Label(), 1, 4);
        main.add(line3, 1, 5);

        main.add(workPhoneLbl, 0, 6);
        main.add(kinWorkPhoneTxt, 1, 6);
        main.add(new Label(), 1, 7);
        main.add(line6, 1, 8);

        // second column
        main.add(addressLbl, 2, 0);
        main.add(kinAddressTxt, 3, 0);
        main.add(new Label(), 3, 1);
        main.add(line4, 3, 2);

        main.add(cityLbl, 2, 3);
        main.add(kinCityTxt, 3, 3);
        main.add(new Label(), 3, 4);
        main.add(line2, 3, 5);

        main.add(postalCodeLbl, 2, 6);
        main.add(kinPostalCodeTxt, 3, 6);
        main.add(new Label(), 3, 7);
        main.add(line5, 3, 8);

        // set margins for all the textfields
        main.setMargin(kinNameTxt, insets);
        main.setMargin(kinAddressTxt, insets);
        main.setMargin(kinPostalCodeTxt, insets);
        main.setMargin(kinHomePhoneTxt, insets);
        main.setMargin(kinWorkPhoneTxt, insets);
        main.setMargin(kinCityTxt, insets);
        kinAddressTxt.setMinWidth(300);

        // center the gridpane
        main.alignmentProperty().set(Pos.CENTER);

        return main;

    }

    /**
     * Purpose: Create the area on the form for caregiver information.
     * 
     * @return the GridPane containing the labels and textfields for the
     *         caregivers information
     * @author Andrew Hudneby
     */
    private GridPane createCaregiverInfoArea()
    {
        // the GridPane containing the labels and textfields for
        // the caregivers information
        GridPane main = new GridPane();

        // all labels for caregiver information
        Label nameLbl = new Label("Name:");
        Label addressLbl = new Label("Address:");
        Label cityLbl = new Label("City:");
        Label postalCodeLbl = new Label("Postal Code:");
        Label homePhoneLbl = new Label("Phone (Home):");
        Label workPhoneLbl = new Label("Phone (Work):");

        // the insets for padding and margins
        Insets insets = new Insets(5, 5, 5, 5);

        // set the padding for all labels
        nameLbl.setPadding(insets);
        addressLbl.setPadding(insets);
        cityLbl.setPadding(insets);
        postalCodeLbl.setPadding(insets);
        homePhoneLbl.setPadding(insets);
        workPhoneLbl.setPadding(insets);

        // the lines for client input
        Separator line = new Separator();
        Separator line2 = new Separator();
        Separator line3 = new Separator();
        Separator line4 = new Separator();
        Separator line5 = new Separator();
        Separator line6 = new Separator();
        line.minWidth(120);
        line2.minWidth(120);
        line3.minWidth(120);
        line4.minWidth(120);
        line5.minWidth(120);
        line6.minWidth(120);

        // first column
        main.add(nameLbl, 0, 0);
        main.add(caregiverNameTxt, 1, 0);
        main.add(new Label(), 1, 1);
        main.add(line, 1, 2);

        main.add(homePhoneLbl, 0, 3);
        main.add(caregiverHomePhoneTxt, 1, 3);
        main.add(new Label(), 1, 4);
        main.add(line3, 1, 5);

        main.add(workPhoneLbl, 0, 6);
        main.add(caregiverWorkPhoneTxt, 1, 6);
        main.add(new Label(), 1, 7);
        main.add(line6, 1, 8);

        // second column
        main.add(addressLbl, 2, 0);
        main.add(caregiverAddressTxt, 3, 0);
        main.add(new Label(), 3, 1);
        main.add(line4, 3, 2);

        main.add(cityLbl, 2, 3);
        main.add(caregiverCityTxt, 3, 3);
        main.add(new Label(), 3, 4);
        main.add(line2, 3, 5);

        main.add(postalCodeLbl, 2, 6);
        main.add(caregiverPostalCodeTxt, 3, 6);
        main.add(new Label(), 3, 7);
        main.add(line5, 3, 8);

        // set margins for all of the textfields
        main.setMargin(caregiverNameTxt, insets);
        main.setMargin(caregiverAddressTxt, insets);
        main.setMargin(caregiverCityTxt, insets);
        main.setMargin(caregiverPostalCodeTxt, insets);
        main.setMargin(caregiverHomePhoneTxt, insets);
        main.setMargin(caregiverWorkPhoneTxt, insets);
        caregiverAddressTxt.setMinWidth(300);

        // center the main gridpane
        main.alignmentProperty().set(Pos.CENTER);

        return main;
    }

    /**
     * Purpose: Creates the area of the form containing the Emergency Contact
     * Information.
     * 
     * @return the GridPane containing the labels and textfields for the
     *         emergency contact information
     * @author Andrew Hundeby cst205
     */
    private GridPane createEmergencyContactArea()
    {

        GridPane mainWithMsg = new GridPane();

        // the gridpane containing the labels and
        // textfields for emergency contact information
        GridPane main = new GridPane();

        // the labels for emergency contact information
        Label name = new Label("First Name:");
        Label lastName = new Label("Last Name");
        Label phone = new Label("Phone Number:");

        // insets for padding and margins
        Insets insets = new Insets(5, 5, 5, 5);

        // set padding for all labels
        name.setPadding(insets);
        lastName.setPadding(insets);
        phone.setPadding(insets);

        // the lines for client input
        Separator line = new Separator();
        Separator line2 = new Separator();
        line.minWidth(120);
        line2.minWidth(120);

        // add labels and textfields to the main gridpane

        // first column
        main.add(name, 0, 0);
        main.add(emergencyContactName, 1, 0);
        main.add(new Label(), 1, 1);
        main.add(line, 1, 2);

        // second column
        main.add(phone, 2, 0);
        main.add(emergencyContactPhone, 3, 0);
        main.add(new Label(), 3, 1);
        main.add(line2, 3, 2);

        Label temp = new Label();
        temp.setMinWidth(150);
        main.add(temp, 4, 0);

        // set margins for the textfields
        main.setMargin(emergencyContactName, insets);
        main.setMargin(emergencyContactPhone, insets);

        mainWithMsg.add(new Label("Please provide a daytime emergency "
                + "contact in case of illness or injury during "
                + "Cosmopolitan business hours"), 0, 0);
        mainWithMsg.add(main, 0, 1);

        // center the gridpane
        main.alignmentProperty().set(Pos.CENTER);
        mainWithMsg.alignmentProperty().set(Pos.CENTER);

        return mainWithMsg;
    }

    /**
     * Purpose: Calls the ParticipantUpdateFormController to get all of the
     * kin's information from the database as required for this form, and puts
     * the information into the correlating textfields.
     * 
     * @author Andrew Hundeby cst205
     */
    private void fillBasicText()
    {
        // get the kin's information
        String[] kinInfo = this.controller.fetchParticipantBasicInfo();

        // put the information into the textfields
        nameTxt.setText(kinInfo[0] + " " + kinInfo[1]);
        addressTxt.setText(kinInfo[2]);
        cityTxt.setText(kinInfo[3]);
        postalCodeTxt.setText(kinInfo[4]);
        phoneTxt.setText(kinInfo[5]);
        PHNTxt.setText(kinInfo[6]);
        SINTxt.setText(kinInfo[7]);
    }

    /**
     * Purpose: Calls the ParticipantUpdateFormController to get all of the
     * kin's information from the database as required for this form, and puts
     * the information into the correlating textfields.
     * 
     * @author andrew hundeby cst205
     */
    private void fillKinText()
    {
        // get the kin's information
        String[] kinInfo = this.controller.fetchKinInfo();

        // put the information into the textfields
        kinNameTxt.setText(kinInfo[0] + " " + kinInfo[1]);
        kinAddressTxt.setText(kinInfo[2]);
        kinCityTxt.setText(kinInfo[3]);
        kinPostalCodeTxt.setText(kinInfo[4]);
        kinHomePhoneTxt.setText(kinInfo[5]);
        kinWorkPhoneTxt.setText(kinInfo[6]);
    }

    /**
     * Purpose: Calls the ParticipantUpdateFormController to get all of the
     * caregiver's information from the databse as required for this form, and
     * puts the information into the correlating textfields.
     * 
     * @author andrew hundeby cst205
     */
    private void fillCaregiverText()
    {
        // get the caregivers information
        String[] cgInfo = this.controller.fetchCaregiverInfo();

        // puts information into the textfields
        caregiverNameTxt.setText(cgInfo[0] + " " + cgInfo[1]);
        caregiverAddressTxt.setText(cgInfo[2]);
        caregiverCityTxt.setText(cgInfo[3]);
        caregiverPostalCodeTxt.setText(cgInfo[4]);
        caregiverHomePhoneTxt.setText(cgInfo[5]);
        caregiverWorkPhoneTxt.setText(cgInfo[6]);
    }

    /**
     * Purpose: Calls the ParticipantUdpateFormController to get all of the
     * Emergency Contact's information from the database as required for the
     * form, and puts the information into the correlating textfields.
     * 
     * @author andrew hundeby cst205
     */
    private void fillEmergencyText()
    {
        // gets the Emergency Contact's information
        String[] eInfo = this.controller.fetchEmergencyContactInfo();

        // puts information into the textfields
        emergencyContactName.setText(eInfo[0] + " " + eInfo[1]);
        emergencyContactPhone.setText(eInfo[2]);
    }
    
    /**
     * Purpose: inner class
     * @author cst205
     *
     */
    public class ImagePrintable implements Printable 
    {

        private double          x, y, width;

        private int             orientation;

        private BufferedImage   image;

        /**
         * Purpose:  for setting the multiple variables
         * @param printJob is used to fetch info such as x, y and width dimensions
         * @param image is scene coming in
         */
        public ImagePrintable(PrinterJob printJob, BufferedImage image) 
        {
            PageFormat pageFormat = printJob.defaultPage();
            this.x = 0;
            this.y = 0;
            this.width = pageFormat.getImageableWidth();
            this.orientation = pageFormat.getOrientation();
            this.image = image;
        }

        /**
         * Purpose: checks to see if orientation is portrait or landscape
         * and prints accordingly
         */
        @Override
        public int print(Graphics g, PageFormat pageFormat, int pageIndex)
                throws PrinterException 
        {
            if (pageIndex == 0) 
            {
 
                
                g.drawImage(image, (int) x, (int) y, 650, 800, null);
                
                //width = 468 height = 495
                return PAGE_EXISTS;
            }
            else 
            {
                return NO_SUCH_PAGE;
            }
        }
    }


    

}
