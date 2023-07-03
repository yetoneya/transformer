package testwork;

import testwork.transformer.ToExcelColumnNameTransformer;
import testwork.transformer.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Transformer transformer = new ToExcelColumnNameTransformer();
        List<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("ToExcelColumnNameTransformer: Numbers from 1 to 2 147 483 647:");
        while (scanner.hasNextInt()) {
            int next = scanner.nextInt();
            list.add(next);
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        List<String> transformed = transformer.transform(array);
        System.out.println(transformed);
    }

}

