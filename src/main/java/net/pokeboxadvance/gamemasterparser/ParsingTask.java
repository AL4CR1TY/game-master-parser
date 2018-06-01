package net.pokeboxadvance.gamemasterparser;

import java.util.ArrayList;
import javafx.concurrent.Task;
import net.pokeboxadvance.Dex;
import net.pokeboxadvance.DexPokemon;
import net.pokeboxadvance.Move;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Parsing task.
 *
 * Parses date into the passed {@link Dex}.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-30
 */
public class ParsingTask extends Task<Void> {

  private static final Logger LOGGER = LogManager.getLogger();

  private ArrayList<String> lines;
  private Dex dex;

  public ParsingTask(ArrayList<String> lines, Dex dex) {
    this.lines = lines;
    this.dex = dex;
  }

  @Override
  public Void call() throws Exception {
    updateProgress(0, 1);
    int moveCount = 0, pokemonCount = 0;
    int moveLinesRead = 0, pokemonLinesRead = 0, lineCount = this.lines.size();
    LOGGER.info("Parsing moves");
    GameMasterParserApplication.updateParsingLabel("Parsing moves");
    //parse moves
    for (String line : this.lines) {
      if (line.contains("MOVE") && !line.contains("MOVEMENT") && !line.contains("REROLL")) {
//        LOGGER.debug(line);
        this.dex.addMove(parseMoveAt(this.lines.indexOf(line) - 1));
        moveCount++;
      }
      updateProgress(++moveLinesRead, lineCount * 2);
    }

    LOGGER.info("Parsing Pok\u00e9mon");
    GameMasterParserApplication.updateParsingLabel("Parsing Pok\u00e9mon");
    //parse pokemon
    for (String line : this.lines) {
      if (line.contains("POKEMON") && !line.contains("SPAWN") && !line.contains("RARITY") && !line
          .contains("TYPE") && !line.contains("FORMS")&& !line.contains("ITEM")) {
//        LOGGER.debug(line);
        this.dex.addPokemon(parsePokemonAt(this.lines.indexOf(line) - 1));
        pokemonCount++;
      }
      updateProgress(moveLinesRead + ++pokemonLinesRead, lineCount * 2);
    }

    updateProgress(1, 1);
    LOGGER.info(
        "Finished parsing. Found " + moveCount + " moves and " + pokemonCount + " Pok\u00e9mon.");
    return null;
  }

//  public ArrayList<String> getReadLines() {
//    return this.readLines;
//  }

  /**
   * Extracts a move from within an {@code item_templates}. Start index must be set at the opening
   * bracket of the {@code item_templates}.
   *
   * @param index the start index
   * @return the parsed move
   */
  public Move parseMoveAt(int index) {
    Move move = new Move() {};
    int level = 0;
    String line;
    String[] split;
    do {
      line = lines.get(index);
      if (line.contains("{")) {
        level++;
      }
      if (line.contains("}")) {
        level--;
      }
      if (line.contains("MOVE")) {
        split = line.split("[_]");
        try {
          move.setId(Integer.parseInt(split[0].replace("V", "")));
        } catch (NumberFormatException e) {
          LOGGER.error("Error parsing move id for " + split[0].replace("V", ""));
        }
        move.setName(line.substring(StringUtils.ordinalIndexOf(line, "_", 2)));
      }

      index++;
    } while (level > 0);
    return move;
  }

  /**
   * Extracts a Pokemon from within an {@code item_templates}. Start index must be set at the opening
   * bracket of the {@code item_templates}.
   *
   * @param index the start index
   * @return the parsed move
   */
  public DexPokemon parsePokemonAt(int index) {
    DexPokemon pokemon = new DexPokemon();
    int level = 0;
    String line;
    String[] split;
    do {
      line = lines.get(index);
      if (line.contains("{")) {
        level++;
      }
      if (line.contains("}")) {
        level--;
      }
      if (line.contains("MOVE")) {
        split = line.split("[_]");
        try {
          pokemon.setNumber(Integer.parseInt(split[0].replace("V", "")));
        } catch (NumberFormatException e) {
          LOGGER.error("Error parsing move id for " + split[0].replace("V", ""));
        }
        pokemon.setName(line.substring(StringUtils.ordinalIndexOf(line, "_", 2)));
      }

      index++;
    } while (level > 0);
    return pokemon;
  }
}
