package net.pokeboxadvance;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import net.pokeboxadvance.gamemasterparser.DelimitedWritable;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritable;
import net.pokeboxadvance.gamemasterparser.Format;
import net.pokeboxadvance.gamemasterparser.Named;
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
public class Move implements DelimitedWritable, ECMAScriptWritable, Named {

  private int id;
  private String name;
  private Type type;
  private int power,
      energy,
      duration;

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

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public int getPower() {
    return power;
  }

  public void setPower(int power) {
    this.power = power;
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = energy;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public String toString() {
    return this.id + " " + this.name + " " + this.type + " " + this.power;
  }

  @Override
  public String toDelimited(char delimiter) {
    return "" + this.id + delimiter + this.name + delimiter + this.type + delimiter + this.power
        + delimiter + this.duration;
  }

  @Override
  public String toECMAScriptDef() {
    Map<String, Object> map = new HashMap<>();
    for(Field field : Move.class.getDeclaredFields()) {
      try {
        map.put(field.getName(), field.get(this));
      } catch (Exception e) {

      }
    }
    return Format.toECMAScriptObject(map);
//    return "var M_" + Format.toVariableName(this.name) + " = {type: " + this.type + ", power: " + this.power + "};";
  }

  @Override
  public String toECMAScriptCollectionDef() {
    return null;
  }

  @Override
  public String toJSON() {
    return null;
  }
}
