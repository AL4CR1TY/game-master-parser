package net.pokeboxadvance.gamemasterparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
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
public class GameMasterParserApplication extends Application {

  private static final Logger LOGGER = LogManager.getLogger();

  private static GameMasterParser gameMasterParser;
  private static LabeledProgressBar readProgressBar;
  private static LabeledProgressBar cleanProgressBar;
  private static LabeledProgressBar parseProgressBar;
  private static LabeledProgressBar saveProgressBar;

  @Override
  public void start(Stage stage) {
    stage.setOnCloseRequest(closeEvent -> Platform.exit());
//    AnchorPane root = new AnchorPane();
    GridPane root = new GridPane();
    root.setVgap(8);
    //set up file chooser
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open decompiled GAME_MASTER");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("txt", "*.txt"));
    //set up file saver
    DirectoryChooser fileSaver = new DirectoryChooser();
    fileSaver.setTitle("Choose folder to save files to");
    fileSaver.setInitialDirectory(new File(System.getProperty("user.dir")));
    //set up buttons
    Button chooseFileButton = new Button("Choose file");
    Button saveButton = new Button("Save to folder");
    //set up checkboxes
    LabeledCheckBox pokemonCheckBox = new LabeledCheckBox("Save Pok\u00e9mon", true);
    LabeledCheckBox moveCheckBox = new LabeledCheckBox("Save moves", true);
    //set grid
    GridPane.setConstraints(chooseFileButton, 0, 0);
    GridPane.setConstraints(pokemonCheckBox, 0, 4);
    GridPane.setConstraints(moveCheckBox, 1, 4);
    GridPane.setConstraints(saveButton, 0, 5);
    //set button actions
    saveButton.setOnAction(saveEvent -> {
      root.getChildren().remove(saveProgressBar);
      File saveDestinationFolder = fileSaver.showDialog(stage);
      if (saveDestinationFolder != null) {
        WritingTask writingTask = new WritingTask(saveDestinationFolder, gameMasterParser.getDex(),
            new WriteArguments(pokemonCheckBox.isSelected(), moveCheckBox.isSelected(),
                saveDestinationFolder.getName().equals("dev")));
        writingTask.setOnSucceeded(writingSuccessEvent -> updateSavingLabel("Saving \u2714"));
        writingTask.setOnFailed(parsingFailureEvent -> updateSavingLabel("Saving \u2716"));
        saveProgressBar = new LabeledProgressBar();
        saveProgressBar.setStatus("Saving");
        saveProgressBar.progressProperty().bind(writingTask.progressProperty());
        GridPane.setConstraints(saveProgressBar, 0, 6);
        root.getChildren().add(saveProgressBar);
        new Thread(() -> writingTask.run()).start();
      } else {
        LOGGER.debug("File save cancelled.");
      }
    });
    chooseFileButton.setOnAction(chooseFileButtonEvent -> {
      File file = fileChooser.showOpenDialog(stage);
      if (file != null) {
        LOGGER.info("Chose file " + file);
        //remove old progress bars
        root.getChildren()
            .removeAll(readProgressBar, cleanProgressBar, parseProgressBar, pokemonCheckBox,
                moveCheckBox, saveButton, saveProgressBar);
        //create new progress bars, set their status
        readProgressBar = new LabeledProgressBar();
        readProgressBar.setStatus("Reading");
        cleanProgressBar = new LabeledProgressBar();
        cleanProgressBar.setStatus("Cleaning");
        parseProgressBar = new LabeledProgressBar();
        parseProgressBar.setStatus("Parsing");
        //set grid positioning
        GridPane.setConstraints(readProgressBar, 0, 1);
        GridPane.setConstraints(cleanProgressBar, 0, 2);
        GridPane.setConstraints(parseProgressBar, 0, 3);
        //create new parser
        gameMasterParser = new GameMasterParser(file);
        //bind progress properties
        readProgressBar.progressProperty()
            .bind(gameMasterParser.getReadingTask().progressProperty());
        cleanProgressBar.progressProperty()
            .bind(gameMasterParser.getCleaningTask().progressProperty());
        parseProgressBar.progressProperty()
            .bind(gameMasterParser.getParsingTask().progressProperty());
        //add progress bars to gui
        root.getChildren().addAll(readProgressBar, cleanProgressBar, parseProgressBar);
        //create task threads
        Thread readingThread = new Thread(() -> gameMasterParser.getReadingTask().run());
        readingThread.setName("Reading Thread");
        Thread cleaningThread = new Thread(() -> gameMasterParser.getCleaningTask().run());
        readingThread.setName("Cleaning Thread");
        Thread parsingThread = new Thread(() -> gameMasterParser.getParsingTask().run());
        parsingThread.setName("Parsing Thread");
        /*
         * set read success -> clean succeed -> parse succeed -> enable save
         *        \ failure ->     \ failure ->     \ failure ->
         * */
        //reading task events
        gameMasterParser.getReadingTask().setOnSucceeded(readingSuccessEvent -> {
          GameMasterParserApplication.updateReadingLabel("Reading \u2714");
          cleaningThread.start();
        });
        gameMasterParser.getReadingTask().setOnFailed(readingFailureEvent -> {
          GameMasterParserApplication.updateReadingLabel("Reading \u2716");
          GameMasterParserApplication.updateCleaningLabel("Cleaning \u2716");
          GameMasterParserApplication.updateParsingLabel("Parsing \u2716");
        });
        //cleaning task events
        gameMasterParser.getCleaningTask().setOnSucceeded(cleaningSuccessEvent -> {
          GameMasterParserApplication.updateCleaningLabel("Cleaning \u2714");
          parsingThread.start();
        });
        gameMasterParser.getCleaningTask().setOnFailed(readingFailureEvent -> {
          GameMasterParserApplication.updateCleaningLabel("Cleaning \u2716");
          GameMasterParserApplication.updateParsingLabel("Parsing \u2716");
        });
        //parsing task events
        gameMasterParser.getParsingTask().setOnSucceeded(parsingSuccessEvent -> {
          GameMasterParserApplication.updateParsingLabel("Parsing \u2714");
          root.getChildren().addAll(saveButton, pokemonCheckBox, moveCheckBox);
        });
        gameMasterParser.getParsingTask().setOnFailed(readingFailureEvent
            -> GameMasterParserApplication.updateParsingLabel("Parsing \u2716"));
        //run reading task
        readingThread.start();
      } else {
        LOGGER.debug("File selection cancelled.");
      }
    });
    root.getChildren().add(chooseFileButton);
    stage.setScene(new Scene(root));
    stage.setTitle("Pok\u00e9box Advance GAME_MASTER parser");
    stage.setHeight(500);
    stage.setWidth(240);
    stage.setResizable(true);
    stage.show();
  }

  /**
   * TODO
   *
   * @param task what is being done as a string
   */
  public static void updateReadingLabel(String task) {
    Platform.runLater(() -> readProgressBar.setStatus(task));
  }

  /**
   * TODO
   *
   * @param task what is being done as a string
   */
  public static void updateCleaningLabel(String task) {
    Platform.runLater(() -> cleanProgressBar.setStatus(task));
  }

  /**
   * TODO
   *
   * @param task what is being done as a string
   */
  public static void updateParsingLabel(String task) {
    Platform.runLater(() -> parseProgressBar.setStatus(task));
  }

  /**
   * TODO
   *
   * @param task what is being done as a string
   */
  public static void updateSavingLabel(String task) {
    Platform.runLater(() -> saveProgressBar.setStatus(task));
  }

  public static void main(String[] args) {
    launch();
  }
}
