package HRMS_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import HRMS_project.entity.LeaveRequest;
import HRMS_project.entity.Role.LeaveStatus;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeEmail(String email);
    List<LeaveRequest> findByStatus(LeaveStatus status);
}