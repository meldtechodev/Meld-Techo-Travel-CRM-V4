package com.meld.techo.travel.crm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

import com.meld.techo.travel.crm.dto.CityDTO;
import com.meld.techo.travel.crm.dto.CustomException;

import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.City;
import com.meld.techo.travel.crm.security.service.CityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("Motherson/crm/v1/city")
public class CityController {
	
	@Autowired
	private CityService cityService;
	


	
	

	private String timestamp;
	public static String uploadDirectory=System.getProperty("user.dir") + "/src/main/image/Dimages";
	
	
	@PostMapping("")
	public ResponseEntity<?> saveCity(@Valid @ModelAttribute City city,
	                                          BindingResult result,
	                                          @RequestParam("image") MultipartFile[] files, HttpServletRequest request) throws IOException {
		
		String clientIpAddress = getClientIp(request);
	     city.setIpaddress(clientIpAddress);
	     
	    if (result.hasErrors()) {
	        String errorMessage = "Validation failed: " + result.getFieldErrors().stream()
	                                    .map(error -> error.getField() + " - " + error.getDefaultMessage())
	                                    .collect(Collectors.joining(", "));
	        return ResponseEntity.badRequest().body(new ErrorResponse("FAILED", errorMessage, "400"));
	    }

	   
	    if (cityService.existsByCityName(city.getCityName())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body(new ErrorResponse("FAILED", "City with the name " + city.getCityName() + " already exists.", "409"));
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
	    city.setD_image(imageUrls);

	    City savedCity = cityService.createdcity(city);
	    return ResponseEntity.ok(new Response<>("SUCCESS", "City created successfully", "created"));
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
	
	

	

	
	
	@GetMapping("/all/paginated")
	public ResponseEntity<?> getCity(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
	    try {
	        // Fetch paginated and filtered destinations in DTO format.
	        Page<CityDTO> citys = cityService.getCity(page, size, sortDirection);
	        
	        // Wrap the result in a custom Response DTO.
	        Response<Page<CityDTO>> response = new Response<>("SUCCESS", "City fetched successfully", citys);
	        
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        // Handle exceptions and return error response.
	        ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), "500");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	
	
	@PutMapping("/{id}")
    public ResponseEntity<?> updateCity(@PathVariable Long id,
                                               @ModelAttribute City city,
                                               @RequestParam(value = "image", required = false) MultipartFile[] files, HttpServletRequest request) throws IOException {
		
        try {
        	
        	String clientIpAddress = getClientIp(request);
   	     city.setIpaddress(clientIpAddress);
            City existingCity = cityService.getCityById(id);

            if (existingCity.getD_image() != null) {
                for (String oldImageUrl : existingCity.getD_image()) {
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

            city.setD_image(imageUrls);

            City updatedCity = cityService.updateCityById(id, city);

            
            //Response<Destination> response = new Response<>("SUCCESS", "Destination updated successfully", updatedDestination);
            //return ResponseEntity.ok(response);
            
            // OR This
            
            return ResponseEntity.ok(new Response<>("SUCCESS", "City created successfully", "updated"));
    	
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
        return System.currentTimeMillis() + "_" + originalFilename; // Unique filename based on current time
    }

	
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        try {
            String result = cityService.deleteCity(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "City deleted successfully", result), HttpStatus.OK);
        } catch (CustomException e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), e.getErrorCode());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", "An unexpected error occurred: " + e.getMessage(), "500");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }}
    
    
    
    
    @GetMapping("/all")
    public ResponseEntity<Response<Object>> getAllCitys() {
        try {
            
            Response<Object> response = cityService.getAllCity();

           
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
           
            throw e; 
    }}

    

    
    


	@GetMapping("/country/{countryId}")
    public ResponseEntity<Response<Object>>getCityByCountryId(@PathVariable Long countryId){
		try {
    	Response<Object> response = cityService.getCityByCountryId(countryId);
    	
    	return new ResponseEntity(response, HttpStatus.OK);
    } catch(Exception e) {
    	throw e;
    }
	}
	

    @GetMapping("/state/{stateId}")
    public ResponseEntity<Response<Object>> getCityByStateId(@PathVariable Long stateId){
    	
    	try {
    		Response<Object> response = cityService.getCityByStateId(stateId);
    		return new ResponseEntity<>(response, HttpStatus.OK);
    	} catch(Exception e) {
    		throw e;
    	}
    }
    
    
 // Helper method to fetch client IP address
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}

    