package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class NotepadController {
    private StringBuilder sb;
    private final int distance = 4;

    @FXML
    private TextArea textArea;



    @FXML
    void decrypt(ActionEvent event) {
         sb = new StringBuilder(textArea.getText());


        for(int i = 0;i < sb.length();i++){
            int val = (int) sb.charAt(i);

            if(val - distance < 31 ){
                val = 122 - (distance + (31 - val));
            }else {
                val -= distance;
            }

            sb.setCharAt(i,(char)val);
        }
        textArea.setText(sb.toString());

    }

    @FXML
    void encrypt(ActionEvent event) {
       sb = new StringBuilder(textArea.getText());


        for(int i = 0;i < sb.length();i++){
        int val = (int) sb.charAt(i);

        if(val + distance > 122 ){
            val = 31 + (distance - (122 - val));
        }else {
            val += distance;
        }

        sb.setCharAt(i,(char)val);
        }
        textArea.setText(sb.toString());

    }


    @FXML
    void save(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file","*.txt"));
        File file = fc.showSaveDialog(null);



        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(textArea.getText());
            writer.close();
        } catch (NullPointerException e) {
            e.getMessage();
        }

    }



    @FXML
    void openFile(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file","*.txt"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Any file","*.*"));
        File selectedFile = fc.showOpenDialog(null);
        String fileContent = Files.readString(Paths.get(selectedFile.getPath()));





        if (getFileExtension(selectedFile).equals("*.txt")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Wybrano zły typ pliku");
            alert.show();

        } else if (selectedFile == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Nie wybrano pliku");
            alert.show();

        } else {
            textArea.setText(fileContent);
        }
    }



    private static String getFileExtension(File file) {
        String extension = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }

        return extension;

    }

}