package gui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.ProgressNotes;
import core.ScanForms;

public class ManageScanFormsGUI
{

    private Stage parentStage;

    private Label lblMessage;

    private GridPane mainPane;

    private TextArea description;
    private Button imageBtn;

    private Label lblDescription;
    private Label lblImageVal;

    private Button btnAdd, btnCancel;

    public ManageScanFormsGUI(Stage parentStage)
    {
        this.parentStage = parentStage;

        this.description = new TextArea();
        this.imageBtn = new Button("Choose File");

        this.lblDescription = new Label("Description: ");
        this.lblImageVal = new Label("");

        this.btnAdd = new Button("Save");
        this.btnCancel = new Button("Cancel");

        mainPane = new GridPane();
    }

    /**
     * Purpose: show the window for the addition of a new progressNote
     * 
     * @param cosmoID
     *            the participant that will be getting the new progresNote
     */
    public void showAddImage( String cosmoID )

    {
        Stage localStage = new Stage();
        lblMessage = new Label("");
        lblMessage.setTextFill(Color.FIREBRICK);
        
        mainPane.add(lblMessage, 1, 0,2,1);

        mainPane.add(lblDescription, 0, 0);
        mainPane.add(lblImageVal, 1, 2);

        mainPane.add(description, 0, 1,3,1);
        mainPane.add(imageBtn, 0, 2);

        description.setMaxWidth(250);
        description.setWrapText(true);
        imageBtn.setMaxWidth(250);

        imageBtn
        .setOnAction(event -> {
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                    "Images", "*.jpg");
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(filter);

            String path = "";

            File file = fc.showOpenDialog(localStage);
            if ( file == null )
            {
                path = "";
            }
            else
            {
                path = file.getAbsolutePath();
            }
            lblImageVal.setText(path);
        });

        
        HBox controls = new HBox();
        
        controls.getChildren().addAll(btnCancel, btnAdd);
        controls.setMinWidth(300);
        controls.setSpacing(10);

        mainPane.add(controls, 1, 3, 2, 1);

        mainPane.setHgap(15);
        mainPane.setVgap(15);

        mainPane.setPadding(new Insets(10, 10, 10, 10));

        btnAdd.setOnAction(event -> {
            if ( lblImageVal.getText().length() != 0 )
            {
                String result = ScanForms.createImage(
                        new ScanForms(LocalDate.now(), description
                                .getText(), lblImageVal.getText()), cosmoID);
                if ( result.equals("") )
                {
                    localStage.close();
                }
                else
                {
                    lblMessage.setText(result);
                }
            }
            else
            {
                lblMessage.setText("Pick an image to add.");
            }
        });
        btnCancel.setOnAction(event -> {
            localStage.close();
        });
        Scene scene = new Scene(mainPane, 400, 300);
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle("Add an image");
        localStage.showAndWait();

    }

    /**
     * Method to display a view of the image.
     * @param scannedForm the form to be displayed
     * @param cosmoId the person whose form is being displayed
     */
    public void showScannedForm( ScanForms scannedForm, String cosmoId )
    {
        Stage localStage = new Stage();
        
        ScrollPane imagePane = new ScrollPane();
        ImageView viewImage = new ImageView();
        
        URL u = null;
        try
        {

                u = (this.getClass().getProtectionDomain().getCodeSource()
                        .getLocation().toURI().toURL());

        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }

        String url = u.toString();

        url = url.substring(0,
                url.length() - (url.length() - url.lastIndexOf("/")));

        url = url.replace("/bin", "");

        Image img = new Image(url + scannedForm.getFileName().get());        

        if ( !(img.isError()) )
        {
            viewImage.setImage(img);
        }
        else
        {
            viewImage.setImage(new Image("images/imageNotFound.png"));
        }
        imagePane.setContent(viewImage);
        Scene scene = new Scene(imagePane, img.getWidth() - 7, img.getHeight() - 7);        
        localStage.setScene(scene);
        localStage.setResizable(false);
        localStage.initModality(Modality.WINDOW_MODAL);
        localStage.initOwner(parentStage);
        localStage.setTitle(scannedForm.getFileName().get());
        localStage.showAndWait();
        
    }
}
