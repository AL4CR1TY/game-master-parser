package net.pokeboxadvance.gamemasterparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
  private ArrayList<String> readLines = new ArrayList<>();

  public GameMasterParser(File file) {
    try {
      this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      read();
    } catch (FileNotFoundException e) {
      LOGGER.error(Message.fileNotFound(file.getPath()));
    }
  }

  public void read() {
    String line = "";
    int linesRead = 0;
    int linesSkipped = 0;
    while (line != null) {
      try {
        line = reader.readLine();
        if(line != null) {
          this.readLines.add(line);
          linesRead++;
        }
      } catch (IOException e) {
        LOGGER.error("Error reading line.");
        linesSkipped++;
      }
    }
    LOGGER.info("Finished reading. Lines read: " + linesRead + ". Lines skipped: " + linesSkipped);
    LOGGER.debug(this.readLines);
  }
}
