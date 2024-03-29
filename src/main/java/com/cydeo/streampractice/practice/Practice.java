package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {

        return getAllEmployees().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {

        return getAllCountries().stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {

        return getAllDepartments().stream()
                .map(department -> department.getManager().getFirstName())
                .collect(Collectors.toList());


    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {

        return getAllDepartments().stream()
                .filter(department -> department.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());

    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {

        return getAllDepartments().stream()
                .filter(department -> department.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());

    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {

        Optional<Region> regionIT = getAllDepartments().stream()
                .filter(department -> department.getDepartmentName().equalsIgnoreCase("IT"))
                .map(department -> department.getLocation().getCountry().getRegion())
                .findFirst();
        return regionIT.orElse(null);
    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        List<Department> departments = getAllDepartments().stream()
                .filter(department -> department.getLocation().getCountry().getRegion().getRegionName().equalsIgnoreCase("Europe"))
                .collect(Collectors.toList());
        return departments;

    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
        boolean salaryLessThan1000 = getAllEmployees().stream()
                .noneMatch(employee -> employee.getSalary() < 1000);
        return salaryLessThan1000;
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        boolean greaterThan2000 = getAllEmployees().stream()
                .filter(employee -> employee.getDepartment().getDepartmentName().equalsIgnoreCase("IT"))
                .anyMatch(employee -> employee.getSalary() > 2000);

        return greaterThan2000;
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        List<Employee> lessThan500 = getAllEmployees().stream()
                .filter(employee -> employee.getSalary() < 5000)
                .collect(Collectors.toList());
        return lessThan500;
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        List btw6000and7000 = getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > 6000 && employee.getSalary() < 7000)
                .collect(Collectors.toList());
        return btw6000and7000;
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        Optional<Long> grantDougSalary = getAllEmployees().stream()
                .filter(employee -> employee.getFirstName().equalsIgnoreCase("Douglas")
                        && employee.getLastName().equalsIgnoreCase("Grant"))
                .map(Employee::getSalary)
                .findFirst();
        if (grantDougSalary.isPresent()) {
            return grantDougSalary.get();
        } else {
            throw new RuntimeException("Name doesn't exist");
        }
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
        Optional<Long> maxSalary = getAllEmployees().stream()
                .map(Employee::getSalary)
                .reduce(Long::max);
        if (maxSalary.isPresent()) {
            return maxSalary.get();
        } else {
            throw new RuntimeException();
        }

    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {

        Optional<Long> maxSalaryOptional = getAllEmployees().stream()
                .map(Employee::getSalary)
                .max(Comparator.naturalOrder());

        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary().equals(maxSalaryOptional.get()))
                .collect(Collectors.toList());

    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        Optional<Employee> maxSalaryEmployees = getAllEmployees().stream()
                .max(Comparator.comparing(Employee::getSalary));

        return maxSalaryEmployees.map(Employee::getJob).get();
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        List<Employee> americasRegEmp = getAllEmployees().stream()
                .filter(e -> e.getDepartment().getLocation().getCountry().getRegion().getRegionName().equalsIgnoreCase("Americas"))
                .collect(Collectors.toList());
        Optional<Long> maxSalary = americasRegEmp.stream()
                .map(Employee::getSalary)
                .max(Comparator.naturalOrder());
        if (maxSalary.isPresent()) {
            return maxSalary.get();
        } else {
            throw new Exception("no such salary");
        }

    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        List<Long> maxSalaries = getAllEmployees().stream()
                .map(Employee::getSalary)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

            return maxSalaries.get(1);

    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
        List<Long> maxSalaries = getAllEmployees().stream()
                .map(Employee::getSalary)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary().equals(maxSalaries.get(1)))
                .collect(Collectors.toList());
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        Optional<Long> minSalary = getAllEmployees().stream()
                .map(Employee::getSalary)
                .min(Comparator.naturalOrder());
        if (minSalary.isPresent()) {
            return minSalary.get();
        } else {
            throw new RuntimeException("no such salary");
        }
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        Optional<Long> optional = getAllEmployees().stream()
                .map(Employee::getSalary)
                .min(Comparator.naturalOrder());
        Long minSalary = optional.get();
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary().equals(minSalary))
                .collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        List<Long> minSalaries = getAllEmployees().stream()
                .map(Employee::getSalary)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return minSalaries.get(1);

    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {
        List<Long> empSalaries = getAllEmployees().stream()
                .map(Employee::getSalary)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary().equals(empSalaries.get(1)))
                .collect(Collectors.toList());
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        return getAllEmployees().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {

        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {

        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() < getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        return getAllEmployees().stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment().getId()));

    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        List<Department> allDepartments = new ArrayList<>(getAllDepartments());
        return (long) allDepartments.size();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        List<Employee> employees = getAllEmployees().stream()
                .filter(employee -> employee.getFirstName().equalsIgnoreCase("Alyssa"))
                .filter(employee -> employee.getManager().getFirstName().equalsIgnoreCase("Eleni"))
                .filter(employee -> employee.getDepartment().getDepartmentName().equalsIgnoreCase("Sales"))
                .collect(Collectors.toList());
        return employees.get(0);
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        return getAllJobHistories().stream()
                .filter(jobHistory -> jobHistory.getStartDate().isAfter(LocalDate.of(2005, 01, 01)))
                .collect(Collectors.toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        return getAllJobHistories().stream()
                .filter(jobHistory -> jobHistory.getEndDate().equals(LocalDate.of(2007, 12, 31)))
                .filter(jobHistory -> jobHistory.getJob().getJobTitle().equalsIgnoreCase("Programmer"))
                .collect(Collectors.toList());


    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        Optional<JobHistory> jobHistoryResult = getAllJobHistories().stream()
                .filter(jobHistory -> jobHistory.getStartDate().equals(LocalDate.of(2007, 01, 01)))
                .filter(jobHistory -> jobHistory.getEndDate().equals(LocalDate.of(2007, 12, 31)))
                .filter(jobHistory -> jobHistory.getDepartment().getDepartmentName().equalsIgnoreCase("Shipping"))
                .findFirst();
        return jobHistoryResult.get().getEmployee();

    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getFirstName().toLowerCase().startsWith("a"))
                .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getJob().getId().toLowerCase().contains("it"))
                .collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        List<Employee> listOfEmployees = getAllEmployees().stream()
                .filter(employee -> employee.getJob().getJobTitle().equalsIgnoreCase("programmer"))
                .filter(employee -> employee.getDepartment().getDepartmentName().equalsIgnoreCase("it"))
                .collect(Collectors.toList());
        return (long) listOfEmployees.size();
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getDepartment().getId() == 50 ||
                        employee.getDepartment().getId() == 80 ||
                        employee.getDepartment().getId() == 100)
                .collect(Collectors.toList());
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
      return getAllEmployees().stream()
                .map(employee -> employee.getFirstName().charAt(0) + "" + employee.getLastName().charAt(0))
              .collect(Collectors.toList());

    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
       return getAllEmployees().stream()
               .map(employee -> employee.getFirstName() + " " + employee.getLastName())
               .collect(Collectors.toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
     Optional<Integer> longestName = getAllEmployeesFullNames().stream()
                .map(String::length)
                .max(Comparator.comparing(Integer::intValue));
     return longestName.get();
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee()  {
       List<String> fullNames = getAllEmployees().stream()
                .map(employee -> employee.getFirstName() + employee.getLastName())
                .collect(Collectors.toList());
       List<Integer> list = fullNames.stream()
               .map(String::length)
               .sorted(Comparator.reverseOrder())
               .collect(Collectors.toList());
       Integer longestName = list.get(0);
      return getAllEmployees().stream()
               .filter(employee -> employee.getFirstName().length()+employee.getLastName().length() == longestName)
               .collect(Collectors.toList());

    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
     return getAllEmployees().stream()
             .filter(employee -> employee.getDepartment().getId()==90||
                     employee.getDepartment().getId()==60 ||
                     employee.getDepartment().getId()==100 ||
                     employee.getDepartment().getId()==120 ||
                     employee.getDepartment().getId()==130)
             .collect(Collectors.toList());
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getDepartment().getId()!=90&&
                        employee.getDepartment().getId()!=60 &&
                        employee.getDepartment().getId()!=100 &&
                        employee.getDepartment().getId()!=120 &&
                        employee.getDepartment().getId()!=130)
                .collect(Collectors.toList());
    }

}
