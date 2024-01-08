import java.time.LocalDate;
import java.util.Scanner;

public class Calendar {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("년도를 입력해주세요.");
		int year = sc.nextInt();
		
		System.out.println("월을 입력해주세요.");
		int month = sc.nextInt();
		
		System.out.println(year + "년 " + month + "월");
		System.out.println("월 화 수 목 금 토 일");
		
		LocalDate localDate = LocalDate.of(year, month, 1);
		
		for (int i = 1, j = 1; j <= localDate.lengthOfMonth(); i++) {
			if (localDate.getDayOfWeek().getValue() <= i) {
				if (j < 10) {
					System.out.print(j + "  ");
				} else {
					System.out.print(j + " ");
				}
				j++;
			} else {
				System.out.print("   ");
			}
			
			if (i % 7 == 0) {
				System.out.println();
			}
		}
		
	}
}
