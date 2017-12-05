package com.mindtree.task.reports;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Persistable; 
 
 
public class ExcelReportView extends AbstractXlsxView{
	
	//extends AbstractXlsxStreamingView  for large excell documents
 
 @Override
 protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
 HttpServletResponse response) throws Exception {
	  
	 response.setHeader("Content-Disposition", "attachment;filename=\"Employees.xlsx\"");
	 List<Persistable> empList = (List<Persistable>) model.get("empList");	 
	  
	  // Create an instance of sheet
	 Sheet sheet = workbook.createSheet("Employee Data");
	 
	 XSSFRow  header = (XSSFRow) sheet.createRow(0);
	 header.createCell(0).setCellValue("MID");
	 header.createCell(1).setCellValue("Name");
	 header.createCell(2).setCellValue("Email ID");
	 header.createCell(3).setCellValue("Join Date");
	 
	  
	 int rowNum = 1;
	 for(Persistable obj:empList){
	 Employee emp = (Employee) obj;
		 Row row = sheet.createRow(rowNum++);
		 row.createCell(0).setCellValue(emp.getMid());
		 row.createCell(1).setCellValue(emp.getName());
		 row.createCell(2).setCellValue(emp.getEmailId());
		 row.createCell(3).setCellValue(emp.getJoinDate());
	 }
	}
	 
 
}
