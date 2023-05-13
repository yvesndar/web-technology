package com.web.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.app.model.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Integer>{

	Zone getZoneById(int id);
}
