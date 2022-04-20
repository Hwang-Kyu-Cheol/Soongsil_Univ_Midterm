package midterm.controller;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import midterm.domain.User;
import midterm.exception.AlreadyExistedIdException;
import midterm.exception.IncorrectIdOrPasswordException;
import midterm.exception.NotValidateInputException;
import midterm.exception.UnauthorizedException;
import midterm.exception.Validator;
import midterm.form.SignUpForm;
import midterm.service.UserService;

@Component
public class UserController {

	private final UserService userService;
	private final Validator validator;
	private final Scanner scanner;
	
	//자동 의존 주입
	@Autowired
	public UserController(UserService userService, Validator validator, Scanner scanner) {
		this.userService = userService;
		this.validator = validator;
		this.scanner = scanner;
	}

	//회원가입
	public void signUp() {
		SignUpForm form = new SignUpForm();
		
		//1. 아이디
		while(true) {
			System.out.println("1. 아이디를 입력하세요.");
			System.out.println("(아이디는 1~10글자 이내이며 영어 또는 숫자로만 이루어져야 합니다.)");
			
			form.id = scanner.nextLine();
			try {
				validator.isValidate("id", form.id);
				userService.isNotExistId(form.id);
			} catch(NotValidateInputException e) {
				System.err.println(e.msg);
				continue;
			} catch(AlreadyExistedIdException e) {
				System.err.println(e.msg);
				continue;
			}
			break;
		}
		
		//2. 비밀번호
		while(true) {
			System.out.println("2. 비밀번호를 입력하세요.");
			System.out.println("(비밀번호는 6~16글자 이내이며 영어, 숫자, 특수문자의 조합으로만 이루어져야 합니다.)");
			
			form.password = scanner.nextLine();
			try {
				validator.isValidate("password", form.password);
			} catch(NotValidateInputException e) {
				System.err.println(e.msg);
				continue;
			} 
			break;
		}
		
		//3. 이름
		while(true) {
			System.out.println("3. 이름을 입력하세요.");
			System.out.println("(이름은 1~5글자 이내이며 한글로만 이루어져야합니다.)");
			
			form.name = scanner.nextLine();
			try {
				validator.isValidate("name", form.name);
			} catch(NotValidateInputException e) {
				System.err.println(e.msg);
				continue;
			} 
			break;
		}
		
		//4. 이메일
		while(true) {
			System.out.println("4. 이메일을 입력하세요.");
			
			form.email = scanner.nextLine();
			try {
				validator.isValidate("email", form.email);
			} catch(NotValidateInputException e) {
				System.err.println(e.msg);
				continue;
			} 
			break;
		}
		
		//5. 휴대폰 전화번호
		while(true) {
			System.out.println("5. 휴대폰 전화번호를 입력하세요.");
			
			form.phoneNumber = scanner.nextLine();
			try {
				validator.isValidate("phoneNumber", form.phoneNumber);
			} catch(NotValidateInputException e) {
				System.err.println(e.msg);
				continue;
			} 
			break;
		}
		
		User user = new User();
		user.setId(form.id);
		user.setPassword(form.password);
		user.setName(form.name);
		user.setEmail(form.email);
		user.setPhoneNumber(form.phoneNumber);
		
		userService.signUp(user);
	}
	
	//로그인
	public int logIn() {
		String id = "";
		String password = "";
		int userIndex;
		
		while(true) {
			System.out.println("아이디를 입력하세요.");
			id = scanner.nextLine();
			System.out.println("비밀번호를 입력하세요.");
			password = scanner.nextLine();
			
			try {
				userIndex = userService.logIn(id, password);
			} catch(IncorrectIdOrPasswordException e) {
				System.err.println(e.msg);
				continue;
			}
			break;
		}
		
		return userIndex;
	}
	
	//로그인한 회원 이름 표시
	public void displayUserName(int index) throws UnauthorizedException{
		
		try {
			userService.authorized(index);
		} catch(UnauthorizedException e) {
			System.err.println(e.msg);
			throw new UnauthorizedException();
		}
		
		String userName = userService.getUserInfo(index).getName();
		
		System.out.println("********************  \n");
		System.out.println(String.format("%s 님 안녕하세요! \n", userName));
		System.out.println("********************  \n");
	}
	
