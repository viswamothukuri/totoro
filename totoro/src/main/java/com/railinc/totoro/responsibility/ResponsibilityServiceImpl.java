package com.railinc.totoro.responsibility;

import static com.google.common.base.Predicates.and;
import static com.railinc.totoro.domain.Responsibility.isRuleNumberMatch;
import static com.railinc.totoro.domain.Responsibility.isSourceSystemMatch;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.railinc.totoro.domain.DataException;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.domain.YesNo;
import com.railinc.totoro.util.PagedCollection;
import com.railinc.totoro.util.PagingResults;

@Service
@Transactional
public class ResponsibilityServiceImpl implements ResponsibilityService {
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public PagedCollection<Responsibility> all(ResponsibilityCriteria criteria) {
		criteria = Optional.fromNullable(criteria).or(new ResponsibilityCriteria());
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Responsibility.class);

		
		if (criteria.getFreeText().isSpecifiedAndNotNull()) {
			String ft = criteria.getFreeText().value();
			
			c.add(
				//Restrictions.or(
						Restrictions.ilike(Responsibility.PROPERTY_RESPONSIBLE_PERSON_ID, ft, MatchMode.ANYWHERE)
						//Restrictions.and(
						//		Restrictions.ge(Responsibility.PROPERTY_RULENUMBER_FROM, ft), 
						//		Restrictions.le(Responsibility.PROPERTY_RULENUMBER_THRU, ft))
						//Restrictions.ilike(Responsibility.PROPERTY_RULENUMBER, ft, MatchMode.ANYWHERE)
				//)
			);
		}

		
		if (criteria.getPerson().isSpecifiedAndNotNull()) {
			c.add(Restrictions.eq(Responsibility.PROPERTY_RESPONSIBLE_PERSON_ID, criteria.getPerson().value()));
		} else if (criteria.getPerson().isSpecifiedAndNull()) {
			c.add(Restrictions.isNull(Responsibility.PROPERTY_RESPONSIBLE_PERSON_ID));
		}

		if (criteria.getPersonType().isSpecifiedAndNotNull()) {
			c.add(Restrictions.eq(Responsibility.PROPERTY_RESPONSIBLE_PERSON_TYPE, criteria.getPersonType().value()));
		} else if (criteria.getPersonType().isSpecifiedAndNull()) {
			c.add(Restrictions.isNull(Responsibility.PROPERTY_RESPONSIBLE_PERSON_TYPE));
		}
		
		if (criteria.getRuleNumberType().isSpecifiedAndNotNull()) {
			c.add(Restrictions.eq(Responsibility.PROPERTY_RULENUMBER_TYPE, criteria.getRuleNumberType().value()));
		} else if (criteria.getRuleNumberFrom().isSpecifiedAndNull()) {
			c.add(Restrictions.isNull(Responsibility.PROPERTY_RULENUMBER_TYPE));
		}
		
		if (criteria.getRuleNumberFrom().isSpecifiedAndNotNull()) {
			c.add(Restrictions.eq(Responsibility.PROPERTY_RULENUMBER_FROM, criteria.getRuleNumberFrom().value()));
		} else if (criteria.getRuleNumberFrom().isSpecifiedAndNull()) {
			c.add(Restrictions.isNull(Responsibility.PROPERTY_RULENUMBER_FROM));
		}
		
		if (criteria.getRuleNumberThru().isSpecifiedAndNotNull()) {
			c.add(Restrictions.eq(Responsibility.PROPERTY_RULENUMBER_THRU, criteria.getRuleNumberThru().value()));
		} else if (criteria.getRuleNumberFrom().isSpecifiedAndNull()) {
			c.add(Restrictions.isNull(Responsibility.PROPERTY_RULENUMBER_THRU));
		}

		if (criteria.getSourceSystem().isSpecifiedAndNotNull()) {
			c.add(Restrictions.eq(Responsibility.PROPERTY_SOURCESYSTEM, criteria.getSourceSystem().value()));
		} else if (criteria.getSourceSystem().isSpecifiedAndNull()) {
			c.add(Restrictions.isNull(Responsibility.PROPERTY_SOURCESYSTEM));
		}

		c.setProjection(Projections.rowCount());
		int count = ((Long) c.uniqueResult()).intValue();
		
		c.setProjection(null);
		c.setFirstResult(criteria.getPage() * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
		
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		PagedCollection<Responsibility> paged = new PagedCollection<Responsibility>(c.list(), new PagingResults(criteria, count));
		return paged;
	}

	@Override
	@Transactional
	public void save(Responsibility s) {
		sessionFactory.getCurrentSession().saveOrUpdate(s);
	}

	@Override
	@Transactional
	public void delete(Responsibility s) {
		s.delete();
		sessionFactory.getCurrentSession().update(s);
	}

	@Override
	@Transactional
	public Responsibility get(Long id) {
		return (Responsibility) sessionFactory.getCurrentSession().get(Responsibility.class, id);
	}
	
	@Override
	public Responsibility getResponsibility(DataException data){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Responsibility.class);
		criteria.add(Restrictions.eq(Responsibility.PROPERTY_DELETED, YesNo.N.name()));
		
		@SuppressWarnings("unchecked")
		List<Responsibility> responsibilityList = (List<Responsibility>)criteria.list();
		
		Collections.sort(responsibilityList);
		
		for(Responsibility responsibility: responsibilityList){
			if(and(isSourceSystemMatch(data.getSourceSystem()), isRuleNumberMatch(data.getRuleNumber())).apply(responsibility)){
				return responsibility;
			}
		}
		return null;
	}
	
	@Override
	public void undelete(Responsibility ss) {
		ss.undelete();
		sessionFactory.getCurrentSession().update(ss);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
