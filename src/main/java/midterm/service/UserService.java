package midterm.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import midterm.domain.User;
import midterm.exception.AlreadyExistedIdException;
import midterm.exception.IncorrectIdOrPasswordException;
import midterm.exception.UnauthorizedException;
import midterm.repository.UserRepository;

@Component
public class UserService {
	
	private final UserRepository userRepository;
	
	//자동 의존 주입
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//회원가입
	public void signUp(User user) {
		userRepository.create(user);
		return;
	}
	
	//중복아이디 체크
	public void isNotExistId(String id) throws AlreadyExistedIdException {
		if(userRepository.findById(id).isPresent()) {
			throw new AlreadyExistedIdException();
		}
		return;
	}
	
	//로그인
	public int logIn(String id, String password) throws IncorrectIdOrPasswordException {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isPresent() && user.get().getPassword().equals(password)) {
			return user.get().getIndex();
		} else {
			throw new IncorrectIdOrPasswordException();
		}
	}
	
	//회원 인증
	public void authorized(int index) throws UnauthorizedException {
		Optional<User> user = userRepository.findByIndex(index);
		if(user.isPresent()) {
			return;
		} else {
			throw new UnauthorizedException();
		}
		
	}
	
	//비밀번호 변경
	public void changePassword(int index, String password) {
		userRepository.update("password", index, password);
		return;
	}
	
	//이름 변경
	public void changeName(int index, String name) {
		userRepository.update("name", index, name);
		return;
	}
	
	//이메일 변경
	public void changeEmail(int index, String email) {
		userRepository.update("email", index, email);
		return;
	}
	
	//휴대폰 전화번호 변경
	public void changePhoneNumber(int index, String phoneNumber) {
		userRepository.update("phoneNumber", index, phoneNumber);
		return;
	}
	
	//회원정보 조회
	public User getUserInfo(int index) throws UnauthorizedException {
		Optional<User> user = userRepository.findByIndex(index);
		if(user.isPresent()) {
			return user.get();
		} else {
			throw new UnauthorizedException();
		}
	}
	
	//회원탈퇴
	public void withdraw(int index) {
		userRepository.delete(index);
	}
}
