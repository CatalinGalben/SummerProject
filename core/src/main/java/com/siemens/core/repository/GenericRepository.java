package com.siemens.core.repository;

import com.siemens.core.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
@NoRepositoryBean
@Transactional
public interface GenericRepository<T extends BaseEntity<K>, K extends Serializable>
extends JpaRepository<T, K>{
}
