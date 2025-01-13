package com.meld.techo.travel.crm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Vendor;
import com.meld.techo.travel.crm.security.service.VendorService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/vendor")
public class VendorController {
	
	
	@Autowired
	private VendorService vendorService;
	
	
	
//	@GetMapping("/getAll")
//	public List<Vendor> getAllVendor(){
//		return vendorService.getAllVendor();
//	}
	
	
	 @GetMapping("/all/paginated")
	    public ResponseEntity<Response<Page<Vendor>>> getVendor(
	            @RequestParam(value = "page", defaultValue = "0") int page,
	            @RequestParam(value = "size", defaultValue = "10") int size,
	            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
	        try {
	            // Validate sort direction
	            if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
	                throw new IllegalArgumentException("Invalid sort direction. It must be 'asc' or 'desc'.");
	            }

	            // Fetch vendor data with pagination
	            Page<Vendor> vendorPage = vendorService.getVendor(page, size, sortDirection);

	            // Check if the page is empty
	            if (vendorPage.isEmpty()) {
	                throw new CustomException("No vendors found", "404");
	            }

	            // Wrap successful response
	            Response<Page<Vendor>> response = new Response<>(
	                "success",
	                "Vendors fetched successfully",
	                vendorPage
	            );

	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            // Handle invalid input
	            Response<Page<Vendor>> errorResponse = new Response<>(
	                "error",
	                e.getMessage(),
	                Page.empty() // Empty page in case of error
	            );

	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	        } catch (CustomException e) {
	            // Handle no records found
	            Response<Page<Vendor>> errorResponse = new Response<>(
	                "error",
	                e.getMessage(),
	                Page.empty()
	            );

	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	        } catch (Exception ex) {
	            // Log and handle unexpected errors
	            ex.printStackTrace();

	            Response<Page<Vendor>> errorResponse = new Response<>(
	                "error",
	                "An unexpected error occurred while fetching vendors",
	                Page.empty()
	            );

	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Response<Vendor>> getVendorById(@PathVariable Long id) {
	        try {
	            // Fetch vendor by ID
	            Optional<Vendor> vendor = vendorService.getVendorById(id);

	            // If vendor is found, return success response
	            if (vendor.isPresent()) {
	                Response<Vendor> response = new Response<>(
	                    "success",
	                    "Vendor fetched successfully",
	                    vendor.get()
	                );

	                return ResponseEntity.ok(response);
	            } else {
	                // If vendor not found, return not found response
	                Response<Vendor> errorResponse = new Response<>(
	                    "error",
	                    "Vendor not found with ID: " + id,
	                    null
	                );

	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	            }
	        } catch (Exception ex) {
	            // Log and handle unexpected errors
	            ex.printStackTrace();

	            Response<Vendor> errorResponse = new Response<>(
	                "error",
	                "An unexpected error occurred while fetching the vendor",
	                null
	            );

	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    }
	
//	@PostMapping("/create")
//	public Vendor addVendor(@RequestBody Vendor vendor) {
//		return this.vendorService.addVendor(vendor);
//	}
	
	
	
	    @PostMapping("")
	    public ResponseEntity<Response<Vendor>> addVendor(@RequestBody Vendor vendor, HttpServletRequest request) {
	        
	    	String clientIpAddress = getClientIp(request);
	    	vendor.setIpaddress(clientIpAddress);
	    	
	    	try {
	            // Check if a vendor with the same email already exists
	            if (vendorService.existsByVendorEmail(vendor.getVendorEmail())) {
	                Response<Vendor> errorResponse = new Response<>(
	                    "error",
	                    "Vendor with the email " + vendor.getVendorEmail() + " already exists.",
	                    null // No data in the response
	                );
	                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	            }

	            // Check if a vendor with the same contact number already exists
	            if (vendorService.existsByVendorContactNo(vendor.getVendorContactNo())) {
	                Response<Vendor> errorResponse = new Response<>(
	                    "error",
	                    "Vendor with the contact number " + vendor.getVendorContactNo() + " already exists.",
	                    null // No data in the response
	                );
	                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	            }

	            // Create the vendor
	            Vendor createdVendor = vendorService.addVendor(vendor);

	            // Wrap the successful response
	            Response<Vendor> successResponse = new Response<>(
	                "success",
	                "Vendor created successfully.",
	                createdVendor
	            );

	            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	        } catch (Exception ex) {
	            // Log the exception (optional for debugging purposes)
	            ex.printStackTrace();

	            // Handle unexpected errors
	            Response<Vendor> errorResponse = new Response<>(
	                "error",
	                "An unexpected error occurred while creating the vendor.",
	                null // No data in the response
	            );

	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    }

	
	
	@PutMapping("/{id}")
	public ResponseEntity<Vendor> updateVendor(@PathVariable Long id , @RequestBody Vendor vendor, HttpServletRequest request){
		
//		String clientIpAddress = getClientIp(request);
//		vendor.setIpaddress(clientIpAddress);
		
		if(vendor != null)
		{
			
			String clientIpAddress = getClientIp(request);
			vendor.setIpaddress(clientIpAddress);
			Vendor v = new Vendor();
			
			v.setId(id);
			v.setVendorName(vendor.getVendorName());
			v.setVendorEmail(vendor.getVendorEmail());
			v.setVendorContactNo(vendor.getVendorContactNo());
			v.setVendorAddress(vendor.getVendorAddress());
			v.setIpaddress(vendor.getIpaddress());
			v.setStatus(vendor.isStatus());
			v.setIsdelete(vendor.isIsdelete());
			v.setCreatedby(vendor.getCreatedby());
			v.setModifiedby(vendor.getModifiedby());
			v.setCreateddate(vendor.getCreateddate());
			v.setModifieddate(vendor.getModifieddate());
			
			vendorService.updateVendor(v);
			return ResponseEntity.ok(v);
		}

		else
		{
			return ResponseEntity.notFound().build();
		}
		}
	
	
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable Long id) {
        try {
            String result = vendorService.deleteVendor(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "Vendor deleted successfully", result), HttpStatus.OK);
        } catch (CustomException e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), e.getErrorCode());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", "An unexpected error occurred: " + e.getMessage(), "500");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }}	
	
	
	
	// Helper method to fetch client IP address
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
	
	
	