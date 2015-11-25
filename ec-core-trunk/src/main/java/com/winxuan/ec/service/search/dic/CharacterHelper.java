/**
 * 
 */
package com.winxuan.ec.service.search.dic;

/**
 * 字符集识别辅助工具类
 * 
 * @author 林良益
 * 
 */
public class CharacterHelper {

	private static final int EIGHT = 8;
	private static final int NINE = 9;
	private static final int TEN = 10;
	private static final int THIRSTEEN = 13;
	private static final int THIRTY_TWO = 32;
	private static final int ONE_HUNDRED_SIXTY = 160;
	private static final int ONE_DOUBLE_TWO_DOUBLE_EIGHT = 12288;
	private static final int SIX_FIVE_TWO_EIGHT_ZERO = 65280;
	private static final int SIX_FIVE_THREE_SEVEN_FIVE = 65375;
	private static final int SIX_FIVE_TWO_FOUR_EIGHT = 65248;
	private static final char LOWER_A = 'a';
	private static final char LOWER_Z = 'z';
	private static final char UPPER_A = 'A';
	private static final char UPPER_Z = 'Z';
	private static final char NUMBER_0 = '0';
	private static final char NUMBER_9 = '9';

	public static boolean isSpaceLetter(char input) {
		return input == EIGHT || input == NINE || input == TEN
				|| input == THIRSTEEN || input == THIRTY_TWO
				|| input == ONE_HUNDRED_SIXTY;
	}

	public static boolean isEnglishLetter(char input) {
		return (input >= LOWER_A && input <= LOWER_Z)
				|| (input >= UPPER_A && input <= UPPER_Z);
	}

	public static boolean isArabicNumber(char input) {
		return input >= NUMBER_0 && input <= NUMBER_9;
	}

	public static boolean isCJKCharacter(char input) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(input);
	 return Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS == ub
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.HANGUL_SYLLABLES
				|| ub == Character.UnicodeBlock.HANGUL_JAMO
				|| ub == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
				|| ub == Character.UnicodeBlock.HIRAGANA 
				|| ub == Character.UnicodeBlock.KATAKANA 
				|| ub == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS;
		
		// 其他的CJK标点符号，可以不做处理
		// || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
		// || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
	}

	/**
	 * 进行字符规格化（全角转半角，大写转小写处理）
	 * 
	 * @param input
	 * @return char
	 */
	public static char regularize(char input) {
		if (input == ONE_DOUBLE_TWO_DOUBLE_EIGHT) {
			input = (char) THIRTY_TWO;

		} else if (input > SIX_FIVE_TWO_EIGHT_ZERO
				&& input < SIX_FIVE_THREE_SEVEN_FIVE) {
			input = (char) (input - SIX_FIVE_TWO_FOUR_EIGHT);

		} else if (input >= UPPER_A && input <= UPPER_Z) {
			input += THIRTY_TWO;
		}

		return input;
	}

}
