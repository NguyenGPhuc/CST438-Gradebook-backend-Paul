package com.cst438.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cst438.controllers.assignmentDTO;
import com.cst438.domain.AssignmentListDTO.AssignmentDTO;

public interface AssignmentRepository extends CrudRepository <Assignment, Integer> {

	@Query("select a from Assignment a where a.needsGrading=1 and a.dueDate < current_date and a.course.instructor= :email order by a.id")
	List<Assignment> findNeedGradingByEmail(@Param("email") String email);
	
//	@Query("select a from Assignment a where a.getName=?? and a.course.instructor= :email order by a.id")
//	List<Assignment> findByAssignmentName(@Param("name") String name);

//	Object findByAssignmentName(Class<Assignment> class1);

	public findByAssignmentName(String name);

//	Assignment findByAssignmentName(Class<assignmentDTO> class1);
	
}
