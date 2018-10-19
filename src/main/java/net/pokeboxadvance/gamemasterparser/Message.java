package net.pokeboxadvance.gamemasterparser;

import net.pokeboxadvance.Move;

/**
 * Class for predefined messages to avoid repetition.
 *
 * All methods of this class return a {@code String} and are self explanatory.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-28
 */
public final class Message {

  public static String fileNotFound(String path) {
    return "File not found for path " + path;
  }

  public static String emptyOrWhitespacePath(String path) {
    return "Path is empty or only consists of whitespace";
  }

  @Deprecated
  public static String exceptionParsingMove(String attribute, String string, Move move) {
    return "Exception parsing move " + attribute + " \"" + string + "\" for " + move;
  }

  public static String exceptionParsing(String attribute, String string, Object object) {
    return "Exception parsing " + attribute + " \"" + string + "\" for " + object.getClass()
        .getSimpleName() + " " + object;
  }
}
