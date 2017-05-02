import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import static java.lang.Integer.min;

/**
 * Palindrom created by &#x43b;&#x451;&#x43d;&#x44f; on 01.05.2017.
 */

public class Palindrom {
    private static int[] even, odd;
    private static HashMap<String, String> palindromes;

    public static void main(String[] args) {
//         long startTime = System.currentTimeMillis();
        try {
            if (args.length == 0) throw new InvalidArgumentException(args);
//             if (!args[1].equals(">")) throw new InvalidArgumentException(args);
            OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(args[2]));
            even = odd = new int[args[0].length()];
            palindromes = new HashMap<>();

            for (int i = 0; i < even.length; i++) {
                even[i] = odd[i] = 0;
            }

            evenCount(args[0]);
            oddCount(args[0]);

            fos.write(Integer.toString(palindromes.size()));
            fos.close();

//             Довольно небрежное обращение с Картой, но что поделать
//             System.out.println(palindromes.keySet());
//             System.out.println(System.currentTimeMillis() - startTime);

        } catch (InvalidArgumentException | java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static void evenCount(String s) {
        int l = 0, r = -1;
        int k;
        for (int i = 0; i < s.length(); i++) {
            if (i > r) k = 1;
            else k = min(even[l + r - i], r - i);
            palindromes.put(s.substring(i, i + 1), "");
            while (0 <= i - k && i + k < s.length() && s.charAt(i - k) == s.charAt(i + k)) {
                if (k != 0) palindromes.put(s.substring(i - k, i + k) + s.charAt(i + k), "");
                k++;
            }
            even[i] = k;
            if (i + k - 1 > r) {
                r = i + k - 1;
                l = i - k + 1;
            }
        }
    }

    private static void oddCount(String s) {
        int l = 0, r = -1;
        int k;
        for (int i = 0; i < s.length(); i++) {
            if (i > r) k = 0;
            else k = min(odd[l + r - i + 1], r - i + 1);

            while (i + k < s.length() && i - k - 1 >= 0 && s.charAt(i + k) == s.charAt(i - k - 1)) {
                palindromes.put(s.substring(i - k - 1, i + k) + s.charAt(i + k), "");
                k++;
            }
            odd[i] = k;

            if (i + k - 1 > r) {
                l = i - k;
                r = i + k - 1;
            }
        }
    }
}

