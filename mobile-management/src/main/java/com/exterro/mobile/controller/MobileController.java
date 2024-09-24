/**
 * @Placed com.exterro.mobile.controller
 */
package com.exterro.mobile.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exterro.mobile.model.Mobile;
import com.exterro.mobile.service.MobileService;

/**
 * @author mrangasamy
 *
 * @date 12-Aug-2024
 */

@RestController
@CrossOrigin("http://localhost:4200/")
public class MobileController {

	@Autowired
	private MobileService mobileSvc;
	
    @GetMapping("/export")
    public ResponseEntity<Resource> exportMobile() throws Exception {
    	System.out.println(Thread.currentThread().getName()+" - In Controller - Export Mobile");
        List<Mobile> mobileList = mobileSvc.getAllMobile().get();

        if (!CollectionUtils.isEmpty(mobileList)) {
            String fileName = "MobileList" + ".xlsx";

            ByteArrayInputStream in = mobileSvc.exportMobile(mobileList, fileName).get();

            InputStreamResource inputStreamResource = new InputStreamResource(in);
            System.out.println(Thread.currentThread().getName()+" - In Controller - Export Mobile");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    )
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel; charset=UTF-8"))
                    .body(inputStreamResource);
        } else {
            throw new Exception("No data");
        }
    }
    
    @PostMapping("addMobile")
    public ResponseEntity<Mobile> addMobile(@RequestBody Mobile mobile) throws InterruptedException, ExecutionException{
    	System.out.println(Thread.currentThread().getName()+" - In Controller - addMobile");
    	return ResponseEntity.ok(mobileSvc.addMobile(mobile).get());
    }
    
    @PostMapping("import")
    public ResponseEntity<?> addMobileFromFile(@RequestParam MultipartFile file) throws InterruptedException, ExecutionException{
    	System.out.println(Thread.currentThread().getName()+" - In Controller - addMobiles from file");
    	try {
    		
			return ResponseEntity.ok(mobileSvc.addFromFile(file).get());
		} catch (EncryptedDocumentException | InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ArrayList<>());
		}
    	
    }
    
    @PutMapping("updateMobile")
    public ResponseEntity<Mobile> updateMobile(@RequestBody Mobile mobile) throws InterruptedException, ExecutionException{
    	System.out.println(Thread.currentThread().getName()+" - In Controller - updateMobile");
    	return ResponseEntity.ok(mobileSvc.updateMobile(mobile).get());
    }
    
    @GetMapping("getAllMobiles")
    public ResponseEntity<List<Mobile>> getAllMobiles() throws InterruptedException, ExecutionException{
    	System.out.println(Thread.currentThread().getName()+" - In Controller - getAllMobiles");
    	return ResponseEntity.ok(mobileSvc.getAllMobile().get());
    }
    
    @GetMapping("getMobile/{imei}")
    public ResponseEntity<Mobile> getMobile(@PathVariable String imei) throws InterruptedException, ExecutionException{
    	System.out.println(Thread.currentThread().getName()+" - In Controller - getMobile");
    	return ResponseEntity.ok(mobileSvc.getMobile(imei).get());
    }
    
    @DeleteMapping("deleteMobile/{imei}")
    public ResponseEntity<String> deleteMobile(@PathVariable String imei) throws InterruptedException, ExecutionException{
    	System.out.println(Thread.currentThread().getName()+" - In Controller - deleteMobile");
    	return ResponseEntity.ok(mobileSvc.deleteMobile(imei).get());
    }
}
