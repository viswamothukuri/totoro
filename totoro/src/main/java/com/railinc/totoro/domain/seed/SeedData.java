package com.railinc.totoro.domain.seed;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.railinc.totoro.domain.Identity;
import com.railinc.totoro.domain.IdentityType;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.domain.RuleNumberType;
import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.responsibility.ResponsibilityCriteria;
import com.railinc.totoro.responsibility.ResponsibilityService;
import com.railinc.totoro.sourcesystem.SourceSystemService;
import com.railinc.totoro.usergroup.UserGroupService;

@Component
@Profile("seed")
public class SeedData implements InitializingBean {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	SourceSystemService sourceSystems;
	
	@Autowired
	UserGroupService userGroups;
	
	@Autowired
	ResponsibilityService responsibilityService;
	

	private final Set<SourceSystem> ss = new HashSet<SourceSystem>();
	private final Set<Responsibility> responsibilities = new HashSet<Responsibility>();
	
	public SeedData() {
		SourceSystem fur = add(sourceSystem("FUR","FindUs.Rail"));
		SourceSystem rapid = add(sourceSystem("RAPID","Rapid"));
		SourceSystem sso = add(sourceSystem("SSO","Single Sign On"));
		SourceSystem crm = add(sourceSystem("CRM","Customer Relationship Management"));
		add(responsibility(null,RuleNumberType.DEFAULT, null, null,IdentityType.SsoRole,"TOTORODATASTEWARD", 10000));
		add(responsibility(sso, RuleNumberType.DEFAULT, null, null, IdentityType.SsoId, "sdtxs01", 900));
		add(responsibility(sso, RuleNumberType.SINGLE, 1L, null, IdentityType.SsoId, "itvxm01", 600));
	}
	
	private void add(Responsibility responsibility) {
		responsibilities.add(responsibility);
	}

	private SourceSystem add(SourceSystem sourceSystem) {
		ss.add(sourceSystem);
		return sourceSystem;
	}

	private SourceSystem sourceSystem(String id, String name) {
		return new SourceSystem(id,name);
	}
	private Responsibility responsibility(SourceSystem sourceSystem, RuleNumberType ruleNumberType,Long ruleNumberFrom, Long ruleNumberThru, IdentityType identityType, String identityId, int precedence) {
		Responsibility r = new Responsibility();
		r.setResponsiblePerson(new Identity(identityType, identityId));
		r.setRuleNumberType(ruleNumberType);
		r.setRuleNumberFrom(ruleNumberFrom);
		r.setRuleNumberThru(ruleNumberThru);
		r.setSourceSystem(sourceSystem);
		r.setResponsiblePersonType(identityType);
		r.setResponsiblePersonId(identityId);
		r.setPrecedence(precedence);
		return r;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			for (SourceSystem s : ss) {
				SourceSystem x = sourceSystems.get(s.getIdentifier());
				if (x == null) {
					logger.info("saving {}", s);
					sourceSystems.save(s);
				}
			}
			for (Responsibility s : responsibilities) {
				if (s.getSourceSystem() != null) {
					s.setSourceSystem(sourceSystems.get(s.getSourceSystem().getIdentifier()));
				}
				ResponsibilityCriteria queryByExample = new ResponsibilityCriteria(s);
				Responsibility x = Iterables.getFirst( responsibilityService.all(queryByExample), null);
				if (x == null) {
					logger.info("saving {}", s);
					responsibilityService.save(s);
				}
			}
		} catch (Exception e) {
			logger.error("Error during afterPropertiesSet", e);
		}
	}
	
}
