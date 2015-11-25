package com.winxuan.ec.task.support.utils;

/**
 * 
 * @author luosh
 *
 */
public class ISBNTool {
    public ISBNTool() {
    }
    public static String convertISBN13To10AddMinus(String isbn) {
      isbn = isbn.trim() ;
      String isbnBak = isbn;
      try {
        isbn = isbn.substring(3,12);
        String c10 = "";
        int sum = 0;
        for (int i = 1; i <= 9; i++) {
          sum += (Integer.parseInt(String.valueOf(isbn.charAt(i - 1)))) * (11 - i);
        }
        sum = 11 - sum % 11;
        if (sum == 10) {
          c10 = "X";
        }
        else if (sum == 11) {
          c10 = "0";
        }
        else {
          c10 = String.valueOf(sum);
        }
        int pos = 0;
        char c2;
        c2 = isbn.charAt(1);

        switch (c2) {
          case '0':
            pos = 2;
            break;
          case '5':
            pos = 4;
            break;
          case '8':
            pos = 5;
            break;
          case '9':
            pos = 6;
            break;
          default:
            pos = 3;
        }
        String isbnFinal = "";
        isbnFinal = isbn.substring(0, 1) + "-" + isbn.substring(1, pos + 1) +
            "-" +
            isbn.substring(pos + 1) + "-" + c10;
        return isbnFinal;
      }
      catch (Exception e) {
        return isbnBak;
      }
    }
    public static String convertISBN13To10(String isbn) {
      isbn = isbn.trim();
      if (isbn.length()!=13){
          return isbn;
      }
      String isbnBak = isbn;
      try {
        isbn = isbn.substring(3,12);
        String c10 = "";
        int sum = 0;
        for (int i = 1; i <= 9; i++) {
          sum += (Integer.parseInt(String.valueOf(isbn.charAt(i - 1)))) * (11 - i);
        }
        sum = 11 - sum % 11;
        if (sum == 10) {
          c10 = "X";
        }
        else if (sum == 11) {
          c10 = "0";
        }
        else {
          c10 = String.valueOf(sum);
        }
        String isbnFinal = "";
        isbnFinal = isbn+ c10;
        return isbnFinal;
      }
      catch (Exception e) {
        return isbnBak;
      }
    }
    public static String convertISBN10To13(String isbn10) {
    	if (isbn10==null ||isbn10.length()!=10){
    		return "";
    	}
    	String isbn="978"+isbn10.substring(0,9);
    	return isbn+calculateCheckDigit(isbn);
    }
    public static String calculateCheckDigit(String isbn12) {
        int checkSum = 0;
        int digit = 0;  
    	if (isbn12==null ||isbn12.length()!=12){
    		return "";
    	}

        //计算校验和
        for (int i = 0; i < isbn12.length(); i++) {
            if (Character.isDigit(isbn12.charAt(i))) {
                digit = Character.digit(isbn12.charAt(i), 10);
                if (i % 2 == 1) {
                    digit *= 3;
                }
                checkSum += digit;
            } else {
                return "";
            }
        }
 
        // compute the checkDigit
        checkSum = 10 - checkSum % 10;
 
        checkSum = checkSum % 10;
 
        return checkSum+"";
    }
 
    public static String getPublisherCodeByISBN13(String isbn){
    	int from=4;
    	int pos;
    	char c2 = isbn.charAt(from);
    	switch (c2) {
        case '0':
          pos = from+2;
          break;
        case '5':
          pos = from+4;
          break;
        case '6':
            pos = from+4;
            break;
        case '7':
            pos = from+4;
            break;
        case '8':
          pos = from+5;
          break;
        case '9':
          pos = from+6;
          break;
        default:
          pos = from+3;
      }
    	return isbn.substring(from,pos);
    }
    
}
