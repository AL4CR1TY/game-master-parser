package net.pokeboxadvance.gamemasterparser;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import net.pokeboxadvance.Dex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Game master parser.
 *
 * Reads a file on creation. If no file name is specified, the default path will be searched. If no
 * file exists, the parser will throw a {@code FileNotFoundException}.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-28
 */
public class GameMasterParser {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final String DEFAULT_PATHNAME = "game_master_decompiled.txt";

  private BufferedReader reader;
  private BufferedReader lineCounter;
  private ArrayList<String> readLines = new ArrayList<>();

  private Dex dex = new Dex();

  private boolean doneReading = false, doneParsing = false;

  private ReadingTask readingTask;
  private CleaningTask cleaningTask;
  private ParsingTask parsingTask;
  private WritingTask csvWritingTask;

  public GameMasterParser(File file) {
    this.readingTask = new ReadingTask(file, this.readLines);
    this.cleaningTask = new CleaningTask(this.readLines);
    this.parsingTask = new ParsingTask(this.readLines, dex);
//    this.readingTask.setOnSucceeded(event -> {
//      this.readLines = this.parsingTask.getReadLines();
//      this.parsingTask.run();
//    });
    this.readingTask.setOnFailed(event -> this.readingTask.getException().printStackTrace());
    this.cleaningTask.setOnFailed(event -> this.cleaningTask.getException().printStackTrace());
    this.parsingTask.setOnFailed(event -> this.parsingTask.getException().printStackTrace());
  }

  public ReadingTask getReadingTask() {
    return this.readingTask;
  }

  public CleaningTask getCleaningTask() {
    return this.cleaningTask;
  }

  public ParsingTask getParsingTask() {
    return this.parsingTask;
  }

  public Dex getDex() {
    return dex;
  }
}
