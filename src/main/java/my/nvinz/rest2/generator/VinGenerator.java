package my.nvinz.rest2.generator;

import java.util.HashMap;
import java.util.Map;

public class VinGenerator {

    private static final String upper = "ABCDEFGHJKLMNPRSTUVWXYZ";
    private static final String digits = "0123456789";
    private static final String symbols = upper + digits;

    public static String generateVIN() {
        String tempVIN = generateWMI() + generateVDS() + generateVIS();
        return checkDigit(tempVIN);
    }

    private static String checkDigit(String tempVIN) {
        int[] lettersNums = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 7, 9, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] weights = {8, 7, 6, 5, 4, 3, 2, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        char[] clearVIN = tempVIN.replace("checkDigit", "").toCharArray();
        for (int i = 0; i < clearVIN.length; i++) {
            if (digits.indexOf(clearVIN[i]) >= 0) {
                sum += Integer.parseInt(String.valueOf(clearVIN[i])) * weights[i];
            } else {
                sum += lettersNums[upper.indexOf(clearVIN[i])] * weights[i];
            }
        }
        Integer result = sum % 11;
        String symbol = result == 10 ? "X" : result.toString();
        return tempVIN.replace("checkDigit", symbol);
    }

    private static String generateWMI() {
        try {
            Map<Integer, String> countryCodes = new HashMap<>();
            //Change zones to countries
            countryCodes.put(0, "A-H");
            countryCodes.put(1, "J-R");
            countryCodes.put(2, "S-Z");
            countryCodes.put(3, "1-5");
            countryCodes.put(4, "6-7");
            countryCodes.put(5, "8-9");

            String mask = countryCodes.get((int) (Math.random() * 6));

            //it's for X-X pattern. need XX-XX pattern

            StringBuilder builder = new StringBuilder();

            int randomCharacterNumber = (int) (Math.random() * (symbols.indexOf(mask.split("-")[1]) - symbols.indexOf(mask.split("-")[0])) + symbols.indexOf(mask.split("-")[0]));
            builder.append(symbols.charAt(randomCharacterNumber));
            builder.append(symbols.charAt((int) (Math.random() * symbols.length())));
            builder.append(symbols.charAt((int) (Math.random() * symbols.length())));

            return builder.toString();
        } catch (StringIndexOutOfBoundsException e) {
            return "ERROR IN generateWMI";
        }
    }

    private static String generateVDS() {
        try {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                int randomCharacterNumber = (int) (Math.random() * symbols.length());
                builder.append(symbols.charAt(randomCharacterNumber));
            }
            builder.append("checkDigit");

            return builder.toString();
        } catch (StringIndexOutOfBoundsException e) {
            return "ERROR IN generateVDS";
        }
    }

    private static String generateVIS() {
        try {
            String lettersForYear = "ABCDEFGHJKLMNPRSTVWXY123456789";
            int yearOffset = Math.abs((int) (Math.random() * 3000) - 1980);
            StringBuilder builder = new StringBuilder();
            builder.append(lettersForYear.charAt(yearOffset % lettersForYear.length()));

            for (int i = 0; i < 1; i++) {
                int randomCharacterNumber = (int) (Math.random() * symbols.length());
                builder.append(symbols.charAt(randomCharacterNumber));
            }
            for (int i = 0; i < 6; i++) {
                int randomCharacterNumber = (int) (Math.random() * digits.length());
                builder.append(digits.charAt(randomCharacterNumber));
            }

            return builder.toString();
        } catch (StringIndexOutOfBoundsException e) {
            return "ERROR IN generateVIS";
        }
    }
}
