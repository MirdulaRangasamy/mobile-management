/**
 * @Placed com.exterro.mobile.service
 */
package com.exterro.mobile.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exterro.mobile.model.Mobile;
import com.exterro.mobile.repository.MobileRepository;

/**
 * @author mrangasamy
 *
 * @date 12-Aug-2024
 */
@Service
public class MobileServiceImpl implements MobileService {

	@Autowired
	private MobileRepository mobileRepo;

	@Override
	@Async("asyncTask")
	public CompletableFuture<Mobile> addMobile(Mobile mobile) {
		System.out.println(Thread.currentThread().getName() + " - In Service - addMobile");
		return CompletableFuture.completedFuture(mobileRepo.save(mobile));
	}

	@Override
	@Async("asyncTask")
	public CompletableFuture<List<Mobile>> addFromFile(MultipartFile file) throws EncryptedDocumentException, IOException {
		System.out.println(Thread.currentThread().getName() + " - In Service - addFromFile");
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		Iterator<Sheet> sheetItr = workbook.sheetIterator();
		List<Mobile> mobiles = new ArrayList<Mobile>();
		while (sheetItr.hasNext()) {
			Sheet sheet = sheetItr.next();
			Iterator<Row> rowItr = sheet.rowIterator();
			if(rowItr.hasNext()) {
				rowItr.next();
			}
			System.out.println(Thread.currentThread().getName() + " - In Service - addFromFile");
			while (rowItr.hasNext()) {
				Row row = rowItr.next();
				Iterator<Cell> cellItr = row.cellIterator();
				Mobile m = new Mobile();
				if (cellItr.hasNext()) {
					m.setImei(cellItr.next().toString());
				}
				if (cellItr.hasNext()) {
					m.setImei(cellItr.next().toString());
				}
				if (cellItr.hasNext()) {
					m.setBrand(cellItr.next().toString());
				}
				if (cellItr.hasNext()) {
					m.setModel(cellItr.next().toString());
				}
				if (cellItr.hasNext()) {
					m.setCost(Double.parseDouble(cellItr.next().toString()));
				}
				mobiles.add(m);
			}
		}
		mobiles = mobileRepo.saveAll(mobiles);
		System.out.println(mobiles);
		System.out.println(Thread.currentThread().getName() + " - In Service - addFromFile");
		return CompletableFuture.completedFuture(mobiles);
	}

	@Override
	@Async("asyncTask")
	public CompletableFuture<List<Mobile>> getAllMobile() {
		System.out.println(Thread.currentThread().getName() + " - In Service - getAllMobile");
		return CompletableFuture.completedFuture(mobileRepo.findAll());
	}

	@Override
	@Async("asyncTask")
	public CompletableFuture<Mobile> getMobile(String imei) {
		System.out.println(Thread.currentThread().getName() + " - In Service - getMobile");
		return CompletableFuture.completedFuture(mobileRepo.findByImei(imei).orElseThrow(() -> new RuntimeException("Mobile Unavailable")));
	}

	@Override
	@Async("asyncTask")
	public CompletableFuture<Mobile> updateMobile(Mobile mobile) {
		System.out.println(Thread.currentThread().getName() + " - In Service - updateMobile");
		Mobile m = mobileRepo.findByImei(mobile.getImei())
				.orElseThrow(() -> new RuntimeException("Mobile Unavailable"));
		mobile.setId(m.getId());
		return CompletableFuture.completedFuture(mobileRepo.save(mobile));
	}

	@Override
	@Async("asyncTask")
	public CompletableFuture<String> deleteMobile(String imei) {
		System.out.println(Thread.currentThread().getName() + " - In Service - deleteMobile");
		Optional<Mobile> m = mobileRepo.findByImei(imei);
		if (m.isPresent()) {
			mobileRepo.delete(m.get());
			System.out.println(Thread.currentThread().getName() + " - In Service - deleteMobile");
			return CompletableFuture.completedFuture("Mobile deleted Successfully");
		}
		return CompletableFuture.completedFuture("Mobile not Available");
	}

	@Override
	@Async("asyncTask")
	public CompletableFuture<ByteArrayInputStream> exportMobile(List<Mobile> mobileList, String fileName) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		System.out.println(Thread.currentThread().getName() + " - In Service - exportMobile");
		Sheet sh = workbook.createSheet("mobileList");
		String[] columnHeadings = { "ID", "IMEI NO", "BRAND", "MODEL", "COST" };
		Row headerRow = sh.createRow(0);
		// Iterate over the column headings to create columns
		for (int i = 0; i < columnHeadings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnHeadings[i]);
		}
		int rownum = 1;
		System.out.println(Thread.currentThread().getName() + " - In Service - exportMobile");
		for (Mobile mobile : mobileList) {
			Row row = sh.createRow(rownum++);
			row.createCell(0).setCellValue(mobile.getId());
			row.createCell(1).setCellValue(mobile.getImei());
			row.createCell(2).setCellValue(mobile.getBrand());
			row.createCell(3).setCellValue(mobile.getModel());
			row.createCell(4).setCellValue(mobile.getCost());
		}
		
		//"Downloads/"+fileName
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		System.out.println(Thread.currentThread().getName() + " - In Service - exportMobile");
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		return CompletableFuture.completedFuture(new ByteArrayInputStream(fileOut.toByteArray()));
	}

}
