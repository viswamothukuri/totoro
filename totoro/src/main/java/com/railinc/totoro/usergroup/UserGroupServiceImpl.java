package com.railinc.totoro.usergroup;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railinc.totoro.domain.UserGroup;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<UserGroup> all(String filter) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(UserGroup.class);
		if (isNotBlank(filter)) {
			filter = "%" + filter + "%";
			c.add(Restrictions.or(
					Restrictions.ilike(UserGroup.PROPERTY_ID, filter),Restrictions.ilike(UserGroup.PROPERTY_NAME, filter))
			);
		}
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.addOrder(Order.asc(UserGroup.PROPERTY_ID));
		
		return c.list();
			
	}

	@Override
	@Transactional
	public void save(UserGroup s) {
		sessionFactory.getCurrentSession().saveOrUpdate(s);
	}

	@Override
	@Transactional
	public void delete(UserGroup s) {
		s.delete();
		sessionFactory.getCurrentSession().update(s);
	}

	@Override
	@Transactional
	public UserGroup get(String id) {
		return (UserGroup) sessionFactory.getCurrentSession().get(UserGroup.class, id);
	}

	@Override
	public void undelete(UserGroup ss) {
		ss.undelete();
		sessionFactory.getCurrentSession().update(ss);
	}
}
