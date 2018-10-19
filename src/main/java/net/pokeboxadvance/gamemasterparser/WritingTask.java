package net.pokeboxadvance.gamemasterparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javafx.concurrent.Task;
import net.pokeboxadvance.Dex;
import net.pokeboxadvance.DexPokemon;
import net.pokeboxadvance.Pokedex;
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
      //write type js
      LOGGER.info("Saving type js");
      GameMasterParserApplication.updateSavingLabel("Saving type js");
      writeECMAScript("types.js", this.dex.getTypedex());

      //write pokemon js
      LOGGER.info("Saving Pok\u00e9mon js");
      GameMasterParserApplication.updateSavingLabel("Saving Pok\u00e9mon js");
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
          new File(this.destinationFolder.getAbsolutePath() + '/' + "pokemon.js"))));

      writeECMAScript(writer, this.dex.getPokedex(), false);
      writer.write("var POKEMON = {");
      int count = 1;
      for (DexPokemon pokemon : this.dex.getPokedex()) {
        writer.write("\"" + pokemon.getName().toUpperCase() + "\": "
            + Format.toVariableName(pokemon.getName())
            + (count++ != this.dex.getPokedex().size() ? "," : ""));
        writer.newLine();
      }
      writer.write("};");
      writer.flush();
      writer.close();

      //write move js
      LOGGER.info("Saving move js");
      GameMasterParserApplication.updateSavingLabel("Saving move js");
      writeECMAScript("moves.js", this.dex.getMovedex());
      LOGGER.debug(this.dex.getMovedex());

      //pokemon json
      LOGGER.info("Saving pokemon json");
      GameMasterParserApplication.updateSavingLabel("Saving pokemon json");
      writeJSON("pokemon.json", this.dex.getPokedex());
    }

    //save types
//    if (this.writeArguments.pokemon || this.writeArguments.moves) {
//      //write pokemon
//      LOGGER.info("Saving types");
//      GameMasterParserApplication.updateSavingLabel("Saving types");
//      writeDelimited("types.csv", this.dex.getTypedex());
//    }

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
  }

  private void writeECMAScript(BufferedWriter writer, ECMAScriptWritableList list,
      boolean closeWriter) throws Exception {
    updateProgress(0, 1);
    int work = 0, listSize = list.size();
    for (ECMAScriptWritable ecmaScriptWritable : list.ecmaScriptWritableList()) {
      String ecmaScriptDef = ecmaScriptWritable.toECMAScriptDef();
      LOGGER.debug(ecmaScriptDef);
      if (ecmaScriptDef != null) {
        writer.write(ecmaScriptDef);
        writer.newLine();
      }
      updateProgress(++work, listSize);
    }
    writer.flush();
    if (closeWriter) {
      writer.close();
    }
  }

  private void writeECMAScript(String fileName, ECMAScriptWritableList list) throws Exception {
    writeECMAScript(new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(new File(this.destinationFolder.getAbsolutePath() + "/" + fileName)))),
        list, true);
  }

  private void writePokemonECMAScriptCollection(BufferedWriter writer, Pokedex pokedex) {

  }

  private void writeJSON(String fileName, ECMAScriptWritableList list) throws Exception {
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
        new File(this.destinationFolder.getAbsolutePath() + '/' + fileName))));
//    writer.write("{\"pokemon\": {");
    writer.write("{");
//    writer.newLine();
    for (ECMAScriptWritable ecmaScriptWritable : list.ecmaScriptWritableList()) {
      writer.write(ecmaScriptWritable.toJSON() + ",");
//      writer.newLine();
    }
//    writer.write("}};");
    writer.write("}");
    writer.flush();
    writer.close();
  }
}
