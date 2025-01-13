package com.meld.techo.travel.crm.controller;

import java.io.IOException;
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
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.meld.techo.travel.crm.dto.CountryDTO;
import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.City;
import com.meld.techo.travel.crm.models.Country;
import com.meld.techo.travel.crm.security.service.CountryService;

import jakarta.servlet.http.HttpServletRequest;

//import com.MotherSon.CRM.models.Country;
//import com.MotherSon.CRM.security.services.CountryService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/country")
public class CountryController {

	@Autowired
	private CountryService countryService;

	@GetMapping("all")
	 public ResponseEntity<Response<List<CountryDTO>>> getAllCountys() {
	        try {
	            // Fetch all countries as DTOs
	            List<CountryDTO> countries = countryService.getAllCountries();

	            // Check if list is empty and throw an exception if needed
	            if (countries.isEmpty()) {
	                throw new CustomException("No countries found", "404");
	            }

	            // Wrap the response in the custom Response<T> object
	            Response<List<CountryDTO>> response = new Response<>(
	                "success",
	                "Countries fetched successfully",
	                countries
	            );

	            // Return the Response<T> wrapped in a ResponseEntity
	            return ResponseEntity.ok(response);
	        } catch (Exception ex) {
	            // Log exception details (optional)
	            ex.printStackTrace();

	            // Rethrow the exception to be handled by GlobalExceptionHandler
	            throw ex;
	        }
	    }

	@GetMapping("/all/paginated")
	public ResponseEntity<Response<Page<Country>>> getCountry(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection
	) {
	    try {
	        // Fetch paginated countries
	        Page<Country> countries = countryService.getCountry(page, size, sortDirection);

	        // Return response
	        Response<Page<Country>> response = new Response<>(
	            "success",
	            "Countries fetched successfully",
	            countries
	        );
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (IllegalArgumentException ex) {
	        // Handle invalid arguments
	        throw new CustomException("Invalid pagination or sort parameters: " + ex.getMessage(), "400");
	    } catch (Exception ex) {
	        // Handle general exceptions
	        throw new CustomException("An unexpected error occurred: " + ex.getMessage(), "500");
	    }
	}


	@GetMapping("/{id}")
	public ResponseEntity<Response<CountryDTO>> getCountrysById(@PathVariable Long id) {
	    CountryDTO country = countryService.getCountrysById(id);

	    // Build a successful response
	    Response<CountryDTO> response = new Response<>(
	        "SUCCESS",
	        "Country fetched successfully",
	        country
	    );
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


//	   
	private String timestamp;
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/image/countryimages";
	
	@PostMapping("")
	public ResponseEntity<?> saveCountry(@Valid @ModelAttribute Country country,
	                                          BindingResult result,
	                                          @RequestParam("image") MultipartFile[] files, HttpServletRequest request) throws IOException {
		
		String clientIpAddress = getClientIp(request);
	     country.setIpaddress(clientIpAddress);
	     
	    if (result.hasErrors()) {
	        String errorMessage = "Validation failed: " + result.getFieldErrors().stream()
	                                    .map(error -> error.getField() + " - " + error.getDefaultMessage())
	                                    .collect(Collectors.joining(", "));
	        return ResponseEntity.badRequest().body(new ErrorResponse("FAILED", errorMessage, "400"));
	    }

	   
	    if (countryService.existsByCountryName(country.getCountryName())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body(new ErrorResponse("FAILED", "Country with the name " + country.getCountryName() + " already exists.", "409"));
	    }

	    List<String> imageUrls = new ArrayList<>();
	    Path uploadPath = Paths.get(uploadDirectory);
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }

	    
	    for (MultipartFile file : files) {
	        if (!isValidImage(file)) {
	            throw new CustomException("File must be a JPEG or PNG image.", "400");
	        }

	        String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());
	        Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);
	        Files.write(fileNameAndPath, file.getBytes());

	        String imageUrl = "/image/" + uniqueFilename;
	        imageUrls.add(imageUrl);
	    }
	    country.setCimage(imageUrls);

	    Country savedCountry = countryService.addCountry(country);
	    return ResponseEntity.ok(new Response<>("SUCCESS", "Country created successfully", "created"));
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


	    String uniqueID = UUID.randomUUID().toString();

	    return timestamp + "_" + uniqueID + extension;
	}
	
	
	
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCountry(@PathVariable Long id,
	                                       @ModelAttribute Country country,
	                                       @RequestParam(value = "image", required = false) MultipartFile[] files, HttpServletRequest request) throws IOException {
	   
		String clientIpAddress = getClientIp(request);
	     country.setIpaddress(clientIpAddress);
		
		try {
	        // Fetch existing country
	        Country existingCountry = countryService.getCountryById(id);

	       
	        if (existingCountry.getCimage() != null) {
	            for (String oldImageUrl : existingCountry.getCimage()) {
	                Path oldImagePath = Paths.get(uploadDirectory, oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1));
	                Files.deleteIfExists(oldImagePath);
	            }
	        }

	        List<String> imageUrls = new ArrayList<>();

	        
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

	        country.setCimage(imageUrls); // Set the new images

	        Country updatedCountry = countryService.updateCountryById(id, country);

	        
	        //Response<Country> response = new Response<>("SUCCESS", "Country updated successfully", null);
	           // OR
	        
	        Response<String> response = new Response<>("SUCCESS", "Country updated successfully", "Updated");
	        
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	    	
	        ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), "404");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    }
	}

	private boolean isValidImage1(MultipartFile file) {
	    String contentType = file.getContentType();
	    return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
	}

	private String generateUniqueFilename1(String originalFilename) {
	    return System.currentTimeMillis() + "_" + originalFilename;
	}



	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        try {
            String result = countryService.deleteCountry(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "Country deleted successfully", result), HttpStatus.OK);
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

