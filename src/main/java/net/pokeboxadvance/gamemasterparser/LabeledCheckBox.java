package net.pokeboxadvance.gamemasterparser;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Checkbox with label.
 *
 * TODO
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-31
 */
public class LabeledCheckBox extends GridPane {

  private static final Logger LOGGER = LogManager.getLogger();

  private Label label;
  private CheckBox checkBox = new CheckBox();

  public LabeledCheckBox(String labelText) {
    this.label = new Label(labelText);
    this.setHgap(5);
    GridPane.setConstraints(label, 0, 0);
    GridPane.setConstraints(checkBox, 1, 0);
    this.getChildren().addAll(this.label, this.checkBox);
  }

  public LabeledCheckBox(String labelText, boolean selected) {
    this.label = new Label(labelText);
    this.checkBox.setSelected(selected);
    this.setHgap(5);
    GridPane.setConstraints(label, 0, 0);
    GridPane.setConstraints(checkBox, 1, 0);
    this.getChildren().addAll(this.label, this.checkBox);
  }

  public boolean isSelected() {
    return this.checkBox.isSelected();
  }

//  public void setSelected(boolean state) {
//    this.checkBox.setSelected(state);
//  }
}
