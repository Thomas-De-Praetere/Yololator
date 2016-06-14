/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yololatorMain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 *
 * @author Thomas De Praetere
 */
public class SceneCompanion {

    @FXML
    private TextArea inputField;
    @FXML
    private TextArea outputField;
    @FXML
    private Button button;
    Translator t;

    public TextArea getInputField() {
        return inputField;
    }

    public void setInputField(TextArea inputField) {
        this.inputField = inputField;
    }

    public TextArea getOutputField() {
        return outputField;
    }

    public void setOutputField(TextArea outputField) {
        this.outputField = outputField;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public SceneCompanion() {
        t = new Translator();
    }
    
    public void initialize(){
        button.setText("Consult Swagtionary");
    }

    public void translate() {
        BufferedWriter bw = null;
        try {
            String translate = t.translate(this.inputField.getText());
            this.outputField.setText(translate);
            File file = new File("Output.txt");
            bw = new BufferedWriter(new FileWriter(file));
            bw.append(translate);
        } catch (IOException ex) {
            Logger.getLogger(SceneCompanion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(SceneCompanion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
