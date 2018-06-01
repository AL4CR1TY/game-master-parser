package net.pokeboxadvance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.pokeboxadvance.gamemasterparser.DelimitedWritable;
import net.pokeboxadvance.gamemasterparser.DelimitedWritableList;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritable;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Pokédex.
 *
 * This class represents a Pokédex and is more or less a list of {@link DexPokemon}. It also
 * contains several methods for managing the dex.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-29
 */
public class Pokedex implements Iterable<DexPokemon>, DelimitedWritableList,
    ECMAScriptWritableList {

  private static final Logger LOGGER = LogManager.getLogger();

  private ArrayList<DexPokemon> pokedex = new ArrayList<>();
  private Comparator<DexPokemon>
      numberComparator = (DexPokemon pokemon1, DexPokemon pokemon2)
      -> Integer.compare(pokemon1.getNumber(), pokemon2.getNumber()),
      nameComparator = (DexPokemon pokemon1, DexPokemon pokemon2)
          -> pokemon1.getName().toLowerCase().compareTo(pokemon2.getName().toLowerCase());

  /**
   * Adds a Pokemon to the Pokedex.
   *
   * @param pokemon the Pokemon to be added
   */
  public void add(DexPokemon pokemon) {
    this.pokedex.add(pokemon);
  }

  /**
   * Sorts the Pokedex by Pokemon number.
   */
  public void sortByNumber() {
    this.pokedex.sort(numberComparator);
  }

  /**
   * Sorts the Pokedex by Pokemon number.
   */
  public void sortByName() {
    this.pokedex.sort(nameComparator);
  }

  /**
   * Returns a Pokemon with a number matching the provided one. If no Pokemon with that number
   * exists, the first one is returned. If the Pokedex is otherwise empty, null is returned.
   *
   * @param number a Pokemon's number
   * @return the Pokemon with a number matching the one provided
   */
  public DexPokemon getByNumber(int number) {
    if (this.pokedex.size() == 0) {
      LOGGER.warn("Pokedex is empty.");
      return null;
    }
    for (DexPokemon pokemon : this.pokedex) {
      if (pokemon.getNumber() == number) {
        return pokemon;
      }
    }
    return this.pokedex.get(0);
  }

  @Override
  public String toString() {
    return this.pokedex.toString();
  }

  @Override
  public Iterator<DexPokemon> iterator() {
    return this.pokedex.iterator();
  }

  @Override
  public ArrayList<DelimitedWritable> delimitedWritableList() {
    return new ArrayList<DelimitedWritable>(this.pokedex);
  }

  @Override
  public ArrayList<ECMAScriptWritable> ecmaScriptWritableList() {
    return new ArrayList<ECMAScriptWritable>(this.pokedex);
  }

  @Override
  public int size() {
    return this.pokedex.size();
  }
}
