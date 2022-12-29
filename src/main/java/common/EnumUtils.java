package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This helper class provides a functional interface to retrieve the attribute(s) of a given enumerated type's constants.<br>
 * This functional interface is used in two ways :
 * {@link EnumUtils#createLookupMap(Class, EnumProperty)}<br>
 * -> creates of map of enumerated type constants' attributes mapped to each enumerated type constant.<br>
 * This enables retrieving an enumerated type constant by its attribute.<br>
 * {@link EnumUtils#getAllValues(Class, EnumProperty)}<br>
 * -> creates a list of all enumerated type constants attributes.<br>
 */
public final class EnumUtils {

  private EnumUtils() {
  }

  /**
   * @param <E> an enumerated type class
   * @param <A> an enum constant's attribute
   */
  public interface EnumProperty<E extends Enum<E>, A> {
    A getValue(E type);
  }

  public static <E extends Enum<E>, A> Map<A, E> createLookupMap(Class<E> enumTypeClass, EnumProperty<E, A> prop) {
    Map<A, E> lookup = new HashMap<>();
    for (E constant : enumTypeClass.getEnumConstants())
      lookup.put(prop.getValue(constant), constant);
    return Collections.unmodifiableMap(lookup);
  }

  public static <E extends Enum<E>, A> List<A> getAllValues(Class<E> enumTypeClass, EnumProperty<E, A> prop) {
    List<A> listOfValues = new ArrayList<>();
    Arrays.stream(enumTypeClass.getEnumConstants())
            .map(prop::getValue)
            .forEach(listOfValues::add);
    return listOfValues;
  }
}
