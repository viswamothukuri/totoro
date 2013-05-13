package com.railinc.totoro.responsibility;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.railinc.totoro.domain.Responsibility;
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
				Restrictions.or(
						Restrictions.ilike(Responsibility.PROPERTY_RESPONSIBLE_PERSON_ID, ft, MatchMode.ANYWHERE),
						Restrictions.ilike(Responsibility.PROPERTY_RULENUMBER, ft, MatchMode.ANYWHERE)
				)
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

		if (criteria.getRuleNumber().isSpecifiedAndNotNull()) {
			c.add(Restrictions.eq(Responsibility.PROPERTY_RULENUMBER, criteria.getRuleNumber().value()));
		} else if (criteria.getRuleNumber().isSpecifiedAndNull()) {
			c.add(Restrictions.isNull(Responsibility.PROPERTY_RULENUMBER));
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
	public void undelete(Responsibility ss) {
		ss.undelete();
		sessionFactory.getCurrentSession().update(ss);
	}
}
