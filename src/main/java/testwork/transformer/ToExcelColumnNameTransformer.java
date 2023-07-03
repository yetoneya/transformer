package testwork.transformer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ToExcelColumnNameTransformer implements Transformer {

    Logger logger = Logger.getLogger(ToExcelColumnNameTransformer.class.getName());

    private static final int SCALE = 26;
    private static final int SHIFT = 64;
    private static final int[] powers = new int[8];

    private static final Map<Integer, String> numberToName = new ConcurrentHashMap<>();

    static {
        int i = 0;
        while (i < powers.length) {
            powers[i] = (int) Math.pow(SCALE, i++);
        }
    }

    @Override
    public List<String> transform(int... input) {
        return Arrays.stream(input)
                .distinct()
                .filter(this::checkNumber)
                .sorted()
                .mapToObj(this::getColumnName)
                .collect(Collectors.toList());
    }

    private boolean checkNumber(int number) {
        if (number <= 0) {
            logger.warning("Skipping of negative or zero value: " + number);
            return false;
        }
        return true;
    }

    private String getColumnName(int number) {
        return numberToName.computeIfAbsent(number, this::mapToString);
    }

    private String mapToString(int number) {
        StringBuilder sb = new StringBuilder();
        int num = number;
        for (int n = 1; n < powers.length; n++) {
            int rest = number % powers[n];
            if (rest == 0) rest = powers[n];
            sb.append((char) (rest / powers[n - 1] + SHIFT));
            if (number / powers[n] == 0) break;
            number = number - rest;
            if (number == 0) break;
        }
        return sb.reverse().insert(0, num + ":").toString();
    }

}
