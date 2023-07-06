import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.List;

public class Events {
    public static void setEventsMouseEnteredExitedForTipShowHide(Node node, Tooltip tooltip, String str) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() { //anonymous class
            @Override
            public void handle(MouseEvent event) {
              showTip(node, tooltip, str);
            }
        });
        node.setOnMouseExited(event -> hideTip(tooltip));
    }

    private static void showTip(Node node, Tooltip toolTip, String str) {
        toolTip.setText(str);
        Tooltip.install(node, toolTip);
        Bounds bounds = node.localToScreen(node.getBoundsInLocal());
        double x = bounds.getMinX() + node.getBoundsInLocal().getWidth() + 10;
        double y = bounds.getMaxY() + 10;
        toolTip.show(node, x, y);
    }

    private static void hideTip(Tooltip tooltip) {
        if (tooltip != null) {
            tooltip.hide();
        }
    }

    public static void addComboBoxDeleteElementEvent(ComboBox<File> comboBox, List<File> list) {
        if ((comboBox == null) || (list == null)) return;

        comboBox.setOnAction(event -> {
            if (comboBox.getItems().isEmpty()) return;
            File selectedElement = comboBox.getSelectionModel().getSelectedItem();
            ObservableList<File> observableList = FXCollections.observableArrayList(comboBox.getItems());
            observableList.remove(selectedElement);
            list.remove(selectedElement);
            Platform.runLater(() -> comboBox.setItems(observableList));
        });
    }
}
