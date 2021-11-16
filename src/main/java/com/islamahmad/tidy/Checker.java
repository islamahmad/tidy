package com.islamahmad.tidy;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Checker {
    
    final static Logger LOGGER = LoggerFactory.getLogger(Checker.class);

    public static void check( ResourceLoader resourceLoader  ) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:data.txt");
    	InputStream inputStream = resource.getInputStream();
		try {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bdata, StandardCharsets.UTF_8);
			ArrayList<String> numbersStrings = new ArrayList<String>();
			data.lines().forEach(line -> {numbersStrings.add(line);});
			ArrayList<Integer> numbers = new ArrayList<Integer>();
			numbersStrings.forEach(number -> {numbers.add(Integer.parseInt(number));});
            //1- read the first value in the file to get the number of test cases
            //2- create a loop for the same number of expected lines 
            for (int index = 1; index <= numbers.get(0); index ++){
                //3- for each test case, read the line
                char[] currentNumber = numbersStrings.get(index).toCharArray();
                Boolean result = checkTidiness(currentNumber); 
                if (result){
                    String stringNumber = new String(currentNumber);

                    LOGGER.error("Case #" + index + ": " + stringNumber);
                } else {
                    //6- if not tidy, reduce by one and test again 
                    reduceAndCheck(currentNumber, index);
                }
            }
        } catch (IOException e) {
            LOGGER.error("IOException while opening file", e);
        }
    }
    private static Boolean reduceAndCheck(char[] currentNumber, int index){
        String stringNumber = new String(currentNumber);
        Integer number = Integer.parseInt(stringNumber)-1;
        if (number >  9){
            char[] newNumber = number.toString().toCharArray();
            Boolean result = checkTidiness(newNumber);
            if (result) {
                LOGGER.error("Case #" + index + ": " + number.toString());
                return result; 
            }
            else return reduceAndCheck(newNumber, index);
        }else return true;
    }
    private static Boolean checkTidiness(char[] currentNumber){
            //4- test tidyness of the line
                // 4.a if a single digit number, it is tidy 
                if (currentNumber.length ==1) return true;
                // 4.b if it is 2 digits number, check if the 2nd is smaller than the first then it is not tidy
                else if (currentNumber.length == 2 && currentNumber[0] <= currentNumber[1]) 
                    return true;
                else {
                   return checkThreeOrMore(currentNumber); 
                }
    }

    private static Boolean checkThreeOrMore(char[] currentNumber){
        // 4.c if the number is 3 digits or more, check each adjacent pairs if they are tidy 
        for (int charIndex = 0 ; charIndex < currentNumber.length-1 ; charIndex++){
            //5- if tidy, print the line
            if (currentNumber[charIndex] <= currentNumber[charIndex+1]){
                if (charIndex <= currentNumber.length-2){
                    continue; 
                }else {
                    return true;
                }
            }else return false; 
        }
        return true; 
    }
}
