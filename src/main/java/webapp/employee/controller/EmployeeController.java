package webapp.employee.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webapp.employee.dto.EmpLimitDTO;
import webapp.employee.dto.EmpRoleDTO;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.dto.PwdIdDTO;
import webapp.employee.service.EmployeeService;
import webapp.security.dto.AuthRequestDTO;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login-check")
    public Boolean loginCheck(@RequestParam String email) {
        return employeeService.emailDuplicateCheck(email);
    }

    @PostMapping("/save")
    public Boolean saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping("/ls-one")
    public EmployeeDTO findById(@RequestParam Integer id) {
        return employeeService.findById(id);
    }

    @GetMapping("/set-status")
    public Boolean updateStatus(@RequestParam Integer id, @RequestParam Boolean status) {
        return employeeService.updateStatus(id, status);
    }

    @GetMapping("/find-one")
    public EmployeeDTO findEmpById(@RequestParam Integer id) {
        return employeeService.findById(id);
    }

    @GetMapping("/ls-all")
    public List<EmployeeDTO> listAll(){
        return employeeService.findAllDTO();
    }

    @GetMapping("/ls-one-join-role")
    public EmpRoleDTO findOneJoinRole(@RequestParam Integer id) {
        return employeeService.findJoinRoleById(id);
    }

    @PostMapping("/save-one-pwd")
    public Integer savePwdById(@RequestBody PwdIdDTO dto) {
        return employeeService.savePwdById(dto.getPassword(), dto.getId());
    }

    @PostMapping("/save-one-part")
    public void saveEmpPartial(@RequestBody EmpLimitDTO dto) {
        employeeService.updateEmployeePartial(dto);
    }

    @PostMapping("/pwd-check")
    public Boolean checkPwd(@RequestBody AuthRequestDTO dto) {
        return employeeService.checkPwd(dto);
    }

    @GetMapping("/ls-by-email")
    public EmployeeDTO findByEmail(@RequestParam String email){
        return employeeService.findDTOByEmail(email);
    }
}
