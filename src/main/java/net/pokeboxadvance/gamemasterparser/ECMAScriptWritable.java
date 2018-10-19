package net.pokeboxadvance.gamemasterparser;

import java.lang.reflect.Field;

/**
 * Interface to ensure {@code toECMAScriptDef()} method.
 *
 * @author AL4CR1TY
 * @version %I%
 * @since 2018-06-01
 */
public interface ECMAScriptWritable {

//  public String objectifyField(Field field) throws Exception;

  public String toECMAScriptDef();

  public abstract String toECMAScriptCollectionDef();

  public abstract String toJSON();
}
