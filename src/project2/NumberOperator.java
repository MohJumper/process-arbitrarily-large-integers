package project2;

/*********************************************************************** 
Name: Moe
File Name: 
Assignment number:

Other comments regarding the file - description of why it is there, etc. 
************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NumberOperator {
	
	private static final int PAD_ARRAY_SIZE = 2;
	private static final String PLUS_SYMBOL = "+";
	private static final String MINUS_SYMBOL = "-";
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TEN = 10;
	private static final int NEG_ONE = -1;
	
	private static boolean flag = false;
	
	private static StackLinkedList<Character> left = new StackLinkedList<>();
	private static StackLinkedList<Character> right = new StackLinkedList<>();
	private static StackLinkedList<Integer> result = new StackLinkedList<>();

	public static void main(String[] args) {
		// NumberOperator no = new NumberOperator();
		// no.readTxtToarray("addsAndSubtracts.txt");
		// remove static from all the methods 
		System.out.println("Just starting ... \n");

		readTxtToarray("addsAndSubtracts.txt");
//		readTxtToarray("test.txt");
		
	}
	// 	public void readTxtToarray(String file) {
	public static void readTxtToarray(String file) {
		Scanner scnr;
		
		try {
			scnr = new Scanner(new File(file));

			
			while(scnr.hasNext()) {
				String line = scnr.nextLine();
				String[] expr = line.split(" ");
				String operandleft = expr[0];
				String operator = expr[1];
				String operandRight = expr[2];
				System.out.printf("%8s \n" ,operandleft);
				System.out.printf(" %4s%s \n" ,operator, operandRight);
				// ----------- this work in terms of return result and clear it for the next line of numbers
				doArithmetic(operandleft, operandRight, operator);
				if(flag) {
					result.pop();
					System.out.printf("%3s -%s\n", "" ,result);
					result.clearStack();
					System.out.println();
				} else {
					result.pop();
					System.out.printf("%8s \n" ,result);
					result.clearStack();
					System.out.printf("%8s \n" ,result);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks multiple conditions to either call add or subtract method
	 * @param operndOne string one on the left side
	 * @param operndTwo string two on the right side
	 * @param operatorSing
	 * @return reverse result stack of the arithmetic 
	 */
	public static StackLinkedList<Integer> doArithmetic(String operndOne, String operndTwo, String operatorSing) {
		if((!isFirstCharNegative(operndOne)) && operatorSing.compareTo(PLUS_SYMBOL) == ZERO 
				&& (!isFirstCharNegative(operndTwo)) 
				|| (!isFirstCharNegative(operndOne)) 
				&& operatorSing.compareTo(MINUS_SYMBOL) == ZERO && isFirstCharNegative(operndTwo) ) {
			addRoutine(operndOne, operndTwo);
			flag = false;
 		}
		//
		if (isFirstCharNegative(operndOne) && operatorSing.compareTo(MINUS_SYMBOL) == ZERO 
				&& (!isFirstCharNegative(operndTwo)) 
				|| isFirstCharNegative(operndOne) 
				&&  operatorSing.compareTo(PLUS_SYMBOL) == ZERO && isFirstCharNegative(operndTwo) ) {
			addRoutine(operndOne, operndTwo);
			flag = true;
		}
		if ((!isFirstCharNegative(operndOne)) && operatorSing.compareTo(MINUS_SYMBOL) == ZERO 
				&& (!isFirstCharNegative(operndTwo)) 
				|| isFirstCharNegative(operndOne) && operatorSing.compareTo(MINUS_SYMBOL) == ZERO 
				&& isFirstCharNegative(operndTwo) ) {
			subtractRoutine(operndOne, operndTwo);
			flag = true;
		}
		
		if((!isFirstCharNegative(operndOne)) && operatorSing.compareTo(PLUS_SYMBOL) == ZERO 
				&& isFirstCharNegative(operndTwo) ){
			subtractRoutine(operndOne, operndTwo);
			flag = false;
		}
		
		if (isFirstCharNegative(operndOne) && operatorSing.compareTo(PLUS_SYMBOL) == ZERO 
				&& (!isFirstCharNegative(operndTwo)) ) {
			subtractRoutine(operndOne, operndTwo);
			flag = true;
		}
		
		result.reverseLinkLsStack();
		return result;
	}
	
	/**
	 * This method add each number from the stack after been processed and removed sing from the string.
	 * Before doing the addition the method makes call to reverse the numbers in each stack. 
	 * @param operndOne
	 * @param operndTwo
	 */
	public static void addRoutine(String operndOne, String operndTwo){
		int sum =0, carry =0, valueOne, valueTwo;
		pushToLsLeft(padZero(operndOne, operndTwo)[ZERO]);
		pushToLsRight(padZero(operndOne, operndTwo)[ONE]);
		left.reverseLinkLsStack();
		right.reverseLinkLsStack();
		while(left.sizeLinkedLs() > ZERO && right.sizeLinkedLs() > ZERO) {
			valueOne = Character.getNumericValue(left.pop());
			valueTwo = Character.getNumericValue(right.pop());
			sum = (valueOne + valueTwo + carry) % 10;
			carry = (valueOne + valueTwo + carry) / 10;
			
			result.push(sum);
		}

		if (carry > ZERO) {
			result.push(carry);
		}
		
	}
	
	/**
	 * This method subtract each number at the time from the stack. The method makes call to three other methods which are explained.
	 * The while loop checks if there is any numbers left in either side stack. It then convert the char in the stack to int.
	 * The sub set to subtract value one from value two if the results is less than zero then add ten and set borrow from zero to -1
	 * This method does not return anything but push the result to result stack and after breaking from the while loop it reverse it.
	 * @param operndOne the left side operand
	 * @param operndTwo the right side operand
	 */
	public static void subtractRoutine(String operndOne, String operndTwo) {
		int sub =0, borrow =0, valueOne, valueTwo;
		pushToLsLeft(padZero(operndOne, operndTwo)[ZERO]);
		pushToLsRight(padZero(operndOne, operndTwo)[ONE]);
		while(left.sizeLinkedLs() > ZERO && right.sizeLinkedLs() > ZERO) {
			valueOne = Character.getNumericValue(left.pop());
			valueTwo = Character.getNumericValue(right.pop());

			sub = borrow + valueOne - valueTwo;
			if (sub < 0) {
				// when you borrow you need to add 10 
				sub = TEN + valueOne - valueTwo;
				// set borrow to -1 so the number been borrowed from cancelled out
				borrow = NEG_ONE;
			} else {
				borrow = ZERO;
			}

			result.push(sub);
		}
		// at the end reverse the numbers in the stack 
		result.reverseLinkLsStack();
	}
	
	/**
	 * This method add zero to each string based on the length of the passed string that been processed and removed negative sing.
	 * Makes call to remove the sing method.
	 * @param strLeft
	 * @param strRight
	 * @return an array of number of strings
	 */
	public static String[] padZero(String strLeft, String strRight) {
		String removedSingLeft = removeSing(strLeft);
		String removedSingRight = removeSing(strRight);
		
		int lenLeft = removedSingLeft.length();
		int lenRight = removedSingRight.length();
		String[] padded = new String[PAD_ARRAY_SIZE];
		
		// add zero at the start regardless of the length
		strLeft = "0" + removedSingLeft;
		strRight = "0" + removedSingRight;
		
		// check for the length of the string and add zero accordingly
		if (lenLeft == lenRight) {
			padded[0] = strLeft;
			padded[1] = strRight;
		} else if (lenLeft > lenRight) {
			// left string is greater right so add to the right 
			padded[0] = strLeft;
			padded[1] = addZeros(lenLeft, lenRight, strRight);
		} else {
			// Right string is greater so add to the left
			padded[0] = strRight;
			padded[1] = addZeros(lenRight, lenLeft, strLeft);
		}
		return padded;
	}
	
	/**
	 * This method add zero to the string of less length 
	 * @param lnLeft
	 * @param lnRight
	 * @param toPad
	 * @return
	 */
	public static String addZeros(int lnLeft, int lnRight, String toPad) {
		for (int i =0; i < (lnLeft - lnRight); i++) {
			toPad = "0" + toPad;
		}
		return toPad;
	}
	
	/**
	 * This method remove the negative sing based on the assumption that sing will be the first char.
	 * @param str string that starts with number sign in this case negative
	 * @return
	 */
	public static String removeSing(String str) {
		String sing = str.substring(0, 1);
		if (sing.compareTo(MINUS_SYMBOL) == ZERO) {
			return str.substring(1);
		}
		return str;
	}
	
	/**
	 * This method is a twin with method pushToLsRight. It pushes padded numbers to the left and the other one to the right.
	 * @param paddedRemSignNum 
	 */
	public static void pushToLsLeft(String paddedRemSignNum) {
		for (int i=0; i < paddedRemSignNum.length(); i++) {
			left.push(paddedRemSignNum.charAt(i));
		}
	}
	
	/**
	 * This method is a twin with method pushToLsLeft. It pushes padded numbers to the right and the other one to the left.
	 * @param paddedRemSignNum
	 */
	public static void pushToLsRight(String paddedRemSignNum) {
		for (int i=0; i < paddedRemSignNum.length(); i++) {
			right.push(paddedRemSignNum.charAt(i));
		}
	}
	
	/**
	 * Checks if the sing at the starts of the string is negative
	 * @param operand string of numbers and sign
	 * @return true or false
	 */
	public static boolean isFirstCharNegative(String operand) {
		if(operand.charAt(ZERO) == '-') {
			return true;
		} else {
			return false;
		}
	}
}
