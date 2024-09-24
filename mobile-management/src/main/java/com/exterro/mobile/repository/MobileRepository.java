/**
 * @Placed com.exterro.mobile.repository
 */
package com.exterro.mobile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exterro.mobile.model.Mobile;

/**
 * @author mrangasamy
 *
 * @date 12-Aug-2024
 */
public interface MobileRepository extends JpaRepository<Mobile,Integer>{
	Optional<Mobile> findByImei(String imei);
}
