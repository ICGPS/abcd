package ex;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class calendar {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // 연도와 월 입력받음
        System.out.print("연도 : ");
        int year = scanner.nextInt();
        System.out.print("월 : ");
        int month = scanner.nextInt();

        // 해당 연도-월에 첫번째 날
        LocalDate date = LocalDate.of(year, month, 1);

        // 첫번째 날의 요일은?
//        System.out.println(date.getDayOfWeek());
        int dayOfWeek = date.getDayOfWeek().ordinal();
        // 몇번째인가? (-> 달력 요일표시)
        String[] dows = { "일", "월", "화", "수", "목", "금", "토" };

        System.out.println(year + "년" + month + "월 달력");

        // 상단 요일 출력
        for (String dow :  dows) {
            System.out.print("\t" + dow + "\t");
        }
        System.out.println();

        // 1일의 요일부터 출력하도록
//        System.out.println(date.getMonth().maxLength());  // 월의 마지막 일

        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {

        } else {
            for (int j = 0; j < dayOfWeek + 1; j++) {
                System.out.print("\t  \t");
            }
        }

        for (int i = 1; i < date.getMonth().maxLength() + 1; i++) {
            // 일요일이면 다음 줄로 이동 후 출력
            if (LocalDate.of(year, month, i).getDayOfWeek() == DayOfWeek.SUNDAY && date.getDayOfMonth() == 1) {
                //  1일은 예외
                if(LocalDate.of(year, month, i).equals(date)) {

                } else {
                    System.out.println();
                }
            }
            System.out.printf("\t%s\t", i); //
        }
    }

/*
연도 : 2024
월 : 3
2024년3월 달력
	일		월		화		수		목		금		토
	  		  		  		  		  		1		2
	3		 4		5 		6		7		8		9
	10		11		12		13		14		15		16
	17		18		19		20		21		22		23
	24		25		26		27		28		29		30
	31
*/
}
