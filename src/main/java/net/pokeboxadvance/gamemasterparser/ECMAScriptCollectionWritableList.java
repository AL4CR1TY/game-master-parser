package net.pokeboxadvance.gamemasterparser;

import java.util.ArrayList;

/**
 * Interface to ensure a return method of type {@code ArrayList<ECMAScriptCollectionWritable>} for
 * list like classes.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-08-15
 */
public interface ECMAScriptCollectionWritableList {

  ArrayList<ECMAScriptCollectionWritable> ecmaScriptCollectionWritableList();
  int size();
}
