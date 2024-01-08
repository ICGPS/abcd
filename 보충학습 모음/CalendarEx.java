import java.time.LocalDate;
import java.util.Scanner;
public class CalendarEx {
    public static void main(String[] args) {
        int year;
        int month;
        int date = 1;
        int index = 0; // 달력 출력 시 줄바꿈을 위한 인덱스

        Scanner sc = new Scanner(System.in);

        System.out.println("[달력 출력 프로그램]");
        System.out.print("달력의 년도를 입력해 주세요.(yyyy): ");
        year = sc.nextInt();
        System.out.print("달력의 월을 입력해 주세요.(mm): ");
        month = sc.nextInt();

        System.out.println("");

        LocalDate myDate = LocalDate.of(year, month, date); //년,월,일
        int dayValue = myDate.getDayOfWeek().getValue(); // 요일을 숫자로 반환
        int lastDay = myDate.lengthOfMonth(); // 해당월의 마지막 일

        System.out.printf("[%d년 %02d월]\n", year, month);
        System.out.println("일\t월\t화\t수\t목\t금\t토");

        for(int i = 0; i < dayValue; i++){      // 달력에서 공백 만드는 반복문
            System.out.print(" \t");
            index += 1;
        }
        for(int i = 1; i < lastDay + 1; i++){   // 날짜 채우는 반복문
            System.out.printf("%02d\t", i);
            index += 1;
            if(index % 7 == 0)
                System.out.println("");
        }
    }
}