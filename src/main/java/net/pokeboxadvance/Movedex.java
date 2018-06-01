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
 * Movedex.
 *
 * This class  is  a list of {@link Move}s. It also contains several methods for managing the dex.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-30
 */
public class Movedex implements Iterable<Move>, DelimitedWritableList, ECMAScriptWritableList {

  private static final Logger LOGGER = LogManager.getLogger();

  private ArrayList<Move> movedex = new ArrayList();
  private Comparator<Move>
      typeCoparator = (Move move1, Move move2)
      -> Integer.compare(move1.getType().getId(), move2.getType().getId()),
      nameComparator = (Move move1, Move move2)
          -> move1.getName().toLowerCase().compareTo(move2.getName().toLowerCase());

  public void add(Move move) {
    this.movedex.add(move);
  }

  @Override
  public Iterator<Move> iterator() {
    return this.movedex.iterator();
  }

  @Override
  public ArrayList<DelimitedWritable> delimitedWritableList() {
    return new ArrayList<DelimitedWritable>(this.movedex);
  }

  @Override
  public ArrayList<ECMAScriptWritable> ecmaScriptWritableList() {
    return new ArrayList<ECMAScriptWritable>(this.movedex);
  }

  @Override
  public int size() {
    return this.movedex.size();
  }
}
