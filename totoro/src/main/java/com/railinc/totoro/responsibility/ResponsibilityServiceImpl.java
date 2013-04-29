package com.railinc.totoro.responsibility;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.util.PagedCollection;

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
		
		if (criteria.getSourceSystem() != null) {
			c.add(Restrictions.eq("sourceSystem", criteria.getSourceSystem()));
		}
		
		
		c.setProjection(Projections.rowCount());
		Long count = (Long) c.uniqueResult();
		
		c.setProjection(null);
		c.setFirstResult(criteria.getPage() * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
		
		
//		if (isNotBlank(filter)) {
//			filter = "%" + filter + "%";
//			c.add(Restrictions.or(
//					Restrictions.ilike(ResponsiblePerson.PROPERTY_ID, filter),Restrictions.ilike(ResponsiblePerson.PROPERTY_NAME, filter))
//			);
//		}
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		c.addOrder(Order.asc(ResponsiblePerson.PROPERTY_ID));
		
		PagedCollection<Responsibility> paged = new PagedCollection<Responsibility>(c.list());
		paged.getPaging().setPage(criteria.getPage());
		paged.getPaging().setPageSize(criteria.getPageSize());
		paged.getPaging().setTotalCount(count.intValue());
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
	public Responsibility get(String id) {
		return (Responsibility) sessionFactory.getCurrentSession().get(Responsibility.class, id);
	}

	@Override
	public void undelete(Responsibility ss) {
		ss.undelete();
		sessionFactory.getCurrentSession().update(ss);
	}
}
