package net.pokeboxadvance.gamemasterparser;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

/**
 * Progress bar with label.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-30
 */
public class LabeledProgressBar extends GridPane {

  private Label statusLabel/*, percentageLabel*/;
  private ProgressBar progressBar;

  public LabeledProgressBar() {
    this.statusLabel = new Label();
//    this.percentageLabel = new Label();
    this.progressBar = new ProgressBar();
    GridPane.setConstraints(this.statusLabel, 0, 0);
    GridPane.setConstraints(this.progressBar, 0, 1);
    this.getChildren().addAll(this.statusLabel,/* this.percentageLabel,*/ this.progressBar);
  }

  /**
   * TODO
   * @param status the status message
   */
  public void setStatus(String status) {
    this.statusLabel.setText(status);
  }

  public DoubleProperty progressProperty() {
    return this.progressBar.progressProperty();
  }
}
