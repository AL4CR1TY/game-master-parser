package net.pokeboxadvance;

import java.util.ArrayList;
import java.util.Arrays;
import net.pokeboxadvance.gamemasterparser.DelimitedWritable;
import net.pokeboxadvance.gamemasterparser.DelimitedWritableList;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritable;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritableList;

/**
 * Type dex.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-06-15
 */
public class Typedex implements DelimitedWritableList, ECMAScriptWritableList {

  private ArrayList<Type> typedex = new ArrayList<>();

  public Typedex() {
    this.typedex.addAll(Arrays.asList(Type.TYPES));
  }

  @Override
  public ArrayList<DelimitedWritable> delimitedWritableList() {
    return null;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public ArrayList<ECMAScriptWritable> ecmaScriptWritableList() {
    return new ArrayList<ECMAScriptWritable>(this.typedex);
  }
}
