package com.railinc.totoro.domain.seed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.sourcesystem.SourceSystemService;
import com.railinc.totoro.usergroup.UserGroupService;

@Component
@Profile("seed")
public class SeedData implements InitializingBean {
	
	@Autowired
	SourceSystemService sourceSystems;
	
	@Autowired
	UserGroupService userGroups;

	private final Set<SourceSystem> ss = new HashSet<SourceSystem>();
	
	public SeedData() {
		add(sourceSystem("SSO","Single Sign On"));
		add(sourceSystem("CRM","Customer Relationship Management"));
	}
	
	private void add(SourceSystem sourceSystem) {
		ss.add(sourceSystem);
	}

	private SourceSystem sourceSystem(String id, String name) {
		return new SourceSystem(id,name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for (SourceSystem s : ss) {
			SourceSystem x = sourceSystems.get(s.getIdentifier());
			
			if (x == null) {
				sourceSystems.save(s);
			}
		}
	}
	
}
