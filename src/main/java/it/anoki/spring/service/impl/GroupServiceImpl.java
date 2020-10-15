package it.anoki.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Group;
import it.anoki.spring.model.User;
import it.anoki.spring.repository.GroupRepository;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupRepository groupRepository;
	@Autowired
	UserRepository userRepository;

	
	@Override
	public Optional<Group> get(Long id) throws Exception {
		return groupRepository.findById(id);
	}

	@Override
	public Group save(Group c) throws Exception {
		return groupRepository.save(c);
	}

	@Override
	public void delete(Long id) throws Exception {
		groupRepository.deleteById(id);
	}

	@Override
	public Group update(Long id, String name,String desc) throws Exception {
		Group g=null;
		if(this.get(id).isPresent() && name!=null || desc!=null){
			g= this.get(id).get();
			if(desc != null)
				g.setDescription(desc);
			if(name != null)
				g.setName(name);
		}
		return groupRepository.save(g);
	}

	@Override
	public boolean addUser(Long idGroup, Long idUser) throws Exception {
		Optional<User> user= userRepository.findById(idUser);
		Optional<Group> group= groupRepository.findById(idGroup);
		if(group.isPresent() && user.isPresent()) {
			group.get().getUsers().add(user.get());
			this.save(group.get());
			return true;
		}
		return false;
	}
	
}
