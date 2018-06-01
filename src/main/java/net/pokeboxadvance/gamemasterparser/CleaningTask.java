package net.pokeboxadvance.gamemasterparser;

import java.util.ArrayList;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cleaning task.
 *
 * Cleans the lines in place.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-30
 */
public class CleaningTask extends Task<Void> {

  private static final Logger LOGGER = LogManager.getLogger();

  private ArrayList<String> lines;

  public CleaningTask(ArrayList<String> lines) {
    this.lines = lines;
  }

  @Override
  protected Void call() throws Exception {
    updateProgress(0, 1);
    int work = 0, listSize = lines.size();
    LOGGER.info("Cleaning lines");
    for(String line : this.lines) {
      line = line.replaceAll("\\s+", "");
      updateProgress(++work, listSize);
    }
    updateProgress(1, 1);
    LOGGER.info("Cleaned lines");
    return null;
  }
}
