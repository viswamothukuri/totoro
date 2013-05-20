package com.railinc.totoro.sourcesystem;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.domain.YesNo;

@Service
@Transactional
public class SourceSystemServiceImpl implements SourceSystemService {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public Collection<SourceSystem> active() {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(SourceSystem.class);
		c.add(Restrictions.eq(SourceSystem.PROPERTY_DELETED, YesNo.N));
		c.addOrder(Order.asc(SourceSystem.DEFAULT_ORDER_BY_PROPERTY));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<SourceSystem> all(String filter) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(SourceSystem.class);
		if (isNotBlank(filter)) {
			filter = "%" + filter + "%";
			c.add(Restrictions.or(
					Restrictions.ilike(SourceSystem.PROPERTY_OUTBOUND_QUEUE, filter, MatchMode.ANYWHERE),
					Restrictions.or(
						Restrictions.ilike(SourceSystem.PROPERTY_ID, filter, MatchMode.ANYWHERE),
						Restrictions.ilike(SourceSystem.PROPERTY_NAME, filter, MatchMode.ANYWHERE)
					)
				));
			
		}
		c.addOrder(Order.asc(SourceSystem.PROPERTY_ID));
		
		return c.list();
			
	}

	@Override
	@Transactional
	public void save(SourceSystem s) {
		sessionFactory.getCurrentSession().saveOrUpdate(s);
	}

	@Override
	@Transactional
	public void delete(SourceSystem s) {
		s.delete();
		sessionFactory.getCurrentSession().update(s);
	}

	@Override
	@Transactional
	public SourceSystem get(String id) {
		return (SourceSystem) sessionFactory.getCurrentSession().get(SourceSystem.class, id);
	}

	@Override
	public void undelete(SourceSystem ss) {
		ss.undelete();
		sessionFactory.getCurrentSession().update(ss);
	}
}
