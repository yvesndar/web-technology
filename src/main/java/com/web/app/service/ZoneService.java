package com.web.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.app.model.Zone;
import com.web.app.repository.ZoneRepository;

@Service
public class ZoneService {

	@Autowired
	private ZoneRepository zoneRepository;
	
	public Zone createZone(Zone zone) {
		return zoneRepository.save(zone);
	}
	
	public List<Zone> findAllZone(){
		return zoneRepository.findAll();
	}
	public void deleteZone(int id) {
		zoneRepository.deleteById(id);
	}
	
	public Zone getZoneById(int id) {
		return zoneRepository.getZoneById(id);
	}
	
	public void updateZone(Zone zone) {
		zoneRepository.save(zone);
	}
}
