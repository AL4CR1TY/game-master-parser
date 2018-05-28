package net.pokeboxadvance.gamemasterparser;

/**
 * Class for predefined messages to avoid repetition.
 *
 * All methods in this class return a {@code String} and are self explanatory.
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
}
