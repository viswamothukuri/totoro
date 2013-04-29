package com.railinc.totoro.usergroup;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railinc.totoro.domain.UserGroup;
import com.railinc.totoro.domain.UserGroupMember;

@Service
@Transactional
public interface UserGroupService {
	Collection<UserGroup> all(String filter);
	void save(UserGroup s);
	void delete(UserGroup s);
	UserGroup get(String id);
	void undelete(UserGroup ss);
}
