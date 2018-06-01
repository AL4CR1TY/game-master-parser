package net.pokeboxadvance.gamemasterparser;

import java.util.ArrayList;

/**
 * Interface to ensure a return method of type {@code ArrayList<DelimitedWritable>} for list like
 * classes.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-06-01
 */
public interface DelimitedWritableList {

  ArrayList<DelimitedWritable> delimitedWritableList();
  int size();
}
