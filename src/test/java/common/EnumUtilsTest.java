package common;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumUtilsTest extends TestCase {

  public void test_givenTwoEnums_whenGetByAttribute_thenReturnsCorrectConstant() {
    //GIVEN
    List<TestStringEnum> expectedStrConstants = List.of(TestStringEnum.STR1, TestStringEnum.STR2, TestStringEnum.STR3);
    List<TestIntegerEnum> expectedIntConstants =
            List.of(TestIntegerEnum.INT1, TestIntegerEnum.INT2, TestIntegerEnum.INT3);

    //WHEN
    List<TestStringEnum> actualStrConstants = TestStringEnum.retrieveByAttribute("One", "Two", "Three");
    List<TestIntegerEnum> actualIntConstants = TestIntegerEnum.retrieveByAttribute(1, 2, 3);

    //THEN
    assertEquals(expectedStrConstants, actualStrConstants);
    assertEquals(expectedIntConstants, actualIntConstants);
  }

  public void test_givenTwoEnums_whenGetAttributes_thenReturnsCorrectList() {
    //GIVEN
    List<String> expectedStrings = List.of("One", "Two", "Three");
    List<Integer> expectedIntegers = List.of(1, 2, 3);

    //WHEN
    List<Object> actualStrings = verifyGetAttributes(TestStringEnum.class, TestStringEnum::getStringAttribute);
    List<Object> actualIntegers = verifyGetAttributes(TestIntegerEnum.class, TestIntegerEnum::getIntAttribute);

    //THEN
    assertEquals(expectedStrings, actualStrings);
    assertEquals(expectedIntegers, actualIntegers);
  }

  static <T extends Enum<T>> List<Object> verifyGetAttributes(Class<T> enumClazz,
                                                              EnumUtils.EnumProperty<T, Object> enumProp) {
    return EnumUtils.getAllValues(enumClazz, enumProp);
  }

  private enum TestStringEnum {
    STR1("One"),
    STR2("Two"),
    STR3("Three");

    private final String stringAttribute;
    private static final EnumUtils.EnumProperty<TestStringEnum, String> ENUM_PROP =
            TestStringEnum::getStringAttribute;
    private static final Map<String, TestStringEnum> LOOK_UP =
            EnumUtils.createLookupMap(TestStringEnum.class, ENUM_PROP);

    TestStringEnum(String stringAttribute) {
      this.stringAttribute = stringAttribute;
    }

    public final String getStringAttribute() {
      return this.stringAttribute;
    }

    public static List<TestStringEnum> retrieveByAttribute(String... attribute) {
      return Arrays.stream(attribute).map(LOOK_UP::get).collect(Collectors.toList());
    }
  }

  private enum TestIntegerEnum {
    INT1(1),
    INT2(2),
    INT3(3);

    private final Integer intAttribute;
    private static final EnumUtils.EnumProperty<TestIntegerEnum, Integer> ENUM_PROP =
            TestIntegerEnum::getIntAttribute;
    private static final Map<Integer, TestIntegerEnum> LOOK_UP =
            EnumUtils.createLookupMap(TestIntegerEnum.class, ENUM_PROP);

    TestIntegerEnum(Integer intAttribute) {
      this.intAttribute = intAttribute;
    }

    private Integer getIntAttribute() {
      return this.intAttribute;
    }

    public static List<TestIntegerEnum> retrieveByAttribute(Integer... attribute) {
      return Arrays.stream(attribute).map(LOOK_UP::get).collect(Collectors.toList());
    }
  }
}
