package net.pokeboxadvance.gamemasterparser;

/**
 * Represents a settings group for writing.
 *
 * Sets the settings for writing parsed data to files.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-31
 */
public class WriteArguments {

  public boolean pokemon, moves, dev;

  public WriteArguments(boolean pokemon, boolean moves, boolean dev) {
    this.pokemon = pokemon;
    this.moves = moves;
    this.dev = dev;
  }
}
