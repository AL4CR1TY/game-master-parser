package net.pokeboxadvance.gamemasterparser;

import java.util.ArrayList;

/**
 * Interface to ensure a return method of type {@code ArrayList<ECMAScriptWritable>} for list like
 * classes.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-06-01
 */
public interface ECMAScriptWritableList {

  ArrayList<ECMAScriptWritable> ecmaScriptWritableList();
  int size();
}
