package com.railinc.totoro.sourcesystem;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railinc.totoro.domain.SourceSystem;

@Service
@Transactional
public interface SourceSystemService {
	Collection<SourceSystem> all(String filter);
	void save(SourceSystem s);
	void delete(SourceSystem s);
	SourceSystem get(String id);
	void undelete(SourceSystem ss);
	Collection<SourceSystem> active();
}
