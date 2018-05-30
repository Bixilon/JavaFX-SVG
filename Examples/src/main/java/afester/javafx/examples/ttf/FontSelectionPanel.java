package afester.javafx.examples.ttf;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


interface FontSelectedHandler extends EventHandler<FontChangedEvent> {
}


class FontSelectionSkin extends SkinBase<FontSelectionPanel> {

    private ListView<String> fontFamilies;
    private CheckBox italic;
    private ListView<FontWeight> fontWeight;
    private ListView<Integer> fontSize;

    private void fireEvent(FontSelectionPanel fsp) {
        // TODO: This is not completely accurate - better use property bindings!
        String fontFamily = fontFamilies.getSelectionModel().getSelectedItem();
        boolean isItalic = italic.isSelected();
        FontWeight weight = fontWeight.getSelectionModel().getSelectedItem();
        Integer size = fontSize.getSelectionModel().getSelectedItem();
        fsp.getOnFontChanged().handle(new FontChangedEvent(fontFamily, isItalic, weight, size));
    }

    protected FontSelectionSkin(FontSelectionPanel fsp) {
        super(fsp);

/// Font family
        VBox fontFamilyPanel = new VBox();
        fontFamilies = new ListView<>();
        Font.getFamilies().forEach(e -> fontFamilies.getItems().add(e));
        fontFamilies.getSelectionModel().select(0);
        fontFamilies.getSelectionModel().getSelectedIndices().addListener( new ListChangeListener<Integer>() {
    
            @Override
            public void onChanged(Change<? extends Integer> arg0) {
                if (arg0.next()) {
                    //Integer idx = arg0.getAddedSubList().get(0);
                    //String fontFamily = fontFamilies.getItems().get(idx);

                    fireEvent(fsp);

                    // Font font = Font.font(fontFamily);
                    // FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
                }
            }

        } );

        fontFamilyPanel.getChildren().addAll(new Text("Font"), fontFamilies, new Button("Load ..."));
    
/// Font style
        VBox fontStylePanel = new VBox();
        italic = new CheckBox("Italic");
        italic.setOnAction( e-> {
            fireEvent(fsp);
        }); 

        fontWeight = new ListView<>();
        fontWeight.getItems().addAll(FontWeight.THIN, FontWeight.LIGHT, FontWeight.NORMAL, FontWeight.BOLD);
        fontWeight.getSelectionModel().select(FontWeight.NORMAL);
        fontWeight.getSelectionModel().getSelectedIndices().addListener( new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> arg0) {
                fireEvent(fsp);
            }
        });

        fontStylePanel.getChildren().addAll(new Text("Style"), italic, fontWeight);

/// Font size
        VBox fontSizePanel = new VBox();
        fontSize= new ListView<>();
        //fontSize.getItems().addAll("6.0", "8.0", "10.0", "11.0", "12.0", "14.0", "16.0", "18.0", "20.0", "22.0", "24.0", "26.0",
        //                           "28.0", "30.0", "32.0", "34.0", "36.0", "38.0", "40.0", "42.0", "44.0", "46.0");
        fontSize.getItems().addAll(6, 8, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26,
                                   28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50,
                                   52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80);
        fontSize.getSelectionModel().select(Integer.valueOf(12));
        fontSize.getSelectionModel().getSelectedIndices().addListener( new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> arg0) {
                fireEvent(fsp);
            }
        });
        
        fontSizePanel.getChildren().addAll(new Text("Size"), fontSize);

        HBox fontPanel = new HBox();
        fontPanel.getChildren().addAll(fontFamilyPanel, fontStylePanel, fontSizePanel);
        getChildren().add(fontPanel);
    }
}



public class FontSelectionPanel extends Control {
   
/// The event handler which is called when a font has been selected 

    private ObjectProperty<EventHandler<? super FontChangedEvent>> onFontChanged = new SimpleObjectProperty<>();

    public final void setOnFontChanged(EventHandler<? super FontChangedEvent> value) {
        onFontChanged.setValue(value); 
    }
    public final EventHandler<? super FontChangedEvent> getOnFontChanged() {
        return onFontChanged.getValue();
    }
    public final ObjectProperty<EventHandler<? super FontChangedEvent>> onFontChangedProperty() {
        return onFontChanged;
    }


    public FontSelectionPanel() {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new FontSelectionSkin(this);
    }

}