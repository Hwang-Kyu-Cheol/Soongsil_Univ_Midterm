package midterm.repository;

import java.util.LinkedList;
import java.util.Optional;

import org.springframework.stereotype.Component;

import midterm.domain.User;

@Component
public class UserRepository {

	private static LinkedList<User> users = new LinkedList<>();
	private static int index = 0;
	
	//생성
	public User create(User user) {
		user.setIndex(++index);
		users.add(user);
		return user;
	}
	
	//id로 조회
	public Optional<User> findById(String id) {
		return users
				.stream()
				.filter(user -> user.getId().equals(id))
				.findFirst();	
	}
	
	//index로 조회
	public Optional<User> findByIndex(int index) {
		return users
				.stream()
				.filter(user -> user.getIndex() == index)
				.findFirst();
	}
	
	//수정
	public void update(String query, int index, String content) {
		switch(query) {
		case "password":
			users.stream()
		     .forEach(user -> {
		    	 if(user.getIndex() == index) {
		    		 user.setPassword(content);
		    		 return;
		    	 }
		     });
			break;
		case "name":
			users.stream()
		     .forEach(user -> {
		    	 if(user.getIndex() == index) {
		    		 user.setName(content);
		    		 return;
		    	 }
		     });
			break;
		case "email":
			users.stream()
		     .forEach(user -> {
		    	 if(user.getIndex() == index) {
		    		 user.setEmail(content);
		    		 return;
		    	 }
		     });
			break;
		case "phoneNumber":
			users.stream()
		     .forEach(user -> {
		    	 if(user.getIndex() == index) {
		    		 user.setPhoneNumber(content);
		    		 return;
		    	 }
		     });
			break;
		}
		return;
	}
	
	//삭제
	public void delete(int index) {
		users.removeIf(user -> user.getIndex() == index);
	}
}
