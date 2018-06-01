package net.pokeboxadvance;

import net.pokeboxadvance.gamemasterparser.DelimitedWritable;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritable;
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
public abstract class Move implements DelimitedWritable, ECMAScriptWritable {

  private static final Logger LOGGER = LogManager.getLogger();

  private int id;
  private String name;
  private Type type;
  private int power;
  private int durationsMs;

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

  public Type getType() {
    return type;
  }

  @Override
  public String toString() {
    return this.id + " " + this.name + " " + this.type + " " + this.power;
  }

  @Override
  public String toDelimited(char delimiter) {
    return this.id + delimiter + this.name + delimiter + this.type + delimiter
        + this.power + delimiter + this.durationsMs;
  }

  @Override
  public String toECMAScriptDef() {
    return "var " + this.name + " = new Move(" + this.id + ", \"" + this.name + "\");";
  }
}
