/**
 * @Placed com.exterro.mobile.service
 */
package com.exterro.mobile.service;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exterro.mobile.model.Mobile;
import com.exterro.mobile.repository.MobileRepository;

/**
 * @author MirdulaRangasamy
 *
 * @date 11 Sept 2024
 */
@ExtendWith(MockitoExtension.class)
class MobileServiceImplTest {

	/**
	 * Test method for {@link com.exterro.mobile.service.MobileServiceImpl#addMobile(com.exterro.mobile.model.Mobile)}.
	 */
	@Mock
	private MobileRepository mobileRepo;
	
	@InjectMocks
	private MobileServiceImpl mobileSvc;
	
	
	@Test
	void testAddMobile() {
		Mobile mobile1 = new Mobile(0,"I101","Samsung","S10",17000.0);
		Mobile mobile2 = new Mobile(41,"I101","Samsung","S10",17000.0);
		when(mobileRepo.save(mobile1)).thenReturn(mobile2);
		Mobile mobile3 = mobileRepo.save(mobile1);

		assertEquals(mobile3.getId(),41);
	}

	/**
	 * Test method for {@link com.exterro.mobile.service.MobileServiceImpl#getAllMobile()}.
	 */
	@Test
	void testGetAllMobile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.exterro.mobile.service.MobileServiceImpl#getMobile(java.lang.String)}.
	 */
	@Test
	void testGetMobile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.exterro.mobile.service.MobileServiceImpl#deleteMobile(java.lang.String)}.
	 */
	@Test
	void testDeleteMobile() {
		fail("Not yet implemented");
	}

}
