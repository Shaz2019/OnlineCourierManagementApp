package com.cg.mts.exception.handlers;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.mts.exceptions.ComplaintNotFoundException;
import com.cg.mts.exceptions.CourierIdExistsException;
import com.cg.mts.exceptions.CourierNotFoundException;
import com.cg.mts.exceptions.CustomerNotFoundException;
import com.cg.mts.exceptions.DuplicateCustomerException;
import com.cg.mts.exceptions.DuplicateStaffMemberFoundException;
import com.cg.mts.exceptions.EmptyDataException;
import com.cg.mts.exceptions.OutletClosedException;
import com.cg.mts.exceptions.OutletNotFoundException;
import com.cg.mts.exceptions.PaymentNotFoundException;
import com.cg.mts.exceptions.StaffMemberNotFoundException;

@ControllerAdvice
public class ApplicationErrorHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> errorList=
		ex.getBindingResult().getFieldErrors()
		.stream().map(fe->fe.getDefaultMessage())
		.collect(Collectors.toList());
		
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("DataError", "Problem in date recieve");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("errors", errorList);
		
		return new ResponseEntity<>(errorbody,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(StaffMemberNotFoundException.class)
	public ResponseEntity<?> handleMissingStaffMember(StaffMemberNotFoundException ex){
		
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Failed data is not present");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		
		return new ResponseEntity<>(errorbody,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicateStaffMemberFoundException.class)
    public ResponseEntity<?> handleDuplicateStaff(DuplicateStaffMemberFoundException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Duplicate data");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		
		return new ResponseEntity<>(errorbody,HttpStatus.BAD_REQUEST);
	}
    
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> handleMissingCustomer(CustomerNotFoundException ex){
		
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Customer data is not present");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		
		return new ResponseEntity<>(errorbody,HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<?> handleDuplicateCustomer(DuplicateCustomerException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Duplicate data");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		
		return new ResponseEntity<>(errorbody,HttpStatus.BAD_REQUEST);
	}
    
	
	@ExceptionHandler(ComplaintNotFoundException.class)
    public ResponseEntity<?> handleComplaintNotFound(ComplaintNotFoundException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", ex.getOperation()+"Failed");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		 
		return new ResponseEntity<>(errorbody,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(CourierIdExistsException.class)
    public ResponseEntity<?> handleDuplicateCourierId(CourierIdExistsException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", ex.getOperation()+"Failed");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		 
		return new ResponseEntity<>(errorbody,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(CourierNotFoundException.class)
    public ResponseEntity<?> handleCourierNotFound(CourierNotFoundException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Courier is not present in Data Base");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		 
		return new ResponseEntity<>(errorbody,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(EmptyDataException.class)
    public ResponseEntity<?> handleEmptyData(EmptyDataException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Empty Data");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		 
		return new ResponseEntity<>(errorbody,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(OutletNotFoundException.class)
    public ResponseEntity<?> handleOutletNotFound(OutletNotFoundException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Office Outlet is not found");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		 
		return new ResponseEntity<>(errorbody,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(OutletClosedException.class)
    public ResponseEntity<?> handleOutletClosed(OutletClosedException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Office Outlet is closed");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		 
		return new ResponseEntity<>(errorbody,HttpStatus.BAD_REQUEST);
		
	}
	

	@ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handlePaymentNotFound(PaymentNotFoundException ex){
		Map<String,Object> errorbody = new LinkedHashMap<>();
		errorbody.put("error", "Payment is not found");
		errorbody.put("timestamp", LocalDateTime.now());
		errorbody.put("details", ex.getMessage());
		 
		return new ResponseEntity<>(errorbody,HttpStatus.NOT_FOUND);
		
	}
	

}
