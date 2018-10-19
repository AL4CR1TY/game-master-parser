package net.pokeboxadvance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dex.
 *
 * Wraps Pokédex and Movedex into one single instance.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-30
 */
public class Dex {

  private static final Logger LOGGER = LogManager.getLogger();

  private Typedex typedex = new Typedex();
  private Pokedex pokedex = new Pokedex();
  private Movedex movedex = new Movedex();

  /**
   * TODO
   *
   * @param pokemon the pokemon
   */
  public void addPokemon(DexPokemon pokemon) {
    this.pokedex.add(pokemon);
  }

  /**
   * TODO
   *
   * @param move the move
   */
  public void addMove(Move move) {
    this.movedex.add(move);
  }

  public Pokedex getPokedex() {
    return this.pokedex;
  }

  public Movedex getMovedex() {
    return this.movedex;
  }

  public Typedex getTypedex() {
    return this.typedex;
  }
}
