package mediathek.javafx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FXDescriptionPanel extends JFXPanel {
    public FXDescriptionPanel() {
        super();
        Platform.runLater(this::initFX);
    }

    private void initFX() {
        setScene(createScene());
    }

    private Scene createScene() {
        WebView webView = new WebView();
        webView.setMaxHeight(100d);
        webView.setContextMenuEnabled(false);
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent("<html><body>Hello <b>World</b></body></html>");

        TabPane tabPane = new TabPane();
        Tab descTab = new Tab("Beschreibung");
        tabPane.getTabs().add(descTab);

        descTab.setContent(webView);
        descTab.setOnCloseRequest(e -> {
            getParent().setVisible(false);
            e.consume();
        });

        return new Scene(tabPane);
    }
}
