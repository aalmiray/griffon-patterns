package org.example;

import griffon.core.artifact.GriffonView;
import griffon.metadata.ArtifactProviderFor;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;

import javax.annotation.Nonnull;
import java.util.Collections;

@ArtifactProviderFor(GriffonView.class)
public class SampleView extends AbstractJavaFXGriffonView {
    private SampleViewModel viewModel;

    @FXML private TextField input;
    @FXML private Label output;

    public void setViewModel(SampleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initUI() {
        Stage stage = (Stage) getApplication()
            .createApplicationContainer(Collections.<String, Object>emptyMap());
        stage.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        stage.setWidth(400);
        stage.setHeight(120);
        stage.setScene(init());
        getApplication().getWindowManager().attach("mainWindow", stage);
    }

    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);

        Node node = loadFromFXML();
        ((Group) scene.getRoot()).getChildren().addAll(node);
        connectActions(node, viewModel);                                       //<1>
        input.textProperty().bindBidirectional(viewModel.inputProperty());     //<2>
        output.textProperty().bindBidirectional(viewModel.outputProperty());   //<2>

        return scene;
    }
}