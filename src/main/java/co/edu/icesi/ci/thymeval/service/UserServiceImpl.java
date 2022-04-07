package co.edu.icesi.ci.thymeval.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.UserType;
import co.edu.icesi.ci.thymeval.model.UserGender;
import co.edu.icesi.ci.thymeval.repository.UserRepository;
import co.edu.icesi.ci.thymeval.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void save(UserApp user) {
//		if(user.getNewPassword()== null) {
//			user.setPassword(findById(user.getId()).get().getPassword());
//			save(user);
//		}else {
//			if(findById(user.getId()).get().getPassword().equals(user.getOldPassword())) {
//				Optional<User> user1 = findById(user.getId());
//		        user1.get().setOldPassword(user.getOldPassword());
//				user1.get().setPassword(user.getNewPassword());
//				
//				System.out.println(user1.get().getPassword());
//				
//			save(user);
//				
//			}
		userRepository.save(user);
		//}
	}

	public Optional<UserApp> findById(long id) {

		return userRepository.findById(id);
	}

	public Iterable<UserApp> findAll() {
		return userRepository.findAll();
	}
	
	public Iterable<UserApp> findAllPatients() {
		return userRepository.findByType(UserType.patient);
	}
	
	public Iterable<UserApp> findAllDoctors() {
		return userRepository.findByType(UserType.doctor);
	}


	public void delete(UserApp user) {
		userRepository.delete(user);

	}

	public UserGender[] getGenders() {
		
		return UserGender.values();
	}

	public UserType[] getTypes() {
		// TODO Auto-generated method stub
		return UserType.values();
	}
}