	//비밀번호 변경
	public void changePassword(int index) throws UnauthorizedException {
		
		try {
			userService.authorized(index);
		} catch(UnauthorizedException e) {
			System.err.println(e.msg);
			throw new UnauthorizedException();
		}
		
		String userPassword = userService.getUserInfo(index).getPassword();
		
		String password1 = "";
		String password2 = "";
		while(true) {
			while(true) {
				System.out.println("변경할 비밀번호를 입력하세요.");
				password1 = scanner.nextLine();
				try {
					validator.isValidate("password", password1);
				} catch(NotValidateInputException e) {
					System.err.println(e.msg);
					continue;
				}

				System.out.println("변경할 비밀번호를 한번 더 입력하세요.");
				password2 = scanner.nextLine();
				try {
					validator.isValidate("password", password2);
				} catch(NotValidateInputException e) {
					System.err.println(e.msg);
					continue;
				}
				
				break;
			}
			
			if(password1.equals(password2)) {
				if(!password1.equals(userPassword)) {
					userService.changePassword(index, password1);
					break;
				}
				System.err.println("기존과 동일한 비밀번호로 변경할 수 없습니다.");
			} else {
				System.err.println("비밀번호가 일치하지 않습니다.");
			}
		}
	}
	
	//회원정보 조회
	public void getUserInfo(int index) throws UnauthorizedException {
		
		try {
			userService.authorized(index);
		} catch(UnauthorizedException e) {
			System.err.println(e.msg);
			throw new UnauthorizedException();
		}
		
		User user = userService.getUserInfo(index);
		
		System.out.println(String.format("1. 아이디 : %s", user.getId()));
		System.out.println(String.format("2. 이름 : %s", user.getName()));
		System.out.println(String.format("3. 이메일 : %s", user.getEmail()));
		System.out.println(String.format("4. 휴대폰 전화번호 : %s", user.getPhoneNumber()));
	}
	
	//회원정보 수정
	public void updateUserInfo(int index) throws UnauthorizedException {
		
		try {
			userService.authorized(index);
		} catch(UnauthorizedException e) {
			System.err.println(e.msg);
			throw new UnauthorizedException();
		}
		
		String choiceNumberStr = "";
		int choiceNumber = 0;
		User user = userService.getUserInfo(index);
		
		while(true) {
			System.out.println("수정하고 싶은 정보를 선택하세요.");
			System.out.println("1. 이름  2. 이메일  3. 휴대폰 전화번호  4. 나가기");
			
			choiceNumberStr = scanner.nextLine();
			
			//입력 에러 핸들링
			try {
				choiceNumber = validator.isValidateChoiceNumber(4, choiceNumberStr);
			} catch(NotValidateInputException e) {
				System.err.println(e.msg);
				continue;
			}
			
			switch(choiceNumber) {
			case 1:
				String name = "";
				while(true) {
					System.out.println(String.format("기존 이름 : %s", user.getName()));
					System.out.println("바꾸고 싶은 이름을 입력하세요.");
					name = scanner.nextLine();
					
					try {
						validator.isValidate("name", name);
					} catch(NotValidateInputException e) {
						System.err.println(e.msg);
						continue;
					}
					break;
				}
				
				userService.changeName(index, name);
				System.out.println("이름 수정 완료!");
				break;
			case 2:
				String email = "";
				while(true) {
					System.out.println(String.format("기존 이메일 : %s", user.getEmail()));
					System.out.println("바꾸고 싶은 이메일을 입력하세요.");
					email = scanner.nextLine();
					
					try {
						validator.isValidate("email", email);
					} catch(NotValidateInputException e) {
						System.err.println(e.msg);
						continue;
					}
					break;
				}
				
				userService.changeEmail(index, email);
				System.out.println("이메일 수정 완료!");
				break;
			case 3:
				String phoneNumber = "";
				while(true) {
					System.out.println(String.format("기존 휴대폰 전화번호 : %s", user.getPhoneNumber()));
					System.out.println("바꾸고 싶은 휴대폰 전화번호을 입력하세요.");
					phoneNumber = scanner.nextLine();
					
					try {
						validator.isValidate("phoneNumber", phoneNumber);
					} catch(NotValidateInputException e) {
						System.err.println(e.msg);
						continue;
					}
					break;
				}
				
				userService.changePhoneNumber(index, phoneNumber);
				System.out.println("휴대폰 전화번호 수정 완료!");
				break;
			case 4:
				return;
			}
		}
	}
	
	//회원탈퇴
	public boolean withdraw(int index) throws UnauthorizedException {
		
		boolean doneWithdraw = false;
		
		try {
			userService.authorized(index);
		} catch(UnauthorizedException e) {
			System.err.println(e.msg);
			throw new UnauthorizedException();
		}
		
		String choiceNumberStr = "";
		int choiceNumber = 0;
		
		while(true) {
			System.out.println("정말 탈퇴하시겠습니까?");
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
		
		switch(choiceNumber) {
		case 1:
			userService.withdraw(index);
			doneWithdraw = true;
			break;
		case 2:
			break;
		}
		
		return doneWithdraw;
	}
}
