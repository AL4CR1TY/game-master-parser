package net.pokeboxadvance;

import java.lang.reflect.Field;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.pokeboxadvance.gamemasterparser.DelimitedWritable;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritable;
import net.pokeboxadvance.gamemasterparser.Format;
import net.pokeboxadvance.gamemasterparser.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;

/**
 * Represents a Pokedex entry.
 *
 * Has all fields necessary to represent a Pokedex entry.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-28
 */
public class DexPokemon implements DelimitedWritable, ECMAScriptWritable, Named {

  private int number;
  private String name;
  private Type primaryType, secondaryType;
  private ArrayList<Type> types = new ArrayList<>();
  private int baseStamina, baseAttack, baseDefense;
  private double pokedexHeight, pokedexWeight;
  private double baseCatchRate, baseFleeRate;
  private int buddyDistance;

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addType(Type type) {
    this.types.add(type);
  }

  public Type getPrimaryType() {
    return primaryType;
  }

  public void setPrimaryType(Type primaryType) {
    this.primaryType = primaryType;
  }

  public Type getSecondaryType() {
    return secondaryType;
  }

  public void setSecondaryType(Type secondaryType) {
    this.secondaryType = secondaryType;
  }

  public int getBaseStamina() {
    return baseStamina;
  }

  public void setBaseStamina(int baseStamina) {
    this.baseStamina = baseStamina;
  }

  public int getBaseAttack() {
    return baseAttack;
  }

  public void setBaseAttack(int baseAttack) {
    this.baseAttack = baseAttack;
  }

  public int getBaseDefense() {
    return baseDefense;
  }

  public void setBaseDefense(int baseDefense) {
    this.baseDefense = baseDefense;
  }

  public double getPokedexHeight() {
    return pokedexHeight;
  }

  public void setPokedexHeight(double pokedexHeight) {
    this.pokedexHeight = pokedexHeight;
  }

  public double getPokedexWeight() {
    return pokedexWeight;
  }

  public void setPokedexWeight(double pokedexWeight) {
    this.pokedexWeight = pokedexWeight;
  }

  public double getBaseCatchRate() {
    return baseCatchRate;
  }

  public void setBaseCatchRate(double baseCatchRate) {
    this.baseCatchRate = baseCatchRate;
  }

  public double getBaseFleeRate() {
    return baseFleeRate;
  }

  public void setBaseFleeRate(double baseFleeRate) {
    this.baseFleeRate = baseFleeRate;
  }

  public int getBuddyDistance() {
    return buddyDistance;
  }

  public void setBuddyDistance(int buddyDistance) {
    this.buddyDistance = buddyDistance;
  }

  @Override
  public String toString() {
    return this.number + " " + this.name + " " /*+ this.primaryType + " " + this.secondaryType*/
        + this.types
        + " HP " + this.baseStamina + " ATK " + this.baseAttack + " DEF " + this.baseDefense
        + " dex height " + this.pokedexHeight + " dex weight " + this.pokedexWeight
        + " catch rate " + this.baseCatchRate + " flee rate " + this.baseFleeRate;
  }

  @Override
  public String toDelimited(char delimiter) {
    return "" + this.number + delimiter + this.name + delimiter + this.primaryType.getName()
        + delimiter + (this.secondaryType != null ? this.secondaryType.getName() : "") + delimiter
        + this.baseStamina + delimiter + this.baseAttack + delimiter + this.baseDefense + delimiter
        + this.pokedexHeight + delimiter + this.pokedexWeight + delimiter + this.baseCatchRate
        + delimiter + this.baseFleeRate;
  }

  /*@Override
  public String toECMAScriptDef() {
    StringBuilder def = new StringBuilder("const " + constName() + " = {");
    Field[] fields = this.getClass().getDeclaredFields();
    int count = 0;
    for (Field field : fields) {
      try {
        def.append(objectifyField(field));

        if (++count != fields.length) {
          def.append(", ");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    def.append("};");
    return def.toString();
  }*/

  @Override
  public String toECMAScriptDef() {
    Map<String, Object> map = new HashMap<>();
    for(Field field : this.getClass().getDeclaredFields()) {
      try {
        map.put(field.getName(), field.get(this));
      } catch (Exception e) {

      }
    }
    return Format.toECMAScriptObject(map);
  }

  @Override
  public String toECMAScriptCollectionDef() {
    return "\"" + this.name + "\": " + Format.toVariableName(this.name);
  }

  @Override
  public String toJSON() {
    StringBuilder json = new StringBuilder(Format.putInQuotes(Format.toVariableName(this.name)) + ": {");
    Field[] fields = this.getClass().getDeclaredFields();
    int count = 0;
    for (Field field : fields) {
      try {
        json.append(jsonifyField(field));

        if (++count != fields.length) {
          json.append(", ");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    json.append('}');
    return json.toString();
  }

  private String jsonifyField(Field field) throws Exception {
    StringBuilder json = new StringBuilder();
    json.append(Format.putInQuotes(field.getName()));
    json.append(": ");
    if (field.get(this) instanceof String || field.get(this) instanceof Type) {
      json.append(Format.putInQuotes(field.get(this)));
    } else if (field.get(this) instanceof ArrayList) {
      ArrayList list = (ArrayList) field.get(this);
      int count = 0;
      json.append('[');
      for (Object listItem : list) {
        json.append(Format.putInQuotes(listItem));
        if (++count != list.size()) {
          json.append(", ");
        }
      }
      json.append(']');
    } else {
      json.append(field.get(this));
    }
    return json.toString();
  }
}
