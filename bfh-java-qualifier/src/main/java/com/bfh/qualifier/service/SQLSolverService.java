package com.bfh.qualifier.service;

public class SQLSolverService {

    // Q1 placeholder (odd regNo). Replace if you need.
    public String solveQuestion1() {
        return "SELECT employee_id FROM employees LIMIT 0;";
    }

    // Q2 solution (even regNo) - as per provided PDF
    public String solveQuestion2() {
        return "SELECT " +
                "e1.EMP_ID, " +
                "e1.FIRST_NAME, " +
                "e1.LAST_NAME, " +
                "d.DEPARTMENT_NAME, " +
                "COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
                "FROM EMPLOYEE e1 " +
                "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                "LEFT JOIN EMPLOYEE e2 " +
                "ON e1.DEPARTMENT = e2.DEPARTMENT " +
                "AND e2.DOB > e1.DOB " +
                "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                "ORDER BY e1.EMP_ID DESC;";
    }
}
