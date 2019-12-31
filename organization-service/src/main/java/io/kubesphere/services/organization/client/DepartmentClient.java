package io.kubesphere.services.organization.client;

import java.util.List;

import com.alibaba.cloud.dubbo.annotation.DubboTransported;
import io.kubesphere.services.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author runzexia
 */
@FeignClient("department-service")
@DubboTransported(protocol = "dubbo")
public interface DepartmentClient {

	@GetMapping("/organization/{organizationId}")
	public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId);
	
	@GetMapping("/organization/{organizationId}/with-employees")
	public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId);
	
}