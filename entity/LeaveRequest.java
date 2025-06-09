package HRMS_project.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import HRMS_project.entity.Role.LeaveStatus;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leave_request_seq")
    @SequenceGenerator(name = "leave_request_seq", sequenceName = "leave_request_sequence", allocationSize = 1)
    private Long id;

    private String employeeEmail;
    private String reason;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    private String managerComment;

    private LocalDate appliedDate;

    // getters and setters
}