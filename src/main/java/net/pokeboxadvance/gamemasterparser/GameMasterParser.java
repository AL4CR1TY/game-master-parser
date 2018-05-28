package net.pokeboxadvance.gamemasterparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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

  private File file;
  private BufferedReader reader;

//  public GameMasterParser() {
//    this.reader = openFile(DEFAULT_PATHNAME);
//  }
//
//  public GameMasterParser(String filePath) {
//    this.reader = openFile(filePath);
//  }

  public GameMasterParser(File file) {
    try {
      this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    } catch (FileNotFoundException e) {
      LOGGER.error(Message.fileNotFound(file.getPath()));
    }
  }

  /**
   * Returns a {@link BufferedReader} for a file with the specified path.
   *
   * If no file is found, a new one is created, a warning message is logged and the program exited.
   *
   * @param path the file path
   * @return the reader
   */
//  private BufferedReader openFile(String path) {
//    try {
//      File file = new File(path);
//      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//      LOGGER.info("Read file " + file);
//      return reader;
//    } catch (FileNotFoundException e) {
//      LOGGER.warn("File with path " + path
//          + " could not be found. Please paste the decompiled game master into the new file.");
//      try {
//        new FileOutputStream(new File(path));
//      } catch (FileNotFoundException e2) {
//        LOGGER.error("Could not create file.");
//      }
//    }
//    System.exit(1);
//    return null;
//  }
}
