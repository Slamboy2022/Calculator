import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    final Map<String, Integer> romeArabMap = new HashMap<>();
    final int[] arabDigit = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};
    final String[] romeDigit = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    Main()
    {
        romeArabMap.put("I", 1);
        romeArabMap.put("II", 2);
        romeArabMap.put("III", 3);
        romeArabMap.put("IV", 4);
        romeArabMap.put("V", 5);
        romeArabMap.put("VI", 6);
        romeArabMap.put("VII", 7);
        romeArabMap.put("VIII", 8);
        romeArabMap.put("IX", 9);
        romeArabMap.put("X", 10);
    }

    public static void main(String[] args) {
        try{
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите выражение с арабскими или римскими цифрами");
            System.out.println(", числа должны быть от 1 до 10 или от I до X через пробел");
            System.out.println("Например: 1 + 1 или I + I");
            System.out.println(" операторы: + - * /");
            System.out.println("Введите выражение:");
            String result = Main.calc(keyboard.readLine());
            System.out.println("Ответ: " + result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static String calc(String input)
    {
        String operator;
        boolean isRomanNumber;
        Main main = new Main();

        List<String> inputList = Arrays.asList(input.split(" "));

        int number1,number2;
//--------------Проверка на количество введеных знаков------------//
        if (inputList.size() != 3)
        {
            throw new IllegalArgumentException("ОШИБКА: Выражение должно быть Число Оператор Число!");
        }
//----------------------------------------------------------------//

//------------------- Проверка на верный оператор -----------------//
        if("*".equals(inputList.get(1)) || "/".equals(inputList.get(1)) || "+".equals(inputList.get(1)) || "-".equals(inputList.get(1)))
        {
            operator = inputList.get(1);
        }
        else
        {
            throw new IllegalArgumentException("ОШИБКА: оператор " + inputList.get(1) + " не корректен");
        }
//-----------------------------------------------------------------//

//--------------------Проверка на пары чисел арабские или римские--//
        if (main.isNumberArab(inputList.get(0)) && main.isNumberArab(inputList.get(2))){
            number1 = Integer.parseInt(inputList.get(0));
            number2 = Integer.parseInt(inputList.get(2));
            isRomanNumber = false;
        } else if (main.isRoman(inputList.get(0)) && main.isRoman(inputList.get(2))){
            number1 = main.romeToArabConvert(inputList.get(0));
            number2 = main.romeToArabConvert(inputList.get(2));
            isRomanNumber = true;
        } else {
            throw new IllegalArgumentException("ОШИБКА. Числа должны быть оба арабские или оба римские");
        }
//------------------------------------------------------------------//

//--------------------- проверка на величину чисел -------------------//
        if (!(number1>=1 && number1<=10)){
            throw new IllegalArgumentException("ОШИБКА. Число #1 должно быть от 1 до 10 или от I до X включительно");
        }

        if (!(number2>=1 && number2<=10)){
            throw new IllegalArgumentException("ОШИБКА. Число #2 должно быть от 1 до 10 или от I до X включительно");
        }
//---------------------------------------------------------------------//

        int result = main.calcArab(number1, operator, number2);

        if (isRomanNumber){
            String sign = result < 0 ? "-" : "";
            return sign + main.arabToRomeConvert(Math.abs(result));
        }





        return String.valueOf(result);
    }
    int calcArab(int num1, String oper, int num2)
    {
        int res;
        switch (oper)
        {
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num1 - num2;
                break;
            case "*":
                res = num1 * num2;
                break;
            case "/":
                res = num1 / num2;
                break;
            default:
                throw  new AssertionError();
        }
        return res;
    }
    public Integer romeToArabConvert(String st){
        if (romeArabMap.containsKey(st)){
            return romeArabMap.get(st);
        }
        return null;
    }
    public String arabToRomeConvert(Integer num){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<arabDigit.length; i++){
            while((num - arabDigit[i])>=0){
                num -= arabDigit[i];
                result.append(romeDigit[i]);
            }
        }
        return result.toString();
    }
    public boolean isNumberArab(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isRoman(String str) {
        return romeArabMap.containsKey(str);
    }
}
