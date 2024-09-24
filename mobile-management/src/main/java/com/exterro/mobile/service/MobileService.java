/**
 * @Placed com.exterro.mobile.service
 */
package com.exterro.mobile.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exterro.mobile.model.Mobile;

/**
 * @author mrangasamy
 *
 * @date 12-Aug-2024
 */

@Service
public interface MobileService {
	 CompletableFuture<Mobile> addMobile(Mobile mobile);
	 CompletableFuture<List<Mobile>> addFromFile(MultipartFile file) throws EncryptedDocumentException, IOException;
	 CompletableFuture<List<Mobile>> getAllMobile();
	 CompletableFuture<Mobile> getMobile(String imei);
	 CompletableFuture<Mobile> updateMobile(Mobile mobile);
	 CompletableFuture<String> deleteMobile(String imei);
	 CompletableFuture<ByteArrayInputStream> exportMobile(List<Mobile> mobileList, String fileName) throws IOException;
}
