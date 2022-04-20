package midterm.exception;

enum Regex {
	ID("^[A-Za-z[0-9]]{1,10}$"),
	PASSWORD("^(?=.*[A-Za-z])(?=.*[0-9])"
			+ "(?=.*[!@#$%^&*?,./\\\\\\\\<>|_-[+]=\\\\`~\\\\(\\\\)\\\\[\\\\]\\\\{\\\\}])"
			+ "[A-Za-z[0-9]!@#$%^&*?,./\\\\\\\\<>|_-[+]=\\\\`~\\\\(\\\\)\\\\[\\\\]\\\\{\\\\}]"
			+ "{8,20}$"),
	NAME("^[ㄱ-ㅎ가-힣]{1,5}$"),
	EMAIL("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"),
	PHONENUMBER("^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$");
	
	private final String pattern;
	
	Regex(String pattern) {
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return pattern;
	}
}
