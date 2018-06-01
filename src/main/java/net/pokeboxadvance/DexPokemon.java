package net.pokeboxadvance;

import net.pokeboxadvance.gamemasterparser.DelimitedWritable;
import net.pokeboxadvance.gamemasterparser.ECMAScriptWritable;

/**
 * Represents a Pokedex entry.
 *
 * Has all fields necessary to represent a Pokedex entry.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-05-28
 */
public class DexPokemon implements DelimitedWritable, ECMAScriptWritable {

  private int number;
  private String name;
  private Type primaryType, secondaryType;
  private int baseStamina, baseAttack, baseDefense;
  private double pokedexHeight, pokedexWeight;
  private double baseCatchRate, baseFleeRate;

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

  @Override
  public String toString() {
    return this.number + " " + this.name + " " + this.primaryType + " " + this.secondaryType
        + " HP " + this.baseStamina + " ATK " + this.baseAttack + " DEF " + this.baseDefense
        + " dex height " + this.pokedexHeight + " dex weight " + this.pokedexWeight
        + " catch rate " + this.baseCatchRate + " flee rate " + this.baseFleeRate;
  }

  @Override
  public String toDelimited(char delimiter) {
    return this.number + delimiter + this.name + delimiter + this.primaryType + delimiter
        + this.secondaryType + delimiter + this.baseStamina + delimiter + this.baseAttack
        + delimiter + this.baseDefense + delimiter + this.pokedexHeight + delimiter
        + this.pokedexWeight + delimiter + this.baseCatchRate + delimiter + this.baseFleeRate;
  }

  @Override
  public String toECMAScriptDef() {
    return "var " + this.name + " = new DexPokemon(" + this.number + ", \"" + this.name + "\")";
  }
}
