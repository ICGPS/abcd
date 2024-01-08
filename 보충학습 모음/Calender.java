import java.time.LocalDate;
import java.util.Scanner;

public class Calender {

    public static void Calender(int year, int month) {

    	int date = 1;
    	// 해당월 1일 설정
    	LocalDate firstDate = LocalDate.of(year, month, date); 

    	int day;        
        day = firstDate.getDayOfWeek().getValue();
        System.out.println("일\t월\t화\t수\t목\t금\t토\t");

        // 1일 전까지의 공백 생성
        for (int i = 0; i < day % 7; i++) {
            System.out.print("\t");
        }

        // 달력 일 출력
        for(int i = 1; i <= firstDate.lengthOfMonth(); i++) {
            System.out.printf("%02d\t", date++);
            day++;
            
         // 다음 주로 줄 바꿈
            if(day % 7 == 0) {
                System.out.println();
            }
        }
    }
    

    public static void main(String[] args) {
        int year;
        int month;
        
        //스캐너 객체 생성
        Scanner scanner = new Scanner(System.in);

        //입력 반복문 실행
        while (true) {
            System.out.print("년도를 입력하세요(4자리) : ");
            String yearNum = scanner.next();
                
            System.out.print("월을 입력하세요(1-2자리) : ");
            String monthNum = scanner.next();

            System.out.println();
        
            //입력이 숫자가 아닐경우 예외처리
            try {
                year = Integer.parseInt(yearNum);
                month = Integer.parseInt(monthNum);

                // 년도는 4자리, 월은 1자리 또는 2자리여야 함 둘다 음수가 아니여야함
                if (yearNum.length() == 4 && monthNum.length() >= 1 && monthNum.length() <= 2
                        && month >= 1 && month <= 12 && year >= 0) {
                    break;
                } else {
                    System.out.println("입력이 잘못되었습니다.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요\n");
            }
        }
        
        Calender(year, month);
    }
}
