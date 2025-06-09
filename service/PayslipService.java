package HRMS_project.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;



import HRMS_project.entity.Payslip;
import HRMS_project.repository.PayslipRepository;

@Service
public class PayslipService {

    @Autowired private PayslipRepository payslipRepo;

    private final String PDF_DIR = "payslips/";

    public Payslip generatePayslip(String email, String name, String month,
                                    double basicPay, double deductions) throws IOException {
        double netPay = basicPay - deductions;
        String fileName = UUID.randomUUID() + "_payslip_" + month + ".pdf";
		Path path = Paths.get(PDF_DIR + fileName);
        Files.createDirectories(path.getParent());

        // Generate PDF content
        String content = "Payslip for " + month + "\n\n" +
                         "Employee: " + name + "\n" +
                         "Email: " + email + "\n\n" +
                         "Basic Pay: ₹" + basicPay + "\n" +
                         "Deductions: ₹" + deductions + "\n" +
                         "Net Pay: ₹" + netPay + "\n";

        // Simple PDF generator (or use iText)
        Files.write(path, content.getBytes());

        Payslip payslip = new Payslip();
        payslip.setEmployeeEmail(email);
        payslip.setEmployeeName(name);
        payslip.setMonth(month);
        payslip.setBasicPay(basicPay);
        payslip.setDeductions(deductions);
        payslip.setNetPay(netPay);
        payslip.setPdfPath(path.toString());
        payslip.setGeneratedDate(LocalDate.now());

        return payslipRepo.save(payslip);
    }

    public List<Payslip> getPayslips(String email) {
        return payslipRepo.findByEmployeeEmail(email);
    }

    public Resource downloadPayslip(Long id) throws IOException {
        Payslip payslip = payslipRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payslip not found"));
        Path path = Paths.get(payslip.getPdfPath());
        return new UrlResource(path.toUri());
    }
}