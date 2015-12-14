package org.example;

import griffon.core.artifact.GriffonController;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Map;

@ArtifactProviderFor(GriffonController.class)
public class SampleViewModel extends AbstractGriffonController {
    private SampleView view;
    private StringProperty input;
    private StringProperty output;

    @Nonnull
    public final StringProperty inputProperty() {
        if (input == null) {
            input = new SimpleStringProperty(this, "input");
        }
        return input;
    }

    public void setInput(String input) {                                //<3>
        inputProperty().set(input);
    }

    public String getInput() {                                          //<3>
        return input == null ? null : inputProperty().get();
    }

    @Nonnull
    public final StringProperty outputProperty() {
        if (output == null) {
            output = new SimpleStringProperty(this, "output");
        }
        return output;
    }

    public void setOutput(String output) {                              //<3>
        outputProperty().set(output);
    }

    public String getOutput() {                                         //<3>
        return output == null ? null : outputProperty().get();
    }

    @Inject
    private SampleService sampleService;

    public void setView(SampleView view) {
        this.view = view;
    }

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {
        inputProperty().bindBidirectional(view.getInput().textProperty());
        outputProperty().bindBidirectional(view.getOutput().textProperty());
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void sayHello() {
        setOutput(sampleService.sayHello(getInput()));
    }
}