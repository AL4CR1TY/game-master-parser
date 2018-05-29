package net.pokeboxadvance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a move.
 *
 * This class is for extension to quick and charge moves.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-29
 */
public abstract class Move {

  private static final Logger LOGGER = LogManager.getLogger();

  private int id;
  private String name;
  private int power;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }
}
