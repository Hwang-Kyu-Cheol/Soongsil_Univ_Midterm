package midterm;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import midterm.form.SignUpForm;
import midterm.service.UserService;
import midterm.controller.UserController;
import midterm.domain.User;
import midterm.exception.AlreadyExistedIdException;
import midterm.exception.IncorrectIdOrPasswordException;
import midterm.exception.NotValidateInputException;
import midterm.exception.UnauthorizedException;
import midterm.exception.Validator;

public class Main {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
			
		UserController userController = ctx.getBean("userController", UserController.class);
		Validator validator = ctx.getBean("validator", Validator.class);
		Scanner scanner = ctx.getBean("scanner", Scanner.class);
			
		//프로그램 필요 변수
		int logInUserIndex = 0;
		boolean isLogIn = false;		
		String choiceNumberStr = "";
		int choiceNumber = 0;
		
		System.out.println("회원관리 시스템에 오신것을 환영합니다!\n");
		
		while(true) {
			//로그인이 안되어있을 경우
			while(!isLogIn) {
				
				System.out.println("원하시는 항목을 선택하세요.");
				System.out.println("1. 회원가입  2. 로그인  3. 종료");
				
				choiceNumberStr = scanner.nextLine();
				//입력 에러 핸들링
				try {
					choiceNumber = validator.isValidateChoiceNumber(3, choiceNumberStr);
				} catch(NotValidateInputException e) {
					System.err.println(e.msg);
					continue;
				}
				
				switch(choiceNumber) {
				//회원가입
				case 1:
					System.out.println("===== 회원가입 =====");
					
					userController.signUp();
					
					System.out.println("회원가입 완료! \n");
					
					break;
				//로그인
				case 2:
					System.out.println("===== 로그인 =====");
					
					logInUserIndex = userController.logIn();
					isLogIn = true;
					
					System.out.println("로그인 완료! \n");
					System.out.println("--------------------  \n");
					break;
				//프로그램 종료
				case 3:
					System.out.println("이용해주셔서 감사합니다. 프로그램을 종료합니다.");
					return;
				}	
			}
			//로그인이 되어있는 경우
			while(isLogIn) {
				
				try {
					userController.displayUserName(logInUserIndex);
				} catch(UnauthorizedException e) {
					return;
				}
				
				System.out.println("원하시는 항목을 선택하세요.");
				System.out.println("1. 비밀번호 변경  2. 회원정보 조회  3. 회원정보 수정  4. 회원 탈퇴  5. 로그아웃  6. 종료");
				
				choiceNumberStr = scanner.nextLine();
				//입력 에러 핸들링
				try {
					choiceNumber = validator.isValidateChoiceNumber(6, choiceNumberStr);
				} catch(NotValidateInputException e) {
					System.err.println(e.msg);
					continue;
				}
				
				switch(choiceNumber) {
				//비밀번호 변경
				case 1:
					System.out.println("===== 비밀번호 변경 =====");
					
					try {
						userController.changePassword(logInUserIndex);
					} catch(UnauthorizedException e) {
						return;
					}
					
					System.out.println("비밀번호 변경 완료! \n");
					break;
				//회원정보 조회
				case 2:
					System.out.println("===== 회원정보 조회 =====");
					
					try {
						userController.getUserInfo(logInUserIndex);
					} catch(UnauthorizedException e) {
						return;
					}
					
					System.out.println("회원정보 조회 완료! \n");
					break;
				//회원정보 수정
				case 3:
					System.out.println("===== 회원정보 수정 =====");
					
					try {
						userController.updateUserInfo(logInUserIndex);
					}catch(UnauthorizedException e) {
						return;
					}
					
					System.out.println("회원정보 수정 완료! \n");
					break;
				//회원 탈퇴
				case 4:
					System.out.println("===== 회원 탈퇴 =====");
					
					boolean doneWithdraw = false;
					try {
						doneWithdraw = userController.withdraw(logInUserIndex);
					} catch(UnauthorizedException e) {
						return;
					}
					
					if(doneWithdraw) {
						System.out.println("회원 탈퇴 완료! \n");
						logInUserIndex = 0;
						isLogIn = false;
					}
					break;
				//로그아웃
				case 5:
					System.out.println("===== 로그아웃 =====");
					
					while(true) {
						System.out.println("정말 로그아웃 하시겠습니까?");
						System.out.println("1. 예  2. 아니오");
						
						choiceNumberStr = scanner.nextLine();
						
						//입력 에러 핸들링
						try {
							choiceNumber = validator.isValidateChoiceNumber(2, choiceNumberStr);
						} catch(NotValidateInputException e) {
							System.err.println(e.msg);
							continue;
						}
						break;
					}
					
					if(choiceNumber == 1) {
						System.out.println("로그아웃 완료! \n");
						logInUserIndex = 0;
						isLogIn = false;
					}
					break;
				//프로그램 종료
				case 6:
					System.out.println("이용해주셔서 감사합니다. 프로그램을 종료합니다.");
					return;
				}
				break;
			}
		}	
	}	
}
