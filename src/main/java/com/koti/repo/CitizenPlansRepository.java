package com.koti.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koti.entity.CitizenPlan;

public interface CitizenPlansRepository extends JpaRepository<CitizenPlan, Integer> {

	@Query("select distinct(planName) from CitizenPlan")
	public List<CitizenPlan> getPlanNames();
	@Query("select distinct(planStatus) from CitizenPlan")
	public List<CitizenPlan> getPlanStatus();
}
