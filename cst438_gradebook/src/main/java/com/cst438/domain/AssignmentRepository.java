package com.cst438.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends CrudRepository <Assignment, Integer> {

	@Query("select a from Assignment a where a.needsGrading=1 and a.dueDate < current_date and a.course.instructor= :email order by a.id")
	List<Assignment> findNeedGradingByEmail(@Param("email") String email);
	
	@Query("select a from Assignment a where a.id = :assignmentId")
	Optional<Assignment> findById(@Param("assignmentId") int assignmentId);
	
//	@Query("select a from Assignment a where a.id = :assignmentId")
//	Optional<Assignment> findByName(@Param("assignmentname") String assignmentName);
	
	@Query("select a from Assignment a where a.course.course_id = :course_id")
	Optional<Assignment> findByCourseId(@Param("course_id") int courseId);
}
