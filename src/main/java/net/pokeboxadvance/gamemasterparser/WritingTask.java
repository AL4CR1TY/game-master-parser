package net.pokeboxadvance.gamemasterparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javafx.concurrent.Task;
import net.pokeboxadvance.Dex;
import net.pokeboxadvance.DexPokemon;
import net.pokeboxadvance.Move;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Writes the parsed data to files.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-31
 */
public class WritingTask extends Task<Void> {

  private static final Logger LOGGER = LogManager.getLogger();

  private File destinationFolder;
  private Dex dex;
  private WriteArguments writeArguments;

  public WritingTask(File destinationFolder, Dex dex, WriteArguments writeArguments) {
    this.destinationFolder = destinationFolder;
    this.dex = dex;
    this.writeArguments = writeArguments;
  }

  @Override
  protected Void call() throws Exception {
    char successSymbol = '\u2714';
    //save js
    if (this.writeArguments.dev) {
      //write pokemon js
      LOGGER.info("Saving Pok\u00e9mon js");
      GameMasterParserApplication.updateSavingLabel("Saving Pok\u00e9mon js");
      writeECMAScript("pokemon.js", this.dex.getPokedex());

      //write move js
      LOGGER.info("Saving move js");
      GameMasterParserApplication.updateSavingLabel("Saving move js");
      writeECMAScript("moves.js", this.dex.getMovedex());
    }

    //save pokemon
    if (this.writeArguments.pokemon) {
      //write pokemon
      LOGGER.info("Saving Pok\u00e9mon");
      GameMasterParserApplication.updateSavingLabel("Saving Pok\u00e9mon");
      writeDelimited("pokemon.csv", this.dex.getPokedex());
    }

    //save moves
    if (this.writeArguments.moves) {
      //write moves
      LOGGER.info("Saving moves");
      GameMasterParserApplication.updateSavingLabel("Saving moves");
      writeDelimited("moves.csv", this.dex.getMovedex());
    }
    return null;
  }

  private void writeDelimited(String fileName, DelimitedWritableList list) throws Exception {
    writeDelimited(fileName, list, ';');
  }

  private void writeDelimited(String fileName, DelimitedWritableList list, char delimiter)
      throws Exception {
    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
          new File(this.destinationFolder.getAbsolutePath() + '/' + fileName))));
      updateProgress(0, 1);
      int work = 0, listSize = list.size();
      for (DelimitedWritable delimitedWritable : list.delimitedWritableList()) {
        writer.write(delimitedWritable.toDelimited(delimiter));
        writer.newLine();
        updateProgress(++work, listSize);
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      LOGGER.debug(e.getMessage());
      throw new Exception(e);
    }
  }

  private void writeECMAScript(String fileName, ECMAScriptWritableList list) throws Exception {
    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
          new File(this.destinationFolder.getAbsolutePath() + '/' + fileName))));
      updateProgress(0, 1);
      int work = 0, listSize = list.size();
      for (ECMAScriptWritable ecmaScriptWritable : list.ecmaScriptWritableList()) {
        writer.write(ecmaScriptWritable.toECMAScriptDef());
        writer.newLine();
        updateProgress(++work, listSize);
      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      throw new Exception(e);
    }
  }
}
