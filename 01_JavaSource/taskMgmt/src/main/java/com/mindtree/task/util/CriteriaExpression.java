package com.mindtree.task.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CriteriaExpression{

	private String fieldName;
	private Object value;
	private String operator;	
	
	public CriteriaExpression(String fieldName, Object value, String operator) {
		super();
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public Object getValue() {
		return value;
	}
	public String getOperator() {
		return operator;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	 @SuppressWarnings("unchecked")
	 public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {  
	        Path expression = null;  
	      
	        expression = root.get(fieldName);  
	        
	        switch (operator) {  
	        case "EQ":  
	            return builder.equal(expression, value);  
	        case "NE":  
	            return builder.notEqual(expression, value);  
	        case "LIKE":  
	            return builder.like((Expression<String>) expression, "%" + value.toString() + "%");  
	        case "LT":  
	            return builder.lessThan(expression, (Comparable) value);  
	        case "GT":  
	            return builder.greaterThan(expression, (Comparable) value);  
	        case "LTE":  
	            return builder.lessThanOrEqualTo(expression, (Comparable) value);  
	        case "GTE":  
	            return builder.greaterThanOrEqualTo(expression, (Comparable) value);  
	        default:  
	            return null;  
	        }  
	    }  
	
}
