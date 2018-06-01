package net.pokeboxadvance.gamemasterparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reading task.
 *
 * TODO
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-30
 */
public class ReadingTask extends Task<ArrayList<String>> {

  private static final Logger LOGGER = LogManager.getLogger();

  private File file;
  private ArrayList<String> readLines;

  public ReadingTask(File file, ArrayList<String> readLines) {
    this.file = file;
    this.readLines = readLines;
  }

  @Override
  protected ArrayList<String> call() throws Exception {
    try {
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(new FileInputStream(this.file)));
      BufferedReader lineCounter = new BufferedReader(
          new InputStreamReader(new FileInputStream(this.file)));
      updateProgress(0, 1);
      int lineCount = -1;
      int linesRead = 0;
      int linesSkipped = 0;
      String line = "";
      GameMasterParserApplication.updateReadingLabel("Counting lines");
      while (line != null) {
        try {
          line = lineCounter.readLine();
          lineCount++;
        } catch (IOException e) {
          LOGGER.error("Error reading line while counting lines.");
        }
      }
      try {
        lineCounter.close();
      } catch (IOException e) {
        LOGGER.debug("Could not close line counter: " + e.getMessage());
      }
      LOGGER.info("Found " + lineCount + " lines.");
      if (lineCount == 0) {
        LOGGER.info("File appears to be empty.");
        updateProgress(1,1);
      } else {
        GameMasterParserApplication.updateReadingLabel("Reading lines");
        line = "";
        while (line != null) {
          try {
            line = reader.readLine();
            if (line != null) {
              readLines.add(line);
              linesRead++;
            }
            updateProgress(linesRead, lineCount);
          } catch (IOException e) {
            LOGGER.error("Error reading line.");
            linesSkipped++;
          }
        }
        LOGGER
            .info(
                "Finished reading. Lines read: " + linesRead + ". Lines skipped: " + linesSkipped);
//    LOGGER.debug(this.readLines);
      }
      try {
        reader.close();
      } catch (IOException e) {
        LOGGER.debug("Could not close reader: " + e.getMessage());
      }
    } catch (FileNotFoundException e) {
      LOGGER.error("File " + this.file + " not found.");
    }
    updateProgress(1,1);
    return readLines;
  }

  public ArrayList<String> getReadLines() {
    return this.readLines;
  }
}
