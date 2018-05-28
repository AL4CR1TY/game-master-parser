package net.pokeboxadvance.gamemasterparser;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class for game master parser.
 *
 * This class' main method is run when starting a jar.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-28
 */
public class GameMasterParserMain extends Application {

  private static final Logger LOGGER = LogManager.getLogger();

  private static GameMasterParser gameMasterParser;

  @Override
  public void start(Stage stage) {
    AnchorPane root = new AnchorPane();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open decompiled GAME_MASTER");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("txt", "*.txt"));
    Button chooseFileButton = new Button("Choose file");
    chooseFileButton.setOnAction(event -> {
      File file = fileChooser.showOpenDialog(stage);
      if(file != null) {
        LOGGER.info("Chose file " + file);
        gameMasterParser = new GameMasterParser(file);
      } else {
        LOGGER.debug("File selection cancelled.");
      }
    });

    root.getChildren().add(chooseFileButton);
    stage.setScene(new Scene(root));
    stage.setTitle("Pokébox Advance GAME_MASTER parser");
    stage.setHeight(480);
    stage.setWidth(853);
    stage.setResizable(true);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
