package net.pokeboxadvance.gamemasterparser;

import java.util.ArrayList;
import javafx.concurrent.Task;
import net.pokeboxadvance.ChargeMove;
import net.pokeboxadvance.Dex;
import net.pokeboxadvance.DexPokemon;
import net.pokeboxadvance.Move;
import net.pokeboxadvance.QuickMove;
import net.pokeboxadvance.Type;
import org.apache.commons.text.WordUtils;
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

  private static final String
      MOVE_ID_NAME = "template_id",
      MOVE_NAME = "movement_id",
      MOVE_TYPE = "pokemon_type",
      MOVE_POWER = "power",
      MOVE_ACCURACY = "accuracy_chance",
      MOVE_HP_LOSS_SCALAR = "stamina_loss_scalar",
      MOVE_TRAINER_LEVEL_MIN = "trainer_level_min",
      MOVE_TRAINER_LEVEL_MAX = "trainer_level_max",
      MOVE_DURATION = "duration_ms",
      MOVE_WINDOW_START = "damage_window_start_ms",
      MOVE_WINDOW_END = "damage_window_end_ms",
      MOVE_ENERGY = "energy_delta",
      POKEMON_NUMBER_NAME = "template_id",
      POKEMON_TYPE = "type",
      POKEMON_TYPE_SECONDARY = "type_2",
      POKEMON_CAPTURE_RATE = "base_capture_rate",
      POKEMON_FLEE_RATE = "base_flee_rate",
      POKEMON_BASE_STAMINA = "base_stamina",
      POKEMON_BASE_ATTACK = "base_attack",
      POKEMON_BASE_DEFENSE = "base_defense",
      POKEMON_QUICK_MOVE = "quick_moves",
      POKEMON_CHARGE_MOVE = "cinematic_move",
      POKEMON_POKEDEX_HEIGHT = "pokedex_height_m",
      POKEMON_POKEDEX_WEIGHT = "pokedex_weight_kg",
      POKEMON_PARENT_POKEMON = "parent_pokemon_id",
      POKEMON_HEIGHT_DEVIATION = "pokemon_height_std_dev",
      POKEMON_WEIGHT_DEVIATION = "pokemon_weight_std_dev",
      POKEMON_FAMILY = "family_id",
      POKEMON_BUDDY_DISTANCE = "km_buddy_distance",
      POKEMON_EVOLUTION = "evolution",
      POKEMON_EVOLUTION_ITEM_REQUIREMENT = "evolution_item_requirement",
      POKEMON_EVOLUTION_CANDY_COST = "candy_cost";

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
          .contains("TYPE") && !line.contains("FORMS") && !line.contains("ITEM") && !line
          .contains("SETTINGS") && !line.contains("STORAGE") && !line.contains("NORMAL")) {
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
    int level = 0;
    String line;
    String[] split;
    Move move;
    if (lines.get(index + 1).contains("FAST")) {
      move = new QuickMove();
    } else {
      move = new ChargeMove();
    }
    do {
      line = lines.get(index);
      split = line.split(":");
      if (line.contains("{")) {
        level++;
      }
      if (line.contains("}")) {
        level--;
      }
      switch (split[0]) {
        case MOVE_ID_NAME:
          String[] idName = split[1].split("_MOVE_");
          if (idName.length < 2) {
            LOGGER.error(Message.exceptionParsing("id and name", split[1], move));
            break;
          }
          try {
            move.setId(Integer.parseInt(idName[0].replace("V", "")));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("id", idName[0], move));
          }
          move.setName(WordUtils.capitalize(idName[1].replace('_', ' ').toLowerCase()));
          break;
        case MOVE_TYPE:
          move.setType(Type.get(split[1]));
          break;
        case MOVE_POWER:
          try {
            move.setPower(Integer.parseInt(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("power", split[1], move));
          }
          break;
        case MOVE_ACCURACY:

          break;
        case MOVE_HP_LOSS_SCALAR:

          break;
        case MOVE_TRAINER_LEVEL_MIN:

          break;
        case MOVE_TRAINER_LEVEL_MAX:

          break;
        case MOVE_DURATION:
          try {
            move.setDuration(Integer.parseInt(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("duration", split[1], move));
          }
          break;
        case MOVE_WINDOW_START:

          break;
        case MOVE_WINDOW_END:

          break;
        case MOVE_ENERGY:
          try {
            move.setEnergy(Math.abs(Integer.parseInt(split[1])));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("energy", split[1], move));
          }
          break;
      }
      index++;
    } while (level > 0);
    LOGGER.debug(
        "Read " + (move.getClass().getSimpleName().equals("FastMove") ? "fast move" : "charge move")
            + " " + move);
    return move;
  }

  /**
   * Extracts a Pokemon from within an {@code item_templates}. Start index must be set at the
   * opening bracket of the {@code item_templates}.
   *
   * @param index the start index
   * @return the parsed move
   */
  public DexPokemon parsePokemonAt(int index) {
    int level = 0;
    String line;
    String[] split;
    DexPokemon pokemon = new DexPokemon();
    do {
      line = lines.get(index);
      split = line.split(":");
      if (line.contains("{")) {
        level++;
      }
      if (line.contains("}")) {
        level--;
      }
      switch (split[0]) {
        case POKEMON_NUMBER_NAME:
          String[] numberName = split[1].replace("_POKEMON_", " ").split(" ");
          if (numberName.length < 2) {
            LOGGER.error(Message.exceptionParsing("number and name", split[1], pokemon));
            break;
          }
          try {
            pokemon.setNumber(Integer.parseInt(numberName[0].replace("V", "")));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("number", numberName[0], pokemon));
          }
          if(numberName[1].contains("_ALOLA")) {
            numberName[1] = "ALOLAN_" + numberName[1].replace("_ALOLA", "");
          }
          pokemon.setName(WordUtils.capitalize(numberName[1].replace('_', ' ').toLowerCase()));
          break;
        case POKEMON_TYPE:
          Type type = Type.get(split[1]);
          pokemon.addType(type);
          pokemon.setPrimaryType(type);
          break;
        case POKEMON_TYPE_SECONDARY:
          Type typeSecondary = Type.get(split[1]);
          pokemon.addType(typeSecondary);
          pokemon.setSecondaryType(typeSecondary);
          break;
        case POKEMON_CAPTURE_RATE:
          try {
            pokemon.setBaseCatchRate(Double.parseDouble(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("base capture rate", split[1], pokemon));
          }
          break;
        case POKEMON_FLEE_RATE:
          try {
            pokemon.setBaseFleeRate(Double.parseDouble(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("base flee rate", split[1], pokemon));
          }
          break;
        case POKEMON_BASE_STAMINA:
          try {
            pokemon.setBaseStamina(Integer.parseInt(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("base stamina", split[1], pokemon));
          }
          break;
        case POKEMON_BASE_ATTACK:
          try {
            pokemon.setBaseAttack(Integer.parseInt(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("base attack", split[1], pokemon));
          }
          break;
        case POKEMON_BASE_DEFENSE:
          try {
            pokemon.setBaseDefense(Integer.parseInt(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("base defense", split[1], pokemon));
          }
          break;
        case POKEMON_QUICK_MOVE:

          break;
        case POKEMON_CHARGE_MOVE:

          break;
        case POKEMON_POKEDEX_HEIGHT:
          try {
            pokemon.setPokedexHeight(Double.parseDouble(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("Pokedex height", split[1], pokemon));
          }
          break;
        case POKEMON_POKEDEX_WEIGHT:
          try {
            pokemon.setPokedexWeight(Double.parseDouble(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("Pokedex weight", split[1], pokemon));
          }
          break;
        case POKEMON_PARENT_POKEMON:

          break;
        case POKEMON_HEIGHT_DEVIATION:

          break;
        case POKEMON_WEIGHT_DEVIATION:

          break;
        case POKEMON_FAMILY:

          break;
        case POKEMON_BUDDY_DISTANCE:
          try {
            pokemon.setBuddyDistance(Integer.parseInt(split[1]));
          } catch (NumberFormatException e) {
            LOGGER.error(Message.exceptionParsing("buddy distance", split[1], pokemon));
          }
          break;
        case POKEMON_EVOLUTION:

          break;
        case POKEMON_EVOLUTION_ITEM_REQUIREMENT:

          break;
        case POKEMON_EVOLUTION_CANDY_COST:

          break;
      }
      index++;
    } while (level > 0);
    LOGGER.debug("Read Pokemon " + pokemon);
    return pokemon;
  }
}
