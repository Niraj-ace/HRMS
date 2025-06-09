package HRMS_project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HRMS_project.entity.LeaveRequest;
import HRMS_project.entity.Role.LeaveStatus;
import HRMS_project.repository.LeaveRequestRepository;

@Service
public class LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRepo;

    public LeaveRequest applyLeave(String email, LeaveRequest request) {
        request.setEmployeeEmail(email);
        request.setStatus(LeaveStatus.PENDING);
        request.setAppliedDate(LocalDate.now());
        return leaveRepo.save(request);
    }

    public List<LeaveRequest> getLeavesForEmployee(String email) {
        return leaveRepo.findByEmployeeEmail(email);
    }

    public List<LeaveRequest> getPendingLeaves() {
        return leaveRepo.findByStatus(LeaveStatus.PENDING);
    }

    public LeaveRequest reviewLeave(Long id, LeaveStatus status, String managerComment) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(status);
        leave.setManagerComment(managerComment);
        return leaveRepo.save(leave);
    }
}