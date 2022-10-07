package com.dgsl.dwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dgsl.dwp.bean.DwpAclEmailTemplate;

@Repository
public interface DwpEmailTemplateRepository extends JpaRepository<DwpAclEmailTemplate, Long>{
	
	@Query(value="select firstName,lastName,email,office,userRole,department,division,groupId,managerId from DwpAclEmailTemplate where toStep =:toStep and fromStep =:fromStep and action =:action")
	public DwpAclEmailTemplate getEmailTemplate(@Param("toStep") String toStep,
			@Param("fromStep") String fromStep,@Param("action") String action);

}
