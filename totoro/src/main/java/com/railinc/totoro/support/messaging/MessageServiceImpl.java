package com.railinc.totoro.support.messaging;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.railinc.totoro.domain.RawInboundMessage;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.integration.Queue;
import com.railinc.totoro.util.PagedCollection;
import com.railinc.totoro.util.PagingResults;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Autowired
	private Queue queue;

	@SuppressWarnings("unchecked")
	@Transactional
	public PagedCollection<RawInboundMessage> all(MessageSearchCriteria criteria) {
		criteria = Optional.fromNullable(criteria).or(new MessageSearchCriteria());
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(RawInboundMessage.class);
		if (criteria.hasInboundSourceCriteria()) {
			c.add(Restrictions.in(RawInboundMessage.PROPERTY_INBOUND_SOURCE, criteria.getInboundSourceCriteria()));
		}
		if (criteria.hasProcessedCriteria()) {
			c.add(Restrictions.eq(RawInboundMessage.PROPERTY_INBOUND_PROCESSED, criteria.getProcessedCriteria()));
		}
		if (criteria.hasDataCriteria()) {
			Criterion root = null;
			for (String s : criteria.getDataCriteria()) {
				Criterion ilike = Restrictions.ilike(RawInboundMessage.PROPERTY_INBOUND_DATA, s , MatchMode.ANYWHERE);
				if (root == null) { root = ilike; } else { root = Restrictions.or(root, ilike);}
			}
			c.add(root);
		}
		
		c.setProjection(Projections.rowCount());
		int count = ((Long) c.uniqueResult()).intValue();
		
		c.setProjection(null);
		c.setFirstResult(criteria.getPage() * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
		
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return new PagedCollection<RawInboundMessage>(c.list(), new PagingResults(criteria, count));
	}

	@Override
	@Transactional
	public void save(RawInboundMessage s) {
		sessionFactory.getCurrentSession().saveOrUpdate(s);
	}

	@Override
	@Transactional
	public void delete(RawInboundMessage s) {
		sessionFactory.getCurrentSession().delete(s);
	}

	@Override
	@Transactional
	public RawInboundMessage get(Long id) {
		return (RawInboundMessage) sessionFactory.getCurrentSession().get(Responsibility.class, id);
	}

	@Override
	public void sendMessage(String data) {
		this.queue.sendMessage(data);
	}

}
