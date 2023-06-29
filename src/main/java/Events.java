import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class Events {
    public static void setEventsMouseEnteredExited(Node node, Tooltip tooltip, String str) {
        node.setOnMouseEntered(event -> {
            showTip(node, tooltip, str);
        });
        node.setOnMouseExited(event -> {
            hideTip(tooltip);
        });
    }

    private static void showTip(Node node, Tooltip toolTip, String str) {
        toolTip = new Tooltip(str);
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
}
