package midterm.exception;

public class NotValidateInputException extends RuntimeException {

	public String msg;
	
	NotValidateInputException(String inputType){
		this.msg = "Error : 입력된 " + inputType +" 이(가) 유효하지 않습니다.";
	}
}
