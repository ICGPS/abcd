package exam02;

import java.time.LocalDate;
import java.util.Scanner;

public class CalendarPractice {

    public static void main(String[] args) {
        // 년도와 월을 입력받아 출력하기 때문에, Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);

        // 년도 입력 전, 년도를 입력하라는 메세지 출력
        System.out.print("년도를 입력하세요: ");
        int year = scanner.nextInt();

        // 월 입력 전, 월을 입력하라는 메세지 출력
        System.out.print("월을 입력하세요: ");
        int month = scanner.nextInt();

        // 입력 받은 년도와 월로 LocalDate 객체 생성
        // LocalDate.of(year, month, dayOfMonth)는 년도, 월, 해당 월의 1일 지정
        LocalDate date = LocalDate.of(year, month, 1);

        // 해당 월의 첫날의 요일을 가져옴 (1: 월요일, 2: 화요일, ..., 7: 일요일)
        // LocalDate 객체에서 해당 날짜의 요일을 나타내는 DayOfWeek 열거형(상수 집합 : 월 ~ 일)을 얻어옵니다
        // get.value로 매핑된 숫자 값을 가져온다.
        int firstDayOfWeek = date.getDayOfWeek().getValue();

        // 해당 월의 마지막 날짜를 가져옴
        // 길이를 뜻하기 때문에, 마지막 날짜가 나타난다.
        int lastDayOfMonth = date.lengthOfMonth();

        // 달력 출력, firstDayOfWeek와 lastDayOfMonth를 매개변수로 가지는 printCalendar 메소드 호출
        printCalendar(firstDayOfWeek, lastDayOfMonth);

        scanner.close();
    }


    // int 데이터 타입의 firstDayOfWeek와 int 데이터 타입의 lastDayOfMonth를 매개 변수로 갖는 printCalendar 메소드 정의
    private static void printCalendar(int firstDayOfWeek, int lastDayOfMonth) {
        System.out.println("일  월  화  수  목  금  토");

        // 요일과 일자를 맞추기 위해, 첫째 주에서 시작 요일까지 빈 공간 출력
        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print("   ");
        }

        // 달의 마지막 일자까지 루프를 돌면서 날짜 출력
        // %2d는 정수를 2자리로 표시
        for (int day = 1; day <= lastDayOfMonth; day++) {
            System.out.printf("%2d ", day);

            // 토요일마다 줄바꿈, 월(1), 화(2), 수(3), 목(4), 금(5), 토(6), 일(7)
            if ((firstDayOfWeek + day - 1) % 7 == 0) {
                System.out.println();
            }
        }

        System.out.println(); // 마지막 줄바꿈
    }
}