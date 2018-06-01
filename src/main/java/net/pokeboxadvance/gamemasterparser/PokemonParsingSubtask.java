package net.pokeboxadvance.gamemasterparser;

import java.util.ArrayList;
import javafx.concurrent.Task;
import net.pokeboxadvance.Pokedex;

/**
 * Pokemon parsing subtask.
 *
 * TODO
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-30
 */
public class PokemonParsingSubtask extends Task<Pokedex> {

  private ArrayList<String> readLines;

  public PokemonParsingSubtask(ArrayList<String> readLines) {
    this.readLines = readLines;
  }

  @Override
  protected Pokedex call() {
    Pokedex pokedex = new Pokedex();
    for(String line : this.readLines) {

    }
    return pokedex;
  }
}
