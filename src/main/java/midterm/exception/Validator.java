package midterm.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Validator {
	
	//항목을 선택하는 입력이 유효한지 판단하는 함수
	public int isValidateChoiceNumber(int maxNum, String choiceNumberStr) throws NotValidateInputException {
		int choiceNumber = 0;
		
		try {
			choiceNumber = Integer.parseInt(choiceNumberStr);	
		} catch(Exception e) {
			throw new NotValidateInputException("숫자");
		}
		
		if(!(choiceNumber >=1 && choiceNumber <= maxNum)) {
			throw new NotValidateInputException("숫자");
		}
		
		return choiceNumber;
	}
	
	//아이디, 비밀번호, 이름, 이메일, 휴대폰전화번호 입력이 유효한지 판단하는 함수
	public void isValidate(String query, String content) throws NotValidateInputException {
		String pattern = "";
		String inputType = "";
		
		switch(query) {
		case "id":
			pattern = Regex.ID.getPattern();
			inputType = "아이디";
			break;
		case "password":
			pattern = Regex.PASSWORD.getPattern();
			inputType = "비밀번호";
			break;
		case "name":
			pattern = Regex.NAME.getPattern();
			inputType = "이름";
			break;
		case "email":
			pattern = Regex.EMAIL.getPattern();
			inputType = "이메일";
			break;
		case "phoneNumber":
			pattern = Regex.PHONENUMBER.getPattern();
			inputType = "휴대폰 전화번호";
			break;
		}
		
		Matcher match = Pattern.compile(pattern).matcher(content);
		if(!match.find()) {
			throw new NotValidateInputException(inputType);
		}
		return;
	}
}
