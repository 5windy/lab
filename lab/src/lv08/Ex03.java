package lv08;

public class Ex03 {
	public static void main(String[] args) {
		
		// String Pool 
		
		String str1 = "김지연";
		String str2 = "김지연";
		
		System.out.println(str1 == str2); 			// true 
		
		String str3 = new String("김지연");
		
		System.out.println(str1 == str3); 			// false
		
		System.out.println(str1.equals(str2));		// true 
		System.out.println(str1.equals(str3));		// true 
		
		System.out.println(System.identityHashCode(str1));	// 1993134103
		System.out.println(System.identityHashCode(str2));	// 1993134103
		System.out.println(System.identityHashCode(str3));	// 405662939
		
		
		// Integer Pool 
		// ㄴ -128 ~ +127 
		
		Integer num1 = 127;
		Integer num2 = 127;

		System.out.println(num1 == num2); 			// true

		Integer num3 = 128;
		Integer num4 = 128;
		
		System.out.println(num3 == num4); 			// false 
		
		System.out.println(System.identityHashCode(num1));	// 653305407
		System.out.println(System.identityHashCode(num2));	// 653305407
		System.out.println(System.identityHashCode(num3));	// 1130478920
		System.out.println(System.identityHashCode(num4));	// 1404928347
		
		
		
	}

}
