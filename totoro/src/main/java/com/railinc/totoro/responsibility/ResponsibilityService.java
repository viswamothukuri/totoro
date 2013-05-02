package com.railinc.totoro.responsibility;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.util.PagedCollection;

@Service
@Transactional
public interface ResponsibilityService {
	PagedCollection<Responsibility> all(ResponsibilityCriteria criteria);
	void save(Responsibility s);
	void delete(Responsibility s);
	Responsibility get(Long id);
	void undelete(Responsibility ss);
}
