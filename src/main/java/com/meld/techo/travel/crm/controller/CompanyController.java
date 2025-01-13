package com.meld.techo.travel.crm.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Company;
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.security.service.CompanyService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("Motherson/crm/v1/company")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CompanyController {
	@Autowired
	private CompanyService companyservice;
	
	
	
	@GetMapping("/all/paginated")
	public ResponseEntity<?> getCompany(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
	    try {
	        Page<Company> company = companyservice.getCompany(page, size, sortDirection);

	        Response<Page<Company>> response = new Response<>("SUCCESS", "Company fetched successfully", company);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	       
	        ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), "500");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	
	//Get All
		@GetMapping("/all")
		public ResponseEntity<Response<Object>> getAllCompany() {
		    try {
		        
		        Response<Object> response = companyservice.getAllCompany();
		        return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch(Exception e) {
		        
		        throw e;
		    }
		}
		
		
		// Get By Company Id
		public ResponseEntity<Object> getCompanyById(@PathVariable Long id){
			try {
				Response<Object> response = companyservice.getCompanysById(id);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}catch(Exception e) {
				throw e;
			}
		}
	
	
	private String timestamp;
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/image/countryimages";
	
	@PostMapping("")
	public ResponseEntity<?> saveCountry(@Valid @ModelAttribute Company company, BindingResult bindingResult, 
	                                     @RequestParam("image") MultipartFile[] files) throws IOException {
	    
	    if (bindingResult.hasErrors()) {
	        Map<String, String> errors = new HashMap<>();
	        bindingResult.getFieldErrors().forEach(error -> {
	            errors.put(error.getField(), error.getDefaultMessage());
	        });
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>("Failed", "Validation failed", errors));
	    }

	    List<String> imageUrls = new ArrayList<>();

	    Path uploadPath = Paths.get(uploadDirectory);
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }

	    for (MultipartFile file : files) {
	        if (!isValidImage(file)) {
	            throw new IllegalArgumentException("File must be a JPEG or PNG image.");
	        }


	        String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());

	        Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);
	        Files.write(fileNameAndPath, file.getBytes());

	        String imageUrl = "/image/" + uniqueFilename;  // Public URL path
	        imageUrls.add(imageUrl);
	    }

	    company.setCompanylogo(imageUrls);

	    Company createdCompany = this.companyservice.createcompanyser(company);

	    return ResponseEntity.ok(new Response<>("Successful", "Company created successfully", null));
	}

	private boolean isValidImage(MultipartFile file) {
	    String contentType = file.getContentType();
	    return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
	}

	private String generateUniqueFilename(String originalFilename) {
	    String extension = "";
	    if (originalFilename != null && originalFilename.lastIndexOf('.') > 0) {
	        extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	    }

	    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm-ss"));
	    
	    // Generate a unique UUID
	    String uniqueID = UUID.randomUUID().toString();

	    // Return the unique filename
	    return timestamp + "_" + uniqueID + extension;
	}
	
	
	@PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id,
                                               @ModelAttribute Company company,
                                               @RequestParam(value = "image", required = false) MultipartFile[] files) throws IOException {
        try {
            // Fetch existing destination
            Company existingCompany = companyservice.getCompanyById(id);

            // Delete old images from the file system
            if (existingCompany.getCompanylogo() != null) {
                for (String oldImageUrl : existingCompany.getCompanylogo()) {
                    Path oldImagePath = Paths.get(uploadDirectory, oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1));
                    Files.deleteIfExists(oldImagePath);
                }
            }

            List<String> imageUrls = new ArrayList<>();

            // Process new images if any
            if (files != null) {
                for (MultipartFile file : files) {
                    if (!isValidImage(file)) {
                        throw new IllegalArgumentException("File must be a JPEG or PNG image.");
                    }

                    String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());
                    Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);
                    Files.write(fileNameAndPath, file.getBytes());

                    String imageUrl = "/uploads/" + uniqueFilename;
                    imageUrls.add(imageUrl);
                }
            }

            company.setCompanylogo(imageUrls);

            // Update destination
            Company updatedCompany = companyservice.updateCompanyById(id, company);

            // Return success response
            Response<Company> response = new Response<>("SUCCESS", "Company updated successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    private boolean isValidImage1(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
    }

    private String generateUniqueFilename1(String originalFilename) {
        return System.currentTimeMillis() + "_" + originalFilename; // Unique filename based on current time
    }
	
	
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        try {
            String result = companyservice.deleteCompany(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "Company deleted successfully", result), HttpStatus.OK);
        } catch (CustomException e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), e.getErrorCode());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", "An unexpected error occurred: " + e.getMessage(), "500");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }}	
	

 
	
	
	
	
 
//		@GetMapping("/ipAddress")
//	    public String getIpAddress() {
//	        try {
//	            InetAddress ip = InetAddress.getLocalHost();
//	            return ip.getHostAddress();
//	        } catch (UnknownHostException e) {
//	            e.printStackTrace();
//	            return "Unable to fetch IP Address";
//	        }
//	    }
//


}

