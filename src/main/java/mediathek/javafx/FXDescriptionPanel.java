package mediathek.javafx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jfxtras.scene.menu.CirclePopupMenu;
import mediathek.config.Daten;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import javax.swing.*;

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

        CirclePopupMenu menu = new CirclePopupMenu(webView, MouseButton.SECONDARY);
        GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

        TabPane tabPane = new TabPane();
        Tab descTab = new Tab("Beschreibung");
        tabPane.getTabs().add(descTab);
        MenuItem mi = new MenuItem("Beschreibung bearbeiten", fontAwesome.create(org.controlsfx.glyphfont.FontAwesome.Glyph.PENCIL_SQUARE_ALT).size(32));
        menu.getItems().add(mi);
        mi = new MenuItem("Website besuchen", fontAwesome.create(org.controlsfx.glyphfont.FontAwesome.Glyph.CLOUD_DOWNLOAD).size(32).color(javafx.scene.paint.Color.BLUE));
        menu.getItems().add(mi);
        mi = new MenuItem("Schrift vergrößern", fontAwesome.create(org.controlsfx.glyphfont.FontAwesome.Glyph.CLOUD_DOWNLOAD).size(32).color(javafx.scene.paint.Color.GREEN));
        menu.getItems().add(mi);
        mi = new MenuItem("Schrift verkleinern", fontAwesome.create(org.controlsfx.glyphfont.FontAwesome.Glyph.CLOUD_DOWNLOAD).size(32).color(javafx.scene.paint.Color.BLUEVIOLET));
        menu.getItems().add(mi);
        mi = new MenuItem("Schrift zurücksetzen", fontAwesome.create(org.controlsfx.glyphfont.FontAwesome.Glyph.CLOUD_DOWNLOAD).size(32));
        menu.getItems().add(mi);

        descTab.setContent(webView);
        descTab.setOnClosed(e -> SwingUtilities.invokeLater(() -> {
                    getParent().remove(this);
                    Daten.getInstance().getMediathekGui().tabFilme.updateUI();
                }
        ));

        return new Scene(tabPane);
    }
}
